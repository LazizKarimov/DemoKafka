package com.example.demokafka.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "notification-service", url = "${notification.service.url}")
public interface NotificationServiceClient {

    @PostMapping("/createNotification")
    NotificationDto createNotification(CreateNotificationDto createNotificationDto);
}
