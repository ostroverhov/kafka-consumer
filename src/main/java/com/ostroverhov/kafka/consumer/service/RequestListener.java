package com.ostroverhov.kafka.consumer.service;

import com.ostroverhov.kafka.consumer.model.EventMessage;
import com.ostroverhov.kafka.consumer.model.ProcessedEventEntity;
import com.ostroverhov.kafka.consumer.repository.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestListener {

    private final ProcessedEventRepository repo;
    private final KafkaTemplate<String, EventMessage> kafkaTemplate;

    @Value("${app.topics.response}")
    private String responseTopic;

    @KafkaListener(topics = "${app.topics.request}")
    @Transactional
    public void onRequest(EventMessage message) {
        // Идемпотентность на случай повторной доставки:
        if (repo.findByEventId(message.eventId()).isPresent()) {
            log.info("Event {} already processed, skipping", message.eventId());
            return;
        }

        repo.save(new ProcessedEventEntity(
                UUID.randomUUID(),
                message.eventId(),
                "DONE",
                Instant.now()
        ));

        kafkaTemplate.send(responseTopic, message.eventId().toString(), message);
        log.info("Processed event {}, saved DONE and sent response to topic={}", message.eventId(), responseTopic);
    }
}
