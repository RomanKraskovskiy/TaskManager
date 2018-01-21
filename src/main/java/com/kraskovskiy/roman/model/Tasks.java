package com.kraskovskiy.roman.model;

import java.util.*;

/**
 * tasks that are incoming in said interval
 * @author Roman Kraskovskiy
 */
public class Tasks {
    /**
     * @param tasks tasks
     * @param from start date of interval
     * @param to end date of interval
     * @return tasks that are incoming in said interval
     * @throws CloneNotSupportedException
     */
    public static Iterable incoming(Iterable tasks,Date from, Date to) throws CloneNotSupportedException {
        Iterator itr = tasks.iterator();
        while(itr.hasNext()) {
            Task t = (Task)itr.next();
            if(t.nextTimeAfter(from) == null ||
            (t.nextTimeAfter(from).after(to))) {
               itr.remove();
            }
        }
        return tasks;
    }

    /**
     * @param tasks tasks
     * @param start start date of interval
     * @param end end date of interval
     * @return SortedMap with dates and all repeated of tasks in said interval
     * @throws CloneNotSupportedException
     */
    public static SortedMap<Date, Set<Task>> calendar(Iterable tasks, Date start, Date end) throws CloneNotSupportedException {
        tasks = incoming(tasks,start,end);
        SortedMap<Date, Set<Task>> sortedMap = new TreeMap<Date, Set<Task>>();
        Iterator itr = tasks.iterator();
        while(itr.hasNext()) {
            Task t = (Task)itr.next();
            if(t.isActive()) {
                for (long start1 = t.nextTimeAfter(start).getTime(); start1 <= end.getTime(); start1 += t.getRepeatInterval() * 1000) {
                    if (sortedMap.containsKey(new Date(start1))) {
                        sortedMap.get(new Date(start1)).add(t);
                    } else {
                        Set tasksHash = new HashSet();
                        tasksHash.add(t);
                        sortedMap.put(new Date(start1), tasksHash);
                    }
                    if (t.getRepeatInterval() == 0) break;

                }
            }
        }
        return sortedMap;
    }
}