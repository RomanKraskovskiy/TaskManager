package com.kraskovskiy.roman.model;

import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Task implements Cloneable, Serializable {
    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;
    private boolean repeated;

    /*@Override
    public void run() {
        while (true){
        Date cur = new Date();
        if(cur.equals(this.nextTimeAfter(cur))) {
            System.out.println("TIME FOR " + title);
        }}
    }*/
    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task)super.clone();
    }
    
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

    public Task() {
    }
    
    private void timeTest(Date start, Date end, int interval) throws TaskException {
        if (end.getTime() < start.getTime()) {
            throw new TaskException("end cannot be > start !!!");
        } else if ( interval < 1 ) {
            throw new TaskException("interval must be > 0 !!!");
        }
    }
    
    //sozdaniye neaktivnoy zadachi BEZ povtoreniya
    public Task(String title, Date time) {
        this.title = title;
        this.time = time;
        this.start = time;
        this.end = time;
        this.interval = 0;
        this.repeated = false;
    }
    //end
    
    //sozdaniye neaktivnoy zadachi S povtoreniyem,s nachalom koncom i intrevalom
    public Task(String title, Date start, Date end, int interval) throws TaskException {
        timeTest(start,end,interval);
        this.title = title;
        this.time = start;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.repeated = true;

    }
    //end

    //metodi dlya schitovaniya i stanovleniya: nazvaniya i sostoyaniya zadachi
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    //end

    //metodi dlya schitovaniya i stanovleniya vremeni zadach BEZ povtoreniy
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
        this.start = time;
        this.end = time;
        this.repeated = false;
        this.interval = 0;
    }
    //end

    //metodi dlya schitovaniya i stanovleniya vremeni zadach S povtoreniyem
    public Date getStartTime() {
        return start;
    }

    public Date getEndTime() {
        return end;
    }

    public int getRepeatInterval() {
        return interval;
    }

    public void setTime(Date start, Date end, int interval) throws TaskException {
        timeTest(start,end,interval);
        this.start = start;
        this.end = end;
        this.time = start;
        this.interval = interval;
        this.repeated = true;
    }

    //metod dlya proverki povtoryaimosti
    public boolean isRepeated() {
        return repeated;
    }
    //end

    //metod dlya vozrasheniya vremeni sled vipolneniya zadachi
    /**
     *@return return value of time for next repeat
     *if task is done and isn't repeat, return NULL
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
    //end


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