package com.example.demokafka.controller;

import com.example.demokafka.dto.CreateOrderDto;
import com.example.demokafka.dto.OrderDto;
import com.example.demokafka.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDto createOrder(@RequestBody CreateOrderDto dto){
        return orderService.createOrder(dto);
    }
}
