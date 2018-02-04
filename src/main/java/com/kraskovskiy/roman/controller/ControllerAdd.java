package com.kraskovskiy.roman.controller;

import com.kraskovskiy.roman.model.Task;
import com.kraskovskiy.roman.model.TaskException;
import com.kraskovskiy.roman.model.TaskList;
import com.kraskovskiy.roman.view.View;
import com.kraskovskiy.roman.view.ViewAddAndChangeTask;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.kraskovskiy.roman.controller.Controller.logger;

/**
 * controller for add task
 * @author Roman Kraskovskiy
 */
public class ControllerAdd extends ControllerAddAndChange {

    /**
     * constructor
     * @param view mainView
     * @param viewAddAndChangeTask view for this controller
     * @param mainController main controller
     * @param taskList model of mvc
     */
    public ControllerAdd(View view, ViewAddAndChangeTask viewAddAndChangeTask, Controller mainController,
                         TaskList taskList) {
        super(view, viewAddAndChangeTask, mainController, taskList);
    }

    /**
     * @return listener for button for adding task
     */
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
     * @throws TaskException
     * @throws CloneNotSupportedException
     */
    public void setTask() throws TaskException, CloneNotSupportedException {
        boolean rep = viewAddAndChangeTask.isRepeatedFromField();
        Task task = new Task();
        task.setTitle(viewAddAndChangeTask.getTitleFromField());
        if (task.getTitle().equals("")) {
            JOptionPane.showConfirmDialog(viewAddAndChangeTask.getAddTaskFrame(),"Title is empty !!!",
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            if (rep) {
                task.setTime(viewAddAndChangeTask.getStartDateFromField(), viewAddAndChangeTask.getEndDateFromField()
                        , viewAddAndChangeTask.getIntervalFromField());
            } else {
                task.setTime(viewAddAndChangeTask.getStartDateFromField());
            }
        } catch (NumberFormatException e) {
            view.showErrorMessage("No correct format for interval !!!");
            logger.info("USER: " + e + " | no correct format for interval");
            return;
        }
        task.setActive(true);
        task.setView(view);
        taskList.add(task);
        if (view.isActiveTasks()) {
            view.showActiveTask(taskList);
        } else {
            view.showAllTask(taskList);
        }
        viewAddAndChangeTask.getAddTaskFrame().setVisible(false);
        viewAddAndChangeTask.getAddTaskFrame().dispose();
        view.setEnabled(true);
        view.setVisible(true);
    }
}
