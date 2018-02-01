package com.kraskovskiy.roman;

import com.kraskovskiy.roman.controller.Controller;
import com.kraskovskiy.roman.model.*;
import com.kraskovskiy.roman.view.View;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * main class
 * @author Roman Kraskovskiy
 */
public class Main {
    /**
     * main method
     * @param args arguments
     * @throws ParseException
     * @throws CloneNotSupportedException
     * @throws IOException
     * @throws TaskInputException
     * @throws TaskOutputException
     * @throws TaskException
     */
    public static void main(String[] args) throws CloneNotSupportedException, IOException, TaskException {
        Controller tasksContoller = new Controller();
        try {
            tasksContoller.updateView();
        } catch (Exception e) {
            Controller.logger.error(e.getMessage(),e);
        }
    }
}
