package com.course.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Map;

public class WarningLog {

    private int id;
    private String metric;
    private Map<String,String> tags;
    private int timestamp;
    private String description;
    private float value;
    private Date time;

    public WarningLog(String metric, Map<String, String> tags, int timestamp, float value, String description, Date time) {
        this.id = id;
        this.metric = metric;
        this.tags = tags;
        this.timestamp = timestamp;
        this.value = value;
        this.description = description;
        this.time = time;
    }

    public WarningLog(PostLog postLog, String description, Date time){
        this.id = id;
        this.metric = postLog.getMetric();
        this.tags = postLog.getTags();
        this.timestamp = postLog.getTimestamp();
        this.value = postLog.getValue();
        this.description = description;
        this.time = time;
    }

    public String getTagsJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(tags);
    }
    @Override
    public String toString() {
        return "WarningLog{" +
                "id=" + id +
                ", metric='" + metric + '\'' +
                ", tags=" + tags +
                ", timestamp=" + timestamp +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", time=" + time +
                '}';
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

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
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
}
