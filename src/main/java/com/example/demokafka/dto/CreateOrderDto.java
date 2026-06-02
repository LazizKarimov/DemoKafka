package com.example.demokafka.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {

    private Long customerId;
    private List<Long> productsIds;
    private String deliveryAddress;
    private String paymentMethod;
    private String orderNotes;
}
