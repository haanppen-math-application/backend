package com.hpmath.common.outbox;

import com.hpmath.common.event.Event;
import com.hpmath.common.event.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "outbox")
@NoArgsConstructor
@Getter
@ToString
class Outbox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @Column(name = "event_payload", columnDefinition = "TEXT")
    private String payload;

    public static Outbox of(Event event, LocalDateTime createdAt) {
        Outbox outbox = new Outbox();
        outbox.createdAt = createdAt;
        outbox.eventType = event.getType();
        outbox.payload = event.toJson();
        return outbox;
    }
}
