package com.ostroverhov.kafka.consumer.repository;

import com.ostroverhov.kafka.consumer.model.ProcessedEventEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, UUID> {

    Optional<ProcessedEventEntity> findByEventId(UUID eventId);

    Page<ProcessedEventEntity> findByProcessedAtBetween(Instant from, Instant to, Pageable pageable);

    Page<ProcessedEventEntity> findByProcessedAtGreaterThanEqual(Instant from, Pageable pageable);

    Page<ProcessedEventEntity> findByProcessedAtLessThanEqual(Instant to, Pageable pageable);
}
