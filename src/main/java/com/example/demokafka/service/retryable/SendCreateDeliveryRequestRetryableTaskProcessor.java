package com.example.demokafka.service.retryable;

import com.example.demokafka.entity.Order;
import com.example.demokafka.entity.RetryableTask;
import com.example.demokafka.mapper.RetryableTaskMapper;
import com.example.demokafka.service.DeliveryService;
import com.example.demokafka.service.RetryableTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Процессор для обработки требующих повторной обработки задач, связанных с отправкой запросов на создание доставки в сервис Доставки.
 * Отвечает за обработку задач, которые ранее не были успешно выполнены и требуют повторной попытки
 * для успешного создания заказов на доставку в сервисе Доставки.
 */
@Slf4j
@Service
public class SendCreateDeliveryRequestRetryableTaskProcessor extends AbstractRetryableTaskProcessor {
    private final DeliveryService deliveryService;
    private final RetryableTaskMapper retryableTaskMapper;

    public SendCreateDeliveryRequestRetryableTaskProcessor(RetryableTaskService retryableTaskService,
                                                           DeliveryService deliveryService,
                                                           RetryableTaskMapper retryableTaskMapper) {
        super(retryableTaskService);
        this.deliveryService = deliveryService;
        this.retryableTaskMapper = retryableTaskMapper;
    }

    @Override
    protected boolean processRetryableTask(RetryableTask retryableTask) {
        var order = retryableTaskMapper.convertJsonToOrder(retryableTask.getPayload());
        return deliveryService.processDelivery(order.getId(), order.getDeliveryAddress());
    }
}
