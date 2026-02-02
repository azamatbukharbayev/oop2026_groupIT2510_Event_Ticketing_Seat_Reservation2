package Service;

import Entities.Customer;
import Repositories.CustomerRepository;
import java.util.UUID;

public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(UUID customerId, String firstName, String lastName, String email) {
        Customer customer = new Customer(customerId, firstName, lastName, email);
        customerRepository.save(customer);
        System.out.println("Customer created: " + firstName + " " + lastName);
        return customer;
    }
}
