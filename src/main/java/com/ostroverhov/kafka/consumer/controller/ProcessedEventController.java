package com.ostroverhov.kafka.consumer.controller;

import com.ostroverhov.kafka.consumer.model.ProcessedEventEntity;
import com.ostroverhov.kafka.consumer.service.ProcessedEventService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/processed-events")
@AllArgsConstructor
public class ProcessedEventController {

    private final ProcessedEventService service;

    /**
     * Пример:
     * GET /processed-events/search?from=2026-01-19T00:00:00Z&to=2026-01-20T00:00:00Z&page=0&size=20&sort=processedAt,desc
     */
    @GetMapping("/search")
    public List<ProcessedEventEntity> search(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant from,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant to,

            Pageable pageable
    ) {
        return service.getProcessedEvents(from, to, pageable).getContent();
    }
}
