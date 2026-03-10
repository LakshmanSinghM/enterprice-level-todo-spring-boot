package com.lakshman.todo.exception;

public class ResourceAlreadyExists extends RuntimeException {

    public ResourceAlreadyExists(String messageString) {
        super(messageString);

    }
}
