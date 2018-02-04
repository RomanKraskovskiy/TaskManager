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
import java.util.*;

/**
 * controller of mvc
 * @author Roman Kraskovskiy
 */
public class Controller {
    private TaskList taskList = new ArrayTaskList();

    private View view = new View();
    private ViewAddTask viewAddTask = new ViewAddTask();
    private ViewChangeTask viewChangeTask = new ViewChangeTask();
    private ViewCalendar viewCalendar = new ViewCalendar();

    private ControllerAdd controllerAdd = new ControllerAdd(view,viewAddTask,this,taskList);
    private ControllerChange controllerChange = new ControllerChange(view,viewChangeTask,this,taskList);
    private ControllerCalendar controllerCalendar = new ControllerCalendar(view,viewCalendar,this,taskList);

    public final static Logger logger = Logger.getLogger(Controller.class);

    /**
     * constructor, adding listener mainView and setting tasks
     * @throws CloneNotSupportedException
     * @throws IOException
     * @throws TaskException
     */
    public Controller() throws CloneNotSupportedException, IOException, TaskException {
        try {
            TaskIO.readBinary(taskList, new File("tasks.txt"));
        }catch (TaskInputException e) {
            Controller.logger.error(e.getMessage(),e);
        }
        view.setVisible(true);
        viewAddTask.setMainFrame(view);
        viewChangeTask.setMainFrame(view);
        viewCalendar.setMainFrame(view);

        view.changeListMenuListener(new ChangeListMenuListener());
        view.addButtonListener(new SetTaskListener());
        view.addChangeAndViewListener(new ChangeTaskListener());
        view.addExitButtonListener(new ExitButtonListener());
        view.addCalendarButtonListener(new CalendarButtonListener());
        view.addRemoveButtonListener(new RemoveTask());
        view.addCurrentTaskIndexListener(new GetIndexChoosedTask());

        viewAddTask.addCheckRepeatedListener(controllerAdd.createRepeatedCheckListener());
        viewAddTask.addTaskButtonListener(controllerAdd.createAddTaskListener());
        viewAddTask.addCancelButtonListener(controllerAdd.createCancelTaskListener());

        viewCalendar.addSetCalendarButtonListener(controllerCalendar.creacteSetCalendarListener());
        viewCalendar.addCancelButtonListener(controllerCalendar.createCancelTaskListener());

        viewChangeTask.addChangeButtonListener(controllerChange.createChangeTask());
        viewChangeTask.addRemoveButtonListener(new RemoveTask());
        viewChangeTask.addCancelButtonListener(controllerChange.createCancelTaskListener());
        viewChangeTask.addCheckRepeatedListener(controllerChange.createRepeatedCheckListener());

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
     * change listMenu listener
     */
    class ChangeListMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.showTask(taskList);
        }
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
            if (view.isActiveTasks()) {
                view.showActiveTask(taskList);
            } else {
                view.showAllTask(taskList);
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
            view.setCurrenTaskIndex(view.getAllTasks().indexOf(source.getSelectedValue()));
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


}
