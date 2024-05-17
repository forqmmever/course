package com.course.dto;

import java.util.List;

public class LogResult extends QueryResult {
    List<Data> data;

    public LogResult(String requestId, int total, List<Data> data) {
        super(requestId, total);
        this.data = data;
    }

    @Override
    public String toString() {
        return "LogResult{" +
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

    public LogResult(String requestId, int total) {
        super(requestId, total);
    }

    public static class Data {
        private String metric;
        private int timestamp;
        private float value;

        @Override
        public String toString() {
            return "Data{" +
                    "metric='" + metric + '\'' +
                    ", timestamp=" + timestamp +
                    ", value=" + value +
                    '}';
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
    }
}
