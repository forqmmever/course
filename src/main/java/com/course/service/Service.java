package com.course.service;

import com.course.pojo.MetricConstraint;
import com.course.pojo.PostLog;
import com.course.pojo.WarningLog;

import javax.annotation.PostConstruct;

@org.springframework.stereotype.Service
public interface Service {

    public String SavePostLog(PostLog postLog);
    public boolean GetPostLog(String metric,String instanceId);
    public MetricConstraint GetMetricConstraint(String metric);
    public boolean SaveWarningLog(WarningLog warningLog);

}
