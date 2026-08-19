package com.example.demokafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderDto {
    private UUID uuid;

    private Long customerId; // ID клиента, который делает заказ

    private List<Long> productIds; // Список ID товаров, которые заказывает клиент

    private Integer quantity; // Количество товаров

    private String deliveryAddress; // Адрес доставки

    private String paymentMethod; // Способ оплаты (например, кредитная карта, PayPal)

    private String orderNotes;
}
