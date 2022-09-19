package com.amigoscode.testing.service;

import com.amigoscode.testing.customer.request.CustomerRegistrationRequest;
import com.amigoscode.testing.repository.CustomerRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerRegistrationService {

    private final CustomerRegistrationRepository customerRepository;

    @Autowired
    public CustomerRegistrationService(CustomerRegistrationRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void registerNewCustomer(CustomerRegistrationRequest request) {

    }
}
