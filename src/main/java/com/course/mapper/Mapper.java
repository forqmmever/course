package com.course.mapper;

import com.course.entity.Log;
import com.course.entity.MetricConstraint;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@org.apache.ibatis.annotations.Mapper
public interface Mapper {

    @Insert("INSERT INTO postlog (metric, tagJson, timestamp, value) VALUES (#{metric}, #{tagJson}, #{timestamp}, #{value})")
    public void SavePostLog(Log post);

    @Select("select * from postlog where metric = #{metric} order by timestamp desc limit 1")
    public Log GetPostLog(String metric);

    @Select("select * from MetricConstraint where metric = #{metric} ")
    public MetricConstraint GetMetricConstraint(String metric);
    @Insert("insert into metricconstraint(metric, constraintType, value, description) values (#{metric},#{constraintType},#{value},#{description})")
    public void AddConstraint(MetricConstraint metricConstraint);

    @Insert("INSERT INTO warninglog (metric, tagJson, timestamp, value, description, time) VALUES (#{metric}, #{tagJson}, #{timestamp}, #{value}, #{description}, #{time})")
    public void SaveWarningLog(Log warningLog);

    @Select("select * from warninglog")
    public List<Log> GetWarningLogAll();
    @Select("Select * from metricconstraint where type >= #{type}")
    public List<MetricConstraint> GetConstraintAll(int type);
    @Update("update metricconstraint set constraintType = #{newData.constraintType},value = #{newData.value},description = #{newData.description} where metric = #{metric}")
    public void UpdateConstraint(String metric,MetricConstraint newData);
    @Delete("delete from metricconstraint where metric = #{metric}")
    public void DeleteConstraint(String metric);
    @Select("select COUNT(*) from postlog")
    public int CountPostLog();

    @Select("select timestamp from postlog order by timestamp desc limit 1")
    public Integer GetStartTime();
    @Select("select timestamp,value,tagJson from postlog where metric = #{metric} order by timestamp desc limit 1")
    public Log GetMemoryLog(String metric);

    @Select("select postlog.timestamp,postlog.value,postlog.tagJson from postlog,(select timestamp,tagJson from postlog order by timestamp desc limit 1) as t where metric ='node_network_receive_bytes_total' and postlog.timestamp >= t.timestamp - #{rage} and postlog.tagJson= t.tagJson limit 1;")
    public Log GetNetworkReceive(int rage);
}
