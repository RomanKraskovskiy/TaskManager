package com.kraskovskiy.roman.model;

/**
 * exception for writing in file or stream
 * @author Roman Kraskovskiy
 */
public class TaskInputException extends Exception {

    /**
     * empty constructor
     */
    public TaskInputException() {
        super();
    }

    /**
     * constructor with message
     * @param message message for user
     */
    public TaskInputException(String message) {
        super(message);
    }

    /**
     * constructor with message and exception
     * @param message message for user
     * @param cause exception that will lead to this
     */
    public TaskInputException(String message, Throwable cause) {
        super(message, cause);
    }
}