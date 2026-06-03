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

@Slf4j
@Service
@RequiredArgsConstructor
public class SendCreateDeliveryRequestRetryableTaskProcessor implements RetryableTaskProcessor {

    private final DeliveryService deliveryService;
    private final RetryableTaskMapper retryableTaskMapper;
    private final RetryableTaskService retryableTaskService;

    @Override
    public void processRetryableTasks(List<RetryableTask> retryableTasks) {
        List<RetryableTask> successRetryableTask = new ArrayList<>();
        for (RetryableTask retryableTask : retryableTasks) {
            boolean isSuccess = processRetryableTask(retryableTask);
            if (isSuccess){
                successRetryableTask.add(retryableTask);
            }
        }

        retryableTaskService.markRetryableTasksAsCompleted(successRetryableTask);
    }

    private boolean processRetryableTask(RetryableTask retryableTask) {
        Order order = retryableTaskMapper.convertJsonToOrder(retryableTask.getPayload());

        return deliveryService.processDelivery(order.getId(), order.getDeliveryAddress());
    }
}
