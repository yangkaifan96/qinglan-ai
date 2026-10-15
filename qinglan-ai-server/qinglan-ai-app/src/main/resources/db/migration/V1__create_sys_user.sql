-- 系统用户表
CREATE TABLE `sys_user`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`      VARCHAR(64)  NOT NULL COMMENT '登录账号',
    `password_hash` VARCHAR(255) NOT NULL COMMENT '密码哈希',
    `nickname`      VARCHAR(64)  NOT NULL COMMENT '用户昵称',
    `status`        TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：0禁用，1正常',
    `last_login_at` DATETIME(3)           DEFAULT NULL COMMENT '最近登录时间',
    `created_at`    DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `updated_at`    DATETIME(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3)
        ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',

    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_sys_user_username` (`username`),
    CONSTRAINT `chk_sys_user_status` CHECK (`status` IN (0, 1))
) ENGINE = InnoDB COMMENT ='系统用户表';
