package repository;

import model.Customer;

import java.util.*;
import java.util.stream.Collectors;

public class CustomerRepository implements Repository<Customer> {

    private static CustomerRepository customerRepositoryInstance;

    private final Map<UUID, Customer> customerDatabase;

    public CustomerRepository() {
        this.customerDatabase = new HashMap<>();
    }

    public static synchronized CustomerRepository getInstance() {
        if (Objects.isNull(customerRepositoryInstance)) {
            customerRepositoryInstance = new CustomerRepository();
        }
        return customerRepositoryInstance;
    }

    @Override
    public List<Customer> findAll() {
        return customerDatabase.values()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Customer save(Customer customer) {
        return customerDatabase.put(generateUniqueKey(), customer);
    }

    private UUID generateUniqueKey() {
        return UUID.randomUUID();
    }
}
