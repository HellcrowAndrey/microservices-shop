package com.github.admins.services;

import com.github.admins.dto.UserRegDto;
import com.github.admins.services.impl.UsersCenterService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "users-center",
        fallback = UsersCenterService.class,
        contextId = "usersCenterId"
)
public interface IUsersCenterService {

    @PostMapping(
            path = "/v1/admins/reg",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void createAdmins(@RequestBody UserRegDto payload);

    @PostMapping(
            path = "/v1/managers/reg",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    void createManager(@RequestBody UserRegDto payload);

}