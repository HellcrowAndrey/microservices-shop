package com.github.deliveries.controllers;

import com.github.deliveries.dto.CompanyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface IDeliveryCompaniesController {

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<CompanyDto> findAll();

    @PostMapping(
            path = "/edit",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    CompanyDto save(@Valid @RequestBody CompanyDto payload);

    @GetMapping(
            path = "/fetch/{id}"
    )
    CompanyDto findById(@PathVariable(name = "id") Long id);

    @PutMapping(
            path = "/edit"
    )
    @ResponseStatus(code = HttpStatus.OK)
    void update(@Valid @RequestBody CompanyDto payload);

    @DeleteMapping(
            path = "/edit/{id}"
    )
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    void remove(@PathVariable(name = "id") Long id);

}
