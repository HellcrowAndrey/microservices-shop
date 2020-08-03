package com.github.admins.services;

import com.github.admins.payload.OrderDetail;
import com.github.admins.payload.OrderStatus;
import com.github.admins.services.impl.OrderService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(
        name = "orders-service",
        fallback = OrderService.class
)
public interface IOrderService {

    @GetMapping(
            path = "/v1/fetch/{status}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Optional<List<OrderDetail>> readAllByStatus(@PathVariable OrderStatus status);

    @GetMapping(
            path = "/v1/fetch/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Optional<OrderDetail> readById(@RequestParam Long id);

    @GetMapping(
            path = "/v1/fetch/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    Optional<List<OrderDetail>> readByUserId(@PathVariable Long userId);

    @PutMapping(
            path = "/v1/edit/"
    )
    void update(@RequestBody OrderDetail o);

    @PutMapping(
            path = "/v1/edit/{productId}/{orderStatus}"
    )
    void update(@PathVariable Long productId, @PathVariable OrderStatus orderStatus);

}
