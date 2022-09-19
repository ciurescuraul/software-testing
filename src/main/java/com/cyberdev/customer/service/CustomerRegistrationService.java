package com.cyberdev.customer.service;

import com.cyberdev.customer.customer.Customer;
import com.cyberdev.customer.customer.request.CustomerRegistrationRequest;
import com.cyberdev.customer.exception.CustomerPhoneNumberTakenException;
import com.cyberdev.customer.repository.CustomerRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerRegistrationService {

    private final CustomerRegistrationRepository customerRepository;

    @Autowired
    public CustomerRegistrationService(CustomerRegistrationRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void registerNewCustomer(CustomerRegistrationRequest request) {
        String phoneNumber = request.getCustomer().getPhoneNumber();

        Optional<Customer> optCustomer = customerRepository.selectCustomerByPhoneNumber(phoneNumber);

        if (optCustomer.isPresent()) {
            Customer customer = optCustomer.get();
            if (customer.getName().equals(request.getCustomer().getName())) {
                return;
            }
            throw new CustomerPhoneNumberTakenException(String.format("phone number [%s] is taken", phoneNumber));
        }

        if (request.getCustomer().getId() == null) {
            request.getCustomer().setId(UUID.randomUUID());
        }

        customerRepository.save(request.getCustomer());
    }
}
