package com.github.ethereum.services.impl;

import com.github.ethereum.entity.*;
import com.github.ethereum.services.*;
import com.github.ethereum.utils.Logging;
import com.github.ethereum.utils.TransferObj;
import com.github.wrapper.ethrereum.facade.IFacadeEthereum;
import com.github.wrapper.ethrereum.model.Information;
import com.github.wrapper.ethrereum.model.TransactionData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.github.ethereum.entity.Contract.DEFAULT_CONTRACT_NAME;

@Service
@RequiredArgsConstructor
public class NetworkService implements INetworkService {

    private final IFacadeEthereum facade;

    private final IAccountService accountService;

    private final IContractService contractService;

    private final ITransactionService transactionService;

    private final IFeeService feeService;

    private final IBlockService blockService;

    @Override
    @Logging
    public Long findBlockNumber() {
        Block block = this.blockService.readByStatus(EntityStatus.on);
        if(Objects.isNull(block)) {
            return this.facade.bastBlock()
                    .orElseGet(() -> this.blockService.createDefault().getNumber());
        }
        return block.getNumber();
    }

    @Override
    @Logging
    public void createOrUpdateFee(Information info) {
        Fee fee = this.feeService.readByStatus(EntityStatus.on);
        if (Objects.isNull(fee)) {
            this.feeService.create(new Fee(info.getFee(), info.getGasPrice(), EntityStatus.on));
        } else {
            this.feeService.update(info.getFee(), info.getGasPrice(), EntityStatus.on);
        }
    }

    @Override
    public List<String> contractsAddresses() {
        return this.contractService.readAllAddresses();
    }

    @Override
    public List<String> accountAddresses() {
        return this.accountService.readAddressesByStatus(EntityStatus.on);
    }

    @Override
    @Logging
    public void incomingTransaction(TransactionData data) {
        if (Objects.nonNull(data)) {
            Account account = this.accountService.readByAddress(data.getTo());
            Contract contract = this.contractService.readByName(DEFAULT_CONTRACT_NAME);
            Transaction tmp = TransferObj.toIncomingTransaction(data, contract);
            Transaction transaction = this.transactionService.create(tmp);
            account.addCurrencyBal(data.getValue());
            account.addTransaction(transaction);
            account.setStatus(EntityStatus.off);
            this.accountService.update(account);
        }
    }

    @Override
    @Logging
    public void outgoingTransaction(TransactionData data) {
        if (Objects.nonNull(data)) {
            Account account = this.accountService.readByAddress(data.getFrom());
            account.subCurrencyBal(data.getValue(), data.getFee());
            this.accountService.update(account);
            Transaction transaction = this.transactionService.readByHash(data.getHash());
            transaction.setBlockHash(data.getBlockHash());
            transaction.setBlockNumber(data.getBlockNumber());
            transaction.setStatus(EntityStatus.on);
            this.transactionService.update(transaction);
        }
    }

    @Override
    @Logging
    public void incomingContract(TransactionData data) {
        if (Objects.nonNull(data)) {
            Account account = this.accountService.readByAddress(data.getTo());
            Contract contract = this.contractService.readByAddress(data.getContact());
            Transaction tmp = TransferObj.toIncomingTransaction(data, contract);
            Transaction transaction = this.transactionService.create(tmp);
            account.addContractBal(contract.getName(), transaction.getValue());
            account.addTransaction(transaction);
            account.setStatus(EntityStatus.off);
            this.accountService.update(account);
        }
    }

    @Override
    @Logging
    public void outgoingContract(TransactionData data) {
        if (Objects.nonNull(data)) {
            Contract contract = this.contractService.readByAddress(data.getContact());
            Account account = this.accountService.readByAddress(data.getFrom());
            account.subContractBal(contract.getName(), data.getValue(), data.getFee());
            this.accountService.update(account);
            Transaction transaction = this.transactionService.readByHash(data.getHash());
            transaction.setBlockHash(data.getBlockHash());
            transaction.setBlockNumber(data.getBlockNumber());
            transaction.setStatus(EntityStatus.on);
            this.transactionService.update(transaction);
        }
    }

    @Override
    @Logging
    public void updateBlockNumber(Long number) {
        this.blockService.update(number, EntityStatus.on);
    }

}
