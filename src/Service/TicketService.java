package Service;

import Entities.Customer;
import Entities.Event;
import Entities.EventStatus;
import Entities.Seat;
import Entities.Ticket;
import Exceptions.EventCancelled;
import Exceptions.SeatAlreadyBooked;
import Repositories.Repository;
import Repositories.SeatRepository;
import Repositories.TicketRepository;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TicketService {

    private final Repository<Event, UUID> eventRepository;
    private final SeatRepository seatRepository;
    private final Repository<Customer, UUID> customerRepository;
    private final TicketRepository ticketRepository;

    public TicketService(EventRepository eventRepository,
            SeatRepository seatRepository,
            CustomerRepository customerRepository,
            TicketRepository ticketRepository) {
        this.eventRepository = eventRepository;
        this.seatRepository = seatRepository;
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
    }

    public void buyTicket(UUID eventId, UUID seatId, UUID customerId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getStatus() == EventStatus.CANCELLED) {
            throw new EventCancelled(eventId);
        }

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        if (seat.isBooked()) {
            throw new SeatAlreadyBooked(seatId);
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        seat.setBooked(true);
        seatRepository.update(seat);

        Ticket ticket = new Ticket(
                UUID.randomUUID(),
                eventId,
                seatId,
                customerId,
                UUID.randomUUID().toString(),
                OffsetDateTime.now());

        ticketRepository.save(ticket);
        System.out.println(
                "Ticket bought by customer: " + customer.getFirstName() + ". Ticket code: " + ticket.getCode());
    }
}
