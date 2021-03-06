package com.github.admins.controllers;

import com.github.admins.dto.PaymentTypesDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IPaymentTypeController {

    @GetMapping(
            path = "/{status}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.OK)
    List<PaymentTypesDto> findAllByStatus(
            @PathVariable(value = "status") String status
    );

    @PutMapping
    @ResponseStatus(code = HttpStatus.OK)
    void update(@RequestBody PaymentTypesDto payload);

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void remove(@PathVariable(value = "id") Long id);

}
