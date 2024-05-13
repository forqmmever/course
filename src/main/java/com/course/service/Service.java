package com.course.service;

import com.course.entity.Log;
import com.course.entity.MetricConstraint;
import com.course.entity.TaskSet;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
public interface Service {

    public String SavePostLog(Log postLog) throws JsonProcessingException;

    public Log GetLatestLog(String metric);

    public float GetLogValue(String metric,int timestamp);

    public List<Float> GetLogValueRage(String metric, int timestamp, int rage);

    public MetricConstraint GetMetricConstraint(String metric);

    public boolean SaveWarningLog(Log warningLog);

    public boolean CheckRules(String ConstraintType, float ConstraintValue, String ConstraintDesciption, Log log);

    public void UpdateConstraint(String metric, MetricConstraint constraint);

    public void AddConstraint(MetricConstraint constraint);

    public boolean DeletConstraint(String metric);

    public List<Log> GetWarningLogAll();

    public List<MetricConstraint> GetConstraintAll(int type);

    public boolean ChangeTaskSet(int interval, Date date);

    public List<Log> GetPostLogAll();
}
