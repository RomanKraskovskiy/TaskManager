package com.kraskovskiy.roman;

import com.kraskovskiy.roman.controller.Controller;
import com.kraskovskiy.roman.model.*;
import com.kraskovskiy.roman.view.View;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException, CloneNotSupportedException, IOException, TaskInputException, TaskOutputException, TaskException {
        TaskList ts = new ArrayTaskList();
        View viewTasks = new View();
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
