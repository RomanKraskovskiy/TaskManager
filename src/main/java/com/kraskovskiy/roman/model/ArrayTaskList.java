package com.kraskovskiy.roman.model;

import java.util.Iterator;

import java.util.Arrays;

/**
 * arraylist for tasks
 * @author Roman Kraskovskiy
 */
public class ArrayTaskList extends TaskList {
    private Task elementData[] = new Task[16];

    /**
     * @return new Iterator
     */
    public Iterator iterator() {
        return new ArrayTaskListIterator();
    }

    /**
     * inner class for iterator
     */
    class ArrayTaskListIterator implements Iterator {
        int cursor;

        /**
         * @return next element
         */
        public Task next() {
            return elementData[cursor++];
        }

        /**
         * @return true if next element exist
         */
        public boolean hasNext() {
            return cursor != size;
        }

        /**
         * remove current element
         */
        public void remove(){
            if(cursor == 0 || cursor > size) throw new IllegalStateException();
            ArrayTaskList.this.remove(getTask(cursor-1));
            cursor --;
        }
    }
    
    /**
     *@param task for adding in ArrayList
     */
    @Override
    public void add(Task task) {
        nullTest(task);
        if(size == elementData.length) {
            Task [] elemData = new Task[elementData.length];
            for(int i = 0; i < elemData.length; i++) {
                elemData[i]=elementData[i];
            }
            elementData = new Task[elementData.length * 2];
            for(int i = 0; i < elementData.length / 2; i++) {
                elementData[i]=elemData[i];
            }
        }
        elementData[size++] = task;
    }
    /**
     *@param task for find and remove this task from ArrayList
     *@return true if this task was find
     */
    @Override
    public boolean remove(Task task) {
        for(int i = 0; i < size; i++) {
            if(elementData[i].equals(task)) {
                for(int j = i; j < size-1; j++) {
                    elementData[j] = elementData[j+1];
                }
                size--;
                elementData[elementData.length - 1] = null;
                return true;
            }
        }
        return false;
    }
    /**
     *@param index for finding task that have this index
     *@return task with getting index
     */
    @Override
    public Task getTask(int index) {
        return elementData[index];
    }

    /**
     * method for display tasks in this list in readable format
     * @return all tasks in readable format
     */
    @Override
    public String toString() {
        String s = "";
        Iterator itr = iterator();
        while(itr.hasNext()) {
            Task t = (Task) itr.next();
            s += t.toString();
            if(!itr.hasNext()) s+= "\n";
        }
        return s;
    }

    /**
     * @return new TaskList cloned from this
     * @throws CloneNotSupportedException
     */
    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList atl = (ArrayTaskList)super.clone();
        atl.elementData = Arrays.copyOf(elementData, size);
        return atl;
    }
}