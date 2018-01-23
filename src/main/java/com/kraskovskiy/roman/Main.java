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
    public static void main(String[] args) throws ParseException, CloneNotSupportedException, IOException, TaskInputException, TaskOutputException, TaskException {
        TaskList ts = new ArrayTaskList();
        View viewTasks = new View();
        viewTasks.setVisible(true);
        Controller tasksContoller = new Controller(ts,viewTasks);
        try {
            TaskIO.readBinary(ts, new File("tasks.txt"));
        }catch (TaskInputException e) {
            System.out.println(e.getMessage());
            Controller.logger.error(e.getMessage(),e);
        }
        for(;;) {
            try {
                tasksContoller.menu();
            }catch (TaskException e) {
                System.out.println(e.getMessage());
            }catch (TaskOutputException e) {
                System.out.println(e.getMessage());
                Controller.logger.error(e.getMessage(),e);
            }catch (Exception e) {
                Controller.logger.fatal(e.getMessage(),e);
            }
        }
    }
}
