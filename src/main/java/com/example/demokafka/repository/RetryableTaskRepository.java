package com.example.demokafka.repository;

import com.example.demokafka.entity.RetryableTask;
import com.example.demokafka.enums.RetryableTaskStatus;
import com.example.demokafka.enums.RetryableTaskType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface RetryableTaskRepository extends CrudRepository<RetryableTask, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RetryableTask r WHERE r.type= :type" +
            " AND r.retryTime<= :retryTime " +
            " AND r.status= :status " +
            " ORDER BY r.retryTime ASC"
    )
    List<RetryableTask> findRetryableTaskForProcessing(RetryableTaskType type, Instant retryTime,
                                                       RetryableTaskStatus status, Pageable pageable);

    @Query("UPDATE RetryableTask r SET r.status= :status where r.id in :retryableTasks")
    void updateRetryableTasks(List<RetryableTask> retryableTasks, RetryableTaskStatus status);
}
