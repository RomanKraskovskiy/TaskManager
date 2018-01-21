package com.kraskovskiy.roman.controller;

import com.kraskovskiy.roman.model.*;
import com.kraskovskiy.roman.view.View;
import org.apache.log4j.Logger;

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
    }

    /**
     * control of viewing of all tasks
     * @throws ParseException
     */
    public void updateView() throws ParseException {
        view.showAllTask(taskList);
    }

    /**
     * control all program processes
     * @throws ParseException
     * @throws CloneNotSupportedException
     * @throws IOException
     * @throws TaskOutputException
     * @throws TaskException
     */
    public void menu() throws ParseException, CloneNotSupportedException, IOException, TaskOutputException, TaskException {
        int choosed;
        updateView();
        view.showMenu();
        Scanner scanner = new Scanner(System.in);
        try {
            choosed = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("no correct format, it must be number");
            logger.info("USER: " + e + " | no correct format, it must be number");
            return;
        }
        switch (choosed) {
            case 1:
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
                    Date s;
                    Date e;
                    System.out.println("Enter start date in format \"[yyyy-MM-dd HH:mm:ss.SSS]\":");
                    scanner.nextLine();
                    s = sdf.parse(scanner.nextLine());
                    System.out.println("Enter end date in format \"[yyyy-MM-dd HH:mm:ss.SSS]\":");
                    e = sdf.parse(scanner.nextLine());
                    TaskList tsForCalendar = taskList.clone();
                    SortedMap<Date, Set<Task>> sortedMap = Tasks.calendar(tsForCalendar, s, e);
                    view.showFromToTasks(sortedMap);
                } catch (ParseException e) {
                    System.out.println("no correct format");
                    logger.info("USER: " + e + " | no correct format for parse date");
                }
                break;
            case 2:
                setTask();
                break;
            case 3:
                System.out.println("Enter number of task:");
                try {
                    int taskNumb = scanner.nextInt();
                    int choosedCurTask;
                    boolean m = true;
                    while (m) {
                        view.showChoosedTask(taskList, taskNumb);
                        choosedCurTask = scanner.nextInt();
                        switch (choosedCurTask) {
                            case 1:
                                removeTask(taskNumb);
                                m = false;
                                break;
                            case 2:
                                changeTitle(taskNumb);
                                break;
                            case 3:
                                changeTime(taskNumb);
                                break;
                            case 4:
                                changeActive(taskNumb);
                                break;
                            case 5:
                                changeRepeat(taskNumb);
                                changeTime(taskNumb);
                                break;
                            case 6:
                                m = false;
                                break;
                        }

                    }
                }catch (InputMismatchException e) {
                    System.out.println("it must be number");
                    logger.info("USER: " + e + " | it must be number");
                }catch (NullPointerException e) {
                    System.out.println("index out of array");
                    logger.info("USER: " + e + " | index out of array");
                }catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("index out of array");
                    logger.info("USER: " + e + " | index out of array(null)");
                }
                break;
            case 4:
                TaskIO.writeBinary(taskList, new File("tasks.txt"));
                System.exit(0);
                break;
        }
    }

    /**
     * change Active of choosed task
     * @param taskNumb index of task
     * @throws CloneNotSupportedException
     */
    public  void changeActive(int taskNumb) throws CloneNotSupportedException {
        taskList.getTask(taskNumb).setActive(!taskList.getTask(taskNumb).isActive());
    }

    /**
     * change Repeated of choosed task
     * @param taskNumb index of task
     * @throws CloneNotSupportedException
     */
    public  void changeRepeat(int taskNumb) throws CloneNotSupportedException {
        taskList.getTask(taskNumb).setRepeated(!taskList.getTask(taskNumb).isRepeated());
    }

    /**
     * change Time of choosed task
     * @param taskNumb index of task
     * @throws ParseException
     * @throws TaskException
     */
    public void changeTime(int taskNumb) throws ParseException, TaskException {
        Scanner scanner = new Scanner(System.in);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
            if (taskList.getTask(taskNumb).isRepeated()) {
                Date s;
                Date e;
                int i;
                System.out.println("Enter start date in format \"[yyyy-MM-dd HH:mm:ss.SSS]\":");
                s = sdf.parse(scanner.nextLine());
                System.out.println("Enter end date in format \"[yyyy-MM-dd HH:mm:ss.SSS]\":");
                e = sdf.parse(scanner.nextLine());
                System.out.println("Enter interval in seconds:");
                i = scanner.nextInt();
                taskList.getTask(taskNumb).setTime(s, e, i);
            } else {
                Date d;
                System.out.println("Enter date in format \"[yyyy-MM-dd HH:mm:ss.SSS]\":");
                d = sdf.parse(scanner.nextLine());
                taskList.getTask(taskNumb).setTime(d);
            }
        } catch (ParseException e) {
            System.out.println("no correct format");
            logger.info("USER: " + e + " | no correct format for parse date");
        }
    }

    /**
     * change Title of choosed task
     * @param taskNumb index of task
     */
    public void changeTitle(int taskNumb) {
        System.out.println("Enter new title:");
        taskList.getTask(taskNumb).setTitle(new Scanner(System.in).next());
    }

    /**
     * remove choosed task
     * @param taskNumb index of task
     */
    public void removeTask(int taskNumb) {
        taskList.remove(taskList.getTask(taskNumb));
    }

    /**
     * add new Task in model(tasklist)
     * @throws ParseException
     * @throws TaskException
     * @throws CloneNotSupportedException
     */
    public void setTask() throws ParseException, TaskException, CloneNotSupportedException {
        boolean rep;
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        Task task = new Task();
        System.out.println("New task:");
        System.out.println("Enter title for task:");
        task.setTitle(scanner.nextLine());
        System.out.println("Repeated(true/false):");
        try {
            rep = scanner.nextBoolean();
        } catch (InputMismatchException e) {
            System.out.println("no correct format, only \"true\" or \"false\"");
            logger.info("USER: " + e + " | no correct format, only \"true\" or \"false\"");
            return;
        }
        try {
            if (rep) {
                Date s;
                Date e;
                int i;
                System.out.println("Enter start date in format \"[yyyy-MM-dd HH:mm:ss.SSS]\":");
                scanner.nextLine();
                s = sdf.parse(scanner.nextLine());
                System.out.println("Enter end date in format \"[yyyy-MM-dd HH:mm:ss.SSS]\":");
                e = sdf.parse(scanner.nextLine());
                System.out.println("Enter interval in seconds:");
                i = scanner.nextInt();
                task.setTime(s, e, i);
            } else {
                Date d;
                System.out.println("Enter date in format \"[yyyy-MM-dd HH:mm:ss.SSS]\":");
                scanner.nextLine();
                d = sdf.parse(scanner.nextLine());
                task.setTime(d);
            }
        }catch (ParseException e) {
            System.out.println("no correct format");
            logger.info("USER: " + e + " | no correct format for parse date");
            return;
        }
        task.setActive(true);
        taskList.add(task);
    }
}
