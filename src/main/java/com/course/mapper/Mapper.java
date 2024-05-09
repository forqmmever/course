package com.course.mapper;

import com.course.pojo.MetricConstraint;
import com.course.pojo.PostLog;
import org.apache.ibatis.annotations.*;


@org.apache.ibatis.annotations.Mapper
public interface Mapper {
    public void SavePostLog(PostLog post);
    public PostLog GetPostLog(String metric,String instanceId);
    public MetricConstraint GetMetricConstraint(String metric);
}