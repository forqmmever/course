package com.course.service;

import com.course.entity.Log;
import com.course.entity.MetricConstraint;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

@org.springframework.stereotype.Service
public interface Service {

    public String SavePostLog(Log postLog) throws JsonProcessingException;

    public boolean GetPostLog(String metric, String instanceId, int timestamp);

    public MetricConstraint GetMetricConstraint(String metric);

    public boolean SaveWarningLog(Log warningLog);

    public boolean CheckRules(MetricConstraint metricConstraint, Log log);
    public Log GetMemoryLog(String MemoryName);
    public Log GetNetworkReceive(int rate);
    public List<Log> GetWarnigLogAll();
    public List<MetricConstraint> GetConstraintAll();
}
