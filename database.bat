@echo off

rem 设置 MySQL 的安装路径
set MYSQL_HOME=C:\Program Files\MySQL\MySQL Server 8.0\bin

rem 设置 MySQL 的用户名和密码
set MYSQL_USER=root
set MYSQL_PASSWORD=123456

rem 设置数据库名称
set DATABASE_NAME=hostlog

rem 设置 MySQL 命令文件路径
set SQL_FILE=commands.sql

rem 将 MySQL 命令写入到命令文件中
echo create database %DATABASE_NAME%; > %SQL_FILE%
echo use %DATABASE_NAME%; >> %SQL_FILE%
echo. >> %SQL_FILE%
echo drop table if exists postlog; >> %SQL_FILE%
echo create table postlog ( >> %SQL_FILE%
echo     id INT PRIMARY KEY AUTO_INCREMENT, >> %SQL_FILE%
echo     metric VARCHAR(50) comment '指标名称', >> %SQL_FILE%
echo     tagJson VARCHAR(225) comment '标签，多个键值对', >> %SQL_FILE%
echo     timestamp int comment '时间戳', >> %SQL_FILE%
echo     value float comment '指标值' >> %SQL_FILE%
echo ); >> %SQL_FILE%
echo create index index_metric on postLog(metric); >> %SQL_FILE%
echo create index index_timestamp on postLog(timestamp desc); >> %SQL_FILE%
echo. >> %SQL_FILE%
echo drop table if exists MetricConstraint; >> %SQL_FILE%
echo CREATE TABLE MetricConstraint ( >> %SQL_FILE%
echo     id INT PRIMARY KEY AUTO_INCREMENT, >> %SQL_FILE%
echo     metric VARCHAR(50) NOT NULL comment '指标名称', >> %SQL_FILE%
echo     constraintType VARCHAR(100) NOT NULL comment '约束类型', >> %SQL_FILE%
echo     value FLOAT NOT NULL comment '约束值', >> %SQL_FILE%
echo     description VARCHAR(20) comment '告警描述', >> %SQL_FILE%
echo     type INT comment '任务类型:0为实时任务:1为定时任务' check ( type = 0 or type = 1 ) >> %SQL_FILE%
echo ); >> %SQL_FILE%
echo create index index_metric on MetricConstraint(metric); >> %SQL_FILE%
echo. >> %SQL_FILE%
echo insert into MetricConstraint (metric, constraintType, value, description,type) values ('up', '=', 0, '宕机',0); >> %SQL_FILE%
echo insert into MetricConstraint (metric, constraintType, value, description,type) values ('ode_netstat_Tcp_CurrEstab', '>', 1000, '主机连接数过高',0); >> %SQL_FILE%
echo insert into MetricConstraint (metric, constraintType, value, description,type) values ('node_filesystem_free_bytes', '<', 1024 * 1024, '磁盘空间剩余不足',0); >> %SQL_FILE%
echo insert into MetricConstraint (metric, constraintType, value, description,type) values ('node_load5', '>=', 10, '5分钟内CPU负载过高',0); >> %SQL_FILE%
echo insert into MetricConstraint (metric, constraintType, value, description,type) values ('node_memory_MemFree_bytes','<=',100 * 1024,'空闲内存过低',0); >> %SQL_FILE%
echo insert into MetricConstraint (metric, constraintType, value, description,type) values ('node_memory','(1-((Buffers_bytes + Cached_bytes + MemFree_bytes)/MemTotal_bytes))*100>',80,'主机内存使用率超过80%',1); >> %SQL_FILE%
echo insert into MetricConstraint (metric, constraintType, value, description,type) values ('node_network','rate(receive_bytes_total[1m])/ 1024 / 1024 >',100,'主机网卡入口流量过高',1); >> %SQL_FILE%
echo. >> %SQL_FILE%
echo drop table if exists WarningLog; >> %SQL_FILE%
echo create table WarningLog ( >> %SQL_FILE%
echo     id INT PRIMARY KEY AUTO_INCREMENT, >> %SQL_FILE%
echo     metric VARCHAR(50) comment '指标名称', >> %SQL_FILE%
echo     tagJson VARCHAR(225) comment '标签，多个键值对', >> %SQL_FILE%
echo     timestamp int comment '时间戳', >> %SQL_FILE%
echo     value float comment '指标值', >> %SQL_FILE%
echo     description VARCHAR(20) comment '告警描述', >> %SQL_FILE%
echo     time DATE comment '告警时间' >> %SQL_FILE%
echo ); >> %SQL_FILE%
echo create index index_metric on WarningLog(metric); >> %SQL_FILE%

rem 使用 MySQL 命令行执行命令文件
"%MYSQL_HOME%\mysql" -u%MYSQL_USER% -p%MYSQL_PASSWORD% < %SQL_FILE%

rem 删除命令文件
del %SQL_FILE%

pause
