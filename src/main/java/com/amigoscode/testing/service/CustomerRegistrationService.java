package com.amigoscode.testing.service;

import com.amigoscode.testing.customer.Customer;
import com.amigoscode.testing.customer.request.CustomerRegistrationRequest;
import com.amigoscode.testing.exception.PhoneNumberTakenException;
import com.amigoscode.testing.repository.CustomerRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            throw new PhoneNumberTakenException(String.format("phone number [%s] is taken", phoneNumber));
        }

        customerRepository.save(request.getCustomer());
    }
}
