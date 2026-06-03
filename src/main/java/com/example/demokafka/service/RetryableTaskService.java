package com.example.demokafka.service;

import com.example.demokafka.entity.Order;
import com.example.demokafka.entity.RetryableTask;
import com.example.demokafka.enums.RetryableTaskStatus;
import com.example.demokafka.enums.RetryableTaskType;
import com.example.demokafka.mapper.RetryableTaskMapper;
import com.example.demokafka.repository.RetryableTaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RetryableTaskService {

    private final RetryableTaskRepository retryableTaskRepository;
    private final RetryableTaskMapper retryableTaskMapper;
    @Value("${retryabletask.limit}")
    private Integer limit;
    @Value("${retryabletask.timeoutInSeconds}")
    private Integer timeoutInSeconds;

    @Transactional
    public RetryableTask createRetryableTask(Order order, RetryableTaskType type) {
        RetryableTask retryableTask = retryableTaskMapper.toRetryableTask(order, type);
        return retryableTaskRepository.save(retryableTask);
    }

    @Transactional
    public List<RetryableTask> getRetryableTasksForProcessing(RetryableTaskType type) {
        var currentTime = Instant.now();
        Pageable pageable = (Pageable) PageRequest.of(0, limit);
        List<RetryableTask> retryableTasks = retryableTaskRepository.findRetryableTaskForProcessing(
                type,
                Instant.now(),
                RetryableTaskStatus.IN_PROGRESS,
                pageable
        );

        for (RetryableTask retryableTask : retryableTasks) {
            retryableTask.setRetryaTime(currentTime.plus(Duration.ofSeconds(timeoutInSeconds)));
        }
        return retryableTasks;
    }


    @Transactional
    public void markRetryableTasksAsCompleted(List<RetryableTask> retryableTasks) {
        retryableTaskRepository.updateRetryableTasks(retryableTasks, RetryableTaskStatus.SUCCESS);
    }
}
