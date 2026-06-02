package com.example.demokafka.enums;

import com.example.demokafka.entity.RetryableTask;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RetryableTaskType {

    SEND_CREATE_NOTIFICATION_REQUEST("SEND_CREATE_NOTIFICATION_REQUEST"),

    SEND_CREATE_DELIVERY_REQUEST("SEND_CREATE_DELIVERY_REQUEST");

    private String value;

    public static RetryableTaskType fromValue(String value) {
        for (RetryableTaskType status : RetryableTaskType.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown type: " + value);
    }
}
