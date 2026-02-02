package Repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import Entities.Customer;

public interface CustomerRepository {
    Optional<Customer> findById(UUID customerId);

    List<Customer> findAll();

    void save(Customer customer);

    void update(Customer customer);
}
