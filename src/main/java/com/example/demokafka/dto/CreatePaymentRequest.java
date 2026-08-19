package com.example.demokafka.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreatePaymentRequest {
    private BigDecimal amount;    // Сумма платежа
    private String currency;      // Валюта (например, USD, EUR)
    private String paymentMethod; // Метод оплаты (CARD, PAYPAL, CASH)
}