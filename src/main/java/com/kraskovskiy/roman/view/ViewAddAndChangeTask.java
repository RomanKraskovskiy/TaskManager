package com.kraskovskiy.roman.view;

import com.kraskovskiy.roman.model.Task;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.SortedMap;

public abstract class ViewAddAndChangeTask {

    protected JFrame addTaskFrame = new JFrame();
    protected JPanel addTaskPanel;
    protected JLabel titleDateLabel = new JLabel("Enter title:");
    protected JTextField titleDate = new JTextField();
    protected JLabel startTimeDateLabel = new JLabel("Enter start date \"[yyyy-MM-dd HH:mm:ss.SSS]\":");
    protected JLabel endTimeDateLabel = new JLabel("Enter end date \"[yyyy-MM-dd HH:mm:ss.SSS]\":");
    protected JCheckBox repeatedCheck = new JCheckBox("Repeated");
    protected JCheckBox activeCheck = new JCheckBox("Active");

    protected SpinnerDateModel modelStart = new SpinnerDateModel();
    protected SpinnerDateModel modelEnd = new SpinnerDateModel();
    protected JSpinner startTimeDate = new JSpinner(modelStart);
    protected JSpinner endTimeDate = new JSpinner(modelEnd);
    protected JSpinner.DateEditor timeStartEditor = new JSpinner.DateEditor(startTimeDate,
            "yyyy-MM-dd HH:mm:ss.SSS");
    protected JSpinner.DateEditor timeEndEditor = new JSpinner.DateEditor(endTimeDate,
            "yyyy-MM-dd HH:mm:ss.SSS");

    protected Calendar calendar = Calendar.getInstance();
    protected SpinnerDateModel modelInterval = new SpinnerDateModel();
    protected JSpinner interval = new JSpinner(modelInterval);
    protected JSpinner.DateEditor timeIntervalEditor = new JSpinner.DateEditor(interval,
            "HH:mm:ss");
    protected DateFormatter dateFormatter = (DateFormatter) timeIntervalEditor.getTextField().getFormatter();

    protected JTextField countOfDays = new JTextField();
    protected JLabel countOfDaysLabel = new JLabel("days");

    protected JButton addTaskButton = new JButton("Add");
    protected JButton cancelTaskButton = new JButton("Cancel");
    protected JLabel intervalLabel = new JLabel("Enter interval:");
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
     * @return active from checkbox
     */
    public boolean isActiveFromField () {
        return activeCheck.isSelected();
    }

    /**
     * change enabled of fields for repeated tasks
     */
    public void setRepeatedCheck() {
        if(endTimeDate.isEnabled()) {
            endTimeDate.setEnabled(false);
            interval.setEnabled(false);
            countOfDays.setEnabled(false);
        } else {
            endTimeDate.setEnabled(true);
            interval.setEnabled(true);
            countOfDays.setEnabled(true);
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
    public Date getStartDateFromField() {
        return (Date) startTimeDate.getValue();
    }

    /**
     * @return end date from field
     */
    public Date getEndDateFromField() {
        return (Date) endTimeDate.getValue();
    }

    /**
     * @return interval from field
     */
    public int getIntervalFromField() {
        Date inter = (Date) interval.getValue();
        return Integer.parseInt(countOfDays.getText()) * 86400 +
                inter.getHours() * 3600 + inter.getMinutes() * 60 + inter.getSeconds();
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
        countOfDays.setText("0");
        startTimeDate.setEditor(timeStartEditor);
        endTimeDate.setEditor(timeEndEditor);
        startTimeDate.setValue(new Date());
        endTimeDate.setValue(new Date());

        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        modelInterval.setValue(calendar.getTime());
        dateFormatter.setAllowsInvalid(false);
        dateFormatter.setOverwriteMode(true);
        interval.setEditor(timeIntervalEditor);


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
        countOfDays.setBounds(9,170,70,20);
        addTaskPanel.add(countOfDays);
        countOfDaysLabel.setBounds(84,170,44,20);
        addTaskPanel.add(countOfDaysLabel);
        interval.setBounds(123, 170, 160, 20);
        if (!repeatedCheck.isSelected()){
            endTimeDate.setEnabled(false);
            interval.setEnabled(false);
            countOfDays.setEnabled(false);
        }
        addTaskPanel.add(interval);
        cancelTaskButton.setBounds(150,210,100,40);
        addTaskPanel.add(cancelTaskButton);
        addTaskFrame.add(addTaskPanel);
        mainFrame.setEnabled(false);
    }

    public abstract void newFrame();

    public void showFromToTasks(SortedMap<Date, Set<Task>> sortedMap) {
        /*
        for overriding
         */
    }
}
