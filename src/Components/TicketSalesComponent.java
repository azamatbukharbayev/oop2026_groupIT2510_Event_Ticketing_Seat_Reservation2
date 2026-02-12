package Components;

import Entities.Customer;
import Entities.Event;
import Entities.EventStatus;
import Entities.Ticket;
import Entities.TicketType;
import Exceptions.EventCancelled;
import Repositories.Repository;
import Repositories.TicketRepository;
import Service.SeatManager;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TicketSalesComponent {

    private final SeatManager seatManager;
    private final TicketRepository ticketRepository;
    private final Repository<Event, UUID> eventRepository;
    private final Repository<Customer, UUID> customerRepository;

    public TicketSalesComponent(SeatManager seatManager,
                                TicketRepository ticketRepository,
                                Repository<Event, UUID> eventRepository,
                                Repository<Customer, UUID> customerRepository) {
        this.seatManager = seatManager;
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.customerRepository = customerRepository;
    }

    public void buyTicket(UUID eventId, UUID seatId, UUID customerId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getStatus() == EventStatus.CANCELLED) {
            throw new EventCancelled(eventId);
        }

        // Delegate seat reservation to SeatManager (Architectural Rule)
        seatManager.reserveSeat(seatId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Ticket ticket = Ticket.create(
                TicketType.STANDARD,
                UUID.randomUUID(),
                eventId,
                seatId,
                customerId,
                UUID.randomUUID().toString().substring(0, 18),
                OffsetDateTime.now());

        ticketRepository.save(ticket);
        System.out.println(
                "Ticket bought by customer: " + customer.getFirstName() + ". Ticket code: "
                        + ticket.getCode());
    }
}
