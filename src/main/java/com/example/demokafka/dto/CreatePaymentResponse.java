package com.example.demokafka.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePaymentResponse {
    private String status;    // Статус платежа ("SUCCESS", "FAILED", "PENDING")
    private String message;   // Сообщение (например, причина ошибки)
}
