package com.example.demokafka.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_table")
@Getter
@Setter
public class Order extends BaseEntity {
    @NotNull(message = "Customer ID не может быть null")
    @Min(value = 1, message = "Customer ID должен быть больше 0")
    private Long customerId;

    @NotBlank(message = "Delivery Address не может быть пустым")
    @Size(max = 255, message = "Delivery Address не должен превышать 255 символов")
    private String deliveryAddress;

    @NotBlank(message = "Payment Method не может быть пустым")
    private String paymentMethod;

    @Size(max = 500, message = "Order Notes не должен превышать 500 символов")
    private String orderNotes;

    @Email(message = "Некорректный email")
    private String customerEmail;
}
