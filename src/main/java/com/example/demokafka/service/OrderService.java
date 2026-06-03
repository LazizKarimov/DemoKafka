package com.example.demokafka.service;

import com.example.demokafka.dto.CreateOrderDto;
import com.example.demokafka.dto.OrderDto;
import com.example.demokafka.enums.RetryableTaskType;
import com.example.demokafka.mapper.OrderMapper;
import com.example.demokafka.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RetryableTaskService retableTaskService;


    @Transactional
    public OrderDto createOrder(CreateOrderDto dto){
        var order = orderRepository.save(orderMapper.toEntity(dto));

        retableTaskService.createRetryableTask(order, RetryableTaskType.SEND_CREATE_DELIVERY_REQUEST);
        retableTaskService.createRetryableTask(order, RetryableTaskType.SEND_CREATE_NOTIFICATION_REQUEST);

        return orderMapper.toDto(order);
    }
}
