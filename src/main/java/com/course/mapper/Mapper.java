package com.course.mapper;

import com.course.entity.Log;
import com.course.entity.MetricConstraint;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@org.apache.ibatis.annotations.Mapper
public interface Mapper {
    public void SavePostLog(Log post);

//    @Select("select * from postlog where metric = #{metric} and tagJson like #{instanceId}")
    public Log GetPostLog(String metric, String instanceId,int timestamp);

    public MetricConstraint GetMetricConstraint(String metric);

    public void SaveWarningLog(Log warningLog);

    @Select("select * from warninglog")
    public List<Log> GetWarningLogAll();
    @Select("Select * from metricconstraint")
    public List<MetricConstraint> GetConstraintAll();

    public Integer GetStartTime();
    public Log GetMemoryLog(String metric);

    public Log GetNetworkReceive(int rage);
}
