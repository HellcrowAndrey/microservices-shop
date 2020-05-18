package com.github.users.center.controllers;

import com.github.users.center.dto.UserAuthDto;
import com.github.users.center.dto.UserRegDto;
import com.github.users.center.payload.JwtRefreshResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;

public interface IAdminController {

    @PostMapping(path = "/reg")
    ResponseEntity<Void> submitReg(
            @RequestHeader(name = "Client-Address") String userUrl,
            @Valid @RequestBody UserRegDto payload
    );

    @PostMapping(path = "/auth")
    ResponseEntity<JwtRefreshResponse> submitAuth(
            @RequestHeader(name = "ETag") String fingerprint,
            @RequestHeader(name = "Location") String address,
            @Valid @RequestBody UserAuthDto payload
    );

    @PostMapping(
            path = "/refresh-tokens",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<JwtRefreshResponse> submitRefreshSession(
            @RequestHeader(name = "Refresh-Token") String refreshToken,
            @Valid @RequestBody String fingerprint
    );

}