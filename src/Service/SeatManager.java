package Service;

import Entities.Seat;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface SeatManager {
    void reserveSeat(UUID seatId);
    List<Seat> getAvailableSeats(UUID eventId);
}
