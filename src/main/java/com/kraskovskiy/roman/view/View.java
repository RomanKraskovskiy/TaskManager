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
    private JButton chooseButton = new JButton("Choose interval for calendar");
    private JButton addButton = new JButton("Add task");
    private JButton chooseTaskButton = new JButton("Choose and view task");
    private JButton exitButton = new JButton("Exit");
    private JLabel labelTasks = new JLabel("All tasks:");

    JFrame addTaskFrame = new JFrame();
    JPanel addTaskPanel;
    private JLabel titleDateLabel = new JLabel("Enter title:");
    private JTextField titleDate = new JTextField();
    private JTextField startTimeDate = new JTextField();
    private JTextField endTimeDate = new JTextField();
    private JLabel startTimeDateLabel = new JLabel("Enter start date:");
    private JLabel endTimeDateLabel = new JLabel("Enter end date:");
    private JCheckBox repeatedCheck = new JCheckBox("Repeated");
    private JButton addTaskButton = new JButton("Add");
    private JButton cancelTaskButton = new JButton("Cancel");
    private JLabel intervalLabel = new JLabel("Enter interval:");
    private JTextField interval = new JTextField();

    private JButton changeTaskButton = new JButton("Change");
    private JCheckBox activeCheck = new JCheckBox("Active");

    private JButton setCalendarButton = new JButton("Calendar");
    private JTextArea calendarArea = new JTextArea();

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
        chooseButton.setBounds(450,115,300,50);
        container.add(chooseButton);
        chooseTaskButton.setBounds(560,45,190,50);
        container.add(chooseTaskButton);
        exitButton.setBounds(450,185,300,50);
        container.add(exitButton);
        this.add(container);
        taskList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println(taskList.getSelectedIndex());
            }
        });
    }

    public void setRepeatedCheck() {
        if(endTimeDate.isEnabled()) {
            endTimeDate.setEnabled(false);
            interval.setEnabled(false);
        } else {
            endTimeDate.setEnabled(true);
            interval.setEnabled(true);
        }
    }

    public void addCloseWindowListener(WindowListener wl) {
        addTaskFrame.addWindowListener(wl);
    }
    public void addButtonListener(ActionListener al) {
        addButton.addActionListener(al);
    }

    public void addCheckRepeatedListener(ActionListener al) {
        repeatedCheck.addActionListener(al);
    }

    public void addTaskButtonListener(ActionListener al) {
        addTaskButton.addActionListener(al);
    }

    public void addCancelButtonListener(ActionListener al) {
        cancelTaskButton.addActionListener(al);
    }

    public void addChangeAndViewListener(ActionListener al) {
        chooseTaskButton.addActionListener(al);
    }

    public void addExitButtonListener(ActionListener al) {
        exitButton.addActionListener(al);
    }

    public void addCalendarButtonListener(ActionListener al) {
        chooseButton.addActionListener(al);
    }

    public void addSetCalendarButtonListener(ActionListener al) {
        setCalendarButton.addActionListener(al);
    }

    public String getTitleFromField() {
        return titleDate.getText();
    }

    public String getStartDateFromField() {
        return startTimeDate.getText();
    }

    public String getEndDateFromField() {
        return endTimeDate.getText();
    }

    public int getIntervalFromField() {
        return Integer.parseInt(interval.getText());
    }

    public JFrame getAddTaskFrame() {
        return addTaskFrame;
    }

    public boolean isRepeatedFromField () {
        return repeatedCheck.isSelected();
    }

    public int getTaskIndex() {
        System.out.println(taskList.getSelectedIndex());
        return taskList.getSelectedIndex();
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
        taskList = new JList(allTasks);
    }

    public void createFrame() {
        titleDate.setText("");
        startTimeDate.setText("");
        endTimeDate.setText("");
        interval.setText("");
        addTaskFrame = new JFrame("Add Task");
        addTaskFrame.setBounds(200, 200, 300, 300);
        addTaskFrame.setResizable(false);
        addTaskFrame.setVisible(true);
        addTaskPanel = new JPanel();
        addTaskPanel.setLayout(null);
        titleDateLabel.setBounds(10, 10, 274, 20);
        addTaskPanel.add(titleDateLabel);
        titleDate.setBounds(9, 30, 274, 20);
        addTaskPanel.add(titleDate);
        startTimeDateLabel.setBounds(10, 50, 274, 20);
        addTaskPanel.add(startTimeDateLabel);
        startTimeDate.setBounds(9, 70, 274, 20);
        addTaskPanel.add(startTimeDate);
        repeatedCheck.setBounds(10, 90, 90, 20);
        addTaskPanel.add(repeatedCheck);
        endTimeDateLabel.setBounds(10, 110, 274, 20);
        addTaskPanel.add(endTimeDateLabel);
        endTimeDate.setBounds(9, 130, 274, 20);
        addTaskPanel.add(endTimeDate);
        intervalLabel.setBounds(10, 150, 274, 20);
        addTaskPanel.add(intervalLabel);
        interval.setBounds(9, 170, 274, 20);
        if (!repeatedCheck.isSelected()){
            endTimeDate.setEnabled(false);
            interval.setEnabled(false);
        }
        addTaskPanel.add(interval);
        cancelTaskButton.setBounds(150,210,100,40);
        addTaskPanel.add(cancelTaskButton);
        addTaskFrame.add(addTaskPanel);
        this.setEnabled(false);
    }

    public void createAddTaskFrame() {
        createFrame();
        addTaskButton.setBounds(40,210,100,40);
        addTaskPanel.add(addTaskButton);
    }

    public void createChangeTaskFrame(TaskList taskList, int index) {
        createFrame();
        activeCheck.setBounds(110, 90, 90, 20);
        addTaskPanel.add(activeCheck);
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        Task task = taskList.getTask(index);
        titleDate.setText(task.getTitle());
        startTimeDate.setText(sdf.format(task.getStartTime()));
        if(task.isActive()) {
            activeCheck.setSelected(true);
        } else {
            activeCheck.setSelected(false);
        }
        if(task.isRepeated()) {
            endTimeDate.setEnabled(true);
            interval.setEnabled(true);
            repeatedCheck.setSelected(true);
            endTimeDate.setText(sdf.format(task.getEndTime()));
            interval.setText(Integer.toString(task.getRepeatInterval()));
        } else {
            endTimeDate.setEnabled(false);
            interval.setEnabled(false);
            repeatedCheck.setSelected(false);
            endTimeDate.setText("");
            interval.setText("");
        }
    }

    public void createCalendarFrame() {
        startTimeDate.setText("");
        endTimeDate.setText("");
        addTaskFrame = new JFrame("Calendar");
        addTaskFrame.setBounds(200, 200, 300, 380);
        addTaskFrame.setResizable(false);
        addTaskFrame.setVisible(true);
        addTaskPanel = new JPanel();
        addTaskPanel.setLayout(null);
        endTimeDate.setEnabled(true);
        startTimeDateLabel.setBounds(10, 20, 274, 20);
        addTaskPanel.add(startTimeDateLabel);
        startTimeDate.setBounds(9, 40, 274, 20);
        addTaskPanel.add(startTimeDate);
        endTimeDateLabel.setBounds(10, 60, 274, 20);
        addTaskPanel.add(endTimeDateLabel);
        endTimeDate.setBounds(9, 80, 274, 20);
        addTaskPanel.add(endTimeDate);
        JScrollPane jsp = new JScrollPane(calendarArea);
        jsp.setBounds(9,120,274,150);
        JLabel calLabel = new JLabel("Calendar:");
        calLabel.setBounds(10,100,274,20);
        addTaskPanel.add(calLabel);
        addTaskPanel.add(jsp);
        calendarArea.setEnabled(false);
        setCalendarButton.setBounds(40,290,100,40);
        addTaskPanel.add(setCalendarButton);
        cancelTaskButton.setBounds(150,290,100,40);
        addTaskPanel.add(cancelTaskButton);
        addTaskFrame.add(addTaskPanel);
        calendarArea.setDisabledTextColor(new Color(0,0,0));
        this.setEnabled(false);
    }
    /**
     * display dates and all repeated of tasks in said interval
     * @param sortedMap SortedMap with dates and all repeated of tasks in said interval
     */
    public void showFromToTasks(SortedMap<Date, Set<Task>> sortedMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        calendarArea.setText("");
        for(Map.Entry<Date, Set<Task>> sm : sortedMap.entrySet()){
            calendarArea.append(sdf.format(sm.getKey()) + " - ");
            int i = 0;
            for(Task st: sm.getValue()) {
                calendarArea.append(st.getTitle());
                if (i < sm.getValue().size() - 1) calendarArea.append(", "); else calendarArea.append(";\n");
                i++;
            }
        }
    }

    /**
     * display information about choosed task
     * @param tasks tasks
     * @param index index of choosed task
     */
    public void showChoosedTask(TaskList tasks, int index) {

    }

    public void showErrorMessage(String s) {
        JOptionPane.showConfirmDialog(getAddTaskFrame(),s,
                "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    public void showCurrentTask(String s) {
        JOptionPane.showConfirmDialog(this,s,
                "What time is now ?", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }
}
