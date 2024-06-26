package com.course.service;

import com.course.dto.AlterResult;
import com.course.dto.LogResult;
import com.course.mapper.Mapper;
import com.course.entity.Log;
import com.course.entity.MetricConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@org.springframework.cache.annotation.EnableCaching
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
            case "==":
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
            case "!=":
                flag = (value != ConstraintValue);
                break;
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
    @Cacheable(value = "latestLogCache", key = "#metric")
    public Log GetLatestLog(String metric) {

        return mapper.GetLatestLog(metric);
    }

    @Override
    @Cacheable(value = "logValueCache", key = "#metric + '-' + #timestamp")
    public float GetLogValue(String metric, int timestamp) {
        return mapper.GetLogValue(metric, timestamp);
    }

    @Override
    @Cacheable(value = "metricConstraintCache", key = "#metric")
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
    public List<Float> GetLogValueByRage(String metric, int timestamp, int rage) {
        return mapper.GetLogValueByRage(metric, timestamp, rage);
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

    @Override
    public LogResult QueryLog(int StartTimestamp, int EndTimestamp) {
        List<LogResult.Data> datas = mapper.GetLogByTime(StartTimestamp, EndTimestamp);
        return new LogResult(UUID.randomUUID().toString(), datas.size(), datas);
    }

    @Override
    public AlterResult QueryAlter(int StartTimestamp, int EndTimestamp) {
        List<AlterResult.Data> datas = mapper.GetAlterByTime(StartTimestamp, EndTimestamp);
        return new AlterResult(UUID.randomUUID().toString(), datas.size(), datas);
    }

    @PostConstruct
    public void StartTask() {
        Long INF = 10000000000L * 1000L;
        Integer startTime = mapper.GetStartTime();
        //如果数据为空将开始时间设为正无穷
        manager.ChangeInitialDelay(new Date(startTime == null ? INF : startTime * 1000L));
    }

}
