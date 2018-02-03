package com.kraskovskiy.roman.controller;

import com.kraskovskiy.roman.model.TaskList;
import com.kraskovskiy.roman.model.Tasks;
import com.kraskovskiy.roman.view.View;
import com.kraskovskiy.roman.view.ViewAddAndChangeTask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;

import static com.kraskovskiy.roman.controller.Controller.logger;

/**
 * controller for add calendar
 * @author Roman Kraskovskiy
 */
public class ControllerCalendar extends ControllerAddAndChange {

    /**
     * constructor
     * @param view mainView
     * @param viewAddAndChangeTask view for this controller
     * @param mainController main controller
     * @param taskList model of mvc
     */
    public ControllerCalendar(View view, ViewAddAndChangeTask viewAddAndChangeTask, Controller mainController,
                              TaskList taskList) {
        super(view, viewAddAndChangeTask, mainController, taskList);
    }

    /**
     * @return listener for button for set calendar
     */
    public SetCalendarListener creacteSetCalendarListener() {
        return new SetCalendarListener();
    }

    /**
     * set calendar buton listener
     */
    class SetCalendarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
            try {
                Date st = viewAddAndChangeTask.getStartDateFromField();
                Date en = viewAddAndChangeTask.getEndDateFromField();
                if(st.getTime() > en.getTime()) {
                    view.showErrorMessage("Start dane can no to be after end !!!");
                }
                SortedMap sortedMap = Tasks.calendar(taskList,st,en);
                viewAddAndChangeTask.showFromToTasks(sortedMap);
            } catch (CloneNotSupportedException e1) {
                logger.error(e1.getMessage(),e1);
                view.showErrorMessage("Unknown error !!!");
            }
        }
    }
}
