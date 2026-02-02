package edu.aitu.oop3.db;

import Entities.Ticket;
import Repositories.TicketRepository;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.*;

public class PostgresTicketRepository implements TicketRepository {
    private final Connection connection;

    public PostgresTicketRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Ticket> findById(UUID ticketId) {
        String sql = "SELECT * FROM tickets WHERE ticket_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, ticketId);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return Optional.empty();

            return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Ticket> findByCode(String code) {
        String sql = "SELECT * FROM tickets WHERE code = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return Optional.empty();

            return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> findByCustomerId(UUID customerId) {
        String sql = "SELECT * FROM tickets WHERE customer_id = ?";
        List<Ticket> tickets = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, customerId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tickets.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tickets;
    }

    @Override
    public List<Ticket> findByEventId(UUID eventId) {
        String sql = "SELECT * FROM tickets WHERE event_id = ?";
        List<Ticket> tickets = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, eventId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tickets.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tickets;
    }

    @Override
    public void save(Ticket ticket) {
        String sql = """
            INSERT INTO tickets (ticket_id, event_id, seat_id, customer_id, code)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, ticket.getTicketId());
            ps.setObject(2, ticket.getEventId());
            ps.setObject(3, ticket.getSeatId());
            ps.setObject(4, ticket.getCustomerId());
            ps.setString(5, ticket.getCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Ticket mapRow(ResultSet rs) throws SQLException {
        return new Ticket(
                UUID.fromString(rs.getString("ticket_id")),
                UUID.fromString(rs.getString("event_id")),
                UUID.fromString(rs.getString("seat_id")),
                UUID.fromString(rs.getString("customer_id")),
                rs.getString("code"),
                rs.getObject("purchased_at", OffsetDateTime.class)
        );
    }
}
