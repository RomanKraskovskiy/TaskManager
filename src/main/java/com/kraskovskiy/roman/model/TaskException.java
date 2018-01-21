package com.kraskovskiy.roman.model;

/**
 * exception for testing right formalization of task
 * @author Roman Kraskovskiy
 */
public class TaskException extends Exception {

    /**
     * empty constructor
     */
    public TaskException() {
        super();
    }

    /**
     * constructor with message
     * @param message message for user
     */
    public TaskException(String message) {
        super(message);
    }
}