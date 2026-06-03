package com.example.demokafka.service.retryable;

import com.example.demokafka.entity.RetryableTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendCreateNotificationRequestRetryableTaskProcessor implements RetryableTaskProcessor{

    @Override
    public void processRetryableTasks(List<RetryableTask> retryableTasks) {

    }
}
