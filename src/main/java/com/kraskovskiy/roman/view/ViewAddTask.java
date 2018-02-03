package com.kraskovskiy.roman.view;

import java.awt.event.ActionListener;

/**
 * view for add task
 * @author Roman Kraskovskiy
 */
public class ViewAddTask extends ViewAddAndChangeTask {

    /**
     * create frame for adding task
     */
    @Override
    public void newFrame() {
        createFrame();
        addTaskButton.setBounds(40,210,100,40);
        addTaskPanel.add(addTaskButton);
    }

    /**
     * add listener for button for add Task
     * @param al
     */
    public void addTaskButtonListener(ActionListener al) {
        addTaskButton.addActionListener(al);
    }

}
