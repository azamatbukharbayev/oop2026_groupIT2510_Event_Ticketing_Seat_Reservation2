package Repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import Entities.Seat;

public interface SeatRepository extends Repository<Seat, UUID> {

    List<Seat> findByEventId(UUID eventId);
}
