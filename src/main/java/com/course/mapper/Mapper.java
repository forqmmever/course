package com.course.mapper;

import com.course.pojo.*;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@org.apache.ibatis.annotations.Mapper
public interface Mapper {
    public void SavePostLog(Log post);

//    @Select("select * from postlog where metric = #{metric} and tagJson like #{instanceId}")
    public Log GetPostLog(String metric, String instanceId,int timestamp);

    public MetricConstraint GetMetricConstraint(String metric);

    public void SaveWarningLog(Log warningLog);

    public Log GetWarningLogAll();

    public Integer GetStartTime();
    public Log GetMemoryLog(String metric);

    public Log GetNetworkReceive(int rage);
}
