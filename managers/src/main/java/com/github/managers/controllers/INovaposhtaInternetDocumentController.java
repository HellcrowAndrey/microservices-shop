package com.github.managers.controllers;

import com.github.managers.dto.NovaposhtaInternetDocumentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface INovaposhtaInternetDocumentController {

    @PostMapping(
            path = "/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    NovaposhtaInternetDocumentDto save(
            @PathVariable(name = "orderId") Long orderId,
            @Valid @RequestBody NovaposhtaInternetDocumentDto payload
    );

    @GetMapping(
            path = "/{orderId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(value = HttpStatus.OK)
    NovaposhtaInternetDocumentDto findByOrderId(@PathVariable(name = "orderId") Long orderId);

}
