package com.ostroverhov.kafka.consumer.model;

import java.time.Instant;
import java.util.UUID;

public record EventMessage(
        UUID eventId,
        String payload,
        Instant createdAt
) {
}
