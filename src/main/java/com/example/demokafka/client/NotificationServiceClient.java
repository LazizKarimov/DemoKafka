package com.example.demokafka.client;

import com.example.demokafka.dto.CreateNotificationDto;
import com.example.demokafka.dto.NotificationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "notification-service", url = "${notification.service.url}")
public interface NotificationServiceClient {

    @PostMapping("/createNotification")
    NotificationDto createNotification(CreateNotificationDto createNotificationDto);
}
