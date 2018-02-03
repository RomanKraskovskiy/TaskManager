package com.kraskovskiy.roman.controller;

import com.kraskovskiy.roman.model.TaskList;
import com.kraskovskiy.roman.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * abstract class controller for add/change task or calendar
 * @author Roman Kraskovskiy
 */
public abstract class ControllerAddAndChange {
    protected View view;
    protected ViewAddAndChangeTask viewAddAndChangeTask;
    protected Controller mainController;
    protected TaskList taskList;

    /**
     * constructor
     * @param view mainView
     * @param viewAddAndChangeTask view for this controller
     * @param mainController main controller
     * @param taskList model of mvc
     */
    public ControllerAddAndChange(View view, ViewAddAndChangeTask viewAddAndChangeTask, Controller mainController,
                                  TaskList taskList) {
        this.view = view;
        this.viewAddAndChangeTask = viewAddAndChangeTask;
        this.mainController = mainController;
        this.taskList = taskList;
    }

    /**
     * @return listener for checkbox for check repeated task
     */
    public RepeatedCheckListener createRepeatedCheckListener() {
        return new RepeatedCheckListener();
    }

    /**
     * repeated checkbox listener
     */
    class RepeatedCheckListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            viewAddAndChangeTask.setRepeatedCheck();
        }
    }

    public CancelTaskListener createCancelTaskListener() {
        return new CancelTaskListener();
    }

    /**
     * cancel button listener
     */
    class CancelTaskListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainController.closeFrame();
        }
    }
}
