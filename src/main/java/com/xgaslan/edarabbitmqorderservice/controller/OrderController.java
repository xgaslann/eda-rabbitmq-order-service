package com.xgaslan.edarabbitmqorderservice.controller;

import com.xgaslan.edarabbitmqorderservice.dto.Order;
import com.xgaslan.edarabbitmqorderservice.dto.OrderEvent;
import com.xgaslan.edarabbitmqorderservice.dto.Status;
import com.xgaslan.edarabbitmqorderservice.publisher.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProducer orderProducer;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = OrderEvent.builder()
                .status(Status.PENDING)
                .message("Order is in pending state")
                .order(order)
                .build();

        orderProducer.sendMessage(orderEvent);
        return ResponseEntity.ok("Order placed successfully with ID: " + order.getOrderId());
    }
}
