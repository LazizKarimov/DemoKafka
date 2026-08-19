package com.example.demokafka.entity;

import com.example.demokafka.enums.RetryableTaskStatus;
import com.example.demokafka.enums.RetryableTaskType;
import com.example.demokafka.util.RetryableTaskStatusConverter;
import com.example.demokafka.util.RetryableTaskTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.Instant;

/**
 * Задача, требующая повторного выполнения
 */
@Entity
@Getter
@Setter
public class RetryableTask extends BaseEntity {
    /**
     * Тело задачи
     */
    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String payload;
    /**
     * Тип задачи
     */
    @Convert(converter = RetryableTaskTypeConverter.class)
    private RetryableTaskType type;
    /**
     * Тип задачи
     */
    @Convert(converter = RetryableTaskStatusConverter.class)
    private RetryableTaskStatus status;
    /**
     * Время повторного выполнения
     */
    private Instant retryTime;
}
