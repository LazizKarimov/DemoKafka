package com.example.demokafka.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class NotificationDto {
    private UUID notificationId;
    private String status;
}