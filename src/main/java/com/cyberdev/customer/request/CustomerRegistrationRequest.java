package com.cyberdev.customer.request;

import com.cyberdev.customer.model.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerRegistrationRequest {

    private final Customer customer;

    public CustomerRegistrationRequest(@JsonProperty(value = "customer") Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "CustomerRegistrationRequest{" +
                "customer=" + customer +
                '}';
    }
}
