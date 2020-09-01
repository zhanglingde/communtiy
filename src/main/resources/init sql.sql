
-- 定时任务表
create table au_task(

    job_id int(11) not null primary key auto_increment comment '任务ID',
    bean_name varchar(100) not null comment 'bean名称',
    method_name varchar(100) not null comment '方法名称',
    method_params varchar(255) null comment '方法参数',
    cron_expression varchar(255) not null comment 'core表达式',
    remark varchar(500) null comment '备注',
    job_status tinyint(1) not null comment '状态，1正常，2暂停',
    create_time datetime not null default CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) comment '定时任务表';