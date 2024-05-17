package com.course.dto;

import java.util.List;

public class AlterResult extends QueryResult {
    List<Data> data;

    public AlterResult(String requestId, int total, List<Data> data) {
        super(requestId, total);
        this.data = data;
    }

    @Override
    public String toString() {
        return "AlterResult{" +
                "requestId='" + getRequestId() + '\'' +
                ", total=" + getTotal() +
                ", data=" + data +
                '}';
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        private String condition;
        private int timestamp;
        private float value;

        @Override
        public String toString() {
            return "Data{" +
                    "condition= " + condition + '\'' +
                    ", timestamp=" + timestamp +
                    ", value=" + value +
                    '}';
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
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
    }
}
