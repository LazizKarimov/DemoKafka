package com.example.demokafka.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {

    private UUID uuid;
    private Long customerId;
    private List<Long> productsIds;
    private Integer quantity;
    private String deliveryAddress;
    private String paymentMethod;
    private String orderNotes;

}
