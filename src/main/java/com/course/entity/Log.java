package com.course.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.collections.MappingChange;

import java.lang.String;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Log {
    private int id;
    private String metric;
    private String tagJson;
    private int timestamp;
    private float value;
    private Map<String, String> tags;
    private String description;
    private Date time;
    public Log(){}
    public Log(String metric, Map<String, String> tags,int timestamp, float value) {
        this.metric = metric;
        this.timestamp = timestamp;
        this.value = value;
        this.tags = tags;
    }

    public Log(int id, String metric, String tagJson, int timestamp, float value) throws JsonProcessingException {
        this.id = id;
        this.metric = metric;
        this.tagJson = tagJson;
        this.timestamp = timestamp;
        this.value = value;
        ObjectMapper mapper = new ObjectMapper();
        tags = mapper.readValue(tagJson, Map.class);
    }

    public Log(String metric, int timestamp, float value, Map<String, String> tags, String tagJson, String description, Date time) {
        this.metric = metric;
        this.timestamp = timestamp;
        this.value = value;
        this.tags = tags;
        this.tagJson = tagJson;
        this.description = description;
        this.time = time;
    }



    public Log(int timestamp, float value, String tagJson) {
        this.timestamp = timestamp;
        this.value = value;
        this.tagJson = tagJson;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
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

    public Map<String, String> getTags() {
        if (tags == null && !tagJson.isEmpty()){
            ObjectMapper mapper = new ObjectMapper();
            try {
                tags = new HashMap<>();
                tags = mapper.readValue(tagJson, Map.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
        if (tagJson.isEmpty()){
            ObjectMapper mapper = new ObjectMapper();
            try {
                tagJson = mapper.writeValueAsString(tags);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getTagJson() {
        if (tagJson.isEmpty() && tags != null){
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(tags);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return tagJson;
    }

    public void setTagJson(String tagJson) {
        this.tagJson = tagJson;
        if (tags == null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                tags = new HashMap<>();
                tags = mapper.readValue(tagJson, Map.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
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

    public String getTagsJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(tags);
    }

    @Override
    public String toString() {
        return "Log{" +
                "metric='" + metric + '\'' +
                ", timestamp=" + timestamp +
                ", value=" + value +
                ", tags=" + tags +
                ", tagJson='" + tagJson + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                '}';
    }
}
