package com.example.demokafka.mapper;

import com.example.demokafka.entity.Order;
import com.example.demokafka.entity.RetryableTask;
import com.example.demokafka.enums.RetryableTaskType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Mapper(componentModel = "spring")
public interface RetryableTaskMapper {

    @Mapping(source = "order", target = "paylod", qualifiedByName = "convertObjectToJson")
    RetryableTask toRetryableTask(Order order, RetryableTaskType type);

//    @Mapping(source = "order", target = "payload", qualifiedByName = "convertObjectToJson")
//    RetryableTask toSendCreateNotificationRequestRetryableTask(Order order);

    @Named("convertObjectToJson")
    default String convertObjectToJson(Order order) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(order);
    }

    @Named("convertObjectToJson")
    default Order convertJsonToOrder(String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(json, Order.class);
    }
}
