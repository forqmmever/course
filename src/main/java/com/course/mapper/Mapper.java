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
    public Log GetLatestLog(String metric);

    @Select("select value from postlog where metric = #{metric} and timestamp = #{timestamp}")
    public float GetLogValue(String metric, int timestamp);

    @Select("select * from MetricConstraint where metric = #{metric} ")
    public MetricConstraint GetMetricConstraint(String metric);

    @Insert("insert into metricconstraint(metric, constraintType, value, description,type) values (#{metric},#{constraintType},#{value},#{description},#{type})")
    public void AddConstraint(MetricConstraint metricConstraint);

    @Insert("insert into warninglog (metric, tagJson, timestamp, value, description, time) values (#{metric}, #{tagJson}, #{timestamp}, #{value}, #{description}, #{time})")
    public void SaveWarningLog(Log warningLog);

    @Select("select * from warninglog")
    public List<Log> GetWarningLogAll();

    @Select("select * from metricconstraint where type >= #{type}")
    public List<MetricConstraint> GetConstraintAll(int type);

    @Select("select * from postlog")
    public List<Log> GetPostLogAll();

    @Update("update metricconstraint set constraintType = #{newData.constraintType},value = #{newData.value},description = #{newData.description},type = #{type} where metric = #{metric}")
    public void UpdateConstraint(String metric, MetricConstraint newData);

    @Delete("delete from metricconstraint where metric = #{metric}")
    public void DeleteConstraint(String metric);

    @Select("select COUNT(*) from postlog")
    public int CountPostLog();

    @Select("select timestamp from postlog order by timestamp desc limit 1")
    public Integer GetStartTime();

    @Select("select postlog.timestamp,postlog.value,postlog.tagJson from postlog,(select timestamp,tagJson from postlog order by timestamp desc limit 1) as t where metric ='node_network_receive_bytes_total' and postlog.timestamp >= t.timestamp - #{rage} and postlog.tagJson= t.tagJson limit 1;")
    public Log GetNetworkReceive(int rage);

    @Select("select value from postlog where metric = #{metric} and timestamp >= #{timestamp} - #{rage} and timestamp <= #{timestamp}")
    public List<Float> GetLogValueRage(String metric, int timestamp, int rage);
}
