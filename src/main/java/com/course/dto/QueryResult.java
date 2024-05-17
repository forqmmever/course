package com.course.dto;




import java.util.Arrays;

public class QueryResult {

    private String requestId;
    private int total;

    public QueryResult(String requestId, int total) {
        this.requestId = requestId;
        this.total = total;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "requestId='" + requestId + '\'' +
                ", total=" + total +
                '}';
    }
}
