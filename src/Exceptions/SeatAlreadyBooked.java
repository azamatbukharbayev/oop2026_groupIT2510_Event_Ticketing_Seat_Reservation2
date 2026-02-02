package Exceptions;

import java.util.UUID;

public class SeatAlreadyBooked extends RuntimeException {
    public SeatAlreadyBooked(UUID seatId) {
        super("Seat is already booked: " + seatId);
    }
}
