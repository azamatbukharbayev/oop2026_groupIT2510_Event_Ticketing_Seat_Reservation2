package Entities;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Ticket {

    private UUID ticketId;
    private UUID eventId;
    private UUID seatId;
    private UUID customerId;
    private String code;
    private OffsetDateTime purchasedAt;

    public Ticket(UUID ticketId, UUID eventId, UUID seatId, UUID customerId,
                  String code, OffsetDateTime purchasedAt) {
        this.ticketId = ticketId;
        this.eventId = eventId;
        this.seatId = seatId;
        this.customerId = customerId;
        this.code = code;
        this.purchasedAt = purchasedAt;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public UUID getEventId() {
        return eventId;
    }

    public UUID getSeatId() {
        return seatId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getCode() {
        return code;
    }

    public OffsetDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public static Ticket create(
            TicketType type,
            UUID ticketId,
            UUID eventId,
            UUID seatId,
            UUID customerId,
            String code,
            OffsetDateTime purchaseTime) {

        return switch (type) {
            case VIP -> new VIPTicket(ticketId, eventId, seatId, customerId, code, purchaseTime);
            case STUDENT -> new StudentTicket(ticketId, eventId, seatId, customerId, code, purchaseTime);
            default -> new StandardTicket(ticketId, eventId, seatId, customerId, code, purchaseTime);
        };
    }
}

public static class StandardTicket extends Ticket {
    public StandardTicket(UUID ticketId, UUID eventId, UUID seatId,
                          UUID customerId, String code, OffsetDateTime purchaseTime) {
        super(ticketId, eventId, seatId, customerId, code, purchaseTime);
    }
}

public static class VIPTicket extends Ticket {
    public VIPTicket(UUID ticketId, UUID eventId, UUID seatId,
                     UUID customerId, String code, OffsetDateTime purchaseTime) {
        super(ticketId, eventId, seatId, customerId, code, purchaseTime);
    }
}

public static class StudentTicket extends Ticket {
    public StudentTicket(UUID ticketId, UUID eventId, UUID seatId,
                         UUID customerId, String code, OffsetDateTime purchaseTime) {
        super(ticketId, eventId, seatId, customerId, code, purchaseTime);
    }
}


public enum TicketType {
    STANDARD,
    VIP,
    STUDENT
}