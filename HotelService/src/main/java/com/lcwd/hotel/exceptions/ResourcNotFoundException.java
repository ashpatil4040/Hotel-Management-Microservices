package com.lcwd.hotel.exceptions;

public class ResourcNotFoundException  extends RuntimeException {

    public ResourcNotFoundException() {
        super("Resource not found on server !!");
    }

    public ResourcNotFoundException(String message) {
        super(message);
    }
}
