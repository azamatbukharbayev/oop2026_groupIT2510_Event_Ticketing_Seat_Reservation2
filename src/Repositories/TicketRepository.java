package Repositories;

import Entities.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository {
    Optional<Ticket> findById(UUID ticketId);
    List<Ticket> findByCustomerId(UUID customerId);
    List<Ticket> findBySeatId(UUID seatId);
    void save(Ticket ticket);
    void update(Ticket ticket);
}
