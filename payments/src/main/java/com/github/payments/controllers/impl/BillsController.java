package com.github.payments.controllers.impl;

import com.github.payments.controllers.IBillsController;
import com.github.payments.dto.BillDto;
import com.github.payments.entity.*;
import com.github.payments.payload.Report;
import com.github.payments.service.*;
import com.github.payments.utils.Logging;
import com.github.payments.utils.TransferObj;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.payments.utils.TransferObj.*;
import static java.util.concurrent.CompletableFuture.runAsync;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/bills")
public class BillsController implements IBillsController {

    private final IBillsService billService;

    private final IBillNotifyService billPushService;

    private final IPaymentTypesService paymentTypesService;

    private final IAssetsService assetsService;

    private final IAliasService aliasService;

    private final IUsersAliasService usersAliasService;

    private final IWhomService whomService;

    private final IWhoService whoService;

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public BillDto save(Long orderId, BillDto payload) {
        PaymentTypes type = this.paymentTypesService
                .readByAlias(payload.getPaymentType());
        Asset asset = this.assetsService.readByName(payload.getAssetName());
        Whom whom = this.whomService.create(toWhom(payload.getWhom()));
        Who who = this.whoService.create(toWho(payload.getWho()));
        Bill bill = toBill(payload, orderId).forCreate(asset, type).forCreate(who, whom);
        return fromBill(this.billService.create(bill));
    }

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public Object findByParams(Long id, EntityStatus status) {
        if (Objects.isNull(id)) {
            return this.billService.readByStatus(status).stream()
                    .map(TransferObj::fromBill)
                    .collect(Collectors.toList());
        }
        return fromBill(this.billService.readById(id));
    }

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public Report updateCrypto(String address, BigInteger value, String transfer) {
        Bill bill = this.billService.readByByAddressAndStatus(address, EntityStatus.on);
        var amount = bill.getAmount();
        var amountPaid = bill.getAmountPaid().add(value);
        var different = amount.subtract(amountPaid);
        bill.different(different).forUpdate(amountPaid, transfer);
        this.billService.update(bill);
        runAsync(() -> billNotify(bill));
        return new Report(amount, amountPaid, different);
    }

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public void updateMastercard(Long billId, BigInteger value, String transfer) {
        Bill bill = this.billService.readById(billId);
        var amount = bill.getAmount();
        var amountPaid = bill.getAmountPaid().add(value);
        var different = amount.subtract(amountPaid);
        bill.different(different).forUpdate(amountPaid, transfer);
        this.billService.update(bill);
    }

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public void remove(Long id) {
        this.billService.remove(id);
    }

    private void billNotify(Bill bill) {
        Alias notify = this.aliasService.findByBillId(bill.getId());
        var ending = this.usersAliasService.findEndingUrl(notify.getUserId())
                .orElse("default");
        this.billPushService.billNotify(ending, fromBill(bill));
    }

}
