package com.kraskovskiy.roman.model;

public class TaskOutputException extends Exception {

    public TaskOutputException() {
        super();
    }

    public TaskOutputException(String message) {
        super(message);
    }
    
    public TaskOutputException(String message, Throwable cause) {
        super(message, cause);
    }
}