package com.kraskovskiy.roman.view;

import com.kraskovskiy.roman.model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class ViewCalendar extends ViewAddAndChangeTask {

    private JButton setCalendarButton = new JButton("Calendar");
    private JTextArea calendarArea = new JTextArea();
    /**
     * create frame for view calendar
     */
    @Override
    public void newFrame() {
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
        mainFrame.setEnabled(false);
    }

    /**
     * add listener for button for set calendar
     * @param al
     */
    public void addSetCalendarButtonListener(ActionListener al) {
        setCalendarButton.addActionListener(al);
    }

    /**
     * display dates and all repeated of tasks in said interval
     * @param sortedMap SortedMap with dates and all repeated of tasks in said interval
     */
    @Override
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
}
