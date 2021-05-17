package com.heycarlight.exceptions;

public class NonExistingEntity extends RuntimeException {
    public NonExistingEntity(String msg) {
        super(msg);
    }
}
