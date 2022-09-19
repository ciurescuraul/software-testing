package com.cyberdev.customer.service;

import com.cyberdev.customer.customer.Customer;
import com.cyberdev.customer.customer.request.CustomerRegistrationRequest;
import com.cyberdev.customer.exception.CustomerPhoneNumberTakenException;
import com.cyberdev.customer.repository.CustomerRegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

class CustomerRegistrationServiceTest {

    @Mock
    private CustomerRegistrationRepository customerRepository;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    private CustomerRegistrationService underTest;

    private Customer customer;
    private Customer customer2;
    private Customer customer3;
    private CustomerRegistrationRequest request;
    private CustomerRegistrationRequest request2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new CustomerRegistrationService(customerRepository);
        customer = new Customer(UUID.randomUUID(), "Maria", "0123456789");
        customer2 = new Customer(UUID.randomUUID(), "John", "0123456789");
        customer3 = new Customer(null, "Raul", "0123456789");
        request = new CustomerRegistrationRequest(customer);
        request2 = new CustomerRegistrationRequest(customer3);
    }

    @Test
    void shouldRegisterNewCustomer() {
        // no Customer returned
        given(customerRepository.selectCustomerByPhoneNumber(customer.getPhoneNumber())).willReturn(Optional.empty());

        // When
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();

        assertThat(customerArgumentCaptorValue).isEqualTo(customer);
    }

    @Test
    void shouldRegisterNewCustomerWhenIdIsNull() {
        // no Customer returned
        given(customerRepository.selectCustomerByPhoneNumber(customer3.getPhoneNumber())).willReturn(Optional.empty());

        // When
        underTest.registerNewCustomer(request2);

        // Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();

        assertThat(customerArgumentCaptorValue).isEqualToIgnoringGivenFields(customer3, "id");
        assertThat(customerArgumentCaptorValue.getId()).isNotNull();
    }

    @Test
    void shouldNotSaveCustomerWhenCustomerExists() {
        // ... an existing customer is returned
        given(customerRepository.selectCustomerByPhoneNumber(customer.getPhoneNumber())).willReturn(Optional.of(customer));

        // When
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should(never()).save(any());
    }

    @Test
    void shouldThrowWhenPhoneNumberIsTaken() {
        // ... an existing customer is returned
        given(customerRepository.selectCustomerByPhoneNumber(customer.getPhoneNumber())).willReturn(Optional.of(customer2));

        // When
        // Then
        assertThatThrownBy(() -> underTest.registerNewCustomer(request))
                .isInstanceOf(CustomerPhoneNumberTakenException.class)
                .hasMessage(String.format("phone number [%s] is taken", request.getCustomer().getPhoneNumber()));

        // Finally
        then(customerRepository).should(never()).save(any(Customer.class));
    }
}
