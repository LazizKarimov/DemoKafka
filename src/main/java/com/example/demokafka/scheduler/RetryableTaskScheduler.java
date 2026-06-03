package com.example.demokafka.scheduler;

import com.example.demokafka.entity.RetryableTask;
import com.example.demokafka.enums.RetryableTaskType;
import com.example.demokafka.mapper.RetryableTaskMapper;
import com.example.demokafka.service.DeliveryService;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryableTaskScheduler {

    private final RetryableTaskService retryableTaskService;
    private final RetryableTaskMapper retryableTaskMapper;
    private final DeliveryService deliveryService;

    private final Map<RetryableTaskType, RetryableTaskProcessor> taskProcessors = Map.of(
            RetryableTaskType.SEND_CREATE_DELIVERY_REQUEST, new SendCreateDeliveryRequestRetryableTaskProcessor(),
            RetryableTaskType.SEND_CREATE_NOTIFICATION_REQUEST, new SendCreateNotificationRequestRetryableTaskProcessor()
    );

    @Scheduled(fixedRate = 5000)
    public void executeRetryableTasks() {

        log.info("Starting retryable task processor");
        for(Map.Entry<RetryableTaskType, RetryableTaskProcessor> entry : taskProcessors.entrySet()){
            RetryableTaskType taskType = entry.getKey();
            RetryableTaskProcessor taskProcessor = entry.getValue();

            List<RetryableTask> retryableTasksForProcessing = retryableTaskService.getRetryableTasksForProcessing(taskType);

            taskProcessor.processRetryableTasks(retryableTasksForProcessing);
        }

    }
}
