package com.ostroverhov.kafka.consumer.service;

import com.ostroverhov.kafka.consumer.model.ProcessedEventEntity;
import com.ostroverhov.kafka.consumer.repository.ProcessedEventRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class ProcessedEventService {

    private final ProcessedEventRepository repo;

    public Page<ProcessedEventEntity> getProcessedEvents(
            Instant from,
            Instant to,
            Pageable pageable
    ) {
        if (from != null && to != null) {
            return repo.findByProcessedAtBetween(from, to, pageable);
        }
        if (from != null) {
            return repo.findByProcessedAtGreaterThanEqual(from, pageable);
        }
        if (to != null) {
            return repo.findByProcessedAtLessThanEqual(to, pageable);
        }
        return repo.findAll(pageable);
    }
}
