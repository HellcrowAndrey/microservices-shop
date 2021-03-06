package com.github.admins.controllers.impl;

import com.github.admins.controllers.IDeliveryCompanyController;
import com.github.admins.dto.CompanyDto;
import com.github.admins.exceptions.BadRequest;
import com.github.admins.exceptions.NotFound;
import com.github.admins.services.IDeliveryCompaniesService;
import com.github.admins.utils.Logging;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/delivery/company")
public class DeliveryCompanyController implements IDeliveryCompanyController {

    private final IDeliveryCompaniesService deliveryCompaniesService;

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public CompanyDto save(CompanyDto payload) {
        return this.deliveryCompaniesService.save(payload)
                .orElseThrow(BadRequest::new);
    }

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public CompanyDto findById(Long id) {
        return this.deliveryCompaniesService.findById(id)
                .orElseThrow(NotFound::new);
    }

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public void update(CompanyDto payload) {
        this.deliveryCompaniesService.update(payload);
    }

    @Override
    @HystrixCommand
    @Logging(isTime = true, isReturn = false)
    public void delete(Long id) {
        this.deliveryCompaniesService.delete(id);
    }
}
