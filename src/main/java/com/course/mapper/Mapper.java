package com.course.mapper;

import com.course.pojo.*;

import java.util.List;


@org.apache.ibatis.annotations.Mapper
public interface Mapper {
    public void SavePostLog(Log post);

    public Log GetPostLog(String metric, String instanceId);

    public MetricConstraint GetMetricConstraint(String metric);

    public void SaveWarningLog(Log warningLog);

    public Log GetWarningLogAll();

    public int GetStartTime();
    public Log GetMemorySum(String[] MemoryNameList);
}
