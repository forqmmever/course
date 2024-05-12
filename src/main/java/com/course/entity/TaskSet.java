package com.course.entity;

import java.util.Date;

public class TaskSet {
    int interval;
    Date date1;
    Date date2;

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    @Override
    public String toString() {
        return "TaskSet{" +
                "interval=" + interval +
                ", date1=" + date1 +
                ", date2=" + date2 +
                '}';
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }
}
