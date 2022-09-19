package com.amigoscode.testing.exception;

public class PhoneNumberTakenException extends IllegalStateException{

    public PhoneNumberTakenException(String s) {
        super(s);
    }
}
