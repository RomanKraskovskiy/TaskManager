package com.kraskovskiy.roman.view;

import com.kraskovskiy.roman.model.Task;
import com.kraskovskiy.roman.model.TaskList;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewChangeTask extends ViewAddAndChangeTask {
    private JButton removeTaskButton = new JButton("Remove");
    private JButton changeTaskButton = new JButton("Change");

    private TaskList taskList;
    private int index;

    public void setTaskListandIndex(TaskList taskList,int index) {
        this.taskList = taskList;
        this.index = index;
    }
    /**
     * add listener for button for changing Task
     * @param al
     */
    public void addChangeButtonListener(ActionListener al) {
        changeTaskButton.addActionListener(al);
    }

    /**
     * add listener for button for remove Task
     * @param al
     */
    public void addRemoveButtonListener(ActionListener al) {
        removeTaskButton.addActionListener(al);
    }

    @Override
    public void newFrame() {
        createFrame();
        activeCheck.setBounds(110, 90, 90, 20);
        addTaskPanel.add(activeCheck);
        changeTaskButton.setBounds(40,210,100,40);
        addTaskPanel.add(changeTaskButton);
        removeTaskButton.setBounds(40,260,210,15);
        addTaskPanel.add(removeTaskButton);
        Task task;
        task = taskList.getTask(index);
        titleDate.setText(task.getTitle());
        startTimeDate.setValue(task.getStartTime());
        if(task.isActive()) {
            activeCheck.setSelected(true);
        } else {
            activeCheck.setSelected(false);
        }
        if(task.isRepeated()) {
            endTimeDate.setEnabled(true);
            interval.setEnabled(true);
            repeatedCheck.setSelected(true);
            endTimeDate.setValue(task.getEndTime());
            interval.setValue(new Date(task.getRepeatInterval()));
        } else {
            endTimeDate.setEnabled(false);
            interval.setEnabled(false);
            repeatedCheck.setSelected(false);
            endTimeDate.setValue(new Date());
            interval.setValue(new Date(0));
        }
    }
}
