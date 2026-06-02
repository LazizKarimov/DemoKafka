package com.example.demokafka.service;

import com.example.demokafka.dto.CreateOrderDto;
import com.example.demokafka.dto.OrderDto;
import com.example.demokafka.mapper.OrderMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderDto createOrder(CreateOrderDto dto){
        var order = orderRepository.save(orderMapper.toEntity(dto));

        return orderMapper.toDto(order);
    }
}
