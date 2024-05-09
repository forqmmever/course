package com.course.pojo;

public class Log {
    private String metric;
    private String tags;
    private int timestamp;
    private float value;

    @Override
    public String toString() {
        return "Log{" +
                "metric='" + metric + '\'' +
                ", tags='" + tags + '\'' +
                ", timestamp=" + timestamp +
                ", value=" + value +
                '}';
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
