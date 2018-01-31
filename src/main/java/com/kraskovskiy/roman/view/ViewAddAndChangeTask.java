package com.kraskovskiy.roman.view;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public abstract class ViewAddAndChangeTask {
    protected JFrame addTaskFrame = new JFrame();
    protected JPanel addTaskPanel;
    protected JLabel titleDateLabel = new JLabel("Enter title:");
    protected JTextField titleDate = new JTextField();
    protected JTextField startTimeDate = new JTextField();
    protected JTextField endTimeDate = new JTextField();
    protected JLabel startTimeDateLabel = new JLabel("Enter start date \"[yyyy-MM-dd HH:mm:ss.SSS]\":");
    protected JLabel endTimeDateLabel = new JLabel("Enter end date \"[yyyy-MM-dd HH:mm:ss.SSS]\":");
    protected JCheckBox repeatedCheck = new JCheckBox("Repeated");
    protected JButton addTaskButton = new JButton("Add");
    protected JButton cancelTaskButton = new JButton("Cancel");
    protected JLabel intervalLabel = new JLabel("Enter interval:");
    protected JTextField interval = new JTextField();
    protected JFrame mainFrame;

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * added listener for closing window
     * @param wl
     */
    public void addCloseWindowListener(WindowListener wl) {
        addTaskFrame.addWindowListener(wl);
    }

    /**
     * change enabled of fields for repeated tasks
     */
    public void setRepeatedCheck() {
        if(endTimeDate.isEnabled()) {
            endTimeDate.setEnabled(false);
            interval.setEnabled(false);
        } else {
            endTimeDate.setEnabled(true);
            interval.setEnabled(true);
        }
    }

    /**
     * add listener for checkbox for change repeated Task
     * @param al
     */
    public void addCheckRepeatedListener(ActionListener al) {
        repeatedCheck.addActionListener(al);
    }



    /**
     * add listener for close frame
     * @param al
     */
    public void addCancelButtonListener(ActionListener al) {
        cancelTaskButton.addActionListener(al);
    }

    /**
     * @return title from field
     */
    public String getTitleFromField() {
        return titleDate.getText();
    }

    /**
     * @return start date from field
     */
    public String getStartDateFromField() {
        return startTimeDate.getText();
    }

    /**
     * @return end date from field
     */
    public String getEndDateFromField() {
        return endTimeDate.getText();
    }

    /**
     * @return interval from field
     */
    public int getIntervalFromField() {
        return Integer.parseInt(interval.getText());
    }

    /**
     * @return current frame(add or change)
     */
    public JFrame getAddTaskFrame() {
        return addTaskFrame;
    }

    /**
     * @return repeated from checkbox
     */
    public boolean isRepeatedFromField () {
        return repeatedCheck.isSelected();
    }

    /**
     * create pattern for add or change task frame
     */
    public void createFrame() {
        titleDate.setText("");
        startTimeDate.setText("");
        endTimeDate.setText("");
        interval.setText("");
        addTaskFrame = new JFrame("Task");
        addTaskFrame.setBounds(200, 200, 300, 320);
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
        mainFrame.setEnabled(false);
    }

    public abstract void newFrame();
}
