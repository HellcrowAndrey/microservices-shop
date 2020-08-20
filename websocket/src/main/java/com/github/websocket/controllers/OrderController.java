package com.github.websocket.controllers;

import com.github.websocket.dto.OrderDto;
import com.github.websocket.network.Broker;
import com.github.websocket.utils.Logging;
import com.github.websocket.utils.Topics;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/push")
@RequiredArgsConstructor
public class OrderController {

    private final Broker broker;

    @PostMapping
    @HystrixCommand
    @ResponseStatus(HttpStatus.OK)
    @Logging(isTime = true, isReturn = false)
    public void pushOrder(@RequestBody OrderDto payload) {
        this.broker.sendOrder(payload);
    }

}
