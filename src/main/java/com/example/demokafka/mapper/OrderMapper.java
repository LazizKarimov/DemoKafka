package com.example.demokafka.mapper;

import com.example.demokafka.dto.CreateOrderDto;
import com.example.demokafka.dto.OrderDto;
import com.example.demokafka.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(CreateOrderDto dto);

    OrderDto toDto(Order order);

}
