package com.course.service;

import com.course.mapper.Mapper;
import com.course.pojo.MetricConstraint;
import com.course.pojo.PostLog;
import com.course.pojo.WarningLog;
import com.course.task.ScheduledTaskManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Date;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private Mapper mapper;
    @Autowired
    private ScheduledTaskManager manager;

//    @Autowired
//    public ServiceImpl(Mapper mapper) {
//        this.mapper = mapper;
//    }

    public boolean SaveLog(PostLog postLog) {
        mapper.SavePostLog(postLog);
        return true;
    }

    public boolean CheckRules(MetricConstraint metricConstraint, PostLog postLog) {
        String ConstraintType = metricConstraint.getConstraintType();
        float ConstraintValue = metricConstraint.getValue();
        String ConstraintDescription = metricConstraint.getDescription();

        float value = postLog.getValue();

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
        if (flag) SaveWarningLog(new WarningLog(postLog,ConstraintDescription,new Date()));
        return flag;
    }

    public String SavePostLog(PostLog postLog) {
        String metric = postLog.getMetric();
        MetricConstraint constraint = mapper.GetMetricConstraint(metric);
        if (constraint != null) {
            if (CheckRules(constraint, postLog))
                return constraint.getDescription();
        }
        SaveLog(postLog);
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
    public boolean SaveWarningLog(WarningLog warningLog) {
        mapper.SaveWarningLog(warningLog);
        return true;
    }

    @PostConstruct
    public void StartTask() {
        //        manager = new ScheduledTaskManager();
//        System.out.println("postconstruct");
//        manager.StartTask();
//        manager.ChangeInitialDelay(new Date());
        manager.ChangeInitialDelay(new Date(mapper.GetStartTime()));
    }
}
