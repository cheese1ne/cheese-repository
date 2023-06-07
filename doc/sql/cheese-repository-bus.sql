
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `dt_borrow_manage_borrow_apply`;

CREATE TABLE `dt_borrow_manage_borrow_apply` (
  `id` bigint NOT NULL COMMENT '唯一标识',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '申请类型：1线上；2线下',
  `apply_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '申请编号',
  `geodata_id` bigint DEFAULT NULL COMMENT '申请文件元数据id',
  `apply_user` bigint DEFAULT NULL COMMENT '申请人id',
  `apply_reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '申请借阅原因',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user` bigint DEFAULT NULL COMMENT '创建人id',
  `update_user` bigint DEFAULT NULL COMMENT '编辑者id',
  `status` tinyint(1) DEFAULT '0' COMMENT '业务状态：0申请中；1通过；2驳回；3拒绝',
  `is_deleted` tinyint unsigned DEFAULT '0' COMMENT '删除标记',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='借阅信息-借阅申请';

INSERT INTO `hb_databank`.`dt_borrow_manage_borrow_apply` (`id`, `type`, `apply_code`, `geodata_id`, `apply_user`, `apply_reason`, `remark`, `create_time`, `update_time`, `create_user`, `update_user`, `status`, `is_deleted`) VALUES (1, 'online', 'TCC-20220103-001', 10086, 50, '因项目需要申请查看', '无', '2021-11-10 17:10:20', '2021-11-10 17:10:20', NULL, NULL, 0, 0);