package Service;

import Entities.Event;
import Entities.EventStatus;
import Repositories.Repository;
import java.time.OffsetDateTime;
import java.util.UUID;

public class EventService {
    private final Repository<Event, UUID> eventRepository;

    public EventService(Repository<Event, UUID> eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void createEvent(UUID eventId, String name, OffsetDateTime startsAt, EventStatus status) {
        Event event = Event.builder()
                .eventId(eventId)
                .name(name)
                .startsAt(startsAt)
                .status(status)
                .build();
        eventRepository.save(event);
        System.out.println("Event created: " + name + " (" + eventId + ")");
    }
}