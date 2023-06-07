
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
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '编辑者id',
  `status` tinyint(1) DEFAULT '0' COMMENT '业务状态：0申请中；1通过；2驳回；3拒绝',
  `is_deleted` tinyint unsigned DEFAULT '0' COMMENT '删除标记',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='借阅信息-借阅申请';

INSERT INTO `hb_databank`.`dt_borrow_manage_borrow_apply` (`id`, `type`, `apply_code`, `geodata_id`, `apply_user`, `apply_reason`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`, `status`, `is_deleted`) VALUES (1, 'online', 'TCC-20220103-001', 10086, 50, '因项目需要申请查看', '无', '2021-11-10 17:10:20', '2021-11-10 17:10:20', NULL, NULL, 0, 0);
INSERT INTO `hb_databank`.`dt_borrow_manage_borrow_apply` (`id`, `type`, `apply_code`, `geodata_id`, `apply_user`, `apply_reason`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`, `status`, `is_deleted`) VALUES (2, 'offline', 'TOF-20220103-00x', 10087, 51, '这是什么 申请一下', '无', '2023-06-07 15:21:11', '2023-06-07 07:21:10', NULL, NULL, 0, 0);
INSERT INTO `hb_databank`.`dt_borrow_manage_borrow_apply` (`id`, `type`, `apply_code`, `geodata_id`, `apply_user`, `apply_reason`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`, `status`, `is_deleted`) VALUES (3, 'offline', 'TOF-20230103-010', 10010, 50, '是资料啊 那就申请吧', '无', '2023-06-07 15:27:09', '2023-06-08 11:23:08', NULL, NULL, 0, 0);

CREATE TABLE `dt_borrow_manage_borrow_apply_user_detail` (
  `id` bigint NOT NULL COMMENT '唯一标识',
  `apply_id` bigint DEFAULT NULL COMMENT '申请id',
  `user_id` bigint DEFAULT NULL COMMENT '申请用户id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '申请用户名称',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '申请用户手机号',
  `identity_card` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '申请用户身份证号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '申请用户邮箱',
  `unit_id` bigint DEFAULT NULL COMMENT '申请用户单位id',
  `unit_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '申请用户单位名称',
  `identity_card_font_file_id` bigint DEFAULT NULL COMMENT '身份证正面照片id,关联file服务,用于预览身份证照片',
  `identity_card_back_file_id` bigint DEFAULT NULL COMMENT '身份证反面照片id,关联file服务,用于预览身份证照片',
  `unit_support_file_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '单位出具的文件id,多个id使用","进行分割',
  `secret_cert_file_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '涉密证书文件id,多个id使用","进行分割',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建人id',
  `update_by` bigint DEFAULT NULL COMMENT '编辑者id',
  `status` tinyint(1) DEFAULT '0' COMMENT '业务状态',
  `is_deleted` tinyint unsigned DEFAULT '0' COMMENT '删除标记',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='借阅信息-借阅申请用户信息详情';


INSERT INTO `hb_databank`.`dt_borrow_manage_borrow_apply_user_detail` (`id`, `apply_id`, `user_id`, `name`, `phone`, `identity_card`, `email`, `unit_id`, `unit_name`, `identity_card_font_file_id`, `identity_card_back_file_id`, `unit_support_file_ids`, `secret_cert_file_ids`, `remark`, `create_time`, `update_time`, `create_by`, `update_by`, `status`, `is_deleted`) VALUES (100, 1, 50, 'cheese1', '13377778888', '422232199902030040', 'cheese@163.com', 20, 'cheese研究院', NULL, NULL, NULL, NULL, '无', '2023-06-07 15:44:57', '2023-06-07 07:45:06', NULL, NULL, 0, 0);