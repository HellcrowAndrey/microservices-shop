package com.github.admins.controllers.impl;

import com.github.admins.controllers.IOrderDetailController;
import com.github.admins.dto.OrderDetailDto;
import com.github.admins.dto.ProductDto;
import com.github.admins.payload.OrderDetail;
import com.github.admins.payload.OrderStatus;
import com.github.admins.services.IOrderService;
import com.github.admins.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.admins.utils.TransferObj.fromOrderDetailDto;
import static com.github.admins.utils.TransferObj.toOrderDetail;

@RestController
@RequestMapping(path = "/v1/orders")
@RequiredArgsConstructor
public class OrderDetailController implements IOrderDetailController {

    private final IOrderService orderService;

    private final IProductService productService;

    @Override
    public List<OrderDetailDto> ordersByStatus(OrderStatus status) {
        var orders = this.orderService.readAllByStatus(status);
        return orders.stream()
                .map(this::collect)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailDto orderById(Long orderId) {
        var order = this.orderService.readById(orderId);
        var products = this.productService.readByIds(order.getProductIds());
        return fromOrderDetailDto(order, products);
    }

    @Override
    public List<OrderDetailDto> userHistory(Long userId) {
        var history = this.orderService.readByUserId(userId);
        return history.stream()
                .map(this::collect)
                .collect(Collectors.toList());
    }

    @Override
    public void updateOrder(@Valid OrderDetailDto payload) {
        var productIds = payload.getProducts().stream()
                .map(ProductDto::getId)
                .collect(Collectors.toList());
        this.orderService.update(toOrderDetail(payload, productIds));
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        this.orderService.update(orderId, orderStatus);
    }

    private OrderDetailDto collect(OrderDetail order) {
        return fromOrderDetailDto(
                order,
                this.productService.readByIds(order.getProductIds())
        );
    }

}
