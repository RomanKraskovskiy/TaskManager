package com.kraskovskiy.roman.model;

import java.util.Date;
import java.io.Serializable;

/**
 * abstract list for tasks
 * model of mvc
 * @author Roman Kraskovskiy
 */
public abstract class TaskList implements Iterable, Cloneable, Serializable {
    protected int size = 0;
    public abstract void add(Task task);
    public abstract boolean remove(Task task);
    public abstract Task getTask(int index);

    /**
     * @return new TaskList cloned from this
     * @throws CloneNotSupportedException
     */
    @Override
    public TaskList clone() throws CloneNotSupportedException  {
        return (TaskList)super.clone();
    }

    /**
     * method for compare tasklists
     * @param object tasklist that compare
     * @return true,if equals; and false if not equals
     */
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

    /**
     * @return hashcode of tasks
     */
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

    /**
     * @param task task that are null testing
     * @throws NullPointerException
     */
    protected void nullTest(Task task) throws NullPointerException{
        if (task == null) {
            throw new NullPointerException();
        }
    }
    
}