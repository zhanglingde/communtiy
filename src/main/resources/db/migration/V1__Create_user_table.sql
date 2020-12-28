-- 编码规则
create table au_code_rule
(
    rule_id     bigint(20)  NOT NULL AUTO_INCREMENT,
    rule_code   varchar(30) NOT NULL COMMENT '编码规则code',
    rule_name   varchar(60) NOT NULL COMMENT '编码规则名',
    description varchar(240)         DEFAULT NULL COMMENT '描述',
    create_time datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期 ',
    update_time datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新日期 ',
    PRIMARY KEY (rule_id),
    UNIQUE KEY hpfm_code_rule_u1 (rule_code)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='编码规则头';

create table au_code_rule_dist
(
    rule_dist_id bigint(20) NOT NULL AUTO_INCREMENT,
    rule_id      bigint(20) NOT NULL,
    level_code   varchar(30)         DEFAULT NULL COMMENT '租户级下的应用层级',
    level_value  varchar(30)         DEFAULT NULL COMMENT '应用层级值',
    enabled_flag tinyint(1)          DEFAULT '1' COMMENT ' 是否启用',
    description  varchar(240)        DEFAULT NULL COMMENT '描述',
    create_time  datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    update_time  datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新日期',
    PRIMARY KEY (rule_dist_id),
    UNIQUE KEY hpfm_code_rule_dist_u1 (rule_id, level_code, level_value)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='编码规则分配';





-- lov值集

CREATE TABLE `au_lov`
(
    `lov_id`        int(11)      NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `lov_code`      varchar(60)  NOT NULL COMMENT 'LOV代码',
    `lov_type_code` varchar(30)           DEFAULT NULL COMMENT 'LOV数据类型：URL/SQL/FIXED',
    `lov_name`      varchar(255) NOT NULL COMMENT '值集名称',
    `description`   varchar(480)          DEFAULT NULL COMMENT '描述',
    `custom_sql`    longtext COMMENT '自定义sql',
    `custom_url`    varchar(600)          DEFAULT NULL COMMENT '查询URL',
    `value_field`   varchar(30)           DEFAULT NULL COMMENT '值字段',
    `display_field` varchar(30)           DEFAULT NULL COMMENT '显示字段',
    `create_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`lov_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC COMMENT ='lov表';

CREATE TABLE `au_lov_value`
(
    `lov_value_id` int(11)     NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `lov_id`       int(11)     NOT NULL COMMENT '关联lov表',
    `lov_code`     varchar(30) NOT NULL COMMENT '值集代码',
    `value`        varchar(30) NOT NULL COMMENT '值集值',
    `meaning`      varchar(120)         DEFAULT NULL COMMENT '含义',
    `description`  varchar(255)         DEFAULT NULL COMMENT '描述',
    `order_seq`    int(11)              DEFAULT '0' COMMENT '排序号',
    `create_time`  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark`       varchar(400)         DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`lov_value_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  ROW_FORMAT = DYNAMIC;

