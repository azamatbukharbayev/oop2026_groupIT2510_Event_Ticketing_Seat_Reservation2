package Repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import Entities.Event;

public interface Repository<T, ID> {
    Optional<T> findById(ID id);

    List<T> findAll();

    void save(T entity);

    void update(T entity);
}
