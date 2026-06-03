package com.example.demokafka.service;

import com.example.demokafka.client.NotificationServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationServiceClient notificationServiceClient;


    public boolean sendNotification(UUID orderId, String userEmail) {
        boolean result = false;

        CreateNotificationDto notificationRequest = buildRequest(orderId, userEmail);
        try {
            NotificationDto notificationRequest = notificationServiceClient.createNotification(notificationRequest);

            if ("success".equals(response.getStatus())) {
                log.info("Notification successfuly created with ID: {}", response.getNotificationId());
                result = true;
            } else {
                log.error("Failed to create notification. Status: {}", response.getStatus());
            }

        } catch (Exception e) {
            log.error("Error occurred while creating delivery ", e);
        }
        return result;
    }

    private CreateNotificationDto buildRequest(UUID orderId, String userEmail) {
        CreateNotificationDto createNotificationDto = new CreateNotificationDto();

        createNotificationDto.setOrderId(orderId);
        createNotificationDto.setDeliveryAddress(deliveryAddress);

        return createNotificationDto;
    }
}
