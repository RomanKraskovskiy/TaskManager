package com.kraskovskiy.roman.model;

import java.io.*;
import java.util.Iterator;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * input/output
 * @author Roman Kraskovskiy
 */
public class TaskIO {
    /**
     * write tasks in stream in binary format
     * @param tasks tasks for writing
     * @param out stream for writing
     * @throws IOException
     * @throws TaskOutputException
     */
    public static void write(TaskList tasks, OutputStream out) throws IOException, TaskOutputException {
        Iterator itr = tasks.iterator();
        try {
            DataOutputStream dataOut = new DataOutputStream(out);
            try {
            dataOut.write(tasks.size());
                while(itr.hasNext()) {
                    Task t = (Task)itr.next();
                    dataOut.write(t.getTitle().length());
                    dataOut.write(t.getTitle().getBytes());
                    if(t.isActive()) {
                        dataOut.write(1);
                    } else {
                        dataOut.write(0);
                    }
                    dataOut.writeInt(t.getRepeatInterval());
                    if(t.isRepeated()) {
                        dataOut.writeLong(t.getStartTime().getTime());
                        dataOut.writeLong(t.getEndTime().getTime());
                    } else {
                        dataOut.writeLong(t.getTime().getTime());
                    }
                }  
            }finally {
                dataOut.close();
            }
        }catch (IOException e){
            throw new TaskOutputException(e.getMessage(),e);
        }
    }

    /**
     * read tasks from stream in binary format
     * @param tasks tasks for reading
     * @param out stream for reading
     * @throws IOException
     * @throws TaskInputException
     * @throws TaskException
     * @throws CloneNotSupportedException
     */
    public static void read(TaskList tasks, InputStream out) throws IOException, TaskInputException, TaskException, CloneNotSupportedException {
        try {
            DataInputStream dataOut = new DataInputStream(out);
            try {
                int count = dataOut.read();
                for(int i = 0; i < count; i++) {
                    Task t = new Task();
                    int leng = dataOut.read();
                    char [] mass = new char[leng];
                    for(int j = 0; j < leng; j++) {
                        mass[j] = (char)dataOut.read();
                    }
                    t.setTitle(new String(mass));
                    boolean acTive;
                    if (dataOut.read() == 1) {
                        acTive = true;
                    } else {
                        acTive = false;
                        //t.setActive(false);
                    }
                    int inter = dataOut.readInt();
                    if (inter != 0) {
                        t.setTime(new Date(dataOut.readLong()),new Date(dataOut.readLong()),inter);
                    } else {
                        t.setTime(new Date(dataOut.readLong()));
                    }
                    t.setActive(acTive);
                    tasks.add(t);
                }
            }finally {
                dataOut.close();
            }
        }catch (FileNotFoundException e){
            throw new TaskInputException(e.getMessage(),e);
        }catch (IOException e){
            throw new TaskInputException(e.getMessage(),e);
        }
    }

    /**
     * write tasks in file in binary format
     * @param tasks tasks for writing
     * @param file file for writing
     * @throws IOException
     * @throws TaskOutputException
     */
    public static void writeBinary(TaskList tasks, File file) throws IOException, TaskOutputException {
        try {
            OutputStream outFile = new FileOutputStream(file);
            try {
                write(tasks,outFile);
            }finally{
                outFile.close();
            }
        }catch (IOException e){
            throw new TaskOutputException(e.getMessage(),e);
        }
    }

    /**
     * read tasks from file in binary format
     * @param tasks tasks for reading
     * @param file file for reading
     * @throws IOException
     * @throws TaskInputException
     * @throws TaskException
     * @throws CloneNotSupportedException
     */
    public static void readBinary(TaskList tasks, File file) throws IOException, TaskInputException, TaskException, CloneNotSupportedException {
        try {
            InputStream outFile = new FileInputStream(file);
            try {
                read(tasks,outFile);
            }finally {
                outFile.close();
            }
        }catch (FileNotFoundException e){
            throw new TaskInputException(e.getMessage(),e);
        }catch (IOException e){
            throw new TaskInputException(e.getMessage(),e);
        }
    }

    /**
     * write tasks in Writer in readable(text) format
     * @param tasks tasks for writing
     * @param out writer for writing
     * @throws IOException
     * @throws TaskOutputException
     */
    public static void write(TaskList tasks, Writer out) throws IOException, TaskOutputException {
        Iterator itr = tasks.iterator();
        try {
            while(itr.hasNext()) {
                Task t = (Task)itr.next();
                String end;
                if (itr.hasNext()) {
                    end = ";";
                } else {
                    end = ".";
                }
                out.write(t.toString() + end);
            }
        }catch (IOException e){
            throw new TaskOutputException(e.getMessage(),e);
        }finally {
            out.close();
        }
    }

    /**
     * read tasks from Reader in readable(text) format
     * @param tasks tasks for reading
     * @param in reader for reading
     * @throws IOException
     * @throws ParseException
     * @throws TaskInputException
     * @throws TaskException
     * @throws CloneNotSupportedException
     */
    public static void read(TaskList tasks, Reader in) throws IOException, ParseException, TaskInputException, TaskException, CloneNotSupportedException {
        SimpleDateFormat a = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        Date date;
        try {
            int data = in.read();
            while (data != '.') {
                data = in.read();
                String s = "";
                Task t = new Task();
                while(data != '"') {
                    while(data != '"') {
                        s += (char)data;
                        data = in.read();
                    }
                    data = in.read();
                    if (data == ' ') {
                        break;
                    } else {
                        s += "\"";
                        data = in.read();
                    }
                }
                t.setTitle(s);
                s = "";
                while(data != 'a' || data != 'f') {
                    data = in.read();
                    if(data == 'a') {
                        while(data != ' ') {
                            data = in.read();
                        }
                        while(data != ']') {
                            data = in.read();
                            s += (char)data;
                        }
                        date = a.parse(s);
                        t.setTime(date);
                        break;
                    } else if ( data == 'f') {
                        Date st,ed;
                        s = "";
                        while(data != ' ') {
                            data = in.read();
                        }
                        while(data != ']') {
                            data = in.read();
                            s += (char)data;
                        }
                        st = a.parse(s);
                        s = "";
                        while(data != 't') {
                            data = in.read();
                        }
                        
                        while(data != ' ') {
                            data = in.read();
                        }
                        while(data != ']') {
                            data = in.read();
                            s += (char)data;
                        }
                        ed = a.parse(s);
                        while(data != '[') {
                            data = in.read();
                        }
                        
                        int inter = 0;
                        String sumstr = "";
                        int sum = 0;
                        while(data != ']') {
                            data = in.read();
                            while(data != ' ') {
                                sumstr += (char)data;
                                data = in.read();
                            }
                            sum = Integer.parseInt(sumstr);
                            data = in.read();
                            if (data == 'd') {
                                sum *= 86400;
                            } else if (data == 'h') {
                                sum *= 3600;
                            } else if (data == 'm') {
                                sum *= 60;
                            }
                            
                            inter += sum;
                            while(data != ' ' && data != ']') {
                                data = in.read();
                            }
                            sum = 0;
                        }
                        t.setTime(st,ed,inter);
                        break;
                    }
                    
                }
                while(data != 'i' && data != ';' && data != '.') {
                    data = in.read();
                    if (data == 'i') {
                        t.setActive(true);
                        while (data != ';' && data != '.'){
                            data = in.read();
                        }
                    } else if (data == ';' && data == '.') {
                        t.setActive(false);
                    }
                }
                if(data != '.') data = in.read();
                tasks.add(t);
            }
        }catch (IOException e){
            throw new TaskInputException(e.getMessage(),e);
        }finally {
            in.close();
        }
    }

    /**
     * write tasks in file in readable(text) format
     * @param tasks tasks for writing
     * @param file file fot writing
     * @throws IOException
     * @throws TaskOutputException
     */
    public static void writeText(TaskList tasks, File file) throws IOException, TaskOutputException {
        try {
            Writer wr = new FileWriter(file);
            try {
                write(tasks,wr);
            }finally{
                wr.close();
            }
        }catch (IOException e){
            throw new TaskOutputException(e.getMessage(),e);
        }
    }

    /**
     * read tasks from file in readable(text) format
     * @param tasks tasks for reading
     * @param file file for reading
     * @throws IOException
     * @throws ParseException
     * @throws TaskInputException
     * @throws TaskException
     * @throws CloneNotSupportedException
     */
    public static void readText(TaskList tasks, File file) throws IOException, ParseException, TaskInputException, TaskException, CloneNotSupportedException {
        try {
            Reader rd = new FileReader(file);
            try {
                read(tasks,rd);
            }finally {
                rd.close();
            }
        }catch (FileNotFoundException e){
            throw new TaskInputException(e.getMessage(),e);
        }catch (IOException e){
            throw new TaskInputException(e.getMessage(),e);
        }
    }
}