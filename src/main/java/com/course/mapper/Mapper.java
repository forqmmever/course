package com.course.mapper;

import com.course.dto.AlterResult;
import com.course.dto.LogResult;
import com.course.entity.Log;
import com.course.entity.MetricConstraint;
import org.apache.ibatis.annotations.*;

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

    @Update("update metricconstraint set constraintType = #{newData.constraintType},value = #{newData.value},description = #{newData.description},type = #{newData.type} where metric = #{metric}")
    public void UpdateConstraint(String metric, MetricConstraint newData);

    @Delete("delete from metricconstraint where metric = #{metric}")
    public void DeleteConstraint(String metric);

    @Select("select timestamp from postlog order by timestamp desc limit 1")
    public Integer GetStartTime();

    @Select("select constraintType from metricconstraint where description = #{description}")
    public String GetConstraintTypeByDesc(String description);

    @Select("select * from postlog where timestamp >= #{StartTimestamp} and timestamp <= #{EndTimestamp}")
    public List<LogResult.Data> GetLogByTime(int StartTimestamp, int EndTimestamp);

    @Select("select CONCAT(w.metric,constraintType,m.value) 'condition',w.timestamp,w.value from warninglog w left join metricconstraint m on w.description = m.description where timestamp >= #{StartTimestamp} and timestamp <= #{EndTimestamp}")
    public List<AlterResult.Data> GetAlterByTime(int StartTimestamp, int EndTimestamp);

    @Select("select postlog.timestamp,postlog.value,postlog.tagJson from postlog,(select timestamp,tagJson from postlog order by timestamp desc limit 1) as t where metric ='node_network_receive_bytes_total' and postlog.timestamp >= t.timestamp - #{rage} and postlog.tagJson= t.tagJson limit 1;")
    public Log GetNetworkReceive(int rage);

    @Select("select value from postlog where metric = #{metric} and timestamp >= #{timestamp} - #{rage} and timestamp <= #{timestamp}")
    public List<Float> GetLogValueByRage(String metric, int timestamp, int rage);
}
