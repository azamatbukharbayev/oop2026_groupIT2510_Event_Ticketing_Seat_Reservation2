package Service;

import Entities.Seat;
import Exceptions.SeatAlreadyBooked;
import Repositories.SeatRepository;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SeatAllocationService implements SeatManager {
    private final SeatRepository seatRepository;

    public SeatAllocationService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public void reserveSeat(UUID seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found: " + seatId));

        if (seat.isBooked()) {
            throw new SeatAlreadyBooked(seatId);
        }

        seat.setBooked(true);
        seatRepository.update(seat);
        System.out.println("Seat booked: " + seatId);
    }

    @Override
    public List<Seat> getAvailableSeats(UUID eventId) {
        return seatRepository.findByEventId(eventId)
                .stream()
                .filter(seat -> !seat.isBooked())
                .collect(Collectors.toList());
    }

    public List<Seat> searchSeats(UUID eventId, Predicate<Seat> filter) {
        return seatRepository.findByEventId(eventId)
                .stream()
                .filter(filter)
                .sorted(Comparator
                        .comparing(Seat::getRow)
                        .thenComparingInt(Seat::getNumber))
                .collect(Collectors.toList());
    }

    public void viewSeatingLayout(UUID eventId) {
        List<Seat> seats = seatRepository.findByEventId(eventId);
        if (seats.isEmpty()) {
            System.out.println("No seats found for event: " + eventId);
            return;
        }

        System.out.println("Seating Layout for Event(" + eventId + "):");

        seats.stream()
                .sorted(Comparator
                        .comparing(Seat::getRow)
                        .thenComparingInt(Seat::getNumber))
                .forEach(seat -> {
                    String status = seat.isBooked() ? "[X]" : "[O]";
                    System.out.println(seat.getRow() + seat.getNumber() + " " + status);
                });
    }
}