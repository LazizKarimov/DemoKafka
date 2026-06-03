package com.example.demokafka.service.retryable;

import com.example.demokafka.entity.RetryableTask;

import java.util.List;


public interface RetryableTaskProcessor {
    void processRetryableTasks(List<RetryableTask> retryableTasks);
}
