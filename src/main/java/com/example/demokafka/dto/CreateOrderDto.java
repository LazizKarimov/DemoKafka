package com.example.demokafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateOrderDto {
    private Long customerId;

    private String deliveryAddress;

    private String paymentMethod;

    private String orderNotes;

    private String customerEmail;
}
