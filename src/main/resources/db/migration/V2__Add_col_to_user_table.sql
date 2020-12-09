CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER NOT NULL,
  `gender` char(1) CHARACTER DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `au_supplier_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(128) NOT NULL COMMENT '用户名',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `company_id` int(11) NOT NULL COMMENT '公司id',
  `token` text COMMENT 'token值，用户登录凭证',
  `token_time` varchar(32) DEFAULT NULL COMMENT '创建更新token时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `parent_id` int(11) NOT NULL DEFAULT '-1' COMMENT '父id',
  `enabled_flag` tinyint(1) DEFAULT '1' COMMENT '启动标识，1启用，0禁用',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='供应商用户表';