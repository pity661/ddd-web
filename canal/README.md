### 针对mysql配置文件 `my.cnf` 
### docker容器中配置文件所在路径 `/etc/mysql/conf.d/my.cnf`
```
[mysqld]
log-bin=mysql-bin # 开启 binlog
binlog-format=ROW # 选择 ROW 模式
server_id=1 # 配置 MySQL replaction 需要定义，不要和 canal 的 slaveId 重复
```
### 重启后判断配置是否生效
```
show variables like 'binlog_format';
show variables like 'log_bin';
```

### 创建canal账号，并设置权限。
```
CREATE USER canal IDENTIFIED BY 'canal';  
GRANT SELECT, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'canal'@'%';
-- GRANT ALL PRIVILEGES ON *.* TO 'canal'@'%' ;
FLUSH PRIVILEGES;
```
### 查看canal用户权限
```
show grants for 'canal';
```