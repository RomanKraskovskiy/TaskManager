package com.kraskovskiy.roman.controller;

import com.kraskovskiy.roman.model.TaskList;
import com.kraskovskiy.roman.model.Tasks;
import com.kraskovskiy.roman.view.View;
import com.kraskovskiy.roman.view.ViewAddAndChangeTask;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;

import static com.kraskovskiy.roman.controller.Controller.logger;

public class ControllerCalendar extends ControllerAddAndChange {
    public ControllerCalendar(View view, ViewAddAndChangeTask viewAddAndChangeTask, Controller mainController,
                              TaskList taskList) {
        super(view, viewAddAndChangeTask, mainController, taskList);
    }

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
                Date st = sdf.parse(viewAddAndChangeTask.getStartDateFromField());
                Date en = sdf.parse(viewAddAndChangeTask.getEndDateFromField());
                if(st.getTime() > en.getTime()) {
                    view.showErrorMessage("Start dane can no to be after end !!!");
                }
                SortedMap sortedMap = Tasks.calendar(taskList,st,en);
                viewAddAndChangeTask.showFromToTasks(sortedMap);
            } catch (CloneNotSupportedException e1) {
                logger.error(e1.getMessage(),e1);
                view.showErrorMessage("Unknown error !!!");
            } catch (ParseException e1) {
                view.showErrorMessage("No correct format for date");
                logger.info("USER: " + e + " | no correct format for parse interval");
            }
        }
    }
}
