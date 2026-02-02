package Entities;

import java.util.UUID;

public class Seat {

    private UUID seatId;
    private UUID eventId;
    private String row;
    private int number;
    private boolean booked;

    public Seat(UUID seatId, UUID eventId, String row, int number, boolean booked) {
        this.seatId = seatId;
        this.eventId = eventId;
        this.row = row;
        this.number = number;
        this.booked = booked;
    }

    public UUID getSeatId() {
        return seatId;
    }

    public UUID getEventId() {
        return eventId;
    }

    public String getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }
}
