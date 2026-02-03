package edu.aitu.oop3.db;

import edu.aitu.oop3.db.DatabaseConnection;
import Entities.Event;
import Entities.EventStatus;
import Repositories.EventRepository;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.*;

public class PostgresEventRepository implements Repository<Event, UUID> {
    private final Connection connection;

    public PostgresEventRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Event> findById(UUID eventId) {
        String sql = "SELECT * FROM events WHERE event_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, eventId);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return Optional.empty();

            return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> findAll() {
        String sql = "SELECT * FROM events";
        List<Event> events = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                events.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return events;
    }

    @Override
    public void save(Event event) {
        String sql = """
            INSERT INTO events (event_id, name, starts_at, status)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, event.getEventId());
            ps.setString(2, event.getName());
            ps.setObject(3, event.getStartsAt());
            ps.setString(4, event.getStatus().name());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Event event) {
        String sql = """
            UPDATE events SET name = ?, starts_at = ?, status = ?
            WHERE event_id = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, event.getName());
            ps.setObject(2, event.getStartsAt());
            ps.setString(3, event.getStatus().name());
            ps.setObject(4, event.getEventId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Event mapRow(ResultSet rs) throws SQLException {
        return new Event(
                UUID.fromString(rs.getString("event_id")),
                rs.getString("name"),
                rs.getObject("starts_at", OffsetDateTime.class),
                EventStatus.valueOf(rs.getString("status"))
        );
    }
}
