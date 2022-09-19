package com.amigoscode.testing.repository;

import com.amigoscode.testing.customer.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRegistrationRepositoryTest {

    @Autowired
    private CustomerRegistrationRepository underTest;

    @Test
    void shouldSelectCustomerByPhoneNumber() {
        // Given
        // When
        // Then
    }

    @Test
    void shouldSaveCustomer() {
        // Given
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid, "John", "0123456789");

        // When
        underTest.save(customer);

        // Then
        Optional<Customer> optionalCustomer = underTest.findById(uuid);

        assertThat(optionalCustomer).isPresent()
                .hasValueSatisfying(c -> {
//                    assertThat(c.getId()).isEqualTo(uuid);
//                    assertThat(c.getName()).isEqualTo("John");
//                    assertThat(c.getPhoneNumber()).isEqualTo("0123456789");
                    assertThat(c).isEqualToComparingFieldByField(customer);
                });
    }
}
