package com.kraskovskiy.roman.view;

import com.kraskovskiy.roman.model.Task;
import com.kraskovskiy.roman.model.TaskList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * view of mvc
 * @author Roman Kraskovskiy
 */
public class View extends JFrame {

    private DefaultListModel allTasks = new DefaultListModel();
    private JList taskList = new JList(allTasks);
    private JButton chooseButton = new JButton("Calendar");
    private JButton addButton = new JButton("Add task");
    private JButton chooseTaskButton = new JButton("Choose and view task");
    private JButton exitButton = new JButton("Exit");
    private JLabel labelTasks = new JLabel("All tasks:");
    private JButton removeTaskButtonMenu = new JButton("Remove");

    private int currenTaskIndex;

    /**
     * @return index of selected in list task
     */
    public int getCurrenTaskIndex() {
        return currenTaskIndex;
    }

    /**
     * set index of selected in list task
     * @param currenTaskIndex
     */
    public void setCurrenTaskIndex(int currenTaskIndex) {
        this.currenTaskIndex = currenTaskIndex;
    }

    /**
     * Constructor
     * main menu
     */
    public View() {
        this.setTitle("Task Manager");
        JPanel container = new JPanel();
        container.setLayout(null);
        this.setBounds(100,100,800,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        labelTasks.setBounds(13,17,100,20);
        labelTasks.setFont(new Font("",Font.BOLD,20));
        container.add(labelTasks);
        JScrollPane jsp = new JScrollPane(taskList);
        jsp.setBounds(10,45,400,200);
        container.add(jsp);
        addButton.setBounds(450,45,100,50);
        container.add(addButton);
        chooseButton.setBounds(450,115,190,50);
        container.add(chooseButton);
        removeTaskButtonMenu.setBounds(650,115,100,50);
        container.add(removeTaskButtonMenu);
        chooseTaskButton.setBounds(560,45,190,50);
        container.add(chooseTaskButton);
        exitButton.setBounds(450,185,300,50);
        container.add(exitButton);
        this.add(container);
        currenTaskIndex = -1;
    }

    /**
     * add listener for button for add frame for add task
     * @param al
     */
    public void addButtonListener(ActionListener al) {
        addButton.addActionListener(al);
    }

    /**
     * add listener for button for adding frame for change task
     * @param al
     */
    public void addChangeAndViewListener(ActionListener al) {
        chooseTaskButton.addActionListener(al);
    }

    /**
     * add listener for button for closing program
     * @param al
     */
    public void addExitButtonListener(ActionListener al) {
        exitButton.addActionListener(al);
    }

    /**
     * add listener for changing selected element in list
     * @param lsl
     */
    public void addCurrentTaskIndexListener(ListSelectionListener lsl) {
        taskList.addListSelectionListener(lsl);
    }

    /**
     * add listener for button for remove Task
     * @param al
     */
    public void addRemoveButtonListener(ActionListener al) {
        removeTaskButtonMenu.addActionListener(al);
    }

    /**
     * add listener for button for adding frame for calendar
     * @param al
     */
    public void addCalendarButtonListener(ActionListener al) {
        chooseButton.addActionListener(al);
    }

    /**
     * display all tasks on screen
     * @param tasks tasks
     */
    public void showAllTask(TaskList tasks) {
        Iterator itr = tasks.iterator();
        int i = 0;
        allTasks.clear();
        while(itr.hasNext()) {
            Task t = (Task) itr.next();
            allTasks.addElement((i + 1) + ") " + t.toString());
            i++;
        }
    }

    /**
     * create dialog window for alerting user
     * @param s message
     */
    public void showErrorMessage(String s) {
        JOptionPane.showConfirmDialog(/*getAddTaskFrame()*/this,s,
                "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * create dialog window for alerting user about task that time is now
     * @param s message
     */
    public void showCurrentTask(String s) {
        JOptionPane.showConfirmDialog(this,s,
                "What time is now ?", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }
}
