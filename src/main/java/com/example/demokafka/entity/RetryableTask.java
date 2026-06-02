package com.example.demokafka.entity;

import com.example.demokafka.enums.RetryableTaskStatus;
import com.example.demokafka.enums.RetryableTaskType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Getter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.Instant;

@Entity
public class RetryableTask extends BaseEntity{

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String payload;

    @Convert(converter = RetryableTaskTypeConverter.class)
    private RetryableTaskType type;

    @Convert(converter = RetryableTaskStatusConverter.class)
    private RetryableTaskStatus status;


    private Instant retryTime;
}
