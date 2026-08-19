package com.example.demokafka.service.retryable;

import com.example.demokafka.entity.RetryableTask;
import com.example.demokafka.mapper.RetryableTaskMapper;
import com.example.demokafka.service.NotificationService;
import com.example.demokafka.service.RetryableTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Процессор для обработки требующих повторной обработки задач, связанных с отправкой запросов на создание нотификации в сервис Уведомлений.
 * Отвечает за обработку задач, которые ранее не были успешно выполнены и требуют повторной попытки
 * для успешного создания заказов на доставку в сервисе Уведомлений.
 */
@Slf4j
@Service
public class SendCreateNotificationRequestRetryableTaskProcessor extends AbstractRetryableTaskProcessor {
    private final NotificationService notificationService;
    private final RetryableTaskMapper retryableTaskMapper;

    public SendCreateNotificationRequestRetryableTaskProcessor(RetryableTaskService retryableTaskService,
                                                               NotificationService notificationService,
                                                               RetryableTaskMapper retryableTaskMapper) {
        super(retryableTaskService);
        this.notificationService = notificationService;
        this.retryableTaskMapper = retryableTaskMapper;
    }

    @Override
    protected boolean processRetryableTask(RetryableTask retryableTask) {
        var order = retryableTaskMapper.convertJsonToOrder(retryableTask.getPayload());
        return notificationService.sendNotification(order.getId(), order.getCustomerEmail());
    }
}
