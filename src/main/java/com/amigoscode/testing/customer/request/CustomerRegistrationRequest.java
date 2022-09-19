package com.amigoscode.testing.customer.request;

import com.amigoscode.testing.customer.CustomerDAO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerRegistrationRequest {

    private final CustomerDAO customerDAO;

    public CustomerRegistrationRequest(@JsonProperty(value = "customerDAO") CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public CustomerDAO getCustomer() {
        return customerDAO;
    }

    @Override
    public String toString() {
        return "CustomerRegistrationRequest{" +
                "customerDAO=" + customerDAO +
                '}';
    }
}
