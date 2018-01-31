package com.kraskovskiy.roman.controller;

import com.kraskovskiy.roman.model.*;
import com.kraskovskiy.roman.view.View;
import com.kraskovskiy.roman.view.ViewAddTask;
import com.kraskovskiy.roman.view.ViewCalendar;
import com.kraskovskiy.roman.view.ViewChangeTask;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * controller of mvc
 * @author Roman Kraskovskiy
 */
public class Controller {
    private TaskList taskList;
    private View view;
    private ViewAddTask viewAddTask = new ViewAddTask();
    private ViewChangeTask viewChangeTask = new ViewChangeTask();
    private ViewCalendar viewCalendar = new ViewCalendar();
    public final static Logger logger = Logger.getLogger(Controller.class);

    /**
     * constructor
     * @param taskList model of mvc
     * @param view view of mvc
     */
    public Controller(TaskList taskList, View view) {
        this.taskList = taskList;
        this.view = view;
        viewAddTask.setMainFrame(view);
        viewChangeTask.setMainFrame(view);
        viewCalendar.setMainFrame(view);
        this.view.addButtonListener(new SetTaskListener());
        this.viewAddTask.addCheckRepeatedListener(new RepeatedCheckListener());
        this.viewAddTask.addTaskButtonListener(new AddTaskListener());
        this.viewAddTask.addCancelButtonListener(new CancelTaskListener());
        this.view.addChangeAndViewListener(new ChangeTaskListener());
        this.view.addExitButtonListener(new ExitButtonListener());
        this.view.addCalendarButtonListener(new CalendarButtonListener());
        this.viewCalendar.addSetCalendarButtonListener(new SetCalendarListener());
        this.viewCalendar.addCancelButtonListener(new CancelTaskListener());
        this.view.addCurrentTaskIndexListener(new GetIndexChoosedTask());
        this.viewChangeTask.addChangeButtonListener(new ChangeTask());
        this.viewChangeTask.addRemoveButtonListener(new RemoveTask());
        this.viewChangeTask.addCancelButtonListener(new CancelTaskListener());
        this.viewChangeTask.addCheckRepeatedListener(new RepeatedCheckListener());
        this.view.addRemoveButtonListener(new RemoveTask());
        Iterator itr = taskList.iterator();
        while(itr.hasNext()) {
            Task t = (Task) itr.next();
            t.setView(view);
        }
    }

    /**
     * control of viewing of all tasks
     * @throws ParseException
     */
    public void updateView() throws ParseException {
        view.showAllTask(taskList);
    }

    /**
     * remove task button listener
     */
    class RemoveTask implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                removeTask(view.getCurrenTaskIndex());
            } catch (ArrayIndexOutOfBoundsException e1) {
                    view.showErrorMessage("Please, choose task for first !");
                    closeFrame();
            }
            closeFrame();
            view.showAllTask(taskList);
        }
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
     * set index of choosed task in list listener
     */
    class GetIndexChoosedTask implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            JList source = (JList)e.getSource();
            view.setCurrenTaskIndex(source.getSelectedIndex());
        }
    }

    /**
     * set calendar buton listener
     */
    class SetCalendarListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
            try {
                Date st = sdf.parse(viewCalendar.getStartDateFromField());
                Date en = sdf.parse(viewCalendar.getEndDateFromField());
                if(st.getTime() > en.getTime()) {
                    view.showErrorMessage("Start dane can no to be after end !!!");
                }
                SortedMap sortedMap = Tasks.calendar(taskList,st,en);
                viewCalendar.showFromToTasks(sortedMap);
            } catch (CloneNotSupportedException e1) {
                logger.error(e1.getMessage(),e1);
                view.showErrorMessage("Unknown error !!!");
            } catch (ParseException e1) {
                view.showErrorMessage("No correct format for date");
                logger.info("USER: " + e + " | no correct format for parse interval");
            }
        }
    }

    /**
     * add calendar frame button listener
     */
    class CalendarButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            viewCalendar.newFrame();
            viewCalendar.addCloseWindowListener(new CloseWindowListener());
        }
    }

    /**
     * close window listener
     */
    class CloseWindowListener implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            view.setEnabled(true);
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }

    /**
     * exit button listener
     */
    class ExitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                TaskIO.writeBinary(taskList,new File("tasks.txt"));
            } catch (IOException e1) {
                view.showErrorMessage("Changing don't save !!!");
                logger.error(e1.getMessage(),e1);
            } catch (TaskOutputException e1) {
                view.showErrorMessage("Changing don't save !!!");
                logger.error(e1.getMessage(),e1);
            }

            System.exit(0);
        }
    }

    /**
     * add frame for adding task listener
     */
    class SetTaskListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            viewAddTask.newFrame();
            viewAddTask.addCloseWindowListener(new CloseWindowListener());
        }
    }

    /**
     * addChangeFrame button listener
     */
    class ChangeTaskListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                viewChangeTask.setTaskListandIndex(taskList, view.getCurrenTaskIndex());
                viewChangeTask.newFrame();
            }catch (ArrayIndexOutOfBoundsException e1) {
                view.showErrorMessage("Please, chose task for first !");
                closeFrame();
            }
            viewChangeTask.addCloseWindowListener(new CloseWindowListener());
        }
    }

    /**
     * repeated checkbox listener
     */
    class RepeatedCheckListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            viewChangeTask.setRepeatedCheck();
            viewAddTask.setRepeatedCheck();
        }
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
     * cancel button listener
     */
    class CancelTaskListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            closeFrame();
        }
    }

    /**
     * close frame (add/change)
     */
    public void closeFrame() {
        viewAddTask.getAddTaskFrame().setVisible(false);
        viewAddTask.getAddTaskFrame().dispose();
        viewChangeTask.getAddTaskFrame().setVisible(false);
        viewChangeTask.getAddTaskFrame().dispose();
        viewCalendar.getAddTaskFrame().setVisible(false);
        viewCalendar.getAddTaskFrame().dispose();
        view.setEnabled(true);
        view.setVisible(true);
    }

    /**
     * remove choosed task
     * @param taskNumb index of task
     */
    public void removeTask(int taskNumb) {
        taskList.remove(taskList.getTask(taskNumb));
    }

    /**
     * add new Task
     * @throws ParseException
     * @throws TaskException
     * @throws CloneNotSupportedException
     */
    public void setTask() throws ParseException, TaskException, CloneNotSupportedException {
        boolean rep = viewAddTask.isRepeatedFromField();
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        Task task = new Task();
        task.setTitle(viewAddTask.getTitleFromField());
        if (task.getTitle().equals("")) {
            JOptionPane.showConfirmDialog(viewAddTask.getAddTaskFrame(),"Title is empty !!!",
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            if (rep) {
                task.setTime(sdf.parse(viewAddTask.getStartDateFromField()), sdf.parse(viewAddTask.getEndDateFromField())
                        , viewAddTask.getIntervalFromField());
            } else {
                task.setTime(sdf.parse(viewAddTask.getStartDateFromField()));
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
        viewAddTask.getAddTaskFrame().setVisible(false);
        viewAddTask.getAddTaskFrame().dispose();
        view.setEnabled(true);
        view.setVisible(true);
    }

    /**
     * change task
     * @param index of task
     * @throws CloneNotSupportedException
     */
    public void changeTask(int index) throws CloneNotSupportedException {
        boolean rep = viewChangeTask.isRepeatedFromField();
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        Task task = taskList.getTask(index);
        task.setTitle(viewChangeTask.getTitleFromField());
        if (task.getTitle().equals("")) {
            JOptionPane.showConfirmDialog(viewChangeTask.getAddTaskFrame(),"Title is empty !!!",
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            if (rep) {
                task.setTime(sdf.parse(viewChangeTask.getStartDateFromField()), sdf.parse(viewChangeTask.getEndDateFromField())
                        , viewChangeTask.getIntervalFromField());
            } else {
                task.setTime(sdf.parse(viewChangeTask.getStartDateFromField()));
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
        task.setActive(viewChangeTask.isActiveFromField());
        task.setView(view);
        view.showAllTask(taskList);
        viewChangeTask.getAddTaskFrame().setVisible(false);
        viewChangeTask.getAddTaskFrame().dispose();
        view.setEnabled(true);
        view.setVisible(true);
    }
}
