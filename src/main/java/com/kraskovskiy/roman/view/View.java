package com.kraskovskiy.roman.view;

import com.kraskovskiy.roman.model.Task;
import com.kraskovskiy.roman.model.TaskList;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * view of mvc
 * @author Roman Kraskovskiy
 */
public class View {

    /**
     * display all tasks on screen
     * @param tasks tasks
     */
    public void showAllTask(TaskList tasks) {
        System.out.println("\nYour tasks:");
        System.out.println(tasks.toString());
    }

    /**
     * display existing actions,menu items
     */
    public void showMenu() {
        System.out.println("Select an action:");
        System.out.println("1) Choose interval for calendar");
        System.out.println("2) Add task");
        System.out.println("3) Choose and view task");
        System.out.println("4) Exit");
    }

    /**
     * display dates and all repeated of tasks in said interval
     * @param sortedMap SortedMap with dates and all repeated of tasks in said interval
     */
    public void showFromToTasks(SortedMap<Date, Set<Task>> sortedMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        System.out.println("\nCalendar:");
        for(Map.Entry<Date, Set<Task>> sm : sortedMap.entrySet()){
            System.out.print(sdf.format(sm.getKey()) + " - ");
            int i = 0;
            for(Task st: sm.getValue()) {
                System.out.print(st.getTitle());
                if (i < sm.getValue().size() - 1) System.out.print(", "); else System.out.println(";");
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
        System.out.println("\n" + tasks.getTask(index).toString());
        System.out.println("Select an action:");
        System.out.println("1) Remove task");
        System.out.println("2) Change title");
        System.out.println("3) Change time");
        System.out.println("4) Change inactive/noactive");
        System.out.println("5) Change repeated/norepeated");
        System.out.println("6) Back to menu");
    }
}
