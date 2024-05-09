package com.course.pojo;

import java.util.Date;

public class Log {
    private int id;
    private String metric;
    private String tags;
    private int timestamp;
    private float value;
    private String description;
    private Date time;

    @Override
    public String toString() {
        String to = "Log{" +
                "metric='" + metric + '\'' +
                ", tags='" + tags + '\'' +
                ", timestamp=" + timestamp +
                ", value=" + value;
        to += description != null? ", description='" + description + '\'' +
                ", time=" + time +
                '}':'}';
        return to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }
}
