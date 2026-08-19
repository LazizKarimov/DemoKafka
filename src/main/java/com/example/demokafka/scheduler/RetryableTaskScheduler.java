package com.example.demokafka.scheduler;

import com.example.demokafka.entity.RetryableTask;
import com.example.demokafka.enums.RetryableTaskType;
import com.example.demokafka.mapper.RetryableTaskMapper;
import com.example.demokafka.service.DeliveryService;
import com.example.demokafka.service.NotificationService;
import com.example.demokafka.service.RetryableTaskService;
import com.example.demokafka.service.retryable.RetryableTaskProcessor;
import com.example.demokafka.service.retryable.SendCreateDeliveryRequestRetryableTaskProcessor;
import com.example.demokafka.service.retryable.SendCreateNotificationRequestRetryableTaskProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Планировщик для выполнения задач, требующих повторной обработки
 */
@Slf4j
@Component
public class RetryableTaskScheduler {
    private final RetryableTaskService retryableTaskService;
    private final Map<RetryableTaskType, RetryableTaskProcessor> taskProcessors;

    public RetryableTaskScheduler(RetryableTaskService retryableTaskService, RetryableTaskMapper retryableTaskMapper, DeliveryService deliveryService, NotificationService notificationService) {
        this.retryableTaskService = retryableTaskService;
        this.taskProcessors = Map.of(
                RetryableTaskType.SEND_CREATE_DELIVERY_REQUEST, new SendCreateDeliveryRequestRetryableTaskProcessor(retryableTaskService,
                        deliveryService, retryableTaskMapper
                ),
                RetryableTaskType.SEND_CREATE_NOTIFICATION_REQUEST, new SendCreateNotificationRequestRetryableTaskProcessor(retryableTaskService,
                        notificationService, retryableTaskMapper));
    }

    @Scheduled(fixedRate = 5000)
    public void executeRetryableTasks() {
        log.info("Starting retryable task processors");
        for (Map.Entry<RetryableTaskType, RetryableTaskProcessor> entry : taskProcessors.entrySet()) {
            var taskType = entry.getKey();
            var taskProcessor = entry.getValue();
            log.info("Processing tasks of type: {}", taskType);

            var retryableTasks = retryableTaskService.getRetryableTasksForProcessing(taskType);

            if(retryableTasks.isEmpty()) {
                log.info("No retryable tasks found for type: {}", taskType);
                continue;
            }
            taskProcessor.processRetryableTasks(retryableTasks);
        }
        log.info("Completed all retryable task processing");
    }
}
