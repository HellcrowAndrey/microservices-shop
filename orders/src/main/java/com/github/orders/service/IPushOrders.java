package com.github.orders.service;

import com.github.orders.dto.OrderDto;
import com.github.orders.service.impl.PushOrders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(
        name = "websocket",
        fallback = PushOrders.class
)
public interface IPushOrders {

    @PostMapping(
            path = "/v1/push",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    void pushOrder(@RequestBody OrderDto payload);

}
