-- 定时任务
-- collate utf8_bin是 以二进制值比较，也就是区分大小写，collate是核对的意思
-- uft-8_general_ci  一般比较，不区分大小写

create table au_executor
(
    executor_id   bigint(20)                    NOT NULL AUTO_INCREMENT,
    executor_code varchar(30) COLLATE utf8_bin  NOT NULL COMMENT '执行器编码',
    executor_name varchar(120) COLLATE utf8_bin NOT NULL COMMENT '执行器名称',
    order_seq     int(11)                                DEFAULT '0' COMMENT '排序',
    executor_type tinyint(1)                             DEFAULT '0' COMMENT '执行器地址类型：0=自动注册、1=手动录入',
    address_list  varchar(240) COLLATE utf8_bin          DEFAULT NULL COMMENT '执行器地址列表，多地址逗号分隔',
    status        varchar(30) COLLATE utf8_bin           DEFAULT NULL COMMENT '执行器状态',
    create_time   datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time   datetime                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`executor_id`),
    UNIQUE KEY `au_executor_u1` (`executor_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='执行器';

CREATE TABLE `au_job_info`
(
    `job_id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `executor_id`       bigint(20)                    DEFAULT NULL COMMENT '执行器ID,hsdr_executor.executor_id',
    `job_code`          varchar(30) COLLATE utf8_bin  DEFAULT NULL COMMENT '任务编码',
    `job_cron`          varchar(60) COLLATE utf8_bin  DEFAULT NULL COMMENT '任务执行corn',
    `description`       varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '任务描述',
    job_status tinyint(1) not null default 1 comment '任务状态（1正常，0暂停）',
    `job_param`         longtext COLLATE utf8_bin COMMENT '执行器任务参数',
    `executor_strategy` varchar(30) COLLATE utf8_bin  DEFAULT NULL COMMENT '执行器策略，HSDR.EXECUTOR_STRATEGY',
    `fail_strategy`     varchar(30) COLLATE utf8_bin  DEFAULT NULL COMMENT '失败处理策略，HSDR.FAIL_STRATEGY',
    `glue_type`         varchar(30) COLLATE utf8_bin  DEFAULT NULL COMMENT '任务类型，HSDR.GLUE_TYPE',
    `job_handler`       varchar(30) COLLATE utf8_bin  DEFAULT NULL COMMENT 'jobHandler',
    `cycle_flag`        tinyint(1)                    DEFAULT '0' COMMENT '周期性',
    `start_date`        datetime                      DEFAULT NULL COMMENT '有效时间从',
    `end_date`          datetime                      DEFAULT NULL COMMENT '有效时间至',
    `create_time`       datetime   NOT NULL           DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       datetime   NOT NULL           DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `strategy_param`    varchar(240) COLLATE utf8_bin DEFAULT NULL COMMENT '策略参数',
    PRIMARY KEY (`job_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='调度任务';

CREATE TABLE `au_job_log`
(
    `log_id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `job_id`         bigint(20)                    DEFAULT NULL COMMENT '任务ID,job_id',
    `job_result`     varchar(30) COLLATE utf8_bin  DEFAULT NULL COMMENT '任务调度结果',
    `client_result`  varchar(30) COLLATE utf8_bin  DEFAULT NULL COMMENT '客户端执行结果',
    `executor_id`    bigint(20) NOT NULL COMMENT '执行器ID,executor_id',
    `address`        varchar(30) COLLATE utf8_bin  DEFAULT NULL COMMENT '任务执行地址',
    `message_header` varchar(480) COLLATE utf8_bin DEFAULT NULL COMMENT '错误信息简略',
    `message`        longtext COLLATE utf8_bin COMMENT '错误信息',
    `start_time`     datetime                      DEFAULT NULL COMMENT '任务开始时间',
    `end_time`       datetime                      DEFAULT NULL COMMENT '任务结束时间',
    `log_url`        varchar(480) COLLATE utf8_bin DEFAULT NULL COMMENT '日志文件url',
    `create_time`    datetime   NOT NULL           DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime   NOT NULL           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`log_id`),
    KEY `hsdr_job_log_n1` (`job_id`),
    KEY `hsdr_job_log_n2` (`executor_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='任务日志';

-- 使用java自带功能实现定时任务 表
CREATE TABLE `au_task`
(
    `job_id`          int(11)      NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `bean_name`       varchar(100) NOT NULL COMMENT 'bean名称',
    `method_name`     varchar(100) NOT NULL COMMENT '方法名称',
    `method_params`   varchar(255)          DEFAULT NULL COMMENT '方法参数',
    `cron_expression` varchar(255) NOT NULL COMMENT 'core表达式',
    `remark`          varchar(500)          DEFAULT NULL COMMENT '备注',
    `job_status`      tinyint(1)   NOT NULL COMMENT '状态，1正常，2暂停',
    `create_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`job_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='定时任务表';

create table au_task(
    job_id int not null auto_increment comment '任务ID',
    bean_name varchar(100) not null comment 'bean名称',
    method_name varchar(100) not null comment '方法名称',
    method_params varchar(255) default null comment '方法参数',
    cron_expression varchar(255) not null comment 'cron表达式',
    job_status tinyint(1) not null comment '状态：1正常，0暂停',
    remark varchar(255) default null comment '备注',
    create_time datetime not null default current_timestamp comment '创建时间',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间'

) comment '定时任务表';