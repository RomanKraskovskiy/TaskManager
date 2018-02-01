package com.kraskovskiy.roman.controller;

import com.kraskovskiy.roman.model.Task;
import com.kraskovskiy.roman.model.TaskException;
import com.kraskovskiy.roman.model.TaskList;
import com.kraskovskiy.roman.view.View;
import com.kraskovskiy.roman.view.ViewAddAndChangeTask;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.kraskovskiy.roman.controller.Controller.logger;

public class ControllerChange extends ControllerAddAndChange {
    public ControllerChange(View view, ViewAddAndChangeTask viewAddAndChangeTask, Controller mainController,
                            TaskList taskList) {
        super(view, viewAddAndChangeTask, mainController, taskList);
    }

    public ChangeTask createChangeTask() {
        return new ChangeTask();
    }

    /**
     * change task button listener
     */
    class ChangeTask implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                changeTask(view.getCurrenTaskIndex());
            } catch (CloneNotSupportedException e1) {
                view.showErrorMessage("");
                logger.error("ERROR: " + e + " | " + e1.getMessage(),e1);
            }
        }
    }

    /**
     * change task
     * @param index of task
     * @throws CloneNotSupportedException
     */
    public void changeTask(int index) throws CloneNotSupportedException {
        boolean rep = viewAddAndChangeTask.isRepeatedFromField();
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        Task task = taskList.getTask(index);
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
        } catch (TaskException e) {
            e.printStackTrace();
        }
        task.setActive(viewAddAndChangeTask.isActiveFromField());
        task.setView(view);
        view.showAllTask(taskList);
        viewAddAndChangeTask.getAddTaskFrame().setVisible(false);
        viewAddAndChangeTask.getAddTaskFrame().dispose();
        view.setEnabled(true);
        view.setVisible(true);
    }
}
