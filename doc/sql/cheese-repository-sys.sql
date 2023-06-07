SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `sys_sql_config`;
CREATE TABLE `sys_sql_config` (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `code` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标识',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '脚本语句',
  `dbkey` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '数据库标识',
  `dbtype` int(2) DEFAULT '0' COMMENT '数据库类型',
  `actiontype` int(2) DEFAULT '0' COMMENT '操作类型',
  `create_by` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code_Unique` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='sql脚本配置';

INSERT INTO `sys_sql_config` (`id`, `code`, `content`, `dbkey`, `dbtype`, `actiontype`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (50, 'INSERT_NEW_SYS_USER', 'INSERT INTO `sys_user` (`name`, `full_name`, `nick_name`, `email`, `telephone`, `password`, `salt`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) \nVALUES (#{ew.data.name}, #{ew.data.full_name}, #{ew.data.nick_name}, #{ew.data.email}, #{ew.data.telephone}, #{ew.data.password}, \'cheesesalt\', #{ew.data.status}, #{ew.data.create_by}, CURRENT_TIMESTAMP, NULL, NULL, #{ew.data.remark})', 'sys', 0, 1, 50, '2023-06-07 03:42:18', NULL, NULL, '无');
INSERT INTO `sys_sql_config` (`id`, `code`, `content`, `dbkey`, `dbtype`, `actiontype`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (51, 'UPDATE_BORROW_APPLY_DATA', 'UPDATE dt_borrow_manage_borrow_apply SET status = #{ew.data.status} WHERE id = #{ew.param.id}', 'bus', 0, 3, 50, '2023-06-07 03:42:18', NULL, NULL, '无');

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(64) NOT NULL COMMENT '主键',
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  `full_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '全名',
  `nick_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱/账号',
  `telephone` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盐',
  `status` tinyint(1) DEFAULT '1' COMMENT '帐号状态，0未激活，1启用，-1锁定',
  `create_by` bigint(64) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` bigint(64) DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT = 10086 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统用户';

