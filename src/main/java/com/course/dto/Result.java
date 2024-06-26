package com.course.dto;

public class Result {
    private String requestId;
    private String msg;


    public Result(String requestId, String msg) {
        this.requestId = requestId;
        this.msg = msg;
    }



    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
