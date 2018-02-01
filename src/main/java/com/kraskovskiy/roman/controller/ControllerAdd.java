package com.kraskovskiy.roman.controller;

import com.kraskovskiy.roman.model.Task;
import com.kraskovskiy.roman.model.TaskException;
import com.kraskovskiy.roman.model.TaskList;
import com.kraskovskiy.roman.view.View;
import com.kraskovskiy.roman.view.ViewAddAndChangeTask;
import com.kraskovskiy.roman.view.ViewAddTask;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.kraskovskiy.roman.controller.Controller.logger;

public class ControllerAdd extends ControllerAddAndChange {

    public ControllerAdd(View view, ViewAddAndChangeTask viewAddAndChangeTask, Controller mainController,
                         TaskList taskList) {
        super(view, viewAddAndChangeTask, mainController, taskList);
    }

    public AddTaskListener createAddTaskListener() {
        return new AddTaskListener();
    }

    /**
     * add task button listener
     */
    class AddTaskListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                setTask();
            } catch (ParseException e1) {
                view.showErrorMessage("No correct format for date");
                logger.info("USER: " + e + " | no correct format for parse interval");
            } catch (TaskException e1) {
                view.showErrorMessage(e1.getMessage());
                logger.info("USER: " + e + " | " + e1.getMessage());
            } catch (CloneNotSupportedException e1) {
                view.showErrorMessage("");
                logger.error("ERROR: " + e + " | " + e1.getMessage(),e1);
            }
        }
    }

    /**
     * add new Task
     * @throws ParseException
     * @throws TaskException
     * @throws CloneNotSupportedException
     */
    public void setTask() throws ParseException, TaskException, CloneNotSupportedException {
        boolean rep = viewAddAndChangeTask.isRepeatedFromField();
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        Task task = new Task();
        task.setTitle(viewAddAndChangeTask.getTitleFromField());
        if (task.getTitle().equals("")) {
            JOptionPane.showConfirmDialog(viewAddAndChangeTask.getAddTaskFrame(),"Title is empty !!!",
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            if (rep) {
                task.setTime(sdf.parse(viewAddAndChangeTask.getStartDateFromField()), sdf.parse(viewAddAndChangeTask.getEndDateFromField())
                        , viewAddAndChangeTask.getIntervalFromField());
            } else {
                task.setTime(sdf.parse(viewAddAndChangeTask.getStartDateFromField()));
            }
        } catch (ParseException e) {
            view.showErrorMessage("No correct format for date!!!");
            logger.info("USER: " + e + " | no correct format for parse date");
            return;
        } catch (NumberFormatException e) {
            view.showErrorMessage("No correct format for interval !!!");
            logger.info("USER: " + e + " | no correct format for interval");
            return;
        }
        task.setActive(true);
        task.setView(view);
        taskList.add(task);
        view.showAllTask(taskList);
        viewAddAndChangeTask.getAddTaskFrame().setVisible(false);
        viewAddAndChangeTask.getAddTaskFrame().dispose();
        view.setEnabled(true);
        view.setVisible(true);
    }
}
