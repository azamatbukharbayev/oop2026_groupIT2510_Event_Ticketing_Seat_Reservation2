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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private UUID eventId;
        private String name;
        private OffsetDateTime startsAt;
        private EventStatus status;

        public Builder eventId(UUID eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder startsAt(OffsetDateTime startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public Builder status(EventStatus status) {
            this.status = status;
            return this;
        }

        public Event build() {

            if (eventId == null)
                throw new IllegalStateException("eventId required");

            if (name == null || name.isBlank())
                throw new IllegalStateException("name required");

            if (startsAt == null)
                throw new IllegalStateException("startsAt required");

            if (status == null)
                status = EventStatus.SCHEDULED;

            Event event = new Event();
            event.eventId = this.eventId;
            event.name = this.name;
            event.startsAt = this.startsAt;
            event.status = this.status;

            return event;
        }
    }
}