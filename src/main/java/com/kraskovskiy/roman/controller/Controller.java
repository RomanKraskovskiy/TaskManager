package com.kraskovskiy.roman.controller;

import com.kraskovskiy.roman.model.*;
import com.kraskovskiy.roman.view.View;
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

    public final static Logger logger = Logger.getLogger(Controller.class);

    /**
     * constructor
     * @param taskList model of mvc
     * @param view view of mvc
     */
    public Controller(TaskList taskList, View view) {
        this.taskList = taskList;
        this.view = view;
        this.view.addButtonListener(new SetTaskListener());
        this.view.addCheckRepeatedListener(new RepeatedCheckListener());
        this.view.addTaskButtonListener(new AddTaskListener());
        this.view.addCancelButtonListener(new CancelTaskListener());
        this.view.addChangeAndViewListener(new ChangeTaskListener());
        this.view.addExitButtonListener(new ExitButtonListener());
        this.view.addCalendarButtonListener(new CalendarButtonListener());
        this.view.addSetCalendarButtonListener(new SetCalendarListener());
        this.view.addCurrentTaskIndexListener(new GetIndexChoosedTask());
        this.view.addChangeButtonListener(new ChangeTask());
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
            removeTask(view.getCurrenTaskIndex());
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
                logger.fatal("ERROR: " + e + " | " + e1.getMessage(),e1);
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
                Date st = sdf.parse(view.getStartDateFromField());
                Date en = sdf.parse(view.getEndDateFromField());
                if(st.getTime() > en.getTime()) {
                    view.showErrorMessage("Start dane can no to be after end !!!");
                }
                SortedMap sortedMap = Tasks.calendar(taskList,st,en);
                view.showFromToTasks(sortedMap);
            } catch (CloneNotSupportedException e1) {
                logger.fatal(e1.getMessage(),e1);
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
            view.createCalendarFrame();
            view.addCloseWindowListener(new CloseWindowListener());
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
                logger.fatal(e1.getMessage(),e1);
            } catch (TaskOutputException e1) {
                view.showErrorMessage("Changing don't save !!!");
                logger.fatal(e1.getMessage(),e1);
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
            view.createAddTaskFrame();
            view.addCloseWindowListener(new CloseWindowListener());
        }
    }

    /**
     * addChangeFrame button listener
     */
    class ChangeTaskListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                view.createChangeTaskFrame(taskList, view.getCurrenTaskIndex());
            }catch (ArrayIndexOutOfBoundsException e1) {
                view.showErrorMessage("Please, chose task for first !");
                closeFrame();
            }
            view.addCloseWindowListener(new CloseWindowListener());
        }
    }

    /**
     * repeated checkbox listener
     */
    class RepeatedCheckListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setRepeatedCheck();
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
                logger.fatal("ERROR: " + e + " | " + e1.getMessage(),e1);
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
        view.getAddTaskFrame().setVisible(false);
        view.getAddTaskFrame().dispose();
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
        boolean rep = view.isRepeatedFromField();
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        Task task = new Task();
        task.setTitle(view.getTitleFromField());
        if (task.getTitle().equals("")) {
            JOptionPane.showConfirmDialog(view.getAddTaskFrame(),"Title is empty !!!",
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            if (rep) {
                task.setTime(sdf.parse(view.getStartDateFromField()), sdf.parse(view.getEndDateFromField())
                        , view.getIntervalFromField());
            } else {
                task.setTime(sdf.parse(view.getStartDateFromField()));
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
        view.getAddTaskFrame().setVisible(false);
        view.getAddTaskFrame().dispose();
        view.setEnabled(true);
        view.setVisible(true);
    }

    /**
     * change task
     * @param index of task
     * @throws CloneNotSupportedException
     */
    public void changeTask(int index) throws CloneNotSupportedException {
        boolean rep = view.isRepeatedFromField();
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        Task task = taskList.getTask(index);
        task.setTitle(view.getTitleFromField());
        if (task.getTitle().equals("")) {
            JOptionPane.showConfirmDialog(view.getAddTaskFrame(),"Title is empty !!!",
                    "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            if (rep) {
                task.setTime(sdf.parse(view.getStartDateFromField()), sdf.parse(view.getEndDateFromField())
                        , view.getIntervalFromField());
            } else {
                task.setTime(sdf.parse(view.getStartDateFromField()));
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
        task.setActive(view.isActiveFromField());
        task.setView(view);
        view.showAllTask(taskList);
        view.getAddTaskFrame().setVisible(false);
        view.getAddTaskFrame().dispose();
        view.setEnabled(true);
        view.setVisible(true);
    }
}
