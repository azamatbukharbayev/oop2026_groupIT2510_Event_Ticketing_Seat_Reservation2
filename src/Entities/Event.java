package Entities;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Event {

    private UUID eventId;
    private String name;
    private OffsetDateTime startsAt;
    private EventStatus status;

    public Event(UUID eventId, String name, OffsetDateTime startsAt, EventStatus status) {
        this.eventId = eventId;
        this.name = name;
        this.startsAt = startsAt;
        this.status = status;
    }

    public UUID getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public OffsetDateTime getStartsAt() {
        return startsAt;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }
}