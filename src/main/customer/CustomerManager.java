package main.customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerManager {
    private List<Customer> customers;

    public CustomerManager() {
        customers = new ArrayList<>();
    }

    // Add a customer to the list
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    // Remove a customer from the list by customer ID
    public boolean removeCustomer(String customerID) {
        return customers.removeIf(customer -> customer.getCustomerId().equals(customerID));
    }

    // Find a customer by customer ID
    public Customer findCustomerByID(String customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerID)) {
                return customer;
            }
        }
        return null;  // Return null if customer is not found
    }

    // Update customer information
    public boolean updateCustomerDetails(String customerID, String name, String email, String phone) {
        Customer customer = findCustomerByID(customerID);
        if (customer != null) {
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhone(phone);
            return true;
        }
        return false;
    }

    // Get all customers (could be useful for reports)
    public List<Customer> getAllCustomers() {
        return customers;
    }

    // Check if a customer exists
    public boolean customerExists(String customerID) {
        return findCustomerByID(customerID) != null;
    }
}
