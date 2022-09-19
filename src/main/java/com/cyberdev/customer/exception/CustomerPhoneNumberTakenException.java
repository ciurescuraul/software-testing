package com.cyberdev.customer.exception;

public class CustomerPhoneNumberTakenException extends IllegalStateException{

    public CustomerPhoneNumberTakenException(String s) {
        super(s);
    }
}
