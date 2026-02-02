package Repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import Entities.Event;

public interface EventRepository {
    Optional<Event> findById(UUID eventId);

    List<Event> findAll();

    void save(Event event);

    void update(Event event);
}
