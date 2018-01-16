package com.kraskovskiy.roman.model;

import java.util.Date;
import java.io.Serializable;

public abstract class TaskList implements Iterable, Cloneable, Serializable {
    protected int size = 0;
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);
    
    public TaskList clone() throws CloneNotSupportedException  {
        return (TaskList)super.clone();
    }
    
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TaskList tl = (TaskList)object;
        if(this.size() != tl.size()) return false;
        
        int acc = 0;
        for(int i = 0; i < size; i++) {
            if(getTask(i).equals(tl.getTask(i))){
                acc++;
            }
        }
        if(acc != size) return false;
        return true;      
        
    }
    
    public int hashCode() {
        int result = 0;
        for(int i = 0; i < size; i++) {
            result = 31*result + getTask(i).hashCode();
        }
        return result;
    }

    /**
     *@return count of tasks in ArrayList
     */
    public int size() {
        return size;
    }
    
    protected void nullTest(Task task) throws NullPointerException{
        if (task == null) {
            throw new NullPointerException();
        }
    }
    /**
     * start and end of period
     *@return tasks that was plan in this period
     */
    public TaskList incoming(Date from, Date to) throws InstantiationException
        ,IllegalAccessException{
        TaskList tasks = this.getClass().newInstance();
        for(int i = 0; i < size; i++) {
            if(getTask(i).nextTimeAfter(from) != null &&
            getTask(i).nextTimeAfter(from).before(to)){
                tasks.add(getTask(i));
            }
        }
        
        return tasks;
    }
    
}