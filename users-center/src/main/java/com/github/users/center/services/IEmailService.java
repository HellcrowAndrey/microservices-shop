package com.github.users.center.services;

import com.github.users.center.payload.EmailNotification;
import com.github.users.center.services.impl.EmailService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "emails",
        fallback = EmailService.class
)
public interface IEmailService {

    @PostMapping(
            path = "/v1/submit/reg",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void submitReg(@RequestBody EmailNotification payload);

    @PostMapping(
            path = "/v1/reset/pass",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void resetPass(@RequestBody EmailNotification payload);

    @PostMapping(
            path = "/v1/login/notification",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void loginNotification(@RequestBody EmailNotification payload);

}
