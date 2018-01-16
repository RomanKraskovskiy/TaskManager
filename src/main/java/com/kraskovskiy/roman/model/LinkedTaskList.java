package com.kraskovskiy.roman.model;

import java.util.Iterator;

public class LinkedTaskList extends TaskList {
    private Node first;
    private Node last;
    
    public Iterator iterator() {
        return new LinkedTaskListIterator();
    }
    
    class LinkedTaskListIterator implements Iterator {
        int cursor;
        Node curNode;
        public Task next() {
            if(cursor == 0) {
                cursor++;
                curNode = first;
                return curNode.task;
            }else {
                cursor++;
                curNode = curNode.next;
                return curNode.task;
            }
        } 
        public boolean hasNext() {
            return cursor != size;
        }
        public void remove() {
            if(cursor == 0 || cursor >= size) throw new IllegalStateException();
            LinkedTaskList.this.remove(curNode.task);
            cursor--;
        }
    }
    
    private void testIndex(int index) throws ArrayIndexOutOfBoundsException {
        if (index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    /**
     *class Node is container fot tasks with links to next and prev elements
     */
    private class Node {
        Task task;
        Node next;
        Node prev;
        public Node(Task task) {
            this.task = task;
            if(size == 0){
                next = null;
                prev = null;
            }else if(size == 1){
                next = null;
                prev = first;
                first.next = this;
            }else{
                last.next = this;
                prev = last;
                next = null;
               
            }
        }
    }
    /**
     *@param task for adding in LinkedList
     */
    @Override
    public void add(Task task) {
        nullTest(task);
        if (first != null) {
            last = new Node(task);
        } else {
            first = new Node(task);
        }
        size++;
    }
    /**
     *@param index for finding task that have this index
     *@return task with getting index
     */
    @Override
    public Task getTask(int index) {
        testIndex(index);
        Node node;
        if(size / 2 > index){
            node = first;
            if(index == 0) {
                return node.task;
            }
            for(int i = 1; i <= index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            if(index == size-1) {
                return node.task;
            }
            for(int i = size-2; i >= index; i--) {
                node = node.prev;
            }
        }
        return node.task;
    }

    /**
     *@param task for find and remove this task from LinkedList
     *@return true if this task was find
     */
    @Override
    public boolean remove(Task task) {
            Node node = first;
            if (node.task.equals(task)){
                if(node.next!=null)
                node.next.prev = null;
                first = node.next;
                size--;
                return true;
            }
            while(node != null) {
                if (node.task.equals(task)) {
                    node.prev.next = node.next;
                    if(node.next != null){
                        node.next.prev = node.prev;
                    }else {
                        last = node.prev;
                    }
                    size--;
                    return true;
                }
                node = node.next;
            }
        return false;
    }
    
    public String toString() {
        LinkedTaskListIterator itr = (LinkedTaskListIterator)iterator();
        int i = 0;
        while(itr.hasNext()){
            System.out.print(i + ") ");
            System.out.println(itr.next().toString());
            i++;
        }
        return "Count of tasks: " + size;
    }
    
    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList atl = (LinkedTaskList)super.clone();
        atl.size = 0;
        LinkedTaskListIterator itr = (LinkedTaskListIterator)iterator();
        while(itr.hasNext()){
            atl.add((Task)itr.next());
        }
        return atl;
    }
}