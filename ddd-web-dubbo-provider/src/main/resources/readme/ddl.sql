drop table if exists customer;
create table customer (
    id bigint auto_increment comment 'id主键'
      primary key,
    name varchar(24) not null default '' comment '姓名',
    age int not null default 0 comment '年龄',
    phone char(11) comment '业务数据',
    created_at datetime not null default CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '更新时间',
    index idx_name(name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户信息记录表';

insert into customer(name, age, phone) values ('wenky', 26, '18866666666');