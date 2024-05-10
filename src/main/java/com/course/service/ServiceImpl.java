package com.course.service;

import com.course.mapper.Mapper;
import com.course.pojo.Log;
import com.course.pojo.MetricConstraint;
//import com.course.pojo.PostLog;
//import com.course.pojo.WarningLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Date;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private Mapper mapper;
    @Autowired
    private ScheduledTaskManager manager;


    @Override
    public boolean CheckRules(MetricConstraint metricConstraint, Log log) {
        String ConstraintType = metricConstraint.getConstraintType();
        float ConstraintValue = metricConstraint.getValue();
        String ConstraintDesciption = metricConstraint.getDescription();

        float value = log.getValue();


        boolean flag = false;

        switch (ConstraintType) {
            case "=":
                flag = (value == ConstraintValue);
                break;
            case ">":
                flag = (value > ConstraintValue);
                break;
            case "<":
                flag = (value < ConstraintValue);
                break;
            case ">=":
                flag = (value >= ConstraintValue);
                break;
            case "<=":
                flag = (value <= ConstraintValue);
                break;
        }
        log.setTime(new Date());
        log.setDescription(ConstraintDesciption);
        if (flag) SaveWarningLog(log);
        return flag;
    }

    public String SavePostLog(Log postLog) throws JsonProcessingException {
        mapper.SavePostLog(postLog);

        String metric = postLog.getMetric();

        MetricConstraint constraint = mapper.GetMetricConstraint(metric);
        if (constraint != null) {
            if (CheckRules(constraint, postLog)) {
                return constraint.getDescription();
            }
        }
        return "";
    }

    @Override
    public boolean GetPostLog(String metric, String instanceId) {
        String keyword = "%\"" + instanceId + "\"%";
        System.out.println(mapper.GetPostLog(metric, keyword));
        return true;
    }

    @Override
    public MetricConstraint GetMetricConstraint(String metric) {
        return mapper.GetMetricConstraint(metric);
    }

    @Override
    public boolean SaveWarningLog(Log warningLog) {
        mapper.SaveWarningLog(warningLog);
        return true;
    }

    @Override
    public Log GetMemorySum(String[] MemoryNameList) {
        return mapper.GetMemorySum(MemoryNameList);
    }

//    @PostConstruct
//    public void StartTask() {
//        Long start = (long)mapper.GetStartTime() * 1000L;
//        Long INF = 200000000L;
//        //如果数据为空将开始时间设为正无穷
//        manager.ChangeInitialDelay(new Date(start >= 0 ?start:INF));
//    }

}
