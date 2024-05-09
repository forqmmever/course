package com.course.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class PostLog {
    private String metric;
    private Map<String,String> tags;
    private int timestamp;
    private float value;

    public PostLog(String metric, Map<String, String> tags, int timestamp, float value) {
        this.metric = metric;
        this.tags = tags;
        this.timestamp = timestamp;
        this.value = value;
    }

    public String getTagsJson() throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(tags);
    }

    @Override
    public String toString() {
        return "PostLog{" +
                "metric='" + metric + '\'' +
                ", tags=" + tags +
                ", timestamp='" + timestamp + '\'' +
                ", value=" + value +
                '}';
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public Map<String,String> getTags() {
        return tags;
    }

    public void setTags(Map<String,String> tags) {
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

    public void setValue(int value) {
        this.value = value;
    }
}
