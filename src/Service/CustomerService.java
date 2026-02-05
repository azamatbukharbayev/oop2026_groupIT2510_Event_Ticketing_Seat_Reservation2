package Service;

import Entities.Customer;
import Repositories.Repository;
import java.util.UUID;

public class CustomerService {
    private final Repository<Customer, UUID> customerRepository;

    public CustomerService(Repository<Customer, UUID> customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(UUID customerId, String firstName, String lastName, String email) {
        Customer customer = new Customer(customerId, firstName, lastName, email);
        customerRepository.save(customer);
        System.out.println("Customer created: " + firstName + " " + lastName);
        return customer;
    }
}
