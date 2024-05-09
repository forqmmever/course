package com.course.service;

import com.course.pojo.PostLog;

@org.springframework.stereotype.Service
public interface Service {

    public boolean SavePostLogPostLog(PostLog postLog);
    public boolean GetPostLog(String metric,String instanceId);
    public boolean GetMetricConstraint(String metric);

}
