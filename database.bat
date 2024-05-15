@echo off

set SERVER=mysql
set USERNAME=root
set PASSWORD=123456
set DATABASE=log

mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% -e "DROP DATABASE IF EXISTS %DATABASE%;"

mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% -e "CREATE DATABASE %DATABASE% CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "DROP TABLE IF EXISTS postlog;"

mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "CREATE TABLE postlog (id INT PRIMARY KEY AUTO_INCREMENT, metric VARCHAR(50) COMMENT '指标名称', tagJson VARCHAR(225) COMMENT '标签，多个键值对', timestamp INT COMMENT '时间戳', value FLOAT COMMENT '指标值');"

mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "CREATE INDEX index_metric ON postlog(metric);"
mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "CREATE INDEX index_timestamp ON postlog(timestamp DESC);"

mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "DROP TABLE IF EXISTS MetricConstraint;"

mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "CREATE TABLE MetricConstraint (id INT PRIMARY KEY AUTO_INCREMENT, metric VARCHAR(50) NOT NULL COMMENT '指标名称', constraintType VARCHAR(100) NOT NULL COMMENT '约束类型', value FLOAT NOT NULL COMMENT '约束值', description VARCHAR(20) COMMENT '告警描述', type INT COMMENT '任务类型:0为实时任务:1为定时任务' CHECK (type = 0 OR type = 1));"

mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "CREATE INDEX index_metric ON MetricConstraint(metric);"

mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description, type) VALUES ('up', '=', 0, '宕机', 0);"
mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description, type) VALUES ('ode_netstat_Tcp_CurrEstab', '>', 1000, '主机连接数过高', 0);"
mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description, type) VALUES ('node_filesystem_free_bytes', '<', 1024 * 1024, '磁盘空间剩余不足', 0);"
mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description, type) VALUES ('node_load5', '>=', 10, '5分钟内CPU负载过高', 0);"
mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description, type) VALUES ('node_memory_MemFree_bytes', '<=', 100 * 1024, '空闲内存过低', 0);"
mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description, type) VALUES ('node_memory', '(1-((Buffers_bytes + Cached_bytes + MemFree_bytes)/MemTotal_bytes))*100>', 80, '主机内存使用率超过80%', 1);"
mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "INSERT INTO MetricConstraint (metric, constraintType, value, description, type) VALUES ('node_network', 'rate(receive_bytes_total[1m])/ 1024 / 1024 >', 100, '主机网卡入口流量过高', 1);"

mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "DROP TABLE IF EXISTS WarningLog;"

mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "CREATE TABLE WarningLog (id INT PRIMARY KEY AUTO_INCREMENT, metric VARCHAR(50) COMMENT '指标名称', tagJson VARCHAR(225) COMMENT '标签，多个键值对', timestamp INT COMMENT '时间戳', value FLOAT COMMENT '指标值', description VARCHAR(20) COMMENT '告警描述', time DATE COMMENT '告警时间');"

mysql -h %SERVER% -u %USERNAME% -p%PASSWORD% %DATABASE% -e "CREATE INDEX index_metric ON WarningLog(metric);"
