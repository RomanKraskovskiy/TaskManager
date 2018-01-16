package com.kraskovskiy.roman.model;

public class TaskInputException extends Exception {

    public TaskInputException() {
        super();
    }

    public TaskInputException(String message) {
        super(message);
    }
    
    public TaskInputException(String message, Throwable cause) {
        super(message, cause);
    }
}