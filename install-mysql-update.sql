-- ----------------------------
-- 老版本2.0 升级用
-- ----------------------------
ALTER TABLE `bbs`.`bbs_post` 
ADD COLUMN `pros` INT(11) NULL DEFAULT 0 COMMENT '' AFTER `update_time`,
ADD COLUMN `cons` INT(11) NULL DEFAULT 0 COMMENT '' AFTER `pros`,
ADD COLUMN `is_accept` INT(11) NULL DEFAULT 0 COMMENT '' AFTER `cons`;