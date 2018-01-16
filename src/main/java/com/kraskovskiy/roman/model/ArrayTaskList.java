package com.kraskovskiy.roman.model;

import java.util.Iterator;

import java.util.Arrays;

public class ArrayTaskList extends TaskList {
    private Task elementData[] = new Task[16];
    
    public Iterator iterator() {
        return new ArrayTaskListIterator();
    }
    
    class ArrayTaskListIterator implements Iterator {
        int cursor;
        public Task next() {
            return elementData[cursor++];
        }
        public boolean hasNext() {
            return cursor != size;
        }
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

    @Override
    public String toString() {
        for(int i = 0; i < size; i++) {
            System.out.print(i + ") ");
            System.out.println(getTask(i).toString());
        }
        return "Count of tasks: " + size;
    }
    
    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList atl = (ArrayTaskList)super.clone();
        atl.elementData = Arrays.copyOf(elementData, size);
        return atl;
    }
}