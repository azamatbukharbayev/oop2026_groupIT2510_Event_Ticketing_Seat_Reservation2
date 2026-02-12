package Components;

import Entities.Event;
import Entities.EventStatus;
import Entities.Seat;
import Repositories.Repository;
import Repositories.SeatRepository;
import Service.EventService;
import java.time.OffsetDateTime;
import java.util.UUID;

public class EventManagementComponent {

    private final EventService eventService;
    private final SeatRepository seatRepository;

    public EventManagementComponent(EventService eventService, SeatRepository seatRepository) {
        this.eventService = eventService;
        this.seatRepository = seatRepository;
    }

    public void createEvent(UUID eventId, String name, OffsetDateTime startsAt, int rows, int seatsPerRow) {
        eventService.createEvent(eventId, name, startsAt, EventStatus.ACTIVE);

        initializeSeats(eventId, rows, seatsPerRow);
    }

    public void updateEvent(UUID eventId, String newName) {
        System.out.println("Updating event " + eventId + " to name: " + newName);
    }

    public void publishEvent(UUID eventId) {
        System.out.println("Publishing event: " + eventId);
    }

    private void initializeSeats(UUID eventId, int rows, int seatsPerRow) {
        char currentRow = 'A';
        for (int i = 0; i < rows; i++) {
            for (int j = 1; j <= seatsPerRow; j++) {
                Seat seat = new Seat(
                        UUID.randomUUID(),
                        eventId,
                        String.valueOf(currentRow),
                        j,
                        false);
                seatRepository.save(seat);
            }
            currentRow++;
        }
        System.out.println("Initialized " + (rows * seatsPerRow) + " seats for event " + eventId);
    }
}
