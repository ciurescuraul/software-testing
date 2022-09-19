package com.amigoscode.testing.repository;

import com.amigoscode.testing.customer.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(properties = {"spring.jpa.properties.javax.persistence.validation.mode=none"})
class CustomerRegistrationRepositoryTest {

    @Autowired
    private CustomerRegistrationRepository underTest;

    @Test
    void shouldSelectCustomerByPhoneNumber() {
        // Given
        String phoneNumber = "0000";
        Customer customer = new Customer(UUID.randomUUID(), "John", phoneNumber);

        // When
        underTest.save(customer);

        // Then
        Optional<Customer> optionalCustomer = underTest.selectCustomerByPhoneNumber(phoneNumber);

        assertThat(optionalCustomer).isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).isEqualToComparingFieldByField(customer);
                });
    }

    @Test
    void shouldNotSelectCustomerByPhoneNumberWhenNumberDoesNotExists() {
        // Given
        String phoneNumber = "0000";

        // When
        Optional<Customer> optionalCustomer = underTest.selectCustomerByPhoneNumber(phoneNumber);

        // Then
        assertThat(optionalCustomer).isNotPresent();
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

    @Test
    void shouldNotSaveCustomerWhenNameIsNull() {
        // Given
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid, null, "0123456789");

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(customer))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value");
    }

    @Test
    void shouldNotSaveCustomerWhenPhoneNumberIsNull() {
        // Given
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid, "Ionescu", null);

        // When
        // Then
        assertThatThrownBy(() -> underTest.save(customer))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("not-null property references a null or transient value");
    }
}
