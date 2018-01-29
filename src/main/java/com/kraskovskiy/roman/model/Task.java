package com.kraskovskiy.roman.model;

import com.kraskovskiy.roman.view.View;

import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * task
 * @author Roman Kraskovskiy
 */
public class Task extends TimerTask implements Cloneable, Serializable {
    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;
    private boolean repeated;
    private Timer timer;
    private Task taskRun;
    private View view;

    /**
     * @param view for show alert
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * @return view for show alert
     */
    public View getView() {
        return view;
    }

    /**
     * @param taskRun set taskRun
     */
    public void setTaskRun(Task taskRun) {
        this.taskRun = taskRun;
    }

    /**
     * @return value of taskRun
     */
    public Task getTaskRun() {
        return taskRun;
    }

    /**
     * overriding run for Timer
     */
    @Override
    public void run() {
        getTaskRun().getView().showCurrentTask("TIME FOR " + title);
        if(nextTimeAfter(new Date()) == null) {
            try {
                getTaskRun().setActive(false);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * method for starting Timer
     * @throws CloneNotSupportedException
     */
    public void startTimer() throws CloneNotSupportedException {
        if(nextTimeAfter(new Date()) != null) {
            timer = new Timer();
            Task task = this.clone();
            task.setTaskRun(this);
            if(isRepeated()) {
                timer.schedule(task, nextTimeAfter(new Date()), interval * 1000);
            } else {
                timer.schedule(task, nextTimeAfter(new Date()));
            }
        } else {
            setActive(false);
        }
    }

    /**
     * @param active set value of active
     * @throws CloneNotSupportedException
     */
    public void setActive(boolean active) throws CloneNotSupportedException {
        this.active = active;
        if(active) startTimer(); else if(timer != null) {
            timer.cancel();
        }
    }

    /**
     * @return new Task cloned from this
     * @throws CloneNotSupportedException
     */
    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task)super.clone();
    }

    /**
     * @return task in readable view
     */
    @Override
    public String toString() {
        SimpleDateFormat a = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        String reap;
        String reapTrue;
        String ac;
        if(isActive()) 
            ac = " inactive"; 
        else
            ac = "";
        if(isRepeated()) {
            reap = " from ";
            reapTrue = " to " + a.format(this.end) + " every " + intervatlToReadable(interval);
        } else {
            reap = " at ";
            reapTrue = "";
        }
        return "\"" + title.replace("\"", "\"\"") + "\"" + reap + a.format(start) + reapTrue + ac;
    }

    /**
     * @param in is interval
     * @return interval in readavle view
     */
    private String intervatlToReadable(int in) {
        int inter = in;
        int [] sec = {86400, 3600, 60};
        int [] count ={0, 0, 0, 0};
        for (int i = 0; i < 3; i++) {
            while(inter >= sec[i]) {
                inter -= sec[i];
                count[i]++;
            }
        }
        count[3] = inter;
        String [] date = {"day", "hour", "minute", "second"};
        String s = "[";
        for (int i = 0; i < 4; i++) {
            if(count[i] != 0) {
                if(i != 0 && count[i-1]!=0) s+= " ";
                s += count[i];
                if ( count[i] > 1) {
                    date[i]+="s";
                }
                s += (" " + date[i]);
            }
        }
        s+= "]";
        return s;
    }

    /**
     * empty constructor
     */
    public Task() {
    }

    /**
     * testing right of time
     * @param start start time
     * @param end end time
     * @param interval interval
     * @throws TaskException if time is not right
     */
    private void timeTest(Date start, Date end, int interval) throws TaskException {
        if (end.getTime() < start.getTime()) {
            throw new TaskException("end cannot be > start !!!");
        } else if ( interval < 1 ) {
            throw new TaskException("interval must be > 0 !!!");
        }
    }

    /**
     * constructor for creating task without repeat
     * @param title name of task
     * @param time date of task
     */
    public Task(String title, Date time) {
        this.title = title;
        this.time = time;
        this.start = time;
        this.end = time;
        this.interval = 0;
        this.repeated = false;
    }

    /**
     * constructor for creating task with repeat
     * @param title name of task
     * @param start start time
     * @param end end time
     * @param interval interval
     * @throws TaskException if time is not right
     */
    public Task(String title, Date start, Date end, int interval) throws TaskException {
        timeTest(start,end,interval);
        this.title = title;
        this.time = start;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.repeated = true;

    }

    /**
     * @return title of task
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title for setting title of task
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return active of task (true/false)
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @return date of task
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time for setting time of not repeat task
     */
    public void setTime(Date time) {
        this.time = time;
        this.start = time;
        this.end = time;
        this.repeated = false;
        this.interval = 0;
    }

    /**
     * @return value of start time
     */
    public Date getStartTime() {
        return start;
    }

    /**
     * @return valuse of end time
     */
    public Date getEndTime() {
        return end;
    }

    /**
     * @return value of interval of repeat
     */
    public int getRepeatInterval() {
        return interval;
    }

    /**
     * @param start for setting start time of task
     * @param end for setting end time of task
     * @param interval for setting interval of task
     * repeat task
     * @throws TaskException if time is not right
     */
    public void setTime(Date start, Date end, int interval) throws TaskException {
        timeTest(start,end,interval);
        this.start = start;
        this.end = end;
        this.time = start;
        this.interval = interval;
        this.repeated = true;
    }

    /**
     * @return repeat of task (true/false)
     */
    public boolean isRepeated() {
        return repeated;
    }

    /**
     * @param repeated for setting value of repeat of task
     */
    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    /**
     * @param current current time
     * @return value of time for next repeat
     */
    public Date nextTimeAfter(Date current) {
        if (!repeated && active) {
            if (current.before(time)) {
                return time;
            } else {
                return null;
            }
        } else if (repeated && active) {
            if (current.before(start)) {
                return start;
            } else if ((current.after(start) || current.equals(start)) && current.before(end)) {
                long s = start.getTime();
                while (s <= current.getTime()) {
                    s += interval*1000;
                }
                if (s > end.getTime()){
                    return null;
                }
                return new Date(s);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * method for compare tasks
     * @param object task that compare
     * @return true,if equals; and false if not equals
     */
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Task task = (Task) object;

        if (!time.equals(task.time)) return false;
        if (!start.equals(task.start)) return false;
        if (!end.equals(task.end)) return false;
        if (interval != task.interval) return false;
        if (active != task.active) return false;
        if (repeated != task.repeated) return false;
        if (!title.equals(task.title)) return false;

        return true;
    }

    /**
     * @return hashcode of task
     */
    public int hashCode() {
        int result = 0;
        result = 31 * result + title.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + start.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + interval;
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (repeated ? 1 : 0);
        return result;
    }
}