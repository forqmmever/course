package com.course.mapper;

import com.course.entity.Log;
import com.course.entity.MetricConstraint;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Update("update metricconstraint set constraintType = #{newData.constraintType},value = #{newData.value},description = #{newData.description} where metric = #{metric}")
    public void UpdateConstraint(String metric,MetricConstraint newData);

    public Integer GetStartTime();
    public Log GetMemoryLog(String metric);

    public Log GetNetworkReceive(int rage);
}
