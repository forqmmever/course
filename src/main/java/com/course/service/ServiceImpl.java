package com.course.service;

import com.course.mapper.Mapper;
import com.course.pojo.MetricConstraint;
import com.course.pojo.PostLog;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{

    private Mapper mapper;

    @Autowired
    public ServiceImpl(Mapper mapper){
        this.mapper = mapper;
    }


    public boolean SaveLog(PostLog postLog){
        mapper.SavePostLog(postLog);
        return true;
    }

    public boolean CheckRules(MetricConstraint metricConstraint, PostLog postLog){
         String ConstraintType = metricConstraint.getConstraintType();
         float ConstraintValue = metricConstraint.getValue();
         String ConstraintDescription = metricConstraint.getDescription();

         float value = postLog.getValue();

         boolean flag = false;

         switch (ConstraintType){
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
             case "<=" :
                 flag = (value <= ConstraintValue);
                 break;
         }
        return flag;
    }

    public String SavePostLog(PostLog postLog) {
        String metric = postLog.getMetric();
        MetricConstraint constraint = mapper.GetMetricConstraint(metric);
        if (constraint != null){
            if (CheckRules(constraint,postLog))
                return constraint.getDescription();
        }
        SaveLog(postLog);
        return "";
    }

    @Override
    public boolean GetPostLog(String metric, String instanceId) {
        String keyword = "%\"" + instanceId + "\"%";
        System.out.println(mapper.GetPostLog(metric,keyword));
        return true;
    }

    @Override
    public MetricConstraint GetMetricConstraint(String metric) {
        return mapper.GetMetricConstraint(metric);
    }



}
