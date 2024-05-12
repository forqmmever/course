@echo off
setlocal

rem 设置 MySQL 登录信息
set MYSQL_HOST=localhost
set MYSQL_PORT=3306
set MYSQL_USER=root
set MYSQL_PASSWORD=your_password

rem 设置数据库名称
set DATABASE_NAME=hostlog

rem 设置 MySQL 客户端路径
set MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"

rem 创建数据库
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -e "CREATE DATABASE IF NOT EXISTS %DATABASE_NAME%;"

rem 切换到指定数据库
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "USE %DATABASE_NAME%;"

rem 执行创建表格的指令
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "DROP TABLE IF EXISTS postlog;"
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "CREATE TABLE postlog (id INT PRIMARY KEY AUTO_INCREMENT, metric VARCHAR(50) comment '指标名称', tagJson VARCHAR(225) comment '标签，多个键值对', timestamp INT comment '时间戳', value FLOAT comment '指标值');"
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "DROP TABLE IF EXISTS MetricConstraint;"
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "CREATE TABLE MetricConstraint (id INT PRIMARY KEY AUTO_INCREMENT, metric VARCHAR(50) NOT NULL comment '指标名称', constraintType VARCHAR(50) NOT NULL comment '约束类型', value FLOAT NOT NULL comment '约束值', description VARCHAR(20) comment '告警描述');"
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "DROP TABLE IF EXISTS WarningLog;"
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "CREATE TABLE WarningLog (id INT PRIMARY KEY AUTO_INCREMENT, metric VARCHAR(50) comment '指标名称', tagJson VARCHAR(225) comment '标签，多个键值对', timestamp INT comment '时间戳', value FLOAT comment '指标值', description VARCHAR(20) comment '告警描述', time DATE comment '告警时间');"

rem 插入数据
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description) VALUES ('up', '=', 0, '宕机');"
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description) VALUES ('ode_netstat_Tcp_CurrEstab', '>', 1000, '主机连接数过高');"
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description) VALUES ('node_filesystem_free_bytes', '<', 1024 * 1024, '磁盘空间剩余不足');"
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description) VALUES ('node_load5', '>=', 10, '5分钟内CPU负载过高');"
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description) VALUES ('node_memory_MemFree_bytes', '<=', 100 * 1024, '空闲内存过低');"
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description) VALUES ('memory', '>', 80, '主机内存使用率超过80%');"
%MYSQL_PATH% -h %MYSQL_HOST% -P %MYSQL_PORT% -u %MYSQL_USER% -p%MYSQL_PASSWORD% -D %DATABASE_NAME% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description) VALUES ('rate_network_receive_bytes_total', '>', 100 * 1024 * 1024, '主机网卡入口流量过高');"

echo 数据库和表格已成功创建并且数据已插入。

endlocal
