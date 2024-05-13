package com.course.service;

import com.course.mapper.Mapper;
import com.course.entity.Log;
import com.course.entity.MetricConstraint;
//
//import com.course.pojo.WarningLog;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private Mapper mapper;
    @Autowired
    private ScheduledTaskManager manager;

    @Override
    public boolean CheckRules(String ConstraintType, float ConstraintValue, String ConstraintDesciption, Log log) {

        float value = log.getValue();

        boolean flag = false;

        switch (ConstraintType) {
            case "=": flag = (value == ConstraintValue);break;
            case ">": flag = (value > ConstraintValue);break;
            case "<": flag = (value < ConstraintValue);break;
            case ">=": flag = (value >= ConstraintValue);break;
            case "<=": flag = (value <= ConstraintValue);break;
        }

        if (flag) {
            log.setTime(new Date());
            log.setDescription(ConstraintDesciption);
            SaveWarningLog(log);
        }
        return flag;
    }

    public String SavePostLog(Log postLog) {
        mapper.SavePostLog(postLog);

        String metric = postLog.getMetric();

        MetricConstraint constraint = mapper.GetMetricConstraint(metric);
        if (constraint != null) {
            if (CheckRules(constraint.getConstraintType(), constraint.getValue(), constraint.getDescription(), postLog)) {
                return constraint.getDescription();
            }
        }
        return "";
    }


    @Override
    public Log GetLatestLog(String metric) {
//        System.out.println(mapper.GetPostLog(metric));

        return mapper.GetLatestLog(metric);
    }

    @Override
    public float GetLogValue(String metric, int timestamp) {
        return mapper.GetLogValue(metric, timestamp);
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
    public void UpdateConstraint(String metric, MetricConstraint constraint) {
        mapper.UpdateConstraint(metric, constraint);
    }

    @Override
    public void AddConstraint(MetricConstraint constraint) {
        mapper.AddConstraint(constraint);
    }

    @Override
    public boolean DeletConstraint(String metric) {
        mapper.DeleteConstraint(metric);
        return true;
    }

    @Override
    public List<Log> GetWarningLogAll() {
        return mapper.GetWarningLogAll();
    }

    @Override
    public List<Float> GetLogValueRage(String metric, int timestamp, int rage) {
        return mapper.GetLogValueRage(metric, timestamp, rage);
    }

    @Override
    public List<MetricConstraint> GetConstraintAll(int type) {
        return mapper.GetConstraintAll(type);
    }

    @Override
    public boolean ChangeTaskSet(int interval, Date date) {
        manager.ChangeInterval(interval);
        manager.ChangeInitialDelay(date);
        return true;
    }

    @Override
    public List<Log> GetPostLogAll() {
        return mapper.GetPostLogAll();
    }

    @PostConstruct
    public void StartTask() {
        Long INF = 10000000000L * 1000L;
        Integer startTime = mapper.GetStartTime();
        //如果数据为空将开始时间设为正无穷
        manager.ChangeInitialDelay(new Date(startTime == null ? INF : startTime * 1000L));
    }

}
