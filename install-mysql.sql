/*
Navicat MySQL Data Transfer

Source Server         : 本地Mysql
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : bbs

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-17 17:38:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for bbs_message
-- ----------------------------
DROP TABLE IF EXISTS `bbs_message`;
CREATE TABLE `bbs_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `topic_id` int(11) DEFAULT NULL,
  `status` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bbs_message
-- ----------------------------
INSERT INTO `bbs_message` VALUES ('1', '1', '59', '1');
INSERT INTO `bbs_message` VALUES ('2', '1', '73', '1');
INSERT INTO `bbs_message` VALUES ('3', '97', '71', '1');
INSERT INTO `bbs_message` VALUES ('4', '95', '78', '1');
INSERT INTO `bbs_message` VALUES ('5', '99', '79', '1');
INSERT INTO `bbs_message` VALUES ('6', '95', '75', '0');

-- ----------------------------
-- Table structure for bbs_module
-- ----------------------------
DROP TABLE IF EXISTS `bbs_module`;
CREATE TABLE `bbs_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `detail` varchar(100) DEFAULT NULL,
  `turn` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bbs_module
-- ----------------------------
INSERT INTO `bbs_module` VALUES ('1', '课程', '', '1');
INSERT INTO `bbs_module` VALUES ('2', 'BBS', null, '2');

-- ----------------------------
-- Table structure for bbs_post
-- ----------------------------
DROP TABLE IF EXISTS `bbs_post`;
CREATE TABLE `bbs_post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `content` text NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `has_reply` bit(1) NOT NULL DEFAULT b'0',
  `update_time` timestamp NULL DEFAULT NULL,
  `pros` int(11) DEFAULT '0',
  `cons` int(11) DEFAULT '0',
  `is_accept` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `topicID_P` (`topic_id`),
  KEY `userID_P` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=269 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bbs_post
-- ----------------------------
INSERT INTO `bbs_post` VALUES ('201', '59', '1', '发布一', '2016-07-13 10:52:31', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('202', '61', '1', '<p>&nbsp; &nbsp; &nbsp;dsf df&nbsp;</p>', '2016-07-13 11:47:17', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('203', '61', '1', '<p>&nbsp;<img src=\"/codeweb//bbs/showPic/1468381645615blob.png\" _src=\"/codeweb//bbs/showPic/1468381645615blob.png\"/></p>', '2016-07-13 11:47:29', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('209', '64', '1', '<p>&nbsp; &nbsp; &nbsp;sdf sdfs</p>', '2016-07-13 13:25:37', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('210', '65', '1', '<p>&nbsp; &nbsp; &nbsp;sdfdfsdfsdfsdf</p>', '2016-07-13 13:27:06', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('211', '66', '1', '<p>sdfsdfsd</p>', '2016-07-13 13:27:28', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('212', '66', '1', '<p>&nbsp; &nbsp; &nbsp;dsfsdf&nbsp;</p>', '2016-07-13 13:27:47', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('213', '66', '1', '<p><a href=\"http://127.0.0.1:7700/codeweb/bbs/topic/66-1\" target=\"_blank\" title=\"课程\">http://127.0.0.1:7700/codeweb/bbs/topic/66-1&nbsp;</a></p>', '2016-07-13 13:47:07', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('214', '67', '1', '<h2>第三方斯蒂芬放到</h2><p>dfdfdf<br/></p><p><br/></p><p><br/></p>', '2016-07-13 13:49:12', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('215', '68', '1', '<p><img src=\"/codeweb//bbs/showPic/1468389086446blob.png\" _src=\"/codeweb//bbs/showPic/1468389086446blob.png\"/></p>', '2016-07-13 13:51:28', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('216', '69', '4', '<p><img src=\"/codeweb//bbs/showPic/1468391755464blob.png\" _src=\"/codeweb//bbs/showPic/1468391755464blob.png\" style=\"width: 754px; height: 585px;\"/></p><p><br/></p><p><br/></p><p>ok，试试看了多发点 多发点，明天搞</p>', '2016-07-13 14:35:57', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('217', '69', '4', '<p>&nbsp; &nbsp;<img src=\"/codeweb//bbs/showPic/1468391773228blob.png\" _src=\"/codeweb//bbs/showPic/1468391773228blob.png\" style=\"width: 680px; height: 445px;\"/></p>', '2016-07-13 14:36:14', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('218', '69', '4', '<p>什么时候讲？</p>', '2016-07-13 14:37:49', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('219', '69', '4', '<p><img src=\"/codeweb//bbs/showPic/1468392229548blob.png\" _src=\"/codeweb//bbs/showPic/1468392229548blob.png\" style=\"width: 700px; height: 444px;\"/></p>', '2016-07-13 14:43:51', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('220', '70', '95', '打发第三方\r\n', '2016-12-06 20:31:04', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('221', '70', '95', '### **李宗翰**', '2016-12-06 20:32:32', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('225', '59', '95', '发布2', '2016-12-10 22:44:52', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('226', '59', '95', '发布三', '2016-12-10 22:44:58', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('227', '59', '95', '发布四', '2016-12-10 22:45:50', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('228', '59', '95', '发布无', '2016-12-10 22:45:56', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('229', '59', '95', '发布六', '2016-12-10 22:46:03', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('230', '59', '95', '发布六', '2016-12-10 22:57:22', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('231', '59', '95', 'dfsdf  sfsdf ', '2016-12-10 23:17:02', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('232', '59', '95', 'sdfsf ', '2016-12-10 23:17:27', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('233', '59', '95', 'sdfsf ', '2016-12-10 23:19:00', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('234', '59', '95', 'dfdsf ', '2016-12-11 00:08:16', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('235', '59', '95', 'df ', '2016-12-11 00:08:19', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('237', '59', '95', 'dsfsdfs\r\ndsfdsfsd\r\n[sdfsffdf](http://163.com \"sdfsffdf\")\r\nsdfsdf\r\n## dfdfdfdfdf\r\n', '2016-12-11 15:49:39', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('241', '59', '1', '<pre><code class=\"lang-java\"><br></code></pre>', '2016-12-26 21:46:13', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('242', '59', '1', '<p>sdfsdfsdf</p>', '2016-12-26 21:46:21', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('243', '59', '95', '<p>的说法是否</p><pre><code class=\"lang-java\"> public static void main(String[] args){\r\n&nbsp;&nbsp;&nbsp;&nbsp;\r\n&nbsp;&nbsp;}</code></pre>', '2016-12-26 22:01:13', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('262', '74', '95', '<p>测试我的新手</p>', '2017-10-26 19:00:45', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('263', '74', '95', '<p>测试我的新功能</p>', '2017-10-26 19:01:19', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('264', '75', '95', '<p>beetl 是最好的模板语言 ！！</p>', '2018-01-24 20:09:15', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('265', '76', '95', '<p>作为中国国家主席，习近平在繁忙的公务活动中，不仅展示了他的睿智严谨，还留下了许多风趣幽默的言行。Most people have only seen the serious side of Chinese President Xi Jinping. But he also makes time for some lighthearted moments despite his packed daily schedule. Not only is Xi a statesman, he is also a football fan, a world traveler and occasionally a historian.客串文化讲解员 Witty interpreter“你们看，我的祖先很魁梧吧。<br></p><p><br></p><p>作为中国国家主席，习近平在繁忙的公务活动中，不仅展示了他的睿智严谨，还留下了许多风趣幽默的言行。Most people have only seen the serious side of Chinese President Xi Jinping. But he also makes time for some lighthearted moments despite his packed daily schedule. Not only is Xi a statesman, he is also a football fan, a world traveler and occasionally a historian.客串文化讲解员 Witty interpreter“你们看，我的祖先很魁梧吧。<br></p>', '2018-01-24 20:14:06', '\0', null, '0', '0', '0');
INSERT INTO `bbs_post` VALUES ('266', '77', '95', '<p>abc沙发上是否sdfsdf&nbsp;</p>', '2018-01-24 20:18:29', '\0', null, '0', '0', '0');

-- ----------------------------
-- Table structure for bbs_reply
-- ----------------------------
DROP TABLE IF EXISTS `bbs_reply`;
CREATE TABLE `bbs_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL DEFAULT '1',
  `post_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `content` varchar(300) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `topicID_R` (`topic_id`),
  KEY `postID_R` (`post_id`),
  KEY `userID_R` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bbs_reply
-- ----------------------------
INSERT INTO `bbs_reply` VALUES ('3', '59', '201', '1', '三东方闪电', '2016-07-13 10:52:40');
INSERT INTO `bbs_reply` VALUES ('4', '59', '201', '1', '辅导费', '2016-07-13 10:52:42');
INSERT INTO `bbs_reply` VALUES ('5', '59', '201', '1', '第三代', '2016-07-13 11:09:03');
INSERT INTO `bbs_reply` VALUES ('6', '61', '203', '1', 'dsfds ', '2016-07-13 11:47:33');
INSERT INTO `bbs_reply` VALUES ('7', '61', '203', '1', 'df ', '2016-07-13 11:47:35');
INSERT INTO `bbs_reply` VALUES ('12', '66', '211', '1', 'fdfdf', '2016-07-13 13:27:52');
INSERT INTO `bbs_reply` VALUES ('13', '68', '215', '1', '好困难', '2016-07-13 13:51:38');
INSERT INTO `bbs_reply` VALUES ('14', '69', '216', '4', '看着不错', '2016-07-13 14:37:30');
INSERT INTO `bbs_reply` VALUES ('15', '69', '216', '4', '精彩', '2016-07-13 14:37:34');
INSERT INTO `bbs_reply` VALUES ('16', '69', '218', '4', '有时间就讲', '2016-07-13 14:43:19');
INSERT INTO `bbs_reply` VALUES ('17', '65', '210', '1', 'sfdsf', '2016-12-05 19:38:04');
INSERT INTO `bbs_reply` VALUES ('18', '65', '210', '1', 'dfdfd', '2016-12-05 19:38:07');
INSERT INTO `bbs_reply` VALUES ('19', '65', '210', '1', 'sdfdsf', '2016-12-05 19:38:54');
INSERT INTO `bbs_reply` VALUES ('20', '65', '210', '1', 'dfdf ', '2016-12-05 19:38:56');
INSERT INTO `bbs_reply` VALUES ('21', '70', '221', '95', '你好', '2016-12-06 20:32:38');
INSERT INTO `bbs_reply` VALUES ('25', '59', '226', '1', 'sfsdfsdfssfsdf', '2016-12-26 21:45:46');
INSERT INTO `bbs_reply` VALUES ('26', '59', '227', '95', 'kansfsfsfsdfsdfdsfdsfdsfdsf', '2017-02-06 21:31:14');
INSERT INTO `bbs_reply` VALUES ('34', '77', '266', '95', 'sfsf  开始算算', '2018-01-24 20:52:26');

-- ----------------------------
-- Table structure for bbs_topic
-- ----------------------------
DROP TABLE IF EXISTS `bbs_topic`;
CREATE TABLE `bbs_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `module_id` int(11) NOT NULL,
  `post_count` int(11) NOT NULL DEFAULT '1',
  `reply_count` int(11) NOT NULL DEFAULT '0',
  `pv` int(11) NOT NULL DEFAULT '0',
  `content` varchar(150) NOT NULL,
  `emotion` tinyint(2) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_nice` bit(1) NOT NULL DEFAULT b'0',
  `is_up` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `moduleID_T` (`module_id`),
  KEY `userID_T` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bbs_topic
-- ----------------------------
INSERT INTO `bbs_topic` VALUES ('59', '1', '2', '17', '0', '87', '再发表一次看看那', null, '2016-07-13 00:00:00', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('60', '1', '2', '3', '0', '16', '地方对双方都', null, '2016-07-13 11:45:14', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('61', '1', '2', '2', '0', '4', 'dfdf ', null, '2016-07-13 11:47:17', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('64', '1', '2', '1', '0', '3', 'sdfsdf', null, '2016-07-13 13:25:37', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('65', '1', '1', '1', '0', '12', 'sfsfs', null, '2016-07-13 13:27:06', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('66', '1', '1', '3', '0', '16', 'hello go', null, '2016-07-13 13:27:28', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('67', '1', '2', '1', '0', '2', '', null, '2016-07-13 13:49:12', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('68', '1', '1', '1', '0', '11', '关于什么什么的课程卡缴纳困难是发顺丰的', null, '2016-07-13 13:51:28', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('69', '4', '1', '4', '0', '98', 'Zookeeper', null, '2016-07-13 14:35:57', '', '\0');
INSERT INTO `bbs_topic` VALUES ('70', '95', '2', '2', '0', '6', '打发第三方\r\n', null, '2016-12-06 20:31:04', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('74', '95', '1', '2', '0', '7', '我的新书1', null, '2017-10-26 19:00:45', '', '');
INSERT INTO `bbs_topic` VALUES ('75', '95', '1', '1', '0', '4', '发个帖子看看那', null, '2018-01-24 20:09:15', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('76', '95', '1', '1', '0', '2', '没什么看看', null, '2018-01-24 20:14:05', '\0', '\0');
INSERT INTO `bbs_topic` VALUES ('77', '95', '1', '3', '0', '21', '都是方式地方', null, '2018-01-24 20:18:29', '', '');

-- ----------------------------
-- Table structure for bbs_user
-- ----------------------------
DROP TABLE IF EXISTS `bbs_user`;
CREATE TABLE `bbs_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT '0' COMMENT '积分',
  `level` int(11) DEFAULT '1' COMMENT '积分换算成等级，新生，老生，班主任，教导主任，校长',
  `balance` int(11) DEFAULT '0' COMMENT '积分余额',
  `corp` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bbs_user
-- ----------------------------
INSERT INTO `bbs_user` VALUES ('1', 'xxx', 'e10adc3949ba59abbe56e057f20f883e', 'xxx', '54', '1', '54', null);
INSERT INTO `bbs_user` VALUES ('4', '李家智', 'e10adc3949ba59abbe56e057f20f883e', null, '140', '2', '0', null);
INSERT INTO `bbs_user` VALUES ('5', '赵晴文', 'e10adc3949ba59abbe56e057f20f883e', 'zhaoqingwen@coamc.com', '1000', '5', '0', null);
INSERT INTO `bbs_user` VALUES ('6', '石萌', 'e10adc3949ba59abbe56e057f20f883e', 'shimeng@coamc.com', '12', '1', '0', null);
INSERT INTO `bbs_user` VALUES ('95', 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'xxxx@coamc.com', '255', '3', '255', null);
INSERT INTO `bbs_user` VALUES ('96', 'lijiazhi', '202cb962ac59075b964b07152d234b70', '123@123.com', '0', '1', null, 'it');
INSERT INTO `bbs_user` VALUES ('97', 'hank', 'e10adc3949ba59abbe56e057f20f883e', 'hank@163.com', '22', '1', '22', 'dfdsf');
INSERT INTO `bbs_user` VALUES ('98', 'test1', 'e10adc3949ba59abbe56e057f20f883e', '123@123.com', '0', '0', '0', '11');
INSERT INTO `bbs_user` VALUES ('99', 'test11', 'f696282aa4cd4f614aa995190cf442fe', 'test1@163.com', '29', '1', '29', '天天公司');
INSERT INTO `bbs_user` VALUES ('100', 'adb', 'e10adc3949ba59abbe56e057f20f883e', 'xxx@126.com', '29', '1', '29', 'cc');
