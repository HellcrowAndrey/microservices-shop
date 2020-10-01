package com.github.admins.services;

import com.github.admins.payload.Company;
import com.github.admins.services.impl.DeliveryCompaniesService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(
        name = "orders-service",
        fallback = DeliveryCompaniesService.class,
        contextId = "deliveryCompanyId"
)
public interface IDeliveryCompaniesService {

    @PostMapping(
            path = "/v1/delivery/company/edit",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    Optional<Company> save(@RequestBody Company payload);

    @GetMapping(
            path = "/v1/delivery/company/fetch/{id}"
    )
    Optional<Company> findById(@PathVariable(name = "id") Long id);

    @PutMapping(
            path = "/v1/delivery/company/edit"
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    void update(@RequestBody Company payload);

    @DeleteMapping(
            path = "/v1/delivery/company/edit/{id}"
    )
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void delete(@PathVariable(name = "id") Long id);

}