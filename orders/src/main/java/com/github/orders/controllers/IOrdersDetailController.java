package com.github.orders.controllers;

import com.github.orders.dto.OrderDetailDto;
import com.github.orders.entity.OrderDetail;
import com.github.orders.entity.OrderStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface IOrdersDetailController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createOrder(
            @RequestAttribute(name = "userId") Long userId,
            @RequestBody @Valid OrderDetailDto payload
    );

    @GetMapping(
            path = "/v1/info/{status}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<OrderDetail> readAllByStatus(@PathVariable OrderStatus status);

    @GetMapping(
            path = "/v1/info/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    OrderDetail readById(@RequestParam Long id);

    @GetMapping(
            path = "/v1/info/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    List<OrderDetail> readByUserId(@PathVariable Long userId);

    @PutMapping(
            path = "/v1/edit/"
    )
    void update(@RequestBody OrderDetail o);

    @PutMapping(
            path = "/v1/edit/{productId}/{orderStatus}"
    )
    void update(@PathVariable Long productId, @PathVariable OrderStatus orderStatus);

}