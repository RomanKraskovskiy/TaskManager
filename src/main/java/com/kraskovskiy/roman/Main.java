package com.kraskovskiy.roman;

import com.kraskovskiy.roman.controller.Controller;
import com.kraskovskiy.roman.model.*;

import java.io.IOException;

/**
 * main class
 * @author Roman Kraskovskiy
 */
public class Main {
    /**
     * main method
     * @param args arguments
     * @throws CloneNotSupportedException
     * @throws IOException
     * @throws TaskException
     */
    public static void main(String[] args) throws CloneNotSupportedException, IOException, TaskException {
        Controller tasksController = new Controller();
        try {
            tasksController.updateView();
        } catch (Exception e) {
            Controller.logger.error(e.getMessage(),e);
        }
    }
}
