package com.kraskovskiy.roman.model;

/**
 * exception for reading from file or stream
 * @author Roman Kraskovskiy
 */
public class TaskOutputException extends Exception {

    /**
     * empty constructor
     */
    public TaskOutputException() {
        super();
    }

    /**
     * constructor with message
     * @param message message for user
     */
    public TaskOutputException(String message) {
        super(message);
    }

    /**
     * constructor with message and exception
     * @param message message for user
     * @param cause exception that will lead to this
     */
    public TaskOutputException(String message, Throwable cause) {
        super(message, cause);
    }
}