@echo off

rem 设置 MySQL 登录信息
set MYSQL_USERNAME=root
set MYSQL_PASSWORD=123456
set MYSQL_HOST=localhost
set MYSQL_PORT=3306
set MYSQL_DATABASE=log

mysql -u %MYSQL_USERNAME% -p%MYSQL_PASSWORD% -h %MYSQL_HOST% -P %MYSQL_PORT% -e "DROP DATABASE IF EXISTS %MYSQL_DATABASE%; CREATE DATABASE %MYSQL_DATABASE% CHARACTER SET utf8mb4; USE %MYSQL_DATABASE%; DROP TABLE IF EXISTS postlog; CREATE TABLE postlog (id INT PRIMARY KEY AUTO_INCREMENT, metric VARCHAR(50), tagJson VARCHAR(225), timestamp INT, value FLOAT); CREATE INDEX index_metric ON postlog(metric); CREATE INDEX index_timestamp ON postlog(timestamp DESC); DROP TABLE IF EXISTS MetricConstraint; CREATE TABLE MetricConstraint (id INT PRIMARY KEY AUTO_INCREMENT, metric VARCHAR(50) NOT NULL, constraintType VARCHAR(225) NOT NULL, value FLOAT NOT NULL, description VARCHAR(50), type INT CHECK (type = 0 OR type = 1)); CREATE INDEX index_metric ON MetricConstraint(metric); DROP TABLE IF EXISTS WarningLog; CREATE TABLE WarningLog (id INT PRIMARY KEY AUTO_INCREMENT, metric VARCHAR(50), tagJson VARCHAR(225), timestamp INT, value FLOAT, description VARCHAR(50), time DATE); CREATE INDEX index_metric ON WarningLog(metric);insert into MetricConstraint (metric, constraintType, value, description,type) values ('up', '==', 0, 'crash',0); insert into MetricConstraint (metric, constraintType, value, description,type) values ('ode_netstat_Tcp_CurrEstab', '>', 1000, 'Too many host connections',0);insert into MetricConstraint (metric, constraintType, value, description,type)values ('node_filesystem_free_bytes', '<', 1024 * 1024, 'Insufficient disk space remaining',0);insert into MetricConstraint (metric, constraintType, value, description,type)values ('node_load5', '>=', 10, 'Excessive CPU load within 5 minutes',0);insert into MetricConstraint (metric, constraintType, value, description,type)values ('node_memory_MemFree_bytes','<=',100 * 1024,'Free memory too low',0);insert into MetricConstraint (metric, constraintType, value, description,type)values ('','(1-((node_memory_Buffers_bytes + node_memory_Cached_bytes + node_memory_MemFree_bytes)/node_memory_MemTotal_bytes))*100>',80,'Host memory usage exceeded 80 per cent',1);insert into MetricConstraint (metric, constraintType, value, description,type)values ('','rate(node_network_receive_bytes_total[1m])/ 1024 / 1024 >',100,'Excessive host NIC ingress traffic',1);"

pause
