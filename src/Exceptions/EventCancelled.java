package Exceptions;

import java.util.UUID;

public class EventCancelled extends RuntimeException {
    public EventCancelled(UUID eventId) {
        super("Event is cancelled: "+ eventId);
    }
}
