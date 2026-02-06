package Repositories;

import Entities.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends Repository<Ticket, UUID> {

    List<Ticket> findByCustomerId(UUID customerId);

    List<Ticket> findBySeatId(UUID seatId);
}
