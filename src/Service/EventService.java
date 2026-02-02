package Service;

import Entities.Event;
import Entities.EventStatus;
import Repositories.EventRepository;
import java.time.OffsetDateTime;
import java.util.UUID;

public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void createEvent(UUID eventId, String name, OffsetDateTime startsAt, EventStatus status) {
        Event event = new Event(eventId, name, startsAt, status);
        eventRepository.save(event);
        System.out.println("Event created: " + name + " (" + eventId + ")");
    }
}