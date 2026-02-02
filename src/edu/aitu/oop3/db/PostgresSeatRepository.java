package edu.aitu.oop3.db;

import Entities.Seat;
import Repositories.SeatRepository;

import java.sql.*;
import java.time.OffsetDateTime;
import java.util.*;

public class PostgresSeatRepository implements SeatRepository {
    private final Connection connection;

    public PostgresSeatRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Seat> findById(UUID seatId) {
        String sql = "SELECT * FROM seats WHERE seat_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, seatId);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return Optional.empty();

            return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Seat> findByEventId(UUID eventId) {
        String sql = "SELECT * FROM seats WHERE event_id = ?";
        List<Seat> seats = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, eventId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                seats.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return seats;
    }

    @Override
    public void save(Seat seat) {
        String sql = """
            INSERT INTO seats (seat_id, event_id, row, number, is_booked)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, seat.getSeatId());
            ps.setObject(2, seat.getEventId());
            ps.setString(3, seat.getRow());
            ps.setInt(4, seat.getNumber());
            ps.setBoolean(5, seat.isBooked());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Seat seat) {
        String sql = "UPDATE seats SET is_booked = ? WHERE seat_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, seat.isBooked());
            ps.setObject(2, seat.getSeatId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Seat mapRow(ResultSet rs) throws SQLException {
        return new Seat(
                UUID.fromString(rs.getString("seat_id")),
                UUID.fromString(rs.getString("event_id")),
                rs.getString("row"),
                rs.getInt("number"),
                rs.getBoolean("is_booked")
        );
    }
}
