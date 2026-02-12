import Components.EventManagementComponent;
import Components.ReportingComponent;
import Components.TicketSalesComponent;
import Entities.Customer;

import Entities.EventStatus;
import Service.EventService;
import Service.SeatAllocationService;
import java.time.OffsetDateTime;
import java.util.UUID;

public class MainDemo {

    public static void main(String[] args) {
        System.out.println("=== Starting Event Ticketing System Demo ===");

        java.sql.Connection connection = edu.aitu.oop3.db.DatabaseConnection.getInstance().getConnection();

        var seatRepository = new edu.aitu.oop3.db.PostgresSeatRepository(connection);
        var ticketRepository = new edu.aitu.oop3.db.PostgresTicketRepository(connection);
        var eventRepository = new edu.aitu.oop3.db.PostgresEventRepository(connection);
        var customerRepository = new edu.aitu.oop3.db.PostgresCustomerRepository(connection);

        var eventService = new EventService(eventRepository);
        var seatAllocationService = new SeatAllocationService(seatRepository);

        var eventManagement = new EventManagementComponent(eventService, seatRepository);
        var ticketSales = new TicketSalesComponent(seatAllocationService, ticketRepository, eventRepository,
                customerRepository);
        var reporting = new ReportingComponent(ticketRepository);

        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "John", "Doe",
                "john" + System.currentTimeMillis() + "@example.com");
        customerRepository.save(customer);
        System.out.println("Created Demo Customer: John Doe (" + customerId + ")");

        System.out.println("\n--- Flow A: Create Event ---");
        UUID eventId = UUID.randomUUID();
        eventManagement.createEvent(eventId, "JavaOne Conference 2026", OffsetDateTime.now().plusDays(30), 2, 2);
        eventManagement.publishEvent(eventId);

        System.out.println("Initial Seating Layout:");
        seatAllocationService.viewSeatingLayout(eventId);

        System.out.println("\n--- Flow B: Buy Ticket ---");
        var availableSeats = seatAllocationService.getAvailableSeats(eventId);
        if (availableSeats.isEmpty()) {
            System.out.println("No seats available!");
            return;
        }

        UUID seatId = availableSeats.get(0).getSeatId();
        System.out.println(
                "Customer selecting seat: " + availableSeats.get(0).getRow() + availableSeats.get(0).getNumber());

        try {
            ticketSales.buyTicket(eventId, seatId, customerId);
        } catch (Exception e) {
            System.out.println("Error buying ticket: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Seating layout after purchase:");
        seatAllocationService.viewSeatingLayout(eventId);

        System.out.println("\n--- Reporting ---");
        reporting.generateSalesReport();

        System.out.println("=== Demo Completed ===");
    }
}
