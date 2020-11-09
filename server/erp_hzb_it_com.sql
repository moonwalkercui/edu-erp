/*
 Navicat Premium Data Transfer

 Source Server         : 187.88
 Source Server Type    : MySQL
 Source Server Version : 50557
 Source Host           : 39.105.187.88:3306
 Source Schema         : erp_hzb_it_com

 Target Server Type    : MySQL
 Target Server Version : 50557
 File Encoding         : 65001

 Date: 27/10/2020 22:48:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for adm
-- ----------------------------
DROP TABLE IF EXISTS `adm`;
CREATE TABLE `adm`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(191) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `reg_time` datetime NULL DEFAULT NULL COMMENT '注册时间',
  `ip` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后登录ip',
  `login_num` int(5) NOT NULL DEFAULT 0 COMMENT '总登录次数',
  `status` tinyint(2) NULL DEFAULT 0 COMMENT '是否禁用/离职 1 是 -1 否',
  `is_staff` tinyint(1) NULL DEFAULT 1 COMMENT '是否是职员 1 是 -1 否',
  `mistake_times` tinyint(1) NULL DEFAULT 0,
  `sort_num` int(6) NULL DEFAULT 0,
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gender` tinyint(1) NULL DEFAULT 1 COMMENT '性别',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 107 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of adm
-- ----------------------------
INSERT INTO `adm` VALUES (1, 'admin', 'c78b6663d47cfbdb4d65ea51c104044e', '', NULL, 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKKanXkQ9KWDJ7y00TXOEicxgIWFiccBcE4ia8oNekf8lOnlRcx45QaDot8iaiaZX7e3Osl2C8BQibh7XUA/132', '2020-08-29 14:04:42', NULL, '39.91.105.126', 2204, 1, 0, 0, 0, '讲师简介', 1);

-- ----------------------------
-- Table structure for advertisement
-- ----------------------------
DROP TABLE IF EXISTS `advertisement`;
CREATE TABLE `advertisement`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `image` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `position` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'home',
  `edit_time` datetime NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `type` tinyint(1) NULL DEFAULT 0,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '/',
  `sort_num` int(5) NULL DEFAULT 0,
  `status` tinyint(4) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of advertisement
-- ----------------------------
INSERT INTO `advertisement` VALUES (6, 'pc首页banner（1920*800）', 'http://www.xinyangedu.com/up/attach/20200426/50b90dd7009d72a5159f2b60b65ae538.jpg', 'pc_banner', '2020-06-10 12:04:06', '2020-04-11 14:19:04', 0, '/', 2, 1);
INSERT INTO `advertisement` VALUES (8, 'pc首页banner（1920*800）', 'http://www.xinyangedu.com/up/attach/20200427/fb4181abdd7616f4c37731e3daa48c88.jpg', 'pc_banner', '2020-06-10 12:04:01', '2020-04-23 13:53:36', 0, '', 3, 1);
INSERT INTO `advertisement` VALUES (9, '关于我们banner（1920*650）', 'http://www.xinyangedu.com/up/attach/20200425/cab442bf4874e4b628f70eaef6a271f0.png', 'about_banner', '2020-04-26 12:25:50', '2020-04-24 11:34:16', 0, '', 0, 1);
INSERT INTO `advertisement` VALUES (10, '学员banner（1920*500）', 'http://www.xinyangedu.com/up/attach/20200424/2b5b464bf3dcf997169d6d1abf89e4f5.jpg', 'student_banner', '2020-04-25 09:53:25', '2020-04-24 11:34:38', 0, '', 0, 1);
INSERT INTO `advertisement` VALUES (11, '新闻banner（1920*500）', 'http://www.xinyangedu.com/up/attach/20200424/9eb4c8f287af58535f9d0d6b0ce2c804.jpg', 'news_banner', '2020-04-24 11:35:14', '2020-04-24 11:34:54', 0, '', 0, 1);
INSERT INTO `advertisement` VALUES (12, '老师banner（1920*500）', 'http://www.xinyangedu.com/up/attach/20200426/13f74d0ca65061fc2909e5653e806f3b.jpg', 'teacher_banner', '2020-04-26 22:44:51', '2020-04-24 11:34:38', 0, '', 0, 1);
INSERT INTO `advertisement` VALUES (13, '文章banner1（1920*500）', 'http://www.xinyangedu.com/up/attach/20200424/2b5b464bf3dcf997169d6d1abf89e4f5.jpg', 'article_banner', '2020-04-25 09:53:25', '2020-04-24 11:34:38', 0, '', 0, 1);
INSERT INTO `advertisement` VALUES (14, '课程banner（1920*500）', 'http://www.xinyangedu.com/up/attach/20200424/2b5b464bf3dcf997169d6d1abf89e4f5.jpg', 'course_banner', '2020-04-25 09:53:25', '2020-04-24 11:34:38', 0, '', 0, 1);
INSERT INTO `advertisement` VALUES (15, '雅思暑假班活动', 'http://www.xinyangedu.com/up/attach/20200610/b5f8a19edf944baec11efedadf23b66a.jpg', 'pc_banner', '2020-06-10 17:47:16', '2020-06-10 12:00:39', 0, 'http://www.xinyangedu.com/article/76.html', 5, 1);
INSERT INTO `advertisement` VALUES (16, '托福暑假课程', 'http://www.xinyangedu.com/up/attach/20200613/b57e78b88a3957fef4f441e1daf59a6e.jpg', 'pc_banner', '2020-06-13 18:08:16', '2020-06-13 17:38:31', 0, 'http://www.xinyangedu.com/article/78.html', 4, 1);

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nav_id` int(11) NULL DEFAULT 0,
  `category_id` int(11) NULL DEFAULT 0,
  `category_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0,',
  `type` tinyint(5) NULL DEFAULT 1 COMMENT '1 普通文章  2 单页',
  `summary` tinytext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `sort_num` int(255) NULL DEFAULT 0,
  `add_time` datetime NULL DEFAULT NULL,
  `edit_time` datetime NULL DEFAULT NULL,
  `status` int(6) NULL DEFAULT 1,
  `seo_keywords` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `seo_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_del` tinyint(1) NULL DEFAULT 0,
  `banner` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 85 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (27, '关于我们', 0, 1, '1', 2, '&nbsp; &nbsp; &nbsp;新阳英语 创办于2006年，是中国具有影响力及知名度的教育培训品牌之一。多年来秉承『Committed to Quali...', '', '&lt;p&gt;&lt;span style=&quot;font-size: 12pt;&quot;&gt;&amp;nbsp; &amp;nbsp; &amp;nbsp;新阳英语 创办于2006年，是中国具有影响力及知名度的教育培训品牌之一。多年来秉承『Committed to Quality English Teaching』的理念，致力于英语教学有效性和实用性的研究。&lt;/span&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;span style=&quot;font-size: 12pt;&quot;&gt;&amp;nbsp; &amp;nbsp; &amp;nbsp;新阳英语&amp;nbsp;针对中国人的思维习惯和英语学习特点和难点，本着『把英语从知识转变为技能』的出发点，从英语语料库的建立、英语思维习惯的养成、英语综合输出的练习三方面构建了全方位、立体化的英语授课与学习体系。作为一家教育机构，新阳英语在英语教学研发、教学服务、教学标准等方面，都成为了高端英语培训行业的代表。&lt;/span&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;span style=&quot;font-size: 12pt;&quot;&gt;&amp;nbsp; &amp;nbsp; 今天，新阳英语&amp;nbsp;有出国语言培训和英语口语培训两大课程中心。 出国语言培训中心有雅思、托福、GRE、GMAT、SAT等出国语言培训项目，以引导和培训学员取得较高的出国语言成绩为培训目标； 英语口语培训中心有养成标准发音、丰富口语素材等各方面进行能力提升的口语基础课，和以面试、商务、生活为目标的实用口语课，以提高英语沟通交流能力为学习目的。&lt;/span&gt;&lt;/p&gt;', 0, '2018-10-27 12:22:13', '2020-06-10 10:56:07', 1, '', '', 1, NULL);
INSERT INTO `article` VALUES (62, '出国考试', 0, 0, '0,', 2, '\r\n\r\n\r\n\r\n\r\n\r\n', '/up/attach/20200427/79d2ebc830c197fcd19e3a9df2a1fc17.jpg', '&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;http://new.xinyangedu.com/up/attach/20200427/12d7659fed6e32a6a61e6c57caebeaf9.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;http://new.xinyangedu.com/up/attach/20200427/0ca78b22b2f9019e8089a0622189c5b3.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;http://new.xinyangedu.com/up/attach/20200427/8e2c8170924cfa8adfe786594e440cc5.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;http://new.xinyangedu.com/up/attach/20200427/438a39973ea566ddca08f8352abd453f.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;http://new.xinyangedu.com/up/attach/20200427/2a512446c72c5f98b19a938feb36218c.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;http://new.xinyangedu.com/up/attach/20200427/0ae0b9f11cb80234facb38fa2143ee36.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;http://new.xinyangedu.com/up/attach/20200427/708f0e68ab7615a491d852ced93de847.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;', 0, '2020-04-26 19:46:55', '2020-06-13 19:07:57', 1, '', '', 0, 'http://new.xinyangedu.com/up/attach/20200426/4e551aa79ebfddb8314ee83248499b97.png');
INSERT INTO `article` VALUES (75, '雅思活动', 0, 0, '0,', 2, '\r\n\r\n', '', '&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200524/e9ac31f095853aa721104fbaa1df1cf7.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;iframe id=&quot;cciframe_A04B81963A59B18E9C33DC5901307461&quot; src=&quot;https://p.bokecc.com/playhtml.bo?vid=A04B81963A59B18E9C33DC5901307461&amp;amp;siteid=7CD260676E910AF8&amp;amp;autoStart=false&amp;amp;playerid=70F1A9646F852BC9&amp;amp;playertype=1&quot; width=&quot;100%&quot; height=&quot;490&quot; frameborder=&quot;0&quot;&gt;&lt;/iframe&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200524/d5c6e554e326824634ef860ac92e900c.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;', 1, '2020-05-31 14:20:39', '2020-05-31 14:20:39', 1, '大连雅思培训 雅思课程', '大连雅思培训 雅思课程', 1, 'http://www.xinyangedu.com/up/attach/20200425/a800125ab93f11a02e93e5cbb59bab93.jpg');
INSERT INTO `article` VALUES (73, '考研英语', 0, 0, '0,', 2, '\r\n\r\n', '/up/attach/20200427/79d2ebc830c197fcd19e3a9df2a1fc17.jpg', '&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;http://new.xinyangedu.com/up/attach/20200511/7cf85cbf9c0f07afa2fbe7d5975af9a7.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;http://new.xinyangedu.com/up/attach/20200511/d10c25e9f18a16383ab938ca9b005126.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;http://new.xinyangedu.com/up/attach/20200511/8130f55124511ff9e925c69688bc5530.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;', 0, '2020-05-11 11:39:00', '2020-05-11 11:39:00', 1, '', '', 0, 'http://new.xinyangedu.com/up/attach/20200424/2b5b464bf3dcf997169d6d1abf89e4f5.jpg');
INSERT INTO `article` VALUES (78, '托福暑假课程', 0, 0, '0,', 2, '\r\n\r\n', '', '&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200613/581d924674f2bf96d419216bd5d26758.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200613/deeca058e97ea5f0dea72353cd2932b9.png&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200613/6e429c1106b82629a478c8dbb3cded04.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;', 0, '2020-06-13 17:41:39', '2020-06-13 18:09:13', 1, '', '', 0, 'http://www.xinyangedu.com/up/attach/20200613/b57e78b88a3957fef4f441e1daf59a6e.jpg');
INSERT INTO `article` VALUES (79, '课程体系', 0, 0, '0,', 2, '\r\n\r\n', '/up/attach/20200427/79d2ebc830c197fcd19e3a9df2a1fc17.jpg', '&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200613/80c40a6b30637d816c79fcbc028f1d9a.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200613/71d1beba563dec164ccd3255371e9d23.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200613/676b02606dd91ec4694419d6921a41c6.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;', 0, '2020-06-13 18:43:55', '2020-06-13 19:14:07', 1, '', '', 0, 'http://www.xinyangedu.com/up/attach/20200426/4e551aa79ebfddb8314ee83248499b97.png');
INSERT INTO `article` VALUES (82, '精彩瞬间', 0, 1, '1', 2, '精彩瞬间 内容准备中...', '/up/attach/20200910/fa8ee3f1312013cd0c79d4fb846a4333.gif', '<p>精彩瞬间 内容准备中...</p>', 0, '2020-09-23 12:28:42', '2020-09-28 21:18:06', 1, '', '', 0, NULL);
INSERT INTO `article` VALUES (84, '测试', 21, 2, '2', 1, '测试', '', '<p>测试</p>', 0, '2020-09-28 21:17:52', '2020-09-28 21:26:27', 1, '', '', 0, NULL);
INSERT INTO `article` VALUES (80, '剑桥MSE课程', 0, 0, '0,', 2, '\r\n\r\n\r\n\r\n&nbsp;', '/up/attach/20200427/79d2ebc830c197fcd19e3a9df2a1fc17.jpg', '&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200613/73fb9f6fc778f4c876287b73e2a94f44.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200613/fcf54d5d5127961e856a8f872fbd2e0a.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200613/b007bf817599921c995b6c24864615a2.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200613/bd863b1ba45813eef7840ffc7147bbda.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&amp;nbsp;&lt;/p&gt;', 0, '2020-06-13 18:46:31', '2020-06-13 18:46:31', 1, '', '', 0, 'http://www.xinyangedu.com/up/attach/20200426/4e551aa79ebfddb8314ee83248499b97.png');
INSERT INTO `article` VALUES (81, '发展历程', 0, 0, '0,', 2, '\r\n\r\n\r\n', '/up/attach/20200427/a88990985ec2d212343183a8bcb44b4f.png', '&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200613/f0c943ad3d950dcf1f89bc53bdf46dca.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200613/eebdd4600f8d62721a8e940d11bc32ec.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200614/5351b571aea98afa79f0ea8fdef73e77.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../../../up/attach/20200614/3137245724872a1720d3cf5a12471ef1.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;', 0, '2020-06-13 21:05:42', '2020-06-14 12:14:54', 1, '', '', 0, 'http://www.xinyangedu.com/up/attach/20200425/cab442bf4874e4b628f70eaef6a271f0.png');

-- ----------------------------
-- Table structure for article_category
-- ----------------------------
DROP TABLE IF EXISTS `article_category`;
CREATE TABLE `article_category`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL DEFAULT 0,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'list',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sort_num` int(6) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of article_category
-- ----------------------------
INSERT INTO `article_category` VALUES (1, 0, 'mark', '关于', 0);
INSERT INTO `article_category` VALUES (2, 0, 'list', '新闻类', 0);

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment`  (
  `att_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '文件原名',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '附件名称',
  `att_dir` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '附件路径',
  `satt_dir` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '压缩图片路径',
  `att_size` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '附件大小',
  `att_type` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '附件类型',
  `pid` int(10) NOT NULL COMMENT '分类ID',
  `time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`att_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1776 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '附件管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attachment
-- ----------------------------
INSERT INTO `attachment` VALUES (1586, 'touxiang6.jpg', '07c5e6dc30d4cdca09683c58f05356d1.jpg', '/up/attach/20200901/07c5e6dc30d4cdca09683c58f05356d1.jpg', '', '32515', 'jpg', 0, '2020-09-01 21:04:34');
INSERT INTO `attachment` VALUES (1584, 'touxiang5.jpg', 'b0d8d59c5cc289ce2f358bd9632ab676.jpg', '/up/attach/20200901/b0d8d59c5cc289ce2f358bd9632ab676.jpg', '', '35022', 'jpg', 0, '2020-09-01 20:59:52');
INSERT INTO `attachment` VALUES (1578, 'WechatIMG259.jpg', '701071415dd6e6a2fa46c3c7e4a5394c.jpg', '/up/attach/20200825/701071415dd6e6a2fa46c3c7e4a5394c.jpg', '', '2370', 'jpg', 0, '2020-08-25 16:45:59');
INSERT INTO `attachment` VALUES (1580, 'touxiang1.jpg', 'edb6962ba40faec06e82e2ba75d6c7f3.jpg', '/up/attach/20200901/edb6962ba40faec06e82e2ba75d6c7f3.jpg', '', '32737', 'jpg', 0, '2020-09-01 20:49:34');
INSERT INTO `attachment` VALUES (1581, 'touxiang2.jpg', '146cf75bf7ab25c02cb897e156096150.jpg', '/up/attach/20200901/146cf75bf7ab25c02cb897e156096150.jpg', '', '30264', 'jpg', 0, '2020-09-01 20:51:44');
INSERT INTO `attachment` VALUES (1582, 'touxiang3.jpg', '178a923d0e0b2d0e9052ec7a61d9f70e.jpg', '/up/attach/20200901/178a923d0e0b2d0e9052ec7a61d9f70e.jpg', '', '29735', 'jpg', 0, '2020-09-01 20:54:27');
INSERT INTO `attachment` VALUES (1583, 'touxiang4.jpg', '012f44961a1eaac3de31b94a67a6c03c.jpg', '/up/attach/20200901/012f44961a1eaac3de31b94a67a6c03c.jpg', '', '31517', 'jpg', 0, '2020-09-01 20:58:03');
INSERT INTO `attachment` VALUES (1577, '试卷预览副本.jpg', '7720eeecfee2188553c0bcc200d274f4.jpg', '/up/attach/20200824/7720eeecfee2188553c0bcc200d274f4.jpg', '', '1006582', 'jpg', 17, '2020-08-24 15:06:04');
INSERT INTO `attachment` VALUES (1587, 'touxiang7.jpg', '04d1d42147526181f9c3350adf34215c.jpg', '/up/attach/20200901/04d1d42147526181f9c3350adf34215c.jpg', '', '30138', 'jpg', 0, '2020-09-01 21:04:35');
INSERT INTO `attachment` VALUES (1725, 'IMG_8888.png', '7777d2bc6bc824013f8ddc978f6d4e86.png', '/up/attach/20200927/7777d2bc6bc824013f8ddc978f6d4e86.png', '', '629031', 'png', -1, '2020-09-27 11:08:47');
INSERT INTO `attachment` VALUES (1726, '大疆手持云台.jpg', '97e939c2fad9bbac4b28edb408111b40.jpg', '/up/attach/20200927/97e939c2fad9bbac4b28edb408111b40.jpg', '', '314443', 'jpg', -1, '2020-09-27 11:09:33');
INSERT INTO `attachment` VALUES (1727, 'IMG_8885.png', 'a44f6e6abab48454a19208ac95df832d.png', '/up/attach/20200927/a44f6e6abab48454a19208ac95df832d.png', '', '559882', 'png', -1, '2020-09-27 11:10:32');
INSERT INTO `attachment` VALUES (1728, 'IMG_8875.png', 'f4d60b68e196fdf83148e3b3aae59bff.png', '/up/attach/20200927/f4d60b68e196fdf83148e3b3aae59bff.png', '', '627360', 'png', -1, '2020-09-27 11:11:35');
INSERT INTO `attachment` VALUES (1729, 'IMG_8876.png', 'e914ce990cc037d5263e5dfcc9442db6.png', '/up/attach/20200927/e914ce990cc037d5263e5dfcc9442db6.png', '', '690441', 'png', -1, '2020-09-27 11:12:55');
INSERT INTO `attachment` VALUES (1730, 'IMG_8882.png', 'bbd3f53573a27aae381b15cdbd4d813a.png', '/up/attach/20200927/bbd3f53573a27aae381b15cdbd4d813a.png', '', '551011', 'png', -1, '2020-09-27 11:22:00');
INSERT INTO `attachment` VALUES (1708, 'DSC00771.jpg', '038db346b9db943c29b31392f9346ad7.jpg', '/up/attach/20200914/038db346b9db943c29b31392f9346ad7.jpg', '', '16261503', 'jpg', 0, '2020-09-14 21:08:12');
INSERT INTO `attachment` VALUES (1709, '笔记本.jpg', '49db83833be4093251d1f4e9392f85db.jpg', '/up/attach/20200917/49db83833be4093251d1f4e9392f85db.jpg', '', '74092', 'jpg', -1, '2020-09-17 08:46:26');
INSERT INTO `attachment` VALUES (1710, '奖章.jpg', '4f9e09a62bc149746729d857c85c938b.jpg', '/up/attach/20200917/4f9e09a62bc149746729d857c85c938b.jpg', '', '95675', 'jpg', -1, '2020-09-17 08:46:27');
INSERT INTO `attachment` VALUES (1711, '流动红旗.jpg', '773e31eba0e79cd55949cb847f741738.jpg', '/up/attach/20200917/773e31eba0e79cd55949cb847f741738.jpg', '', '100173', 'jpg', -1, '2020-09-17 08:46:28');
INSERT INTO `attachment` VALUES (1712, '塑封膜.jpg', '3dfa9eeef5c11a75fd64fe2ab49c602d.jpg', '/up/attach/20200917/3dfa9eeef5c11a75fd64fe2ab49c602d.jpg', '', '109317', 'jpg', -1, '2020-09-17 08:46:29');
INSERT INTO `attachment` VALUES (1713, '外教盒子.jpg', 'e84c02f1b710f0f7e553484c42dc5d0b.jpg', '/up/attach/20200917/e84c02f1b710f0f7e553484c42dc5d0b.jpg', '', '393535', 'jpg', -1, '2020-09-17 08:46:30');
INSERT INTO `attachment` VALUES (1714, '颜料.jpg', '725f5b0ed035b250c934df5d79abd843.jpg', '/up/attach/20200917/725f5b0ed035b250c934df5d79abd843.jpg', '', '99139', 'jpg', -1, '2020-09-17 08:46:31');
INSERT INTO `attachment` VALUES (1715, 'U盘.jpg', 'c5d0e5f7faea21c4057032ac90d3a7da.jpg', '/up/attach/20200917/c5d0e5f7faea21c4057032ac90d3a7da.jpg', '', '115947', 'jpg', -1, '2020-09-17 10:13:55');
INSERT INTO `attachment` VALUES (1716, '纽扣.jpg', '9f02d909efcb5392580284b8ed77998e.jpg', '/up/attach/20200917/9f02d909efcb5392580284b8ed77998e.jpg', '', '122305', 'jpg', -1, '2020-09-17 10:13:56');
INSERT INTO `attachment` VALUES (1717, '针线.jpg', '2d5b5333259bded50bb9d25b049d3654.jpg', '/up/attach/20200917/2d5b5333259bded50bb9d25b049d3654.jpg', '', '104947', 'jpg', -1, '2020-09-17 10:13:57');
INSERT INTO `attachment` VALUES (1718, 'IMG_8879.png', '7fc11ca6a631b9c758ea4776f21677fc.png', '/up/attach/20200927/7fc11ca6a631b9c758ea4776f21677fc.png', '', '528784', 'png', -1, '2020-09-27 10:45:20');
INSERT INTO `attachment` VALUES (1719, 'IMG_8872.png', 'd11acfa91b96cdc7e08945060ab35ee5.png', '/up/attach/20200927/d11acfa91b96cdc7e08945060ab35ee5.png', '', '663621', 'png', -1, '2020-09-27 10:48:04');
INSERT INTO `attachment` VALUES (1720, 'IMG_8873.png', '277240578d2d7018d76acccb16668df8.png', '/up/attach/20200927/277240578d2d7018d76acccb16668df8.png', '', '581246', 'png', -1, '2020-09-27 10:51:08');
INSERT INTO `attachment` VALUES (1721, 'IMG_8874.png', 'a89d17ccba32445a0fb656a51ff2d989.png', '/up/attach/20200927/a89d17ccba32445a0fb656a51ff2d989.png', '', '665857', 'png', -1, '2020-09-27 10:58:54');
INSERT INTO `attachment` VALUES (1722, 'IMG_8877.png', 'ccb74b5fc0399afa1f520501fab6fe88.png', '/up/attach/20200927/ccb74b5fc0399afa1f520501fab6fe88.png', '', '671778', 'png', -1, '2020-09-27 11:00:03');
INSERT INTO `attachment` VALUES (1723, 'IMG_8878.png', '44598f7d0a102989d66e7bcb20139684.png', '/up/attach/20200927/44598f7d0a102989d66e7bcb20139684.png', '', '608538', 'png', -1, '2020-09-27 11:01:09');
INSERT INTO `attachment` VALUES (1724, 'IMG_8880.png', 'bb2239b1c59b5967ca6716221515305f.png', '/up/attach/20200927/bb2239b1c59b5967ca6716221515305f.png', '', '613865', 'png', -1, '2020-09-27 11:05:18');
INSERT INTO `attachment` VALUES (1703, 'index-4.mp4', 'fc4d5105a80d2e3fab48991ade130d78.mp4', '/up/attach/20200906/fc4d5105a80d2e3fab48991ade130d78.mp4', '', '10105082', 'mp4', 0, '2020-09-06 14:27:46');
INSERT INTO `attachment` VALUES (1704, 'bg.gif', '9e0d28effb54495a424f3dd8050a84f4.gif', '/up/attach/20200908/9e0d28effb54495a424f3dd8050a84f4.gif', '', '3591', 'gif', 0, '2020-09-08 11:38:57');
INSERT INTO `attachment` VALUES (1705, 'bg.gif', 'f7a9be5e0681180341dc87c218b447ad.gif', '/up/attach/20200908/f7a9be5e0681180341dc87c218b447ad.gif', '', '3591', 'gif', 0, '2020-09-08 17:09:23');
INSERT INTO `attachment` VALUES (1706, 'bg.gif', 'fa8ee3f1312013cd0c79d4fb846a4333.gif', '/up/attach/20200910/fa8ee3f1312013cd0c79d4fb846a4333.gif', '', '3591', 'gif', 0, '2020-09-10 20:31:36');
INSERT INTO `attachment` VALUES (1707, '头像.jpeg', '5eeb26ffd8a89eb07081772dc554108d.jpeg', '/up/attach/20200914/5eeb26ffd8a89eb07081772dc554108d.jpeg', '', '197445', 'jpeg', 0, '2020-09-14 21:06:44');
INSERT INTO `attachment` VALUES (1617, '12.jpg', 'fe8eb26540936150acc0058d996d98a1.jpg', '/up/attach/20200903/fe8eb26540936150acc0058d996d98a1.jpg', '', '77904', 'jpg', 0, '2020-09-03 17:28:30');
INSERT INTO `attachment` VALUES (1618, '12.jpg', 'bdf88ce4c59e9fad5a4d55fbfc279524.jpg', '/up/attach/20200903/bdf88ce4c59e9fad5a4d55fbfc279524.jpg', '', '77904', 'jpg', 0, '2020-09-03 17:29:15');
INSERT INTO `attachment` VALUES (1731, 'IMG_8881.png', '5c3d399fc5e015048dc1c25788ae6d57.png', '/up/attach/20200927/5c3d399fc5e015048dc1c25788ae6d57.png', '', '662259', 'png', -1, '2020-09-27 11:22:01');
INSERT INTO `attachment` VALUES (1732, 'IMG_8883.png', '224df57e06b18ebb9ff4f4550cbf6cb0.png', '/up/attach/20200927/224df57e06b18ebb9ff4f4550cbf6cb0.png', '', '463717', 'png', -1, '2020-09-27 11:22:52');
INSERT INTO `attachment` VALUES (1733, 'IMG_8884.png', 'c178de757f8b97bd04251939b0f786af.png', '/up/attach/20200927/c178de757f8b97bd04251939b0f786af.png', '', '1065081', 'png', -1, '2020-09-27 11:22:53');
INSERT INTO `attachment` VALUES (1734, 'IMG_8871.png', '71c17e675ac56aa2260cea1d32e1cfd3.png', '/up/attach/20200927/71c17e675ac56aa2260cea1d32e1cfd3.png', '', '635775', 'png', -1, '2020-09-27 11:27:15');
INSERT INTO `attachment` VALUES (1735, 'IMG_8846.png', '7ada7dfe64827e917d91a39ece992016.png', '/up/attach/20200927/7ada7dfe64827e917d91a39ece992016.png', '', '194063', 'png', -1, '2020-09-27 11:28:08');
INSERT INTO `attachment` VALUES (1736, 'IMG_8847.png', '4c0898cb60d91321203de4a71a107cb8.png', '/up/attach/20200927/4c0898cb60d91321203de4a71a107cb8.png', '', '185525', 'png', -1, '2020-09-27 11:28:58');
INSERT INTO `attachment` VALUES (1737, '核酸检测1.jpg', 'a2782b102ea3916e0cde303a41ae1957.jpg', '/up/attach/20200927/a2782b102ea3916e0cde303a41ae1957.jpg', '', '674568', 'jpg', -1, '2020-09-27 11:33:50');
INSERT INTO `attachment` VALUES (1738, '核酸检测2.jpg', 'e3e9d07ff48c29d5ab824d45f8c47bd0.jpg', '/up/attach/20200927/e3e9d07ff48c29d5ab824d45f8c47bd0.jpg', '', '757583', 'jpg', -1, '2020-09-27 11:34:34');
INSERT INTO `attachment` VALUES (1739, 'IMG_8891.jpg', '81d2dca6df50248f00e040e3f60b9330.jpg', '/up/attach/20200927/81d2dca6df50248f00e040e3f60b9330.jpg', '', '3253046', 'jpg', -1, '2020-09-27 11:41:32');
INSERT INTO `attachment` VALUES (1740, 'IMG_8867.png', '9e709b1c4de04cb5c4a600aeb840fc66.png', '/up/attach/20200927/9e709b1c4de04cb5c4a600aeb840fc66.png', '', '940837', 'png', -1, '2020-09-27 11:42:30');
INSERT INTO `attachment` VALUES (1741, 'IMG_B80628049D56-1.jpeg', '53b3c01e20b0690547482856a5842838.jpeg', '/up/attach/20200927/53b3c01e20b0690547482856a5842838.jpeg', '', '787485', 'jpeg', -1, '2020-09-27 11:49:52');
INSERT INTO `attachment` VALUES (1742, 'IMG_8869.png', 'c1e17d4af06ea7c6f5eebd59b2f63dca.png', '/up/attach/20200927/c1e17d4af06ea7c6f5eebd59b2f63dca.png', '', '231006', 'png', -1, '2020-09-27 11:51:43');
INSERT INTO `attachment` VALUES (1743, 'IMG_8870.png', 'a118426d5d939ddf9ea4b88b02604cfe.png', '/up/attach/20200927/a118426d5d939ddf9ea4b88b02604cfe.png', '', '239743', 'png', -1, '2020-09-27 11:51:44');
INSERT INTO `attachment` VALUES (1744, 'IMG_8860.png', '5644bf85d2fe0a6e2388df3b17e14b5d.png', '/up/attach/20200927/5644bf85d2fe0a6e2388df3b17e14b5d.png', '', '209062', 'png', -1, '2020-09-27 11:53:19');
INSERT INTO `attachment` VALUES (1745, 'IMG_8859.png', 'd6df9b43f77e41464eeb97dd76618508.png', '/up/attach/20200927/d6df9b43f77e41464eeb97dd76618508.png', '', '243373', 'png', -1, '2020-09-27 11:53:20');
INSERT INTO `attachment` VALUES (1746, 'IMG_8868.png', '80b38da100771d1276918571e9357bae.png', '/up/attach/20200927/80b38da100771d1276918571e9357bae.png', '', '926799', 'png', -1, '2020-09-27 11:54:51');
INSERT INTO `attachment` VALUES (1747, 'IMG_8862.png', 'aca7ba56826ea5065367a7a7f5482651.png', '/up/attach/20200927/aca7ba56826ea5065367a7a7f5482651.png', '', '221408', 'png', -1, '2020-09-27 11:56:37');
INSERT INTO `attachment` VALUES (1748, 'IMG_8864.png', '74a2003bdf7838ca6294e34c79b4ebf2.png', '/up/attach/20200927/74a2003bdf7838ca6294e34c79b4ebf2.png', '', '213051', 'png', -1, '2020-09-27 11:56:39');
INSERT INTO `attachment` VALUES (1749, 'IMG_8863.png', '240afb836d19c0b21e62d20364210c10.png', '/up/attach/20200927/240afb836d19c0b21e62d20364210c10.png', '', '206507', 'png', -1, '2020-09-27 11:57:50');
INSERT INTO `attachment` VALUES (1750, 'IMG_8865.png', 'fc83369ba8d392528c31727b2036a7fd.png', '/up/attach/20200927/fc83369ba8d392528c31727b2036a7fd.png', '', '211991', 'png', -1, '2020-09-27 11:57:51');
INSERT INTO `attachment` VALUES (1751, 'IMG_8848.png', '334ebe9a51c3e8ab678f167b7b776139.png', '/up/attach/20200927/334ebe9a51c3e8ab678f167b7b776139.png', '', '221782', 'png', -1, '2020-09-27 12:00:58');
INSERT INTO `attachment` VALUES (1752, 'IMG_8849.png', '7cdb1dd222aaaec4f5594f22301c57b2.png', '/up/attach/20200927/7cdb1dd222aaaec4f5594f22301c57b2.png', '', '230535', 'png', -1, '2020-09-27 12:02:08');
INSERT INTO `attachment` VALUES (1753, 'IMG_8856.png', '07bd13001b4941721c9b967e9f5901d8.png', '/up/attach/20200927/07bd13001b4941721c9b967e9f5901d8.png', '', '227202', 'png', -1, '2020-09-27 12:02:10');
INSERT INTO `attachment` VALUES (1754, 'IMG_8857.png', '2e855cfe26452113f9cde0262f457084.png', '/up/attach/20200927/2e855cfe26452113f9cde0262f457084.png', '', '196204', 'png', -1, '2020-09-27 12:02:28');
INSERT INTO `attachment` VALUES (1755, 'IMG_8893.png', 'e17b4ffbce6f192af5e0f0357ced3ad9.png', '/up/attach/20200927/e17b4ffbce6f192af5e0f0357ced3ad9.png', '', '516367', 'png', -1, '2020-09-27 12:08:33');
INSERT INTO `attachment` VALUES (1756, 'IMG_8850.png', '6bc9b8fd281b3f84994e8b1f472fe3cc.png', '/up/attach/20200927/6bc9b8fd281b3f84994e8b1f472fe3cc.png', '', '214781', 'png', -1, '2020-09-27 12:08:34');
INSERT INTO `attachment` VALUES (1757, 'IMG_8851.png', '999f68adcdcffa54ead9881295ae86a4.png', '/up/attach/20200927/999f68adcdcffa54ead9881295ae86a4.png', '', '213431', 'png', -1, '2020-09-27 12:08:35');
INSERT INTO `attachment` VALUES (1758, 'IMG_8855.png', 'b49abcf369fb82eb97400ba0841e0735.png', '/up/attach/20200927/b49abcf369fb82eb97400ba0841e0735.png', '', '198627', 'png', -1, '2020-09-27 12:08:36');
INSERT INTO `attachment` VALUES (1759, 'IMG_8852.png', '1cc64bf072780533f3c233e3302f0fdf.png', '/up/attach/20200927/1cc64bf072780533f3c233e3302f0fdf.png', '', '170393', 'png', -1, '2020-09-27 12:10:52');
INSERT INTO `attachment` VALUES (1760, 'IMG_8853.png', '123a740e61b642c5faaa2a8ff0ca4c31.png', '/up/attach/20200927/123a740e61b642c5faaa2a8ff0ca4c31.png', '', '161627', 'png', -1, '2020-09-27 12:10:53');
INSERT INTO `attachment` VALUES (1761, 'IMG_8854.png', 'f7904543f75d75aed1cf4039a10e2628.png', '/up/attach/20200927/f7904543f75d75aed1cf4039a10e2628.png', '', '195133', 'png', -1, '2020-09-27 12:10:54');
INSERT INTO `attachment` VALUES (1762, 'IMG_8861.png', 'b6b86b730467acbfff1efa4018835b5d.png', '/up/attach/20200927/b6b86b730467acbfff1efa4018835b5d.png', '', '221638', 'png', -1, '2020-09-27 12:11:58');
INSERT INTO `attachment` VALUES (1763, 'IMG_8858.png', '72ed518010970525070fe9c7848f2bea.png', '/up/attach/20200927/72ed518010970525070fe9c7848f2bea.png', '', '225783', 'png', -1, '2020-09-27 12:13:13');
INSERT INTO `attachment` VALUES (1764, 'IMG_5B00AF1CD1CC-1.jpeg', '9158f09a5947eefa24b46a25ecb0950c.jpeg', '/up/attach/20200927/9158f09a5947eefa24b46a25ecb0950c.jpeg', '', '393485', 'jpeg', -1, '2020-09-27 12:17:47');
INSERT INTO `attachment` VALUES (1770, 'bg.gif', '43c25b4374a3130f554a712dc86767d4.gif', '/up/attach/20200927/43c25b4374a3130f554a712dc86767d4.gif', '', '3591', 'gif', -1, '2020-09-27 14:52:30');
INSERT INTO `attachment` VALUES (1773, 'tomcat.jpg', '58d14cd631a5f77578a37e77a322235e.jpg', '/up/attach/20201010/58d14cd631a5f77578a37e77a322235e.jpg', '', '16723', 'jpg', 0, '2020-10-10 12:48:54');
INSERT INTO `attachment` VALUES (1774, 'tomcat.jpg', 'e978c4d99fd6a049acc732797400973c.jpg', '/up/attach/20201010/e978c4d99fd6a049acc732797400973c.jpg', '', '16723', 'jpg', 0, '2020-10-10 12:48:54');
INSERT INTO `attachment` VALUES (1775, 'tomcat.jpg', 'dc4faecd0dd5e683257ccc2bfdb1ec6b.jpg', '/up/attach/20201010/dc4faecd0dd5e683257ccc2bfdb1ec6b.jpg', '', '16723', 'jpg', -1, '2020-10-10 12:48:54');

-- ----------------------------
-- Table structure for attachment_category
-- ----------------------------
DROP TABLE IF EXISTS `attachment_category`;
CREATE TABLE `attachment_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NULL DEFAULT 0 COMMENT '父级ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `enname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类目录',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '附件分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attachment_category
-- ----------------------------
INSERT INTO `attachment_category` VALUES (17, 0, 'KB0', NULL);
INSERT INTO `attachment_category` VALUES (52, 0, 'KB1', NULL);

-- ----------------------------
-- Table structure for attendance
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `staff_id` int(11) NOT NULL,
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '1 正常上班  2 休假中',
  `date` date NOT NULL COMMENT '日期',
  `come_time` datetime NULL DEFAULT NULL,
  `leave_time` datetime NULL DEFAULT NULL,
  `be_late` tinyint(1) NULL DEFAULT -1 COMMENT '是否迟到 -1否 0补签 1 是',
  `late_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `leave_early` tinyint(1) NULL DEFAULT -1 COMMENT '是否早退 -1否 0补签 1 是',
  `leave_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `offduty_id` int(11) NULL DEFAULT NULL COMMENT '休假id',
  `verifier_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `verify_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `staff_id`(`staff_id`) USING BTREE,
  INDEX `date`(`date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '考勤记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of attendance
-- ----------------------------
INSERT INTO `attendance` VALUES (16, 3, NULL, '2020-09-22', NULL, '2020-09-22 18:17:35', -1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (17, 4, NULL, '2020-09-22', NULL, '2020-09-22 18:23:31', 1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (18, 4, NULL, '2020-09-23', '2020-09-23 07:20:28', '2020-09-23 13:14:53', -1, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (19, 2, NULL, '2020-09-23', '2020-09-23 07:31:43', '2020-09-23 17:31:48', 1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (20, 1, NULL, '2020-09-23', '2020-09-23 07:32:00', '2020-09-23 17:44:57', 1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (21, 11, NULL, '2020-09-23', '2020-09-23 08:00:11', '2020-09-23 17:33:36', 1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (22, 8, NULL, '2020-09-23', NULL, '2020-09-23 17:49:09', -1, '123123', -1, NULL, NULL, 'cui', '123123');
INSERT INTO `attendance` VALUES (23, 4, NULL, '2020-09-24', '2020-09-24 07:12:37', NULL, -1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (24, 1, NULL, '2020-09-24', '2020-09-24 07:26:47', '2020-09-24 17:33:48', -1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (25, 2, NULL, '2020-09-24', '2020-09-24 07:27:34', '2020-09-24 17:33:10', -1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (26, 11, NULL, '2020-09-24', '2020-09-24 07:30:59', '2020-09-24 17:35:24', -1, '132', -1, NULL, NULL, 'cui', '2131e4');
INSERT INTO `attendance` VALUES (27, 8, NULL, '2020-09-24', '2020-09-24 07:32:02', NULL, -1, '12313', -1, NULL, NULL, 'cui', '123213');
INSERT INTO `attendance` VALUES (28, 11, NULL, '2020-09-25', '2020-09-25 07:25:25', '2020-09-25 17:35:51', -1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (29, 2, NULL, '2020-09-25', '2020-09-25 07:28:03', NULL, -1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (30, 1, NULL, '2020-09-25', '2020-09-25 07:29:23', NULL, -1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (31, 2, NULL, '2020-09-28', '2020-09-28 07:28:32', '2020-09-28 17:31:21', -1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (32, 1, NULL, '2020-09-28', '2020-09-28 07:28:54', '2020-09-28 17:32:07', -1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (33, 1, NULL, '2020-09-29', '2020-09-29 07:28:54', NULL, -1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (34, 2, NULL, '2020-09-29', '2020-09-29 07:29:19', NULL, -1, NULL, -1, NULL, NULL, NULL, NULL);
INSERT INTO `attendance` VALUES (35, 4, NULL, '2020-09-29', '2020-09-29 09:10:22', NULL, -1, '1234', -1, NULL, NULL, 'cui', '143');

-- ----------------------------
-- Table structure for auth_node
-- ----------------------------
DROP TABLE IF EXISTS `auth_node`;
CREATE TABLE `auth_node`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` smallint(6) UNSIGNED NULL DEFAULT NULL,
  `sort` smallint(6) UNSIGNED NULL DEFAULT 0,
  `is_menu` int(1) UNSIGNED NULL DEFAULT 1 COMMENT '是否是菜单',
  `icon` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `label_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `visible` tinyint(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 184 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_node
-- ----------------------------
INSERT INTO `auth_node` VALUES (7, '内 容', 'article/articles?type=1', 0, 4, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (31, '管理员列表', 'auth/admList', 21, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (32, '新增与编辑账号', 'auth/newadm', 118, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (33, '添加账号', 'auth/addadm', 21, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (34, '修改账号密码', 'auth/changepw', 118, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (35, '管理组列表', 'auth/rolelist', 21, 1, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (36, '新建角色', 'auth/newrole', 21, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (37, '添加角色', 'auth/addRole', 21, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (38, '角色权限列表', 'auth/rolenode', 21, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (39, '编辑权限', 'auth/editRoleNode', 21, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (40, '增加权限节点', 'auth/addNode', 21, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (41, '编辑角色', 'auth/role_Do', 21, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (42, '管理员账号', 'auth/adm_Do', 21, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (43, '节点管理', 'auth/node_Do', 21, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (44, '参数设置', 'system/config', 21, 0, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (45, '操作日志', 'auth/admLog', 22, 9, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (46, '图文管理', NULL, 7, 0, 1, 'newspaper outline', NULL, 1);
INSERT INTO `auth_node` VALUES (47, '文章列表', 'article/articles?type=1', 46, 0, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (48, '单页管理', 'article/articles?type=2', 46, 1, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (49, '广告管理', 'article/advertisement', 46, 9, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (50, '账单明细', 'user/accountlog', 19, 1, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (51, '附件管理', 'attachment/manage?type=0', 22, 0, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (2, '教 务', NULL, 0, 0, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (5, '系 统', 'system/config', 0, 9, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (121, '请 假', 'staff/offduty', 118, 3, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (11, '课程管理', NULL, 2, 0, 1, 'calendar minus', NULL, 1);
INSERT INTO `auth_node` VALUES (12, '课 时', 'section/getList', 11, 2, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (138, '管辖人员设置', 'staff/manageStaff', 118, 1, 0, '', '', 0);
INSERT INTO `auth_node` VALUES (16, '试 卷', 'test/getList', 11, 4, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (120, '请 款', 'staff/payout', 118, 4, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (18, '试 题', 'test/questionList', 11, 3, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (113, '微信管理', NULL, 7, 0, 1, 'newspaper outline', NULL, 1);
INSERT INTO `auth_node` VALUES (21, '角色与权限', NULL, 5, 0, 1, 'key', NULL, 1);
INSERT INTO `auth_node` VALUES (22, '系统工具', NULL, 5, 1, 1, 'archive', NULL, 1);
INSERT INTO `auth_node` VALUES (53, '公告管理', 'article/notifications', 52, 0, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (52, '公告与消息', '', 7, 1, 0, 'paper plane', NULL, 1);
INSERT INTO `auth_node` VALUES (56, '导航管理', '', 7, 2, 1, 'bullhorn', NULL, 1);
INSERT INTO `auth_node` VALUES (182, '作业圈', 'student/zoneTask', 128, 6, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (62, '操作记录', 'user/log', 19, 9, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (63, '学员等级', 'user/level', 19, 2, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (110, '知识点', 'knowledge/getList', 11, 5, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (101, '试题列表', 'card/storeLog', 98, 4, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (92, '核销记录', 'card/couponLog', 93, 2, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (114, '公众号菜单', 'wx/menu', 113, 0, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (117, '知识点讲解', 'knowledge/h5List', 11, 6, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (118, '职 员', NULL, 2, 3, 1, 'users', '', 1);
INSERT INTO `auth_node` VALUES (84, '文章分类', 'article/category', 46, 0, 0, '', '', 0);
INSERT INTO `auth_node` VALUES (86, '文章回收站', 'article/articles?is_del=1', 46, 5, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (181, '考勤坐标设置', 'system/mapPicker', 22, 0, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (89, '官网导航', 'article/nav?position=home', 56, 0, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (99, '试卷列表', 'store/getList', 98, 1, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (98, '试卷管理', NULL, 4, 1, 1, 'globe', '', 1);
INSERT INTO `auth_node` VALUES (119, '职 员', 'staff/getList', 118, 1, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (141, '删除课程', 'course/delCourse', 11, 1, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (139, '课次管理', 'course/courseTimes', 11, 2, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (140, '添加课程', 'course/saveCourse', 11, 1, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (127, '考 勤', 'staff/attendance', 118, 2, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (128, '学 员', '', 2, 0, 1, 'users', '', 1);
INSERT INTO `auth_node` VALUES (142, '课次列表', 'course/saveTimes', 11, 2, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (134, '精彩瞬间', 'student/moments', 128, 5, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (130, '学 员', 'student/getList', 128, 1, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (131, '成 绩', 'student/score', 128, 2, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (132, '积 分', 'student/point', 128, 3, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (133, '班 级', 'student/clazz', 128, 4, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (183, '往期学员', 'article/students', 46, 1, 1, '', '', 1);
INSERT INTO `auth_node` VALUES (137, '课程表', 'course/calendar', 11, 1, 1, '', NULL, 1);
INSERT INTO `auth_node` VALUES (143, '删除课次', 'course/delTimes', 11, 2, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (144, '知识点编辑', 'knowledge/saveKnowledge', 11, 5, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (145, '知识点分类', 'knowledge/category', 11, 5, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (146, '知识点删除', 'knowledge/delete', 11, 5, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (147, '知识点分类添加', 'knowledge/saveCategory', 11, 5, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (148, '知识点分类删除', 'knowledge/deleteCategory', 11, 5, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (149, '知识讲解预览', 'knowledge/h5Preview', 11, 6, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (150, '知识讲解编辑', 'knowledge/saveH5', 11, 6, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (151, '知识讲解删除', 'knowledge/deleteH5', 11, 6, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (152, '编辑管辖人员', 'staff/manageStaff', 118, 2, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (153, '值班安排', 'schedule/staffSchedule', 118, 2, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (155, '请假类型编辑', 'staff/saveOffdutyType', 118, 3, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (156, '请假类型删除', 'staff/delOffdutyType', 118, 3, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (157, '请款类型编辑', 'staff/savePayoutType', 118, 4, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (158, '请款类型删除', 'staff/delPayoutType', 118, 4, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (159, '学员账号编辑', 'student/saveStudent', 128, 1, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (160, '学员账号删除', 'student/deleteStudent', 128, 1, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (161, '班级编辑', 'student/saveClazz', 128, 4, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (162, '班级删除', 'student/deleteClazz', 128, 4, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (163, '学员积分增减', 'student/changePoint', 128, 3, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (164, '学员积分明细', 'student/pointLog', 128, 3, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (165, '学员成绩详情', 'student/scoreDetail', 128, 2, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (166, '录成绩', 'student/editScore', 128, 2, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (167, '成绩作废', 'student/delScore', 128, 2, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (168, '试卷编辑', 'test/saveTest', 11, 4, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (169, '试卷组题', 'test/saveTestQuestion', 11, 4, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (170, '试卷删除', 'test/deleteTest', 11, 4, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (171, '试卷分类管理', 'test/testCategory', 11, 4, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (172, '添加试题', 'test/saveQuestion', 11, 3, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (173, '添加综合题', 'test/saveQuestionGroup', 11, 3, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (174, '试题分类管理', 'test/questionCategory', 11, 3, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (175, '删除试题', 'test/deleteQuestion', 11, 3, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (176, '课时编辑', 'section/saveSection', 11, 2, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (177, '课时删除', 'section/deleteSection', 11, 2, 0, '', '', 1);
INSERT INTO `auth_node` VALUES (178, '导出考勤', 'staff/exportAttendance', 118, 3, 0, '', NULL, 1);
INSERT INTO `auth_node` VALUES (180, '腾讯云点播', 'tencent/vodlist', 22, 1, 1, '', NULL, 1);

-- ----------------------------
-- Table structure for auth_node_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_node_role`;
CREATE TABLE `auth_node_role`  (
  `role_id` smallint(6) UNSIGNED NOT NULL,
  `node_id` smallint(6) UNSIGNED NOT NULL,
  `level` tinyint(1) NULL DEFAULT NULL,
  `module` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `node_id`(`node_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_node_role
-- ----------------------------
INSERT INTO `auth_node_role` VALUES (10, 88, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 83, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 137, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 136, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (63, 139, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (63, 116, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (63, 74, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 135, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 134, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (17, 137, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (17, 113, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (17, 112, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (17, 63, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (17, 62, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (17, 60, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (17, 54, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (17, 53, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (17, 52, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (17, 74, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (17, 72, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (17, 2, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (63, 72, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (63, 71, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 133, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 132, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 131, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 130, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 129, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 128, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 127, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 6, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (64, 56, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (64, 57, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 105, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 84, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 85, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 86, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 87, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 60, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 62, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 63, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 2, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 121, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 122, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 123, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 124, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 125, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 126, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 12, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 14, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 13, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 19, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 15, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 16, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 17, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 18, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 36, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 37, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 38, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 40, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 42, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 43, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 44, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 45, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 46, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 47, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 119, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 120, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 39, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 41, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 108, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 107, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 7, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 22, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 23, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 24, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 26, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 27, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 28, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 29, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 31, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 32, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 33, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 34, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 35, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 49, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 50, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 51, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 52, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 53, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 144, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 145, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 146, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 147, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 148, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 149, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 48, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 112, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 20, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 21, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 25, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 30, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 116, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 96, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 113, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (10, 114, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 121, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 127, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 32, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 182, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 134, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (68, 0, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (67, 15, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 166, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 45, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 180, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 51, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 181, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 35, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 41, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 40, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 39, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 38, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 37, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 36, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 32, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 31, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (66, 14, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 33, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 31, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 43, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 44, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 42, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 89, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 53, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 114, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 49, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 86, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 48, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 183, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 21, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 48, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 165, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (68, 22, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (68, 0, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (68, 0, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (68, 0, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (66, 15, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (66, 17, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 47, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 46, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 52, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (67, 25, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (67, 16, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 20, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 23, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 19, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 16, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 15, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 14, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 13, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 11, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 33, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 34, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 35, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 36, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 38, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 39, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 40, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 41, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 42, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 43, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 44, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 45, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 22, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (18, 51, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 84, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 47, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 120, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 157, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 158, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 156, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 155, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 178, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 121, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 153, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 127, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 152, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 138, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 119, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 34, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 32, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 151, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 150, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 117, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 149, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 148, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 144, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 131, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 160, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 159, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 130, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 133, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 164, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 163, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 132, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 177, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 176, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 143, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 142, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 140, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 139, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 141, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 151, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 150, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 149, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 117, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 148, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 147, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 146, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 145, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 144, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 110, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 171, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 170, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 169, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 168, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 16, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 175, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 174, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 173, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 172, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 18, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 177, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 176, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 143, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 142, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 139, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 12, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 137, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 140, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 141, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 145, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 146, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 147, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 110, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 16, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 171, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 168, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 169, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 170, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 175, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 174, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 172, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 173, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 18, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 12, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 142, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 143, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 176, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 139, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 177, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 141, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 140, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 137, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 182, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 134, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 162, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 133, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 161, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 132, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 164, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 163, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 167, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 131, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 165, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 166, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 12, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 137, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 168, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 169, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 170, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 171, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 172, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 173, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 174, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 175, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 16, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 18, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 144, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 145, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 146, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 147, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 148, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 149, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 150, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 151, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 110, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 117, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (80, 134, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 167, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 166, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 165, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 131, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 159, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 130, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 150, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 149, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 117, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 145, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 144, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 159, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 110, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 160, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 171, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 169, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 168, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 16, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 174, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 173, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 172, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 18, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 176, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 143, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 142, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 139, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 12, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 137, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 140, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 167, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 132, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 163, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 164, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 133, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 161, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 162, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 134, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 182, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 119, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 127, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 153, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 121, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 155, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 156, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 178, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 120, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 157, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 158, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 47, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 48, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 85, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 86, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 49, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (81, 89, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 140, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 137, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 12, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 139, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 142, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 176, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 18, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 172, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 173, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 174, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 16, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 168, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 169, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 171, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 110, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 144, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 145, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 147, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 117, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 149, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 150, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 130, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 159, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 131, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 165, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 166, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 167, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 132, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 163, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 164, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 133, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 161, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 134, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 182, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 32, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 34, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 138, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 119, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 127, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 152, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 153, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 121, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 155, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 178, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 120, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 157, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 47, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 48, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 85, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (82, 114, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (9, 120, NULL, NULL);
INSERT INTO `auth_node_role` VALUES (1, 130, NULL, NULL);

-- ----------------------------
-- Table structure for auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role`  (
  `id` smallint(6) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否启用  1启用 2禁用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 83 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_role
-- ----------------------------
INSERT INTO `auth_role` VALUES (1, '系统管理员', '可使用所有功能，拥有系统设置权限', 1);
INSERT INTO `auth_role` VALUES (9, 'TA', '可使用所有基础功能以及部分审批功能', 1);
INSERT INTO `auth_role` VALUES (80, '外籍教师', '只能使用课表及课程相关内容', 1);
INSERT INTO `auth_role` VALUES (81, '主管', '可使用所有基础功能及审批功能', 1);
INSERT INTO `auth_role` VALUES (82, '行政', '可使用所有基础功能及部分审批功能', 1);

-- ----------------------------
-- Table structure for auth_role_staff
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_staff`;
CREATE TABLE `auth_role_staff`  (
  `role_id` int(10) UNSIGNED NULL DEFAULT NULL,
  `staff_id` int(10) NULL DEFAULT NULL,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `adm_id`(`staff_id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of auth_role_staff
-- ----------------------------
INSERT INTO `auth_role_staff` VALUES (10, 97);
INSERT INTO `auth_role_staff` VALUES (17, 97);
INSERT INTO `auth_role_staff` VALUES (18, 97);
INSERT INTO `auth_role_staff` VALUES (1, 98);
INSERT INTO `auth_role_staff` VALUES (9, 105);
INSERT INTO `auth_role_staff` VALUES (10, 99);
INSERT INTO `auth_role_staff` VALUES (18, 101);
INSERT INTO `auth_role_staff` VALUES (9, 106);
INSERT INTO `auth_role_staff` VALUES (1, 4);
INSERT INTO `auth_role_staff` VALUES (1, 100);
INSERT INTO `auth_role_staff` VALUES (1, 101);
INSERT INTO `auth_role_staff` VALUES (18, 102);
INSERT INTO `auth_role_staff` VALUES (18, 0);
INSERT INTO `auth_role_staff` VALUES (1, 0);
INSERT INTO `auth_role_staff` VALUES (18, 104);
INSERT INTO `auth_role_staff` VALUES (1, 103);
INSERT INTO `auth_role_staff` VALUES (1, 104);
INSERT INTO `auth_role_staff` VALUES (9, 1);
INSERT INTO `auth_role_staff` VALUES (1, 3);
INSERT INTO `auth_role_staff` VALUES (80, 5);
INSERT INTO `auth_role_staff` VALUES (80, 6);
INSERT INTO `auth_role_staff` VALUES (80, 7);
INSERT INTO `auth_role_staff` VALUES (9, 2);
INSERT INTO `auth_role_staff` VALUES (82, 11);
INSERT INTO `auth_role_staff` VALUES (80, 9);
INSERT INTO `auth_role_staff` VALUES (1, 10);
INSERT INTO `auth_role_staff` VALUES (81, 8);
INSERT INTO `auth_role_staff` VALUES (1, 12);
INSERT INTO `auth_role_staff` VALUES (9, 13);

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `grade_id` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入学年份',
  `staff_in_charge` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人id，多个用,分开',
  `add_time` datetime NULL DEFAULT NULL,
  `creator_id` int(11) NULL DEFAULT NULL,
  `delete_time` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES (1, '104', '1', '', '2020-08-30 14:27:20', NULL, NULL);
INSERT INTO `clazz` VALUES (2, '103', '1', '', '2020-08-30 15:50:00', NULL, NULL);
INSERT INTO `clazz` VALUES (3, '102', '1', '3', '2020-09-01 11:02:37', NULL, NULL);
INSERT INTO `clazz` VALUES (4, '101', '1', '3', '2020-09-01 11:03:03', NULL, NULL);
INSERT INTO `clazz` VALUES (5, '105', '1', '', '2020-09-01 21:08:51', NULL, NULL);
INSERT INTO `clazz` VALUES (6, '106', '1', '', '2020-09-01 21:09:05', NULL, NULL);
INSERT INTO `clazz` VALUES (7, '107', '1', '', '2020-09-01 21:09:16', NULL, NULL);
INSERT INTO `clazz` VALUES (8, '108', '1', '', '2020-09-01 21:09:28', NULL, NULL);
INSERT INTO `clazz` VALUES (9, '201', '1', '', '2020-09-01 21:09:42', NULL, NULL);
INSERT INTO `clazz` VALUES (10, '202', '1', '', '2020-09-01 21:09:52', NULL, NULL);
INSERT INTO `clazz` VALUES (11, '203', '1', '', '2020-09-01 21:10:03', NULL, NULL);
INSERT INTO `clazz` VALUES (12, '204', '1', '', '2020-09-01 21:10:13', NULL, NULL);
INSERT INTO `clazz` VALUES (13, '205', '1', '', '2020-09-01 21:10:23', NULL, NULL);
INSERT INTO `clazz` VALUES (14, '206', '1', '', '2020-09-01 21:10:33', NULL, NULL);
INSERT INTO `clazz` VALUES (15, '207', '1', '', '2020-09-01 21:10:45', NULL, NULL);
INSERT INTO `clazz` VALUES (16, '208', '1', '', '2020-09-01 21:10:55', NULL, NULL);
INSERT INTO `clazz` VALUES (17, '301', '1', '', '2020-09-01 21:11:08', NULL, NULL);
INSERT INTO `clazz` VALUES (18, '302', '1', '', '2020-09-01 21:11:34', NULL, NULL);
INSERT INTO `clazz` VALUES (19, '303', '1', '', '2020-09-01 21:11:48', NULL, NULL);
INSERT INTO `clazz` VALUES (20, '304', '1', '', '2020-09-01 21:12:00', NULL, NULL);
INSERT INTO `clazz` VALUES (21, '305', '1', '', '2020-09-01 21:12:21', NULL, NULL);
INSERT INTO `clazz` VALUES (22, '306', '1', '', '2020-09-01 21:12:31', NULL, NULL);
INSERT INTO `clazz` VALUES (23, '307', '1', '', '2020-09-01 21:12:42', NULL, NULL);
INSERT INTO `clazz` VALUES (24, '308', '1', '', '2020-09-01 21:12:53', NULL, NULL);
INSERT INTO `clazz` VALUES (25, '701', '1', '', '2020-09-01 21:13:09', NULL, NULL);
INSERT INTO `clazz` VALUES (26, '702', '1', '', '2020-09-01 21:13:23', NULL, NULL);
INSERT INTO `clazz` VALUES (27, '703', '1', '', '2020-09-01 21:13:33', NULL, NULL);
INSERT INTO `clazz` VALUES (28, '704', '1', '', '2020-09-01 21:13:43', NULL, NULL);
INSERT INTO `clazz` VALUES (29, '705', '1', '', '2020-09-01 21:13:53', NULL, NULL);
INSERT INTO `clazz` VALUES (30, '706', '1', '', '2020-09-01 21:14:07', NULL, NULL);
INSERT INTO `clazz` VALUES (31, '707', '1', '', '2020-09-01 21:14:17', NULL, NULL);
INSERT INTO `clazz` VALUES (32, '708', '1', '', '2020-09-01 21:14:28', NULL, NULL);
INSERT INTO `clazz` VALUES (33, '709', '1', '', '2020-09-01 21:14:38', NULL, NULL);
INSERT INTO `clazz` VALUES (34, '801', '1', '', '2020-09-01 21:14:51', NULL, NULL);
INSERT INTO `clazz` VALUES (35, '802', '1', '', '2020-09-01 21:15:02', NULL, NULL);
INSERT INTO `clazz` VALUES (36, '803', '1', '', '2020-09-01 21:15:12', NULL, NULL);
INSERT INTO `clazz` VALUES (37, '804', '1', '', '2020-09-01 21:15:21', NULL, NULL);
INSERT INTO `clazz` VALUES (38, '805', '1', '', '2020-09-01 21:15:32', NULL, NULL);
INSERT INTO `clazz` VALUES (39, '806', '1', '', '2020-09-01 21:15:42', NULL, NULL);
INSERT INTO `clazz` VALUES (40, '807', '1', '', '2020-09-01 21:15:53', NULL, NULL);
INSERT INTO `clazz` VALUES (41, '808', '1', '', '2020-09-01 21:16:04', NULL, NULL);
INSERT INTO `clazz` VALUES (42, '111', NULL, '11', '2020-10-18 17:09:35', NULL, NULL);

-- ----------------------------
-- Table structure for clazz_event
-- ----------------------------
DROP TABLE IF EXISTS `clazz_event`;
CREATE TABLE `clazz_event`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `clazz_id` int(11) NULL DEFAULT NULL,
  `teacher_id` int(11) NULL DEFAULT NULL,
  `student_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `date` date NULL DEFAULT NULL,
  `target_id` int(11) NULL DEFAULT NULL,
  `type` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '班级事件' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of clazz_event
-- ----------------------------
INSERT INTO `clazz_event` VALUES (1, '一年级四班', '2020-08-30 14:27:20', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (2, '一年级三班', '2020-08-30 15:50:00', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (3, '一年级二班', '2020-09-01 11:02:37', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (4, '一年级一班', '2020-09-01 11:03:03', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (5, '一年级五班', '2020-09-01 21:08:51', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (6, '一年级六班', '2020-09-01 21:09:05', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (7, '一年级七班', '2020-09-01 21:09:16', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (8, '一年级八班', '2020-09-01 21:09:28', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (9, '二年级一班', '2020-09-01 21:09:42', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (10, '二年级二班', '2020-09-01 21:09:52', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (11, '二年级三班', '2020-09-01 21:10:03', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (12, '二年级四班', '2020-09-01 21:10:13', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (13, '二年级五班', '2020-09-01 21:10:23', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (14, '二年级六班', '2020-09-01 21:10:33', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (15, '二年级七班', '2020-09-01 21:10:45', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (16, '二年级八班', '2020-09-01 21:10:55', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (17, '三年级一班', '2020-09-01 21:11:08', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (18, '三年级二班', '2020-09-01 21:11:34', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (19, '三年级三班', '2020-09-01 21:11:48', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (20, '三年级四班', '2020-09-01 21:12:00', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (21, '三年级五班', '2020-09-01 21:12:21', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (22, '三年级六班', '2020-09-01 21:12:31', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (23, '三年级七班', '2020-09-01 21:12:42', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (24, '三年级八班', '2020-09-01 21:12:53', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (25, '七年级一班', '2020-09-01 21:13:09', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (26, '七年级二班', '2020-09-01 21:13:23', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (27, '七年级三班', '2020-09-01 21:13:33', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (28, '七年级四班', '2020-09-01 21:13:43', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (29, '七年级五班', '2020-09-01 21:13:53', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (30, '七年级六班', '2020-09-01 21:14:07', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (31, '七年级七班', '2020-09-01 21:14:17', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (32, '七年级八班', '2020-09-01 21:14:28', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (33, '七年级九班', '2020-09-01 21:14:38', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (34, '八年级一班', '2020-09-01 21:14:51', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (35, '八年级二班', '2020-09-01 21:15:02', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (36, '八年级三班', '2020-09-01 21:15:12', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (37, '八年级四班', '2020-09-01 21:15:21', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (38, '八年级五班', '2020-09-01 21:15:32', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (39, '八年级六班', '2020-09-01 21:15:42', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (40, '八年级七班', '2020-09-01 21:15:53', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (41, '八年级八班', '2020-09-01 21:16:04', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `clazz_event` VALUES (42, 'AM第2节Shannon老师给学生们上了一节KB0 L9', NULL, 40, 2, NULL, '2020-09-11', NULL, 0);
INSERT INTO `clazz_event` VALUES (43, 'AM第2节Shannon老师给学生们上了一节KB1 L7', NULL, 40, 2, NULL, '2020-09-12', 3, 0);
INSERT INTO `clazz_event` VALUES (44, 'admin老师上传了精彩媒体', NULL, 1, 3, '10003', '2020-09-10', 33, 2);
INSERT INTO `clazz_event` VALUES (45, 'AM第1节Alonso老师给学生们上了一节KB1 L3', '2020-09-13 16:00:06', 18, 6, NULL, '2020-09-14', 4, 0);
INSERT INTO `clazz_event` VALUES (46, 'AM第1节Monty老师给学生们上了一节KB1 L8', '2020-09-13 16:04:41', 25, 5, NULL, '2020-09-14', 5, 0);
INSERT INTO `clazz_event` VALUES (47, 'AM第1节Dario老师给学生们上了一节KB0 L9', '2020-09-13 16:06:16', 34, 9, NULL, '2020-09-14', 6, 0);
INSERT INTO `clazz_event` VALUES (48, 'admin老师上传了精彩媒体', '2020-09-14 21:06:57', 2, 3, '10003,10004', '2020-09-14', 34, 2);
INSERT INTO `clazz_event` VALUES (49, 'admin老师上传了精彩媒体', '2020-09-14 21:08:43', 5, 3, '10003', '2020-09-14', 35, 2);
INSERT INTO `clazz_event` VALUES (50, 'admin老师更新了积分', '2020-09-17 09:45:05', 4, 3, '10004', '2020-09-17', 10004, 3);
INSERT INTO `clazz_event` VALUES (51, 'admin老师更新了积分', '2020-09-17 09:45:14', 4, 3, '10004', '2020-09-17', 10004, 3);
INSERT INTO `clazz_event` VALUES (52, 'admin老师更新了积分', '2020-09-17 09:55:07', 3, 3, '10003', '2020-09-17', 28, 3);
INSERT INTO `clazz_event` VALUES (53, 'admin老师更新了积分', '2020-09-18 10:58:07', 40, 3, '10003', '2020-09-18', 37, 3);
INSERT INTO `clazz_event` VALUES (54, 'admin老师更新了积分', '2020-09-18 11:11:39', 40, 3, '10003', '2020-09-18', 38, 3);
INSERT INTO `clazz_event` VALUES (55, 'collin老师上传了精彩媒体', '2020-09-27 15:23:38', 1, 4, '', '2020-09-27', 36, 2);
INSERT INTO `clazz_event` VALUES (56, '第三节collin老师给学生们上了一节30 grade 7 world of animals lesson 2', '2020-10-14 16:51:27', 39, 4, NULL, '2020-10-14', 2683, 0);
INSERT INTO `clazz_event` VALUES (57, '第二节Edward老师给学生们上了一节32 grade 7 world of animals lesson4', '2020-10-16 13:33:12', 41, 1, NULL, '2020-10-16', 2684, 0);
INSERT INTO `clazz_event` VALUES (58, '第一节Edward老师给学生们上了一节32 grade 7 world of animals lesson4', '2020-10-16 15:56:01', 41, 1, NULL, '2020-10-16', 2685, 0);
INSERT INTO `clazz_event` VALUES (59, 'cui老师更新了积分', '2020-10-20 17:16:55', NULL, 10, '10003', '2020-10-20', 41, 3);

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `value` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key`(`key`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 94 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES (79, 'org_intro', '高新区校区：高新区黄浦路', 'about', NULL, 0);
INSERT INTO `config` VALUES (76, 'lng', '118.767507', 'attendance', NULL, 0);
INSERT INTO `config` VALUES (6, 'app_name', '系统名', 'system', NULL, 0);
INSERT INTO `config` VALUES (72, 'sign_pm_start', '12:01', 'attendance', NULL, 0);
INSERT INTO `config` VALUES (73, 'sign_pm_end', '23:59', 'attendance', NULL, 0);
INSERT INTO `config` VALUES (91, 'sms_note', '新阳英语', 'sms', NULL, 0);
INSERT INTO `config` VALUES (71, 'sign_am_end', '12:00', 'attendance', NULL, 0);
INSERT INTO `config` VALUES (30, 'site_domain', 'erp.hzb-it.com', 'system', NULL, 0);
INSERT INTO `config` VALUES (31, 'site_name', '凤华ESL项目', 'system', NULL, 0);
INSERT INTO `config` VALUES (32, 'seo_keyword', '', 'system', NULL, 0);
INSERT INTO `config` VALUES (33, 'seo_description', '', 'system', NULL, 0);
INSERT INTO `config` VALUES (34, 'icp', '鲁ICP备20020546号', 'system', NULL, 0);
INSERT INTO `config` VALUES (35, 'analytics', '', 'system', NULL, 0);
INSERT INTO `config` VALUES (78, 'org_name', '新阳英语', 'about', NULL, 0);
INSERT INTO `config` VALUES (70, 'sign_am_start', '0:01', 'attendance', NULL, 0);
INSERT INTO `config` VALUES (77, 'sign_max_distance', '100', 'attendance', NULL, 0);
INSERT INTO `config` VALUES (51, 'site_logo', 'http://hzb-it.com/up/attach/20200825/701071415dd6e6a2fa46c3c7e4a5394c.jpg', 'system', NULL, 0);
INSERT INTO `config` VALUES (52, 'app_ver', '1.6', 'system', NULL, 0);
INSERT INTO `config` VALUES (75, 'lat', '32.008940', 'attendance', NULL, 0);
INSERT INTO `config` VALUES (80, 'org_website', 'http://hzb-it.com', 'about', NULL, 0);
INSERT INTO `config` VALUES (81, 'hotline', '123456', 'about', NULL, 0);
INSERT INTO `config` VALUES (82, 'org_address', '【高新校区】高新万达旁嘉创大厦', 'about', NULL, 0);
INSERT INTO `config` VALUES (83, 'org_email', 'shinsun_xy@163.com', 'about', NULL, 0);
INSERT INTO `config` VALUES (84, 'wx_qr', 'http://new.xinyangedu.com/up/attach/20200425/c6c1df673af88f6d4d2894b6d7d461ff.jpg', 'about', NULL, 0);
INSERT INTO `config` VALUES (85, 'wx_qr1', 'http://new.xinyangedu.com/up/attach/20200425/4e804db81c49c4155ae1900c14b2a38e.jpg', 'about', NULL, 0);
INSERT INTO `config` VALUES (89, 'sms_key', '111', 'sms', NULL, 0);
INSERT INTO `config` VALUES (90, 'sms_secret', '000', 'sms', NULL, 0);
INSERT INTO `config` VALUES (87, 'home_course_best', '专注高品质英语教学15年', 'about', NULL, 0);
INSERT INTO `config` VALUES (88, 'home_student_remark', '我在英语学习，我为英语代言', 'about', NULL, 0);
INSERT INTO `config` VALUES (92, 'sms_temp_code', 'SMS_176880409', 'sms', NULL, 0);
INSERT INTO `config` VALUES (93, 'id', '', 'system', NULL, 0);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `clazz_id` int(11) NOT NULL,
  `grade_id` int(11) NULL DEFAULT NULL,
  `date` date NOT NULL,
  `staff_id` int(11) NOT NULL COMMENT '老师id',
  `assistant_id` int(11) NULL DEFAULT NULL COMMENT '助教id',
  `times_id` int(11) NULL DEFAULT NULL,
  `section_id` int(11) NULL DEFAULT NULL,
  `delete_time` int(11) NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `edit_time` datetime NULL DEFAULT NULL,
  `creator_id` int(11) NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `date`(`date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2690 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (153, 18, NULL, '2020-09-14', 6, 0, 1, NULL, 1600839106, '2020-09-23 13:28:31', '2020-09-23 13:28:31', 4, 1);
INSERT INTO `course` VALUES (154, 18, NULL, '2020-09-28', 6, 0, 1, NULL, 1600839101, '2020-09-23 13:28:31', '2020-09-23 13:28:31', 4, 1);
INSERT INTO `course` VALUES (155, 18, NULL, '2020-10-12', 6, 0, 1, NULL, 1600839096, '2020-09-23 13:28:31', '2020-09-23 13:28:31', 4, 1);
INSERT INTO `course` VALUES (156, 18, NULL, '2020-10-26', 6, 0, 1, NULL, 1600839092, '2020-09-23 13:28:31', '2020-09-23 13:28:31', 4, 1);
INSERT INTO `course` VALUES (157, 18, NULL, '2020-11-09', 6, 0, 1, NULL, 1600839089, '2020-09-23 13:28:31', '2020-09-23 13:28:31', 4, 1);
INSERT INTO `course` VALUES (158, 18, NULL, '2020-11-23', 6, 0, 1, NULL, 1600839085, '2020-09-23 13:28:31', '2020-09-23 13:28:31', 4, 1);
INSERT INTO `course` VALUES (159, 18, NULL, '2020-12-07', 6, 0, 1, NULL, 1600839081, '2020-09-23 13:28:31', '2020-09-23 13:28:31', 4, 1);
INSERT INTO `course` VALUES (160, 18, NULL, '2020-12-21', 6, 0, 1, NULL, 1600839077, '2020-09-23 13:28:31', '2020-09-23 13:28:31', 4, 1);
INSERT INTO `course` VALUES (161, 18, NULL, '2021-01-04', 6, 0, 1, NULL, 1600839072, '2020-09-23 13:28:31', '2020-09-23 13:28:31', 4, 1);
INSERT INTO `course` VALUES (162, 18, NULL, '2021-01-18', 6, 0, 1, NULL, 1600839068, '2020-09-23 13:28:31', '2020-09-23 13:28:31', 4, 1);
INSERT INTO `course` VALUES (163, 18, NULL, '2020-09-07', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (164, 18, NULL, '2020-09-14', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (165, 18, NULL, '2020-09-21', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (166, 18, NULL, '2020-09-28', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (167, 18, NULL, '2020-10-05', 6, 0, 1, NULL, 1601338867, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (168, 18, NULL, '2020-10-12', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (169, 18, NULL, '2020-10-19', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (170, 18, NULL, '2020-10-26', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (171, 18, NULL, '2020-11-02', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (172, 18, NULL, '2020-11-09', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (173, 18, NULL, '2020-11-16', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (174, 18, NULL, '2020-11-23', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (175, 18, NULL, '2020-11-30', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (176, 18, NULL, '2020-12-07', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (177, 18, NULL, '2020-12-14', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (178, 18, NULL, '2020-12-21', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (179, 18, NULL, '2020-12-28', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (180, 18, NULL, '2021-01-04', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (181, 18, NULL, '2021-01-11', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (182, 18, NULL, '2021-01-18', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (183, 18, NULL, '2021-01-25', 6, 0, 1, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (205, 7, NULL, '2020-09-07', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (206, 7, NULL, '2020-09-14', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (207, 7, NULL, '2020-09-21', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (208, 7, NULL, '2020-09-28', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (209, 7, NULL, '2020-10-05', 6, 1, 2, NULL, 1601338867, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (210, 7, NULL, '2020-10-12', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (211, 7, NULL, '2020-10-19', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (212, 7, NULL, '2020-10-26', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (213, 7, NULL, '2020-11-02', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (214, 7, NULL, '2020-11-09', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (215, 7, NULL, '2020-11-16', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (216, 7, NULL, '2020-11-23', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (217, 7, NULL, '2020-11-30', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (218, 7, NULL, '2020-12-07', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (219, 7, NULL, '2020-12-14', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (220, 7, NULL, '2020-12-21', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (221, 7, NULL, '2020-12-28', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (222, 7, NULL, '2021-01-04', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (223, 7, NULL, '2021-01-11', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (224, 7, NULL, '2021-01-18', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (225, 7, NULL, '2021-01-25', 6, 1, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (226, 20, NULL, '2020-09-07', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (227, 20, NULL, '2020-09-14', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (228, 20, NULL, '2020-09-21', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (229, 20, NULL, '2020-09-28', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (230, 20, NULL, '2020-10-05', 7, 0, 2, NULL, 1601338867, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (231, 20, NULL, '2020-10-12', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (232, 20, NULL, '2020-10-19', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (233, 20, NULL, '2020-10-26', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (234, 20, NULL, '2020-11-02', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (235, 20, NULL, '2020-11-09', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (236, 20, NULL, '2020-11-16', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (237, 20, NULL, '2020-11-23', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (238, 20, NULL, '2020-11-30', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (239, 20, NULL, '2020-12-07', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (240, 20, NULL, '2020-12-14', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (241, 20, NULL, '2020-12-21', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (242, 20, NULL, '2020-12-28', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (243, 20, NULL, '2021-01-04', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (244, 20, NULL, '2021-01-11', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (245, 20, NULL, '2021-01-18', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (246, 20, NULL, '2021-01-25', 7, 0, 2, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (247, 21, NULL, '2020-09-07', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (248, 21, NULL, '2020-09-14', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (249, 21, NULL, '2020-09-21', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (250, 21, NULL, '2020-09-28', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (251, 21, NULL, '2020-10-05', 6, 0, 3, NULL, 1601338867, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (252, 21, NULL, '2020-10-12', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (253, 21, NULL, '2020-10-19', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (254, 21, NULL, '2020-10-26', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (255, 21, NULL, '2020-11-02', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (256, 21, NULL, '2020-11-09', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (257, 21, NULL, '2020-11-16', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (258, 21, NULL, '2020-11-23', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (259, 21, NULL, '2020-11-30', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (260, 21, NULL, '2020-12-07', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (261, 21, NULL, '2020-12-14', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (262, 21, NULL, '2020-12-21', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (263, 21, NULL, '2020-12-28', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (264, 21, NULL, '2021-01-04', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (265, 21, NULL, '2021-01-11', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (266, 21, NULL, '2021-01-18', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (267, 21, NULL, '2021-01-25', 6, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (268, 23, NULL, '2020-09-07', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (269, 23, NULL, '2020-09-14', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (270, 23, NULL, '2020-09-21', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (271, 23, NULL, '2020-09-28', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (272, 23, NULL, '2020-10-05', 7, 0, 3, NULL, 1601338867, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (273, 23, NULL, '2020-10-12', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (274, 23, NULL, '2020-10-19', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (275, 23, NULL, '2020-10-26', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (276, 23, NULL, '2020-11-02', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (277, 23, NULL, '2020-11-09', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (278, 23, NULL, '2020-11-16', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (279, 23, NULL, '2020-11-23', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (280, 23, NULL, '2020-11-30', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (281, 23, NULL, '2020-12-07', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (282, 23, NULL, '2020-12-14', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (283, 23, NULL, '2020-12-21', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (284, 23, NULL, '2020-12-28', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (285, 23, NULL, '2021-01-04', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (286, 23, NULL, '2021-01-11', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (287, 23, NULL, '2021-01-18', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (288, 23, NULL, '2021-01-25', 7, 0, 3, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (289, 5, NULL, '2020-09-07', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (290, 5, NULL, '2020-09-14', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (291, 5, NULL, '2020-09-21', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (292, 5, NULL, '2020-09-28', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (293, 5, NULL, '2020-10-05', 7, 2, 5, NULL, 1601338867, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (294, 5, NULL, '2020-10-12', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (295, 5, NULL, '2020-10-19', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (296, 5, NULL, '2020-10-26', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (297, 5, NULL, '2020-11-02', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (298, 5, NULL, '2020-11-09', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (299, 5, NULL, '2020-11-16', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (300, 5, NULL, '2020-11-23', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (301, 5, NULL, '2020-11-30', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (302, 5, NULL, '2020-12-07', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (303, 5, NULL, '2020-12-14', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (304, 5, NULL, '2020-12-21', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (305, 5, NULL, '2020-12-28', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (306, 5, NULL, '2021-01-04', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (307, 5, NULL, '2021-01-11', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (308, 5, NULL, '2021-01-18', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (309, 5, NULL, '2021-01-25', 7, 2, 5, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (331, 17, NULL, '2020-09-07', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (332, 17, NULL, '2020-09-14', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (333, 17, NULL, '2020-09-21', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (334, 17, NULL, '2020-09-28', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (335, 17, NULL, '2020-10-05', 6, 0, 7, NULL, 1601338867, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (336, 17, NULL, '2020-10-12', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (337, 17, NULL, '2020-10-19', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (338, 17, NULL, '2020-10-26', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (339, 17, NULL, '2020-11-02', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (340, 17, NULL, '2020-11-09', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (341, 17, NULL, '2020-11-16', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (342, 17, NULL, '2020-11-23', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (343, 17, NULL, '2020-11-30', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (344, 17, NULL, '2020-12-07', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (345, 17, NULL, '2020-12-14', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (346, 17, NULL, '2020-12-21', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (347, 17, NULL, '2020-12-28', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (348, 17, NULL, '2021-01-04', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (349, 17, NULL, '2021-01-11', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (350, 17, NULL, '2021-01-18', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (351, 17, NULL, '2021-01-25', 6, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (352, 19, NULL, '2020-09-07', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (353, 19, NULL, '2020-09-14', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (354, 19, NULL, '2020-09-21', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (355, 19, NULL, '2020-09-28', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (356, 19, NULL, '2020-10-05', 7, 0, 7, NULL, 1601338867, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (357, 19, NULL, '2020-10-12', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (358, 19, NULL, '2020-10-19', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (359, 19, NULL, '2020-10-26', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (360, 19, NULL, '2020-11-02', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (361, 19, NULL, '2020-11-09', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (362, 19, NULL, '2020-11-16', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (363, 19, NULL, '2020-11-23', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (364, 19, NULL, '2020-11-30', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (365, 19, NULL, '2020-12-07', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (366, 19, NULL, '2020-12-14', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (367, 19, NULL, '2020-12-21', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (368, 19, NULL, '2020-12-28', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (369, 19, NULL, '2021-01-04', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (370, 19, NULL, '2021-01-11', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (371, 19, NULL, '2021-01-18', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (372, 19, NULL, '2021-01-25', 7, 0, 7, NULL, NULL, '2020-09-23 13:42:46', '2020-09-23 13:42:46', 4, 1);
INSERT INTO `course` VALUES (373, 9, NULL, '2020-09-08', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (374, 9, NULL, '2020-09-15', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (375, 9, NULL, '2020-09-22', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (376, 9, NULL, '2020-09-29', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (377, 9, NULL, '2020-10-06', 6, 1, 1, NULL, 1601338867, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (378, 9, NULL, '2020-10-13', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (379, 9, NULL, '2020-10-20', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (380, 9, NULL, '2020-10-27', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (381, 9, NULL, '2020-11-03', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (382, 9, NULL, '2020-11-10', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (383, 9, NULL, '2020-11-17', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (384, 9, NULL, '2020-11-24', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (385, 9, NULL, '2020-12-01', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (386, 9, NULL, '2020-12-08', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (387, 9, NULL, '2020-12-15', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (388, 9, NULL, '2020-12-22', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (389, 9, NULL, '2020-12-29', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (390, 9, NULL, '2021-01-05', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (391, 9, NULL, '2021-01-12', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (392, 9, NULL, '2021-01-19', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (393, 9, NULL, '2021-01-26', 6, 1, 1, NULL, NULL, '2020-09-23 13:45:18', '2020-09-23 13:45:18', 4, 1);
INSERT INTO `course` VALUES (414, 8, NULL, '2020-09-08', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (415, 8, NULL, '2020-09-15', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (416, 8, NULL, '2020-09-22', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (417, 8, NULL, '2020-09-29', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (418, 8, NULL, '2020-10-06', 6, 2, 2, NULL, 1601338867, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (419, 8, NULL, '2020-10-13', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (420, 8, NULL, '2020-10-20', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (421, 8, NULL, '2020-10-27', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (422, 8, NULL, '2020-11-03', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (423, 8, NULL, '2020-11-10', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (424, 8, NULL, '2020-11-17', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (425, 8, NULL, '2020-11-24', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (426, 8, NULL, '2020-12-01', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (427, 8, NULL, '2020-12-08', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (428, 8, NULL, '2020-12-15', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (429, 8, NULL, '2020-12-22', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (430, 8, NULL, '2020-12-29', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (431, 8, NULL, '2021-01-05', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (432, 8, NULL, '2021-01-12', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (433, 8, NULL, '2021-01-19', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (434, 8, NULL, '2021-01-26', 6, 2, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (435, 13, NULL, '2020-09-08', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (436, 13, NULL, '2020-09-15', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (437, 13, NULL, '2020-09-22', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (438, 13, NULL, '2020-09-29', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (439, 13, NULL, '2020-10-06', 7, 1, 2, NULL, 1601338867, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (440, 13, NULL, '2020-10-13', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (441, 13, NULL, '2020-10-20', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (442, 13, NULL, '2020-10-27', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (443, 13, NULL, '2020-11-03', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (444, 13, NULL, '2020-11-10', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (445, 13, NULL, '2020-11-17', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (446, 13, NULL, '2020-11-24', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (447, 13, NULL, '2020-12-01', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (448, 13, NULL, '2020-12-08', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (449, 13, NULL, '2020-12-15', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (450, 13, NULL, '2020-12-22', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (451, 13, NULL, '2020-12-29', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (452, 13, NULL, '2021-01-05', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (453, 13, NULL, '2021-01-12', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (454, 13, NULL, '2021-01-19', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (455, 13, NULL, '2021-01-26', 7, 1, 2, NULL, NULL, '2020-09-23 13:48:48', '2020-09-23 13:48:48', 4, 1);
INSERT INTO `course` VALUES (476, 11, NULL, '2020-09-08', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (477, 11, NULL, '2020-09-15', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (478, 11, NULL, '2020-09-22', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (479, 11, NULL, '2020-09-29', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (480, 11, NULL, '2020-10-06', 6, 1, 3, NULL, 1601338867, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (481, 11, NULL, '2020-10-13', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (482, 11, NULL, '2020-10-20', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (483, 11, NULL, '2020-10-27', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (484, 11, NULL, '2020-11-03', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (485, 11, NULL, '2020-11-10', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (486, 11, NULL, '2020-11-17', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (487, 11, NULL, '2020-11-24', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (488, 11, NULL, '2020-12-01', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (489, 11, NULL, '2020-12-08', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (490, 11, NULL, '2020-12-15', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (491, 11, NULL, '2020-12-22', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (492, 11, NULL, '2020-12-29', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (493, 11, NULL, '2021-01-05', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (494, 11, NULL, '2021-01-12', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (495, 11, NULL, '2021-01-19', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (496, 11, NULL, '2021-01-26', 6, 1, 3, NULL, NULL, '2020-09-23 14:10:33', '2020-09-23 14:10:33', 4, 1);
INSERT INTO `course` VALUES (507, 14, NULL, '2020-09-08', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (508, 14, NULL, '2020-09-15', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (509, 14, NULL, '2020-09-22', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (510, 14, NULL, '2020-09-29', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (511, 14, NULL, '2020-10-06', 7, 1, 4, NULL, 1601338867, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (512, 14, NULL, '2020-10-13', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (513, 14, NULL, '2020-10-20', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (514, 14, NULL, '2020-10-27', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (515, 14, NULL, '2020-11-03', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (516, 14, NULL, '2020-11-10', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (517, 14, NULL, '2020-11-17', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (518, 14, NULL, '2020-11-24', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (519, 14, NULL, '2020-12-01', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (520, 14, NULL, '2020-12-08', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (521, 14, NULL, '2020-12-15', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (522, 14, NULL, '2020-12-22', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (523, 14, NULL, '2020-12-29', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (524, 14, NULL, '2021-01-05', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (525, 14, NULL, '2021-01-12', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (526, 14, NULL, '2021-01-19', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (527, 14, NULL, '2021-01-26', 7, 1, 4, NULL, NULL, '2020-09-23 14:11:14', '2020-09-23 14:11:14', 4, 1);
INSERT INTO `course` VALUES (538, 6, NULL, '2020-09-08', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (539, 6, NULL, '2020-09-15', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (540, 6, NULL, '2020-09-22', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (541, 6, NULL, '2020-09-29', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (542, 6, NULL, '2020-10-06', 7, 2, 5, NULL, 1601338867, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (543, 6, NULL, '2020-10-13', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (544, 6, NULL, '2020-10-20', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (545, 6, NULL, '2020-10-27', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (546, 6, NULL, '2020-11-03', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (547, 6, NULL, '2020-11-10', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (548, 6, NULL, '2020-11-17', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (549, 6, NULL, '2020-11-24', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (550, 6, NULL, '2020-12-01', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (551, 6, NULL, '2020-12-08', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (552, 6, NULL, '2020-12-15', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (553, 6, NULL, '2020-12-22', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (554, 6, NULL, '2020-12-29', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (555, 6, NULL, '2021-01-05', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (556, 6, NULL, '2021-01-12', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (557, 6, NULL, '2021-01-19', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (558, 6, NULL, '2021-01-26', 7, 2, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (559, 10, NULL, '2020-09-08', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (560, 10, NULL, '2020-09-15', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (561, 10, NULL, '2020-09-22', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (562, 10, NULL, '2020-09-29', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (563, 10, NULL, '2020-10-06', 6, 1, 5, NULL, 1601338867, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (564, 10, NULL, '2020-10-13', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (565, 10, NULL, '2020-10-20', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (566, 10, NULL, '2020-10-27', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (567, 10, NULL, '2020-11-03', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (568, 10, NULL, '2020-11-10', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (569, 10, NULL, '2020-11-17', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (570, 10, NULL, '2020-11-24', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (571, 10, NULL, '2020-12-01', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (572, 10, NULL, '2020-12-08', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (573, 10, NULL, '2020-12-15', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (574, 10, NULL, '2020-12-22', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (575, 10, NULL, '2020-12-29', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (576, 10, NULL, '2021-01-05', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (577, 10, NULL, '2021-01-12', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (578, 10, NULL, '2021-01-19', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (579, 10, NULL, '2021-01-26', 6, 1, 5, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (611, 12, NULL, '2020-09-08', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (612, 12, NULL, '2020-09-15', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (613, 12, NULL, '2020-09-22', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (614, 12, NULL, '2020-09-29', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (615, 12, NULL, '2020-10-06', 6, 1, 6, NULL, 1601338867, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (616, 12, NULL, '2020-10-13', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (617, 12, NULL, '2020-10-20', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (618, 12, NULL, '2020-10-27', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (619, 12, NULL, '2020-11-03', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (620, 12, NULL, '2020-11-10', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (621, 12, NULL, '2020-11-17', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (622, 12, NULL, '2020-11-24', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (623, 12, NULL, '2020-12-01', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (624, 12, NULL, '2020-12-08', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (625, 12, NULL, '2020-12-15', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (626, 12, NULL, '2020-12-22', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (627, 12, NULL, '2020-12-29', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (628, 12, NULL, '2021-01-05', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (629, 12, NULL, '2021-01-12', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (630, 12, NULL, '2021-01-19', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (631, 12, NULL, '2021-01-26', 6, 1, 6, NULL, NULL, '2020-09-23 14:16:33', '2020-09-23 14:16:33', 4, 1);
INSERT INTO `course` VALUES (642, 9, NULL, '2020-09-09', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (643, 9, NULL, '2020-09-16', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (644, 9, NULL, '2020-09-23', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (645, 9, NULL, '2020-09-30', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (646, 9, NULL, '2020-10-07', 6, 1, 1, NULL, 1601338867, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (647, 9, NULL, '2020-10-14', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (648, 9, NULL, '2020-10-21', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (649, 9, NULL, '2020-10-28', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (650, 9, NULL, '2020-11-04', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (651, 9, NULL, '2020-11-11', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (652, 9, NULL, '2020-11-18', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (653, 9, NULL, '2020-11-25', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (654, 9, NULL, '2020-12-02', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (655, 9, NULL, '2020-12-09', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (656, 9, NULL, '2020-12-16', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (657, 9, NULL, '2020-12-23', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (658, 9, NULL, '2020-12-30', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (659, 9, NULL, '2021-01-06', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (660, 9, NULL, '2021-01-13', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (661, 9, NULL, '2021-01-20', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (662, 9, NULL, '2021-01-27', 6, 1, 1, NULL, NULL, '2020-09-23 14:23:10', '2020-09-23 14:23:10', 4, 1);
INSERT INTO `course` VALUES (744, 16, NULL, '2020-09-09', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (745, 16, NULL, '2020-09-16', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (746, 16, NULL, '2020-09-23', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (747, 16, NULL, '2020-09-30', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (748, 16, NULL, '2020-10-07', 7, 1, 5, NULL, 1601338867, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (749, 16, NULL, '2020-10-14', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (750, 16, NULL, '2020-10-21', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (751, 16, NULL, '2020-10-28', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (752, 16, NULL, '2020-11-04', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (753, 16, NULL, '2020-11-11', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (754, 16, NULL, '2020-11-18', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (755, 16, NULL, '2020-11-25', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (756, 16, NULL, '2020-12-02', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (757, 16, NULL, '2020-12-09', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (758, 16, NULL, '2020-12-16', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (759, 16, NULL, '2020-12-23', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (760, 16, NULL, '2020-12-30', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (761, 16, NULL, '2021-01-06', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (762, 16, NULL, '2021-01-13', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (763, 16, NULL, '2021-01-20', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (764, 16, NULL, '2021-01-27', 7, 1, 5, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (785, 15, NULL, '2020-09-09', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (786, 15, NULL, '2020-09-16', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (787, 15, NULL, '2020-09-23', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (788, 15, NULL, '2020-09-30', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (789, 15, NULL, '2020-10-07', 7, 1, 6, NULL, 1601338867, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (790, 15, NULL, '2020-10-14', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (791, 15, NULL, '2020-10-21', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (792, 15, NULL, '2020-10-28', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (793, 15, NULL, '2020-11-04', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (794, 15, NULL, '2020-11-11', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (795, 15, NULL, '2020-11-18', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (796, 15, NULL, '2020-11-25', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (797, 15, NULL, '2020-12-02', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (798, 15, NULL, '2020-12-09', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (799, 15, NULL, '2020-12-16', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (800, 15, NULL, '2020-12-23', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (801, 15, NULL, '2020-12-30', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (802, 15, NULL, '2021-01-06', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (803, 15, NULL, '2021-01-13', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (804, 15, NULL, '2021-01-20', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (805, 15, NULL, '2021-01-27', 7, 1, 6, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (816, 18, NULL, '2020-09-09', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (817, 18, NULL, '2020-09-16', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (818, 18, NULL, '2020-09-23', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (819, 18, NULL, '2020-09-30', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (820, 18, NULL, '2020-10-07', 6, 0, 7, NULL, 1601338867, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (821, 18, NULL, '2020-10-14', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (822, 18, NULL, '2020-10-21', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (823, 18, NULL, '2020-10-28', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (824, 18, NULL, '2020-11-04', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (825, 18, NULL, '2020-11-11', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (826, 18, NULL, '2020-11-18', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (827, 18, NULL, '2020-11-25', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (828, 18, NULL, '2020-12-02', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (829, 18, NULL, '2020-12-09', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (830, 18, NULL, '2020-12-16', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (831, 18, NULL, '2020-12-23', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (832, 18, NULL, '2020-12-30', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (833, 18, NULL, '2021-01-06', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (834, 18, NULL, '2021-01-13', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (835, 18, NULL, '2021-01-20', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (836, 18, NULL, '2021-01-27', 6, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (837, 20, NULL, '2020-09-09', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (838, 20, NULL, '2020-09-16', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (839, 20, NULL, '2020-09-23', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (840, 20, NULL, '2020-09-30', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (841, 20, NULL, '2020-10-07', 7, 0, 7, NULL, 1601338867, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (842, 20, NULL, '2020-10-14', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (843, 20, NULL, '2020-10-21', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (844, 20, NULL, '2020-10-28', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (845, 20, NULL, '2020-11-04', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (846, 20, NULL, '2020-11-11', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (847, 20, NULL, '2020-11-18', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (848, 20, NULL, '2020-11-25', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (849, 20, NULL, '2020-12-02', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (850, 20, NULL, '2020-12-09', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (851, 20, NULL, '2020-12-16', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (852, 20, NULL, '2020-12-23', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (853, 20, NULL, '2020-12-30', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (854, 20, NULL, '2021-01-06', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (855, 20, NULL, '2021-01-13', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (856, 20, NULL, '2021-01-20', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (857, 20, NULL, '2021-01-27', 7, 0, 7, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (858, 21, NULL, '2020-09-09', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (859, 21, NULL, '2020-09-16', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (860, 21, NULL, '2020-09-23', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (861, 21, NULL, '2020-09-30', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (862, 21, NULL, '2020-10-07', 6, 0, 8, NULL, 1601338867, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (863, 21, NULL, '2020-10-14', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (864, 21, NULL, '2020-10-21', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (865, 21, NULL, '2020-10-28', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (866, 21, NULL, '2020-11-04', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (867, 21, NULL, '2020-11-11', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (868, 21, NULL, '2020-11-18', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (869, 21, NULL, '2020-11-25', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (870, 21, NULL, '2020-12-02', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (871, 21, NULL, '2020-12-09', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (872, 21, NULL, '2020-12-16', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (873, 21, NULL, '2020-12-23', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (874, 21, NULL, '2020-12-30', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (875, 21, NULL, '2021-01-06', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (876, 21, NULL, '2021-01-13', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (877, 21, NULL, '2021-01-20', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (878, 21, NULL, '2021-01-27', 6, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (879, 23, NULL, '2020-09-09', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (880, 23, NULL, '2020-09-16', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (881, 23, NULL, '2020-09-23', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (882, 23, NULL, '2020-09-30', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (883, 23, NULL, '2020-10-07', 7, 0, 8, NULL, 1601338867, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (884, 23, NULL, '2020-10-14', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (885, 23, NULL, '2020-10-21', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (886, 23, NULL, '2020-10-28', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (887, 23, NULL, '2020-11-04', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (888, 23, NULL, '2020-11-11', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (889, 23, NULL, '2020-11-18', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (890, 23, NULL, '2020-11-25', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (891, 23, NULL, '2020-12-02', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (892, 23, NULL, '2020-12-09', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (893, 23, NULL, '2020-12-16', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (894, 23, NULL, '2020-12-23', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (895, 23, NULL, '2020-12-30', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (896, 23, NULL, '2021-01-06', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (897, 23, NULL, '2021-01-13', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (898, 23, NULL, '2021-01-20', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (899, 23, NULL, '2021-01-27', 7, 0, 8, NULL, NULL, '2020-09-23 14:31:56', '2020-09-23 14:31:56', 4, 1);
INSERT INTO `course` VALUES (921, 17, NULL, '2020-09-10', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (922, 17, NULL, '2020-09-17', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (923, 17, NULL, '2020-09-24', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (924, 17, NULL, '2020-10-01', 6, 0, 1, NULL, 1601360651, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (925, 17, NULL, '2020-10-08', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (926, 17, NULL, '2020-10-15', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (927, 17, NULL, '2020-10-22', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (928, 17, NULL, '2020-10-29', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (929, 17, NULL, '2020-11-05', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (930, 17, NULL, '2020-11-12', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (931, 17, NULL, '2020-11-19', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (932, 17, NULL, '2020-11-26', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (933, 17, NULL, '2020-12-03', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (934, 17, NULL, '2020-12-10', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (935, 17, NULL, '2020-12-17', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (936, 17, NULL, '2020-12-24', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (937, 17, NULL, '2020-12-31', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (938, 17, NULL, '2021-01-07', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (939, 17, NULL, '2021-01-14', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (940, 17, NULL, '2021-01-21', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (941, 17, NULL, '2021-01-28', 6, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (942, 19, NULL, '2020-09-10', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (943, 19, NULL, '2020-09-17', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (944, 19, NULL, '2020-09-24', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (945, 19, NULL, '2020-10-01', 7, 0, 1, NULL, 1601360651, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (946, 19, NULL, '2020-10-08', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (947, 19, NULL, '2020-10-15', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (948, 19, NULL, '2020-10-22', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (949, 19, NULL, '2020-10-29', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (950, 19, NULL, '2020-11-05', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (951, 19, NULL, '2020-11-12', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (952, 19, NULL, '2020-11-19', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (953, 19, NULL, '2020-11-26', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (954, 19, NULL, '2020-12-03', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (955, 19, NULL, '2020-12-10', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (956, 19, NULL, '2020-12-17', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (957, 19, NULL, '2020-12-24', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (958, 19, NULL, '2020-12-31', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (959, 19, NULL, '2021-01-07', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (960, 19, NULL, '2021-01-14', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (961, 19, NULL, '2021-01-21', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (962, 19, NULL, '2021-01-28', 7, 0, 1, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (985, 10, NULL, '2019-06-06', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (986, 10, NULL, '2019-06-13', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (987, 10, NULL, '2019-06-20', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (988, 10, NULL, '2019-06-27', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (989, 10, NULL, '2019-07-04', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (990, 10, NULL, '2019-07-11', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (991, 10, NULL, '2019-07-18', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (992, 10, NULL, '2019-07-25', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (993, 10, NULL, '2019-08-01', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (994, 10, NULL, '2019-08-08', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (995, 10, NULL, '2019-08-15', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (996, 10, NULL, '2019-08-22', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (997, 10, NULL, '2019-08-29', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (998, 10, NULL, '2019-09-05', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (999, 10, NULL, '2019-09-12', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1000, 10, NULL, '2019-09-19', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1001, 10, NULL, '2019-09-26', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1002, 10, NULL, '2019-10-03', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1003, 10, NULL, '2019-10-10', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1004, 10, NULL, '2019-10-17', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1005, 10, NULL, '2019-10-24', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1006, 10, NULL, '2019-10-31', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1007, 10, NULL, '2019-11-07', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1008, 10, NULL, '2019-11-14', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1009, 10, NULL, '2019-11-21', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1010, 10, NULL, '2019-11-28', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1011, 10, NULL, '2019-12-05', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1012, 10, NULL, '2019-12-12', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1013, 10, NULL, '2019-12-19', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1014, 10, NULL, '2019-12-26', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1015, 10, NULL, '2020-01-02', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1016, 10, NULL, '2020-01-09', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1017, 10, NULL, '2020-01-16', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1018, 10, NULL, '2020-01-23', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1019, 10, NULL, '2020-01-30', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1020, 10, NULL, '2020-02-06', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1021, 10, NULL, '2020-02-13', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1022, 10, NULL, '2020-02-20', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1023, 10, NULL, '2020-02-27', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1024, 10, NULL, '2020-03-05', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1025, 10, NULL, '2020-03-12', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1026, 10, NULL, '2020-03-19', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1027, 10, NULL, '2020-03-26', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1028, 10, NULL, '2020-04-02', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1029, 10, NULL, '2020-04-09', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1030, 10, NULL, '2020-04-16', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1031, 10, NULL, '2020-04-23', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1032, 10, NULL, '2020-04-30', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1033, 10, NULL, '2020-05-07', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1034, 10, NULL, '2020-05-14', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1035, 10, NULL, '2020-05-21', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1036, 10, NULL, '2020-05-28', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1037, 10, NULL, '2020-06-04', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1038, 10, NULL, '2020-06-11', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1039, 10, NULL, '2020-06-18', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1040, 10, NULL, '2020-06-25', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1041, 10, NULL, '2020-07-02', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1042, 10, NULL, '2020-07-09', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1043, 10, NULL, '2020-07-16', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1044, 10, NULL, '2020-07-23', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1045, 10, NULL, '2020-07-30', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1046, 10, NULL, '2020-08-06', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1047, 10, NULL, '2020-08-13', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1048, 10, NULL, '2020-08-20', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1049, 10, NULL, '2020-08-27', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1050, 10, NULL, '2020-09-03', 6, 1, 2, NULL, 1600907339, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1051, 10, NULL, '2020-09-10', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1052, 10, NULL, '2020-09-17', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1053, 10, NULL, '2020-09-24', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1054, 10, NULL, '2020-10-01', 6, 1, 2, NULL, 1601360651, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1055, 10, NULL, '2020-10-08', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1056, 10, NULL, '2020-10-15', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1057, 10, NULL, '2020-10-22', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1058, 10, NULL, '2020-10-29', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1059, 10, NULL, '2020-11-05', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1060, 10, NULL, '2020-11-12', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1061, 10, NULL, '2020-11-19', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1062, 10, NULL, '2020-11-26', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1063, 10, NULL, '2020-12-03', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1064, 10, NULL, '2020-12-10', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1065, 10, NULL, '2020-12-17', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1066, 10, NULL, '2020-12-24', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1067, 10, NULL, '2020-12-31', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1068, 10, NULL, '2021-01-07', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1069, 10, NULL, '2021-01-14', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1070, 10, NULL, '2021-01-21', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1071, 10, NULL, '2021-01-28', 6, 1, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1072, 24, NULL, '2020-09-10', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1073, 24, NULL, '2020-09-17', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1074, 24, NULL, '2020-09-24', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1075, 24, NULL, '2020-10-01', 7, 0, 2, NULL, 1601360651, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1076, 24, NULL, '2020-10-08', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1077, 24, NULL, '2020-10-15', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1078, 24, NULL, '2020-10-22', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1079, 24, NULL, '2020-10-29', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1080, 24, NULL, '2020-11-05', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1081, 24, NULL, '2020-11-12', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1082, 24, NULL, '2020-11-19', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1083, 24, NULL, '2020-11-26', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1084, 24, NULL, '2020-12-03', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1085, 24, NULL, '2020-12-10', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1086, 24, NULL, '2020-12-17', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1087, 24, NULL, '2020-12-24', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1088, 24, NULL, '2020-12-31', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1089, 24, NULL, '2021-01-07', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1090, 24, NULL, '2021-01-14', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1091, 24, NULL, '2021-01-21', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1092, 24, NULL, '2021-01-28', 7, 0, 2, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1093, 5, NULL, '2020-09-10', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1094, 5, NULL, '2020-09-17', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1095, 5, NULL, '2020-09-24', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1096, 5, NULL, '2020-10-01', 7, 2, 3, NULL, 1601360651, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1097, 5, NULL, '2020-10-08', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1098, 5, NULL, '2020-10-15', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1099, 5, NULL, '2020-10-22', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1100, 5, NULL, '2020-10-29', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1101, 5, NULL, '2020-11-05', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1102, 5, NULL, '2020-11-12', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1103, 5, NULL, '2020-11-19', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1104, 5, NULL, '2020-11-26', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1105, 5, NULL, '2020-12-03', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1106, 5, NULL, '2020-12-10', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1107, 5, NULL, '2020-12-17', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1108, 5, NULL, '2020-12-24', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1109, 5, NULL, '2020-12-31', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1110, 5, NULL, '2021-01-07', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1111, 5, NULL, '2021-01-14', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1112, 5, NULL, '2021-01-21', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1113, 5, NULL, '2021-01-28', 7, 2, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1114, 22, NULL, '2020-09-10', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1115, 22, NULL, '2020-09-17', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1116, 22, NULL, '2020-09-24', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1117, 22, NULL, '2020-10-01', 6, 0, 3, NULL, 1601360651, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1118, 22, NULL, '2020-10-08', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1119, 22, NULL, '2020-10-15', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1120, 22, NULL, '2020-10-22', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1121, 22, NULL, '2020-10-29', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1122, 22, NULL, '2020-11-05', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1123, 22, NULL, '2020-11-12', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1124, 22, NULL, '2020-11-19', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1125, 22, NULL, '2020-11-26', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1126, 22, NULL, '2020-12-03', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1127, 22, NULL, '2020-12-10', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1128, 22, NULL, '2020-12-17', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1129, 22, NULL, '2020-12-24', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1130, 22, NULL, '2020-12-31', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1131, 22, NULL, '2021-01-07', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1132, 22, NULL, '2021-01-14', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1133, 22, NULL, '2021-01-21', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1134, 22, NULL, '2021-01-28', 6, 0, 3, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1155, 7, NULL, '2020-09-10', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1156, 7, NULL, '2020-09-17', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1157, 7, NULL, '2020-09-24', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1158, 7, NULL, '2020-10-01', 6, 2, 4, NULL, 1601360651, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1159, 7, NULL, '2020-10-08', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1160, 7, NULL, '2020-10-15', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1161, 7, NULL, '2020-10-22', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1162, 7, NULL, '2020-10-29', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1163, 7, NULL, '2020-11-05', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1164, 7, NULL, '2020-11-12', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1165, 7, NULL, '2020-11-19', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1166, 7, NULL, '2020-11-26', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1167, 7, NULL, '2020-12-03', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1168, 7, NULL, '2020-12-10', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1169, 7, NULL, '2020-12-17', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1170, 7, NULL, '2020-12-24', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1171, 7, NULL, '2020-12-31', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1172, 7, NULL, '2021-01-07', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1173, 7, NULL, '2021-01-14', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1174, 7, NULL, '2021-01-21', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1175, 7, NULL, '2021-01-28', 6, 2, 4, NULL, NULL, '2020-09-23 14:45:41', '2020-09-23 14:45:41', 4, 1);
INSERT INTO `course` VALUES (1196, 13, NULL, '2020-09-10', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1197, 13, NULL, '2020-09-17', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1198, 13, NULL, '2020-09-24', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1199, 13, NULL, '2020-10-01', 7, 1, 5, NULL, 1601360651, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1200, 13, NULL, '2020-10-08', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1201, 13, NULL, '2020-10-15', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1202, 13, NULL, '2020-10-22', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1203, 13, NULL, '2020-10-29', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1204, 13, NULL, '2020-11-05', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1205, 13, NULL, '2020-11-12', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1206, 13, NULL, '2020-11-19', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1207, 13, NULL, '2020-11-26', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1208, 13, NULL, '2020-12-03', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1209, 13, NULL, '2020-12-10', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1210, 13, NULL, '2020-12-17', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1211, 13, NULL, '2020-12-24', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1212, 13, NULL, '2020-12-31', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1213, 13, NULL, '2021-01-07', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1214, 13, NULL, '2021-01-14', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1215, 13, NULL, '2021-01-21', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1216, 13, NULL, '2021-01-28', 7, 1, 5, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1227, 14, NULL, '2020-09-10', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1228, 14, NULL, '2020-09-17', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1229, 14, NULL, '2020-09-24', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1230, 14, NULL, '2020-10-01', 7, 1, 6, NULL, 1601360651, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1231, 14, NULL, '2020-10-08', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1232, 14, NULL, '2020-10-15', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1233, 14, NULL, '2020-10-22', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1234, 14, NULL, '2020-10-29', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1235, 14, NULL, '2020-11-05', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1236, 14, NULL, '2020-11-12', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1237, 14, NULL, '2020-11-19', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1238, 14, NULL, '2020-11-26', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1239, 14, NULL, '2020-12-03', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1240, 14, NULL, '2020-12-10', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1241, 14, NULL, '2020-12-17', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1242, 14, NULL, '2020-12-24', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1243, 14, NULL, '2020-12-31', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1244, 14, NULL, '2021-01-07', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1245, 14, NULL, '2021-01-14', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1246, 14, NULL, '2021-01-21', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1247, 14, NULL, '2021-01-28', 7, 1, 6, NULL, NULL, '2020-09-23 14:50:19', '2020-09-23 14:50:19', 4, 1);
INSERT INTO `course` VALUES (1289, 11, NULL, '2020-09-11', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1290, 11, NULL, '2020-09-18', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1291, 11, NULL, '2020-09-25', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1292, 11, NULL, '2020-10-02', 6, 1, 1, NULL, 1601360651, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1293, 11, NULL, '2020-10-09', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1294, 11, NULL, '2020-10-16', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1295, 11, NULL, '2020-10-23', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1296, 11, NULL, '2020-10-30', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1297, 11, NULL, '2020-11-06', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1298, 11, NULL, '2020-11-13', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1299, 11, NULL, '2020-11-20', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1300, 11, NULL, '2020-11-27', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1301, 11, NULL, '2020-12-04', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1302, 11, NULL, '2020-12-11', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1303, 11, NULL, '2020-12-18', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1304, 11, NULL, '2020-12-25', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1305, 11, NULL, '2021-01-01', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1306, 11, NULL, '2021-01-08', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1307, 11, NULL, '2021-01-15', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1308, 11, NULL, '2021-01-22', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1309, 11, NULL, '2021-01-29', 6, 1, 1, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1341, 12, NULL, '2020-09-11', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1342, 12, NULL, '2020-09-18', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1343, 12, NULL, '2020-09-25', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1344, 12, NULL, '2020-10-02', 6, 1, 2, NULL, 1601360651, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1345, 12, NULL, '2020-10-09', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1346, 12, NULL, '2020-10-16', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1347, 12, NULL, '2020-10-23', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1348, 12, NULL, '2020-10-30', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1349, 12, NULL, '2020-11-06', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1350, 12, NULL, '2020-11-13', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1351, 12, NULL, '2020-11-20', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1352, 12, NULL, '2020-11-27', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1353, 12, NULL, '2020-12-04', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1354, 12, NULL, '2020-12-11', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1355, 12, NULL, '2020-12-18', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1356, 12, NULL, '2020-12-25', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1357, 12, NULL, '2021-01-01', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1358, 12, NULL, '2021-01-08', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1359, 12, NULL, '2021-01-15', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1360, 12, NULL, '2021-01-22', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1361, 12, NULL, '2021-01-29', 6, 1, 2, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1372, 6, NULL, '2020-09-11', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1373, 6, NULL, '2020-09-18', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1374, 6, NULL, '2020-09-25', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1375, 6, NULL, '2020-10-02', 7, 2, 3, NULL, 1601360651, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1376, 6, NULL, '2020-10-09', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1377, 6, NULL, '2020-10-16', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1378, 6, NULL, '2020-10-23', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1379, 6, NULL, '2020-10-30', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1380, 6, NULL, '2020-11-06', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1381, 6, NULL, '2020-11-13', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1382, 6, NULL, '2020-11-20', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1383, 6, NULL, '2020-11-27', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1384, 6, NULL, '2020-12-04', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1385, 6, NULL, '2020-12-11', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1386, 6, NULL, '2020-12-18', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1387, 6, NULL, '2020-12-25', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1388, 6, NULL, '2021-01-01', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1389, 6, NULL, '2021-01-08', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1390, 6, NULL, '2021-01-15', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1391, 6, NULL, '2021-01-22', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1392, 6, NULL, '2021-01-29', 7, 2, 3, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1413, 8, NULL, '2020-09-11', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1414, 8, NULL, '2020-09-18', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1415, 8, NULL, '2020-09-25', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1416, 8, NULL, '2020-10-02', 6, 2, 4, NULL, 1601360651, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1417, 8, NULL, '2020-10-09', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1418, 8, NULL, '2020-10-16', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1419, 8, NULL, '2020-10-23', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1420, 8, NULL, '2020-10-30', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1421, 8, NULL, '2020-11-06', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1422, 8, NULL, '2020-11-13', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1423, 8, NULL, '2020-11-20', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1424, 8, NULL, '2020-11-27', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1425, 8, NULL, '2020-12-04', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1426, 8, NULL, '2020-12-11', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1427, 8, NULL, '2020-12-18', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1428, 8, NULL, '2020-12-25', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1429, 8, NULL, '2021-01-01', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1430, 8, NULL, '2021-01-08', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1431, 8, NULL, '2021-01-15', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1432, 8, NULL, '2021-01-22', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1433, 8, NULL, '2021-01-29', 6, 2, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1434, 16, NULL, '2020-09-11', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1435, 16, NULL, '2020-09-18', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1436, 16, NULL, '2020-09-25', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1437, 16, NULL, '2020-10-02', 7, 1, 4, NULL, 1601338867, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1438, 16, NULL, '2020-10-09', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1439, 16, NULL, '2020-10-16', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1440, 16, NULL, '2020-10-23', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1441, 16, NULL, '2020-10-30', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1442, 16, NULL, '2020-11-06', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1443, 16, NULL, '2020-11-13', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1444, 16, NULL, '2020-11-20', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1445, 16, NULL, '2020-11-27', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1446, 16, NULL, '2020-12-04', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1447, 16, NULL, '2020-12-11', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1448, 16, NULL, '2020-12-18', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1449, 16, NULL, '2020-12-25', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1450, 16, NULL, '2021-01-01', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1451, 16, NULL, '2021-01-08', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1452, 16, NULL, '2021-01-15', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1453, 16, NULL, '2021-01-22', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1454, 16, NULL, '2021-01-29', 7, 1, 4, NULL, NULL, '2020-09-23 14:59:35', '2020-09-23 14:59:35', 4, 1);
INSERT INTO `course` VALUES (1475, 22, NULL, '2020-09-11', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1476, 22, NULL, '2020-09-18', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1477, 22, NULL, '2020-09-25', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1478, 22, NULL, '2020-10-02', 6, 0, 5, NULL, 1601338867, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1479, 22, NULL, '2020-10-09', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1480, 22, NULL, '2020-10-16', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1481, 22, NULL, '2020-10-23', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1482, 22, NULL, '2020-10-30', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1483, 22, NULL, '2020-11-06', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1484, 22, NULL, '2020-11-13', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1485, 22, NULL, '2020-11-20', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1486, 22, NULL, '2020-11-27', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1487, 22, NULL, '2020-12-04', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1488, 22, NULL, '2020-12-11', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1489, 22, NULL, '2020-12-18', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1490, 22, NULL, '2020-12-25', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1491, 22, NULL, '2021-01-01', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1492, 22, NULL, '2021-01-08', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1493, 22, NULL, '2021-01-15', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1494, 22, NULL, '2021-01-22', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1495, 22, NULL, '2021-01-29', 6, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1496, 24, NULL, '2020-09-11', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1497, 24, NULL, '2020-09-18', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1498, 24, NULL, '2020-09-25', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1499, 24, NULL, '2020-10-02', 7, 0, 5, NULL, 1601338867, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1500, 24, NULL, '2020-10-09', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1501, 24, NULL, '2020-10-16', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1502, 24, NULL, '2020-10-23', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1503, 24, NULL, '2020-10-30', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1504, 24, NULL, '2020-11-06', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1505, 24, NULL, '2020-11-13', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1506, 24, NULL, '2020-11-20', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1507, 24, NULL, '2020-11-27', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1508, 24, NULL, '2020-12-04', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1509, 24, NULL, '2020-12-11', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1510, 24, NULL, '2020-12-18', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1511, 24, NULL, '2020-12-25', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1512, 24, NULL, '2021-01-01', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1513, 24, NULL, '2021-01-08', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1514, 24, NULL, '2021-01-15', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1515, 24, NULL, '2021-01-22', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1516, 24, NULL, '2021-01-29', 7, 0, 5, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1547, 15, NULL, '2020-09-11', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1548, 15, NULL, '2020-09-18', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1549, 15, NULL, '2020-09-25', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1550, 15, NULL, '2020-10-02', 7, 1, 7, NULL, 1601338867, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1551, 15, NULL, '2020-10-09', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1552, 15, NULL, '2020-10-16', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1553, 15, NULL, '2020-10-23', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1554, 15, NULL, '2020-10-30', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1555, 15, NULL, '2020-11-06', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1556, 15, NULL, '2020-11-13', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1557, 15, NULL, '2020-11-20', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1558, 15, NULL, '2020-11-27', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1559, 15, NULL, '2020-12-04', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1560, 15, NULL, '2020-12-11', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1561, 15, NULL, '2020-12-18', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1562, 15, NULL, '2020-12-25', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1563, 15, NULL, '2021-01-01', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1564, 15, NULL, '2021-01-08', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1565, 15, NULL, '2021-01-15', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1566, 15, NULL, '2021-01-22', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1567, 15, NULL, '2021-01-29', 7, 1, 7, NULL, NULL, '2020-09-23 15:07:27', '2020-09-23 15:07:27', 4, 1);
INSERT INTO `course` VALUES (1738, 4, NULL, '2020-09-07', 5, 2, 1, NULL, 1600907477, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1739, 4, NULL, '2020-09-14', 5, 2, 1, NULL, 1600907496, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1740, 4, NULL, '2020-09-21', 5, 2, 1, NULL, 1600907502, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1741, 4, NULL, '2020-09-28', 5, 2, 1, NULL, 1600907507, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1742, 4, NULL, '2020-10-05', 5, 2, 1, NULL, 1600907512, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1743, 4, NULL, '2020-10-12', 5, 2, 1, NULL, 1600907517, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1744, 4, NULL, '2020-10-19', 5, 2, 1, NULL, 1600907521, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1745, 4, NULL, '2020-10-26', 5, 2, 1, NULL, 1600907526, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1746, 4, NULL, '2020-11-02', 5, 2, 1, NULL, 1600907532, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1747, 4, NULL, '2020-11-09', 5, 2, 1, NULL, 1600907536, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1748, 4, NULL, '2020-11-16', 5, 2, 1, NULL, 1600907540, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1749, 4, NULL, '2020-11-23', 5, 2, 1, NULL, 1600907556, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1750, 4, NULL, '2020-11-30', 5, 2, 1, NULL, 1600907606, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1751, 4, NULL, '2020-12-07', 5, 2, 1, NULL, 1600907611, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1752, 4, NULL, '2020-12-14', 5, 2, 1, NULL, 1600907616, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1753, 4, NULL, '2020-12-21', 5, 2, 1, NULL, 1600907621, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1754, 4, NULL, '2020-12-28', 5, 2, 1, NULL, 1600907626, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1755, 4, NULL, '2021-01-04', 5, 2, 1, NULL, 1600907630, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1756, 4, NULL, '2021-01-11', 5, 2, 1, NULL, 1600907635, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1757, 4, NULL, '2021-01-18', 5, 2, 1, NULL, 1600907639, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1758, 4, NULL, '2021-01-25', 5, 2, 1, NULL, 1600907644, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1759, 2, NULL, '2020-09-07', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1760, 2, NULL, '2020-09-14', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1761, 2, NULL, '2020-09-21', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1762, 2, NULL, '2020-09-28', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1763, 2, NULL, '2020-10-05', 5, 2, 6, NULL, 1601338867, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1764, 2, NULL, '2020-10-12', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1765, 2, NULL, '2020-10-19', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1766, 2, NULL, '2020-10-26', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1767, 2, NULL, '2020-11-02', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1768, 2, NULL, '2020-11-09', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1769, 2, NULL, '2020-11-16', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1770, 2, NULL, '2020-11-23', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1771, 2, NULL, '2020-11-30', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1772, 2, NULL, '2020-12-07', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1773, 2, NULL, '2020-12-14', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1774, 2, NULL, '2020-12-21', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1775, 2, NULL, '2020-12-28', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1776, 2, NULL, '2021-01-04', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1777, 2, NULL, '2021-01-11', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1778, 2, NULL, '2021-01-18', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1779, 2, NULL, '2021-01-25', 5, 2, 6, NULL, NULL, '2020-09-23 17:38:10', '2020-09-23 17:38:10', 4, 1);
INSERT INTO `course` VALUES (1780, 25, NULL, '2020-09-08', 5, 0, 1, NULL, NULL, '2020-09-23 17:39:27', '2020-09-23 17:39:27', 4, 1);
INSERT INTO `course` VALUES (1781, 25, NULL, '2020-09-22', 5, 0, 1, NULL, NULL, '2020-09-23 17:39:27', '2020-09-23 17:39:27', 4, 1);
INSERT INTO `course` VALUES (1782, 25, NULL, '2020-10-06', 5, 0, 1, NULL, 1601338867, '2020-09-23 17:39:27', '2020-09-23 17:39:27', 4, 1);
INSERT INTO `course` VALUES (1783, 25, NULL, '2020-10-20', 5, 0, 1, NULL, NULL, '2020-09-23 17:39:27', '2020-09-23 17:39:27', 4, 1);
INSERT INTO `course` VALUES (1784, 25, NULL, '2020-11-03', 5, 0, 1, NULL, NULL, '2020-09-23 17:39:27', '2020-09-23 17:39:27', 4, 1);
INSERT INTO `course` VALUES (1785, 25, NULL, '2020-11-17', 5, 0, 1, NULL, NULL, '2020-09-23 17:39:27', '2020-09-23 17:39:27', 4, 1);
INSERT INTO `course` VALUES (1786, 25, NULL, '2020-12-01', 5, 0, 1, NULL, NULL, '2020-09-23 17:39:27', '2020-09-23 17:39:27', 4, 1);
INSERT INTO `course` VALUES (1787, 25, NULL, '2020-12-15', 5, 0, 1, NULL, NULL, '2020-09-23 17:39:27', '2020-09-23 17:39:27', 4, 1);
INSERT INTO `course` VALUES (1788, 25, NULL, '2020-12-29', 5, 0, 1, NULL, NULL, '2020-09-23 17:39:27', '2020-09-23 17:39:27', 4, 1);
INSERT INTO `course` VALUES (1789, 25, NULL, '2021-01-12', 5, 0, 1, NULL, NULL, '2020-09-23 17:39:27', '2020-09-23 17:39:27', 4, 1);
INSERT INTO `course` VALUES (1790, 25, NULL, '2021-01-26', 5, 0, 1, NULL, NULL, '2020-09-23 17:39:27', '2020-09-23 17:39:27', 4, 1);
INSERT INTO `course` VALUES (1791, 34, NULL, '2020-09-08', 9, 0, 1, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1792, 34, NULL, '2020-09-22', 9, 0, 1, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1793, 34, NULL, '2020-10-06', 9, 0, 1, NULL, 1601338867, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1794, 34, NULL, '2020-10-20', 9, 0, 1, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1795, 34, NULL, '2020-11-03', 9, 0, 1, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1796, 34, NULL, '2020-11-17', 9, 0, 1, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1797, 34, NULL, '2020-12-01', 9, 0, 1, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1798, 34, NULL, '2020-12-15', 9, 0, 1, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1799, 34, NULL, '2020-12-29', 9, 0, 1, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1800, 34, NULL, '2021-01-12', 9, 0, 1, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1801, 34, NULL, '2021-01-26', 9, 0, 1, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1802, 26, NULL, '2020-09-08', 5, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1803, 26, NULL, '2020-09-22', 5, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1804, 26, NULL, '2020-10-06', 5, 0, 2, NULL, 1601338867, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1805, 26, NULL, '2020-10-20', 5, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1806, 26, NULL, '2020-11-03', 5, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1807, 26, NULL, '2020-11-17', 5, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1808, 26, NULL, '2020-12-01', 5, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1809, 26, NULL, '2020-12-15', 5, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1810, 26, NULL, '2020-12-29', 5, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1811, 26, NULL, '2021-01-12', 5, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1812, 26, NULL, '2021-01-26', 5, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1813, 35, NULL, '2020-09-08', 9, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1814, 35, NULL, '2020-09-22', 9, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1815, 35, NULL, '2020-10-06', 9, 0, 2, NULL, 1601338867, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1816, 35, NULL, '2020-10-20', 9, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1817, 35, NULL, '2020-11-03', 9, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1818, 35, NULL, '2020-11-17', 9, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1819, 35, NULL, '2020-12-01', 9, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1820, 35, NULL, '2020-12-15', 9, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1821, 35, NULL, '2020-12-29', 9, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1822, 35, NULL, '2021-01-12', 9, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1823, 35, NULL, '2021-01-26', 9, 0, 2, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1824, 36, NULL, '2020-09-08', 9, 0, 3, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1825, 36, NULL, '2020-09-22', 9, 0, 3, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1826, 36, NULL, '2020-10-06', 9, 0, 3, NULL, 1601338867, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1827, 36, NULL, '2020-10-20', 9, 0, 3, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1828, 36, NULL, '2020-11-03', 9, 0, 3, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1829, 36, NULL, '2020-11-17', 9, 0, 3, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1830, 36, NULL, '2020-12-01', 9, 0, 3, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1831, 36, NULL, '2020-12-15', 9, 0, 3, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1832, 36, NULL, '2020-12-29', 9, 0, 3, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1833, 36, NULL, '2021-01-12', 9, 0, 3, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1834, 36, NULL, '2021-01-26', 9, 0, 3, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1835, 37, NULL, '2020-09-08', 9, 0, 4, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1836, 37, NULL, '2020-09-22', 9, 0, 4, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1837, 37, NULL, '2020-10-06', 9, 0, 4, NULL, 1601338867, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1838, 37, NULL, '2020-10-20', 9, 0, 4, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1839, 37, NULL, '2020-11-03', 9, 0, 4, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1840, 37, NULL, '2020-11-17', 9, 0, 4, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1841, 37, NULL, '2020-12-01', 9, 0, 4, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1842, 37, NULL, '2020-12-15', 9, 0, 4, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1843, 37, NULL, '2020-12-29', 9, 0, 4, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1844, 37, NULL, '2021-01-12', 9, 0, 4, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1845, 37, NULL, '2021-01-26', 9, 0, 4, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1846, 27, NULL, '2020-09-08', 5, 0, 5, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1847, 27, NULL, '2020-09-22', 5, 0, 5, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1848, 27, NULL, '2020-10-06', 5, 0, 5, NULL, 1601338867, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1849, 27, NULL, '2020-10-20', 5, 0, 5, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1850, 27, NULL, '2020-11-03', 5, 0, 5, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1851, 27, NULL, '2020-11-17', 5, 0, 5, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1852, 27, NULL, '2020-12-01', 5, 0, 5, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1853, 27, NULL, '2020-12-15', 5, 0, 5, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1854, 27, NULL, '2020-12-29', 5, 0, 5, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1855, 27, NULL, '2021-01-12', 5, 0, 5, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1856, 27, NULL, '2021-01-26', 5, 0, 5, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1857, 1, NULL, '2020-09-08', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1858, 1, NULL, '2020-09-15', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1859, 1, NULL, '2020-09-22', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1860, 1, NULL, '2020-09-29', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1861, 1, NULL, '2020-10-06', 5, 2, 6, NULL, 1601338867, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1862, 1, NULL, '2020-10-13', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1863, 1, NULL, '2020-10-20', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1864, 1, NULL, '2020-10-27', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1865, 1, NULL, '2020-11-03', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1866, 1, NULL, '2020-11-10', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1867, 1, NULL, '2020-11-17', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1868, 1, NULL, '2020-11-24', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1869, 1, NULL, '2020-12-01', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1870, 1, NULL, '2020-12-08', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1871, 1, NULL, '2020-12-15', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1872, 1, NULL, '2020-12-22', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1873, 1, NULL, '2020-12-29', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1874, 1, NULL, '2021-01-05', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1875, 1, NULL, '2021-01-12', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1876, 1, NULL, '2021-01-19', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1877, 1, NULL, '2021-01-26', 5, 2, 6, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1878, 28, NULL, '2020-09-08', 5, 0, 7, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1879, 28, NULL, '2020-09-22', 5, 0, 7, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1880, 28, NULL, '2020-10-06', 5, 0, 7, NULL, 1601338867, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1881, 28, NULL, '2020-10-20', 5, 0, 7, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1882, 28, NULL, '2020-11-03', 5, 0, 7, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1883, 28, NULL, '2020-11-17', 5, 0, 7, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1884, 28, NULL, '2020-12-01', 5, 0, 7, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1885, 28, NULL, '2020-12-15', 5, 0, 7, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1886, 28, NULL, '2020-12-29', 5, 0, 7, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1887, 28, NULL, '2021-01-12', 5, 0, 7, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1888, 28, NULL, '2021-01-26', 5, 0, 7, NULL, NULL, '2020-09-23 18:09:01', '2020-09-23 18:09:01', 4, 1);
INSERT INTO `course` VALUES (1889, 29, NULL, '2020-09-16', 5, 0, 1, NULL, 1600905281, '2020-09-24 07:52:01', '2020-09-24 07:52:01', 4, 1);
INSERT INTO `course` VALUES (1890, 29, NULL, '2020-09-30', 5, 0, 1, NULL, 1600905277, '2020-09-24 07:52:01', '2020-09-24 07:52:01', 4, 1);
INSERT INTO `course` VALUES (1891, 29, NULL, '2020-10-14', 5, 0, 1, NULL, 1600905273, '2020-09-24 07:52:01', '2020-09-24 07:52:01', 4, 1);
INSERT INTO `course` VALUES (1892, 29, NULL, '2020-10-28', 5, 0, 1, NULL, 1600905269, '2020-09-24 07:52:01', '2020-09-24 07:52:01', 4, 1);
INSERT INTO `course` VALUES (1893, 29, NULL, '2020-11-11', 5, 0, 1, NULL, 1600905265, '2020-09-24 07:52:01', '2020-09-24 07:52:01', 4, 1);
INSERT INTO `course` VALUES (1894, 29, NULL, '2020-11-25', 5, 0, 1, NULL, 1600905262, '2020-09-24 07:52:01', '2020-09-24 07:52:01', 4, 1);
INSERT INTO `course` VALUES (1895, 29, NULL, '2020-12-09', 5, 0, 1, NULL, 1600905258, '2020-09-24 07:52:01', '2020-09-24 07:52:01', 4, 1);
INSERT INTO `course` VALUES (1896, 29, NULL, '2020-12-23', 5, 0, 1, NULL, 1600905254, '2020-09-24 07:52:01', '2020-09-24 07:52:01', 4, 1);
INSERT INTO `course` VALUES (1897, 29, NULL, '2021-01-06', 5, 0, 1, NULL, 1600905250, '2020-09-24 07:52:01', '2020-09-24 07:52:01', 4, 1);
INSERT INTO `course` VALUES (1898, 29, NULL, '2021-01-20', 5, 0, 1, NULL, 1600905246, '2020-09-24 07:52:01', '2020-09-24 07:52:01', 4, 1);
INSERT INTO `course` VALUES (1899, 29, NULL, '2020-09-09', 5, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1900, 29, NULL, '2020-09-23', 5, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1901, 29, NULL, '2020-10-07', 5, 0, 1, NULL, 1601338867, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1902, 29, NULL, '2020-10-21', 5, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1903, 29, NULL, '2020-11-04', 5, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1904, 29, NULL, '2020-11-18', 5, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1905, 29, NULL, '2020-12-02', 5, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1906, 29, NULL, '2020-12-16', 5, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1907, 29, NULL, '2020-12-30', 5, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1908, 29, NULL, '2021-01-13', 5, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1909, 29, NULL, '2021-01-27', 5, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1910, 38, NULL, '2020-09-09', 9, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1911, 38, NULL, '2020-09-23', 9, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1912, 38, NULL, '2020-10-07', 9, 0, 1, NULL, 1601338867, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1913, 38, NULL, '2020-10-21', 9, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1914, 38, NULL, '2020-11-04', 9, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1915, 38, NULL, '2020-11-18', 9, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1916, 38, NULL, '2020-12-02', 9, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1917, 38, NULL, '2020-12-16', 9, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1918, 38, NULL, '2020-12-30', 9, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1919, 38, NULL, '2021-01-13', 9, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1920, 38, NULL, '2021-01-27', 9, 0, 1, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1921, 39, NULL, '2020-09-09', 9, 0, 2, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1922, 39, NULL, '2020-09-23', 9, 0, 2, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1923, 39, NULL, '2020-10-07', 9, 0, 2, NULL, 1601338867, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1924, 39, NULL, '2020-10-21', 9, 0, 2, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1925, 39, NULL, '2020-11-04', 9, 0, 2, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1926, 39, NULL, '2020-11-18', 9, 0, 2, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1927, 39, NULL, '2020-12-02', 9, 0, 2, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1928, 39, NULL, '2020-12-16', 9, 0, 2, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1929, 39, NULL, '2020-12-30', 9, 0, 2, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1930, 39, NULL, '2021-01-13', 9, 0, 2, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1931, 39, NULL, '2021-01-27', 9, 0, 2, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1932, 31, NULL, '2020-09-09', 5, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1933, 31, NULL, '2020-09-23', 5, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1934, 31, NULL, '2020-10-07', 5, 0, 3, NULL, 1601338867, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1935, 31, NULL, '2020-10-21', 5, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1936, 31, NULL, '2020-11-04', 5, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1937, 31, NULL, '2020-11-18', 5, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1938, 31, NULL, '2020-12-02', 5, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1939, 31, NULL, '2020-12-16', 5, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1940, 31, NULL, '2020-12-30', 5, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1941, 31, NULL, '2021-01-13', 5, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1942, 31, NULL, '2021-01-27', 5, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1943, 40, NULL, '2020-09-09', 9, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1944, 40, NULL, '2020-09-23', 9, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1945, 40, NULL, '2020-10-07', 9, 0, 3, NULL, 1601338867, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1946, 40, NULL, '2020-10-21', 9, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1947, 40, NULL, '2020-11-04', 9, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1948, 40, NULL, '2020-11-18', 9, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1949, 40, NULL, '2020-12-02', 9, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1950, 40, NULL, '2020-12-16', 9, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1951, 40, NULL, '2020-12-30', 9, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1952, 40, NULL, '2021-01-13', 9, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1953, 40, NULL, '2021-01-27', 9, 0, 3, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1954, 32, NULL, '2020-09-09', 9, 0, 4, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1955, 32, NULL, '2020-09-23', 9, 0, 4, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1956, 32, NULL, '2020-10-07', 9, 0, 4, NULL, 1601338867, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1957, 32, NULL, '2020-10-21', 9, 0, 4, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1958, 32, NULL, '2020-11-04', 9, 0, 4, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1959, 32, NULL, '2020-11-18', 9, 0, 4, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1960, 32, NULL, '2020-12-02', 9, 0, 4, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1961, 32, NULL, '2020-12-16', 9, 0, 4, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1962, 32, NULL, '2020-12-30', 9, 0, 4, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1963, 32, NULL, '2021-01-13', 9, 0, 4, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1964, 32, NULL, '2021-01-27', 9, 0, 4, NULL, NULL, '2020-09-24 07:57:36', '2020-09-24 07:57:36', 4, 1);
INSERT INTO `course` VALUES (1965, 33, NULL, '2020-09-09', 5, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1966, 33, NULL, '2020-09-23', 5, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1967, 33, NULL, '2020-10-07', 5, 0, 5, NULL, 1601338867, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1968, 33, NULL, '2020-10-21', 5, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1969, 33, NULL, '2020-11-04', 5, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1970, 33, NULL, '2020-11-18', 5, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1971, 33, NULL, '2020-12-02', 5, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1972, 33, NULL, '2020-12-16', 5, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1973, 33, NULL, '2020-12-30', 5, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1974, 33, NULL, '2021-01-13', 5, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1975, 33, NULL, '2021-01-27', 5, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1976, 41, NULL, '2020-09-09', 9, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1977, 41, NULL, '2020-09-23', 9, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1978, 41, NULL, '2020-10-07', 9, 0, 5, NULL, 1601338867, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1979, 41, NULL, '2020-10-21', 9, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1980, 41, NULL, '2020-11-04', 9, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1981, 41, NULL, '2020-11-18', 9, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1982, 41, NULL, '2020-12-02', 9, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1983, 41, NULL, '2020-12-16', 9, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1984, 41, NULL, '2020-12-30', 9, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1985, 41, NULL, '2021-01-13', 9, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1986, 41, NULL, '2021-01-27', 9, 0, 5, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1987, 30, NULL, '2020-09-09', 9, 0, 6, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1988, 30, NULL, '2020-09-23', 9, 0, 6, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1989, 30, NULL, '2020-10-07', 9, 0, 6, NULL, 1601338867, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1990, 30, NULL, '2020-10-21', 9, 0, 6, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1991, 30, NULL, '2020-11-04', 9, 0, 6, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1992, 30, NULL, '2020-11-18', 9, 0, 6, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1993, 30, NULL, '2020-12-02', 9, 0, 6, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1994, 30, NULL, '2020-12-16', 9, 0, 6, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1995, 30, NULL, '2020-12-30', 9, 0, 6, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1996, 30, NULL, '2021-01-13', 9, 0, 6, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1997, 30, NULL, '2021-01-27', 9, 0, 6, NULL, NULL, '2020-09-24 07:59:26', '2020-09-24 07:59:26', 4, 1);
INSERT INTO `course` VALUES (1998, 2, NULL, '2020-09-10', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (1999, 2, NULL, '2020-09-17', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2000, 2, NULL, '2020-09-24', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2001, 2, NULL, '2020-10-01', 5, 2, 1, NULL, 1601360651, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2002, 2, NULL, '2020-10-08', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2003, 2, NULL, '2020-10-15', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2004, 2, NULL, '2020-10-22', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2005, 2, NULL, '2020-10-29', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2006, 2, NULL, '2020-11-05', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2007, 2, NULL, '2020-11-12', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2008, 2, NULL, '2020-11-19', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2009, 2, NULL, '2020-11-26', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2010, 2, NULL, '2020-12-03', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2011, 2, NULL, '2020-12-10', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2012, 2, NULL, '2020-12-17', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2013, 2, NULL, '2020-12-24', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2014, 2, NULL, '2020-12-31', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2015, 2, NULL, '2021-01-07', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2016, 2, NULL, '2021-01-14', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2017, 2, NULL, '2021-01-21', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2018, 2, NULL, '2021-01-28', 5, 2, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2019, 34, NULL, '2020-09-10', 9, 0, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2020, 34, NULL, '2020-09-24', 9, 0, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2021, 34, NULL, '2020-10-08', 9, 0, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2022, 34, NULL, '2020-10-22', 9, 0, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2023, 34, NULL, '2020-11-05', 9, 0, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2024, 34, NULL, '2020-11-19', 9, 0, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2025, 34, NULL, '2020-12-03', 9, 0, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2026, 34, NULL, '2020-12-17', 9, 0, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2027, 34, NULL, '2020-12-31', 9, 0, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2028, 34, NULL, '2021-01-14', 9, 0, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2029, 34, NULL, '2021-01-28', 9, 0, 1, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2030, 4, NULL, '2020-09-10', 5, 2, 2, 10, NULL, '2020-09-24 08:05:57', '2020-09-24 09:08:49', 4, 1);
INSERT INTO `course` VALUES (2031, 4, NULL, '2020-09-17', 5, 2, 2, 11, NULL, '2020-09-24 08:05:57', '2020-09-24 09:09:00', 4, 1);
INSERT INTO `course` VALUES (2032, 4, NULL, '2020-09-24', 5, 2, 2, 12, NULL, '2020-09-24 08:05:57', '2020-09-24 09:09:11', 4, 1);
INSERT INTO `course` VALUES (2033, 4, NULL, '2020-10-01', 5, 2, 2, 13, 1601360651, '2020-09-24 08:05:57', '2020-09-24 09:09:21', 4, 1);
INSERT INTO `course` VALUES (2034, 4, NULL, '2020-10-08', 5, 2, 2, 14, NULL, '2020-09-24 08:05:57', '2020-09-24 09:09:33', 4, 1);
INSERT INTO `course` VALUES (2035, 4, NULL, '2020-10-15', 5, 2, 2, 15, NULL, '2020-09-24 08:05:57', '2020-09-24 09:09:44', 4, 1);
INSERT INTO `course` VALUES (2036, 4, NULL, '2020-10-22', 5, 2, 2, 17, NULL, '2020-09-24 08:05:57', '2020-09-24 09:10:00', 4, 1);
INSERT INTO `course` VALUES (2037, 4, NULL, '2020-10-29', 5, 2, 2, 19, NULL, '2020-09-24 08:05:57', '2020-09-24 09:10:16', 4, 1);
INSERT INTO `course` VALUES (2038, 4, NULL, '2020-11-05', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2039, 4, NULL, '2020-11-12', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2040, 4, NULL, '2020-11-19', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2041, 4, NULL, '2020-11-26', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2042, 4, NULL, '2020-12-03', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2043, 4, NULL, '2020-12-10', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2044, 4, NULL, '2020-12-17', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2045, 4, NULL, '2020-12-24', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2046, 4, NULL, '2020-12-31', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2047, 4, NULL, '2021-01-07', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2048, 4, NULL, '2021-01-14', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2049, 4, NULL, '2021-01-21', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2050, 4, NULL, '2021-01-28', 5, 2, 2, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2051, 25, NULL, '2020-09-10', 5, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2052, 25, NULL, '2020-09-24', 5, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2053, 25, NULL, '2020-10-08', 5, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2054, 25, NULL, '2020-10-22', 5, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2055, 25, NULL, '2020-11-05', 5, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2056, 25, NULL, '2020-11-19', 5, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2057, 25, NULL, '2020-12-03', 5, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2058, 25, NULL, '2020-12-17', 5, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2059, 25, NULL, '2020-12-31', 5, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2060, 25, NULL, '2021-01-14', 5, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2061, 25, NULL, '2021-01-28', 5, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2062, 35, NULL, '2020-09-10', 9, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2063, 35, NULL, '2020-09-24', 9, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2064, 35, NULL, '2020-10-08', 9, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2065, 35, NULL, '2020-10-22', 9, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2066, 35, NULL, '2020-11-05', 9, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2067, 35, NULL, '2020-11-19', 9, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2068, 35, NULL, '2020-12-03', 9, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2069, 35, NULL, '2020-12-17', 9, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2070, 35, NULL, '2020-12-31', 9, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2071, 35, NULL, '2021-01-14', 9, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2072, 35, NULL, '2021-01-28', 9, 0, 3, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2073, 26, NULL, '2020-09-10', 5, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2074, 26, NULL, '2020-09-24', 5, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2075, 26, NULL, '2020-10-08', 5, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2076, 26, NULL, '2020-10-22', 5, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2077, 26, NULL, '2020-11-05', 5, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2078, 26, NULL, '2020-11-19', 5, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2079, 26, NULL, '2020-12-03', 5, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2080, 26, NULL, '2020-12-17', 5, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2081, 26, NULL, '2020-12-31', 5, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2082, 26, NULL, '2021-01-14', 5, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2083, 26, NULL, '2021-01-28', 5, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2084, 36, NULL, '2020-09-10', 9, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2085, 36, NULL, '2020-09-24', 9, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2086, 36, NULL, '2020-10-08', 9, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2087, 36, NULL, '2020-10-22', 9, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2088, 36, NULL, '2020-11-05', 9, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2089, 36, NULL, '2020-11-19', 9, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2090, 36, NULL, '2020-12-03', 9, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2091, 36, NULL, '2020-12-17', 9, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2092, 36, NULL, '2020-12-31', 9, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2093, 36, NULL, '2021-01-14', 9, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2094, 36, NULL, '2021-01-28', 9, 0, 4, NULL, NULL, '2020-09-24 08:05:57', '2020-09-24 08:05:57', 4, 1);
INSERT INTO `course` VALUES (2095, 27, NULL, '2020-09-10', 5, 0, 5, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2096, 27, NULL, '2020-09-24', 5, 0, 5, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2097, 27, NULL, '2020-10-08', 5, 0, 5, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2098, 27, NULL, '2020-10-22', 5, 0, 5, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2099, 27, NULL, '2020-11-05', 5, 0, 5, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2100, 27, NULL, '2020-11-19', 5, 0, 5, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2101, 27, NULL, '2020-12-03', 5, 0, 5, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2102, 27, NULL, '2020-12-17', 5, 0, 5, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2103, 27, NULL, '2020-12-31', 5, 0, 5, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2104, 27, NULL, '2021-01-14', 5, 0, 5, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2105, 27, NULL, '2021-01-28', 5, 0, 5, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2106, 28, NULL, '2020-09-10', 5, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2107, 28, NULL, '2020-09-24', 5, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2108, 28, NULL, '2020-10-08', 5, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2109, 28, NULL, '2020-10-22', 5, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2110, 28, NULL, '2020-11-05', 5, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2111, 28, NULL, '2020-11-19', 5, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2112, 28, NULL, '2020-12-03', 5, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2113, 28, NULL, '2020-12-17', 5, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2114, 28, NULL, '2020-12-31', 5, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2115, 28, NULL, '2021-01-14', 5, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2116, 28, NULL, '2021-01-28', 5, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2117, 37, NULL, '2020-09-10', 9, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2118, 37, NULL, '2020-09-24', 9, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2119, 37, NULL, '2020-10-08', 9, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2120, 37, NULL, '2020-10-22', 9, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2121, 37, NULL, '2020-11-05', 9, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2122, 37, NULL, '2020-11-19', 9, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2123, 37, NULL, '2020-12-03', 9, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2124, 37, NULL, '2020-12-17', 9, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2125, 37, NULL, '2020-12-31', 9, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2126, 37, NULL, '2021-01-14', 9, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2127, 37, NULL, '2021-01-28', 9, 0, 6, NULL, NULL, '2020-09-24 08:07:39', '2020-09-24 08:07:39', 4, 1);
INSERT INTO `course` VALUES (2128, 1, NULL, '2020-09-11', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2129, 1, NULL, '2020-09-18', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2130, 1, NULL, '2020-09-25', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2131, 1, NULL, '2020-10-02', 5, 2, 1, NULL, 1601338867, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2132, 1, NULL, '2020-10-09', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2133, 1, NULL, '2020-10-16', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2134, 1, NULL, '2020-10-23', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2135, 1, NULL, '2020-10-30', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2136, 1, NULL, '2020-11-06', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2137, 1, NULL, '2020-11-13', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2138, 1, NULL, '2020-11-20', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2139, 1, NULL, '2020-11-27', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2140, 1, NULL, '2020-12-04', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2141, 1, NULL, '2020-12-11', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2142, 1, NULL, '2020-12-18', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2143, 1, NULL, '2020-12-25', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2144, 1, NULL, '2021-01-01', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2145, 1, NULL, '2021-01-08', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2146, 1, NULL, '2021-01-15', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2147, 1, NULL, '2021-01-22', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2148, 1, NULL, '2021-01-29', 5, 2, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2149, 38, NULL, '2020-09-11', 9, 0, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2150, 38, NULL, '2020-09-25', 9, 0, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2151, 38, NULL, '2020-10-09', 9, 0, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2152, 38, NULL, '2020-10-23', 9, 0, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2153, 38, NULL, '2020-11-06', 9, 0, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2154, 38, NULL, '2020-11-20', 9, 0, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2155, 38, NULL, '2020-12-04', 9, 0, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2156, 38, NULL, '2020-12-18', 9, 0, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2157, 38, NULL, '2021-01-01', 9, 0, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2158, 38, NULL, '2021-01-15', 9, 0, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2159, 38, NULL, '2021-01-29', 9, 0, 1, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2160, 3, NULL, '2020-09-11', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2161, 3, NULL, '2020-09-18', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2162, 3, NULL, '2020-09-25', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2163, 3, NULL, '2020-10-02', 5, 2, 2, NULL, 1601338867, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2164, 3, NULL, '2020-10-09', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2165, 3, NULL, '2020-10-16', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2166, 3, NULL, '2020-10-23', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2167, 3, NULL, '2020-10-30', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2168, 3, NULL, '2020-11-06', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2169, 3, NULL, '2020-11-13', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2170, 3, NULL, '2020-11-20', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2171, 3, NULL, '2020-11-27', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2172, 3, NULL, '2020-12-04', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2173, 3, NULL, '2020-12-11', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2174, 3, NULL, '2020-12-18', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2175, 3, NULL, '2020-12-25', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2176, 3, NULL, '2021-01-01', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2177, 3, NULL, '2021-01-08', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2178, 3, NULL, '2021-01-15', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2179, 3, NULL, '2021-01-22', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2180, 3, NULL, '2021-01-29', 5, 2, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2181, 30, NULL, '2020-09-11', 9, 0, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2182, 30, NULL, '2020-09-25', 9, 0, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2183, 30, NULL, '2020-10-09', 9, 0, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2184, 30, NULL, '2020-10-23', 9, 0, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2185, 30, NULL, '2020-11-06', 9, 0, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2186, 30, NULL, '2020-11-20', 9, 0, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2187, 30, NULL, '2020-12-04', 9, 0, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2188, 30, NULL, '2020-12-18', 9, 0, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2189, 30, NULL, '2021-01-01', 9, 0, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2190, 30, NULL, '2021-01-15', 9, 0, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2191, 30, NULL, '2021-01-29', 9, 0, 2, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2192, 33, NULL, '2020-09-11', 5, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2193, 33, NULL, '2020-09-25', 5, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2194, 33, NULL, '2020-10-09', 5, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2195, 33, NULL, '2020-10-23', 5, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2196, 33, NULL, '2020-11-06', 5, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2197, 33, NULL, '2020-11-20', 5, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2198, 33, NULL, '2020-12-04', 5, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2199, 33, NULL, '2020-12-18', 5, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2200, 33, NULL, '2021-01-01', 5, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2201, 33, NULL, '2021-01-15', 5, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2202, 33, NULL, '2021-01-29', 5, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2203, 39, NULL, '2020-09-11', 9, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2204, 39, NULL, '2020-09-25', 9, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2205, 39, NULL, '2020-10-09', 9, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2206, 39, NULL, '2020-10-23', 9, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2207, 39, NULL, '2020-11-06', 9, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2208, 39, NULL, '2020-11-20', 9, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2209, 39, NULL, '2020-12-04', 9, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2210, 39, NULL, '2020-12-18', 9, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2211, 39, NULL, '2021-01-01', 9, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2212, 39, NULL, '2021-01-15', 9, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2213, 39, NULL, '2021-01-29', 9, 0, 3, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2214, 29, NULL, '2020-09-11', 5, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2215, 29, NULL, '2020-09-25', 5, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2216, 29, NULL, '2020-10-09', 5, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2217, 29, NULL, '2020-10-23', 5, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2218, 29, NULL, '2020-11-06', 5, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2219, 29, NULL, '2020-11-20', 5, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2220, 29, NULL, '2020-12-04', 5, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2221, 29, NULL, '2020-12-18', 5, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2222, 29, NULL, '2021-01-01', 5, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2223, 29, NULL, '2021-01-15', 5, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2224, 29, NULL, '2021-01-29', 5, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2225, 40, NULL, '2020-09-11', 9, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2226, 40, NULL, '2020-09-25', 9, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2227, 40, NULL, '2020-10-09', 9, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2228, 40, NULL, '2020-10-23', 9, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2229, 40, NULL, '2020-11-06', 9, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2230, 40, NULL, '2020-11-20', 9, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2231, 40, NULL, '2020-12-04', 9, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2232, 40, NULL, '2020-12-18', 9, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2233, 40, NULL, '2021-01-01', 9, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2234, 40, NULL, '2021-01-15', 9, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2235, 40, NULL, '2021-01-29', 9, 0, 4, NULL, NULL, '2020-09-24 08:20:41', '2020-09-24 08:20:41', 4, 1);
INSERT INTO `course` VALUES (2239, 31, NULL, '2020-09-11', 5, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2240, 31, NULL, '2020-09-25', 5, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2241, 31, NULL, '2020-10-09', 5, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2242, 31, NULL, '2020-10-23', 5, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2243, 31, NULL, '2020-11-06', 5, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2244, 31, NULL, '2020-11-20', 5, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2245, 31, NULL, '2020-12-04', 5, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2246, 31, NULL, '2020-12-18', 5, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2247, 31, NULL, '2021-01-01', 5, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2248, 31, NULL, '2021-01-15', 5, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2249, 31, NULL, '2021-01-29', 5, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2250, 41, NULL, '2020-09-11', 9, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2251, 41, NULL, '2020-09-25', 9, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2252, 41, NULL, '2020-10-09', 9, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2253, 41, NULL, '2020-10-23', 9, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2254, 41, NULL, '2020-11-06', 9, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2255, 41, NULL, '2020-11-20', 9, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2256, 41, NULL, '2020-12-04', 9, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2257, 41, NULL, '2020-12-18', 9, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2258, 41, NULL, '2021-01-01', 9, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2259, 41, NULL, '2021-01-15', 9, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2260, 41, NULL, '2021-01-29', 9, 0, 5, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2261, 32, NULL, '2020-09-11', 9, 0, 6, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2262, 32, NULL, '2020-09-25', 9, 0, 6, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2263, 32, NULL, '2020-10-09', 9, 0, 6, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2264, 32, NULL, '2020-10-23', 9, 0, 6, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2265, 32, NULL, '2020-11-06', 9, 0, 6, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2266, 32, NULL, '2020-11-20', 9, 0, 6, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2267, 32, NULL, '2020-12-04', 9, 0, 6, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2268, 32, NULL, '2020-12-18', 9, 0, 6, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2269, 32, NULL, '2021-01-01', 9, 0, 6, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2270, 32, NULL, '2021-01-15', 9, 0, 6, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2271, 32, NULL, '2021-01-29', 9, 0, 6, NULL, NULL, '2020-09-24 08:26:32', '2020-09-24 08:26:32', 4, 1);
INSERT INTO `course` VALUES (2272, 4, NULL, '2020-09-07', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2273, 4, NULL, '2020-09-14', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2274, 4, NULL, '2020-09-21', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2275, 4, NULL, '2020-09-28', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2276, 4, NULL, '2020-10-05', 5, 2, 2, NULL, 1601338867, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2277, 4, NULL, '2020-10-12', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2278, 4, NULL, '2020-10-19', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2279, 4, NULL, '2020-10-26', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2280, 4, NULL, '2020-11-02', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2281, 4, NULL, '2020-11-09', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2282, 4, NULL, '2020-11-16', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2283, 4, NULL, '2020-11-23', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2284, 4, NULL, '2020-11-30', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2285, 4, NULL, '2020-12-07', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2286, 4, NULL, '2020-12-14', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2287, 4, NULL, '2020-12-21', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2288, 4, NULL, '2020-12-28', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2289, 4, NULL, '2021-01-04', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2290, 4, NULL, '2021-01-11', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2291, 4, NULL, '2021-01-18', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2292, 4, NULL, '2021-01-25', 5, 2, 2, NULL, NULL, '2020-09-24 08:37:08', '2020-09-24 08:37:08', 4, 1);
INSERT INTO `course` VALUES (2293, 25, NULL, '2020-09-07', 5, 0, 1, NULL, 1600910433, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2294, 25, NULL, '2020-09-21', 5, 0, 1, NULL, 1600910433, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2295, 25, NULL, '2020-10-05', 5, 0, 1, NULL, 1600910433, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2296, 25, NULL, '2020-10-19', 5, 0, 1, NULL, 1600910433, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2297, 25, NULL, '2020-11-02', 5, 0, 1, NULL, 1600910433, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2298, 25, NULL, '2020-11-16', 5, 0, 1, NULL, 1600910433, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2299, 25, NULL, '2020-11-30', 5, 0, 1, NULL, 1600910433, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2300, 25, NULL, '2020-12-14', 5, 0, 1, NULL, 1600910433, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2301, 25, NULL, '2020-12-28', 5, 0, 1, NULL, 1600910433, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2302, 25, NULL, '2021-01-11', 5, 0, 1, NULL, 1600910286, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2303, 25, NULL, '2021-01-25', 5, 0, 1, NULL, 1600910286, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2304, 34, NULL, '2020-09-07', 9, 0, 1, NULL, 1600910474, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2305, 34, NULL, '2020-09-21', 9, 0, 1, NULL, 1600910474, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2306, 34, NULL, '2020-10-05', 9, 0, 1, NULL, 1600910474, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2307, 34, NULL, '2020-10-19', 9, 0, 1, NULL, 1600910474, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2308, 34, NULL, '2020-11-02', 9, 0, 1, NULL, 1600910474, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2309, 34, NULL, '2020-11-16', 9, 0, 1, NULL, 1600910474, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2310, 34, NULL, '2020-11-30', 9, 0, 1, NULL, 1600910474, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2311, 34, NULL, '2020-12-14', 9, 0, 1, NULL, 1600910474, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2312, 34, NULL, '2020-12-28', 9, 0, 1, NULL, 1600910474, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2313, 34, NULL, '2021-01-11', 9, 0, 1, NULL, 1600910474, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2314, 34, NULL, '2021-01-25', 9, 0, 1, NULL, 1600910474, '2020-09-24 08:38:46', '2020-09-24 08:38:46', 4, 1);
INSERT INTO `course` VALUES (2315, 30, NULL, '2020-09-07', 9, 0, 2, NULL, 1600910908, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2316, 30, NULL, '2020-09-21', 9, 0, 2, NULL, 1600910908, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2317, 30, NULL, '2020-10-05', 9, 0, 2, NULL, 1600910908, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2318, 30, NULL, '2020-10-19', 9, 0, 2, NULL, 1600910908, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2319, 30, NULL, '2020-11-02', 9, 0, 2, NULL, 1600910908, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2320, 30, NULL, '2020-11-16', 9, 0, 2, NULL, 1600910908, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2321, 30, NULL, '2020-11-30', 9, 0, 2, NULL, 1600910908, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2322, 30, NULL, '2020-12-14', 9, 0, 2, NULL, 1600910908, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2323, 30, NULL, '2020-12-28', 9, 0, 2, NULL, 1600910908, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2324, 30, NULL, '2021-01-11', 9, 0, 2, NULL, 1600910908, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2325, 30, NULL, '2021-01-25', 9, 0, 2, NULL, 1600910908, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2326, 27, NULL, '2020-09-07', 5, 0, 3, NULL, 1600910948, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2327, 27, NULL, '2020-09-21', 5, 0, 3, NULL, 1600910948, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2328, 27, NULL, '2020-10-05', 5, 0, 3, NULL, 1600910948, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2329, 27, NULL, '2020-10-19', 5, 0, 3, NULL, 1600910948, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2330, 27, NULL, '2020-11-02', 5, 0, 3, NULL, 1600910948, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2331, 27, NULL, '2020-11-16', 5, 0, 3, NULL, 1600910948, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2332, 27, NULL, '2020-11-30', 5, 0, 3, NULL, 1600910948, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2333, 27, NULL, '2020-12-14', 5, 0, 3, NULL, 1600910948, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2334, 27, NULL, '2020-12-28', 5, 0, 3, NULL, 1600910948, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2335, 27, NULL, '2021-01-11', 5, 0, 3, NULL, 1600910948, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2336, 27, NULL, '2021-01-25', 5, 0, 3, NULL, 1600910948, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2337, 35, NULL, '2020-09-07', 9, 0, 3, NULL, 1600910999, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2338, 35, NULL, '2020-09-21', 9, 0, 3, NULL, 1600910999, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2339, 35, NULL, '2020-10-05', 9, 0, 3, NULL, 1600910999, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2340, 35, NULL, '2020-10-19', 9, 0, 3, NULL, 1600910999, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2341, 35, NULL, '2020-11-02', 9, 0, 3, NULL, 1600910999, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2342, 35, NULL, '2020-11-16', 9, 0, 3, NULL, 1600910999, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2343, 35, NULL, '2020-11-30', 9, 0, 3, NULL, 1600910999, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2344, 35, NULL, '2020-12-14', 9, 0, 3, NULL, 1600910999, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2345, 35, NULL, '2020-12-28', 9, 0, 3, NULL, 1600910999, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2346, 35, NULL, '2021-01-11', 9, 0, 3, NULL, 1600910999, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2347, 35, NULL, '2021-01-25', 9, 0, 3, NULL, 1600910999, '2020-09-24 08:53:07', '2020-09-24 08:53:07', 4, 1);
INSERT INTO `course` VALUES (2348, 25, NULL, '2020-09-14', 5, 0, 1, NULL, NULL, '2020-09-24 09:31:22', '2020-09-24 09:31:22', 4, 1);
INSERT INTO `course` VALUES (2349, 25, NULL, '2020-09-28', 5, 0, 1, NULL, NULL, '2020-09-24 09:31:22', '2020-09-24 09:31:22', 4, 1);
INSERT INTO `course` VALUES (2350, 25, NULL, '2020-10-12', 5, 0, 1, NULL, NULL, '2020-09-24 09:31:22', '2020-09-24 09:31:22', 4, 1);
INSERT INTO `course` VALUES (2351, 25, NULL, '2020-10-26', 5, 0, 1, NULL, NULL, '2020-09-24 09:31:22', '2020-09-24 09:31:22', 4, 1);
INSERT INTO `course` VALUES (2352, 25, NULL, '2020-11-09', 5, 0, 1, NULL, NULL, '2020-09-24 09:31:22', '2020-09-24 09:31:22', 4, 1);
INSERT INTO `course` VALUES (2353, 25, NULL, '2020-11-23', 5, 0, 1, NULL, NULL, '2020-09-24 09:31:22', '2020-09-24 09:31:22', 4, 1);
INSERT INTO `course` VALUES (2354, 25, NULL, '2020-12-07', 5, 0, 1, NULL, NULL, '2020-09-24 09:31:22', '2020-09-24 09:31:22', 4, 1);
INSERT INTO `course` VALUES (2355, 25, NULL, '2020-12-21', 5, 0, 1, NULL, NULL, '2020-09-24 09:31:22', '2020-09-24 09:31:22', 4, 1);
INSERT INTO `course` VALUES (2356, 25, NULL, '2021-01-18', 5, 0, 1, NULL, NULL, '2020-09-24 09:31:22', '2020-09-24 09:31:22', 4, 1);
INSERT INTO `course` VALUES (2357, 34, NULL, '2020-09-14', 9, 0, 1, NULL, NULL, '2020-09-24 09:32:25', '2020-09-24 09:32:25', 4, 1);
INSERT INTO `course` VALUES (2358, 34, NULL, '2020-09-28', 9, 0, 1, NULL, NULL, '2020-09-24 09:32:25', '2020-09-24 09:32:25', 4, 1);
INSERT INTO `course` VALUES (2359, 34, NULL, '2020-10-12', 9, 0, 1, NULL, NULL, '2020-09-24 09:32:25', '2020-09-24 09:32:25', 4, 1);
INSERT INTO `course` VALUES (2360, 34, NULL, '2020-10-26', 9, 0, 1, NULL, NULL, '2020-09-24 09:32:25', '2020-09-24 09:32:25', 4, 1);
INSERT INTO `course` VALUES (2361, 34, NULL, '2020-11-09', 9, 0, 1, NULL, NULL, '2020-09-24 09:32:25', '2020-09-24 09:32:25', 4, 1);
INSERT INTO `course` VALUES (2362, 34, NULL, '2020-11-23', 9, 0, 1, NULL, NULL, '2020-09-24 09:32:25', '2020-09-24 09:32:25', 4, 1);
INSERT INTO `course` VALUES (2363, 34, NULL, '2020-12-07', 9, 0, 1, NULL, NULL, '2020-09-24 09:32:25', '2020-09-24 09:32:25', 4, 1);
INSERT INTO `course` VALUES (2364, 34, NULL, '2020-12-21', 9, 0, 1, NULL, NULL, '2020-09-24 09:32:25', '2020-09-24 09:32:25', 4, 1);
INSERT INTO `course` VALUES (2365, 34, NULL, '2021-01-18', 9, 0, 1, NULL, NULL, '2020-09-24 09:32:25', '2020-09-24 09:32:25', 4, 1);
INSERT INTO `course` VALUES (2366, 30, NULL, '2020-09-14', 9, 0, 2, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2367, 30, NULL, '2020-09-28', 9, 0, 2, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2368, 30, NULL, '2020-10-12', 9, 0, 2, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2369, 30, NULL, '2020-10-26', 9, 0, 2, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2370, 30, NULL, '2020-11-09', 9, 0, 2, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2371, 30, NULL, '2020-11-23', 9, 0, 2, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2372, 30, NULL, '2020-12-07', 9, 0, 2, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2373, 30, NULL, '2020-12-21', 9, 0, 2, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2374, 30, NULL, '2021-01-18', 9, 0, 2, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2375, 27, NULL, '2020-09-14', 5, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2376, 27, NULL, '2020-09-28', 5, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2377, 27, NULL, '2020-10-12', 5, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2378, 27, NULL, '2020-10-26', 5, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2379, 27, NULL, '2020-11-09', 5, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2380, 27, NULL, '2020-11-23', 5, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2381, 27, NULL, '2020-12-07', 5, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2382, 27, NULL, '2020-12-21', 5, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2383, 27, NULL, '2021-01-18', 5, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2384, 35, NULL, '2020-09-14', 9, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2385, 35, NULL, '2020-09-28', 9, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2386, 35, NULL, '2020-10-12', 9, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2387, 35, NULL, '2020-10-26', 9, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2388, 35, NULL, '2020-11-09', 9, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2389, 35, NULL, '2020-11-23', 9, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2390, 35, NULL, '2020-12-07', 9, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2391, 35, NULL, '2020-12-21', 9, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2392, 35, NULL, '2021-01-18', 9, 0, 3, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2393, 28, NULL, '2020-09-14', 5, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2394, 28, NULL, '2020-09-28', 5, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2395, 28, NULL, '2020-10-12', 5, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2396, 28, NULL, '2020-10-26', 5, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2397, 28, NULL, '2020-11-09', 5, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2398, 28, NULL, '2020-11-23', 5, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2399, 28, NULL, '2020-12-07', 5, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2400, 28, NULL, '2020-12-21', 5, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2401, 28, NULL, '2021-01-18', 5, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2402, 36, NULL, '2020-09-14', 9, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2403, 36, NULL, '2020-09-28', 9, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2404, 36, NULL, '2020-10-12', 9, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2405, 36, NULL, '2020-10-26', 9, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2406, 36, NULL, '2020-11-09', 9, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2407, 36, NULL, '2020-11-23', 9, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2408, 36, NULL, '2020-12-07', 9, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2409, 36, NULL, '2020-12-21', 9, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2410, 36, NULL, '2021-01-18', 9, 0, 4, NULL, NULL, '2020-09-24 09:37:05', '2020-09-24 09:37:05', 4, 1);
INSERT INTO `course` VALUES (2411, 29, NULL, '2020-09-14', 5, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2412, 29, NULL, '2020-09-28', 5, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2413, 29, NULL, '2020-10-12', 5, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2414, 29, NULL, '2020-10-26', 5, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2415, 29, NULL, '2020-11-09', 5, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2416, 29, NULL, '2020-11-23', 5, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2417, 29, NULL, '2020-12-07', 5, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2418, 29, NULL, '2020-12-21', 5, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2419, 29, NULL, '2021-01-18', 5, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2420, 37, NULL, '2020-09-14', 9, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2421, 37, NULL, '2020-09-28', 9, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2422, 37, NULL, '2020-10-12', 9, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2423, 37, NULL, '2020-10-26', 9, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2424, 37, NULL, '2020-11-09', 9, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2425, 37, NULL, '2020-11-23', 9, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2426, 37, NULL, '2020-12-07', 9, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2427, 37, NULL, '2020-12-21', 9, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2428, 37, NULL, '2021-01-18', 9, 0, 5, NULL, NULL, '2020-09-24 09:39:31', '2020-09-24 09:39:31', 4, 1);
INSERT INTO `course` VALUES (2429, 26, NULL, '2020-09-15', 5, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2430, 26, NULL, '2020-09-29', 5, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2431, 26, NULL, '2020-10-13', 5, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2432, 26, NULL, '2020-10-27', 5, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2433, 26, NULL, '2020-11-10', 5, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2434, 26, NULL, '2020-11-24', 5, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2435, 26, NULL, '2020-12-08', 5, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2436, 26, NULL, '2020-12-22', 5, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2437, 26, NULL, '2021-01-19', 5, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2438, 38, NULL, '2020-09-15', 9, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2439, 38, NULL, '2020-09-29', 9, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2440, 38, NULL, '2020-10-13', 9, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2441, 38, NULL, '2020-10-27', 9, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2442, 38, NULL, '2020-11-10', 9, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2443, 38, NULL, '2020-11-24', 9, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2444, 38, NULL, '2020-12-08', 9, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2445, 38, NULL, '2020-12-22', 9, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2446, 38, NULL, '2021-01-19', 9, 0, 1, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2447, 31, NULL, '2020-09-15', 5, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2448, 31, NULL, '2020-09-29', 5, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2449, 31, NULL, '2020-10-13', 5, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2450, 31, NULL, '2020-10-27', 5, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2451, 31, NULL, '2020-11-10', 5, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2452, 31, NULL, '2020-11-24', 5, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2453, 31, NULL, '2020-12-08', 5, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2454, 31, NULL, '2020-12-22', 5, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2455, 31, NULL, '2021-01-19', 5, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2456, 39, NULL, '2020-09-15', 9, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2457, 39, NULL, '2020-09-29', 9, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2458, 39, NULL, '2020-10-13', 9, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2459, 39, NULL, '2020-10-27', 9, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2460, 39, NULL, '2020-11-10', 9, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2461, 39, NULL, '2020-11-24', 9, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2462, 39, NULL, '2020-12-08', 9, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2463, 39, NULL, '2020-12-22', 9, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2464, 39, NULL, '2021-01-19', 9, 0, 2, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2465, 40, NULL, '2020-09-15', 9, 0, 3, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2466, 40, NULL, '2020-09-29', 9, 0, 3, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2467, 40, NULL, '2020-10-13', 9, 0, 3, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2468, 40, NULL, '2020-10-27', 9, 0, 3, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2469, 40, NULL, '2020-11-10', 9, 0, 3, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2470, 40, NULL, '2020-11-24', 9, 0, 3, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2471, 40, NULL, '2020-12-08', 9, 0, 3, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2472, 40, NULL, '2020-12-22', 9, 0, 3, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2473, 40, NULL, '2021-01-19', 9, 0, 3, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2474, 41, NULL, '2020-09-15', 9, 0, 4, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2475, 41, NULL, '2020-09-29', 9, 0, 4, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2476, 41, NULL, '2020-10-13', 9, 0, 4, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2477, 41, NULL, '2020-10-27', 9, 0, 4, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2478, 41, NULL, '2020-11-10', 9, 0, 4, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2479, 41, NULL, '2020-11-24', 9, 0, 4, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2480, 41, NULL, '2020-12-08', 9, 0, 4, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2481, 41, NULL, '2020-12-22', 9, 0, 4, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2482, 41, NULL, '2021-01-19', 9, 0, 4, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2483, 28, NULL, '2020-09-15', 5, 0, 5, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2484, 28, NULL, '2020-09-29', 5, 0, 5, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2485, 28, NULL, '2020-10-13', 5, 0, 5, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2486, 28, NULL, '2020-10-27', 5, 0, 5, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2487, 28, NULL, '2020-11-10', 5, 0, 5, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2488, 28, NULL, '2020-11-24', 5, 0, 5, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2489, 28, NULL, '2020-12-08', 5, 0, 5, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2490, 28, NULL, '2020-12-22', 5, 0, 5, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2491, 28, NULL, '2021-01-19', 5, 0, 5, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2492, 33, NULL, '2020-09-15', 5, 0, 7, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2493, 33, NULL, '2020-09-29', 5, 0, 7, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2494, 33, NULL, '2020-10-13', 5, 0, 7, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2495, 33, NULL, '2020-10-27', 5, 0, 7, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2496, 33, NULL, '2020-11-10', 5, 0, 7, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2497, 33, NULL, '2020-11-24', 5, 0, 7, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2498, 33, NULL, '2020-12-08', 5, 0, 7, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2499, 33, NULL, '2020-12-22', 5, 0, 7, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2500, 33, NULL, '2021-01-19', 5, 0, 7, NULL, NULL, '2020-09-24 10:06:43', '2020-09-24 10:06:43', 4, 1);
INSERT INTO `course` VALUES (2501, 25, NULL, '2020-09-16', 5, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2502, 25, NULL, '2020-09-30', 5, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2503, 25, NULL, '2020-10-14', 5, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2504, 25, NULL, '2020-10-28', 5, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2505, 25, NULL, '2020-11-11', 5, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2506, 25, NULL, '2020-11-25', 5, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2507, 25, NULL, '2020-12-09', 5, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2508, 25, NULL, '2020-12-23', 5, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2509, 25, NULL, '2021-01-20', 5, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2510, 34, NULL, '2020-09-16', 9, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2511, 34, NULL, '2020-09-30', 9, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2512, 34, NULL, '2020-10-14', 9, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2513, 34, NULL, '2020-10-28', 9, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2514, 34, NULL, '2020-11-11', 9, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2515, 34, NULL, '2020-11-25', 9, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2516, 34, NULL, '2020-12-09', 9, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2517, 34, NULL, '2020-12-23', 9, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2518, 34, NULL, '2021-01-20', 9, 0, 1, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2519, 26, NULL, '2020-09-16', 5, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2520, 26, NULL, '2020-09-30', 5, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2521, 26, NULL, '2020-10-14', 5, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2522, 26, NULL, '2020-10-28', 5, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2523, 26, NULL, '2020-11-11', 5, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2524, 26, NULL, '2020-11-25', 5, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2525, 26, NULL, '2020-12-09', 5, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2526, 26, NULL, '2020-12-23', 5, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2527, 26, NULL, '2021-01-20', 5, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2528, 35, NULL, '2020-09-16', 9, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2529, 35, NULL, '2020-09-30', 9, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2530, 35, NULL, '2020-10-14', 9, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2531, 35, NULL, '2020-10-28', 9, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2532, 35, NULL, '2020-11-11', 9, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2533, 35, NULL, '2020-11-25', 9, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2534, 35, NULL, '2020-12-09', 9, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2535, 35, NULL, '2020-12-23', 9, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2536, 35, NULL, '2021-01-20', 9, 0, 2, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2537, 27, NULL, '2020-09-16', 5, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2538, 27, NULL, '2020-09-30', 5, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2539, 27, NULL, '2020-10-14', 5, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2540, 27, NULL, '2020-10-28', 5, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2541, 27, NULL, '2020-11-11', 5, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2542, 27, NULL, '2020-11-25', 5, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2543, 27, NULL, '2020-12-09', 5, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2544, 27, NULL, '2020-12-23', 5, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2545, 27, NULL, '2021-01-20', 5, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2546, 36, NULL, '2020-09-16', 9, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2547, 36, NULL, '2020-09-30', 9, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2548, 36, NULL, '2020-10-14', 9, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2549, 36, NULL, '2020-10-28', 9, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2550, 36, NULL, '2020-11-11', 9, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2551, 36, NULL, '2020-11-25', 9, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2552, 36, NULL, '2020-12-09', 9, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2553, 36, NULL, '2020-12-23', 9, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2554, 36, NULL, '2021-01-20', 9, 0, 3, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2555, 32, NULL, '2020-09-16', 9, 0, 4, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2556, 32, NULL, '2020-09-30', 9, 0, 4, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2557, 32, NULL, '2020-10-14', 9, 0, 4, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2558, 32, NULL, '2020-10-28', 9, 0, 4, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2559, 32, NULL, '2020-11-11', 9, 0, 4, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2560, 32, NULL, '2020-11-25', 9, 0, 4, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2561, 32, NULL, '2020-12-09', 9, 0, 4, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2562, 32, NULL, '2020-12-23', 9, 0, 4, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2563, 32, NULL, '2021-01-20', 9, 0, 4, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2564, 29, NULL, '2020-09-16', 5, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2565, 29, NULL, '2020-09-30', 5, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2566, 29, NULL, '2020-10-14', 5, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2567, 29, NULL, '2020-10-28', 5, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2568, 29, NULL, '2020-11-11', 5, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2569, 29, NULL, '2020-11-25', 5, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2570, 29, NULL, '2020-12-09', 5, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2571, 29, NULL, '2020-12-23', 5, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2572, 29, NULL, '2021-01-20', 5, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2573, 37, NULL, '2020-09-16', 9, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2574, 37, NULL, '2020-09-30', 9, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2575, 37, NULL, '2020-10-14', 9, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2576, 37, NULL, '2020-10-28', 9, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2577, 37, NULL, '2020-11-11', 9, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2578, 37, NULL, '2020-11-25', 9, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2579, 37, NULL, '2020-12-09', 9, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2580, 37, NULL, '2020-12-23', 9, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2581, 37, NULL, '2021-01-20', 9, 0, 5, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2582, 30, NULL, '2020-09-16', 9, 0, 6, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2583, 30, NULL, '2020-09-30', 9, 0, 6, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2584, 30, NULL, '2020-10-14', 9, 0, 6, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2585, 30, NULL, '2020-10-28', 9, 0, 6, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2586, 30, NULL, '2020-11-11', 9, 0, 6, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2587, 30, NULL, '2020-11-25', 9, 0, 6, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2588, 30, NULL, '2020-12-09', 9, 0, 6, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2589, 30, NULL, '2020-12-23', 9, 0, 6, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2590, 30, NULL, '2021-01-20', 9, 0, 6, NULL, NULL, '2020-09-24 10:13:21', '2020-09-24 10:13:21', 4, 1);
INSERT INTO `course` VALUES (2591, 38, NULL, '2020-09-17', 9, 0, 1, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2592, 38, NULL, '2020-10-01', 9, 0, 1, NULL, 1601360651, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2593, 38, NULL, '2020-10-15', 9, 0, 1, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2594, 38, NULL, '2020-10-29', 9, 0, 1, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2595, 38, NULL, '2020-11-12', 9, 0, 1, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2596, 38, NULL, '2020-11-26', 9, 0, 1, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2597, 38, NULL, '2020-12-10', 9, 0, 1, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2598, 38, NULL, '2020-12-24', 9, 0, 1, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2599, 38, NULL, '2021-01-21', 9, 0, 1, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2600, 31, NULL, '2020-09-17', 5, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2601, 31, NULL, '2020-10-01', 5, 0, 3, NULL, 1601360651, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2602, 31, NULL, '2020-10-15', 5, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2603, 31, NULL, '2020-10-29', 5, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2604, 31, NULL, '2020-11-12', 5, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2605, 31, NULL, '2020-11-26', 5, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2606, 31, NULL, '2020-12-10', 5, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2607, 31, NULL, '2020-12-24', 5, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2608, 31, NULL, '2021-01-21', 5, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2609, 39, NULL, '2020-09-17', 9, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2610, 39, NULL, '2020-10-01', 9, 0, 3, NULL, 1601360651, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2611, 39, NULL, '2020-10-15', 9, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2612, 39, NULL, '2020-10-29', 9, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2613, 39, NULL, '2020-11-12', 9, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2614, 39, NULL, '2020-11-26', 9, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2615, 39, NULL, '2020-12-10', 9, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2616, 39, NULL, '2020-12-24', 9, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2617, 39, NULL, '2021-01-21', 9, 0, 3, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2618, 40, NULL, '2020-09-17', 9, 0, 4, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2619, 40, NULL, '2020-10-01', 9, 0, 4, NULL, 1601360651, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2620, 40, NULL, '2020-10-15', 9, 0, 4, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2621, 40, NULL, '2020-10-29', 9, 0, 4, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2622, 40, NULL, '2020-11-12', 9, 0, 4, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2623, 40, NULL, '2020-11-26', 9, 0, 4, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2624, 40, NULL, '2020-12-10', 9, 0, 4, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2625, 40, NULL, '2020-12-24', 9, 0, 4, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2626, 40, NULL, '2021-01-21', 9, 0, 4, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2627, 32, NULL, '2020-09-17', 9, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2628, 32, NULL, '2020-10-01', 9, 0, 5, NULL, 1601360651, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2629, 32, NULL, '2020-10-15', 9, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2630, 32, NULL, '2020-10-29', 9, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2631, 32, NULL, '2020-11-12', 9, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2632, 32, NULL, '2020-11-26', 9, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2633, 32, NULL, '2020-12-10', 9, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2634, 32, NULL, '2020-12-24', 9, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2635, 32, NULL, '2021-01-21', 9, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2636, 33, NULL, '2020-09-17', 5, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2637, 33, NULL, '2020-10-01', 5, 0, 5, NULL, 1601360651, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2638, 33, NULL, '2020-10-15', 5, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2639, 33, NULL, '2020-10-29', 5, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2640, 33, NULL, '2020-11-12', 5, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2641, 33, NULL, '2020-11-26', 5, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2642, 33, NULL, '2020-12-10', 5, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2643, 33, NULL, '2020-12-24', 5, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2644, 33, NULL, '2021-01-21', 5, 0, 5, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2645, 41, NULL, '2020-09-17', 9, 0, 6, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2646, 41, NULL, '2020-10-01', 9, 0, 6, NULL, 1601360652, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2647, 41, NULL, '2020-10-15', 9, 0, 6, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2648, 41, NULL, '2020-10-29', 9, 0, 6, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2649, 41, NULL, '2020-11-12', 9, 0, 6, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2650, 41, NULL, '2020-11-26', 9, 0, 6, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2651, 41, NULL, '2020-12-10', 9, 0, 6, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2652, 41, NULL, '2020-12-24', 9, 0, 6, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2653, 41, NULL, '2021-01-21', 9, 0, 6, NULL, NULL, '2020-09-24 10:19:21', '2020-09-24 10:19:21', 4, 1);
INSERT INTO `course` VALUES (2654, 3, NULL, '2020-09-09', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2655, 3, NULL, '2020-09-16', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2656, 3, NULL, '2020-09-23', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2657, 3, NULL, '2020-09-30', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2658, 3, NULL, '2020-10-07', 5, 2, 4, NULL, 1601338867, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2659, 3, NULL, '2020-10-14', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2660, 3, NULL, '2020-10-21', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2661, 3, NULL, '2020-10-28', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2662, 3, NULL, '2020-11-04', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2663, 3, NULL, '2020-11-11', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2664, 3, NULL, '2020-11-18', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2665, 3, NULL, '2020-11-25', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2666, 3, NULL, '2020-12-02', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2667, 3, NULL, '2020-12-09', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2668, 3, NULL, '2020-12-16', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2669, 3, NULL, '2020-12-23', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2670, 3, NULL, '2020-12-30', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2671, 3, NULL, '2021-01-06', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2672, 3, NULL, '2021-01-13', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2673, 3, NULL, '2021-01-20', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2674, 3, NULL, '2021-01-27', 5, 2, 4, NULL, NULL, '2020-09-24 10:24:47', '2020-09-24 10:24:47', 4, 1);
INSERT INTO `course` VALUES (2675, 41, NULL, '2020-10-01', 12, 0, 3, NULL, NULL, '2020-09-30 14:43:32', '2020-09-30 14:43:32', 10, 1);
INSERT INTO `course` VALUES (2676, 41, NULL, '2020-10-08', 12, 0, 3, NULL, NULL, '2020-09-30 14:43:32', '2020-09-30 14:43:32', 10, 1);
INSERT INTO `course` VALUES (2677, 41, NULL, '2020-10-15', 12, 0, 3, NULL, NULL, '2020-09-30 14:43:32', '2020-09-30 14:43:32', 10, 1);
INSERT INTO `course` VALUES (2678, 41, NULL, '2020-10-22', 12, 0, 3, NULL, NULL, '2020-09-30 14:43:32', '2020-09-30 14:43:32', 10, 1);
INSERT INTO `course` VALUES (2679, 41, NULL, '2020-10-29', 12, 0, 3, NULL, NULL, '2020-09-30 14:43:32', '2020-09-30 14:43:32', 10, 1);
INSERT INTO `course` VALUES (2680, 41, NULL, '2020-10-05', 1, 8, 1, NULL, NULL, '2020-10-05 14:43:52', '2020-10-05 14:43:52', 10, 1);
INSERT INTO `course` VALUES (2681, 41, NULL, '2020-10-13', 1, 2, 2, NULL, NULL, '2020-10-13 15:28:24', '2020-10-13 15:28:24', 10, 1);
INSERT INTO `course` VALUES (2682, 41, NULL, '2020-10-15', 1, 2, 2, NULL, NULL, '2020-10-13 15:28:24', '2020-10-13 15:28:24', 10, 1);
INSERT INTO `course` VALUES (2683, 39, NULL, '2020-10-14', 4, NULL, 3, 57, NULL, '2020-10-14 16:51:27', '2020-10-14 16:51:27', 10, 1);
INSERT INTO `course` VALUES (2684, 41, NULL, '2020-10-16', 1, NULL, 2, 59, NULL, '2020-10-16 13:33:12', '2020-10-16 13:33:12', 10, 1);
INSERT INTO `course` VALUES (2685, 41, NULL, '2020-10-16', 1, NULL, 1, 59, NULL, '2020-10-16 15:56:01', '2020-10-16 15:56:01', 10, 1);
INSERT INTO `course` VALUES (2686, 41, NULL, '2020-10-21', 1, 3, 3, NULL, NULL, '2020-10-17 09:01:52', '2020-10-17 09:01:52', 10, 1);
INSERT INTO `course` VALUES (2687, 41, NULL, '2020-10-23', 1, 3, 3, NULL, NULL, '2020-10-17 09:01:52', '2020-10-17 09:01:52', 10, 1);
INSERT INTO `course` VALUES (2688, 41, NULL, '2020-10-28', 1, 3, 3, NULL, NULL, '2020-10-17 09:01:52', '2020-10-17 09:01:52', 10, 1);
INSERT INTO `course` VALUES (2689, 41, NULL, '2020-10-30', 1, 3, 3, NULL, NULL, '2020-10-17 09:01:52', '2020-10-17 09:01:52', 10, 1);

-- ----------------------------
-- Table structure for course_times
-- ----------------------------
DROP TABLE IF EXISTS `course_times`;
CREATE TABLE `course_times`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `delete_time` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课次表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of course_times
-- ----------------------------
INSERT INTO `course_times` VALUES (1, '第一节', NULL);
INSERT INTO `course_times` VALUES (2, '第二节', NULL);
INSERT INTO `course_times` VALUES (3, '第三节', NULL);
INSERT INTO `course_times` VALUES (4, '第四节', NULL);
INSERT INTO `course_times` VALUES (5, '第五节', NULL);
INSERT INTO `course_times` VALUES (6, '第六节', NULL);
INSERT INTO `course_times` VALUES (7, '第七节', NULL);
INSERT INTO `course_times` VALUES (8, '第八节', NULL);

-- ----------------------------
-- Table structure for duty_schedule
-- ----------------------------
DROP TABLE IF EXISTS `duty_schedule`;
CREATE TABLE `duty_schedule`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `staff_id` int(11) NOT NULL DEFAULT 0,
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `plan_ids` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行计划id 多个用,隔开',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `staff_id`(`staff_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工值班编排' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of duty_schedule
-- ----------------------------
INSERT INTO `duty_schedule` VALUES (1, 1, 3, '1');
INSERT INTO `duty_schedule` VALUES (2, 2, 1, '1');
INSERT INTO `duty_schedule` VALUES (5, 4, NULL, '1');
INSERT INTO `duty_schedule` VALUES (6, 8, NULL, '1');
INSERT INTO `duty_schedule` VALUES (7, 5, NULL, '1');
INSERT INTO `duty_schedule` VALUES (8, 6, NULL, '1');
INSERT INTO `duty_schedule` VALUES (9, 7, NULL, '1');
INSERT INTO `duty_schedule` VALUES (10, 11, NULL, '1');
INSERT INTO `duty_schedule` VALUES (11, 12, NULL, '1');

-- ----------------------------
-- Table structure for duty_schedule_plan
-- ----------------------------
DROP TABLE IF EXISTS `duty_schedule_plan`;
CREATE TABLE `duty_schedule_plan`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '计划名',
  `start_am` time NULL DEFAULT NULL COMMENT '上午开始时间',
  `end_am` time NULL DEFAULT NULL COMMENT '上午结束时间',
  `start_pm` time NULL DEFAULT NULL COMMENT '下午开始时间',
  `end_pm` time NULL DEFAULT NULL COMMENT '下午结束时间',
  `weekdays` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '每周工作日 1- 7',
  `times` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作时间节点\r\n预约时间段设置 若为空表示自动按时间间隔计划 必须在上下午时间范围内',
  `duration` tinyint(4) NULL DEFAULT NULL COMMENT '每时间段时长(分钟)',
  `service_count` int(11) NULL DEFAULT 1 COMMENT '每时间段顾客数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '值班编排计划设置' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of duty_schedule_plan
-- ----------------------------
INSERT INTO `duty_schedule_plan` VALUES (1, '常规计划', '07:30:00', '11:30:00', '13:30:00', '17:30:00', '1,2,3,4,5', '', 40, 1);

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `delete_time` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (1, '2020级', NULL);
INSERT INTO `grade` VALUES (2, '2021级', NULL);

-- ----------------------------
-- Table structure for h5
-- ----------------------------
DROP TABLE IF EXISTS `h5`;
CREATE TABLE `h5`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `editor_id` int(11) NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `edit_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of h5
-- ----------------------------
INSERT INTO `h5` VALUES (1, '312312', NULL, '2020-10-14 16:54:27', '&lt;p&gt;&amp;lt;p&amp;gt;&amp;amp;lt;p&amp;amp;gt;1231231&amp;amp;lt;/p&amp;amp;gt;&amp;lt;/p&amp;gt;&lt;/p&gt;', '2020-10-20 17:26:00');

-- ----------------------------
-- Table structure for help
-- ----------------------------
DROP TABLE IF EXISTS `help`;
CREATE TABLE `help`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `key` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询key',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `caption`(`key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of help
-- ----------------------------
INSERT INTO `help` VALUES (1, 'wxpay', '开启微信支付', '若要开启微信支付，必须配置商户号和商户KEY。', NULL);
INSERT INTO `help` VALUES (2, 'bklogin', '欢迎使用', '本系统由XiaoYuan系统强力驱动。', NULL);
INSERT INTO `help` VALUES (3, 'productlist', '课时管理', '请注意添加完课程后是需要进行【课时编排】的哟，否则学员买了课程后是看不到课表记录的。', NULL);
INSERT INTO `help` VALUES (4, 'coursesign', '课时与签到', '报名成功且课时数有剩余的学员有签到资格', NULL);

-- ----------------------------
-- Table structure for knowledge
-- ----------------------------
DROP TABLE IF EXISTS `knowledge`;
CREATE TABLE `knowledge`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `edit_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of knowledge
-- ----------------------------
INSERT INTO `knowledge` VALUES (22, 'Marie,Mask-man,Monty', '2020-09-01 09:18:24', '2020-09-01 09:18:24');
INSERT INTO `knowledge` VALUES (23, 'WHAT’S YOUR NAME? I’M_______', '2020-09-01 09:18:24', '2020-09-01 09:18:24');
INSERT INTO `knowledge` VALUES (24, 'Numbers:one,two,three,four,five,six', '2020-09-01 10:43:01', '2020-09-01 10:43:01');
INSERT INTO `knowledge` VALUES (25, 'How old are you? I\'m...', '2020-09-01 10:43:01', '2020-09-01 10:43:01');
INSERT INTO `knowledge` VALUES (26, 'girl,boy', '2020-09-01 10:50:14', '2020-09-01 10:50:14');
INSERT INTO `knowledge` VALUES (27, 'Hello,I\'m...,I\'m...years old.I\'m a girl/boy.', '2020-09-01 10:50:14', '2020-09-01 10:50:14');
INSERT INTO `knowledge` VALUES (28, 'eraser, pencil, table', '2020-09-01 11:05:51', '2020-09-01 11:05:51');
INSERT INTO `knowledge` VALUES (29, 'What’s this? It’s a ____.', '2020-09-01 11:05:51', '2020-09-01 11:05:51');
INSERT INTO `knowledge` VALUES (30, 'bag, book, chair，open your books ，close your books', '2020-09-01 11:17:27', '2020-09-01 11:17:27');
INSERT INTO `knowledge` VALUES (31, 'what\'s this? it\'s a ...', '2020-09-01 11:17:27', '2020-09-01 11:17:27');
INSERT INTO `knowledge` VALUES (32, 'bag,book,chair', '2020-09-01 11:30:01', '2020-09-01 11:30:01');
INSERT INTO `knowledge` VALUES (33, 'stand up,sit down,listen,look,point', '2020-09-01 13:49:47', '2020-09-01 13:49:47');
INSERT INTO `knowledge` VALUES (34, 'point to the...', '2020-09-01 13:49:47', '2020-09-01 13:49:47');
INSERT INTO `knowledge` VALUES (35, 'Numbers:one,two,three,four,five,six,seven,eight,nine,ten', '2020-09-01 14:03:40', '2020-09-01 14:03:40');
INSERT INTO `knowledge` VALUES (36, 'excited/good/happy/sad', '2020-09-01 14:07:58', '2020-09-01 14:07:58');
INSERT INTO `knowledge` VALUES (37, 'How are you?    --I\'m excited/good/happy/sad.', '2020-09-01 14:07:58', '2020-09-01 14:07:58');
INSERT INTO `knowledge` VALUES (38, 'eraser,pencil,desk,table', '2020-09-01 14:17:31', '2020-09-01 14:17:31');
INSERT INTO `knowledge` VALUES (39, 'I point to the...', '2020-09-01 14:17:31', '2020-09-01 14:17:31');
INSERT INTO `knowledge` VALUES (40, 'Red, yellow, pink, green', '2020-09-01 14:30:33', '2020-09-01 14:30:33');
INSERT INTO `knowledge` VALUES (41, 'What’s colour is it? It’s ___.', '2020-09-01 14:30:33', '2020-09-01 14:30:33');
INSERT INTO `knowledge` VALUES (42, 'Orange,purple,blue,rainbow', '2020-09-01 14:38:25', '2020-09-01 14:38:25');
INSERT INTO `knowledge` VALUES (43, 'What’s colour is it? It’s...', '2020-09-01 14:38:25', '2020-09-01 14:38:25');
INSERT INTO `knowledge` VALUES (44, '1231', NULL, NULL);

-- ----------------------------
-- Table structure for knowledge_category
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_category`;
CREATE TABLE `knowledge_category`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of knowledge_category
-- ----------------------------
INSERT INTO `knowledge_category` VALUES (1, '词汇');
INSERT INTO `knowledge_category` VALUES (2, '语句');
INSERT INTO `knowledge_category` VALUES (3, '发音');

-- ----------------------------
-- Table structure for knowledge_category_rel
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_category_rel`;
CREATE TABLE `knowledge_category_rel`  (
  `knowledge_id` int(10) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of knowledge_category_rel
-- ----------------------------
INSERT INTO `knowledge_category_rel` VALUES (11, 1);
INSERT INTO `knowledge_category_rel` VALUES (11, 2);
INSERT INTO `knowledge_category_rel` VALUES (10, 3);
INSERT INTO `knowledge_category_rel` VALUES (7, 2);
INSERT INTO `knowledge_category_rel` VALUES (7, 1);
INSERT INTO `knowledge_category_rel` VALUES (9, 3);
INSERT INTO `knowledge_category_rel` VALUES (9, 2);
INSERT INTO `knowledge_category_rel` VALUES (8, 2);
INSERT INTO `knowledge_category_rel` VALUES (8, 3);
INSERT INTO `knowledge_category_rel` VALUES (6, 1);
INSERT INTO `knowledge_category_rel` VALUES (12, 1);
INSERT INTO `knowledge_category_rel` VALUES (13, 1);
INSERT INTO `knowledge_category_rel` VALUES (16, 2);
INSERT INTO `knowledge_category_rel` VALUES (17, 2);
INSERT INTO `knowledge_category_rel` VALUES (18, 1);
INSERT INTO `knowledge_category_rel` VALUES (19, 2);
INSERT INTO `knowledge_category_rel` VALUES (20, 3);
INSERT INTO `knowledge_category_rel` VALUES (21, 1);
INSERT INTO `knowledge_category_rel` VALUES (15, 2);
INSERT INTO `knowledge_category_rel` VALUES (14, 1);
INSERT INTO `knowledge_category_rel` VALUES (14, 2);
INSERT INTO `knowledge_category_rel` VALUES (22, 1);
INSERT INTO `knowledge_category_rel` VALUES (23, 2);
INSERT INTO `knowledge_category_rel` VALUES (24, 1);
INSERT INTO `knowledge_category_rel` VALUES (25, 2);
INSERT INTO `knowledge_category_rel` VALUES (26, 1);
INSERT INTO `knowledge_category_rel` VALUES (27, 2);
INSERT INTO `knowledge_category_rel` VALUES (28, 1);
INSERT INTO `knowledge_category_rel` VALUES (29, 2);
INSERT INTO `knowledge_category_rel` VALUES (30, 1);
INSERT INTO `knowledge_category_rel` VALUES (31, 2);
INSERT INTO `knowledge_category_rel` VALUES (32, 1);
INSERT INTO `knowledge_category_rel` VALUES (33, 1);
INSERT INTO `knowledge_category_rel` VALUES (34, 2);
INSERT INTO `knowledge_category_rel` VALUES (35, 1);
INSERT INTO `knowledge_category_rel` VALUES (36, 1);
INSERT INTO `knowledge_category_rel` VALUES (37, 2);
INSERT INTO `knowledge_category_rel` VALUES (38, 1);
INSERT INTO `knowledge_category_rel` VALUES (39, 2);
INSERT INTO `knowledge_category_rel` VALUES (40, 1);
INSERT INTO `knowledge_category_rel` VALUES (41, 2);
INSERT INTO `knowledge_category_rel` VALUES (42, 1);
INSERT INTO `knowledge_category_rel` VALUES (43, 2);
INSERT INTO `knowledge_category_rel` VALUES (44, 1);

-- ----------------------------
-- Table structure for knowledge_h5
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_h5`;
CREATE TABLE `knowledge_h5`  (
  `knowledge_id` int(11) NOT NULL,
  `h5_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of knowledge_h5
-- ----------------------------
INSERT INTO `knowledge_h5` VALUES (44, 0);
INSERT INTO `knowledge_h5` VALUES (39, 1);

-- ----------------------------
-- Table structure for knowledge_question
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_question`;
CREATE TABLE `knowledge_question`  (
  `knowledge_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of knowledge_question
-- ----------------------------
INSERT INTO `knowledge_question` VALUES (1, 3);
INSERT INTO `knowledge_question` VALUES (2, 3);
INSERT INTO `knowledge_question` VALUES (1, 4);
INSERT INTO `knowledge_question` VALUES (1, 5);
INSERT INTO `knowledge_question` VALUES (2, 6);
INSERT INTO `knowledge_question` VALUES (2, 7);
INSERT INTO `knowledge_question` VALUES (2, 8);
INSERT INTO `knowledge_question` VALUES (1, 9);
INSERT INTO `knowledge_question` VALUES (1, 10);
INSERT INTO `knowledge_question` VALUES (1, 11);
INSERT INTO `knowledge_question` VALUES (1, 12);
INSERT INTO `knowledge_question` VALUES (14, 16);
INSERT INTO `knowledge_question` VALUES (15, 17);
INSERT INTO `knowledge_question` VALUES (14, 18);
INSERT INTO `knowledge_question` VALUES (14, 19);
INSERT INTO `knowledge_question` VALUES (15, 29);
INSERT INTO `knowledge_question` VALUES (18, 33);
INSERT INTO `knowledge_question` VALUES (15, 29);
INSERT INTO `knowledge_question` VALUES (15, 35);
INSERT INTO `knowledge_question` VALUES (14, 34);
INSERT INTO `knowledge_question` VALUES (15, 31);
INSERT INTO `knowledge_question` VALUES (14, 30);
INSERT INTO `knowledge_question` VALUES (16, 32);
INSERT INTO `knowledge_question` VALUES (22, 40);
INSERT INTO `knowledge_question` VALUES (28, 40);
INSERT INTO `knowledge_question` VALUES (22, 42);
INSERT INTO `knowledge_question` VALUES (24, 42);

-- ----------------------------
-- Table structure for knowledge_section
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_section`;
CREATE TABLE `knowledge_section`  (
  `knowledge_id` int(11) NOT NULL,
  `section_id` int(11) NOT NULL,
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '1 words_imp 重点词汇\r\n2 sentence_imp 重点句型\r\n3 words_att 附加词汇\r\n4 sentence_att 附加句型\r\n5 \'pronunciation 发音',
  INDEX `section_id`(`section_id`, `type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of knowledge_section
-- ----------------------------
INSERT INTO `knowledge_section` VALUES (22, 10, 1);
INSERT INTO `knowledge_section` VALUES (23, 10, 2);
INSERT INTO `knowledge_section` VALUES (24, 11, 1);
INSERT INTO `knowledge_section` VALUES (25, 11, 2);
INSERT INTO `knowledge_section` VALUES (26, 12, 1);
INSERT INTO `knowledge_section` VALUES (27, 12, 2);
INSERT INTO `knowledge_section` VALUES (30, 14, 1);
INSERT INTO `knowledge_section` VALUES (31, 14, 2);
INSERT INTO `knowledge_section` VALUES (28, 15, 1);
INSERT INTO `knowledge_section` VALUES (29, 15, 2);
INSERT INTO `knowledge_section` VALUES (32, 16, 1);
INSERT INTO `knowledge_section` VALUES (31, 16, 2);
INSERT INTO `knowledge_section` VALUES (33, 17, 1);
INSERT INTO `knowledge_section` VALUES (34, 17, 2);
INSERT INTO `knowledge_section` VALUES (35, 18, 1);
INSERT INTO `knowledge_section` VALUES (25, 18, 2);
INSERT INTO `knowledge_section` VALUES (36, 20, 1);
INSERT INTO `knowledge_section` VALUES (37, 20, 2);
INSERT INTO `knowledge_section` VALUES (38, 22, 1);
INSERT INTO `knowledge_section` VALUES (39, 22, 2);
INSERT INTO `knowledge_section` VALUES (40, 23, 1);
INSERT INTO `knowledge_section` VALUES (41, 23, 2);
INSERT INTO `knowledge_section` VALUES (42, 24, 1);
INSERT INTO `knowledge_section` VALUES (43, 24, 2);

-- ----------------------------
-- Table structure for knowledge_test
-- ----------------------------
DROP TABLE IF EXISTS `knowledge_test`;
CREATE TABLE `knowledge_test`  (
  `knowledge_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of knowledge_test
-- ----------------------------
INSERT INTO `knowledge_test` VALUES (15, 15);

-- ----------------------------
-- Table structure for media
-- ----------------------------
DROP TABLE IF EXISTS `media`;
CREATE TABLE `media`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `clazz_id` int(11) NULL DEFAULT NULL,
  `student_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `video_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频url',
  `images` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片 多个用,分开',
  `add_time` datetime NULL DEFAULT NULL,
  `edit_time` datetime NULL DEFAULT NULL,
  `editor_id` int(11) NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `view_num` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of media
-- ----------------------------
INSERT INTO `media` VALUES (30, 4, '10003,10004', NULL, 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/f238eb2a5285890807291729210/HAMyacyUcF4A.m4v', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/f238eb2a5285890807291729210/5285890807291729211.jpg', '2020-09-08 11:20:39', '2020-09-08 11:36:14', 3, '很不错的一堂课2', 0);
INSERT INTO `media` VALUES (31, 4, '10003,10004', NULL, 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/f238eb2a5285890807291729210/HAMyacyUcF4A.m4v', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/f238eb2a5285890807291729210/5285890807291729211.jpg', '2020-09-08 11:20:39', '2020-09-08 11:36:14', 3, '很不错的一堂课2', 0);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `to_user_id` int(11) NULL DEFAULT NULL COMMENT '接收者的id列表',
  `from_user_id` int(11) NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `send_time` datetime NULL DEFAULT NULL,
  `type` tinyint(1) NULL DEFAULT 0 COMMENT '0系统消息 1个人消息',
  `attach_type` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '附件类型user case',
  `attach_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `type`(`type`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (47, 2639, NULL, '欢迎注册律师树', '您可以在律师树上发布案件和代理案件 —— 律师树愿做您的随身律师', '2020-07-14 18:04:28', 1, NULL, NULL);
INSERT INTO `message` VALUES (45, 2638, NULL, '欢迎注册律宝贝', '您可以在律宝贝上发布案件和代理案件 —— 律宝贝愿做您的随身律师', '2020-06-28 15:46:02', 1, NULL, NULL);
INSERT INTO `message` VALUES (46, 2636, 2632, 'Ryan给你发了一个消息', 'Ryan:sdf ', '2020-06-28 17:44:05', 1, NULL, NULL);

-- ----------------------------
-- Table structure for nav
-- ----------------------------
DROP TABLE IF EXISTS `nav`;
CREATE TABLE `nav`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NULL DEFAULT 0,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `position` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_blank` tinyint(1) NULL DEFAULT 0,
  `sort_num` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of nav
-- ----------------------------
INSERT INTO `nav` VALUES (15, 0, '关于我们', 'home', '', 0, 10);
INSERT INTO `nav` VALUES (16, 0, '线上课程', 'home', '/course.html', 0, 9);
INSERT INTO `nav` VALUES (17, 33, '出国考试', 'home', '/article/62.html', 0, 8);
INSERT INTO `nav` VALUES (18, 0, '往期学员', 'home', '/students.html', 1, 7);
INSERT INTO `nav` VALUES (19, 0, '新阳动态', 'home', '/news.html', 0, 5);
INSERT INTO `nav` VALUES (21, 19, '资料分享', 'home', '/news/21.html', 0, 10);
INSERT INTO `nav` VALUES (23, 19, '近期资讯', 'home', '/news/23.html', 0, 7);
INSERT INTO `nav` VALUES (24, 19, '知识胶囊', 'home', '/news/24.html', 0, 9);
INSERT INTO `nav` VALUES (26, 19, '口语实战', 'home', '/news/26.html', 0, 8);
INSERT INTO `nav` VALUES (27, 0, '辅助功能', 'home', '', 0, 6);
INSERT INTO `nav` VALUES (29, 27, '基础测试', 'home', 'http://wx.xinyangedu.com/exam.html', 0, 2);
INSERT INTO `nav` VALUES (31, 27, '自适应学习', 'home', 'https://xy.xiaosaas.com/org/in', 1, 0);
INSERT INTO `nav` VALUES (32, 27, '精听练习', 'home', 'http://www.xinyangedu.com/audios', 0, 3);
INSERT INTO `nav` VALUES (33, 0, '课程体系', 'home', '', 0, 8);
INSERT INTO `nav` VALUES (34, 33, '考研英语', 'home', '/article/73.html', 0, 0);
INSERT INTO `nav` VALUES (35, 33, '剑桥MSE', 'home', '/article/80.html', 0, 7);
INSERT INTO `nav` VALUES (36, 33, '课程体系', 'home', '/article/79.html', 0, 9);
INSERT INTO `nav` VALUES (37, 15, '教学团队', 'home', '/about.html', 0, 0);
INSERT INTO `nav` VALUES (38, 15, '发展历程', 'home', '/article/81.html', 0, 0);

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `edit_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '平台通知' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notification
-- ----------------------------
INSERT INTO `notification` VALUES (41, '律宝贝上线了', '律宝贝上线了', NULL, '2020-03-23 00:00:00', '2020-06-27 17:43:59');

-- ----------------------------
-- Table structure for offduty_type
-- ----------------------------
DROP TABLE IF EXISTS `offduty_type`;
CREATE TABLE `offduty_type`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of offduty_type
-- ----------------------------
INSERT INTO `offduty_type` VALUES (1, '事假');
INSERT INTO `offduty_type` VALUES (2, '病假');
INSERT INTO `offduty_type` VALUES (3, '串休');

-- ----------------------------
-- Table structure for old_student
-- ----------------------------
DROP TABLE IF EXISTS `old_student`;
CREATE TABLE `old_student`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `photo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `study_time` date NULL DEFAULT NULL,
  `sort_num` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2694 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of old_student
-- ----------------------------
INSERT INTO `old_student` VALUES (2693, '汪影', 'http://www.xinyangedu.com/up/attach/20200523/9a2ac01ecc33c1e0b7174d561693abf8.jpg', 'http://www.xinyangedu.com/up/attach/20200523/3220b27390140ef0af5d8ed0c8e17581.jpg', '\r\n\r\n我是医科大学的学生，雅思7分，在新阳学习了三个月。我觉得在新阳的学习对我帮助特别大，我先报名...', '2016-09-24', '', '&lt;p&gt;&lt;iframe id=&quot;cciframe_8221CF84A36CF3E39C33DC5901307461&quot; src=&quot;https://p.bokecc.com/playhtml.bo?vid=8221CF84A36CF3E39C33DC5901307461&amp;amp;siteid=7CD260676E910AF8&amp;amp;autoStart=false&amp;amp;playerid=70F1A9646F852BC9&amp;amp;playertype=1&quot; width=&quot;100%&quot; height=&quot;490&quot; frameborder=&quot;0&quot;&gt;&lt;/iframe&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;img style=&quot;width: 100%;&quot; src=&quot;../../up/attach/20200523/d046346feb2fe03640537fb6a63e3b2b.jpg&quot; alt=&quot;&quot; /&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;span style=&quot;font-size: 14pt;&quot;&gt;我是医科大学的学生，雅思7分，在新阳学习了三个月。我觉得在新阳的学习对我帮助特别大，我先报名的雅思考试然后开始的雅思学习。&lt;/span&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;span style=&quot;font-size: 14pt;&quot;&gt;备考的材料主要是使用的剑桥真题，听力方面主要是通过美剧来提升的，口语老师是特别懂得我们思维，不仅能教我们发音等，还从口语的思维逻辑方面给我们指导。我练口语一般是晚上7-9点在咖啡厅和小伙伴练，需要克服其他人异样的眼光，后来就慢慢适应了在别人面前用英语讲话。&lt;/span&gt;&lt;/p&gt;\r\n&lt;p&gt;&lt;span style=&quot;font-size: 14pt;&quot;&gt;写作要用作文纸写，这样才能比较直观的看到自己写了多少行多少字。&lt;/span&gt;&lt;/p&gt;');

-- ----------------------------
-- Table structure for payout
-- ----------------------------
DROP TABLE IF EXISTS `payout`;
CREATE TABLE `payout`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `staff_id` int(11) NOT NULL,
  `type_id` int(11) NULL DEFAULT NULL,
  `payee` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `payee_account` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `money` decimal(11, 2) NOT NULL DEFAULT 0.00 COMMENT '金额',
  `add_time` datetime NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '-1驳回 1 已批准 2已支付 0 申请中',
  `reason` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `delete_time` int(11) NULL DEFAULT NULL,
  `verifier_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人',
  `verify_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `verify_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核说明',
  `paid_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `paid_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `paid_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `images` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `staff_id`(`staff_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '请款' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of payout
-- ----------------------------
INSERT INTO `payout` VALUES (17, 11, 3, '刘红', '6222021607023377691', 318.50, '2020-09-17 08:56:52', 1, '教学使用的奖章、流动红旗、塑封膜、颜料，外教上课使用的文件盒', NULL, 'hanxiaoyan', '2020-09-19 17:15:37', '', NULL, NULL, NULL, '/up/attach/20200917/49db83833be4093251d1f4e9392f85db.jpg,/up/attach/20200917/4f9e09a62bc149746729d857c85c938b.jpg,/up/attach/20200917/773e31eba0e79cd55949cb847f741738.jpg,/up/attach/20200917/3dfa9eeef5c11a75fd64fe2ab49c602d.jpg,/up/attach/20200917/e84c02f1b710f0f7e553484c42dc5d0b.jpg,/up/attach/20200917/725f5b0ed035b250c934df5d79abd843.jpg');
INSERT INTO `payout` VALUES (18, 11, 3, '刘红', '6222021607023377691', 55.37, '2020-09-17 10:14:41', 0, '教学用U盘，教学手工针线', 1600332189, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200917/c5d0e5f7faea21c4057032ac90d3a7da.jpg,/up/attach/20200917/9f02d909efcb5392580284b8ed77998e.jpg,/up/attach/20200917/2d5b5333259bded50bb9d25b049d3654.jpg');
INSERT INTO `payout` VALUES (20, 4, 1, '杨树军', '微信', 3500.00, '2020-09-27 08:41:31', 0, '电脑原价5395，折价3500', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/7fc11ca6a631b9c758ea4776f21677fc.png');
INSERT INTO `payout` VALUES (21, 4, 1, '杨树军', '微信', 209.00, '2020-09-27 08:45:52', 0, '电脑主机增加内存', 1601174732, NULL, NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `payout` VALUES (22, 4, 1, '杨树军', '微信', 209.00, '2020-09-27 08:46:42', 0, '电脑主机增加内存', 1601167736, NULL, NULL, NULL, NULL, NULL, NULL, '');
INSERT INTO `payout` VALUES (23, 4, 1, '杨树军', '微信', 209.00, '2020-09-27 10:48:23', 0, '给电脑加内存', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/d11acfa91b96cdc7e08945060ab35ee5.png');
INSERT INTO `payout` VALUES (24, 4, 1, '杨树军', '微信', 60.00, '2020-09-27 10:58:24', 0, '台式机用的音箱，原价89，折价60', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/277240578d2d7018d76acccb16668df8.png');
INSERT INTO `payout` VALUES (25, 4, 1, '杨树军', '微信', 450.00, '2020-09-27 10:59:10', 0, '台式机用的显示器，原价649，折价450', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/a89d17ccba32445a0fb656a51ff2d989.png');
INSERT INTO `payout` VALUES (26, 4, 1, '杨树军', '微信', 6000.00, '2020-09-27 11:00:29', 0, '录课用的索尼微单相机，原价7399，折价6000', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/ccb74b5fc0399afa1f520501fab6fe88.png');
INSERT INTO `payout` VALUES (27, 4, 1, '杨树军', '微信', 2500.00, '2020-09-27 11:01:57', 0, '录课用的相机拾音麦克风，原价2849，折价2500', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/44598f7d0a102989d66e7bcb20139684.png');
INSERT INTO `payout` VALUES (28, 4, 1, '杨树军', '微信', 30.00, '2020-09-27 11:05:49', 0, '相机读卡器，原价49.9，折价30', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/bb2239b1c59b5967ca6716221515305f.png');
INSERT INTO `payout` VALUES (29, 4, 1, '杨树军', '微信', 350.00, '2020-09-27 11:09:01', 0, '64GB索尼相机专用高速SD卡，原价469，折价350', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/7777d2bc6bc824013f8ddc978f6d4e86.png');
INSERT INTO `payout` VALUES (30, 4, 1, '杨树军', '微信', 400.00, '2020-09-27 11:09:56', 0, '课堂录视频用手持云台，原价699，折价400', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/97e939c2fad9bbac4b28edb408111b40.jpg');
INSERT INTO `payout` VALUES (31, 4, 1, '杨树军', '微信', 200.00, '2020-09-27 11:10:52', 0, '三脚架，原价278，折价200', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/a44f6e6abab48454a19208ac95df832d.png');
INSERT INTO `payout` VALUES (32, 4, 1, '杨树军', '微信', 150.00, '2020-09-27 11:11:51', 0, '麦克风充电电池，原价229，折价150', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/f4d60b68e196fdf83148e3b3aae59bff.png');
INSERT INTO `payout` VALUES (33, 4, 1, '杨树军', '微信', 100.00, '2020-09-27 11:13:10', 0, '相机充电电池和充电器，原价134，折价100', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/e914ce990cc037d5263e5dfcc9442db6.png');
INSERT INTO `payout` VALUES (34, 4, 3, '杨树军', '微信', 780.00, '2020-09-27 11:22:07', 0, '教材 Kid\'s Box 4/5各3套', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/bbd3f53573a27aae381b15cdbd4d813a.png,/up/attach/20200927/5c3d399fc5e015048dc1c25788ae6d57.png');
INSERT INTO `payout` VALUES (35, 4, 3, '杨树军', '微信', 1290.00, '2020-09-27 11:22:58', 0, '教材 Kid\'s Box 0/1/2/3/6各3套', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/224df57e06b18ebb9ff4f4550cbf6cb0.png,/up/attach/20200927/c178de757f8b97bd04251939b0f786af.png');
INSERT INTO `payout` VALUES (36, 4, 1, '杨树军', '微信', 284.00, '2020-09-27 11:27:23', 0, '塑封机', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/71c17e675ac56aa2260cea1d32e1cfd3.png');
INSERT INTO `payout` VALUES (37, 4, 2, '杨树军', '微信', 729.00, '2020-09-27 11:28:20', 0, '7月23日飞机票往返', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/7ada7dfe64827e917d91a39ece992016.png');
INSERT INTO `payout` VALUES (38, 4, 2, '杨树军', '微信', 100.72, '2020-09-27 11:29:04', 0, '7月26日后寨-机场', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/4c0898cb60d91321203de4a71a107cb8.png');
INSERT INTO `payout` VALUES (39, 4, 2, '杨树军', '微信', 109.38, '2020-09-27 11:34:00', 0, '7月28日核酸检测', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/a2782b102ea3916e0cde303a41ae1957.jpg');
INSERT INTO `payout` VALUES (40, 4, 2, '杨树军', '微信', 102.89, '2020-09-27 11:34:38', 0, '8月12日核酸检测', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/e3e9d07ff48c29d5ab824d45f8c47bd0.jpg');
INSERT INTO `payout` VALUES (41, 4, 2, '杨树军', '微信', 910.00, '2020-09-27 11:41:39', 0, '8月14日船票', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/81d2dca6df50248f00e040e3f60b9330.jpg');
INSERT INTO `payout` VALUES (42, 4, 2, '杨树军', '微信', 105.24, '2020-09-27 11:42:33', 0, '8月15日高速费', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/9e709b1c4de04cb5c4a600aeb840fc66.png');
INSERT INTO `payout` VALUES (43, 4, 2, '杨树军', '微信', 260.95, '2020-09-27 11:50:00', 0, '8月27日去曲阜高速费', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/53b3c01e20b0690547482856a5842838.jpeg');
INSERT INTO `payout` VALUES (44, 4, 2, '杨树军', '微信', 515.00, '2020-09-27 11:51:54', 0, '8月27日去曲阜加油', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/c1e17d4af06ea7c6f5eebd59b2f63dca.png,/up/attach/20200927/a118426d5d939ddf9ea4b88b02604cfe.png');
INSERT INTO `payout` VALUES (45, 4, 2, '杨树军', '微信', 531.22, '2020-09-27 11:53:24', 0, '9月9日回大连加油', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/5644bf85d2fe0a6e2388df3b17e14b5d.png,/up/attach/20200927/d6df9b43f77e41464eeb97dd76618508.png');
INSERT INTO `payout` VALUES (46, 4, 2, '杨树军', '微信', 487.54, '2020-09-27 11:54:54', 0, '9月9日回大连高速费', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/80b38da100771d1276918571e9357bae.png');
INSERT INTO `payout` VALUES (47, 4, 2, '杨树军', '微信', 480.00, '2020-09-27 11:56:42', 0, '9月20日回寿光加油', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/aca7ba56826ea5065367a7a7f5482651.png,/up/attach/20200927/74a2003bdf7838ca6294e34c79b4ebf2.png');
INSERT INTO `payout` VALUES (48, 4, 2, '杨树军', '微信', 522.00, '2020-09-27 11:58:03', 0, '9月20日回寿光高速费', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/240afb836d19c0b21e62d20364210c10.png,/up/attach/20200927/fc83369ba8d392528c31727b2036a7fd.png');
INSERT INTO `payout` VALUES (49, 4, 1, '杨树军', '微信', 300.00, '2020-09-27 12:01:09', 0, '8月5日微信公众号认证', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/334ebe9a51c3e8ab678f167b7b776139.png');
INSERT INTO `payout` VALUES (50, 4, 8, '杨树军', '微信', 10000.00, '2020-09-27 12:02:51', 2, 'Monty中介费，共18000，已支付10000', NULL, 'collin', '2020-09-27 12:03:04', '已支付', '2020-09-27 12:03:11', 'collin', '已支付', '/up/attach/20200927/7cdb1dd222aaaec4f5594f22301c57b2.png,/up/attach/20200927/07bd13001b4941721c9b967e9f5901d8.png,/up/attach/20200927/2e855cfe26452113f9cde0262f457084.png');
INSERT INTO `payout` VALUES (51, 4, 1, '杨树军', '微信', 1387.34, '2020-09-27 12:09:18', 0, '办公用品，两次预支1000+500，剩余81退回，一件缺货退回31.66', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '/up/attach/20200927/e17b4ffbce6f192af5e0f0357ced3ad9.png,/up/attach/20200927/6bc9b8fd281b3f84994e8b1f472fe3cc.png,/up/attach/20200927/999f68adcdcffa54ead9881295ae86a4.png,/up/attach/20200927/b49abcf369fb82eb97400ba0841e0735.png');
INSERT INTO `payout` VALUES (52, 4, 1, '杨树军', '微信', 89.00, '2020-09-27 12:11:21', 1, '8月17日购买插排/笔记本等，后来插排和线退货120', NULL, 'cui', '2020-10-18 17:09:23', '', NULL, NULL, NULL, '/up/attach/20200927/1cc64bf072780533f3c233e3302f0fdf.png,/up/attach/20200927/123a740e61b642c5faaa2a8ff0ca4c31.png,/up/attach/20200927/f7904543f75d75aed1cf4039a10e2628.png');
INSERT INTO `payout` VALUES (53, 4, 1, '杨树军', '微信', 10000.00, '2020-09-27 12:12:25', -1, '9月11日系统开发第一笔费用10000，差5000', NULL, 'cui', '2020-10-10 20:13:25', '按收费神帝返水', NULL, NULL, NULL, '/up/attach/20200927/b6b86b730467acbfff1efa4018835b5d.png');
INSERT INTO `payout` VALUES (54, 4, 8, '杨树军', '微信', 259.00, '2020-09-27 12:13:17', 1, '外教租房买简易衣柜', NULL, 'cui', '2020-10-03 18:22:30', '111', NULL, NULL, NULL, '/up/attach/20200927/72ed518010970525070fe9c7848f2bea.png');
INSERT INTO `payout` VALUES (55, 4, 8, '杨树军', '微信', 1000.00, '2020-09-27 12:17:50', 2, '9月17日预支Monty工资', NULL, 'cui', '2020-09-30 14:52:45', '', '2020-10-03 18:22:24', 'cui', '23222', '/up/attach/20200927/9158f09a5947eefa24b46a25ecb0950c.jpeg');

-- ----------------------------
-- Table structure for payout_type
-- ----------------------------
DROP TABLE IF EXISTS `payout_type`;
CREATE TABLE `payout_type`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of payout_type
-- ----------------------------
INSERT INTO `payout_type` VALUES (1, '办公用品');
INSERT INTO `payout_type` VALUES (2, '交通费');
INSERT INTO `payout_type` VALUES (3, '教学物资');
INSERT INTO `payout_type` VALUES (5, '固定资产');
INSERT INTO `payout_type` VALUES (6, '社交活动');
INSERT INTO `payout_type` VALUES (7, '职员工资');
INSERT INTO `payout_type` VALUES (8, '外教工资');

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `body` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '1 选择 2 填空 3 判断 4 简答',
  `group_id` int(11) NULL DEFAULT NULL,
  `category_id` int(11) NULL DEFAULT NULL,
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '正确答案',
  `explain` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '解析',
  `editor_id` int(11) NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `edit_time` datetime NULL DEFAULT NULL,
  `sort_num` int(10) NULL DEFAULT NULL COMMENT '在题组里的顺序',
  `delete_time` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (36, '<p><span style=\"font-family: 宋体; font-size: 14px; text-indent: 28px;\">Andrew Heidrich now visits websites that discuss online gaming addiction to ______</span></p>', 2, 107, 0, 'HEIDRICH', '', 3, '2020-08-23 20:02:57', '2020-09-01 11:10:04', 2, NULL);
INSERT INTO `question` VALUES (40, '<p>听音在正确选项下面打&radic;</p>', 1, 119, 0, 'A', '', 10, '2020-09-01 09:37:44', '2020-10-08 23:30:37', 0, NULL);
INSERT INTO `question` VALUES (41, '<p>Directions: In this section, you will hear three news reports. At the end of each news report, you will hear two or three questions. Both the news report and the questions will be spoken only once. After you hear a question, you must choose the best answer from the four choices marked A), B), C) and D). Then mark the corresponding letter on Answer Sheet 1 with a single line through the centre.</p>\r\n<p>Questions 1 and 2 will be based on the following news item.</p>', 1, 117, 0, 'A', '', 10, '2020-10-05 14:42:39', '2020-10-08 23:27:51', 0, NULL);
INSERT INTO `question` VALUES (42, '<p>123123131</p>', 1, 121, 2, '112', '<p>12313</p>', 10, '2020-10-14 16:52:52', '2020-10-14 16:52:52', 0, NULL);

-- ----------------------------
-- Table structure for question_category
-- ----------------------------
DROP TABLE IF EXISTS `question_category`;
CREATE TABLE `question_category`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of question_category
-- ----------------------------
INSERT INTO `question_category` VALUES (2, '词汇测试2', 0);
INSERT INTO `question_category` VALUES (3, '语法测试小测试', 1);
INSERT INTO `question_category` VALUES (4, '334324', 2);

-- ----------------------------
-- Table structure for question_group
-- ----------------------------
DROP TABLE IF EXISTS `question_group`;
CREATE TABLE `question_group`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `body` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `editor_id` int(11) NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `edit_time` datetime NULL DEFAULT NULL,
  `delete_time` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 125 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of question_group
-- ----------------------------
INSERT INTO `question_group` VALUES (107, '', 3, '2020-09-01 11:10:04', '2020-09-01 11:10:04', NULL);
INSERT INTO `question_group` VALUES (117, '<p><span style=\"color: #333333; font-family: 宋体;\"><span style=\"font-size: 14px;\">Section A</span></span></p>', 10, '2020-10-05 14:42:10', '2020-10-05 14:42:10', NULL);
INSERT INTO `question_group` VALUES (119, '', 10, '2020-10-08 23:30:37', '2020-10-08 23:30:37', NULL);
INSERT INTO `question_group` VALUES (121, '<p>1231231</p>', 10, '2020-10-14 16:52:20', '2020-10-14 16:52:20', NULL);
INSERT INTO `question_group` VALUES (124, '', 10, '2020-10-20 17:22:44', '2020-10-20 17:22:44', NULL);

-- ----------------------------
-- Table structure for question_option
-- ----------------------------
DROP TABLE IF EXISTS `question_option`;
CREATE TABLE `question_option`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NULL DEFAULT NULL,
  `body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort_num` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 198 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of question_option
-- ----------------------------
INSERT INTO `question_option` VALUES (3, 3, '&lt;p&gt;太阳是圆的&lt;/p&gt;', '0');
INSERT INTO `question_option` VALUES (4, 3, '&lt;p&gt;马云是我弟&lt;/p&gt;', '1');
INSERT INTO `question_option` VALUES (5, 3, '&lt;p&gt;我是有钱人&lt;/p&gt;', '2');
INSERT INTO `question_option` VALUES (6, 3, '&lt;p&gt;一天是24小时&lt;/p&gt;', '3');
INSERT INTO `question_option` VALUES (7, 4, '', '0');
INSERT INTO `question_option` VALUES (15, 5, '&lt;p&gt;232323&lt;/p&gt;', '0');
INSERT INTO `question_option` VALUES (16, 6, '&lt;p&gt;士大夫&lt;/p&gt;', '0');
INSERT INTO `question_option` VALUES (17, 6, '&lt;p&gt;2323&lt;/p&gt;', '1');
INSERT INTO `question_option` VALUES (18, 7, '', '0');
INSERT INTO `question_option` VALUES (19, 8, '&lt;p&gt;1223&lt;/p&gt;', '0');
INSERT INTO `question_option` VALUES (20, 9, '&lt;p&gt;323&lt;/p&gt;', '0');
INSERT INTO `question_option` VALUES (24, 10, '<p>sdf</p>', '0');
INSERT INTO `question_option` VALUES (25, 11, '', '0');
INSERT INTO `question_option` VALUES (26, 12, '', '0');
INSERT INTO `question_option` VALUES (27, 13, '', '0');
INSERT INTO `question_option` VALUES (28, 14, '', '0');
INSERT INTO `question_option` VALUES (29, 15, '', '0');
INSERT INTO `question_option` VALUES (30, 16, '<p>这件事</p>', '0');
INSERT INTO `question_option` VALUES (31, 16, '<p>哪件事</p>', '1');
INSERT INTO `question_option` VALUES (32, 16, '<p>那件事</p>', '2');
INSERT INTO `question_option` VALUES (33, 16, '<p>到底是哪件事</p>', '3');
INSERT INTO `question_option` VALUES (34, 17, '', '0');
INSERT INTO `question_option` VALUES (35, 18, '<p>2323</p>', '0');
INSERT INTO `question_option` VALUES (36, 18, '<p>2323</p>', '1');
INSERT INTO `question_option` VALUES (37, 19, '', '0');
INSERT INTO `question_option` VALUES (38, 20, '', '0');
INSERT INTO `question_option` VALUES (39, 21, '', '0');
INSERT INTO `question_option` VALUES (40, 22, '', '0');
INSERT INTO `question_option` VALUES (41, 23, '', '0');
INSERT INTO `question_option` VALUES (42, 24, '', '0');
INSERT INTO `question_option` VALUES (43, 25, '', '0');
INSERT INTO `question_option` VALUES (44, 26, '', '0');
INSERT INTO `question_option` VALUES (45, 27, '', '0');
INSERT INTO `question_option` VALUES (46, 28, '<p>请问</p>', '0');
INSERT INTO `question_option` VALUES (47, 29, '', '0');
INSERT INTO `question_option` VALUES (54, 33, '', '0');
INSERT INTO `question_option` VALUES (121, 36, '', '0');
INSERT INTO `question_option` VALUES (124, 39, '', '0');
INSERT INTO `question_option` VALUES (125, 37, '', '0');
INSERT INTO `question_option` VALUES (150, 31, '<p>Her daughter\'s repeated complaints.</p>', 'A');
INSERT INTO `question_option` VALUES (151, 31, '<p>Fatigue resulting from lack of sleep.</p>', 'B');
INSERT INTO `question_option` VALUES (152, 31, '<p>The poorly managed state of her house.</p>', 'C');
INSERT INTO `question_option` VALUES (153, 31, '<p>The high financial costs adding up.</p>', 'D');
INSERT INTO `question_option` VALUES (154, 30, '<p>People should be warned of its harmful consequences.</p>', 'A');
INSERT INTO `question_option` VALUES (155, 30, '<p>It has become virtually inevitable.</p>', 'B');
INSERT INTO `question_option` VALUES (156, 30, '<p>It has been somewhat exaggerated.</p>', 'C');
INSERT INTO `question_option` VALUES (157, 30, '<p>People haven\'t yet reached agreement on its definition.</p>', 'D');
INSERT INTO `question_option` VALUES (158, 32, '<p>try to improve the Internet environment</p>', 'A');
INSERT INTO `question_option` VALUES (159, 32, '<p>become aware of its serious consequences</p>', 'B');
INSERT INTO `question_option` VALUES (160, 32, '<p>can reach a consensus on its definition</p>', 'C');
INSERT INTO `question_option` VALUES (161, 32, '<p>become aware of its serious consequences</p>', 'D');
INSERT INTO `question_option` VALUES (172, 35, '<p>It allows him to make a lot of friends.</p>', 'A');
INSERT INTO `question_option` VALUES (173, 35, '<p>It requires him to work long hours.</p>', 'B');
INSERT INTO `question_option` VALUES (174, 35, '<p>It enables him to apply theory to practice.</p>', 'C');
INSERT INTO `question_option` VALUES (175, 35, '<p>It helps him understand people better.</p>', 'D');
INSERT INTO `question_option` VALUES (176, 34, '<p>it seriously affected family relationships</p>', 'A');
INSERT INTO `question_option` VALUES (177, 34, '<p>one visited porn websites frequently</p>', 'B');
INSERT INTO `question_option` VALUES (178, 34, '<p>too much time was spent in chat rooms</p>', 'C');
INSERT INTO `question_option` VALUES (179, 34, '<p>people got involved in online gambling</p>', 'D');
INSERT INTO `question_option` VALUES (184, 41, '<p>Christmas-time attacks made by Somali rebels.</p>', 'A');
INSERT INTO `question_option` VALUES (185, 41, '<p>An explosion at a bus station in central Nairobi.</p>', 'B');
INSERT INTO `question_option` VALUES (186, 41, '<p>The killing of more than 70 Ugandans in Kampala.</p>', 'C');
INSERT INTO `question_option` VALUES (187, 41, '<p>Blasts set off by a Somali group in Uganda&rsquo;s capital.</p>', 'D');
INSERT INTO `question_option` VALUES (192, 40, '<p>accumulate</p>', 'A');
INSERT INTO `question_option` VALUES (193, 40, '<p>circling</p>', 'B');
INSERT INTO `question_option` VALUES (194, 40, '<p>communities</p>', 'C');
INSERT INTO `question_option` VALUES (195, 40, '<p>competition</p>', 'D');
INSERT INTO `question_option` VALUES (196, 42, '<p>123</p>', 'A');
INSERT INTO `question_option` VALUES (197, 42, '<p>121</p>', 'B');

-- ----------------------------
-- Table structure for region
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region`  (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` int(6) NULL DEFAULT NULL COMMENT '父id',
  `short_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简称',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `merger_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '全称',
  `level` tinyint(2) NULL DEFAULT NULL COMMENT '层级 0 1 2 省市区县',
  `pinyin` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拼音',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '长途区号',
  `zip_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `first` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '首字母',
  `lng` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度',
  `lat` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pinyin`(`pinyin`) USING BTREE,
  INDEX `pid`(`pid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3750 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of region
-- ----------------------------
INSERT INTO `region` VALUES (1393, 1387, '城阳', '城阳区', '山东省,青岛市,城阳区', 3, 'chengyang', '0532', '266041', 'C', '120.39621', '36.30735');
INSERT INTO `region` VALUES (1394, 1387, '胶州', '胶州市', '山东省,青岛市,胶州市', 3, 'jiaozhou', '0532', '266300', 'J', '120.0335', '36.26442');
INSERT INTO `region` VALUES (1395, 1387, '即墨', '即墨市', '山东省,青岛市,即墨市', 3, 'jimo', '0532', '266200', 'J', '120.44699', '36.38907');
INSERT INTO `region` VALUES (1396, 1387, '平度', '平度市', '山东省,青岛市,平度市', 3, 'pingdu', '0532', '266700', 'P', '119.95996', '36.78688');
INSERT INTO `region` VALUES (1397, 1387, '莱西', '莱西市', '山东省,青岛市,莱西市', 3, 'laixi', '0532', '266600', 'L', '120.51773', '36.88804');
INSERT INTO `region` VALUES (1398, 1387, '西海岸', '西海岸新区', '山东省,青岛市,西海岸新区', 3, 'xihai\'an', '0532', '266500', 'X', '120.19775', '35.96065');
INSERT INTO `region` VALUES (1399, 1375, '淄博', '淄博市', '山东省,淄博市', 2, 'zibo', '0533', '255039', 'Z', '118.047648', '36.814939');
INSERT INTO `region` VALUES (1400, 1399, '淄川', '淄川区', '山东省,淄博市,淄川区', 3, 'zichuan', '0533', '255100', 'Z', '117.96655', '36.64339');
INSERT INTO `region` VALUES (1401, 1399, '张店', '张店区', '山东省,淄博市,张店区', 3, 'zhangdian', '0533', '255022', 'Z', '118.01788', '36.80676');
INSERT INTO `region` VALUES (1402, 1399, '博山', '博山区', '山东省,淄博市,博山区', 3, 'boshan', '0533', '255200', 'B', '117.86166', '36.49469');
INSERT INTO `region` VALUES (1403, 1399, '临淄', '临淄区', '山东省,淄博市,临淄区', 3, 'linzi', '0533', '255400', 'L', '118.30966', '36.8259');
INSERT INTO `region` VALUES (1404, 1399, '周村', '周村区', '山东省,淄博市,周村区', 3, 'zhoucun', '0533', '255300', 'Z', '117.86969', '36.80322');
INSERT INTO `region` VALUES (1405, 1399, '桓台', '桓台县', '山东省,淄博市,桓台县', 3, 'huantai', '0533', '256400', 'H', '118.09698', '36.96036');
INSERT INTO `region` VALUES (1406, 1399, '高青', '高青县', '山东省,淄博市,高青县', 3, 'gaoqing', '0533', '256300', 'G', '117.82708', '37.17197');
INSERT INTO `region` VALUES (1407, 1399, '沂源', '沂源县', '山东省,淄博市,沂源县', 3, 'yiyuan', '0533', '256100', 'Y', '118.17105', '36.18536');
INSERT INTO `region` VALUES (1408, 1375, '枣庄', '枣庄市', '山东省,枣庄市', 2, 'zaozhuang', '0632', '277101', 'Z', '117.557964', '34.856424');
INSERT INTO `region` VALUES (1409, 1408, '市中区', '市中区', '山东省,枣庄市,市中区', 3, 'shizhongqu', '0632', '277101', 'S', '117.55603', '34.86391');
INSERT INTO `region` VALUES (1410, 1408, '薛城', '薛城区', '山东省,枣庄市,薛城区', 3, 'xuecheng', '0632', '277000', 'X', '117.26318', '34.79498');
INSERT INTO `region` VALUES (1411, 1408, '峄城', '峄城区', '山东省,枣庄市,峄城区', 3, 'yicheng', '0632', '277300', NULL, '117.59057', '34.77225');
INSERT INTO `region` VALUES (1412, 1408, '台儿庄', '台儿庄区', '山东省,枣庄市,台儿庄区', 3, 'taierzhuang', '0632', '277400', 'T', '117.73452', '34.56363');
INSERT INTO `region` VALUES (1413, 1408, '山亭', '山亭区', '山东省,枣庄市,山亭区', 3, 'shanting', '0632', '277200', 'S', '117.4663', '35.09541');
INSERT INTO `region` VALUES (1414, 1408, '滕州', '滕州市', '山东省,枣庄市,滕州市', 3, 'tengzhou', '0632', '277500', NULL, '117.165', '35.10534');
INSERT INTO `region` VALUES (1415, 1375, '东营', '东营市', '山东省,东营市', 2, 'dongying', '0546', '257093', 'D', '118.4963', '37.461266');
INSERT INTO `region` VALUES (1416, 1415, '东营', '东营区', '山东省,东营市,东营区', 3, 'dongying', '0546', '257029', 'D', '118.5816', '37.44875');
INSERT INTO `region` VALUES (1417, 1415, '河口', '河口区', '山东省,东营市,河口区', 3, 'hekou', '0546', '257200', 'H', '118.5249', '37.88541');
INSERT INTO `region` VALUES (1418, 1415, '垦利', '垦利县', '山东省,东营市,垦利县', 3, 'kenli', '0546', '257500', 'K', '118.54815', '37.58825');
INSERT INTO `region` VALUES (1419, 1415, '利津', '利津县', '山东省,东营市,利津县', 3, 'lijin', '0546', '257400', 'L', '118.25637', '37.49157');
INSERT INTO `region` VALUES (1420, 1415, '广饶', '广饶县', '山东省,东营市,广饶县', 3, 'guangrao', '0546', '257300', 'G', '118.40704', '37.05381');
INSERT INTO `region` VALUES (1421, 1375, '烟台', '烟台市', '山东省,烟台市', 2, 'yantai', '0635', '264010', 'Y', '121.391382', '37.539297');
INSERT INTO `region` VALUES (1422, 1421, '芝罘', '芝罘区', '山东省,烟台市,芝罘区', 3, 'zhifu', '0635', '264001', 'Z', '121.40023', '37.54064');
INSERT INTO `region` VALUES (1423, 1421, '福山', '福山区', '山东省,烟台市,福山区', 3, 'fushan', '0635', '265500', 'F', '121.26812', '37.49841');
INSERT INTO `region` VALUES (1424, 1421, '牟平', '牟平区', '山东省,烟台市,牟平区', 3, 'muping', '0635', '264100', 'M', '121.60067', '37.38846');
INSERT INTO `region` VALUES (1425, 1421, '莱山', '莱山区', '山东省,烟台市,莱山区', 3, 'laishan', '0635', '264600', 'L', '121.44512', '37.51165');
INSERT INTO `region` VALUES (1426, 1421, '长岛', '长岛县', '山东省,烟台市,长岛县', 3, 'changdao', '0635', '265800', 'C', '120.738', '37.91754');
INSERT INTO `region` VALUES (1427, 1421, '龙口', '龙口市', '山东省,烟台市,龙口市', 3, 'longkou', '0635', '265700', 'L', '120.50634', '37.64064');
INSERT INTO `region` VALUES (1428, 1421, '莱阳', '莱阳市', '山东省,烟台市,莱阳市', 3, 'laiyang', '0635', '265200', 'L', '120.71066', '36.98012');
INSERT INTO `region` VALUES (1429, 1421, '莱州', '莱州市', '山东省,烟台市,莱州市', 3, 'laizhou', '0635', '261400', 'L', '119.94137', '37.17806');
INSERT INTO `region` VALUES (1430, 1421, '蓬莱', '蓬莱市', '山东省,烟台市,蓬莱市', 3, 'penglai', '0635', '265600', 'P', '120.75988', '37.81119');
INSERT INTO `region` VALUES (1431, 1421, '招远', '招远市', '山东省,烟台市,招远市', 3, 'zhaoyuan', '0635', '265400', 'Z', '120.40481', '37.36269');
INSERT INTO `region` VALUES (1432, 1421, '栖霞', '栖霞市', '山东省,烟台市,栖霞市', 3, 'qixia', '0635', '265300', 'Q', '120.85025', '37.33571');
INSERT INTO `region` VALUES (1433, 1421, '海阳', '海阳市', '山东省,烟台市,海阳市', 3, 'haiyang', '0635', '265100', 'H', '121.15976', '36.77622');
INSERT INTO `region` VALUES (1434, 1375, '潍坊', '潍坊市', '山东省,潍坊市', 2, 'weifang', '0536', '261041', 'W', '119.107078', '36.70925');
INSERT INTO `region` VALUES (1435, 1434, '潍城', '潍城区', '山东省,潍坊市,潍城区', 3, 'weicheng', '0536', '261021', 'W', '119.10582', '36.7139');
INSERT INTO `region` VALUES (1436, 1434, '寒亭', '寒亭区', '山东省,潍坊市,寒亭区', 3, 'hanting', '0536', '261100', 'H', '119.21832', '36.77504');
INSERT INTO `region` VALUES (1437, 1434, '坊子', '坊子区', '山东省,潍坊市,坊子区', 3, 'fangzi', '0536', '261200', 'F', '119.16476', '36.65218');
INSERT INTO `region` VALUES (1438, 1434, '奎文', '奎文区', '山东省,潍坊市,奎文区', 3, 'kuiwen', '0536', '261031', 'K', '119.12532', '36.70723');
INSERT INTO `region` VALUES (1439, 1434, '临朐', '临朐县', '山东省,潍坊市,临朐县', 3, 'linqu', '0536', '262600', 'L', '118.544', '36.51216');
INSERT INTO `region` VALUES (1440, 1434, '昌乐', '昌乐县', '山东省,潍坊市,昌乐县', 3, 'changle', '0536', '262400', 'C', '118.83017', '36.7078');
INSERT INTO `region` VALUES (1441, 1434, '青州', '青州市', '山东省,潍坊市,青州市', 3, 'qingzhou', '0536', '262500', 'Q', '118.47915', '36.68505');
INSERT INTO `region` VALUES (1442, 1434, '诸城', '诸城市', '山东省,潍坊市,诸城市', 3, 'zhucheng', '0536', '262200', 'Z', '119.40988', '35.99662');
INSERT INTO `region` VALUES (1443, 1434, '寿光', '寿光市', '山东省,潍坊市,寿光市', 3, 'shouguang', '0536', '262700', 'S', '118.74047', '36.88128');
INSERT INTO `region` VALUES (1444, 1434, '安丘', '安丘市', '山东省,潍坊市,安丘市', 3, 'anqiu', '0536', '262100', 'A', '119.2189', '36.47847');
INSERT INTO `region` VALUES (1445, 1434, '高密', '高密市', '山东省,潍坊市,高密市', 3, 'gaomi', '0536', '261500', 'G', '119.75701', '36.38397');
INSERT INTO `region` VALUES (1446, 1434, '昌邑', '昌邑市', '山东省,潍坊市,昌邑市', 3, 'changyi', '0536', '261300', 'C', '119.39767', '36.86008');
INSERT INTO `region` VALUES (1447, 1375, '济宁', '济宁市', '山东省,济宁市', 2, 'jining', '0537', '272119', 'J', '116.587245', '35.415393');
INSERT INTO `region` VALUES (1448, 1447, '任城', '任城区', '山东省,济宁市,任城区', 3, 'rencheng', '0537', '272113', 'R', '116.59504', '35.40659');
INSERT INTO `region` VALUES (1449, 1447, '兖州', '兖州区', '山东省,济宁市,兖州区', 3, 'yanzhou', '0537', '272000', NULL, '116.826546', '35.552305');
INSERT INTO `region` VALUES (1450, 1447, '微山', '微山县', '山东省,济宁市,微山县', 3, 'weishan', '0537', '277600', 'W', '117.12875', '34.80712');
INSERT INTO `region` VALUES (1451, 1447, '鱼台', '鱼台县', '山东省,济宁市,鱼台县', 3, 'yutai', '0537', '272300', 'Y', '116.64761', '34.99674');
INSERT INTO `region` VALUES (1452, 1447, '金乡', '金乡县', '山东省,济宁市,金乡县', 3, 'jinxiang', '0537', '272200', 'J', '116.31146', '35.065');
INSERT INTO `region` VALUES (1453, 1447, '嘉祥', '嘉祥县', '山东省,济宁市,嘉祥县', 3, 'jiaxiang', '0537', '272400', 'J', '116.34249', '35.40836');
INSERT INTO `region` VALUES (1454, 1447, '汶上', '汶上县', '山东省,济宁市,汶上县', 3, 'wenshang', '0537', '272501', NULL, '116.48742', '35.73295');
INSERT INTO `region` VALUES (1455, 1447, '泗水', '泗水县', '山东省,济宁市,泗水县', 3, 'sishui', '0537', '273200', NULL, '117.27948', '35.66113');
INSERT INTO `region` VALUES (1456, 1447, '梁山', '梁山县', '山东省,济宁市,梁山县', 3, 'liangshan', '0537', '272600', 'L', '116.09683', '35.80322');
INSERT INTO `region` VALUES (1457, 1447, '曲阜', '曲阜市', '山东省,济宁市,曲阜市', 3, 'qufu', '0537', '273100', 'Q', '116.98645', '35.58091');
INSERT INTO `region` VALUES (1458, 1447, '邹城', '邹城市', '山东省,济宁市,邹城市', 3, 'zoucheng', '0537', '273500', 'Z', '116.97335', '35.40531');
INSERT INTO `region` VALUES (1459, 1375, '泰安', '泰安市', '山东省,泰安市', 2, 'tai\'an', '0538', '271000', 'T', '117.129063', '36.194968');
INSERT INTO `region` VALUES (1460, 1459, '泰山', '泰山区', '山东省,泰安市,泰山区', 3, 'taishan', '0538', '271000', 'T', '117.13446', '36.19411');
INSERT INTO `region` VALUES (1461, 1459, '岱岳', '岱岳区', '山东省,泰安市,岱岳区', 3, 'daiyue', '0538', '271000', NULL, '117.04174', '36.1875');
INSERT INTO `region` VALUES (1462, 1459, '宁阳', '宁阳县', '山东省,泰安市,宁阳县', 3, 'ningyang', '0538', '271400', 'N', '116.80542', '35.7599');
INSERT INTO `region` VALUES (1463, 1459, '东平', '东平县', '山东省,泰安市,东平县', 3, 'dongping', '0538', '271500', 'D', '116.47113', '35.93792');
INSERT INTO `region` VALUES (1464, 1459, '新泰', '新泰市', '山东省,泰安市,新泰市', 3, 'xintai', '0538', '271200', 'X', '117.76959', '35.90887');
INSERT INTO `region` VALUES (1465, 1459, '肥城', '肥城市', '山东省,泰安市,肥城市', 3, 'feicheng', '0538', '271600', 'F', '116.76815', '36.18247');
INSERT INTO `region` VALUES (1466, 1375, '威海', '威海市', '山东省,威海市', 2, 'weihai', '0631', '264200', 'W', '122.116394', '37.509691');
INSERT INTO `region` VALUES (1467, 1466, '环翠', '环翠区', '山东省,威海市,环翠区', 3, 'huancui', '0631', '264200', 'H', '122.12344', '37.50199');
INSERT INTO `region` VALUES (1468, 1466, '文登', '文登区', '山东省,威海市,文登区', 3, 'wendeng', '0631', '266440', 'W', '122.057139', '37.196211');
INSERT INTO `region` VALUES (1469, 1466, '荣成', '荣成市', '山东省,威海市,荣成市', 3, 'rongcheng', '0631', '264300', 'R', '122.48773', '37.1652');
INSERT INTO `region` VALUES (1470, 1466, '乳山', '乳山市', '山东省,威海市,乳山市', 3, 'rushan', '0631', '264500', 'R', '121.53814', '36.91918');
INSERT INTO `region` VALUES (1471, 1375, '日照', '日照市', '山东省,日照市', 2, 'rizhao', '0633', '276800', 'R', '119.461208', '35.428588');
INSERT INTO `region` VALUES (1472, 1471, '东港', '东港区', '山东省,日照市,东港区', 3, 'donggang', '0633', '276800', 'D', '119.46237', '35.42541');
INSERT INTO `region` VALUES (1473, 1471, '岚山', '岚山区', '山东省,日照市,岚山区', 3, 'lanshan', '0633', '276808', NULL, '119.31884', '35.12203');
INSERT INTO `region` VALUES (1474, 1471, '五莲', '五莲县', '山东省,日照市,五莲县', 3, 'wulian', '0633', '262300', 'W', '119.207', '35.75004');
INSERT INTO `region` VALUES (1475, 1471, '莒县', '莒县', '山东省,日照市,莒县', 3, 'juxian', '0633', '276500', NULL, '118.83789', '35.58054');
INSERT INTO `region` VALUES (1476, 1375, '莱芜', '莱芜市', '山东省,莱芜市', 2, 'laiwu', '0634', '271100', 'L', '117.677736', '36.214397');
INSERT INTO `region` VALUES (1477, 1476, '莱城', '莱城区', '山东省,莱芜市,莱城区', 3, 'laicheng', '0634', '271199', 'L', '117.65986', '36.2032');
INSERT INTO `region` VALUES (1478, 1476, '钢城', '钢城区', '山东省,莱芜市,钢城区', 3, 'gangcheng', '0634', '271100', 'G', '117.8049', '36.06319');
INSERT INTO `region` VALUES (1479, 1375, '临沂', '临沂市', '山东省,临沂市', 2, 'linyi', '0539', '253000', 'L', '118.326443', '35.065282');
INSERT INTO `region` VALUES (1480, 1479, '兰山', '兰山区', '山东省,临沂市,兰山区', 3, 'lanshan', '0539', '276002', 'L', '118.34817', '35.06872');
INSERT INTO `region` VALUES (1481, 1479, '罗庄', '罗庄区', '山东省,临沂市,罗庄区', 3, 'luozhuang', '0539', '276022', 'L', '118.28466', '34.99627');
INSERT INTO `region` VALUES (1482, 1479, '河东', '河东区', '山东省,临沂市,河东区', 3, 'hedong', '0539', '276034', 'H', '118.41055', '35.08803');
INSERT INTO `region` VALUES (1483, 1479, '沂南', '沂南县', '山东省,临沂市,沂南县', 3, 'yinan', '0539', '276300', 'Y', '118.47061', '35.55131');
INSERT INTO `region` VALUES (1484, 1479, '郯城', '郯城县', '山东省,临沂市,郯城县', 3, 'tancheng', '0539', '276100', NULL, '118.36712', '34.61354');
INSERT INTO `region` VALUES (1485, 1479, '沂水', '沂水县', '山东省,临沂市,沂水县', 3, 'yishui', '0539', '276400', 'Y', '118.63009', '35.78731');
INSERT INTO `region` VALUES (1486, 1479, '兰陵', '兰陵县', '山东省,临沂市,兰陵县', 3, 'lanling', '0539', '277700', 'L', '117.856592', '34.738315');
INSERT INTO `region` VALUES (1487, 1479, '费县', '费县', '山东省,临沂市,费县', 3, 'feixian', '0539', '273400', 'F', '117.97836', '35.26562');
INSERT INTO `region` VALUES (1488, 1479, '平邑', '平邑县', '山东省,临沂市,平邑县', 3, 'pingyi', '0539', '273300', 'P', '117.63867', '35.50573');
INSERT INTO `region` VALUES (1489, 1479, '莒南', '莒南县', '山东省,临沂市,莒南县', 3, 'junan', '0539', '276600', NULL, '118.83227', '35.17539');
INSERT INTO `region` VALUES (1490, 1479, '蒙阴', '蒙阴县', '山东省,临沂市,蒙阴县', 3, 'mengyin', '0539', '276200', 'M', '117.94592', '35.70996');
INSERT INTO `region` VALUES (1491, 1479, '临沭', '临沭县', '山东省,临沂市,临沭县', 3, 'linshu', '0539', '276700', 'L', '118.65267', '34.92091');
INSERT INTO `region` VALUES (1492, 1375, '德州', '德州市', '山东省,德州市', 2, 'dezhou', '0534', '253000', 'D', '116.307428', '37.453968');
INSERT INTO `region` VALUES (1493, 1492, '德城', '德城区', '山东省,德州市,德城区', 3, 'decheng', '0534', '253012', 'D', '116.29943', '37.45126');
INSERT INTO `region` VALUES (1494, 1492, '陵城', '陵城区', '山东省,德州市,陵城区', 3, 'lingcheng', '0534', '253500', 'L', '116.57601', '37.33571');
INSERT INTO `region` VALUES (1495, 1492, '宁津', '宁津县', '山东省,德州市,宁津县', 3, 'ningjin', '0534', '253400', 'N', '116.79702', '37.65301');
INSERT INTO `region` VALUES (1496, 1492, '庆云', '庆云县', '山东省,德州市,庆云县', 3, 'qingyun', '0534', '253700', 'Q', '117.38635', '37.77616');
INSERT INTO `region` VALUES (1497, 1492, '临邑', '临邑县', '山东省,德州市,临邑县', 3, 'linyi', '0534', '251500', 'L', '116.86547', '37.19053');
INSERT INTO `region` VALUES (1498, 1492, '齐河', '齐河县', '山东省,德州市,齐河县', 3, 'qihe', '0534', '251100', 'Q', '116.75515', '36.79532');
INSERT INTO `region` VALUES (1499, 1492, '平原', '平原县', '山东省,德州市,平原县', 3, 'pingyuan', '0534', '253100', 'P', '116.43432', '37.16632');
INSERT INTO `region` VALUES (1500, 1492, '夏津', '夏津县', '山东省,德州市,夏津县', 3, 'xiajin', '0534', '253200', 'X', '116.0017', '36.94852');
INSERT INTO `region` VALUES (1501, 1492, '武城', '武城县', '山东省,德州市,武城县', 3, 'wucheng', '0534', '253300', 'W', '116.07009', '37.21403');
INSERT INTO `region` VALUES (1502, 1492, '乐陵', '乐陵市', '山东省,德州市,乐陵市', 3, 'leling', '0534', '253600', 'L', '117.23141', '37.73164');
INSERT INTO `region` VALUES (1503, 1492, '禹城', '禹城市', '山东省,德州市,禹城市', 3, 'yucheng', '0534', '251200', 'Y', '116.64309', '36.93444');
INSERT INTO `region` VALUES (1504, 1375, '聊城', '聊城市', '山东省,聊城市', 2, 'liaocheng', '0635', '252052', 'L', '115.980367', '36.456013');
INSERT INTO `region` VALUES (1505, 1504, '东昌府', '东昌府区', '山东省,聊城市,东昌府区', 3, 'dongchangfu', '0635', '252000', 'D', '115.97383', '36.44458');
INSERT INTO `region` VALUES (1506, 1504, '阳谷', '阳谷县', '山东省,聊城市,阳谷县', 3, 'yanggu', '0635', '252300', 'Y', '115.79126', '36.11444');
INSERT INTO `region` VALUES (1507, 1504, '莘县', '莘县', '山东省,聊城市,莘县', 3, 'shenxian', '0635', '252400', NULL, '115.6697', '36.23423');
INSERT INTO `region` VALUES (1508, 1504, '茌平', '茌平县', '山东省,聊城市,茌平县', 3, 'chiping', '0635', '252100', NULL, '116.25491', '36.57969');
INSERT INTO `region` VALUES (1509, 1504, '东阿', '东阿县', '山东省,聊城市,东阿县', 3, 'dong\'e', '0635', '252200', 'D', '116.25012', '36.33209');
INSERT INTO `region` VALUES (1510, 1504, '冠县', '冠县', '山东省,聊城市,冠县', 3, 'guanxian', '0635', '252500', 'G', '115.44195', '36.48429');
INSERT INTO `region` VALUES (1511, 1504, '高唐', '高唐县', '山东省,聊城市,高唐县', 3, 'gaotang', '0635', '252800', 'G', '116.23172', '36.86535');
INSERT INTO `region` VALUES (1512, 1504, '临清', '临清市', '山东省,聊城市,临清市', 3, 'linqing', '0635', '252600', 'L', '115.70629', '36.83945');
INSERT INTO `region` VALUES (1513, 1375, '滨州', '滨州市', '山东省,滨州市', 2, 'binzhou', '0543', '256619', 'B', '118.016974', '37.383542');
INSERT INTO `region` VALUES (1514, 1513, '滨城', '滨城区', '山东省,滨州市,滨城区', 3, 'bincheng', '0543', '256613', 'B', '118.02026', '37.38524');
INSERT INTO `region` VALUES (1515, 1513, '沾化', '沾化区', '山东省,滨州市,沾化区', 3, 'zhanhua', '0543', '256800', 'Z', '118.13214', '37.69832');
INSERT INTO `region` VALUES (1516, 1513, '惠民', '惠民县', '山东省,滨州市,惠民县', 3, 'huimin', '0543', '251700', 'H', '117.51113', '37.49013');
INSERT INTO `region` VALUES (1517, 1513, '阳信', '阳信县', '山东省,滨州市,阳信县', 3, 'yangxin', '0543', '251800', 'Y', '117.58139', '37.64198');
INSERT INTO `region` VALUES (1518, 1513, '无棣', '无棣县', '山东省,滨州市,无棣县', 3, 'wudi', '0543', '251900', 'W', '117.61395', '37.74009');
INSERT INTO `region` VALUES (1519, 1513, '博兴', '博兴县', '山东省,滨州市,博兴县', 3, 'boxing', '0543', '256500', 'B', '118.1336', '37.14316');
INSERT INTO `region` VALUES (1520, 1513, '邹平', '邹平县', '山东省,滨州市,邹平县', 3, 'zouping', '0543', '256200', 'Z', '117.74307', '36.86295');
INSERT INTO `region` VALUES (1521, 1513, '北海新区', '北海新区', '山东省,滨州市,北海新区', 3, 'beihaixinqu', '0543', '256200', 'B', '118.016974', '37.383542');
INSERT INTO `region` VALUES (1522, 1375, '菏泽', '菏泽市', '山东省,菏泽市', 2, 'heze', '0530', '274020', 'H', '115.469381', '35.246531');
INSERT INTO `region` VALUES (1523, 1522, '牡丹', '牡丹区', '山东省,菏泽市,牡丹区', 3, 'mudan', '0530', '274009', 'M', '115.41662', '35.25091');
INSERT INTO `region` VALUES (1524, 1522, '曹县', '曹县', '山东省,菏泽市,曹县', 3, 'caoxian', '0530', '274400', 'C', '115.54226', '34.82659');
INSERT INTO `region` VALUES (1525, 1522, '单县', '单县', '山东省,菏泽市,单县', 3, 'shanxian', '0530', '273700', 'D', '116.08703', '34.79514');
INSERT INTO `region` VALUES (1526, 1522, '成武', '成武县', '山东省,菏泽市,成武县', 3, 'chengwu', '0530', '274200', 'C', '115.8897', '34.95332');
INSERT INTO `region` VALUES (1527, 1522, '巨野', '巨野县', '山东省,菏泽市,巨野县', 3, 'juye', '0530', '274900', 'J', '116.09497', '35.39788');
INSERT INTO `region` VALUES (1528, 1522, '郓城', '郓城县', '山东省,菏泽市,郓城县', 3, 'yuncheng', '0530', '274700', NULL, '115.94439', '35.60044');
INSERT INTO `region` VALUES (1529, 1522, '鄄城', '鄄城县', '山东省,菏泽市,鄄城县', 3, 'juancheng', '0530', '274600', NULL, '115.50997', '35.56412');
INSERT INTO `region` VALUES (1530, 1522, '定陶', '定陶县', '山东省,菏泽市,定陶县', 3, 'dingtao', '0530', '274100', 'D', '115.57287', '35.07118');
INSERT INTO `region` VALUES (1531, 1522, '东明', '东明县', '山东省,菏泽市,东明县', 3, 'dongming', '0530', '274500', 'D', '115.09079', '35.28906');

-- ----------------------------
-- Table structure for section
-- ----------------------------
DROP TABLE IF EXISTS `section`;
CREATE TABLE `section`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `category_id` int(11) NULL DEFAULT NULL,
  `editor_id` int(11) NULL DEFAULT NULL COMMENT '编辑人',
  `add_time` datetime NULL DEFAULT NULL,
  `edit_time` datetime NULL DEFAULT NULL,
  `target` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授课目标',
  `layout` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课堂框架',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of section
-- ----------------------------
INSERT INTO `section` VALUES (10, 'KB0L1', 1, NULL, '2020-09-01 09:18:51', '2020-09-14 20:44:32', '学习自我介绍；\r\n星星家族成员名字', 'WARM UP\r\nSING A SONG(5 MINUTS)\r\nRULES:\r\n1.HANDS ON THE DESK\r\n2.EYES ON_\r\n3.WHO CAN TRY? I CAN TRY!\r\n\r\nSHOW CHARACTER\r\nMarie  10MINUT\r\n\r\nT:KNOCK KNOCK KNOCK!\r\nWHO’S COMING?HI!WHAT’S YOUR NAME?\r\nI’M MARIE(PRETENT TO SPEAK AS A CARTOONNESS)\r\nHELLO MARIE!EVERYBODY,LET’S SAY HELLO MARIE!\r\nMarie:WHAT’S YOUR NAME?(ASK TEACHER)\r\nT:I’M_____\r\nMarie:WHAT’S YOUR NAME?(ASK CO-TEACHER)\r\nCO-T:I’M_______.\r\n\r\nSAY HELLO MARIE \r\nLET MARIE ASK SOME KIDS “WHAT’S YOUR NAME?”\r\nGAME\r\nBRING MARIE RUN THROUGH THE LINE OF THE CLASS,LET KIDS SAY HI MARIE AND TRY TO HAVE FIVE WITH HER,IF THEY HAVE FIVE WITH HER SUCCESFUULLY,MARIE WILL ASK WHAT’S YOUR NAME?STUDENTS NEED TO ANWSER\r\n\r\nMASK-MAN 10MINUT\r\nMAKE MASK-MAN FLYING IN THE CLASSROOM,SAY HI TO STUDENT 10\r\nASK MASK-MAN WHAT’S YOUR NAME?LET HIM ANWSER.ALSO LET MASK-MAN ASK FEW KIDS WHAT’S YOUR NAME.\r\nGAME\r\nEVERY BODY SATND UP\r\nSAY HI TO MASK MAN AND JUMP ,TRY TO USE THEIR HEAD TOUCH MASK-MAN.\r\nIF THEY TOUCH MASK-MAN,HE WILL ASK WHAT’S UR NAME,STUDENTS NEED TO ANWSER\r\n\r\nMONTY 10MINUT\r\n\r\nASK ASSISTAN PUT SOME MOUSE F\\C IN THE CLASSROOM, BEFORE THE CLASS\r\nMAKE SOME NOISE OF LITTLE MOUSE\r\nPRETEND CAN NOT FIND IT\r\nLET SOME KIDS HELP TO FIND,BUT THEY WILL FIND SOME MOUSE THAT NOT MONTY\r\nT:THIS IS NOT THE ONE I WANT!NOT THIS ONE!ALSO NOT THIS ONE!\r\nOOPS!THAT IS WHAT I WANT! WHAT’S  UR NAME?\r\nMONTY:I’M  MONTY!\r\nT:LET’S SAY HI MONTY!\r\nGAME\r\nLET KIDS SAY HI MONTY,WHO CAN CATCH MONTY,MONTY WILL ASK QUESTION AND KISS U\r\n\r\nREVIEW PART 5MINUT\r\nSHOW F/C,LET KIDS SAY THE NAME\r\nLET KIDS ASK”WHAT’S YOUR NAME?”\r\nT USE THE CHARACTOR ANWSER', '教具：... ');
INSERT INTO `section` VALUES (11, 'KB0L2', 1, NULL, '2020-09-01 10:43:01', '2020-09-14 20:44:43', '1.理解和使用数字1-10；\r\n2.学会问好与回答\r\n', 'WARM UP 5MINUT\r\nSING AND DANCE\r\nRULE\r\n1.HANDS ON YOUR DESK\r\n2.EYES ON ____\r\n3.WHO CAN TRY ?I CAN TRY\r\n\r\nREVIEW CHARACTOR NAME 5MINUT\r\nASK AND ANWSER\r\nWHAT’S YOUR NAME?\r\n\r\nT:HELLO EVERYONE!I’M_,CAN YOU SAY HELLO___? 20MINUT\r\nGOOD JOB!TODAY I WANT TO INTRODUCE U GUYS SOME NEW FRIENDS!\r\nSONG:10 PENGUINS\r\nT:YES!TODAY WE ARE GOING TO LEARN NUMBERS!\r\nLET’S READ AFTER ME!ONE!TWO!THREE!\r\nGAME1:FASTER CARD:MIX CARD SHOW KIDS\r\nGAME2:\r\nBLOW BALLON(NO.1) U SAY ONE ONE ONE! BYE BYE ONE!\r\nBLOW BALLON(NO.2)SAY 2.2.2BYE BYE 2!\r\nBLOW BALLON(NO.3)SAY3.3.3BYE BYE 3!\r\n\r\nT:OH LOOK!HERE I HAVE MORE FRIENDS !\r\nI HAVE ONE TWO THREE/ FOUR FIVE SIX BOOKS!\r\nREAPEAT AFTER ME PLEASE!ONE TWO THREE!FOUR FIVE SIX!\r\nSHOW F/C LET KIDS SAY 4.5.6\r\nLOOK!MY FRIEND CAN NOT WAIT TO SEE U FROM THE BOOKS!FIRST FRIEND IS FROM BOOK?LET KIDS SAY NUMBER OF THE BOOK!(3.5.6.4.2.1.....)\r\n [BUTTERFLY FLY AWAY....]\r\n\r\nGAME:GIVE SMALL CARD 1-6 TO 2 GROUPS OF KIDS(12 KIDS),WHEN T SAY THE NUMBER,THEY NEED TO STAND UP AND SAY THEIR NUMBER\r\n \r\nSENTENCE:10MINUT\r\nHOW OLD ARE YOU?\r\nT ASK CO-T ,CO-T ANWSER\r\nGIVE CO-T A CAKE WITH 23NUMBER CANDEL\r\n\r\nT ASK ONE CARTOON GIRL HOW OLD ARE YOU?\r\nCARTOON GIRL SAYS I’M 6\r\nT GIVE HER A CAKE WITH CANDLE\r\n\r\nT ASK COUPLE OF KIDS HOW OLD ARE U\r\nGIVE CAKES WITH NUMBER CANDLE\r\nT:EVERY BODY SHOW ME YOUR HANDS!READ AFTER ME!\r\n\r\nREVIEW 5MINUTS\r\nF\\C OF NO.\r\nASK AND ANWSER', '');
INSERT INTO `section` VALUES (12, 'KB0L3', 1, NULL, '2020-09-01 10:50:14', '2020-09-14 20:44:55', '1.认识男孩和女孩\r\n2.能够做一个关于年龄和性别的自我介绍。', 'WARM UP 5MINUT\r\nSING AND DANCE\r\nRULE\r\n1.HANDS ON YOUR DESK\r\n2.EYES ON ____\r\n3.WHO CAN TRY ?I CAN TRY\r\n\r\nNUMBER1-6/HOW OLD ARE YOU?I’M.....  5MINUT\r\nASK AND ANWSER\r\nWHAT’S YOUR NAME?\r\nKEY WORDS:15 MINUT\r\nT:[A BIG PICTURE ON THE BOARD BOY/GIRL]\r\nLOOK!THERE IS A PICTURE,HERE IS A BOY AND A GIRL!\r\nLET’S REPEAT WITH ME!BOY BOY!GIRL GIRL!\r\nWHEN I POINT TO BOY!BOY SAY BOY LOULDLY!\r\nWHEN I POINT RO GIRL,GIRL SAY GIRL LOUDLY!\r\n\r\nTHIS TIME,LET’S PLAY A GAME!\r\nGROUP A AND B LISTEN UP!\r\nWHEN I SHOW U F/C GIRL ,GIRL STAND UP AND SAY GIRL\r\nWHEN I SHOW F/C BOY,BOY STAND UP AND SAY BOY!\r\n[FIRST TEACHER SAY THE WORDS AND SHOW F/C,WHEN U FEEL KIDS CAN REMEMBER WORDS.U ONLY SHOW F/C,LET THEM SPEAK OUT THE WORDS]\r\nCHANGE TO THE OTHER 2 GROUPS PLAY\r\n\r\nSENTENCE: 15 MINTUS\r\nPUT WHAT’S YOUR NAME?\r\nI’m...\r\nHow OLD ARE YOU?\r\nI’M....\r\nARE YOU A GIRL OR BOY?\r\nI’M A ....\r\n\r\nON BOARD\r\nLET KIDS SAY SEVERL TIME WITH T\r\nLET THEM PRACTICE\r\nLATER LET THEM STAND UP AND INTRODUCE THEM SELF (EACH GROUP CHOOSE 3)', '');
INSERT INTO `section` VALUES (13, 'KB0L4', 1, NULL, '2020-09-01 10:54:13', '2020-09-14 20:45:07', '1.复习L1-L3。\r\n2.做一个测试。', 'K: Hello, Marie! \r\nHello Mask-man! \r\nHello Monty!\r\nT: Hello! What’s your name? (with the FC of Marie)\r\nK: My name is ___.\r\nT: Hello! How old are you? (with the FC of Monty)\r\nK: I’m ___ years old.\r\nT: Great! Are you a boy or a girl?(with the FC of Mask-man)\r\nK: I’m a girl/boy.\r\nLet kids introduce themself.', '');
INSERT INTO `section` VALUES (14, 'KB0L5', 1, NULL, '2020-09-01 11:05:51', '2020-09-14 20:45:20', '1.认识关于教室的物品。\r\n2.学会回答”这个是什么？--它是一个什么。”', 'Warm up\r\nSing a song\r\nRules\r\n1.Hands on the desk\r\n2.Eyes on_\r\n3.Who can try? I can try!\r\nBag 7minut\r\nT: (The teacher takes a bag to put on the platform.) Wow! What’s this? Oh! This is a bag. (The teacher carries a bag in the classroom shuttle.) I have a bag, I am so happy, Do you have a bag? Show me your bag!\r\nS:The students took out their schoolbags and showed them to the teacher.\r\nT: Show me your bag up! Down!\r\nS: bag!bag!bag! \r\nThe students read after the teacher.\r\nBook 7minut\r\n\r\nOn the ppt, there is a background picture of the school. The teacher is carrying a bag and says, \"I\'m going to school now!\" What should I take to school? Open the bag, take out a cookie, can I take it to school? The ta teacher takes the children and says, no, the teacher takes out a toy, can I take it to school? The TA teacher takes the students and says no.The teacher asked, \"What should I take to school?\" The teaching assistant took out a book at this time. You should take this to school! The children read after the teacher, Book! Book! Book!\r\nThe students read after the teacher.\r\n\r\nChair 7minut\r\n\r\nThe teacher said I was very tired, I can sit there, the teacher suddenly sat down on the table, asked the students, can I sit here, the students said no, the teacher sat on the ground, can I sit here, the students said no, at this time pull up a chair and said, Can I sit here? The students said yes. Students follow the teacher to read chair!chair!chair!Wow! I have a chair, it can let me relax, let me see who has a chair, you have!you have! you also have,!(The teacher pointed to the students\' chairs said.)\r\n\r\nopen your books 6minu\r\n1. The foreign teacher shows, open your book and do some movements for the students. 2. The foreign teacher said take out your book, the teaching assistant did a demonstration, help the students take out a book from the table hole.3. The foreign teacher said, open your book, the students do the action, the faster the teacher said, the students do the action.\r\n close your books 6minut\r\nThe teacher and the TA do a demo, and the teacher says, class is over! The TA teacher says goodbye, the TA says close your book, the TA teacher does the action. The foreign teacher shows, close your book and do some movements for the students. The foreign teacher said, close your book, the students do action, the faster the teacher said, the faster the students do action.\r\nReview part 5minut\r\nShow flashcards,The students read after the teacher.Choose a few students to answer the question, the teacher does say the phrase, the students do the corresponding action,Students and teaching assistants say words and phrases, and foreign teachers do actions. The faster you speak, the faster you move.\r\n', '');
INSERT INTO `section` VALUES (15, 'KB0L6', 1, NULL, '2020-09-01 11:08:58', '2020-09-14 20:45:32', '1.认识铅笔、橡皮、桌子。\r\n2.学会提问与回答“这个是什么？--它是一个...\"', 'WARM-UP\r\nSing a song (5minuts)\r\nRULES\r\n1.Hands on the desk\r\n2.Eyes on ___\r\n3.Who can try? I can try!\r\nSHOW WORDS(21min)\r\n\r\nEraser 7min\r\nCO-T\r\n1.Run into the classroom\r\n2.Oh no, who can help me? It’s so dirty\r\n3.Show the drawing board with many spots(drawing board)\r\nT\r\n1.Don’t worry. Let me help you!\r\n2.Show the FC of eraser\r\n3.Use the eraser to rub the board\r\n4.Say eraser to children, everybody follow T, say eraser several times \r\n5.The louder they say the word, the faster T erase the board. \r\n\r\nGAME\r\n\r\nPencil 7min\r\nCO-T\r\n1.Put the FC of pencil in a corner\r\n2.Say: I need to write my name!\r\n3.___, can you give me a pencil?\r\nT\r\n1.No problem! Try to find the pencil\r\n2.Oh no! Where is my pencil, it must in the classroom. \r\n3.Who can help me to find my pencil\r\n4.Choose one kid to find the FC\r\n5.Use a box to contain the pencil. And say pencil several times.\r\n\r\n\r\n\r\n\r\n\r\n\r\nTable 7min\r\nT\r\n1.Now we need put the eraser and pencil on somewhere\r\n2.Show the PPT with some pictures\r\n3.Ask children and change the pics to put those on the table\r\n4.Say we put eraser and pencil on the table\r\n5.Everyone follow T to say table several times\r\n\r\nSHOW SENTENCE( 9min)\r\nT\r\n1.Now learn the sentence with CO-T\r\n2.Show the FC of eraser/pencil/table and say: what’s this?\r\nCO-T\r\nIt’s a ___.\r\nRepeat several times\r\n\r\nGAME \r\n\r\nREVIEW PART\r\n\r\nShow FC,let kids say the words\r\nAsk kids ‘what’s this’ and let them answer ‘it a ___’.', '');
INSERT INTO `section` VALUES (16, 'KB1L1', 9, NULL, '2020-09-01 11:30:01', '2020-09-14 20:45:44', '1.认识书包、书、椅子。\r\n2.学会提问与回答“这个是什么？--它是一个...”', 'Warm up\r\nSing a song\r\nRules\r\n1.Hands on the desk\r\n2.Eyes on_\r\n3.Who can try? I can try!\r\nBag 7minut\r\nT: (The teacher takes a bag to put on the platform.) Wow! What’s this? Oh! This is a bag. (The teacher carries a bag in the classroom shuttle.) I have a bag, I am so happy, Do you have a bag? Show me your bag!\r\nS:The students took out their schoolbags and showed them to the teacher.\r\nT: Show me your bag up! Down!\r\nS: bag!bag!bag! \r\nThe students read after the teacher.\r\nBook 7minut\r\n\r\nOn the ppt, there is a background picture of the school. The teacher is carrying a bag and says, \"I\'m going to school now!\" What should I take to school? Open the bag, take out a cookie, can I take it to school? The ta teacher takes the children and says, no, the teacher takes out a toy, can I take it to school? The TA teacher takes the students and says no.The teacher asked, \"What should I take to school?\" The teaching assistant took out a book at this time. You should take this to school! The children read after the teacher, Book! Book! Book!\r\nThe students read after the teacher.\r\n\r\nChair 7minut\r\n\r\nThe teacher said I was very tired, I can sit there, the teacher suddenly sat down on the table, asked the students, can I sit here, the students said no, the teacher sat on the ground, can I sit here, the students said no, at this time pull up a chair and said, Can I sit here? The students said yes. Students follow the teacher to read chair!chair!chair!Wow! I have a chair, it can let me relax, let me see who has a chair, you have!you have! you also have,!(The teacher pointed to the students\' chairs said.)\r\n\r\nopen your books 6minu\r\n1. The foreign teacher shows, open your book and do some movements for the students. 2. The foreign teacher said take out your book, the teaching assistant did a demonstration, help the students take out a book from the table hole.3. The foreign teacher said, open your book, the students do the action, the faster the teacher said, the students do the action.\r\n close your books 6minut\r\nThe teacher and the TA do a demo, and the teacher says, class is over! The TA teacher says goodbye, the TA says close your book, the TA teacher does the action. The foreign teacher shows, close your book and do some movements for the students. The foreign teacher said, close your book, the students do action, the faster the teacher said, the faster the students do action.\r\nReview part 5minut\r\nShow flashcards,The students read after the teacher.Choose a few students to answer the question, the teacher does say the phrase, the students do the corresponding action,Students and teaching assistants say words and phrases, and foreign teachers do actions. The faster you speak, the faster you move.\r\nWhat’s this?\r\nIt’s a ...\r\n1.repeat with Teacher\r\n2.Game:teacher point to card,let kids answer', '');
INSERT INTO `section` VALUES (17, 'KB0L7', 1, NULL, '2020-09-01 13:49:47', '2020-09-14 20:45:55', '掌握起立、坐下、听、看、指；\r\n学会句型“指向...\"', 'Warm up\r\nBaby shark\r\nRules\r\n1.Hands on the desk\r\n2.Eyes on_\r\n3.Who can try? I can try!\r\nShow character\r\nStand up 5minut\r\nPrepare a chair on the podium,foreign teacher sits in a chair\r\nT: Stand up!\r\nForeign teacher stand up quickly.\r\nForeign teacher told the children to stand up, the children immediately stood up, students follow the foreign teacher read phrases.\r\nS: Stand up!\r\nForeign teacher stand up quickly, the faster the student say, foreign teacher action more quickly.\r\nSit down 5minut\r\n\r\nPrepare a chair, the foreign teacher stands beside,\r\nT: sit down!\r\nThe foreign teacher does the action by himself. Repeat three times, the students read the phrase to the teacher, then the teacher asks the children to stand up and says sit down, the children sit down quickly, repeat three times,then the children give the teacher the instruction.\r\n S:sit down!\r\nThe teacher sit down quickly.\r\nHere to do an interaction, the teacher suddenly said stand up, the children quickly up, after a while, the teacher suddenly said sit down, the children quickly sit down, choose two children to pk on the podium, the teacher under the instruction, see who do the action quickly.\r\n\r\nlisten 5minut\r\n\r\nPut a beautiful moving music, then foreign teacher immersed in the music, enjoy, body shaking slightly, to make gestures, foreign teacher walked to the ta, ta imitate foreign teacher immersed in music, and make a gesture, at this point, the tas and teachers with the children immersed in the music, to listen to the gestures, while doing gestures, said a word listen.\r\n\r\nlook 5minut\r\nThe teacher pointed to the beautiful scenery outside the window and said, look! Beautiful campus! The teacher pointed at his classmates and said, look! Lovely students! The teacher leans on the classmate\'s desk, looks to the blackboard, makes looks the action, says, looks! The students follow the teacher as they look, follow the teacher as they read.\r\npoint 5minut\r\nDraw a rotary table on the PPT, with a point written in the middle. The foreign teacher points the rotary table in the middle. Where does the pointer stop, the teacher adds a few points.The foreign teacher asks the students to point, read the point first, then point, where the pointer stops, add points.\r\npoint to the...5minut\r\nPoint to the bag,point to the book,point to the chair,point to the eraser,point to the pencil,point to the table\r\nThe foreign teacher at this time plays a parent, asks the student, I am going to school, what is in the school, the assistant teacher plays the student.\r\nTA:Point to the bag!point to the book! point to the chair! point to the eraser! point to the pencil! point to the table!\r\nSelect a few students, the foreign teacher gives instructions, students in the classroom to find the appropriate items, and point to read.\r\n\r\nReview part 5minut\r\nThe teacher shows the flash card, the students read the words, the students say the words, the teacher does the actions, the teacher says the words, the students do the actions.', '');
INSERT INTO `section` VALUES (18, 'KB1L2', 9, NULL, '2020-09-01 14:03:40', '2020-09-14 20:46:13', '1.学会数字1-10。\r\n2.学会提问和回答年龄。', 'WARM UP 5MINUT\r\nSING AND DANCE\r\nRULE\r\n1.HANDS ON YOUR DESK\r\n2.EYES ON ____\r\n3.WHO CAN TRY ?I CAN TRY\r\n\r\nREVIEW CHARACTOR NAME 5MINUT\r\nASK AND ANWSER\r\nWHAT’S YOUR NAME?\r\n\r\nT:HELLO EVERYONE!I’M_,CAN YOU SAY HELLO___? 20MINUT\r\nGOOD JOB!TODAY I WANT TO INTRODUCE U GUYS SOME NEW FRIENDS!\r\nSONG:10 PENGUINS\r\nT:YES!TODAY WE ARE GOING TO LEARN NUMBERS!\r\nLET’S READ AFTER ME!ONE!TWO!THREE!\r\nGAME1:FASTER CARD:MIX CARD SHOW KIDS\r\n4.5.6\r\nPut BALLON 1.2.3 ON THE BOARD\r\nGAME:\r\nBLOW BALLON(NO.4) U SAY ONE 4.4.4. BYE BYE4!\r\nBLOW BALLON(NO.5)SAY5BYE BYE5!\r\nBLOW BALLON(NO.6)SAY6 BYE BYE 6!\r\n 7.8.9.10\r\nT:OH LOOK!HERE I HAVE MORE FRIENDS !\r\nI HAVE ONE TWO THREE/ FOUR FIVE SIX,7.8.9.10 CARDS !\r\nREAPEAT AFTER ME PLEASE!ONE TWO THREE!FOUR FIVE SIX!7.8.9.10\r\nSHOW F/C LET KIDS SAY 7.8.9.10\r\nLOOK!MY FRIEND CAN NOT WAIT TO SEE U FROM THE CARDS!FIRST FRIEND IS FROM CARD NO._?LET KIDS SAY NUMBER OF THE CARD!(9.3.5.8.6.4.10.2.1.7.....)\r\n [BUTTERFLY FLY AWAY....]\r\n\r\nGAME:GIVE SMALL CARD 1-10 TO 2 GROUPS OF KIDS(12 KIDS),WHEN T SAY THE NUMBER,THEY NEED TO STAND UP AND SAY THEIR NUMBER\r\n \r\nSENTENCE:10MINUT\r\nHOW OLD ARE YOU?\r\nT ASK CO-T ,CO-T ANWSER\r\nGIVE CO-T A CAKE WITH 23NUMBER CANDEL\r\n\r\nT ASK ONE CARTOON GIRL HOW OLD ARE YOU?\r\nCARTOON GIRL SAYS I’M 6\r\nT GIVE HER A CAKE WITH CANDLE\r\n\r\nT ASK COUPLE OF KIDS HOW OLD ARE U\r\nGIVE CAKES WITH NUMBER CANDLE\r\nT:EVERY BODY SHOW ME YOUR HANDS!READ AFTER ME!\r\n\r\nREVIEW 5MINUTS\r\nF\\C OF NO.\r\nASK AND ANWSER\r\n', '');
INSERT INTO `section` VALUES (19, 'KB0L8', 1, NULL, '2020-09-01 14:07:36', '2020-09-14 20:46:24', '复习L5-L7;\r\n进行测试', '1.A: Hello Mr Star, my name is Sam. \r\n  B: OK Sam, now open your book.\r\n2.A: Everyone look at me. Hey, Sam, sit down please.\r\n3.A: Hey Marry, point to the book.\r\n4.A: Wow, Sam your bag is beautiful!\r\n5.A: Oh,what’s this Sam? B: Mr Star, It’s an eraser.\r\n6.A: Everyone listen to me. Stand up! \r\n7.A:Sam, write your name and close your book.', '');
INSERT INTO `section` VALUES (20, 'KB1L3', 9, NULL, '2020-09-01 14:07:58', '2020-09-14 20:46:34', '更多的练习说出各种情绪。', 'WARM UP\r\nSING A SONG\r\nREVIEW NO.1-NO.10\r\nRULES\r\n\r\nGOOD\r\nT:I WANT TO GO TO SCHOOL(PRETENT TO SAY HI WITH CO-T,BUT NEARLLY TRIPPED BY A CHAIR)\r\nCO-T:HOW ARE YOU\r\nT:I’M GOOD!\r\nTHUMB TO KIDS,LET KIDS SAY:GOOD!\r\n\r\nHAPPY\r\nT SHOW THE EXAM PAPER OF OTHER SUBJECT(ON PPT ALSO)\r\nALL EXAM PAPER HAVE GOOD RESULT\r\nT SHOWS HAPPY\r\nT:I’M HAPPY\r\nSHOW HAPPY F/C LET’S SAY HAPPY\r\nPRETENT LAUGHTING LOUDLY\r\nLET STUDENT FOLLOW AND SAY HAPPY\r\n\r\nREVIEW GOOD-HAPPY 2 WORDS AGAIN\r\n\r\nEXCITED\r\nCO-T GIVE T A GIFT{BIG BOX GIFT}ON PPT\r\nCO-T:I HAVE A GIFT FOR U!U NEED TO FIND FROM THE ROOM!\r\nT PRETEND SO EXCITED\r\nT:OH!THANK YOU!\r\nT:I AM EXCITED \r\nSHOW F/C TO STUDENT AND LET THEM FOLLOW TEACHER TO SHOW EXCITED.\r\nRepeat SEVERL TIME\r\n\r\nSHOW GOOD HAPPY EXCITED\r\n\r\nSAD\r\nCO-T NOW U CAN OPEN UR GIFT BOX!\r\nT:OK!I’M SO EXCITED!\r\nWHEN T OPEN,INSID IS A EXAM PAPER BUT WITH BAD RESULT!\r\nCO-T USE HAMMER HIT T:U HAVE A BAD RESULT OF THE EXAM!\r\nT:OH I’M SO SAD!\r\nLET KIDS FOLLOW TEACHER PRETEND CRY AND SAY SAD!SAD!\r\n\r\nSENTENCE\r\nT:SHOW ME YOUR HANDS AND SAY\r\nHOW ARE U?\r\nI’M HAPPY/SAD/GOOD/EXCITED!\r\n\r\nGAME:T SHOW F/C WORDS, S ASK HOW ARE U and answer BY THEM SELF :I’M.....', '');
INSERT INTO `section` VALUES (21, 'KB1L4', 9, NULL, '2020-09-01 14:13:30', '2020-09-14 20:46:44', '1.复习L1-L3。\r\n2.做一个关于日常问候和年龄的对话。', 'T: Hello, How are you?\r\nS: I\'m excited/good/happy/sad. Thank you!( show the f/c of excited/good/happy/sad)\r\n   And you?\r\nT: I\'m excited/good/happy/sad.\r\n   What’s your name?\r\nS: I’m ...\r\nT: Hello Mr Star! How old are you?\r\nS: I’m...years old. (show the f/c of 1-10)\r\nT: What’s this?\r\nS: It’s a bag/ book/ chair.(show the f/c of bag/book/chair)\r\nT: Goodbye!\r\nS: Goodbye!\r\n', '');
INSERT INTO `section` VALUES (22, 'KB1L5', 0, NULL, '2020-09-01 14:17:31', '2020-09-14 20:46:53', '能够更多说出学习用品的名称。', 'Warm up\r\nBaby shark\r\nRules\r\n1.Hands on the desk\r\n2.Eyes on_\r\n3.Who can try? I can try!\r\nShow character\r\neraser 7minut\r\nCO-T\r\nRun into the classroom\r\nOh no, who can help me? It’s so dirty\r\nShow the drawing board with many spots(drawing board)\r\nT\r\nDon’t worry. Let me help you!\r\nShow the FC of eraser\r\nUse the eraser to rub the board\r\nSay eraser to children, everybody follow T, say eraser several times \r\nThe louder they say the word, the faster T erase the board. \r\n\r\nGAME\r\npencil 7minut\r\n\r\nCO-T\r\nPut the FC of pencil in a corner\r\nSay: I need to write my name!\r\n___, can you give me a pencil?\r\nT\r\nNo problem! Try to find the pencil\r\nOh no! Where is my pencil, it must in the classroom. \r\nWho can help me to find my pencil\r\nChoose one kid to find the FC\r\nUse a box to contain the pencil. And say pencil several times.\r\n\r\ndesk, table10 minut\r\nT\r\nNow we need put the eraser and pencil on somewhere(Such as windowsills,floor...)\r\nShow the PPT with some pictures.(Put the pencil and eraser in the wrong place.)\r\nAsk children and change the pictures to put those on the desk\r\nSay we put eraser and pencil on the desk\r\nEveryone follow T to say desk several times\r\nUse flash cards to display table and desk.\r\nI point to the...7minut\r\nThe foreign teacher and the Chinese teacher hold the flash card in their hands. Now they are on both sides of the classroom respectively. Choose two students to pk.\r\nT: I Point to the bag!I point to the book!\r\nTwo students reacted quickly, pointing the prop at the flash card.\r\nThen, The teacher hold flash cards\r\nS: Point to the bag!point to the book!\r\nTA do the actions.\r\n\r\nReview part 5minut\r\nThe teacher shows the flash card, the students read the words, the students say the words, the teacher does the actions, the teacher says the words, the students do the actions.', '');
INSERT INTO `section` VALUES (23, 'KB1L6', 9, NULL, '2020-09-01 14:30:33', '2020-09-14 20:47:03', '学会表达更多的颜色。', 'WARM-UP\r\nSing a song (5minuts)\r\nRULES\r\n1.Hands on the desk\r\n2.Eyes on ___\r\n3.Who can try? I can try!\r\nSHOW WORDS\r\nREVIEW\r\n\r\nKey words\r\neraser,pencil,desk,table\r\nKey sentence\r\nI point to the...\r\nYELLOW\r\nT:LOOK!I HAVE A MAGIC PEN!\r\nWHEN T SHAKE THE PEN,THE BOARD HAVE A YELLOW BLOT ,SHAKE 2.3TIMES\r\nSHOW F/C.IT’S YELLOW!\r\nGAME: LET KIDS SAY THE WORDS LOUDLY, WHO LOUDLY,LET THE KIDS SHAKE THE PEN\r\n\r\nRED\r\nT:OH LOOK WHAT I HAVE IN MY BOX!OPPS!SOMANY RED BALLS!LET’S SAY RED!RED!\r\nPRETEND TRIPPED BY UR FOOT,THE BALL FROM THE BOX WILL ALL SIPLLED ON THE GROUND\r\nT:WHO CAN HELP ME![SOME KIDS WOULD ALREDY PICK UP THE BALL,LET THEM HOLD A MOMENT ]\r\nGAME:LET STUDENT WHO HOLD THE BALL SAY RED AND THROW IT BACK IN THE BOX\r\nGAME2:GIVE THE BALL TO 4 MORE STUDENT WHO WANT TO PLAY THIS GAME,AND LET THEM SAY RED AND THROW THE BALL INTO THE BOX.\r\n\r\n\r\nPINK:\r\nUSE A  PINK BALLON\r\nLET KIDS SAY PINK AND BLOW IT\r\n\r\nGREEN\r\nPUT A GREEN TAIL ON THE BACK OF T\r\nT:I LOOSE ONE OF MY FRIEND!HIS NAME IS GREEN!WHERE IS HE? \r\nTURN AROUND AND AROUND!LET KIDS SAY ON YOUR BANCK[IN CHINESE]\r\nT:OH!U ARE HERE!EVERYONE LET’S SAY HELLO GREEN!\r\nGAME:JUMP AND SAY GREEN\r\n\r\nREVIEW GAME:\r\nPUT 4 COLORS ON THE PPT BOARD,T POINT TO WHICH COLOR, S NEED TO SAY THE NAME OF COLOR,WHICH GROUP LOUDLY,WHICH GROUP MORE STAR.\r\n\r\nSENTENCE\r\nWHAT COLOR IS IT?\r\nIT’S ...\r\nCLAP HANDS AND SAY SENTENCE.', '');
INSERT INTO `section` VALUES (24, 'KB1L7', 9, NULL, '2020-09-01 14:38:25', '2020-09-14 20:47:13', '认识更多的颜色。', 'Warm up\r\nSing a song\r\nRules\r\n1.Hands on the desk\r\n2.Eyes on_\r\n3.Who can try? I can try!\r\nReview\r\nred,yellow,pink,green\r\nTeacher show the flashcards, Students read the words.\r\n\r\nShow character\r\norange 6minutes\r\nPut orange paint on the top of the lid. Guess what color it is. Shake the bottle to make orange water.\r\nT: Wow!It turned into orange water!Orange!\r\nS: Orange!\r\npurple 6minutes\r\n\r\nUse the whiteboard software. Draw grapes with no color.\r\nT: What colour is it? Oh! It’s purple.\r\n(The foreign teacher painted a purple grape)\r\nT: Who can paint it? Who can try?\r\nS: I can try(The foreign teacher painted a purple grapes)\r\n\r\nBlue 6 minutes\r\nUse the whiteboard software.\r\nShow a beautiful landscape of white clouds and grass. The sky is colorless.\r\nT: What colour is the sky?(The teacher point the whiteboard and painted the sky blue.)\r\nT: Oh!It’s blue! Blue! Blue!\r\nS:Blue!\r\n\r\nRainbow 6minutes\r\nUse the whiteboard software. The teacher choose six students.Each person draws a curved line in a different color.\r\nT: Can you guess what this is? Wow! It’s rainbow!\r\nS: Rainbow!\r\nChoose another six students to randomly color on the whiteboard.\r\nT: Wow!It’s rainbow!\r\nS: Rainbow!\r\nWhat’s colour is it?  7minut\r\nIt’s...\r\nT: What colour is it?\r\nS: It’s red,yellow,pink,green,orange,purple,blue...\r\nGame:\r\nThe teacher has many different colored bars in his hand. I am a driver, now my car drive in front of who, answered my question, please follow me.\r\nT: What colour is it?\r\nS: It’s...\r\nThe teacher gave the students the stripes and the students followed.\r\nReview part 5minut\r\nShow the f/c,read the words.\r\n', '');
INSERT INTO `section` VALUES (26, 'KB1L8', 9, NULL, '2020-09-01 16:52:39', '2020-09-14 20:47:23', '1.复习L5-L7\r\n2.更多的练习关于学习用品。', 'REVIEW\r\n\r\nPPT with pencil, eraser, table and desk in different colors\r\n\r\nT\r\nWhat’s color it is? It’s red (kids follow)\r\nI point to the desk (kids follow)\r\nWow amazing, It’s a red desk.(PPT change)\r\n\r\nBlue pencil/orange table/green table/purple eraser/pink desk/yellow eraser/rainbow pencil\r\n\r\nPAPER\r\n1.A: Sam, Do you see my book? B: I put it on the table.\r\n2.A: Sam, my paper is so dirty. Can I use your eraser?\r\n3.A: What’s color is your pencil? B: It’s red.\r\n4.A: Sam, Do you see my book, it’s pink.\r\n5.A: now I want to draw with ,em a purple pencil.\r\n6.A: Amazing! look at the sky, it’s a rainbow!\r\n', '');
INSERT INTO `section` VALUES (27, 'KB1L9', 0, NULL, '2020-09-14 20:43:36', '2020-09-14 20:43:36', '', '', '');
INSERT INTO `section` VALUES (28, '1 grade 7 get ready for class L1', 0, NULL, '2020-09-16 10:15:23', '2020-09-16 10:15:23', '', '', '');
INSERT INTO `section` VALUES (29, '2 grade7 get ready for class lesson2', 0, NULL, '2020-09-16 10:17:13', '2020-09-16 10:17:13', '', '', '');
INSERT INTO `section` VALUES (30, '3 grade 7 get ready for class lesson3', 0, NULL, '2020-09-16 10:17:49', '2020-09-16 10:17:49', '', '', '');
INSERT INTO `section` VALUES (31, '4 grade 7 get ready for class lesson4', 0, NULL, '2020-09-16 10:18:22', '2020-09-16 10:18:22', '', '', '');
INSERT INTO `section` VALUES (32, '5 grade 7 family lesson1', 0, NULL, '2020-09-16 10:19:28', '2020-09-16 10:19:28', '', '', '');
INSERT INTO `section` VALUES (33, '6 grade 7 family lesson2', 0, NULL, '2020-09-16 10:20:07', '2020-09-16 10:20:07', '', '', '');
INSERT INTO `section` VALUES (34, '7 grade 7 family lesson3', 0, NULL, '2020-09-16 10:20:50', '2020-09-16 10:20:50', '', '', '');
INSERT INTO `section` VALUES (35, '8 grade 7 family lesson4', 0, NULL, '2020-09-16 10:21:21', '2020-09-16 10:21:33', '', '', '');
INSERT INTO `section` VALUES (36, '9 grade 7 home lesson1', 0, NULL, '2020-09-16 10:27:12', '2020-09-16 10:27:12', '', '', '');
INSERT INTO `section` VALUES (37, '10 grade7 home lesson2', 0, NULL, '2020-09-16 10:27:49', '2020-09-16 10:27:49', '', '', '');
INSERT INTO `section` VALUES (38, '11 grade 7 home lesson3', 0, NULL, '2020-09-16 10:28:36', '2020-09-16 10:28:36', '', '', '');
INSERT INTO `section` VALUES (39, '12 grade 7 home lesson4', 0, NULL, '2020-09-16 10:29:41', '2020-09-16 10:29:41', '', '', '');
INSERT INTO `section` VALUES (40, '13 grade7 a day lesson1', 0, NULL, '2020-09-16 10:30:52', '2020-09-16 10:30:52', '', '', '');
INSERT INTO `section` VALUES (41, '14 grade 7 a day lesson2', 0, NULL, '2020-09-16 10:31:22', '2020-09-16 10:31:22', '', '', '');
INSERT INTO `section` VALUES (42, '15 grade 7 a day lesson3', 0, NULL, '2020-09-16 10:31:52', '2020-09-16 10:31:52', '', '', '');
INSERT INTO `section` VALUES (43, '16 grade 7 a day lesson4', 0, NULL, '2020-09-16 10:32:30', '2020-09-16 10:32:30', '', '', '');
INSERT INTO `section` VALUES (44, '17 grade 7 in the city lesson1', 0, NULL, '2020-09-16 10:33:04', '2020-09-16 10:33:04', '', '', '');
INSERT INTO `section` VALUES (45, '18 grade 7 in th city lesson2', 0, NULL, '2020-09-16 10:33:34', '2020-09-16 10:33:34', '', '', '');
INSERT INTO `section` VALUES (46, '19 grade 7 in the city lesson3', 0, NULL, '2020-09-16 10:34:15', '2020-09-16 10:34:15', '', '', '');
INSERT INTO `section` VALUES (47, '20 grade7 in the city lesson4', 0, NULL, '2020-09-16 10:34:51', '2020-09-16 10:34:51', '', '', '');
INSERT INTO `section` VALUES (48, '21 grade 7 fit and well(my baby) lesson1', 0, NULL, '2020-09-16 10:35:43', '2020-09-16 10:35:43', '', '', '');
INSERT INTO `section` VALUES (49, '22 grade 7 fit and well(my baby) lesson2', 0, NULL, '2020-09-16 10:36:21', '2020-09-16 10:36:21', '', '', '');
INSERT INTO `section` VALUES (50, '23 grade 7 fit and well(my baby) lesson3', 0, NULL, '2020-09-16 10:37:09', '2020-09-16 10:37:09', '', '', '');
INSERT INTO `section` VALUES (51, '24 grade 7 fit and well(my baby) lesson 4', 0, NULL, '2020-09-16 10:37:50', '2020-09-16 10:37:50', '', '', '');
INSERT INTO `section` VALUES (52, '25 grade 7 country lesson1', 0, NULL, '2020-09-16 10:38:14', '2020-09-16 10:38:14', '', '', '');
INSERT INTO `section` VALUES (53, '26 grade 7 country lesson2', 0, NULL, '2020-09-16 10:38:53', '2020-09-16 10:38:53', '', '', '');
INSERT INTO `section` VALUES (54, '27 grade7  country lesson3', 0, NULL, '2020-09-16 10:39:34', '2020-09-16 10:39:34', '', '', '');
INSERT INTO `section` VALUES (55, '28 grade 7 country lesson4', 0, NULL, '2020-09-16 10:41:13', '2020-09-16 10:41:13', '', '', '');
INSERT INTO `section` VALUES (56, '29 grade 7 world of animals lesson1', 0, NULL, '2020-09-16 10:42:19', '2020-09-16 10:42:19', '', '', '');
INSERT INTO `section` VALUES (57, '30 grade 7 world of animals lesson 2', 0, NULL, '2020-09-16 10:43:02', '2020-09-16 10:43:02', '', '', '');
INSERT INTO `section` VALUES (58, '31 grade 7 world of animals lesson3', 0, NULL, '2020-09-16 10:43:34', '2020-09-16 10:43:34', '', '', '');
INSERT INTO `section` VALUES (59, '32 grade 7 world of animals lesson4', 0, NULL, '2020-09-16 10:44:39', '2020-09-16 10:44:39', '', '', '');

-- ----------------------------
-- Table structure for section_category
-- ----------------------------
DROP TABLE IF EXISTS `section_category`;
CREATE TABLE `section_category`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of section_category
-- ----------------------------
INSERT INTO `section_category` VALUES (1, 'KB0', 0);
INSERT INTO `section_category` VALUES (3, 'KB0 Unit1', 2);
INSERT INTO `section_category` VALUES (6, 'KB1 Unit2', 2);
INSERT INTO `section_category` VALUES (9, 'KB1', 0);

-- ----------------------------
-- Table structure for section_h5
-- ----------------------------
DROP TABLE IF EXISTS `section_h5`;
CREATE TABLE `section_h5`  (
  `section_id` int(11) NOT NULL,
  `h5_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of section_h5
-- ----------------------------

-- ----------------------------
-- Table structure for section_question
-- ----------------------------
DROP TABLE IF EXISTS `section_question`;
CREATE TABLE `section_question`  (
  `section_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of section_question
-- ----------------------------
INSERT INTO `section_question` VALUES (1, 3);
INSERT INTO `section_question` VALUES (4, 4);
INSERT INTO `section_question` VALUES (4, 5);
INSERT INTO `section_question` VALUES (3, 6);
INSERT INTO `section_question` VALUES (3, 7);
INSERT INTO `section_question` VALUES (3, 9);
INSERT INTO `section_question` VALUES (3, 10);
INSERT INTO `section_question` VALUES (4, 10);
INSERT INTO `section_question` VALUES (3, 11);
INSERT INTO `section_question` VALUES (4, 11);
INSERT INTO `section_question` VALUES (3, 12);
INSERT INTO `section_question` VALUES (9, 18);
INSERT INTO `section_question` VALUES (9, 19);
INSERT INTO `section_question` VALUES (9, 26);
INSERT INTO `section_question` VALUES (9, 27);
INSERT INTO `section_question` VALUES (9, 29);
INSERT INTO `section_question` VALUES (10, 40);
INSERT INTO `section_question` VALUES (11, 42);

-- ----------------------------
-- Table structure for section_test
-- ----------------------------
DROP TABLE IF EXISTS `section_test`;
CREATE TABLE `section_test`  (
  `section_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of section_test
-- ----------------------------
INSERT INTO `section_test` VALUES (10, 17);
INSERT INTO `section_test` VALUES (59, 19);

-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_log`;
CREATE TABLE `sms_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `user_id` int(50) NULL DEFAULT NULL COMMENT '会员名称',
  `mobile` bigint(15) UNSIGNED NOT NULL DEFAULT 0,
  `add_time` int(11) NOT NULL DEFAULT 0,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '短信内容',
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '是否发送成功 1 是 0 否',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `mobile`(`mobile`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 421 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sms_log
-- ----------------------------
INSERT INTO `sms_log` VALUES (420, NULL, 15666323771, 1600491578, '{\"code\":3365}', '123.233.17.86', 1);
INSERT INTO `sms_log` VALUES (419, NULL, 13604110536, 1600437202, '{\"code\":8736}', '42.84.38.39', 1);
INSERT INTO `sms_log` VALUES (418, NULL, 15666323771, 1600437082, '{\"code\":4519}', '123.233.17.86', 1);

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `delete_time` int(11) NULL DEFAULT NULL,
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '照片',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `is_adm` tinyint(1) NULL DEFAULT 0 COMMENT '是否是管理员',
  `login_time` datetime NULL DEFAULT NULL,
  `reg_time` datetime NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `login_num` int(11) NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '1 正常 0 离职',
  `gender` tinyint(1) NULL DEFAULT NULL,
  `mistake_times` tinyint(3) NULL DEFAULT 0,
  `sort_num` int(11) NULL DEFAULT 0,
  `manage_staff_leave` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `manage_staff_payout` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `wx_access_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '职员表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES (1, 'Edward', 'd93a5def7511da3d0f2d171d9c344e91', NULL, '五年工作经验擅长领域描述', 'http://sj.xinyangedu.com/up/attach/20200901/edb6962ba40faec06e82e2ba75d6c7f3.jpg', '123456', 0, '2020-09-27 08:41:50', NULL, '112.53.84.132', 9, 1, NULL, 0, NULL, NULL, NULL, 22);
INSERT INTO `staff` VALUES (2, 'Shannon', 'd93a5def7511da3d0f2d171d9c344e91', NULL, '三年工作经验擅长领域描述', 'http://sj.xinyangedu.com/up/attach/20200901/07c5e6dc30d4cdca09683c58f05356d1.jpg', '123456', 0, '2020-09-23 07:31:31', NULL, '117.136.78.250', 4, 1, NULL, 0, NULL, NULL, NULL, 21);
INSERT INTO `staff` VALUES (3, 'admin', '86a3a022ff928bbf51b7716b0a88229c', NULL, NULL, NULL, '15666320000', 0, '2020-09-23 07:18:03', NULL, '112.53.84.136', 159, 0, NULL, 5, 0, '1,2,3,4,5,6,7,8,9,10,11,12,13', '1,2,3,4,5,6,7,8,9,10,11,12,13', 15);
INSERT INTO `staff` VALUES (4, 'collin', '86a3a022ff928bbf51b7716b0a88229c', NULL, NULL, NULL, '15668680000', 0, '2020-09-29 14:22:36', NULL, '112.53.84.136', 34, 1, NULL, 0, 0, '1,2,3,4,5,6,7,8,9,11,10,12', '1,2,3,4,5,6,7,8,9,10,11,12', 16);
INSERT INTO `staff` VALUES (5, 'Monty', 'd93a5def7511da3d0f2d171d9c344e91', NULL, NULL, 'http://sj.xinyangedu.com/up/attach/20200901/178a923d0e0b2d0e9052ec7a61d9f70e.jpg', '123456', 0, NULL, NULL, NULL, NULL, 1, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `staff` VALUES (6, 'Daniel', 'd93a5def7511da3d0f2d171d9c344e91', NULL, NULL, 'http://sj.xinyangedu.com/up/attach/20200901/012f44961a1eaac3de31b94a67a6c03c.jpg', '123456', 0, NULL, NULL, NULL, NULL, 1, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `staff` VALUES (7, 'Lilly', 'd93a5def7511da3d0f2d171d9c344e91', NULL, NULL, 'http://sj.xinyangedu.com/up/attach/20200901/b0d8d59c5cc289ce2f358bd9632ab676.jpg', '123456', 0, NULL, NULL, NULL, NULL, 1, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `staff` VALUES (8, 'Susan', 'd93a5def7511da3d0f2d171d9c344e91', NULL, NULL, 'http://sj.xinyangedu.com/up/attach/20200901/146cf75bf7ab25c02cb897e156096150.jpg', '123456', 0, '2020-09-27 11:35:56', NULL, '112.53.84.132', 5, 1, NULL, 0, 0, '1,2,5,6,7,8,11,9', '1,2,5,6,7,8,9,11', 19);
INSERT INTO `staff` VALUES (9, 'Rico', 'd93a5def7511da3d0f2d171d9c344e91', NULL, NULL, NULL, '123456', 0, NULL, NULL, NULL, NULL, 1, NULL, 0, 0, NULL, NULL, NULL);
INSERT INTO `staff` VALUES (10, 'cui', 'c78b6663d47cfbdb4d65ea51c104044e', NULL, NULL, NULL, '15666323771', 0, '2020-10-27 17:29:26', NULL, '113.247.58.178', 242, 1, NULL, 0, 0, '1,3,5,4,2,6,8,7,10,11,9,12,13', '1,2,4,3,5,6,7,8,9,10,11,12,13', NULL);
INSERT INTO `staff` VALUES (11, '刘红', 'd93a5def7511da3d0f2d171d9c344e91', NULL, NULL, NULL, '123456', 0, '2020-09-24 07:43:17', NULL, '111.35.18.233', 9, 1, NULL, 0, 0, NULL, NULL, 23);
INSERT INTO `staff` VALUES (12, '韩晓燕', 'eb3028ad37e04b17e65b94111d3c437e', NULL, NULL, NULL, '', 0, '2020-09-19 17:26:37', NULL, '112.253.25.52', 2, 1, NULL, 1, 0, '1,2,3,4,5,6,7,8,9,10,11,12', '1,2,3,4,5,6,7,8,9,10,11,12', 20);
INSERT INTO `staff` VALUES (13, 'Lane', 'd93a5def7511da3d0f2d171d9c344e91', NULL, NULL, NULL, '', 0, '2020-09-21 07:26:02', NULL, '112.53.84.135', 1, 1, NULL, 0, 0, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for staff_offduty
-- ----------------------------
DROP TABLE IF EXISTS `staff_offduty`;
CREATE TABLE `staff_offduty`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `staff_id` int(11) NOT NULL COMMENT '0表示全体休息',
  `type_id` tinyint(1) NULL DEFAULT NULL COMMENT '1事假 2病假 3串休',
  `start_date` date NOT NULL,
  `start_date_time` time NULL DEFAULT NULL,
  `end_date` date NULL DEFAULT NULL,
  `end_date_time` time NULL DEFAULT NULL,
  `edit_time` datetime NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '-1驳回 1 成功 0 申请中',
  `reason` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `verifier_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人',
  `verify_time` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `verify_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核说明',
  `delete_time` int(11) NULL DEFAULT NULL,
  `images` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `staff_id`(`staff_id`, `start_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工请假' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of staff_offduty
-- ----------------------------
INSERT INTO `staff_offduty` VALUES (21, 10, 1, '2020-10-15', '00:00:00', '2020-10-31', '00:00:00', NULL, '2020-10-12 09:37:32', 1, '回老家', 'cui', '2020-10-12 09:37:42', '', NULL, '');
INSERT INTO `staff_offduty` VALUES (22, 10, 2, '2020-10-13', '00:00:00', '2020-10-14', '00:00:00', NULL, '2020-10-12 09:38:44', -1, '牙疼', 'cui', '2020-10-12 11:31:24', '', NULL, '');

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  `deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '门店表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES (1, '高新万达店', '地址如是', NULL, 0);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `point` int(10) NULL DEFAULT 0,
  `clazz_id` int(11) NULL DEFAULT NULL,
  `gender` tinyint(4) NULL DEFAULT 0,
  `status` tinyint(4) NULL DEFAULT 1,
  `delete_time` datetime NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '崔先生', NULL, '15666320000', 0, 1, 1, 1, NULL, NULL);
INSERT INTO `student` VALUES (2, '杨先生', NULL, '15668680000', 0, 1, 1, 1, '0000-00-00 00:00:00', '2020-09-28 20:55:43');
INSERT INTO `student` VALUES (3, '1', NULL, '1', 0, 1, 0, 1, NULL, '2020-10-14 10:11:41');
INSERT INTO `student` VALUES (4, 'w', NULL, 'w', 0, 1, 0, 1, NULL, '2020-10-14 16:29:39');

-- ----------------------------
-- Table structure for student_account
-- ----------------------------
DROP TABLE IF EXISTS `student_account`;
CREATE TABLE `student_account`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `student_id` int(10) NOT NULL,
  `balance` decimal(8, 2) UNSIGNED NULL DEFAULT 0.00 COMMENT '虚拟余额',
  `point` int(11) NULL DEFAULT 0 COMMENT '积分',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uid`(`student_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student_account
-- ----------------------------
INSERT INTO `student_account` VALUES (1, 10003, 0.00, 25);
INSERT INTO `student_account` VALUES (2, 10004, 0.00, 15);

-- ----------------------------
-- Table structure for student_account_log
-- ----------------------------
DROP TABLE IF EXISTS `student_account_log`;
CREATE TABLE `student_account_log`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `student_id` int(10) NOT NULL,
  `type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1 余额记录  2 积分记录',
  `stage` tinyint(1) NULL DEFAULT NULL,
  `amount` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT '金额',
  `remaining_point` int(11) NULL DEFAULT 0 COMMENT '剩余积分',
  `add_time` datetime NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`student_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student_account_log
-- ----------------------------
INSERT INTO `student_account_log` VALUES (11, 2, 2, NULL, 2.00, 18, '2020-08-30 16:55:49', '心情好');
INSERT INTO `student_account_log` VALUES (12, 2, 2, NULL, -1.00, 17, '2020-08-30 16:56:14', '减去测试');
INSERT INTO `student_account_log` VALUES (13, 1, 2, NULL, -1.00, 0, '2020-09-02 14:10:52', '慷慨解囊看');
INSERT INTO `student_account_log` VALUES (14, 1, 2, NULL, 5.00, 5, '2020-09-02 14:11:03', '看见你');
INSERT INTO `student_account_log` VALUES (15, 1, 2, NULL, 9.00, 14, '2020-09-02 14:11:15', '开不开机了');
INSERT INTO `student_account_log` VALUES (16, 1, 2, NULL, -3.00, 11, '2020-09-02 14:11:27', 'i哦离开；，乱码');
INSERT INTO `student_account_log` VALUES (17, 10004, 2, NULL, 1.00, 15, '2020-09-17 09:44:43', '1');
INSERT INTO `student_account_log` VALUES (18, 10004, 2, NULL, 1.00, 16, '2020-09-17 09:45:05', '1');
INSERT INTO `student_account_log` VALUES (19, 10004, 2, NULL, -1.00, 15, '2020-09-17 09:45:14', '1');
INSERT INTO `student_account_log` VALUES (20, 10003, 2, NULL, 2.00, 13, '2020-09-17 09:55:07', '作业点评');
INSERT INTO `student_account_log` VALUES (21, 10003, 2, NULL, 5.00, 18, '2020-09-18 10:58:07', '作业点评');
INSERT INTO `student_account_log` VALUES (22, 10003, 2, NULL, 2.00, 20, '2020-09-18 11:11:39', '作业点评');
INSERT INTO `student_account_log` VALUES (23, 10003, 2, NULL, 5.00, 25, '2020-10-20 17:16:55', '作业点评');

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作内容',
  `add_time` datetime NULL DEFAULT NULL COMMENT '发生时间',
  `login_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作人姓名',
  `login_id` mediumint(8) UNSIGNED NOT NULL DEFAULT 0 COMMENT '操作人ID',
  `ip` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'IP',
  `path` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'act&op',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4497 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_log
-- ----------------------------
INSERT INTO `system_log` VALUES (3157, '[发送消息成功] [MODEL] app\\backend\\model\\MessageModel', '2020-03-29 20:50:39', 'admin', 1, '123.233.73.24', 'backend/article/sendMessage');
INSERT INTO `system_log` VALUES (3158, '[发送消息成功] [MODEL] app\\backend\\model\\MessageModel', '2020-03-29 20:51:12', 'admin', 1, '123.233.73.24', 'backend/article/sendMessage');
INSERT INTO `system_log` VALUES (3159, '[发送消息成功] [MODEL] app\\backend\\model\\MessageModel', '2020-03-29 20:52:01', 'admin', 1, '123.233.73.24', 'backend/article/sendMessage');
INSERT INTO `system_log` VALUES (3160, '[修改消息模板成功] [MODEL] app\\backend\\model\\MessageTemplateModel', '2020-03-29 21:20:25', 'admin', 1, '123.233.73.24', 'backend/article/createMsgTemplate');
INSERT INTO `system_log` VALUES (3161, '[添加消息模板成功] [MODEL] app\\backend\\model\\MessageTemplateModel', '2020-03-29 21:22:17', 'admin', 1, '123.233.73.24', 'backend/article/createMsgTemplate');
INSERT INTO `system_log` VALUES (3162, '[修改消息模板成功] [MODEL] app\\backend\\model\\MessageTemplateModel', '2020-03-29 21:22:22', 'admin', 1, '123.233.73.24', 'backend/article/createMsgTemplate');
INSERT INTO `system_log` VALUES (3163, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-03-29 21:53:38', 'admin', 1, '123.233.73.24', 'backend/article/createnav/position/createNav');
INSERT INTO `system_log` VALUES (3164, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-03-29 21:55:01', 'admin', 1, '123.233.73.24', 'backend/article/createnav/position/createNav');
INSERT INTO `system_log` VALUES (3165, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-03-29 21:56:36', 'admin', 1, '123.233.73.24', 'backend/article/createnav/position/top');
INSERT INTO `system_log` VALUES (3166, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-03-29 22:00:52', 'admin', 1, '123.233.73.24', 'backend/article/createnav/position/bottom');
INSERT INTO `system_log` VALUES (3167, '[登录成功]', '2020-03-30 14:04:34', 'admin', 1, '60.216.176.160', 'backend/login/login');
INSERT INTO `system_log` VALUES (3168, '[登录成功]', '2020-03-31 22:00:49', 'admin', 1, '123.233.73.24', 'backend/login/login');
INSERT INTO `system_log` VALUES (3169, '[登录成功]', '2020-03-31 22:25:32', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3170, '[登录成功]', '2020-04-01 14:56:57', 'admin', 1, '60.216.176.160', 'backend/login/login');
INSERT INTO `system_log` VALUES (3171, '[登录成功]', '2020-04-01 22:31:16', 'admin', 1, '116.3.199.13', 'backend/login/login');
INSERT INTO `system_log` VALUES (3172, '[登录成功]', '2020-04-02 16:57:20', 'admin', 1, '60.216.176.160', 'backend/login/login');
INSERT INTO `system_log` VALUES (3173, '[登录成功]', '2020-04-02 21:06:13', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3174, '[登录成功]', '2020-04-04 15:41:01', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3175, '[登录成功]', '2020-04-04 15:41:09', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3176, '[登录成功]', '2020-04-04 15:42:01', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3177, '[登录成功]', '2020-04-04 15:42:17', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3178, '[登录成功]', '2020-04-04 15:42:19', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3179, '[登录成功]', '2020-04-04 15:42:23', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3180, '[登录成功]', '2020-04-04 15:42:26', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3181, '[登录成功]', '2020-04-04 15:42:47', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3182, '[登录成功]', '2020-04-04 15:43:44', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3183, '[登录成功]', '2020-04-04 15:44:29', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3184, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-04-04 15:51:13', 'admin', 1, '39.82.104.62', 'backend/course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3185, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-04-04 15:53:41', 'admin', 1, '39.82.104.62', 'backend/course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3186, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-04-04 15:53:56', 'admin', 1, '39.82.104.62', 'backend/course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3187, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-04-04 15:54:13', 'admin', 1, '39.82.104.62', 'backend/course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3188, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-04-04 15:56:42', 'admin', 1, '39.82.104.62', 'backend/course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3189, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-04-04 15:56:54', 'admin', 1, '39.82.104.62', 'backend/course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3190, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-04-04 15:57:12', 'admin', 1, '39.82.104.62', 'backend/course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3191, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-04-04 15:59:08', 'admin', 1, '39.82.104.62', 'backend/course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3192, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-04-04 15:59:22', 'admin', 1, '39.82.104.62', 'backend/course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3193, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-04-04 15:59:35', 'admin', 1, '39.82.104.62', 'backend/course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3194, '[修改学员信息成功] [MODEL] app\\backend\\model\\UserModel', '2020-04-04 16:37:06', 'admin', 1, '39.82.104.62', 'backend/user/saveUser');
INSERT INTO `system_log` VALUES (3195, '[登录成功]', '2020-04-04 17:44:36', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3196, '[登录成功]', '2020-04-05 09:16:06', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3197, '[登录成功]', '2020-04-05 11:31:36', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3198, '[修改试卷成功] [MODEL] app\\backend\\model\\ExamPaperModel', '2020-04-05 11:39:19', 'admin', 1, '39.82.104.62', 'backend/exam/createPaper');
INSERT INTO `system_log` VALUES (3199, '[修改试卷成功] [MODEL] app\\backend\\model\\ExamPaperModel', '2020-04-05 11:54:07', 'admin', 1, '39.82.104.62', 'backend/exam/createPaper');
INSERT INTO `system_log` VALUES (3200, '[添加试卷成功] [MODEL] app\\backend\\model\\ExamPaperModel', '2020-04-05 11:55:37', 'admin', 1, '39.82.104.62', 'backend/exam/createPaper');
INSERT INTO `system_log` VALUES (3201, '[修改试卷成功] [MODEL] app\\backend\\model\\ExamPaperModel', '2020-04-05 12:00:19', 'admin', 1, '39.82.104.62', 'backend/exam/createPaper');
INSERT INTO `system_log` VALUES (3202, '[登录成功]', '2020-04-05 16:44:59', 'admin', 1, '39.82.104.62', 'backend/login/login');
INSERT INTO `system_log` VALUES (3203, '[登录成功]', '2020-04-05 20:52:30', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3204, '[登录成功]', '2020-04-06 08:29:46', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3205, '[添加试题选项成功] [MODEL] app\\backend\\model\\ExamQuestionOptionModel', '2020-04-06 08:43:03', 'admin', 1, '112.230.110.244', 'backend/exam/createquestionoption/question_id/2');
INSERT INTO `system_log` VALUES (3206, '[添加试题选项成功] [MODEL] app\\backend\\model\\ExamQuestionOptionModel', '2020-04-06 08:43:14', 'admin', 1, '112.230.110.244', 'backend/exam/createquestionoption/question_id/2');
INSERT INTO `system_log` VALUES (3207, '[添加试题选项成功] [MODEL] app\\backend\\model\\ExamQuestionOptionModel', '2020-04-06 08:43:21', 'admin', 1, '112.230.110.244', 'backend/exam/createquestionoption/question_id/2');
INSERT INTO `system_log` VALUES (3208, '[添加试题选项成功] [MODEL] app\\backend\\model\\ExamQuestionOptionModel', '2020-04-06 08:43:28', 'admin', 1, '112.230.110.244', 'backend/exam/createquestionoption/question_id/2');
INSERT INTO `system_log` VALUES (3209, '[添加试题选项成功] [MODEL] app\\backend\\model\\ExamQuestionOptionModel', '2020-04-06 08:51:36', 'admin', 1, '112.230.110.244', 'backend/exam/createquestionoption/question_id/1');
INSERT INTO `system_log` VALUES (3210, '[添加试题选项成功] [MODEL] app\\backend\\model\\ExamQuestionOptionModel', '2020-04-06 08:51:48', 'admin', 1, '112.230.110.244', 'backend/exam/createquestionoption/question_id/1');
INSERT INTO `system_log` VALUES (3211, '[添加试题选项成功] [MODEL] app\\backend\\model\\ExamQuestionOptionModel', '2020-04-06 08:52:03', 'admin', 1, '112.230.110.244', 'backend/exam/createquestionoption/question_id/1');
INSERT INTO `system_log` VALUES (3212, '[添加试题选项成功] [MODEL] app\\backend\\model\\ExamQuestionOptionModel', '2020-04-06 08:52:10', 'admin', 1, '112.230.110.244', 'backend/exam/createquestionoption/question_id/1');
INSERT INTO `system_log` VALUES (3213, '[修改文章成功] [MODEL] app\\backend\\model\\ExamQuestionBodyModel', '2020-04-06 09:59:05', 'admin', 1, '112.230.110.244', 'backend/exam/createBody');
INSERT INTO `system_log` VALUES (3214, '[修改试题成功] [MODEL] app\\backend\\model\\ExamQuestionModel', '2020-04-06 09:59:17', 'admin', 1, '112.230.110.244', 'backend/exam/createQuestion');
INSERT INTO `system_log` VALUES (3215, '[修改试题成功] [MODEL] app\\backend\\model\\ExamQuestionModel', '2020-04-06 10:29:56', 'admin', 1, '112.230.110.244', 'backend/exam/createQuestion');
INSERT INTO `system_log` VALUES (3216, '[修改试题成功] [MODEL] app\\backend\\model\\ExamQuestionModel', '2020-04-06 10:30:25', 'admin', 1, '112.230.110.244', 'backend/exam/createQuestion');
INSERT INTO `system_log` VALUES (3217, '[登录成功]', '2020-04-06 16:45:53', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3218, '[登录成功]', '2020-04-08 20:44:58', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3219, '[登录成功]', '2020-04-08 20:46:37', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3220, '[登录成功]', '2020-04-08 20:48:04', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3221, '[登录成功]', '2020-04-08 20:50:25', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3222, '[登录成功]', '2020-04-08 20:50:50', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3223, '[登录成功]', '2020-04-08 20:51:22', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3224, '[登录成功]', '2020-04-08 20:52:34', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3225, '[登录成功]', '2020-04-08 20:53:08', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3226, '[登录成功]', '2020-04-08 20:53:22', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3227, '[登录成功]', '2020-04-08 20:53:39', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3228, '[登录成功]', '2020-04-08 20:54:26', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3229, '[登录成功]', '2020-04-08 20:54:32', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3230, '[登录成功]', '2020-04-09 14:06:47', 'admin', 1, '60.216.176.160', 'backend/login/login');
INSERT INTO `system_log` VALUES (3231, '[登录成功]', '2020-04-09 14:06:58', 'admin', 1, '60.216.176.160', 'backend/login/login');
INSERT INTO `system_log` VALUES (3232, '[登录成功]', '2020-04-09 21:38:34', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3233, '[登录成功]', '2020-04-09 21:38:48', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3234, '[登录成功]', '2020-04-09 21:39:05', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3235, '[登录成功]', '2020-04-09 21:42:48', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3236, '[登录成功]', '2020-04-10 17:22:12', 'admin', 1, '60.216.176.160', 'backend/login/login');
INSERT INTO `system_log` VALUES (3237, '[登录成功]', '2020-04-10 20:45:12', 'admin', 1, '112.230.110.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (3238, '[修改设置]', '2020-04-10 22:04:29', 'admin', 1, '112.230.110.244', 'backend/system/Config');
INSERT INTO `system_log` VALUES (3239, '[修改设置]', '2020-04-10 22:05:40', 'admin', 1, '112.230.110.244', 'backend/system/Config');
INSERT INTO `system_log` VALUES (3240, '[修改设置]', '2020-04-10 22:16:02', 'admin', 1, '112.230.110.244', 'backend/article/segment');
INSERT INTO `system_log` VALUES (3241, '[修改设置]', '2020-04-10 22:16:07', 'admin', 1, '112.230.110.244', 'backend/article/segment');
INSERT INTO `system_log` VALUES (3242, '[修改设置]', '2020-04-10 22:17:05', 'admin', 1, '112.230.110.244', 'backend/article/segment');
INSERT INTO `system_log` VALUES (3243, '[修改设置]', '2020-04-10 22:17:47', 'admin', 1, '112.230.110.244', 'backend/article/segment');
INSERT INTO `system_log` VALUES (3244, '[修改设置]', '2020-04-10 22:17:58', 'admin', 1, '112.230.110.244', 'backend/article/segment');
INSERT INTO `system_log` VALUES (3245, '[修改设置]', '2020-04-10 22:24:17', 'admin', 1, '112.230.110.244', 'backend/article/segment');
INSERT INTO `system_log` VALUES (3246, '[修改设置]', '2020-04-10 22:25:24', 'admin', 1, '112.230.110.244', 'backend/article/segment');
INSERT INTO `system_log` VALUES (3247, '[修改设置]', '2020-04-10 22:25:46', 'admin', 1, '112.230.110.244', 'backend/article/segment');
INSERT INTO `system_log` VALUES (3248, '[修改设置]', '2020-04-10 22:26:48', 'admin', 1, '112.230.110.244', 'backend/article/segment/group/ui_text');
INSERT INTO `system_log` VALUES (3249, '[修改设置]', '2020-04-10 22:27:17', 'admin', 1, '112.230.110.244', 'backend/article/segment/group/ui_text');
INSERT INTO `system_log` VALUES (3250, '[修改设置]', '2020-04-10 22:31:54', 'admin', 1, '112.230.110.244', 'backend/article/segment/group/ui_text');
INSERT INTO `system_log` VALUES (3251, '[修改设置]', '2020-04-10 22:33:15', 'admin', 1, '112.230.110.244', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3252, '[修改设置]', '2020-04-10 22:33:22', 'admin', 1, '112.230.110.244', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3253, '[修改设置]', '2020-04-10 22:33:32', 'admin', 1, '112.230.110.244', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3254, '[修改设置]', '2020-04-10 22:33:52', 'admin', 1, '112.230.110.244', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3255, '[登录成功]', '2020-04-11 10:11:35', 'admin', 1, '60.216.176.160', 'backend/login/login');
INSERT INTO `system_log` VALUES (3256, '[修改设置]', '2020-04-11 10:27:06', 'admin', 1, '60.216.176.160', 'backend/article/segment/group/ui_text');
INSERT INTO `system_log` VALUES (3257, '[修改设置]', '2020-04-11 10:31:36', 'admin', 1, '60.216.176.160', 'backend/article/segment/group/ui_text');
INSERT INTO `system_log` VALUES (3258, '[修改导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-11 10:39:55', 'admin', 1, '60.216.176.160', 'backend/article/createnav/position/bottom');
INSERT INTO `system_log` VALUES (3259, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-11 10:42:08', 'admin', 1, '60.216.176.160', 'backend/article/createnav/position/bottom');
INSERT INTO `system_log` VALUES (3260, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-11 10:55:55', 'admin', 1, '60.216.176.160', 'backend/article/createnav/position/bottom');
INSERT INTO `system_log` VALUES (3261, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-11 10:56:36', 'admin', 1, '60.216.176.160', 'backend/article/createnav/position/bottom');
INSERT INTO `system_log` VALUES (3262, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-11 10:57:06', 'admin', 1, '60.216.176.160', 'backend/article/createnav/position/bottom');
INSERT INTO `system_log` VALUES (3263, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-11 10:57:35', 'admin', 1, '60.216.176.160', 'backend/article/createnav/position/bottom');
INSERT INTO `system_log` VALUES (3264, '[修改导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-11 10:57:43', 'admin', 1, '60.216.176.160', 'backend/article/createnav/position/bottom');
INSERT INTO `system_log` VALUES (3265, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-11 10:58:05', 'admin', 1, '60.216.176.160', 'backend/article/createnav/position/bottom');
INSERT INTO `system_log` VALUES (3266, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-11 10:58:52', 'admin', 1, '60.216.176.160', 'backend/article/createnav/position/bottom');
INSERT INTO `system_log` VALUES (3267, '[登录成功]', '2020-04-11 13:35:24', 'admin', 1, '60.216.176.160', 'backend/login/login');
INSERT INTO `system_log` VALUES (3268, '[修改设置]', '2020-04-11 13:35:35', 'admin', 1, '60.216.176.160', 'backend/article/segment/group/ui_text');
INSERT INTO `system_log` VALUES (3269, '[登录成功]', '2020-04-11 14:09:56', 'admin', 1, '60.216.176.160', 'backend/login/login');
INSERT INTO `system_log` VALUES (3270, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-04-11 14:12:14', 'admin', 1, '60.216.176.160', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3271, '[添加广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-04-11 14:12:37', 'admin', 1, '60.216.176.160', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3272, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-04-11 14:12:42', 'admin', 1, '60.216.176.160', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3273, '[添加广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-04-11 14:12:57', 'admin', 1, '60.216.176.160', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3274, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-04-11 14:17:45', 'admin', 1, '60.216.176.160', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3275, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-04-11 14:19:04', 'admin', 1, '60.216.176.160', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3276, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-04-11 14:19:04', 'admin', 1, '60.216.176.160', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3277, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-04-11 16:24:02', 'admin', 1, '60.216.176.160', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3278, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-04-11 16:26:36', 'admin', 1, '60.216.176.160', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3279, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-04-11 16:27:13', 'admin', 1, '60.216.176.160', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3280, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-04-11 16:30:18', 'admin', 1, '60.216.176.160', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3281, '[登录成功]', '2020-04-17 14:36:52', 'admin', 1, '39.82.242.172', 'backend/login/login');
INSERT INTO `system_log` VALUES (3282, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-04-17 14:41:26', 'admin', 1, '39.82.242.172', 'backend/course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3283, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-04-17 15:22:16', 'admin', 1, '39.82.242.172', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3284, '[登录成功]', '2020-04-21 16:33:54', 'admin', 1, '42.84.43.84', 'backend/login/login');
INSERT INTO `system_log` VALUES (3285, '[登录成功]', '2020-04-21 16:33:58', 'admin', 1, '39.71.120.237', 'backend/login/login');
INSERT INTO `system_log` VALUES (3286, '[登录成功]', '2020-04-21 16:34:10', 'admin', 1, '42.84.43.84', 'backend/login/login');
INSERT INTO `system_log` VALUES (3287, '[登录成功]', '2020-04-22 21:51:06', 'admin', 1, '123.233.73.54', 'backend/login/login');
INSERT INTO `system_log` VALUES (3288, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-22 22:05:10', 'admin', 1, '123.233.73.54', 'backend/article/createnav/position/home');
INSERT INTO `system_log` VALUES (3289, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-22 22:05:20', 'admin', 1, '123.233.73.54', 'backend/article/createnav/position/home');
INSERT INTO `system_log` VALUES (3290, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-22 22:05:38', 'admin', 1, '123.233.73.54', 'backend/article/createnav/position/home');
INSERT INTO `system_log` VALUES (3291, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-22 22:06:07', 'admin', 1, '123.233.73.54', 'backend/article/createnav/position/home');
INSERT INTO `system_log` VALUES (3292, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-22 22:06:23', 'admin', 1, '123.233.73.54', 'backend/article/createnav/position/home');
INSERT INTO `system_log` VALUES (3293, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-22 22:06:40', 'admin', 1, '123.233.73.54', 'backend/article/createnav/position/home');
INSERT INTO `system_log` VALUES (3294, '[添加导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-04-22 22:06:55', 'admin', 1, '123.233.73.54', 'backend/article/createnav/position/home');
INSERT INTO `system_log` VALUES (3295, '[登录成功]', '2020-04-25 10:11:28', 'admin', 1, '42.84.45.143', 'backend/login/login');
INSERT INTO `system_log` VALUES (3296, '[登录成功]', '2020-04-28 11:46:59', 'admin', 1, '222.135.231.229', 'backend/login/login');
INSERT INTO `system_log` VALUES (3297, '[登录成功]', '2020-04-29 14:22:38', 'admin', 1, '175.162.3.98', 'backend/login/login');
INSERT INTO `system_log` VALUES (3298, '[登录成功]', '2020-05-15 13:34:57', 'admin', 1, '39.82.168.80', 'backend/login/login');
INSERT INTO `system_log` VALUES (3299, '[登录成功]', '2020-05-23 10:35:59', 'admin', 1, '39.71.122.43', 'backend/login/login');
INSERT INTO `system_log` VALUES (3300, '[登录成功]', '2020-05-24 11:49:44', 'admin', 1, '60.216.126.165', 'backend/login/login');
INSERT INTO `system_log` VALUES (3301, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-05-24 11:59:46', 'admin', 1, '60.216.126.165', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3302, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-05-24 11:59:54', 'admin', 1, '60.216.126.165', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3303, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-05-24 12:04:25', 'admin', 1, '60.216.126.165', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3304, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-05-24 12:06:25', 'admin', 1, '60.216.126.165', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3305, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-05-24 12:07:20', 'admin', 1, '60.216.126.165', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3306, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-05-24 12:07:29', 'admin', 1, '60.216.126.165', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3307, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-05-24 12:07:43', 'admin', 1, '60.216.126.165', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3308, '[修改设置]', '2020-05-24 12:59:31', 'admin', 1, '60.216.126.165', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3309, '[修改设置]', '2020-05-24 13:05:49', 'admin', 1, '60.216.126.165', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3310, '[修改设置]', '2020-05-24 13:07:24', 'admin', 1, '60.216.126.165', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3311, '[修改设置]', '2020-05-24 13:40:55', 'admin', 1, '60.216.126.165', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3312, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-05-24 18:14:34', 'admin', 1, '60.216.126.165', 'backend/article/createAdvertisement');
INSERT INTO `system_log` VALUES (3313, '[登录成功]', '2020-05-27 10:18:29', 'admin', 1, '39.71.122.43', 'backend/login/login');
INSERT INTO `system_log` VALUES (3314, '[修改设置]', '2020-05-27 10:19:53', 'admin', 1, '39.71.122.43', 'backend/article/segment/group/about');
INSERT INTO `system_log` VALUES (3315, '[登录成功]', '2020-05-27 13:59:58', 'admin', 1, '39.71.122.43', 'backend/login/login');
INSERT INTO `system_log` VALUES (3316, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 15:27:05', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/18');
INSERT INTO `system_log` VALUES (3317, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 15:27:48', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/18');
INSERT INTO `system_log` VALUES (3318, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 15:28:04', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/18');
INSERT INTO `system_log` VALUES (3319, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 15:28:15', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/18');
INSERT INTO `system_log` VALUES (3320, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 15:29:04', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/18');
INSERT INTO `system_log` VALUES (3321, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 16:16:04', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3322, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 16:18:20', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3323, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 16:54:02', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3324, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 16:54:58', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3325, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 16:55:22', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3326, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 16:56:09', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3327, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 16:57:04', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3328, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-27 16:58:30', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3329, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 15:26:32', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3330, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 15:27:29', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3331, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 15:27:41', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3332, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 15:41:50', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3333, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 15:46:19', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3334, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 16:13:40', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3335, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 16:19:25', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3336, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 16:21:07', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3337, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 16:37:24', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3338, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 16:48:57', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3339, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 16:53:15', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/22');
INSERT INTO `system_log` VALUES (3340, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 16:55:56', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/22');
INSERT INTO `system_log` VALUES (3341, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 17:06:46', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3342, '[设置章节关联视频成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 17:10:24', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/1/section_id/1/course_id/23');
INSERT INTO `system_log` VALUES (3343, '[设置章节关联视频成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 17:10:45', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/1/section_id/1/course_id/23');
INSERT INTO `system_log` VALUES (3344, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 17:23:26', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3345, '[设置章节关联视频成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 17:23:27', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/1/section_id/1/course_id/23');
INSERT INTO `system_log` VALUES (3346, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 17:23:31', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3347, '[设置章节关联视频成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-28 17:23:33', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/1/section_id/2/course_id/23');
INSERT INTO `system_log` VALUES (3348, '[修改设置]', '2020-05-28 19:57:11', 'admin', 1, '39.71.122.43', 'backend/system/config/group/ccapi');
INSERT INTO `system_log` VALUES (3349, '[登录成功]', '2020-05-28 22:06:49', 'admin', 1, '60.216.126.165', 'backend/login/login');
INSERT INTO `system_log` VALUES (3350, '[修改设置]', '2020-05-29 09:15:26', 'admin', 1, '39.71.122.43', 'backend/system/config/group/ccapi');
INSERT INTO `system_log` VALUES (3351, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:01:51', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3352, '[设置章节关联视频成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:01:54', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/1/section_id/1/course_id/23');
INSERT INTO `system_log` VALUES (3353, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:02:47', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3354, '[设置章节关联视频成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:02:54', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/1/section_id/2/course_id/23');
INSERT INTO `system_log` VALUES (3355, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:13:06', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3356, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:14:22', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3357, '[添加课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:14:29', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3358, '[添加课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:14:39', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3359, '[添加课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:15:12', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3360, '[添加课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:16:56', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3361, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:17:08', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3362, '[添加课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:17:31', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3363, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:18:00', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3364, '[添加课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:19:48', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3365, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:44:05', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3366, '[设置章节关联试题成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:44:07', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/4/section_id/4/course_id/23');
INSERT INTO `system_log` VALUES (3367, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:44:28', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3368, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:45:19', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3369, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:47:13', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3370, '[设置章节关联试题成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:47:14', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/4/section_id/4/course_id/23');
INSERT INTO `system_log` VALUES (3371, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 15:47:51', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3372, '[修改设置]', '2020-05-29 16:34:35', 'admin', 1, '39.71.122.43', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3373, '[修改设置]', '2020-05-29 16:35:41', 'admin', 1, '39.71.122.43', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3374, '[修改设置]', '2020-05-29 16:47:11', 'admin', 1, '39.71.122.43', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3375, '[修改设置]', '2020-05-29 16:47:34', 'admin', 1, '39.71.122.43', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3376, '[修改设置]', '2020-05-29 16:50:54', 'admin', 1, '39.71.122.43', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3377, '[修改设置]', '2020-05-29 16:52:56', 'admin', 1, '39.71.122.43', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3378, '[修改设置]', '2020-05-29 16:53:58', 'admin', 1, '39.71.122.43', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3379, '[修改设置]', '2020-05-29 16:59:57', 'admin', 1, '39.71.122.43', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3380, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 17:35:30', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3381, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 17:48:15', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3382, '[修改设置]', '2020-05-29 17:52:10', 'admin', 1, '39.71.122.43', 'backend/system/config/group/ccapi');
INSERT INTO `system_log` VALUES (3383, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 17:53:41', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3384, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 17:58:11', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3385, '[添加直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-29 18:09:50', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/3/course_id/23');
INSERT INTO `system_log` VALUES (3386, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:11:26', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3387, '[添加直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-29 18:11:33', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/3/course_id/23');
INSERT INTO `system_log` VALUES (3388, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:12:19', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3389, '[添加直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-29 18:12:25', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/3/course_id/23');
INSERT INTO `system_log` VALUES (3390, '[添加直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-29 18:13:29', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/3/course_id/23');
INSERT INTO `system_log` VALUES (3391, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:14:22', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3392, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:14:47', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3393, '[编辑直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-29 18:18:24', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/3/course_id/23');
INSERT INTO `system_log` VALUES (3394, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:18:28', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3395, '[编辑直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-29 18:18:41', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/3/course_id/23');
INSERT INTO `system_log` VALUES (3396, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:18:46', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3397, '[添加课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:29:15', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3398, '[修改课程章节成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:29:29', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3399, '[添加课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:35:22', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3400, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:35:41', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3401, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:35:50', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3402, '[添加课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:37:13', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3403, '[添加课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:38:13', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3404, '[添加课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:40:59', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3405, '[添加课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:42:41', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3406, '[添加课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:43:09', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3407, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 18:48:28', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3408, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 19:06:03', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3409, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-29 19:33:09', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3410, '[登录成功]', '2020-05-29 21:55:01', 'admin', 1, '60.216.126.165', 'backend/login/login');
INSERT INTO `system_log` VALUES (3411, '[修改设置]', '2020-05-29 22:30:03', 'admin', 1, '60.216.126.165', 'backend/system/config/group/ccapi');
INSERT INTO `system_log` VALUES (3412, '[修改设置]', '2020-05-29 22:30:28', 'admin', 1, '60.216.126.165', 'backend/system/config/group/ccapi');
INSERT INTO `system_log` VALUES (3413, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 13:54:38', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3414, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 14:05:26', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3415, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 14:05:44', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3416, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 14:36:23', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3417, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 14:36:45', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3418, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 15:01:21', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3419, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 15:32:46', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3420, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 15:38:36', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3421, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 15:49:30', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3422, '[添加课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 15:51:08', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3423, '[添加直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 15:52:40', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/70/course_id/23');
INSERT INTO `system_log` VALUES (3424, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 15:52:52', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3425, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 15:55:11', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3426, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 15:55:59', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3427, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 15:56:48', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3428, '[编辑直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 15:57:47', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/70/course_id/23');
INSERT INTO `system_log` VALUES (3429, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 15:57:51', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3430, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 15:58:02', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3431, '[编辑直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 15:58:45', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/3/course_id/23');
INSERT INTO `system_log` VALUES (3432, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 16:19:19', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3433, '[编辑直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 16:19:40', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/70/course_id/23');
INSERT INTO `system_log` VALUES (3434, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 16:19:44', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3435, '[编辑直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 16:19:46', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/70/course_id/23');
INSERT INTO `system_log` VALUES (3436, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 16:19:50', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3437, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 16:20:40', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3438, '[编辑直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 16:20:47', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/3/course_id/23');
INSERT INTO `system_log` VALUES (3439, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 16:20:51', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3440, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 16:22:52', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3441, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 16:23:04', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3442, '[编辑直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 16:24:48', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/3/course_id/23');
INSERT INTO `system_log` VALUES (3443, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 16:24:52', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3444, '[编辑直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 16:24:55', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/3/course_id/23');
INSERT INTO `system_log` VALUES (3445, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 16:24:59', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3446, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 16:25:17', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3447, '[编辑直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 16:25:21', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/3/course_id/23');
INSERT INTO `system_log` VALUES (3448, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 16:59:08', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3449, '[编辑直播信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 16:59:15', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/2/section_id/3/course_id/23');
INSERT INTO `system_log` VALUES (3450, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 17:02:49', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3451, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 18:44:48', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3452, '[添加课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 18:45:17', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3453, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 18:47:52', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3454, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 18:50:07', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3455, '[添加小班课信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 18:55:35', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/3/section_id/71/course_id/23');
INSERT INTO `system_log` VALUES (3456, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 18:58:55', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3457, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 19:04:41', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3458, '[编辑小班课信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 19:09:51', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/3/section_id/71/course_id/23');
INSERT INTO `system_log` VALUES (3459, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 19:10:09', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3460, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 19:12:01', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3461, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 19:12:07', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3462, '[编辑小班课信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 19:12:10', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/3/section_id/71/course_id/23');
INSERT INTO `system_log` VALUES (3463, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 19:12:29', 'admin', 1, '39.71.122.43', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3464, '[编辑小班课信息成功] [MODEL] app\\common\\model\\CourseLive', '2020-05-30 19:12:31', 'admin', 1, '39.71.122.43', 'backend/course/createsectiontype/type/3/section_id/71/course_id/23');
INSERT INTO `system_log` VALUES (3465, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 21:38:09', 'admin', 1, '60.216.126.165', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3466, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-30 21:38:15', 'admin', 1, '60.216.126.165', 'backend/course/createsection/course_id/23');
INSERT INTO `system_log` VALUES (3467, '[登录成功]', '2020-05-31 08:58:50', 'admin', 1, '39.71.122.43', 'backend/login/login');
INSERT INTO `system_log` VALUES (3468, '[登录成功]', '2020-05-31 10:33:33', 'admin', 1, '39.71.122.43', 'login/login');
INSERT INTO `system_log` VALUES (3469, '[登录成功]', '2020-05-31 10:36:28', 'admin', 1, '39.71.122.43', 'login/login');
INSERT INTO `system_log` VALUES (3470, '[修改课程课时成功] [MODEL] app\\backend\\model\\CourseSectionModel', '2020-05-31 10:59:25', 'admin', 1, '39.71.122.43', 'course/createsection/course_id/16');
INSERT INTO `system_log` VALUES (3471, '[登录成功]', '2020-06-10 21:15:00', 'admin', 1, '39.82.46.247', 'login/login');
INSERT INTO `system_log` VALUES (3472, '[登录成功]', '2020-06-11 11:28:37', 'admin', 1, '39.71.81.166', 'login/login');
INSERT INTO `system_log` VALUES (3473, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-06-11 11:30:41', 'admin', 1, '39.71.81.166', 'course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3474, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-06-11 11:41:54', 'admin', 1, '39.71.81.166', 'course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3475, '[登录成功]', '2020-06-11 15:44:42', 'admin', 1, '39.71.81.166', 'login/login');
INSERT INTO `system_log` VALUES (3476, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-06-11 15:56:25', 'admin', 1, '39.71.81.166', 'course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3477, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-06-11 15:56:38', 'admin', 1, '39.71.81.166', 'course/createcourse/type/1');
INSERT INTO `system_log` VALUES (3478, '[成功] [MODEL] AttachmentCateModel', '2020-06-11 16:00:55', 'admin', 1, '39.71.81.166', 'attachment/handlecreate/_m/AttachmentCateModel');
INSERT INTO `system_log` VALUES (3479, '[登录成功]', '2020-06-12 10:30:17', 'admin', 1, '39.71.81.166', 'login/login');
INSERT INTO `system_log` VALUES (3480, '[添加下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 12:01:58', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3481, '[添加下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 12:10:40', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3482, '[修改下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 13:39:49', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3483, '[修改下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 13:40:00', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3484, '[修改下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 13:41:29', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3485, '[修改下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 13:42:13', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3486, '[修改下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 13:42:21', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3487, '[修改下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 14:59:12', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3488, '[登录成功]', '2020-06-12 15:53:02', 'admin', 1, '39.71.81.166', 'login/login');
INSERT INTO `system_log` VALUES (3489, '[添加下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 15:57:16', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3490, '[修改下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 15:58:05', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3491, '[添加下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 15:58:22', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3492, '[添加下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 15:58:39', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3493, '[修改下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 17:45:13', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3494, '[添加下载文件成功] [MODEL] app\\backend\\model\\DownloadModel', '2020-06-12 17:47:47', 'admin', 1, '39.71.81.166', 'download/createFile');
INSERT INTO `system_log` VALUES (3495, '[登录成功]', '2020-06-12 19:47:24', 'admin', 1, '39.71.81.166', 'login/login');
INSERT INTO `system_log` VALUES (3496, '[登录成功]', '2020-06-27 17:39:46', 'admin', 1, '123.233.71.145', 'login/login');
INSERT INTO `system_log` VALUES (3497, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-06-27 17:40:35', 'admin', 1, '123.233.71.145', 'article/createAdvertisement');
INSERT INTO `system_log` VALUES (3498, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-06-27 17:41:36', 'admin', 1, '123.233.71.145', 'article/createAdvertisement');
INSERT INTO `system_log` VALUES (3499, '[修改公告成功] [MODEL] app\\backend\\model\\NotificationModel', '2020-06-27 17:43:59', 'admin', 1, '123.233.71.145', 'article/createNotice');
INSERT INTO `system_log` VALUES (3500, '[添加广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-06-27 17:58:48', 'admin', 1, '123.233.71.145', 'article/createAdvertisement');
INSERT INTO `system_log` VALUES (3501, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-06-27 18:01:50', 'admin', 1, '123.233.71.145', 'article/createAdvertisement');
INSERT INTO `system_log` VALUES (3502, '[登录成功]', '2020-07-05 20:59:35', 'admin', 1, '39.82.46.247', 'login/login');
INSERT INTO `system_log` VALUES (3503, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-07-05 21:00:11', 'admin', 1, '39.82.46.247', 'article/createAdvertisement');
INSERT INTO `system_log` VALUES (3504, '[修改广告成功] [MODEL] app\\backend\\model\\AdvertisementModel', '2020-07-05 21:00:36', 'admin', 1, '39.82.46.247', 'article/createAdvertisement');
INSERT INTO `system_log` VALUES (3505, '[登录成功]', '2020-07-23 11:07:51', 'admin', 1, '39.71.43.67', 'login/login');
INSERT INTO `system_log` VALUES (3506, '[登录成功]', '2020-07-24 09:18:57', 'admin', 1, '39.71.43.67', 'login/login');
INSERT INTO `system_log` VALUES (3507, '[修改VIP会员成功] [MODEL] app\\backend\\model\\CardVipModel', '2020-07-24 09:27:04', 'admin', 1, '39.71.43.67', 'card/editVip');
INSERT INTO `system_log` VALUES (3508, '[修改VIP会员成功] [MODEL] app\\backend\\model\\CardVipModel', '2020-07-24 09:27:09', 'admin', 1, '39.71.43.67', 'card/editVip');
INSERT INTO `system_log` VALUES (3509, '[修改VIP会员成功] [MODEL] app\\backend\\model\\CardVipModel', '2020-07-24 11:29:18', 'admin', 1, '39.71.43.67', 'card/editVip');
INSERT INTO `system_log` VALUES (3510, '[修改VIP会员成功] [MODEL] app\\backend\\model\\CardVipModel', '2020-07-24 11:29:23', 'admin', 1, '39.71.43.67', 'card/editVip');
INSERT INTO `system_log` VALUES (3511, '[修改VIP会员成功] [MODEL] app\\backend\\model\\CardVipModel', '2020-07-24 13:47:50', 'admin', 1, '39.71.43.67', 'card/editVip');
INSERT INTO `system_log` VALUES (3512, '[修改学员信息成功] [MODEL] app\\backend\\model\\UserModel', '2020-07-24 13:58:55', 'admin', 1, '39.71.43.67', 'user/saveUser');
INSERT INTO `system_log` VALUES (3513, '[修改学员信息成功] [MODEL] app\\backend\\model\\UserModel', '2020-07-24 13:59:03', 'admin', 1, '39.71.43.67', 'user/saveUser');
INSERT INTO `system_log` VALUES (3514, '[修改学员信息成功] [MODEL] app\\backend\\model\\UserModel', '2020-07-24 13:59:48', 'admin', 1, '39.71.43.67', 'user/saveUser');
INSERT INTO `system_log` VALUES (3515, '[添加成功] [MODEL] app\\backend\\model\\CardTypesModel', '2020-07-24 15:51:21', 'admin', 1, '39.71.43.67', 'card/editcard/type/editCard');
INSERT INTO `system_log` VALUES (3516, '[添加VIP激活卡成功] [MODEL] app\\backend\\model\\CardTypesModel', '2020-07-24 15:55:51', 'admin', 1, '39.71.43.67', 'card/editcard/type/9');
INSERT INTO `system_log` VALUES (3517, '[修改VIP激活卡成功] [MODEL] app\\backend\\model\\CardTypesModel', '2020-07-24 15:55:57', 'admin', 1, '39.71.43.67', 'card/editcard/type/9');
INSERT INTO `system_log` VALUES (3518, '[修改VIP激活卡成功] [MODEL] app\\backend\\model\\CardTypesModel', '2020-07-24 15:56:05', 'admin', 1, '39.71.43.67', 'card/editcard/type/9');
INSERT INTO `system_log` VALUES (3519, '[添加代金券成功] [MODEL] app\\backend\\model\\CardTypesModel', '2020-07-24 15:57:55', 'admin', 1, '39.71.43.67', 'card/editcard/type/2');
INSERT INTO `system_log` VALUES (3520, '[修改VIP激活卡成功] [MODEL] app\\backend\\model\\CardTypesModel', '2020-07-24 17:43:13', 'admin', 1, '39.71.43.67', 'card/editcard/type/9');
INSERT INTO `system_log` VALUES (3521, '[修改店员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-07-24 18:02:25', 'admin', 1, '39.71.43.67', 'store/editStaff');
INSERT INTO `system_log` VALUES (3522, '[删除成功] [MODEL] AuthRole', '2020-07-24 18:10:56', 'admin', 1, '39.71.43.67', 'auth/delrole');
INSERT INTO `system_log` VALUES (3523, '[添加店员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-07-24 18:19:53', 'admin', 1, '39.71.43.67', 'store/editStaff');
INSERT INTO `system_log` VALUES (3524, '[登录成功]', '2020-08-10 15:55:16', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3525, '[登录成功]', '2020-08-10 21:11:58', 'admin', 1, '112.230.104.188', 'backend/login/login');
INSERT INTO `system_log` VALUES (3526, '[登录成功]', '2020-08-12 10:27:50', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3527, '[修改知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-12 17:53:10', 'admin', 1, '39.91.105.126', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3528, '[修改知识点分类成功] [MODEL] app\\backend\\model\\KnowledgeCategoryModel', '2020-08-12 17:56:41', 'admin', 1, '39.91.105.126', 'backend/knowledge/saveCategory');
INSERT INTO `system_log` VALUES (3529, '[修改知识点分类成功] [MODEL] app\\backend\\model\\KnowledgeCategoryModel', '2020-08-12 17:56:46', 'admin', 1, '39.91.105.126', 'backend/knowledge/saveCategory');
INSERT INTO `system_log` VALUES (3530, '[修改试题分类成功] [MODEL] app\\backend\\model\\QuestionCategoryModel', '2020-08-12 18:13:07', 'admin', 1, '39.91.105.126', 'backend/test/saveCategory');
INSERT INTO `system_log` VALUES (3531, '[修改试题分类成功] [MODEL] app\\backend\\model\\QuestionCategoryModel', '2020-08-12 18:13:19', 'admin', 1, '39.91.105.126', 'backend/test/saveCategory');
INSERT INTO `system_log` VALUES (3532, '[添加试题分类成功] [MODEL] app\\backend\\model\\QuestionCategoryModel', '2020-08-12 18:16:23', 'admin', 1, '39.91.105.126', 'backend/test/saveCategory');
INSERT INTO `system_log` VALUES (3533, '[修改试题分类成功] [MODEL] app\\backend\\model\\QuestionCategoryModel', '2020-08-12 18:18:49', 'admin', 1, '39.91.105.126', 'backend/test/saveCategory');
INSERT INTO `system_log` VALUES (3534, '[登录成功]', '2020-08-12 21:40:12', 'admin', 1, '112.230.104.188', 'backend/login/login');
INSERT INTO `system_log` VALUES (3535, '[登录成功]', '2020-08-13 10:00:04', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3536, '[添加知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-13 13:30:33', 'admin', 1, '39.91.105.126', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3537, '[登录成功]', '2020-08-14 22:11:22', 'admin', 1, '112.230.104.188', 'backend/login/login');
INSERT INTO `system_log` VALUES (3538, '[登录成功]', '2020-08-14 22:21:35', 'admin', 1, '112.230.104.188', 'backend/login/login');
INSERT INTO `system_log` VALUES (3539, '[登录成功]', '2020-08-16 10:04:09', 'admin', 1, '223.104.190.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (3540, '[登录成功]', '2020-08-17 15:04:50', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3541, '[登录成功]', '2020-08-17 21:04:04', 'admin', 1, '112.230.104.188', 'backend/login/login');
INSERT INTO `system_log` VALUES (3542, '[登录成功]', '2020-08-18 11:07:37', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3543, '[登录成功]', '2020-08-18 11:24:54', 'admin', 1, '112.224.67.56', 'backend/login/login');
INSERT INTO `system_log` VALUES (3544, '[登录成功]', '2020-08-18 11:33:30', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3545, '[修改试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-18 14:07:42', 'admin', 1, '39.91.105.126', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3546, '[修改试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-18 14:09:37', 'admin', 1, '39.91.105.126', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3547, '[修改试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-18 14:10:15', 'admin', 1, '39.91.105.126', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3548, '[添加试卷成功] [MODEL] app\\backend\\model\\TestModel', '2020-08-18 15:26:16', 'admin', 1, '39.91.105.126', 'backend/test/savetest');
INSERT INTO `system_log` VALUES (3549, '[添加试卷成功] [MODEL] app\\backend\\model\\TestModel', '2020-08-18 15:27:03', 'admin', 1, '39.91.105.126', 'backend/test/savetest');
INSERT INTO `system_log` VALUES (3550, '[添加试卷成功] [MODEL] app\\backend\\model\\TestModel', '2020-08-18 15:27:31', 'admin', 1, '39.91.105.126', 'backend/test/savetest');
INSERT INTO `system_log` VALUES (3551, '[修改试卷成功] [MODEL] app\\backend\\model\\TestModel', '2020-08-18 15:43:28', 'admin', 1, '39.91.105.126', 'backend/test/savetest');
INSERT INTO `system_log` VALUES (3552, '[添加试卷成功] [MODEL] app\\backend\\model\\TestModel', '2020-08-18 15:44:53', 'admin', 1, '39.91.105.126', 'backend/test/savetest');
INSERT INTO `system_log` VALUES (3553, '[添加试卷成功] [MODEL] app\\backend\\model\\TestModel', '2020-08-18 15:46:08', 'admin', 1, '39.91.105.126', 'backend/test/savetest');
INSERT INTO `system_log` VALUES (3554, '[添加试题部分成功] [MODEL] app\\backend\\model\\TestPartModel', '2020-08-18 18:34:55', 'admin', 1, '39.91.105.126', 'backend/test/addtestpart/test_id/11');
INSERT INTO `system_log` VALUES (3555, '[登录成功]', '2020-08-19 20:11:50', 'admin', 1, '117.136.78.8', 'backend/login/login');
INSERT INTO `system_log` VALUES (3556, '[登录成功]', '2020-08-19 20:19:27', 'admin', 1, '112.230.104.188', 'backend/login/login');
INSERT INTO `system_log` VALUES (3557, '[修改知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-19 20:32:53', 'admin', 1, '117.136.78.8', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3558, '[修改试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-20 07:43:27', 'admin', 1, '111.35.18.247', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3559, '[修改试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-20 07:43:41', 'admin', 1, '111.35.18.247', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3560, '[修改试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-20 07:43:56', 'admin', 1, '111.35.18.247', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3561, '[添加试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-20 07:44:08', 'admin', 1, '111.35.18.247', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3562, '[修改试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-20 07:44:33', 'admin', 1, '111.35.18.247', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3563, '[修改试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-20 07:44:46', 'admin', 1, '111.35.18.247', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3564, '[添加试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-20 07:45:13', 'admin', 1, '111.35.18.247', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3565, '[修改试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-20 07:45:22', 'admin', 1, '111.35.18.247', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3566, '[修改试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-20 07:45:29', 'admin', 1, '111.35.18.247', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3567, '[添加试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-20 07:45:48', 'admin', 1, '111.35.18.247', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3568, '[修改试卷分类成功] [MODEL] app\\backend\\model\\TestCategoryModel', '2020-08-20 07:45:57', 'admin', 1, '111.35.18.247', 'backend/test/saveTestCategory');
INSERT INTO `system_log` VALUES (3569, '[登录成功]', '2020-08-20 09:01:23', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3570, '[修改课时分类成功] [MODEL] app\\backend\\model\\SectionCategoryModel', '2020-08-20 09:07:15', 'admin', 1, '39.91.105.126', 'backend/section/saveSectionCategory');
INSERT INTO `system_log` VALUES (3571, '[修改课时分类成功] [MODEL] app\\backend\\model\\SectionCategoryModel', '2020-08-20 09:07:23', 'admin', 1, '39.91.105.126', 'backend/section/saveSectionCategory');
INSERT INTO `system_log` VALUES (3572, '[添加知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-20 09:29:52', 'admin', 1, '39.91.105.126', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3573, '[修改知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-20 09:36:53', 'admin', 1, '39.91.105.126', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3574, '[修改知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-20 09:37:00', 'admin', 1, '39.91.105.126', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3575, '[修改知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-20 09:37:10', 'admin', 1, '39.91.105.126', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3576, '[修改知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-20 09:37:17', 'admin', 1, '39.91.105.126', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3577, '[修改知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-20 09:37:34', 'admin', 1, '39.91.105.126', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3578, '[修改知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-20 09:39:06', 'admin', 1, '39.91.105.126', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3579, '[修改课时分类成功] [MODEL] app\\backend\\model\\SectionCategoryModel', '2020-08-20 09:55:04', 'admin', 1, '111.35.18.247', 'backend/section/saveSectionCategory');
INSERT INTO `system_log` VALUES (3580, '[添加课时分类成功] [MODEL] app\\backend\\model\\SectionCategoryModel', '2020-08-20 09:55:33', 'admin', 1, '111.35.18.247', 'backend/section/saveSectionCategory');
INSERT INTO `system_log` VALUES (3581, '[修改知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-20 15:32:04', 'admin', 1, '112.53.84.140', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3582, '[修改知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-20 15:32:20', 'admin', 1, '112.53.84.140', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3583, '[登录成功]', '2020-08-21 11:15:55', 'admin', 1, '223.104.187.37', 'backend/login/login');
INSERT INTO `system_log` VALUES (3584, '[登录成功]', '2020-08-21 11:23:26', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3585, '[登录成功]', '2020-08-21 14:41:38', 'admin', 1, '111.35.18.247', 'backend/login/login');
INSERT INTO `system_log` VALUES (3586, '[登录成功]', '2020-08-22 13:00:01', 'admin', 1, '112.53.84.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (3587, '[登录成功]', '2020-08-22 16:52:49', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3588, '[登录成功]', '2020-08-22 22:05:28', 'admin', 1, '112.230.104.188', 'backend/login/login');
INSERT INTO `system_log` VALUES (3589, '[修改知识点讲解成功] [MODEL] app\\backend\\model\\H5Model', '2020-08-22 22:41:55', 'admin', 1, '112.230.104.188', 'backend/knowledge/saveH5');
INSERT INTO `system_log` VALUES (3590, '[修改知识点讲解成功] [MODEL] app\\backend\\model\\H5Model', '2020-08-22 22:42:09', 'admin', 1, '112.230.104.188', 'backend/knowledge/saveH5');
INSERT INTO `system_log` VALUES (3591, '[成功] [MODEL] AttachmentCateModel', '2020-08-23 09:10:01', 'admin', 1, '223.104.187.23', 'backend/attachment/handlesave/_m/AttachmentCateModel');
INSERT INTO `system_log` VALUES (3592, '[成功] [MODEL] AttachmentCateModel', '2020-08-23 09:10:12', 'admin', 1, '223.104.187.23', 'backend/attachment/handlesave/_m/AttachmentCateModel');
INSERT INTO `system_log` VALUES (3593, '[修改知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-23 12:23:22', 'admin', 1, '112.230.104.188', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3594, '[修改知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-08-23 12:24:52', 'admin', 1, '112.230.104.188', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (3595, '[添加知识点讲解成功] [MODEL] app\\backend\\model\\H5Model', '2020-08-23 12:59:02', 'admin', 1, '112.230.104.188', 'backend/knowledge/saveh5/kid/15');
INSERT INTO `system_log` VALUES (3596, '[登录成功]', '2020-08-23 19:22:00', 'admin', 1, '112.230.104.188', 'backend/login/login');
INSERT INTO `system_log` VALUES (3597, '[修改知识点讲解成功] [MODEL] app\\backend\\model\\H5Model', '2020-08-23 19:39:34', 'admin', 1, '112.230.104.188', 'backend/knowledge/saveh5');
INSERT INTO `system_log` VALUES (3598, '[修改知识点讲解成功] [MODEL] app\\backend\\model\\H5Model', '2020-08-23 19:39:57', 'admin', 1, '112.230.104.188', 'backend/knowledge/saveh5');
INSERT INTO `system_log` VALUES (3599, '[添加知识点讲解成功] [MODEL] app\\backend\\model\\H5Model', '2020-08-23 19:41:34', 'admin', 1, '112.230.104.188', 'backend/knowledge/saveh5');
INSERT INTO `system_log` VALUES (3600, '[登录成功]', '2020-08-23 21:18:44', 'admin', 1, '112.230.104.188', 'backend/login/login');
INSERT INTO `system_log` VALUES (3601, '[登录成功]', '2020-08-23 22:24:59', 'admin', 1, '223.104.187.23', 'backend/login/login');
INSERT INTO `system_log` VALUES (3602, '[登录成功]', '2020-08-24 08:57:00', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3603, '[添加知识点讲解成功] [MODEL] app\\backend\\model\\H5Model', '2020-08-24 15:06:51', 'admin', 1, '112.53.84.140', 'backend/knowledge/saveh5');
INSERT INTO `system_log` VALUES (3604, '[修改知识点讲解成功] [MODEL] app\\backend\\model\\H5Model', '2020-08-24 15:08:34', 'admin', 1, '112.53.84.140', 'backend/knowledge/saveh5');
INSERT INTO `system_log` VALUES (3605, '[登录成功]', '2020-08-24 16:12:11', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3606, '[登录成功]', '2020-08-24 18:23:39', 'admin', 1, '112.53.84.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (3607, '[登录成功]', '2020-08-24 21:58:17', 'admin', 1, '112.231.190.132', 'backend/login/login');
INSERT INTO `system_log` VALUES (3608, '[登录成功]', '2020-08-25 08:47:03', 'admin', 1, '112.53.84.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (3609, '[登录成功]', '2020-08-25 09:20:00', 'admin', 1, '112.53.84.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (3610, '[登录成功]', '2020-08-25 09:44:09', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3611, '[登录成功]', '2020-08-25 13:44:46', 'admin', 1, '112.53.84.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (3612, '[登录成功]', '2020-08-25 13:48:08', 'admin', 1, '112.53.84.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (3613, '[登录成功]', '2020-08-25 14:17:14', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3614, '[登录成功]', '2020-08-25 15:53:28', 'admin', 1, '112.53.84.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (3615, '[修改设置]', '2020-08-25 16:47:15', 'admin', 1, '111.35.18.247', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (3616, '[登录成功]', '2020-08-26 08:56:42', 'admin', 1, '112.53.84.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (3617, '[登录成功]', '2020-08-26 10:12:53', 'admin', 1, '112.53.84.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (3618, '[登录成功]', '2020-08-26 15:49:47', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3619, '[登录成功]', '2020-08-26 16:46:31', 'admin', 1, '112.53.84.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (3620, '[登录成功]', '2020-08-28 21:10:57', 'admin', 1, '123.233.216.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3621, '[修改值班成功] [MODEL] app\\backend\\model\\ScheduleModel', '2020-08-28 21:59:50', 'admin', 1, '123.233.216.89', 'backend/schedule/editSchedule');
INSERT INTO `system_log` VALUES (3622, '[修改编排时间段计划成功] [MODEL] app\\backend\\model\\SchedulePlanModel', '2020-08-28 22:11:14', 'admin', 1, '123.233.216.89', 'backend/schedule/editPlan');
INSERT INTO `system_log` VALUES (3623, '[修改编排时间段计划成功] [MODEL] app\\backend\\model\\SchedulePlanModel', '2020-08-28 22:11:21', 'admin', 1, '123.233.216.89', 'backend/schedule/editPlan');
INSERT INTO `system_log` VALUES (3624, '[修改编排时间段计划成功] [MODEL] app\\backend\\model\\SchedulePlanModel', '2020-08-28 22:13:45', 'admin', 1, '123.233.216.89', 'backend/schedule/editPlan');
INSERT INTO `system_log` VALUES (3625, '[修改值班成功] [MODEL] app\\backend\\model\\ScheduleModel', '2020-08-28 22:15:17', 'admin', 1, '123.233.216.89', 'backend/schedule/editSchedule');
INSERT INTO `system_log` VALUES (3626, '[修改编排时间段计划成功] [MODEL] app\\backend\\model\\SchedulePlanModel', '2020-08-28 22:15:28', 'admin', 1, '123.233.216.89', 'backend/schedule/editPlan');
INSERT INTO `system_log` VALUES (3627, '[修改编排时间段计划成功] [MODEL] app\\backend\\model\\SchedulePlanModel', '2020-08-28 22:15:34', 'admin', 1, '123.233.216.89', 'backend/schedule/editPlan');
INSERT INTO `system_log` VALUES (3628, '[修改值班成功] [MODEL] app\\backend\\model\\ScheduleModel', '2020-08-28 22:15:45', 'admin', 1, '123.233.216.89', 'backend/schedule/editSchedule');
INSERT INTO `system_log` VALUES (3629, '[修改编排时间段计划成功] [MODEL] app\\backend\\model\\SchedulePlanModel', '2020-08-28 22:16:10', 'admin', 1, '123.233.216.89', 'backend/schedule/editPlan');
INSERT INTO `system_log` VALUES (3630, '[修改老师成功] [MODEL] app\\backend\\model\\StaffModel', '2020-08-28 22:18:48', 'admin', 1, '123.233.216.89', 'backend/staff/editStaff');
INSERT INTO `system_log` VALUES (3631, '[登录成功]', '2020-08-29 11:25:02', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3632, '[修改老师成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-08-29 11:43:14', 'admin', 1, '39.91.105.126', 'backend/staff/verifyOffduty');
INSERT INTO `system_log` VALUES (3633, '[审批成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-08-29 11:46:11', 'admin', 1, '39.91.105.126', 'backend/staff/verifyOffduty');
INSERT INTO `system_log` VALUES (3634, '[审批成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-08-29 11:46:23', 'admin', 1, '39.91.105.126', 'backend/staff/verifyOffduty');
INSERT INTO `system_log` VALUES (3635, '[登录成功]', '2020-08-29 14:04:42', 'admin', 1, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3636, '[审批成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-08-29 14:39:41', 'admin', 1, '39.91.105.126', 'backend/staff/verifyOffduty');
INSERT INTO `system_log` VALUES (3637, '[审批成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-08-29 14:41:16', 'admin', 1, '39.91.105.126', 'backend/staff/verifyPayout');
INSERT INTO `system_log` VALUES (3638, '[登录成功]', '2020-08-29 14:49:34', 'admin', 3, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3639, '[登录成功]', '2020-08-29 14:50:53', 'admin', 3, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3640, '[登录成功]', '2020-08-29 14:51:15', 'admin', 3, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3641, '[登录成功]', '2020-08-29 14:51:20', 'admin', 3, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3642, '[修改请假类型成功] [MODEL] app\\backend\\model\\OffdutyTypeModel', '2020-08-29 16:18:54', 'admin', 3, '39.91.105.126', 'backend/staff/saveOffdutyType');
INSERT INTO `system_log` VALUES (3643, '[修改请假类型成功] [MODEL] app\\backend\\model\\OffdutyTypeModel', '2020-08-29 16:19:00', 'admin', 3, '39.91.105.126', 'backend/staff/saveOffdutyType');
INSERT INTO `system_log` VALUES (3644, '[添加请假类型成功] [MODEL] app\\backend\\model\\OffdutyTypeModel', '2020-08-29 16:19:09', 'admin', 3, '39.91.105.126', 'backend/staff/saveOffdutyType');
INSERT INTO `system_log` VALUES (3645, '[登录成功]', '2020-08-29 16:32:15', 'admin', 3, '112.224.67.170', 'backend/login/login');
INSERT INTO `system_log` VALUES (3646, '[我要请假成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-08-29 17:36:23', 'admin', 3, '39.91.105.126', 'backend/staff/saveOffduty');
INSERT INTO `system_log` VALUES (3647, '[我要请假成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-08-29 17:37:19', 'admin', 3, '39.91.105.126', 'backend/staff/saveOffduty');
INSERT INTO `system_log` VALUES (3648, '[我要请假成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-08-29 17:55:40', 'admin', 3, '39.91.105.126', 'backend/staff/saveOffduty');
INSERT INTO `system_log` VALUES (3649, '[审批成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-08-29 17:59:43', 'admin', 3, '39.91.105.126', 'backend/staff/verifyOffduty');
INSERT INTO `system_log` VALUES (3650, '[登录成功]', '2020-08-29 21:33:33', 'admin', 3, '123.233.216.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3651, '[修改请假成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-08-29 21:54:25', 'admin', 3, '123.233.216.89', 'backend/staff/saveOffduty');
INSERT INTO `system_log` VALUES (3652, '[修改请假成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-08-29 21:55:42', 'admin', 3, '123.233.216.89', 'backend/staff/saveOffduty');
INSERT INTO `system_log` VALUES (3653, '[修改请假成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-08-29 21:56:24', 'admin', 3, '123.233.216.89', 'backend/staff/saveOffduty');
INSERT INTO `system_log` VALUES (3654, '[登录成功]', '2020-08-29 23:08:07', 'admin', 3, '123.233.216.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3655, '[审批成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-08-29 23:22:27', 'admin', 3, '123.233.216.89', 'backend/staff/verifyPayout');
INSERT INTO `system_log` VALUES (3656, '[我要请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-08-29 23:36:08', 'admin', 3, '123.233.216.89', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (3657, '[登录成功]', '2020-08-30 11:26:19', 'admin', 3, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3658, '[请款支付成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-08-30 11:57:08', 'admin', 3, '39.91.105.126', 'backend/staff/paidPayout');
INSERT INTO `system_log` VALUES (3659, '[请款支付成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-08-30 11:59:17', 'admin', 3, '39.91.105.126', 'backend/staff/paidPayout');
INSERT INTO `system_log` VALUES (3660, '[请款审批成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-08-30 11:59:35', 'admin', 3, '39.91.105.126', 'backend/staff/verifyPayout');
INSERT INTO `system_log` VALUES (3661, '[修改请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-08-30 12:11:51', 'admin', 3, '39.91.105.126', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (3662, '[登录成功]', '2020-08-30 13:44:23', 'admin', 3, '112.224.67.170', 'backend/login/login');
INSERT INTO `system_log` VALUES (3663, '[添加学员信息成功] [MODEL] app\\backend\\model\\StudentModel', '2020-08-30 14:27:44', 'admin', 3, '39.91.105.126', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (3664, '[修改学员信息成功] [MODEL] app\\backend\\model\\StudentModel', '2020-08-30 14:31:54', 'admin', 3, '39.91.105.126', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (3665, '[修改学员信息成功] [MODEL] app\\backend\\model\\StudentModel', '2020-08-30 14:32:33', 'admin', 3, '39.91.105.126', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (3666, '[修改学员信息成功] [MODEL] app\\backend\\model\\StudentModel', '2020-08-30 14:36:14', 'admin', 3, '39.91.105.126', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (3667, '[修改学员信息成功] [MODEL] app\\backend\\model\\StudentModel', '2020-08-30 14:36:20', 'admin', 3, '39.91.105.126', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (3668, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-08-30 15:50:00', 'admin', 3, '39.91.105.126', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3669, '[添加学员成功] [MODEL] app\\backend\\model\\StudentModel', '2020-08-30 16:11:26', 'admin', 3, '39.91.105.126', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (3670, '[添加学员成功] [MODEL] app\\backend\\model\\StudentModel', '2020-08-30 16:12:02', 'admin', 3, '39.91.105.126', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (3671, '[添加学员成功] [MODEL] app\\backend\\model\\StudentModel', '2020-08-30 16:13:20', 'admin', 3, '39.91.105.126', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (3672, '[添加学员成功] [MODEL] app\\backend\\model\\StudentModel', '2020-08-30 16:14:33', 'admin', 3, '39.91.105.126', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (3673, '[增加积分1成功] [MODEL] app\\common\\model\\UserAccount', '2020-08-30 16:29:19', 'admin', 3, '39.91.105.126', 'backend/student/changepoint/type/inc');
INSERT INTO `system_log` VALUES (3674, '[增加积分1成功] [MODEL] app\\common\\model\\UserAccount', '2020-08-30 16:31:01', 'admin', 3, '39.91.105.126', 'backend/student/changepoint/type/inc');
INSERT INTO `system_log` VALUES (3675, '[增加积分10成功] [MODEL] app\\common\\model\\UserAccount', '2020-08-30 16:31:11', 'admin', 3, '39.91.105.126', 'backend/student/changepoint/type/inc');
INSERT INTO `system_log` VALUES (3676, '[请款审批成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-08-30 16:35:07', 'admin', 3, '112.224.67.170', 'backend/staff/verifyPayout');
INSERT INTO `system_log` VALUES (3677, '[请款支付成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-08-30 16:35:17', 'admin', 3, '112.224.67.170', 'backend/staff/paidPayout');
INSERT INTO `system_log` VALUES (3678, '[减少积分1成功] [MODEL] app\\common\\model\\UserAccount', '2020-08-30 16:37:53', 'admin', 3, '39.91.105.126', 'backend/student/changepoint/type/dec');
INSERT INTO `system_log` VALUES (3679, '[增加积分1成功] [MODEL] app\\common\\model\\UserAccount', '2020-08-30 16:51:52', 'admin', 3, '39.91.105.126', 'backend/student/changepoint/type/inc');
INSERT INTO `system_log` VALUES (3680, '[增加积分1成功] [MODEL] app\\common\\model\\UserAccount', '2020-08-30 16:52:12', 'admin', 3, '39.91.105.126', 'backend/student/changepoint/type/inc');
INSERT INTO `system_log` VALUES (3681, '[增加积分5成功] [MODEL] app\\common\\model\\UserAccount', '2020-08-30 16:52:31', 'admin', 3, '39.91.105.126', 'backend/student/changepoint/type/inc');
INSERT INTO `system_log` VALUES (3682, '[增加积分2成功] [MODEL] app\\common\\model\\UserAccount', '2020-08-30 16:55:49', 'admin', 3, '39.91.105.126', 'backend/student/changepoint/type/inc');
INSERT INTO `system_log` VALUES (3683, '[减少积分1成功] [MODEL] app\\common\\model\\UserAccount', '2020-08-30 16:56:14', 'admin', 3, '39.91.105.126', 'backend/student/changepoint/type/dec');
INSERT INTO `system_log` VALUES (3684, '[登录成功]', '2020-08-30 20:32:40', 'admin', 3, '123.233.216.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3685, '[添加课时分类成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-08-30 21:02:17', 'admin', 3, '123.233.216.89', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3686, '[添加课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-08-30 21:02:42', 'admin', 3, '123.233.216.89', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3687, '[添加学员成功] [MODEL] app\\backend\\model\\CourseMoel', '2020-08-30 21:05:19', 'admin', 3, '123.233.216.89', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3688, '[修改课程成功] [MODEL] app\\backend\\model\\CourseMoel', '2020-08-30 21:06:17', 'admin', 3, '123.233.216.89', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3689, '[登录成功]', '2020-08-31 07:56:38', 'admin', 3, '112.53.84.131', 'backend/login/login');
INSERT INTO `system_log` VALUES (3690, '[修改编排时间段计划成功] [MODEL] app\\backend\\model\\SchedulePlanModel', '2020-08-31 07:57:52', 'admin', 3, '112.53.84.131', 'backend/schedule/editPlan');
INSERT INTO `system_log` VALUES (3691, '[登录成功]', '2020-08-31 14:20:03', 'admin', 3, '39.91.105.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (3692, '[登录成功]', '2020-09-01 08:01:10', 'admin', 3, '112.53.84.131', 'backend/login/login');
INSERT INTO `system_log` VALUES (3693, '[登录成功]', '2020-09-01 08:05:25', 'collin', 4, '112.53.84.131', 'backend/login/login');
INSERT INTO `system_log` VALUES (3694, '[登录成功]', '2020-09-01 08:05:50', 'admin', 3, '112.53.84.131', 'backend/login/login');
INSERT INTO `system_log` VALUES (3695, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-01 08:57:53', 'admin', 3, '112.53.84.131', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (3696, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-01 08:58:31', 'admin', 3, '112.53.84.131', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (3697, '[请款审批成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-01 08:58:42', 'admin', 3, '112.53.84.131', 'backend/staff/verifyPayout');
INSERT INTO `system_log` VALUES (3698, '[登录成功]', '2020-09-01 09:03:24', 'admin', 3, '111.35.18.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (3699, '[添加课时分类成功] [MODEL] app\\backend\\model\\SectionCategoryModel', '2020-09-01 09:04:58', 'admin', 3, '111.35.18.236', 'backend/section/saveSectionCategory');
INSERT INTO `system_log` VALUES (3700, '[添加课时分类成功] [MODEL] app\\backend\\model\\SectionCategoryModel', '2020-09-01 09:07:21', 'admin', 3, '111.35.18.236', 'backend/section/saveSectionCategory');
INSERT INTO `system_log` VALUES (3701, '[登录成功]', '2020-09-01 09:14:20', 'admin', 3, '123.233.216.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3702, '[登录成功]', '2020-09-01 09:34:39', 'admin', 3, '111.35.18.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (3703, '[登录成功]', '2020-09-01 09:57:29', 'admin', 3, '111.35.18.230', 'backend/login/login');
INSERT INTO `system_log` VALUES (3704, '[登录成功]', '2020-09-01 10:36:16', 'admin', 3, '111.35.18.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (3705, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-01 10:52:59', 'admin', 3, '112.53.84.131', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3706, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 11:01:54', 'admin', 3, '112.53.84.131', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3707, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 11:02:08', 'admin', 3, '112.53.84.131', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3708, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 11:02:37', 'admin', 3, '112.53.84.131', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3709, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 11:03:04', 'admin', 3, '112.53.84.131', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3710, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 11:09:03', 'admin', 3, '112.53.84.131', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3711, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 11:09:16', 'admin', 3, '112.53.84.131', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3712, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 11:09:47', 'admin', 3, '112.53.84.131', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3713, '[修改学员成功] [MODEL] app\\backend\\model\\StudentModel', '2020-09-01 11:12:06', 'admin', 3, '112.53.84.131', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (3714, '[登录成功]', '2020-09-01 11:23:05', 'admin', 3, '111.35.18.230', 'backend/login/login');
INSERT INTO `system_log` VALUES (3715, '[登录成功]', '2020-09-01 11:24:36', 'admin', 3, '111.35.18.230', 'backend/login/login');
INSERT INTO `system_log` VALUES (3716, '[登录成功]', '2020-09-01 11:28:02', 'admin', 3, '111.35.18.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (3717, '[登录成功]', '2020-09-01 13:36:45', 'admin', 3, '111.35.18.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (3718, '[登录成功]', '2020-09-01 13:45:39', 'admin', 3, '111.35.18.230', 'backend/login/login');
INSERT INTO `system_log` VALUES (3719, '[登录成功]', '2020-09-01 14:00:59', 'admin', 3, '123.233.216.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3720, '[登录成功]', '2020-09-01 14:35:01', 'admin', 3, '111.35.18.230', 'backend/login/login');
INSERT INTO `system_log` VALUES (3721, '[修改学员成功] [MODEL] app\\backend\\model\\StudentModel', '2020-09-01 15:11:40', 'admin', 3, '111.35.18.226', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (3722, '[修改学员成功] [MODEL] app\\backend\\model\\StudentModel', '2020-09-01 15:11:49', 'admin', 3, '111.35.18.226', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (3723, '[登录成功]', '2020-09-01 15:35:58', 'admin', 3, '111.35.18.234', 'backend/login/login');
INSERT INTO `system_log` VALUES (3724, '[登录成功]', '2020-09-01 20:00:51', 'admin', 3, '112.224.65.198', 'backend/login/login');
INSERT INTO `system_log` VALUES (3725, '[登录成功]', '2020-09-01 20:11:51', 'admin', 3, '123.233.216.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3726, '[添加课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:24:01', 'admin', 3, '123.233.216.89', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3727, '[添加课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:24:53', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3728, '[添加课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:25:02', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3729, '[添加课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:25:13', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3730, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:25:39', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3731, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:25:51', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3732, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:26:02', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3733, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:26:12', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3734, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:26:23', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3735, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:26:36', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3736, '[添加课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:27:05', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3737, '[添加课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:27:19', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3738, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:27:32', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3739, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:27:39', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3740, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:27:46', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3741, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:27:55', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3742, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:28:05', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3743, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-01 20:28:15', 'admin', 3, '112.224.65.198', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3744, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-01 20:28:29', 'admin', 3, '112.224.65.198', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3745, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-01 20:28:38', 'admin', 3, '112.224.65.198', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3746, '[登录成功]', '2020-09-01 20:35:25', '李四', 2, '123.233.216.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3747, '[登录成功]', '2020-09-01 20:37:13', 'admin', 3, '123.233.216.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3748, '[修改编排时间段计划成功] [MODEL] app\\backend\\model\\SchedulePlanModel', '2020-09-01 20:43:05', 'admin', 3, '112.224.65.198', 'backend/schedule/editPlan');
INSERT INTO `system_log` VALUES (3749, '[修改值班成功] [MODEL] app\\backend\\model\\ScheduleModel', '2020-09-01 20:43:59', 'admin', 3, '123.233.216.89', 'backend/schedule/editSchedule');
INSERT INTO `system_log` VALUES (3750, '[登录成功]', '2020-09-01 21:04:32', 'cui', 10, '123.233.216.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3751, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:07:00', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3752, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:07:35', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3753, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:07:44', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3754, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:07:56', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3755, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:08:09', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3756, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:08:51', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3757, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:09:05', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3758, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:09:16', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3759, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:09:28', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3760, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:09:42', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3761, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:09:53', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3762, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:10:03', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3763, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:10:13', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3764, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:10:23', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3765, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:10:33', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3766, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:10:45', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3767, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:10:56', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3768, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:11:08', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3769, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:11:23', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3770, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:11:34', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3771, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:11:48', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3772, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:12:00', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3773, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:12:21', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3774, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:12:31', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3775, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:12:42', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3776, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:12:53', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3777, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:13:09', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3778, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:13:23', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3779, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:13:33', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3780, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:13:43', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3781, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:13:53', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3782, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:14:07', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3783, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:14:17', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3784, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:14:28', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3785, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:14:38', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3786, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:14:51', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3787, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:15:02', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3788, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:15:12', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3789, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:15:22', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3790, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:15:32', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3791, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:15:42', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3792, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:15:53', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3793, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzMoel', '2020-09-01 21:16:05', 'admin', 3, '112.224.65.198', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (3794, '[添加成功] [MODEL] AuthRole', '2020-09-01 21:19:05', 'admin', 3, '112.224.65.198', 'backend/auth/addRole');
INSERT INTO `system_log` VALUES (3795, '[角色更新] [ID] 9', '2020-09-01 21:19:38', 'admin', 3, '112.224.65.198', 'backend/auth/addRole');
INSERT INTO `system_log` VALUES (3796, '[修改成功] [MODEL] AuthRole', '2020-09-01 21:19:38', 'admin', 3, '112.224.65.198', 'backend/auth/addRole');
INSERT INTO `system_log` VALUES (3797, '[添加成功] [MODEL] AuthRole', '2020-09-01 21:20:08', 'admin', 3, '112.224.65.198', 'backend/auth/addRole');
INSERT INTO `system_log` VALUES (3798, '[角色更新] [ID] 1', '2020-09-01 21:20:42', 'admin', 3, '112.224.65.198', 'backend/auth/addRole');
INSERT INTO `system_log` VALUES (3799, '[修改成功] [MODEL] AuthRole', '2020-09-01 21:20:42', 'admin', 3, '112.224.65.198', 'backend/auth/addRole');
INSERT INTO `system_log` VALUES (3800, '[添加成功] [MODEL] AuthRole', '2020-09-01 21:21:07', 'admin', 3, '112.224.65.198', 'backend/auth/addRole');
INSERT INTO `system_log` VALUES (3801, '[登录成功]', '2020-09-01 21:25:12', '夏世杰', 2, '112.224.65.198', 'backend/login/login');
INSERT INTO `system_log` VALUES (3802, '[登录成功]', '2020-09-01 21:29:03', 'admin', 3, '112.224.65.198', 'backend/login/login');
INSERT INTO `system_log` VALUES (3803, '[角色更新] [ID] 9', '2020-09-02 07:36:15', 'admin', 3, '112.53.84.131', 'backend/auth/addRole');
INSERT INTO `system_log` VALUES (3804, '[修改成功] [MODEL] AuthRole', '2020-09-02 07:36:16', 'admin', 3, '112.53.84.131', 'backend/auth/addRole');
INSERT INTO `system_log` VALUES (3805, '[登录成功]', '2020-09-02 07:37:42', 'Edward', 1, '112.53.84.130', 'backend/login/login');
INSERT INTO `system_log` VALUES (3806, '[登录成功]', '2020-09-02 07:37:46', 'Shannon', 2, '111.35.18.230', 'backend/login/login');
INSERT INTO `system_log` VALUES (3807, '[登录成功]', '2020-09-02 07:38:19', 'Susan', 8, '112.53.84.141', 'backend/login/login');
INSERT INTO `system_log` VALUES (3808, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-02 10:15:35', 'admin', 3, '123.233.216.89', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (3809, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-02 10:16:26', 'admin', 3, '123.233.216.89', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (3810, '[登录成功]', '2020-09-02 10:27:14', 'cui', 10, '123.233.216.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3811, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-02 10:31:14', 'admin', 3, '123.233.216.89', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (3812, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-02 10:31:32', 'admin', 3, '123.233.216.89', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (3813, '[登录成功]', '2020-09-02 13:41:29', 'admin', 3, '112.53.84.131', 'backend/login/login');
INSERT INTO `system_log` VALUES (3814, '[申请请假成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-09-02 13:54:32', 'admin', 3, '112.53.84.131', 'backend/staff/saveOffduty');
INSERT INTO `system_log` VALUES (3815, '[审批成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-09-02 13:54:57', 'admin', 3, '112.53.84.131', 'backend/staff/verifyOffduty');
INSERT INTO `system_log` VALUES (3816, '[修改值班成功] [MODEL] app\\backend\\model\\ScheduleModel', '2020-09-02 13:56:00', 'admin', 3, '112.53.84.131', 'backend/schedule/editSchedule');
INSERT INTO `system_log` VALUES (3817, '[添加值班成功] [MODEL] app\\backend\\model\\ScheduleModel', '2020-09-02 13:59:06', 'admin', 3, '112.53.84.131', 'backend/schedule/editSchedule');
INSERT INTO `system_log` VALUES (3818, '[添加值班成功] [MODEL] app\\backend\\model\\ScheduleModel', '2020-09-02 13:59:19', 'admin', 3, '112.53.84.131', 'backend/schedule/editSchedule');
INSERT INTO `system_log` VALUES (3819, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-02 14:09:42', 'admin', 3, '112.53.84.131', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (3820, '[登录成功]', '2020-09-02 14:09:56', 'admin', 3, '112.53.84.131', 'backend/login/login');
INSERT INTO `system_log` VALUES (3821, '[减少积分1成功] [MODEL] app\\common\\model\\UserAccount', '2020-09-02 14:10:52', 'admin', 3, '112.53.84.131', 'backend/student/changepoint/type/dec');
INSERT INTO `system_log` VALUES (3822, '[增加积分5成功] [MODEL] app\\common\\model\\UserAccount', '2020-09-02 14:11:03', 'admin', 3, '112.53.84.131', 'backend/student/changepoint/type/inc');
INSERT INTO `system_log` VALUES (3823, '[增加积分9成功] [MODEL] app\\common\\model\\UserAccount', '2020-09-02 14:11:15', 'admin', 3, '112.53.84.131', 'backend/student/changepoint/type/inc');
INSERT INTO `system_log` VALUES (3824, '[减少积分3成功] [MODEL] app\\common\\model\\UserAccount', '2020-09-02 14:11:27', 'admin', 3, '112.53.84.131', 'backend/student/changepoint/type/dec');
INSERT INTO `system_log` VALUES (3825, '[迟到补签成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-09-02 21:31:05', 'admin', 3, '39.71.25.144', 'backend/staff/attendanceapply/type/late_apply');
INSERT INTO `system_log` VALUES (3826, '[迟到补签审核成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-09-02 21:31:20', 'admin', 3, '39.71.25.144', 'backend/staff/attendanceapply/type/late_verify');
INSERT INTO `system_log` VALUES (3827, '[登录成功]', '2020-09-03 09:04:01', 'admin', 3, '112.53.84.130', 'backend/login/login');
INSERT INTO `system_log` VALUES (3828, '[登录成功]', '2020-09-03 09:05:29', '刘红', 11, '112.53.84.130', 'backend/login/login');
INSERT INTO `system_log` VALUES (3829, '[登录成功]', '2020-09-03 09:06:00', 'admin', 3, '112.53.84.130', 'backend/login/login');
INSERT INTO `system_log` VALUES (3830, '[修改请款类型成功] [MODEL] app\\backend\\model\\PayoutTypeModel', '2020-09-03 09:07:41', 'admin', 3, '112.53.84.130', 'backend/staff/savePayoutType');
INSERT INTO `system_log` VALUES (3831, '[修改请款类型成功] [MODEL] app\\backend\\model\\PayoutTypeModel', '2020-09-03 09:07:57', 'admin', 3, '112.53.84.130', 'backend/staff/savePayoutType');
INSERT INTO `system_log` VALUES (3832, '[添加请款类型成功] [MODEL] app\\backend\\model\\PayoutTypeModel', '2020-09-03 09:08:21', 'admin', 3, '112.53.84.130', 'backend/staff/savePayoutType');
INSERT INTO `system_log` VALUES (3833, '[添加请款类型成功] [MODEL] app\\backend\\model\\PayoutTypeModel', '2020-09-03 09:08:33', 'admin', 3, '112.53.84.130', 'backend/staff/savePayoutType');
INSERT INTO `system_log` VALUES (3834, '[添加请款类型成功] [MODEL] app\\backend\\model\\PayoutTypeModel', '2020-09-03 09:09:19', 'admin', 3, '112.53.84.130', 'backend/staff/savePayoutType');
INSERT INTO `system_log` VALUES (3835, '[添加请款类型成功] [MODEL] app\\backend\\model\\PayoutTypeModel', '2020-09-03 09:09:53', 'admin', 3, '112.53.84.130', 'backend/staff/savePayoutType');
INSERT INTO `system_log` VALUES (3836, '[登录成功]', '2020-09-03 09:39:18', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3837, '[登录成功]', '2020-09-03 09:52:40', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3838, '[登录成功]', '2020-09-03 10:15:58', 'admin', 3, '112.53.84.130', 'backend/login/login');
INSERT INTO `system_log` VALUES (3839, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-03 13:41:04', 'admin', 3, '39.71.25.144', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (3840, '[修改请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-03 13:48:43', 'admin', 3, '39.71.25.144', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (3841, '[登录成功]', '2020-09-03 17:14:52', 'admin', 3, '112.53.84.131', 'backend/login/login');
INSERT INTO `system_log` VALUES (3842, '[登录成功]', '2020-09-03 17:15:14', '刘红', 11, '112.53.84.130', 'backend/login/login');
INSERT INTO `system_log` VALUES (3843, '[登录成功]', '2020-09-03 17:28:13', 'admin', 3, '112.53.84.130', 'backend/login/login');
INSERT INTO `system_log` VALUES (3844, '[登录成功]', '2020-09-04 07:27:39', 'admin', 3, '112.53.84.131', 'backend/login/login');
INSERT INTO `system_log` VALUES (3845, '[早退补签成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-09-04 07:29:58', 'admin', 3, '112.53.84.131', 'backend/staff/attendanceapply/type/leave_apply');
INSERT INTO `system_log` VALUES (3846, '[早退补签审核成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-09-04 07:30:09', 'admin', 3, '112.53.84.131', 'backend/staff/attendanceapply/type/leave_verify');
INSERT INTO `system_log` VALUES (3847, '[登录成功]', '2020-09-04 09:41:24', 'admin', 3, '112.53.84.131', 'backend/login/login');
INSERT INTO `system_log` VALUES (3848, '[登录成功]', '2020-09-04 09:50:55', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3849, '[登录成功]', '2020-09-04 10:35:19', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3850, '[登录成功]', '2020-09-04 10:40:02', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3851, '[登录成功]', '2020-09-04 10:41:26', 'cui', 10, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3852, '[登录成功]', '2020-09-04 10:42:05', 'cui', 10, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3853, '[登录成功]', '2020-09-04 10:42:09', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3854, '[登录成功]', '2020-09-04 10:44:47', 'admin', 3, '112.224.75.241', 'backend/login/login');
INSERT INTO `system_log` VALUES (3855, '[登录成功]', '2020-09-04 11:01:06', 'cui', 10, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3856, '[登录成功]', '2020-09-04 11:01:23', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3857, '[登录成功]', '2020-09-04 12:19:02', 'cui', 10, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3858, '[登录成功]', '2020-09-04 12:19:59', 'cui', 10, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3859, '[登录成功]', '2020-09-04 13:42:32', 'admin', 3, '112.53.84.131', 'backend/login/login');
INSERT INTO `system_log` VALUES (3860, '[登录成功]', '2020-09-04 15:11:13', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3861, '[登录成功]', '2020-09-04 15:39:39', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3862, '[申请请假成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-09-04 16:10:38', 'admin', 3, '39.71.25.144', 'backend/staff/saveOffduty');
INSERT INTO `system_log` VALUES (3863, '[申请请假成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-09-04 16:11:52', 'admin', 3, '39.71.25.144', 'backend/staff/saveOffduty');
INSERT INTO `system_log` VALUES (3864, '[申请请假成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-09-04 16:13:30', 'admin', 3, '39.71.25.144', 'backend/staff/saveOffduty');
INSERT INTO `system_log` VALUES (3865, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-04 16:14:50', 'admin', 3, '39.71.25.144', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (3866, '[登录成功]', '2020-09-06 10:09:25', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3867, '[添加精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-06 10:40:31', 'admin', 3, '39.71.25.144', 'backend/student/saveMoment');
INSERT INTO `system_log` VALUES (3868, '[修改精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-06 10:42:44', 'admin', 3, '39.71.25.144', 'backend/student/saveMoment');
INSERT INTO `system_log` VALUES (3869, '[修改精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-06 10:54:33', 'admin', 3, '39.71.25.144', 'backend/student/saveMoment');
INSERT INTO `system_log` VALUES (3870, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-06 12:27:47', 'admin', 3, '39.71.25.144', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (3871, '[添加精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-06 12:42:49', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/2');
INSERT INTO `system_log` VALUES (3872, '[修改精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-06 12:43:30', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/1');
INSERT INTO `system_log` VALUES (3873, '[修改精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-06 14:16:57', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/1');
INSERT INTO `system_log` VALUES (3874, '[添加精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-06 14:18:57', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/2');
INSERT INTO `system_log` VALUES (3875, '[添加精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-06 14:22:04', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/2');
INSERT INTO `system_log` VALUES (3876, '[添加精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-06 14:23:38', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/2');
INSERT INTO `system_log` VALUES (3877, '[修改精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-06 14:27:48', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/2');
INSERT INTO `system_log` VALUES (3878, '[修改精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-06 14:27:54', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/1');
INSERT INTO `system_log` VALUES (3879, '[登录成功]', '2020-09-06 14:38:59', 'admin', 3, '111.35.18.245', 'backend/login/login');
INSERT INTO `system_log` VALUES (3880, '[登录成功]', '2020-09-06 19:38:24', 'admin', 3, '117.136.92.38', 'backend/login/login');
INSERT INTO `system_log` VALUES (3881, '[登录成功]', '2020-09-07 09:13:39', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3882, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-07 10:37:22', 'admin', 3, '39.71.25.144', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (3883, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-07 10:41:02', 'admin', 3, '39.71.25.144', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (3884, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-07 10:53:39', 'admin', 3, '39.71.25.144', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (3885, '[登录成功]', '2020-09-07 14:59:47', 'admin', 3, '223.104.187.34', 'backend/login/login');
INSERT INTO `system_log` VALUES (3886, '[登录成功]', '2020-09-07 19:25:33', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3887, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-07 21:39:06', 'admin', 3, '39.71.25.144', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (3888, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-07 21:46:48', 'admin', 3, '39.71.25.144', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (3889, '[登录成功]', '2020-09-08 07:01:15', 'admin', 3, '111.35.18.245', 'backend/login/login');
INSERT INTO `system_log` VALUES (3890, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-08 07:08:48', 'admin', 3, '111.35.18.245', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (3891, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-08 07:14:07', 'admin', 3, '111.35.18.245', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (3892, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-08 07:16:55', 'admin', 3, '111.35.18.245', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (3893, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-08 09:34:55', 'admin', 3, '39.71.25.144', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (3894, '[登录成功]', '2020-09-08 09:55:36', 'Susan', 8, '112.53.84.141', 'backend/login/login');
INSERT INTO `system_log` VALUES (3895, '[添加精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-08 11:20:39', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/2');
INSERT INTO `system_log` VALUES (3896, '[修改精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-08 11:32:40', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/2');
INSERT INTO `system_log` VALUES (3897, '[修改精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-08 11:36:14', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/2');
INSERT INTO `system_log` VALUES (3898, '[添加精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-08 11:39:05', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/1');
INSERT INTO `system_log` VALUES (3899, '[登录成功]', '2020-09-08 11:43:40', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3900, '[登录成功]', '2020-09-08 11:44:12', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3901, '[登录成功]', '2020-09-08 12:13:52', 'Edward', 1, '111.35.18.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (3902, '[登录成功]', '2020-09-08 12:14:24', 'admin', 3, '111.35.18.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (3903, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-08 12:24:10', 'admin', 3, '111.35.18.236', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (3904, '[登录成功]', '2020-09-08 13:59:30', 'admin', 3, '223.104.193.105', 'backend/login/login');
INSERT INTO `system_log` VALUES (3905, '[添加精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-08 17:09:35', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/1');
INSERT INTO `system_log` VALUES (3906, '[登录成功]', '2020-09-08 18:34:56', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3907, '[登录成功]', '2020-09-08 20:50:29', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3908, '[登录成功]', '2020-09-09 09:14:21', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (3909, '[修改设置]', '2020-09-09 12:10:26', 'admin', 3, '39.71.25.144', 'backend/system/config/group/attendance');
INSERT INTO `system_log` VALUES (3910, '[登录成功]', '2020-09-09 15:01:22', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3911, '[修改设置]', '2020-09-09 15:17:50', 'admin', 3, '39.71.25.144', 'backend/system/config/group/attendance');
INSERT INTO `system_log` VALUES (3912, '[登录成功]', '2020-09-09 16:39:41', 'admin', 3, '111.35.18.246', 'backend/login/login');
INSERT INTO `system_log` VALUES (3913, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-09 16:57:11', 'admin', 3, '111.35.18.246', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (3914, '[登录成功]', '2020-09-09 17:26:54', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3915, '[登录成功]', '2020-09-10 09:18:44', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3916, '[登录成功]', '2020-09-10 10:35:43', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (3917, '[登录成功]', '2020-09-10 10:42:17', 'admin', 3, '42.84.250.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3918, '[登录成功]', '2020-09-10 10:48:12', 'collin', 4, '42.84.250.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3919, '[登录成功]', '2020-09-10 11:16:15', 'collin', 4, '42.84.250.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3920, '[登录成功]', '2020-09-10 11:16:25', 'admin', 3, '42.84.250.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (3921, '[修改设置]', '2020-09-10 11:17:27', 'admin', 3, '42.84.250.89', 'backend/system/config/group/attendance');
INSERT INTO `system_log` VALUES (3922, '[登录成功]', '2020-09-10 19:39:52', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3923, '[添加课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-10 20:20:56', 'admin', 3, '39.71.25.144', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3924, '[添加课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-10 20:23:22', 'admin', 3, '39.71.25.144', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3925, '[添加精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-10 20:31:43', 'admin', 3, '39.71.25.144', 'backend/student/savemoment/type/1');
INSERT INTO `system_log` VALUES (3926, '[登录成功]', '2020-09-11 09:38:43', 'admin', 3, '116.3.204.229', 'backend/login/login');
INSERT INTO `system_log` VALUES (3927, '[登录成功]', '2020-09-11 11:28:04', 'admin', 3, '117.136.92.26', 'backend/login/login');
INSERT INTO `system_log` VALUES (3928, '[登录成功]', '2020-09-11 13:50:44', 'admin', 3, '111.35.18.246', 'backend/login/login');
INSERT INTO `system_log` VALUES (3929, '[登录成功]', '2020-09-11 14:09:09', 'admin', 3, '111.35.18.246', 'backend/login/login');
INSERT INTO `system_log` VALUES (3930, '[登录成功]', '2020-09-11 14:32:03', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (3931, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-11 14:34:46', 'admin', 3, '111.35.18.233', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (3932, '[登录成功]', '2020-09-11 16:53:49', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (3933, '[登录成功]', '2020-09-12 21:04:04', 'admin', 3, '113.235.125.179', 'backend/login/login');
INSERT INTO `system_log` VALUES (3934, '[登录成功]', '2020-09-13 13:26:03', 'admin', 3, '39.71.25.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (3935, '[登录成功]', '2020-09-13 15:54:05', 'admin', 3, '175.167.154.112', 'backend/login/login');
INSERT INTO `system_log` VALUES (3936, '[添加课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-13 16:00:06', 'admin', 3, '175.167.154.112', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3937, '[添加课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-13 16:04:41', 'admin', 3, '175.167.154.112', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3938, '[添加课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-13 16:06:15', 'admin', 3, '175.167.154.112', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3939, '[添加排课计划成功] [MODEL] app\\backend\\model\\CourseScheduleModel', '2020-09-13 21:51:55', 'admin', 3, '39.71.25.144', 'backend/course/saveschedule/type/1');
INSERT INTO `system_log` VALUES (3940, '[登录成功]', '2020-09-14 07:39:17', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (3941, '[登录成功]', '2020-09-14 08:38:21', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (3942, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-14 12:39:50', 'admin', 3, '123.233.17.86', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3943, '[登录成功]', '2020-09-14 14:06:24', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (3944, '[登录成功]', '2020-09-14 16:52:41', 'admin', 3, '175.167.130.41', 'backend/login/login');
INSERT INTO `system_log` VALUES (3945, '[登录成功]', '2020-09-14 17:26:26', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (3946, '[登录成功]', '2020-09-14 20:23:38', 'admin', 3, '42.84.39.69', 'backend/login/login');
INSERT INTO `system_log` VALUES (3947, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-14 20:27:28', 'admin', 3, '42.84.39.69', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (3948, '[登录成功]', '2020-09-14 20:29:09', 'collin', 4, '42.84.39.69', 'backend/login/login');
INSERT INTO `system_log` VALUES (3949, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-14 20:29:33', 'collin', 4, '42.84.39.69', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3950, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-14 20:29:54', 'collin', 4, '42.84.39.69', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3951, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-14 20:41:08', 'collin', 4, '42.84.39.69', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3952, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-14 20:41:16', 'collin', 4, '42.84.39.69', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3953, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-14 20:41:24', 'collin', 4, '42.84.39.69', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3954, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-14 20:41:32', 'collin', 4, '42.84.39.69', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3955, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-14 20:41:40', 'collin', 4, '42.84.39.69', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3956, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-14 20:41:49', 'collin', 4, '42.84.39.69', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3957, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-14 20:41:57', 'collin', 4, '42.84.39.69', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3958, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-09-14 20:42:05', 'collin', 4, '42.84.39.69', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (3959, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-14 20:42:16', 'collin', 4, '42.84.39.69', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (3960, '[登录成功]', '2020-09-14 20:52:05', 'admin', 3, '42.84.39.69', 'backend/login/login');
INSERT INTO `system_log` VALUES (3961, '[添加精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-14 21:06:57', 'admin', 3, '42.84.39.69', 'backend/student/savemoment/type/1');
INSERT INTO `system_log` VALUES (3962, '[添加精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-14 21:08:43', 'admin', 3, '42.84.39.69', 'backend/student/savemoment/type/1');
INSERT INTO `system_log` VALUES (3963, '[登录成功]', '2020-09-15 07:41:30', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (3964, '[登录成功]', '2020-09-15 08:18:50', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (3965, '[登录成功]', '2020-09-15 09:38:18', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (3966, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 10:57:56', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3967, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 10:58:07', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3968, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 10:58:17', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3969, '[添加公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 10:58:39', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3970, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 10:58:57', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3971, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 10:59:08', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3972, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 10:59:20', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3973, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 10:59:28', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3974, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 10:59:36', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3975, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 10:59:47', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3976, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 10:59:52', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3977, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 11:00:00', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3978, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 11:00:07', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3979, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 13:49:48', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3980, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 13:50:05', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3981, '[添加公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-15 13:51:42', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3982, '[登录成功]', '2020-09-15 14:16:50', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (3983, '[登录成功]', '2020-09-15 14:19:18', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (3984, '[登录成功]', '2020-09-15 16:31:58', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (3985, '[登录成功]', '2020-09-15 21:36:59', 'admin', 3, '42.84.39.69', 'backend/login/login');
INSERT INTO `system_log` VALUES (3986, '[迟到补签成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-09-15 21:37:40', 'admin', 3, '42.84.39.69', 'backend/staff/attendanceapply/type/late_apply');
INSERT INTO `system_log` VALUES (3987, '[迟到补签审核成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-09-15 21:37:52', 'admin', 3, '42.84.39.69', 'backend/staff/attendanceapply/type/late_verify');
INSERT INTO `system_log` VALUES (3988, '[登录成功]', '2020-09-15 22:35:17', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (3989, '[登录成功]', '2020-09-16 07:46:46', 'admin', 3, '111.35.18.230', 'backend/login/login');
INSERT INTO `system_log` VALUES (3990, '[登录成功]', '2020-09-16 09:39:04', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (3991, '[登录成功]', '2020-09-16 10:12:40', 'admin', 3, '111.35.18.230', 'backend/login/login');
INSERT INTO `system_log` VALUES (3992, '[登录成功]', '2020-09-16 15:07:06', 'admin', 3, '116.3.192.183', 'backend/login/login');
INSERT INTO `system_log` VALUES (3993, '[登录成功]', '2020-09-16 21:06:54', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (3994, '[修改导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-09-16 21:07:31', 'admin', 3, '123.233.17.86', 'backend/article/createnav/position/home');
INSERT INTO `system_log` VALUES (3995, '[修改导航成功] [MODEL] app\\backend\\model\\NavModel', '2020-09-16 21:09:29', 'admin', 3, '123.233.17.86', 'backend/article/createnav/position/home');
INSERT INTO `system_log` VALUES (3996, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-16 21:10:13', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3997, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-16 21:10:38', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3998, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-16 21:19:09', 'admin', 3, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (3999, '[登录成功]', '2020-09-16 21:31:38', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4000, '[登录成功]', '2020-09-17 07:37:38', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4001, '[登录成功]', '2020-09-17 08:41:07', '刘红', 11, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4002, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-17 08:56:52', '刘红', 11, '111.35.18.233', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4003, '[减少积分1成功] [MODEL] app\\common\\model\\StudentAccount', '2020-09-17 09:41:16', 'admin', 3, '123.233.17.86', 'backend/student/changepoint/type/changePoint');
INSERT INTO `system_log` VALUES (4004, '[减少积分1成功] [MODEL] app\\common\\model\\StudentAccount', '2020-09-17 09:42:23', 'admin', 3, '123.233.17.86', 'backend/student/changepoint/type/changePoint');
INSERT INTO `system_log` VALUES (4005, '[减少积分1成功] [MODEL] app\\common\\model\\StudentAccount', '2020-09-17 09:42:38', 'admin', 3, '123.233.17.86', 'backend/student/changepoint/type/changePoint');
INSERT INTO `system_log` VALUES (4006, '[增加积分1成功] [MODEL] app\\common\\model\\StudentAccount', '2020-09-17 09:44:43', 'admin', 3, '123.233.17.86', 'backend/student/changepoint/type/inc');
INSERT INTO `system_log` VALUES (4007, '[增加积分1成功] [MODEL] app\\common\\model\\StudentAccount', '2020-09-17 09:45:05', 'admin', 3, '123.233.17.86', 'backend/student/changepoint/type/inc');
INSERT INTO `system_log` VALUES (4008, '[减少积分1成功] [MODEL] app\\common\\model\\StudentAccount', '2020-09-17 09:45:14', 'admin', 3, '123.233.17.86', 'backend/student/changepoint/type/dec');
INSERT INTO `system_log` VALUES (4009, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-17 10:14:41', '刘红', 11, '111.35.18.233', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4010, '[登录成功]', '2020-09-17 10:18:59', 'admin', 3, '111.35.18.229', 'backend/login/login');
INSERT INTO `system_log` VALUES (4011, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-17 10:59:28', 'admin', 3, '111.35.18.229', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (4012, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-17 11:03:56', 'admin', 3, '111.35.18.229', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (4013, '[登录成功]', '2020-09-17 13:28:52', 'admin', 3, '111.35.18.229', 'backend/login/login');
INSERT INTO `system_log` VALUES (4014, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-17 13:32:17', 'admin', 3, '111.35.18.229', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (4015, '[登录成功]', '2020-09-17 14:19:02', '刘红', 11, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4016, '[登录成功]', '2020-09-17 16:34:57', '刘红', 11, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4017, '[登录成功]', '2020-09-18 07:42:46', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4018, '[登录成功]', '2020-09-18 10:07:40', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4019, '[登录成功]', '2020-09-18 10:10:36', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4020, '[修改作业成功] [MODEL] app\\backend\\model\\ZoneTaskModel', '2020-09-18 10:22:29', 'admin', 3, '123.233.17.86', 'backend/student/editZoneTask');
INSERT INTO `system_log` VALUES (4021, '[修改作业成功] [MODEL] app\\backend\\model\\ZoneTaskModel', '2020-09-18 10:22:36', 'admin', 3, '123.233.17.86', 'backend/student/editZoneTask');
INSERT INTO `system_log` VALUES (4022, '[作业点评成功] [MODEL] app\\backend\\model\\ZoneModel', '2020-09-18 10:51:09', 'admin', 3, '123.233.17.86', 'backend/student/zoneVerify');
INSERT INTO `system_log` VALUES (4023, '[登录成功]', '2020-09-18 21:30:11', 'admin', 3, '42.84.38.39', 'backend/login/login');
INSERT INTO `system_log` VALUES (4024, '[登录成功]', '2020-09-18 21:54:49', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4025, '[登录成功]', '2020-09-19 10:40:09', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4026, '[登录成功]', '2020-09-19 16:26:32', 'admin', 3, '113.235.117.48', 'backend/login/login');
INSERT INTO `system_log` VALUES (4027, '[请款审批成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-19 16:29:00', 'admin', 3, '113.235.117.48', 'backend/staff/verifyPayout');
INSERT INTO `system_log` VALUES (4028, '[请款支付成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-19 16:29:10', 'admin', 3, '113.235.117.48', 'backend/staff/paidPayout');
INSERT INTO `system_log` VALUES (4029, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-19 16:32:03', 'admin', 3, '113.235.117.48', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4030, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-19 16:33:11', 'admin', 3, '113.235.117.48', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4031, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:34:21', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4032, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:34:31', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4033, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:34:40', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4034, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:34:49', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4035, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:34:58', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4036, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:35:08', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4037, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:35:18', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4038, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:35:28', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4039, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:35:43', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4040, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:35:52', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4041, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:36:02', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4042, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:36:12', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4043, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:36:21', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4044, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:36:30', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4045, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:36:40', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4046, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:36:51', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4047, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:37:01', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4048, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:37:12', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4049, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:37:22', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4050, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:37:32', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4051, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:37:42', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4052, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:37:52', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4053, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:38:02', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4054, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:38:14', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4055, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:38:26', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4056, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:38:36', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4057, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:38:47', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4058, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:38:57', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4059, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:39:06', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4060, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:39:18', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4061, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:39:27', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4062, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:39:36', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4063, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:39:46', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4064, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:39:57', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4065, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:40:06', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4066, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:40:16', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4067, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:40:28', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4068, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:40:39', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4069, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:40:49', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4070, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:42:53', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4071, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-19 16:43:04', 'admin', 3, '113.235.117.48', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4072, '[登录成功]', '2020-09-19 16:46:39', 'admin', 3, '113.235.117.48', 'backend/login/login');
INSERT INTO `system_log` VALUES (4073, '[登录成功]', '2020-09-19 17:03:43', 'collin', 4, '113.235.117.48', 'backend/login/login');
INSERT INTO `system_log` VALUES (4074, '[登录成功]', '2020-09-19 17:04:28', 'collin', 4, '113.235.117.48', 'backend/login/login');
INSERT INTO `system_log` VALUES (4075, '[登录成功]', '2020-09-19 17:04:41', 'admin', 3, '113.235.117.48', 'backend/login/login');
INSERT INTO `system_log` VALUES (4076, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-19 17:05:52', 'admin', 3, '113.235.117.48', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (4077, '[登录成功]', '2020-09-19 17:06:10', 'hanxiaoyan', 12, '112.253.25.52', 'backend/login/login');
INSERT INTO `system_log` VALUES (4078, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-19 17:06:16', 'admin', 3, '113.235.117.48', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (4079, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-19 17:07:00', 'admin', 3, '113.235.117.48', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (4080, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-19 17:07:13', 'admin', 3, '113.235.117.48', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (4081, '[登录成功]', '2020-09-19 17:07:46', 'collin', 4, '113.235.117.48', 'backend/login/login');
INSERT INTO `system_log` VALUES (4082, '[请款审批成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-19 17:15:37', 'hanxiaoyan', 12, '112.253.25.52', 'backend/staff/verifyPayout');
INSERT INTO `system_log` VALUES (4083, '[登录成功]', '2020-09-21 07:24:19', 'admin', 3, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4084, '[登录成功]', '2020-09-21 07:26:02', 'Lane', 13, '112.53.84.135', 'backend/login/login');
INSERT INTO `system_log` VALUES (4085, '[登录成功]', '2020-09-21 07:40:42', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4086, '[登录成功]', '2020-09-22 10:11:53', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4087, '[登录成功]', '2020-09-22 13:48:24', 'admin', 3, '111.35.18.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (4088, '[登录成功]', '2020-09-22 17:11:23', 'admin', 3, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4089, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-22 17:16:33', 'admin', 3, '112.53.84.136', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (4090, '[添加值班成功] [MODEL] app\\backend\\model\\ScheduleModel', '2020-09-22 17:26:13', 'admin', 3, '112.53.84.136', 'backend/schedule/editSchedule');
INSERT INTO `system_log` VALUES (4091, '[添加值班成功] [MODEL] app\\backend\\model\\ScheduleModel', '2020-09-22 17:26:28', 'admin', 3, '112.53.84.136', 'backend/schedule/editSchedule');
INSERT INTO `system_log` VALUES (4092, '[添加值班成功] [MODEL] app\\backend\\model\\ScheduleModel', '2020-09-22 17:26:54', 'admin', 3, '112.53.84.136', 'backend/schedule/editSchedule');
INSERT INTO `system_log` VALUES (4093, '[添加值班成功] [MODEL] app\\backend\\model\\ScheduleModel', '2020-09-22 17:27:09', 'admin', 3, '112.53.84.136', 'backend/schedule/editSchedule');
INSERT INTO `system_log` VALUES (4094, '[添加值班成功] [MODEL] app\\backend\\model\\ScheduleModel', '2020-09-22 17:27:22', 'admin', 3, '112.53.84.136', 'backend/schedule/editSchedule');
INSERT INTO `system_log` VALUES (4095, '[登录成功]', '2020-09-22 17:28:51', 'admin', 3, '111.35.18.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (4096, '[修改设置]', '2020-09-22 17:29:00', 'admin', 3, '112.53.84.136', 'backend/system/config/group/attendance');
INSERT INTO `system_log` VALUES (4097, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-22 17:30:05', 'admin', 3, '111.35.18.236', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (4098, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-22 17:30:43', 'admin', 3, '111.35.18.236', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (4099, '[添加点播视频成功] [MODEL] app\\common\\model\\Vod', '2020-09-22 17:31:24', 'admin', 3, '111.35.18.236', 'backend/tencent/vodupload');
INSERT INTO `system_log` VALUES (4100, '[登录成功]', '2020-09-22 17:32:24', 'admin', 3, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4101, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-22 17:38:11', 'admin', 3, '112.53.84.136', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (4102, '[登录成功]', '2020-09-22 17:39:37', 'admin', 3, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4103, '[登录成功]', '2020-09-22 17:39:54', 'collin', 4, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4104, '[登录成功]', '2020-09-22 17:42:04', 'Edward', 1, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4105, '[登录成功]', '2020-09-22 17:44:32', 'admin', 3, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4106, '[登录成功]', '2020-09-22 17:46:20', 'Edward', 1, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4107, '[登录成功]', '2020-09-22 17:49:24', 'admin', 3, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4108, '[登录成功]', '2020-09-22 17:50:22', 'admin', 3, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4109, '[登录成功]', '2020-09-22 17:50:33', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4110, '[登录成功]', '2020-09-22 17:57:51', 'Edward', 1, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4111, '[登录成功]', '2020-09-23 07:18:03', 'admin', 3, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4112, '[修改设置]', '2020-09-23 07:19:02', 'admin', 3, '112.53.84.136', 'backend/system/config/group/attendance');
INSERT INTO `system_log` VALUES (4113, '[登录成功]', '2020-09-23 07:38:41', '刘红', 11, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4114, '[登录成功]', '2020-09-23 07:39:30', 'Edward', 1, '111.35.18.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (4115, '[登录成功]', '2020-09-23 10:59:15', 'collin', 4, '111.35.18.245', 'backend/login/login');
INSERT INTO `system_log` VALUES (4116, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-23 10:59:54', 'collin', 4, '111.35.18.245', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4117, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-23 11:00:44', 'collin', 4, '111.35.18.245', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4118, '[登录成功]', '2020-09-23 11:18:54', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4119, '[登录成功]', '2020-09-23 11:30:58', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4120, '[添加文章成功] [MODEL] app\\backend\\model\\ArticleModel', '2020-09-23 12:28:42', 'cui', 10, '123.233.17.86', 'backend/article/createarticle/type/2');
INSERT INTO `system_log` VALUES (4121, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-23 12:29:18', 'cui', 10, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4122, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-23 12:30:21', 'cui', 10, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4123, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-23 12:30:29', 'cui', 10, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4124, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-23 12:30:59', 'cui', 10, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4125, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-23 12:31:11', 'cui', 10, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4126, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-23 12:31:24', 'cui', 10, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4127, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-23 12:31:35', 'cui', 10, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4128, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-23 12:31:51', 'cui', 10, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4129, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-23 12:32:03', 'cui', 10, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4130, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-23 12:32:12', 'cui', 10, '123.233.17.86', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4131, '[登录成功]', '2020-09-23 13:15:37', 'collin', 4, '111.35.18.245', 'backend/login/login');
INSERT INTO `system_log` VALUES (4132, '[登录成功]', '2020-09-23 13:16:14', 'collin', 4, '111.35.18.245', 'backend/login/login');
INSERT INTO `system_log` VALUES (4133, '[登录成功]', '2020-09-23 13:47:30', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4134, '[登录成功]', '2020-09-23 17:00:12', 'collin', 4, '111.35.18.245', 'backend/login/login');
INSERT INTO `system_log` VALUES (4135, '[登录成功]', '2020-09-24 07:25:25', 'collin', 4, '112.224.75.155', 'backend/login/login');
INSERT INTO `system_log` VALUES (4136, '[登录成功]', '2020-09-24 07:39:31', '刘红', 11, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4137, '[登录成功]', '2020-09-24 07:43:17', '刘红', 11, '111.35.18.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4138, '[登录成功]', '2020-09-24 08:17:49', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4139, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-24 09:08:49', 'collin', 4, '112.224.75.155', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4140, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-24 09:09:00', 'collin', 4, '112.224.75.155', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4141, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-24 09:09:11', 'collin', 4, '112.224.75.155', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4142, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-24 09:09:21', 'collin', 4, '112.224.75.155', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4143, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-24 09:09:33', 'collin', 4, '112.224.75.155', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4144, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-24 09:09:44', 'collin', 4, '112.224.75.155', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4145, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-24 09:10:00', 'collin', 4, '112.224.75.155', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4146, '[修改课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-09-24 09:10:16', 'collin', 4, '112.224.75.155', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4147, '[登录成功]', '2020-09-24 09:19:49', 'collin', 4, '112.224.75.155', 'backend/login/login');
INSERT INTO `system_log` VALUES (4148, '[登录成功]', '2020-09-24 10:33:09', 'Edward', 1, '111.35.18.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (4149, '[登录成功]', '2020-09-24 15:38:15', 'collin', 4, '112.53.84.135', 'backend/login/login');
INSERT INTO `system_log` VALUES (4150, '[登录成功]', '2020-09-25 18:54:26', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4151, '[登录成功]', '2020-09-25 20:06:51', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4152, '[登录成功]', '2020-09-26 08:29:54', 'collin', 4, '111.35.18.232', 'backend/login/login');
INSERT INTO `system_log` VALUES (4153, '[登录成功]', '2020-09-27 08:41:51', 'Edward', 1, '112.53.84.132', 'backend/login/login');
INSERT INTO `system_log` VALUES (4154, '[登录成功]', '2020-09-27 08:43:18', 'Susan', 8, '112.53.84.132', 'backend/login/login');
INSERT INTO `system_log` VALUES (4155, '[登录成功]', '2020-09-27 08:43:51', 'collin', 4, '112.53.84.132', 'backend/login/login');
INSERT INTO `system_log` VALUES (4156, '[修改请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 10:45:24', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4157, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 10:48:23', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4158, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 10:58:24', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4159, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 10:59:10', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4160, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:00:29', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4161, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:01:57', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4162, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:05:49', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4163, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:09:01', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4164, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:09:56', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4165, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:10:52', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4166, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:11:51', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4167, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:13:10', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4168, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:22:07', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4169, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:22:58', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4170, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:27:23', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4171, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:28:20', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4172, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:29:04', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4173, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:34:00', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4174, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:34:38', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4175, '[登录成功]', '2020-09-27 11:35:57', 'Susan', 8, '112.53.84.132', 'backend/login/login');
INSERT INTO `system_log` VALUES (4176, '[登录成功]', '2020-09-27 11:38:11', 'collin', 4, '112.53.84.132', 'backend/login/login');
INSERT INTO `system_log` VALUES (4177, '[登录成功]', '2020-09-27 11:39:12', 'collin', 4, '112.53.84.132', 'backend/login/login');
INSERT INTO `system_log` VALUES (4178, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:41:39', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4179, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:42:33', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4180, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:50:00', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4181, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:51:54', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4182, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:53:24', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4183, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:54:54', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4184, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:56:42', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4185, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 11:58:03', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4186, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 12:01:09', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4187, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 12:02:51', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4188, '[请款审批成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 12:03:04', 'collin', 4, '112.53.84.132', 'backend/staff/verifyPayout');
INSERT INTO `system_log` VALUES (4189, '[请款支付成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 12:03:11', 'collin', 4, '112.53.84.132', 'backend/staff/paidPayout');
INSERT INTO `system_log` VALUES (4190, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 12:09:18', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4191, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 12:11:21', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4192, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 12:12:25', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4193, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 12:13:17', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4194, '[添加请款成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-27 12:17:50', 'collin', 4, '112.53.84.132', 'backend/staff/savePayout');
INSERT INTO `system_log` VALUES (4195, '[登录成功]', '2020-09-27 12:33:04', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4196, '[登录成功]', '2020-09-27 14:13:48', 'collin', 4, '112.53.84.132', 'backend/login/login');
INSERT INTO `system_log` VALUES (4197, '[登录成功]', '2020-09-27 14:40:55', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4198, '[登录成功]', '2020-09-27 14:43:18', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4199, '[修改成功] [MODEL] app\\backend\\model\\AttachmentModel', '2020-09-27 14:45:26', 'cui', 10, '123.233.17.86', 'backend/attachment/moveImg');
INSERT INTO `system_log` VALUES (4200, '[登录成功]', '2020-09-27 14:52:06', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4201, '[登录成功]', '2020-09-27 14:52:38', 'collin', 4, '112.53.84.132', 'backend/login/login');
INSERT INTO `system_log` VALUES (4202, '[修改成功] [MODEL] app\\backend\\model\\AttachmentModel', '2020-09-27 14:55:33', 'collin', 4, '112.53.84.132', 'backend/attachment/moveImg');
INSERT INTO `system_log` VALUES (4203, '[修改成功] [MODEL] app\\backend\\model\\AttachmentModel', '2020-09-27 14:55:45', 'collin', 4, '112.53.84.132', 'backend/attachment/moveImg');
INSERT INTO `system_log` VALUES (4204, '[登录成功]', '2020-09-27 15:12:55', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4205, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-09-27 15:13:29', 'cui', 10, '123.233.17.86', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (4206, '[登录成功]', '2020-09-27 15:21:01', 'collin', 4, '112.53.84.132', 'backend/login/login');
INSERT INTO `system_log` VALUES (4207, '[登录成功]', '2020-09-27 15:21:47', 'collin', 4, '112.53.84.132', 'backend/login/login');
INSERT INTO `system_log` VALUES (4208, '[添加精彩瞬间成功] [MODEL] app\\backend\\model\\MediaModel', '2020-09-27 15:23:38', 'collin', 4, '112.53.84.132', 'backend/student/savemoment/type/1');
INSERT INTO `system_log` VALUES (4209, '[登录成功]', '2020-09-28 07:15:42', 'collin', 4, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4210, '[登录成功]', '2020-09-28 08:39:17', 'collin', 4, '111.35.18.245', 'backend/login/login');
INSERT INTO `system_log` VALUES (4211, '[登录成功]', '2020-09-28 08:39:18', 'collin', 4, '111.35.18.245', 'backend/login/login');
INSERT INTO `system_log` VALUES (4212, '[登录成功]', '2020-09-28 08:39:19', 'collin', 4, '111.35.18.245', 'backend/login/login');
INSERT INTO `system_log` VALUES (4213, '[登录成功]', '2020-09-28 10:08:03', 'collin', 4, '111.35.18.245', 'backend/login/login');
INSERT INTO `system_log` VALUES (4214, '[登录成功]', '2020-09-28 11:09:09', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4215, '[添加学员成功] [MODEL] app\\backend\\model\\StudentModel', '2020-09-28 20:52:02', 'cui', 10, '123.233.17.86', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (4216, '[添加学员成功] [MODEL] app\\backend\\model\\StudentModel', '2020-09-28 20:55:44', 'cui', 10, '123.233.17.86', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (4217, '[修改学员成功] [MODEL] app\\backend\\model\\StudentModel', '2020-09-28 20:55:50', 'cui', 10, '123.233.17.86', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (4218, '[登录成功]', '2020-09-28 21:08:08', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4219, '[添加文章成功] [MODEL] app\\backend\\model\\ArticleModel', '2020-09-28 21:16:33', 'cui', 10, '123.233.17.86', 'backend/article/createarticle/type/1');
INSERT INTO `system_log` VALUES (4220, '[添加文章成功] [MODEL] app\\backend\\model\\ArticleModel', '2020-09-28 21:17:52', 'cui', 10, '123.233.17.86', 'backend/article/createarticle/type/1');
INSERT INTO `system_log` VALUES (4221, '[修改文章成功] [MODEL] app\\backend\\model\\ArticleModel', '2020-09-28 21:18:06', 'cui', 10, '123.233.17.86', 'backend/article/createarticle/type/2');
INSERT INTO `system_log` VALUES (4222, '[登录成功]', '2020-09-28 21:22:00', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4223, '[修改文章分类成功] [MODEL] app\\backend\\model\\ArticleCategoryModel', '2020-09-28 21:24:01', 'cui', 10, '123.233.17.86', 'backend/article/createCategory');
INSERT INTO `system_log` VALUES (4224, '[添加文章分类成功] [MODEL] app\\backend\\model\\ArticleCategoryModel', '2020-09-28 21:24:15', 'cui', 10, '123.233.17.86', 'backend/article/createCategory');
INSERT INTO `system_log` VALUES (4225, '[修改文章成功] [MODEL] app\\backend\\model\\ArticleModel', '2020-09-28 21:26:27', 'cui', 10, '123.233.17.86', 'backend/article/createarticle/type/1');
INSERT INTO `system_log` VALUES (4226, '[修改设置]', '2020-09-28 21:35:08', 'cui', 10, '123.233.17.86', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (4227, '[登录成功]', '2020-09-29 08:18:18', 'collin', 4, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4228, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-29 08:21:31', 'collin', 4, '112.53.84.136', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4229, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-29 08:21:41', 'collin', 4, '112.53.84.136', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4230, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-29 08:21:50', 'collin', 4, '112.53.84.136', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4231, '[修改班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-09-29 08:22:00', 'collin', 4, '112.53.84.136', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4232, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-29 09:06:34', 'collin', 4, '112.53.84.136', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4233, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-29 09:06:43', 'collin', 4, '112.53.84.136', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4234, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-29 09:06:51', 'collin', 4, '112.53.84.136', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4235, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-29 09:07:00', 'collin', 4, '112.53.84.136', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4236, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-09-29 09:08:31', 'collin', 4, '112.53.84.136', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4237, '[登录成功]', '2020-09-29 14:22:36', 'collin', 4, '112.53.84.136', 'backend/login/login');
INSERT INTO `system_log` VALUES (4238, '[登录成功]', '2020-09-29 15:33:13', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4239, '[修改设置]', '2020-09-29 15:39:13', 'cui', 10, '123.233.17.86', 'backend/system/config/group/system');
INSERT INTO `system_log` VALUES (4240, '[登录成功]', '2020-09-29 15:53:00', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4241, '[登录成功]', '2020-09-29 15:54:24', 'cui', 10, '112.3.68.104', 'backend/login/login');
INSERT INTO `system_log` VALUES (4242, '[登录成功]', '2020-09-29 15:56:32', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4243, '[登录成功]', '2020-09-29 16:00:42', 'cui', 10, '112.224.19.201', 'backend/login/login');
INSERT INTO `system_log` VALUES (4244, '[登录成功]', '2020-09-29 16:04:15', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4245, '[登录成功]', '2020-09-29 23:15:27', 'cui', 10, '171.213.110.50', 'backend/login/login');
INSERT INTO `system_log` VALUES (4246, '[登录成功]', '2020-09-29 23:55:33', 'cui', 10, '120.230.66.3', 'backend/login/login');
INSERT INTO `system_log` VALUES (4247, '[登录成功]', '2020-09-30 10:05:58', 'cui', 10, '123.233.17.86', 'backend/login/login');
INSERT INTO `system_log` VALUES (4248, '[登录成功]', '2020-09-30 10:11:40', 'cui', 10, '120.36.255.208', 'backend/login/login');
INSERT INTO `system_log` VALUES (4249, '[登录成功]', '2020-09-30 10:25:19', 'cui', 10, '113.109.206.101', 'backend/login/login');
INSERT INTO `system_log` VALUES (4250, '[登录成功]', '2020-09-30 11:11:36', 'cui', 10, '125.35.105.118', 'backend/login/login');
INSERT INTO `system_log` VALUES (4251, '[登录成功]', '2020-09-30 11:23:23', 'cui', 10, '222.94.237.19', 'backend/login/login');
INSERT INTO `system_log` VALUES (4252, '[登录成功]', '2020-09-30 11:52:22', 'cui', 10, '222.76.112.41', 'backend/login/login');
INSERT INTO `system_log` VALUES (4253, '[登录成功]', '2020-09-30 13:20:45', 'cui', 10, '123.148.141.173', 'backend/login/login');
INSERT INTO `system_log` VALUES (4254, '[登录成功]', '2020-09-30 13:28:57', 'cui', 10, '59.61.238.146', 'backend/login/login');
INSERT INTO `system_log` VALUES (4255, '[登录成功]', '2020-09-30 14:31:34', 'cui', 10, '211.140.48.2', 'backend/login/login');
INSERT INTO `system_log` VALUES (4256, '[登录成功]', '2020-09-30 14:41:05', 'cui', 10, '27.203.39.36', 'backend/login/login');
INSERT INTO `system_log` VALUES (4257, '[登录成功]', '2020-09-30 14:46:08', 'cui', 10, '27.203.39.36', 'backend/login/login');
INSERT INTO `system_log` VALUES (4258, '[登录成功]', '2020-09-30 14:47:05', 'cui', 10, '27.203.39.36', 'backend/login/login');
INSERT INTO `system_log` VALUES (4259, '[登录成功]', '2020-09-30 14:47:09', 'cui', 10, '27.203.39.36', 'backend/login/login');
INSERT INTO `system_log` VALUES (4260, '[登录成功]', '2020-09-30 14:47:58', 'cui', 10, '27.203.39.36', 'backend/login/login');
INSERT INTO `system_log` VALUES (4261, '[登录成功]', '2020-09-30 14:48:47', 'cui', 10, '27.203.39.36', 'backend/login/login');
INSERT INTO `system_log` VALUES (4262, '[请款审批成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-09-30 14:52:45', 'cui', 10, '27.203.39.36', 'backend/staff/verifyPayout');
INSERT INTO `system_log` VALUES (4263, '[登录成功]', '2020-09-30 15:35:18', 'cui', 10, '42.232.66.203', 'backend/login/login');
INSERT INTO `system_log` VALUES (4264, '[登录成功]', '2020-09-30 16:18:35', 'cui', 10, '103.20.249.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (4265, '[迟到补签成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-09-30 16:20:16', 'cui', 10, '103.20.249.236', 'backend/staff/attendanceapply/type/late_apply');
INSERT INTO `system_log` VALUES (4266, '[迟到补签审核成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-09-30 16:20:22', 'cui', 10, '103.20.249.236', 'backend/staff/attendanceapply/type/late_verify');
INSERT INTO `system_log` VALUES (4267, '[登录成功]', '2020-09-30 16:41:13', 'cui', 10, '111.207.25.230', 'backend/login/login');
INSERT INTO `system_log` VALUES (4268, '[登录成功]', '2020-09-30 18:40:23', 'cui', 10, '39.154.15.234', 'backend/login/login');
INSERT INTO `system_log` VALUES (4269, '[登录成功]', '2020-09-30 22:47:04', 'cui', 10, '223.150.180.117', 'backend/login/login');
INSERT INTO `system_log` VALUES (4270, '[登录成功]', '2020-10-01 21:14:26', 'cui', 10, '27.39.73.223', 'backend/login/login');
INSERT INTO `system_log` VALUES (4271, '[登录成功]', '2020-10-02 15:02:12', 'cui', 10, '27.17.106.186', 'backend/login/login');
INSERT INTO `system_log` VALUES (4272, '[登录成功]', '2020-10-02 20:50:43', 'cui', 10, '112.44.104.190', 'backend/login/login');
INSERT INTO `system_log` VALUES (4273, '[登录成功]', '2020-10-03 14:36:11', 'cui', 10, '125.210.247.218', 'backend/login/login');
INSERT INTO `system_log` VALUES (4274, '[登录成功]', '2020-10-03 15:56:59', 'cui', 10, '117.157.99.123', 'backend/login/login');
INSERT INTO `system_log` VALUES (4275, '[登录成功]', '2020-10-03 18:21:16', 'cui', 10, '218.82.138.89', 'backend/login/login');
INSERT INTO `system_log` VALUES (4276, '[请款支付成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-10-03 18:22:24', 'cui', 10, '218.82.138.89', 'backend/staff/paidPayout');
INSERT INTO `system_log` VALUES (4277, '[请款审批成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-10-03 18:22:30', 'cui', 10, '218.82.138.89', 'backend/staff/verifyPayout');
INSERT INTO `system_log` VALUES (4278, '[登录成功]', '2020-10-03 22:48:30', 'cui', 10, '211.97.121.78', 'backend/login/login');
INSERT INTO `system_log` VALUES (4279, '[登录成功]', '2020-10-04 18:41:06', 'cui', 10, '113.205.111.251', 'backend/login/login');
INSERT INTO `system_log` VALUES (4280, '[登录成功]', '2020-10-05 08:09:59', 'cui', 10, '111.196.210.152', 'backend/login/login');
INSERT INTO `system_log` VALUES (4281, '[登录成功]', '2020-10-05 09:16:05', 'cui', 10, '218.56.224.4', 'backend/login/login');
INSERT INTO `system_log` VALUES (4282, '[登录成功]', '2020-10-05 09:49:35', 'cui', 10, '222.210.136.180', 'backend/login/login');
INSERT INTO `system_log` VALUES (4283, '[登录成功]', '2020-10-05 14:39:53', 'cui', 10, '112.10.75.181', 'backend/login/login');
INSERT INTO `system_log` VALUES (4284, '[登录成功]', '2020-10-05 18:44:21', 'cui', 10, '117.148.92.68', 'backend/login/login');
INSERT INTO `system_log` VALUES (4285, '[登录成功]', '2020-10-05 19:19:38', 'cui', 10, '112.50.59.93', 'backend/login/login');
INSERT INTO `system_log` VALUES (4286, '[登录成功]', '2020-10-05 21:11:53', 'cui', 10, '114.96.156.213', 'backend/login/login');
INSERT INTO `system_log` VALUES (4287, '[登录成功]', '2020-10-05 22:03:23', 'cui', 10, '114.96.156.213', 'backend/login/login');
INSERT INTO `system_log` VALUES (4288, '[登录成功]', '2020-10-06 07:02:36', 'cui', 10, '106.16.154.59', 'backend/login/login');
INSERT INTO `system_log` VALUES (4289, '[登录成功]', '2020-10-06 13:20:24', 'cui', 10, '27.218.200.79', 'backend/login/login');
INSERT INTO `system_log` VALUES (4290, '[登录成功]', '2020-10-06 14:59:23', 'cui', 10, '223.87.242.145', 'backend/login/login');
INSERT INTO `system_log` VALUES (4291, '[登录成功]', '2020-10-06 15:56:00', 'cui', 10, '114.101.152.141', 'backend/login/login');
INSERT INTO `system_log` VALUES (4292, '[登录成功]', '2020-10-06 19:12:34', 'cui', 10, '175.165.21.57', 'backend/login/login');
INSERT INTO `system_log` VALUES (4293, '[登录成功]', '2020-10-06 19:31:50', 'cui', 10, '175.165.21.57', 'backend/login/login');
INSERT INTO `system_log` VALUES (4294, '[登录成功]', '2020-10-06 20:17:57', 'cui', 10, '101.70.131.255', 'backend/login/login');
INSERT INTO `system_log` VALUES (4295, '[登录成功]', '2020-10-07 11:20:36', 'cui', 10, '175.165.21.57', 'backend/login/login');
INSERT INTO `system_log` VALUES (4296, '[登录成功]', '2020-10-07 12:27:11', 'cui', 10, '14.192.247.92', 'backend/login/login');
INSERT INTO `system_log` VALUES (4297, '[登录成功]', '2020-10-07 13:04:44', 'cui', 10, '58.20.75.154', 'backend/login/login');
INSERT INTO `system_log` VALUES (4298, '[登录成功]', '2020-10-07 13:07:11', 'cui', 10, '36.7.87.91', 'backend/login/login');
INSERT INTO `system_log` VALUES (4299, '[登录成功]', '2020-10-07 14:46:15', 'cui', 10, '27.218.200.79', 'backend/login/login');
INSERT INTO `system_log` VALUES (4300, '[登录成功]', '2020-10-07 16:19:33', 'cui', 10, '113.109.204.124', 'backend/login/login');
INSERT INTO `system_log` VALUES (4301, '[登录成功]', '2020-10-07 21:42:15', 'cui', 10, '27.18.103.208', 'backend/login/login');
INSERT INTO `system_log` VALUES (4302, '[登录成功]', '2020-10-08 00:56:15', 'cui', 10, '39.149.37.91', 'backend/login/login');
INSERT INTO `system_log` VALUES (4303, '[登录成功]', '2020-10-08 13:51:19', 'cui', 10, '36.153.57.202', 'backend/login/login');
INSERT INTO `system_log` VALUES (4304, '[登录成功]', '2020-10-08 16:59:20', 'cui', 10, '58.62.182.100', 'backend/login/login');
INSERT INTO `system_log` VALUES (4305, '[登录成功]', '2020-10-08 21:35:03', 'cui', 10, '111.32.65.31', 'backend/login/login');
INSERT INTO `system_log` VALUES (4306, '[登录成功]', '2020-10-08 21:48:30', 'cui', 10, '123.232.61.161', 'backend/login/login');
INSERT INTO `system_log` VALUES (4307, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-10-08 21:49:01', 'cui', 10, '123.232.61.161', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (4308, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-10-08 21:49:07', 'cui', 10, '123.232.61.161', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (4309, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-10-08 21:49:15', 'cui', 10, '123.232.61.161', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (4310, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-10-08 21:49:20', 'cui', 10, '123.232.61.161', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (4311, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-10-08 21:49:27', 'cui', 10, '123.232.61.161', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (4312, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-10-08 21:49:34', 'cui', 10, '123.232.61.161', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (4313, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-10-08 21:49:43', 'cui', 10, '123.232.61.161', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (4314, '[修改课次成功] [MODEL] app\\backend\\model\\CourseTimesModel', '2020-10-08 21:49:50', 'cui', 10, '123.232.61.161', 'backend/course/saveTimes');
INSERT INTO `system_log` VALUES (4315, '[登录成功]', '2020-10-08 23:03:37', 'cui', 10, '36.17.90.153', 'backend/login/login');
INSERT INTO `system_log` VALUES (4316, '[登录成功]', '2020-10-08 23:09:04', 'cui', 10, '123.232.61.161', 'backend/login/login');
INSERT INTO `system_log` VALUES (4317, '[登录成功]', '2020-10-09 02:30:34', 'cui', 10, '112.67.195.212', 'backend/login/login');
INSERT INTO `system_log` VALUES (4318, '[登录成功]', '2020-10-09 09:34:43', 'cui', 10, '58.51.67.208', 'backend/login/login');
INSERT INTO `system_log` VALUES (4319, '[登录成功]', '2020-10-09 09:58:58', 'cui', 10, '125.127.51.90', 'backend/login/login');
INSERT INTO `system_log` VALUES (4320, '[登录成功]', '2020-10-09 10:39:17', 'cui', 10, '171.9.24.105', 'backend/login/login');
INSERT INTO `system_log` VALUES (4321, '[登录成功]', '2020-10-09 13:48:55', 'cui', 10, '49.77.229.97', 'backend/login/login');
INSERT INTO `system_log` VALUES (4322, '[登录成功]', '2020-10-09 13:50:23', 'cui', 10, '49.77.229.97', 'backend/login/login');
INSERT INTO `system_log` VALUES (4323, '[登录成功]', '2020-10-09 23:22:00', 'cui', 10, '113.206.120.224', 'backend/login/login');
INSERT INTO `system_log` VALUES (4324, '[登录成功]', '2020-10-10 07:30:39', 'cui', 10, '120.229.113.41', 'backend/login/login');
INSERT INTO `system_log` VALUES (4325, '[登录成功]', '2020-10-10 09:49:16', 'cui', 10, '124.90.38.197', 'backend/login/login');
INSERT INTO `system_log` VALUES (4326, '[登录成功]', '2020-10-10 12:46:55', 'cui', 10, '124.89.119.90', 'backend/login/login');
INSERT INTO `system_log` VALUES (4327, '[登录成功]', '2020-10-10 14:08:25', 'cui', 10, '115.60.18.4', 'backend/login/login');
INSERT INTO `system_log` VALUES (4328, '[登录成功]', '2020-10-10 14:32:04', 'cui', 10, '119.139.196.48', 'backend/login/login');
INSERT INTO `system_log` VALUES (4329, '[登录成功]', '2020-10-10 14:38:49', 'cui', 10, '114.102.145.166', 'backend/login/login');
INSERT INTO `system_log` VALUES (4330, '[登录成功]', '2020-10-10 15:15:44', 'cui', 10, '49.4.221.187', 'backend/login/login');
INSERT INTO `system_log` VALUES (4331, '[登录成功]', '2020-10-10 15:28:23', 'cui', 10, '112.10.88.93', 'backend/login/login');
INSERT INTO `system_log` VALUES (4332, '[登录成功]', '2020-10-10 16:27:59', 'cui', 10, '1.62.124.2', 'backend/login/login');
INSERT INTO `system_log` VALUES (4333, '[登录成功]', '2020-10-10 17:13:03', 'cui', 10, '113.116.24.221', 'backend/login/login');
INSERT INTO `system_log` VALUES (4334, '[登录成功]', '2020-10-10 17:20:23', 'cui', 10, '117.89.226.78', 'backend/login/login');
INSERT INTO `system_log` VALUES (4335, '[登录成功]', '2020-10-10 17:39:14', 'cui', 10, '117.89.226.78', 'backend/login/login');
INSERT INTO `system_log` VALUES (4336, '[登录成功]', '2020-10-10 17:56:50', 'cui', 10, '49.118.197.205', 'backend/login/login');
INSERT INTO `system_log` VALUES (4337, '[登录成功]', '2020-10-10 18:05:21', 'cui', 10, '222.74.5.247', 'backend/login/login');
INSERT INTO `system_log` VALUES (4338, '[登录成功]', '2020-10-10 19:11:44', 'cui', 10, '114.241.218.198', 'backend/login/login');
INSERT INTO `system_log` VALUES (4339, '[登录成功]', '2020-10-10 20:12:06', 'cui', 10, '123.144.61.29', 'backend/login/login');
INSERT INTO `system_log` VALUES (4340, '[请款审批成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-10-10 20:13:25', 'cui', 10, '123.144.61.29', 'backend/staff/verifyPayout');
INSERT INTO `system_log` VALUES (4341, '[登录成功]', '2020-10-11 02:09:48', 'cui', 10, '39.128.24.61', 'backend/login/login');
INSERT INTO `system_log` VALUES (4342, '[登录成功]', '2020-10-11 11:04:38', 'cui', 10, '211.69.200.202', 'backend/login/login');
INSERT INTO `system_log` VALUES (4343, '[登录成功]', '2020-10-11 11:44:10', 'cui', 10, '117.188.21.149', 'backend/login/login');
INSERT INTO `system_log` VALUES (4344, '[登录成功]', '2020-10-11 22:27:50', 'cui', 10, '112.10.88.174', 'backend/login/login');
INSERT INTO `system_log` VALUES (4345, '[登录成功]', '2020-10-12 09:08:58', 'cui', 10, '117.89.226.78', 'backend/login/login');
INSERT INTO `system_log` VALUES (4346, '[登录成功]', '2020-10-12 09:16:16', 'cui', 10, '117.89.226.78', 'backend/login/login');
INSERT INTO `system_log` VALUES (4347, '[申请请假成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-10-12 09:37:32', 'cui', 10, '117.89.226.78', 'backend/staff/saveOffduty');
INSERT INTO `system_log` VALUES (4348, '[审批成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-10-12 09:37:42', 'cui', 10, '117.89.226.78', 'backend/staff/verifyOffduty');
INSERT INTO `system_log` VALUES (4349, '[申请请假成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-10-12 09:38:44', 'cui', 10, '117.89.226.78', 'backend/staff/saveOffduty');
INSERT INTO `system_log` VALUES (4350, '[登录成功]', '2020-10-12 10:34:06', 'cui', 10, '183.15.206.46', 'backend/login/login');
INSERT INTO `system_log` VALUES (4351, '[登录成功]', '2020-10-12 11:30:47', 'cui', 10, '183.195.226.178', 'backend/login/login');
INSERT INTO `system_log` VALUES (4352, '[审批成功] [MODEL] app\\backend\\model\\StaffOffdutyModel', '2020-10-12 11:31:24', 'cui', 10, '183.195.226.178', 'backend/staff/verifyOffduty');
INSERT INTO `system_log` VALUES (4353, '[登录成功]', '2020-10-12 11:51:12', 'cui', 10, '119.101.137.159', 'backend/login/login');
INSERT INTO `system_log` VALUES (4354, '[登录成功]', '2020-10-12 14:07:07', 'cui', 10, '114.100.87.182', 'backend/login/login');
INSERT INTO `system_log` VALUES (4355, '[登录成功]', '2020-10-12 15:14:33', 'cui', 10, '122.245.123.121', 'backend/login/login');
INSERT INTO `system_log` VALUES (4356, '[登录成功]', '2020-10-12 15:22:51', 'cui', 10, '119.128.145.254', 'backend/login/login');
INSERT INTO `system_log` VALUES (4357, '[登录成功]', '2020-10-12 15:32:21', 'cui', 10, '113.89.237.232', 'backend/login/login');
INSERT INTO `system_log` VALUES (4358, '[登录成功]', '2020-10-12 16:31:00', 'cui', 10, '60.177.93.144', 'backend/login/login');
INSERT INTO `system_log` VALUES (4359, '[登录成功]', '2020-10-12 18:39:02', 'cui', 10, '123.232.61.161', 'backend/login/login');
INSERT INTO `system_log` VALUES (4360, '[登录成功]', '2020-10-12 19:33:15', 'cui', 10, '123.232.61.161', 'backend/login/login');
INSERT INTO `system_log` VALUES (4361, '[登录成功]', '2020-10-12 19:49:27', 'cui', 10, '112.96.39.68', 'backend/login/login');
INSERT INTO `system_log` VALUES (4362, '[登录成功]', '2020-10-12 20:05:04', 'cui', 10, '183.24.219.69', 'backend/login/login');
INSERT INTO `system_log` VALUES (4363, '[登录成功]', '2020-10-13 09:17:34', 'cui', 10, '218.88.23.74', 'backend/login/login');
INSERT INTO `system_log` VALUES (4364, '[登录成功]', '2020-10-13 09:18:42', 'cui', 10, '122.247.155.99', 'backend/login/login');
INSERT INTO `system_log` VALUES (4365, '[登录成功]', '2020-10-13 09:23:56', 'cui', 10, '117.88.223.176', 'backend/login/login');
INSERT INTO `system_log` VALUES (4366, '[登录成功]', '2020-10-13 11:19:55', 'cui', 10, '124.64.10.9', 'backend/login/login');
INSERT INTO `system_log` VALUES (4367, '[登录成功]', '2020-10-13 11:30:48', 'cui', 10, '171.117.44.99', 'backend/login/login');
INSERT INTO `system_log` VALUES (4368, '[登录成功]', '2020-10-13 14:37:40', 'cui', 10, '117.89.133.200', 'backend/login/login');
INSERT INTO `system_log` VALUES (4369, '[登录成功]', '2020-10-13 14:46:31', 'cui', 10, '60.26.247.219', 'backend/login/login');
INSERT INTO `system_log` VALUES (4370, '[登录成功]', '2020-10-13 15:27:22', 'cui', 10, '8.210.55.117', 'backend/login/login');
INSERT INTO `system_log` VALUES (4371, '[登录成功]', '2020-10-13 16:13:15', 'cui', 10, '60.208.131.178', 'backend/login/login');
INSERT INTO `system_log` VALUES (4372, '[登录成功]', '2020-10-13 16:22:47', 'cui', 10, '106.37.229.246', 'backend/login/login');
INSERT INTO `system_log` VALUES (4373, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-10-13 16:24:15', 'cui', 10, '106.37.229.246', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4374, '[登录成功]', '2020-10-13 19:01:20', 'cui', 10, '58.22.120.58', 'backend/login/login');
INSERT INTO `system_log` VALUES (4375, '[登录成功]', '2020-10-14 09:01:36', 'cui', 10, '117.88.223.176', 'backend/login/login');
INSERT INTO `system_log` VALUES (4376, '[登录成功]', '2020-10-14 10:11:07', 'cui', 10, '117.88.223.176', 'backend/login/login');
INSERT INTO `system_log` VALUES (4377, '[添加学员成功] [MODEL] app\\backend\\model\\StudentModel', '2020-10-14 10:11:41', 'cui', 10, '117.88.223.176', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (4378, '[登录成功]', '2020-10-14 14:33:47', 'cui', 10, '123.124.95.166', 'backend/login/login');
INSERT INTO `system_log` VALUES (4379, '[添加学员成功] [MODEL] app\\backend\\model\\StudentModel', '2020-10-14 16:29:39', 'cui', 10, '117.88.223.176', 'backend/student/saveStudent');
INSERT INTO `system_log` VALUES (4380, '[登录成功]', '2020-10-14 16:48:47', 'cui', 10, '27.11.241.231', 'backend/login/login');
INSERT INTO `system_log` VALUES (4381, '[添加课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-10-14 16:51:27', 'cui', 10, '27.11.241.231', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4382, '[添加试题分类成功] [MODEL] app\\backend\\model\\QuestionCategoryModel', '2020-10-14 16:53:50', 'cui', 10, '27.11.241.231', 'backend/test/saveCategory');
INSERT INTO `system_log` VALUES (4383, '[添加知识点讲解成功] [MODEL] app\\backend\\model\\H5Model', '2020-10-14 16:54:27', 'cui', 10, '27.11.241.231', 'backend/knowledge/saveh5/kid/43');
INSERT INTO `system_log` VALUES (4384, '[修改管辖人员成功] [MODEL] app\\backend\\model\\StaffModel', '2020-10-14 16:54:48', 'cui', 10, '27.11.241.231', 'backend/staff/manageStaff');
INSERT INTO `system_log` VALUES (4385, '[迟到补签成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-10-14 16:54:55', 'cui', 10, '27.11.241.231', 'backend/staff/attendanceapply/type/late_apply');
INSERT INTO `system_log` VALUES (4386, '[迟到补签审核成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-10-14 16:55:00', 'cui', 10, '27.11.241.231', 'backend/staff/attendanceapply/type/late_verify');
INSERT INTO `system_log` VALUES (4387, '[迟到补签成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-10-14 16:55:05', 'cui', 10, '27.11.241.231', 'backend/staff/attendanceapply/type/late_apply');
INSERT INTO `system_log` VALUES (4388, '[迟到补签审核成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-10-14 16:55:09', 'cui', 10, '27.11.241.231', 'backend/staff/attendanceapply/type/late_verify');
INSERT INTO `system_log` VALUES (4389, '[添加知识点成功] [MODEL] app\\backend\\model\\KnowledgeModel', '2020-10-14 16:56:22', 'cui', 10, '27.11.241.231', 'backend/knowledge/saveKnowledge');
INSERT INTO `system_log` VALUES (4390, '[登录成功]', '2020-10-14 17:04:01', 'cui', 10, '58.62.93.149', 'backend/login/login');
INSERT INTO `system_log` VALUES (4391, '[登录成功]', '2020-10-14 18:32:51', 'cui', 10, '119.108.98.8', 'backend/login/login');
INSERT INTO `system_log` VALUES (4392, '[登录成功]', '2020-10-14 20:28:29', 'cui', 10, '171.213.127.148', 'backend/login/login');
INSERT INTO `system_log` VALUES (4393, '[登录成功]', '2020-10-14 21:43:23', 'cui', 10, '222.247.19.85', 'backend/login/login');
INSERT INTO `system_log` VALUES (4394, '[登录成功]', '2020-10-14 22:34:45', 'cui', 10, '222.90.229.184', 'backend/login/login');
INSERT INTO `system_log` VALUES (4395, '[登录成功]', '2020-10-15 09:36:25', 'cui', 10, '117.89.226.78', 'backend/login/login');
INSERT INTO `system_log` VALUES (4396, '[登录成功]', '2020-10-15 10:03:24', 'cui', 10, '223.74.62.149', 'backend/login/login');
INSERT INTO `system_log` VALUES (4397, '[登录成功]', '2020-10-15 10:09:22', 'cui', 10, '223.74.62.149', 'backend/login/login');
INSERT INTO `system_log` VALUES (4398, '[登录成功]', '2020-10-15 10:12:22', 'cui', 10, '14.153.185.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (4399, '[登录成功]', '2020-10-15 10:57:08', 'cui', 10, '113.247.58.178', 'backend/login/login');
INSERT INTO `system_log` VALUES (4400, '[登录成功]', '2020-10-15 11:19:53', 'cui', 10, '119.139.196.12', 'backend/login/login');
INSERT INTO `system_log` VALUES (4401, '[登录成功]', '2020-10-15 11:48:04', 'cui', 10, '182.200.69.114', 'backend/login/login');
INSERT INTO `system_log` VALUES (4402, '[登录成功]', '2020-10-15 13:15:23', 'cui', 10, '101.70.143.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (4403, '[登录成功]', '2020-10-15 13:35:11', 'cui', 10, '117.88.223.176', 'backend/login/login');
INSERT INTO `system_log` VALUES (4404, '[登录成功]', '2020-10-15 14:04:52', 'cui', 10, '218.206.170.118', 'backend/login/login');
INSERT INTO `system_log` VALUES (4405, '[登录成功]', '2020-10-15 14:40:49', 'cui', 10, '125.77.89.204', 'backend/login/login');
INSERT INTO `system_log` VALUES (4406, '[登录成功]', '2020-10-15 15:33:55', 'cui', 10, '222.160.37.14', 'backend/login/login');
INSERT INTO `system_log` VALUES (4407, '[登录成功]', '2020-10-15 16:02:14', 'cui', 10, '220.189.212.106', 'backend/login/login');
INSERT INTO `system_log` VALUES (4408, '[登录成功]', '2020-10-15 19:42:30', 'cui', 10, '223.104.111.199', 'backend/login/login');
INSERT INTO `system_log` VALUES (4409, '[登录成功]', '2020-10-15 21:56:33', 'cui', 10, '112.227.176.79', 'backend/login/login');
INSERT INTO `system_log` VALUES (4410, '[登录成功]', '2020-10-16 13:16:55', 'cui', 10, '116.21.125.120', 'backend/login/login');
INSERT INTO `system_log` VALUES (4411, '[登录成功]', '2020-10-16 13:32:41', 'cui', 10, '117.88.223.176', 'backend/login/login');
INSERT INTO `system_log` VALUES (4412, '[添加课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-10-16 13:33:12', 'cui', 10, '117.88.223.176', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4413, '[登录成功]', '2020-10-16 13:42:25', 'cui', 10, '61.142.233.213', 'backend/login/login');
INSERT INTO `system_log` VALUES (4414, '[登录成功]', '2020-10-16 14:12:26', 'cui', 10, '123.139.168.25', 'backend/login/login');
INSERT INTO `system_log` VALUES (4415, '[登录成功]', '2020-10-16 15:49:55', 'cui', 10, '121.35.103.104', 'backend/login/login');
INSERT INTO `system_log` VALUES (4416, '[登录成功]', '2020-10-16 15:54:23', 'cui', 10, '113.118.198.125', 'backend/login/login');
INSERT INTO `system_log` VALUES (4417, '[添加课程成功] [MODEL] app\\backend\\model\\CourseModel', '2020-10-16 15:56:01', 'cui', 10, '113.118.198.125', 'backend/course/saveCourse');
INSERT INTO `system_log` VALUES (4418, '[登录成功]', '2020-10-16 16:14:04', 'cui', 10, '113.118.198.125', 'backend/login/login');
INSERT INTO `system_log` VALUES (4419, '[登录成功]', '2020-10-16 17:18:31', 'cui', 10, '123.233.117.42', 'backend/login/login');
INSERT INTO `system_log` VALUES (4420, '[登录成功]', '2020-10-17 09:00:37', 'cui', 10, '114.228.207.110', 'backend/login/login');
INSERT INTO `system_log` VALUES (4421, '[登录成功]', '2020-10-17 09:40:13', 'cui', 10, '125.40.90.47', 'backend/login/login');
INSERT INTO `system_log` VALUES (4422, '[登录成功]', '2020-10-17 10:07:56', 'cui', 10, '183.160.1.77', 'backend/login/login');
INSERT INTO `system_log` VALUES (4423, '[登录成功]', '2020-10-17 10:35:49', 'cui', 10, '115.152.76.134', 'backend/login/login');
INSERT INTO `system_log` VALUES (4424, '[登录成功]', '2020-10-17 22:19:18', 'cui', 10, '183.193.186.252', 'backend/login/login');
INSERT INTO `system_log` VALUES (4425, '[登录成功]', '2020-10-17 22:22:49', 'cui', 10, '119.248.183.161', 'backend/login/login');
INSERT INTO `system_log` VALUES (4426, '[登录成功]', '2020-10-17 22:30:28', 'cui', 10, '117.29.42.83', 'backend/login/login');
INSERT INTO `system_log` VALUES (4427, '[登录成功]', '2020-10-18 09:42:16', 'cui', 10, '223.98.214.131', 'backend/login/login');
INSERT INTO `system_log` VALUES (4428, '[登录成功]', '2020-10-18 11:38:21', 'cui', 10, '115.192.247.247', 'backend/login/login');
INSERT INTO `system_log` VALUES (4429, '[登录成功]', '2020-10-18 12:30:40', 'cui', 10, '223.104.111.199', 'backend/login/login');
INSERT INTO `system_log` VALUES (4430, '[登录成功]', '2020-10-18 16:29:31', 'cui', 10, '125.67.23.36', 'backend/login/login');
INSERT INTO `system_log` VALUES (4431, '[登录成功]', '2020-10-18 16:55:32', 'cui', 10, '115.60.4.174', 'backend/login/login');
INSERT INTO `system_log` VALUES (4432, '[登录成功]', '2020-10-18 17:08:58', 'cui', 10, '202.98.71.161', 'backend/login/login');
INSERT INTO `system_log` VALUES (4433, '[请款审批成功] [MODEL] app\\backend\\model\\PayoutModel', '2020-10-18 17:09:23', 'cui', 10, '202.98.71.161', 'backend/staff/verifyPayout');
INSERT INTO `system_log` VALUES (4434, '[添加班级成功] [MODEL] app\\backend\\model\\ClazzModel', '2020-10-18 17:09:35', 'cui', 10, '202.98.71.161', 'backend/student/saveClazz');
INSERT INTO `system_log` VALUES (4435, '[登录成功]', '2020-10-18 19:25:16', 'cui', 10, '117.30.171.214', 'backend/login/login');
INSERT INTO `system_log` VALUES (4436, '[登录成功]', '2020-10-18 19:32:59', 'cui', 10, '117.30.171.214', 'backend/login/login');
INSERT INTO `system_log` VALUES (4437, '[登录成功]', '2020-10-19 09:06:23', 'cui', 10, '61.153.199.34', 'backend/login/login');
INSERT INTO `system_log` VALUES (4438, '[登录成功]', '2020-10-19 10:32:08', 'cui', 10, '223.102.91.140', 'backend/login/login');
INSERT INTO `system_log` VALUES (4439, '[登录成功]', '2020-10-19 10:38:08', 'cui', 10, '119.123.73.15', 'backend/login/login');
INSERT INTO `system_log` VALUES (4440, '[登录成功]', '2020-10-19 11:52:26', 'cui', 10, '27.151.211.218', 'backend/login/login');
INSERT INTO `system_log` VALUES (4441, '[登录成功]', '2020-10-19 14:11:08', 'cui', 10, '114.88.92.64', 'backend/login/login');
INSERT INTO `system_log` VALUES (4442, '[登录成功]', '2020-10-19 14:16:05', 'cui', 10, '175.167.130.54', 'backend/login/login');
INSERT INTO `system_log` VALUES (4443, '[登录成功]', '2020-10-19 16:02:04', 'cui', 10, '58.62.182.59', 'backend/login/login');
INSERT INTO `system_log` VALUES (4444, '[登录成功]', '2020-10-19 16:26:00', 'cui', 10, '183.253.122.185', 'backend/login/login');
INSERT INTO `system_log` VALUES (4445, '[登录成功]', '2020-10-19 16:28:36', 'cui', 10, '222.240.3.226', 'backend/login/login');
INSERT INTO `system_log` VALUES (4446, '[登录成功]', '2020-10-19 17:34:59', 'cui', 10, '222.218.183.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (4447, '[登录成功]', '2020-10-19 19:54:49', 'cui', 10, '221.15.178.188', 'backend/login/login');
INSERT INTO `system_log` VALUES (4448, '[登录成功]', '2020-10-19 21:03:13', 'cui', 10, '112.39.107.60', 'backend/login/login');
INSERT INTO `system_log` VALUES (4449, '[登录成功]', '2020-10-19 22:33:24', 'cui', 10, '118.250.174.205', 'backend/login/login');
INSERT INTO `system_log` VALUES (4450, '[登录成功]', '2020-10-20 11:40:14', 'cui', 10, '211.137.78.135', 'backend/login/login');
INSERT INTO `system_log` VALUES (4451, '[登录成功]', '2020-10-20 13:42:58', 'cui', 10, '61.161.125.1', 'backend/login/login');
INSERT INTO `system_log` VALUES (4452, '[登录成功]', '2020-10-20 14:11:33', 'cui', 10, '115.60.197.239', 'backend/login/login');
INSERT INTO `system_log` VALUES (4453, '[登录成功]', '2020-10-20 15:26:09', 'cui', 10, '218.77.40.202', 'backend/login/login');
INSERT INTO `system_log` VALUES (4454, '[登录成功]', '2020-10-20 16:32:38', 'cui', 10, '183.195.26.71', 'backend/login/login');
INSERT INTO `system_log` VALUES (4455, '[登录成功]', '2020-10-20 17:05:36', 'cui', 10, '202.100.242.131', 'backend/login/login');
INSERT INTO `system_log` VALUES (4456, '[修改成功] [MODEL] app\\backend\\model\\AttachmentModel', '2020-10-20 17:13:28', 'cui', 10, '202.100.242.131', 'backend/attachment/moveImg');
INSERT INTO `system_log` VALUES (4457, '[修改知识点讲解成功] [MODEL] app\\backend\\model\\H5Model', '2020-10-20 17:25:39', 'cui', 10, '202.100.242.131', 'backend/knowledge/saveh5');
INSERT INTO `system_log` VALUES (4458, '[修改知识点讲解成功] [MODEL] app\\backend\\model\\H5Model', '2020-10-20 17:26:00', 'cui', 10, '202.100.242.131', 'backend/knowledge/saveh5');
INSERT INTO `system_log` VALUES (4459, '[登录成功]', '2020-10-20 21:56:15', 'cui', 10, '125.71.152.236', 'backend/login/login');
INSERT INTO `system_log` VALUES (4460, '[登录成功]', '2020-10-21 07:06:32', 'cui', 10, '39.154.11.149', 'backend/login/login');
INSERT INTO `system_log` VALUES (4461, '[登录成功]', '2020-10-21 13:23:41', 'cui', 10, '117.89.175.44', 'backend/login/login');
INSERT INTO `system_log` VALUES (4462, '[登录成功]', '2020-10-21 14:56:40', 'cui', 10, '125.70.76.206', 'backend/login/login');
INSERT INTO `system_log` VALUES (4463, '[登录成功]', '2020-10-21 17:54:09', 'cui', 10, '27.154.251.26', 'backend/login/login');
INSERT INTO `system_log` VALUES (4464, '[登录成功]', '2020-10-21 18:06:05', 'cui', 10, '220.112.244.106', 'backend/login/login');
INSERT INTO `system_log` VALUES (4465, '[登录成功]', '2020-10-21 21:36:20', 'cui', 10, '101.80.55.172', 'backend/login/login');
INSERT INTO `system_log` VALUES (4466, '[登录成功]', '2020-10-21 21:40:22', 'cui', 10, '121.31.188.38', 'backend/login/login');
INSERT INTO `system_log` VALUES (4467, '[登录成功]', '2020-10-22 00:45:54', 'cui', 10, '223.88.150.237', 'backend/login/login');
INSERT INTO `system_log` VALUES (4468, '[登录成功]', '2020-10-22 00:52:01', 'cui', 10, '182.139.64.14', 'backend/login/login');
INSERT INTO `system_log` VALUES (4469, '[登录成功]', '2020-10-22 13:24:25', 'cui', 10, '223.104.64.241', 'backend/login/login');
INSERT INTO `system_log` VALUES (4470, '[修改公众号菜单成功] [MODEL] app\\backend\\model\\WxMenuModel', '2020-10-22 13:26:34', 'cui', 10, '223.104.64.241', 'backend/wx/saveMenu');
INSERT INTO `system_log` VALUES (4471, '[登录成功]', '2020-10-22 14:30:01', 'cui', 10, '36.27.73.152', 'backend/login/login');
INSERT INTO `system_log` VALUES (4472, '[登录成功]', '2020-10-22 15:34:38', 'cui', 10, '114.86.88.57', 'backend/login/login');
INSERT INTO `system_log` VALUES (4473, '[登录成功]', '2020-10-22 17:54:18', 'cui', 10, '123.112.243.213', 'backend/login/login');
INSERT INTO `system_log` VALUES (4474, '[登录成功]', '2020-10-22 19:48:59', 'cui', 10, '103.3.136.110', 'backend/login/login');
INSERT INTO `system_log` VALUES (4475, '[登录成功]', '2020-10-22 22:16:23', 'cui', 10, '110.87.76.104', 'backend/login/login');
INSERT INTO `system_log` VALUES (4476, '[登录成功]', '2020-10-23 15:53:19', 'cui', 10, '1.80.37.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4477, '[登录成功]', '2020-10-23 16:33:30', 'cui', 10, '116.20.65.11', 'backend/login/login');
INSERT INTO `system_log` VALUES (4478, '[登录成功]', '2020-10-23 16:37:40', 'cui', 10, '118.81.55.243', 'backend/login/login');
INSERT INTO `system_log` VALUES (4479, '[登录成功]', '2020-10-24 22:32:18', 'cui', 10, '183.193.62.85', 'backend/login/login');
INSERT INTO `system_log` VALUES (4480, '[登录成功]', '2020-10-24 23:12:45', 'cui', 10, '111.196.219.151', 'backend/login/login');
INSERT INTO `system_log` VALUES (4481, '[登录成功]', '2020-10-25 09:38:52', 'cui', 10, '183.234.139.126', 'backend/login/login');
INSERT INTO `system_log` VALUES (4482, '[迟到补签成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-10-25 09:39:31', 'cui', 10, '183.234.139.126', 'backend/staff/attendanceapply/type/late_apply');
INSERT INTO `system_log` VALUES (4483, '[迟到补签审核成功] [MODEL] app\\backend\\model\\AttendanceModel', '2020-10-25 09:39:37', 'cui', 10, '183.234.139.126', 'backend/staff/attendanceapply/type/late_verify');
INSERT INTO `system_log` VALUES (4484, '[登录成功]', '2020-10-25 12:08:39', 'cui', 10, '60.1.2.52', 'backend/login/login');
INSERT INTO `system_log` VALUES (4485, '[登录成功]', '2020-10-25 12:09:32', 'cui', 10, '60.1.2.52', 'backend/login/login');
INSERT INTO `system_log` VALUES (4486, '[登录成功]', '2020-10-25 22:30:33', 'cui', 10, '123.185.110.133', 'backend/login/login');
INSERT INTO `system_log` VALUES (4487, '[登录成功]', '2020-10-26 02:55:17', 'cui', 10, '120.43.134.174', 'backend/login/login');
INSERT INTO `system_log` VALUES (4488, '[登录成功]', '2020-10-26 10:12:51', 'cui', 10, '218.68.216.148', 'backend/login/login');
INSERT INTO `system_log` VALUES (4489, '[登录成功]', '2020-10-26 12:34:41', 'cui', 10, '116.249.42.233', 'backend/login/login');
INSERT INTO `system_log` VALUES (4490, '[登录成功]', '2020-10-26 13:39:03', 'cui', 10, '117.63.176.215', 'backend/login/login');
INSERT INTO `system_log` VALUES (4491, '[登录成功]', '2020-10-26 23:58:21', 'cui', 10, '114.217.214.36', 'backend/login/login');
INSERT INTO `system_log` VALUES (4492, '[登录成功]', '2020-10-27 09:08:08', 'cui', 10, '117.28.194.8', 'backend/login/login');
INSERT INTO `system_log` VALUES (4493, '[登录成功]', '2020-10-27 11:18:26', 'cui', 10, '101.246.191.57', 'backend/login/login');
INSERT INTO `system_log` VALUES (4494, '[登录成功]', '2020-10-27 11:27:20', 'cui', 10, '113.204.4.250', 'backend/login/login');
INSERT INTO `system_log` VALUES (4495, '[登录成功]', '2020-10-27 17:07:54', 'cui', 10, '114.222.22.244', 'backend/login/login');
INSERT INTO `system_log` VALUES (4496, '[登录成功]', '2020-10-27 17:29:26', 'cui', 10, '113.247.58.178', 'backend/login/login');

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `editor_id` int(11) NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `edit_time` datetime NULL DEFAULT NULL,
  `delete_time` int(11) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `category_id` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES (16, 'KB0 TEST2', 3, '2020-09-01 09:40:17', '2020-09-01 09:41:18', 1600766636, '', 0);
INSERT INTO `test` VALUES (17, '测试勿删', 3, '2020-09-01 09:47:29', '2020-09-01 09:57:54', 1600766632, '', 1);
INSERT INTO `test` VALUES (18, '十一假期后第一次考试', 10, '2020-10-08 22:35:29', '2020-10-08 23:10:33', NULL, '测验假期作业情况', 1);
INSERT INTO `test` VALUES (19, '1231231', 10, '2020-10-14 16:56:51', '2020-10-20 17:23:52', NULL, '312312', 4);

-- ----------------------------
-- Table structure for test_category
-- ----------------------------
DROP TABLE IF EXISTS `test_category`;
CREATE TABLE `test_category`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of test_category
-- ----------------------------
INSERT INTO `test_category` VALUES (1, 'KB0测验', 0);
INSERT INTO `test_category` VALUES (2, 'KB1测验', 0);
INSERT INTO `test_category` VALUES (3, 'KB0 Unit1', 1);
INSERT INTO `test_category` VALUES (4, 'KB0 Unit2', 1);
INSERT INTO `test_category` VALUES (5, 'KB1 Unit1', 2);
INSERT INTO `test_category` VALUES (6, 'KB1 Unit2', 2);

-- ----------------------------
-- Table structure for test_part
-- ----------------------------
DROP TABLE IF EXISTS `test_part`;
CREATE TABLE `test_part`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `test_id` int(11) NOT NULL,
  `sort_num` tinyint(4) NULL DEFAULT 0,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部分说明',
  `score` tinyint(4) NULL DEFAULT 1 COMMENT '每题分数参考',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `test_id`(`test_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '考试子部分表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of test_part
-- ----------------------------
INSERT INTO `test_part` VALUES (16, 11, 0, '22', NULL);
INSERT INTO `test_part` VALUES (17, 11, 0, 'we423', 23);
INSERT INTO `test_part` VALUES (18, 12, 0, '12121231232eqweeqwe3123', 5);
INSERT INTO `test_part` VALUES (19, 12, 1, '435340oiu123', 4);
INSERT INTO `test_part` VALUES (20, 12, 2, '', 1);
INSERT INTO `test_part` VALUES (21, 13, 0, '', 1);
INSERT INTO `test_part` VALUES (22, 14, 0, '选择题', 1);
INSERT INTO `test_part` VALUES (23, 14, 1, '填空题', 1);
INSERT INTO `test_part` VALUES (24, 14, 2, '阅读理解', 1);
INSERT INTO `test_part` VALUES (25, 15, 0, '部分的说明1', 1);
INSERT INTO `test_part` VALUES (26, 15, 1, '部分的说明2', 1);
INSERT INTO `test_part` VALUES (27, 16, 0, '', 1);
INSERT INTO `test_part` VALUES (28, 17, 0, '第一部分', 10);
INSERT INTO `test_part` VALUES (30, 17, 1, '第二部', 15);
INSERT INTO `test_part` VALUES (31, 18, 0, '语法', 1);
INSERT INTO `test_part` VALUES (32, 18, 1, '听力', 1);
INSERT INTO `test_part` VALUES (33, 19, 0, '1231312', 1);
INSERT INTO `test_part` VALUES (34, 19, 1, '12313', 1);
INSERT INTO `test_part` VALUES (35, 19, 2, '312312321', 1);

-- ----------------------------
-- Table structure for test_question
-- ----------------------------
DROP TABLE IF EXISTS `test_question`;
CREATE TABLE `test_question`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `test_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `score` tinyint(4) NULL DEFAULT 0 COMMENT '分数',
  `sort_num` tinyint(4) NULL DEFAULT 0,
  `part_id` int(11) NULL DEFAULT NULL,
  `group_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of test_question
-- ----------------------------
INSERT INTO `test_question` VALUES (33, 15, 34, 30, 0, 25, 103);
INSERT INTO `test_question` VALUES (34, 15, 30, 10, 1, 25, 74);
INSERT INTO `test_question` VALUES (35, 15, 31, 10, 4, 25, 74);
INSERT INTO `test_question` VALUES (36, 15, 32, 10, 5, 25, 74);
INSERT INTO `test_question` VALUES (37, 15, 37, 1, 0, 26, 104);
INSERT INTO `test_question` VALUES (38, 15, 36, 1, 1, 26, 87);
INSERT INTO `test_question` VALUES (39, 15, 35, 1, 2, 26, 102);
INSERT INTO `test_question` VALUES (40, 16, 40, 1, 0, 27, 105);
INSERT INTO `test_question` VALUES (41, 17, 40, 10, 0, 28, 105);
INSERT INTO `test_question` VALUES (42, 17, 36, 15, 0, 30, 87);
INSERT INTO `test_question` VALUES (43, 18, 41, 10, 0, 31, 117);
INSERT INTO `test_question` VALUES (44, 18, 36, 10, 1, 31, 107);
INSERT INTO `test_question` VALUES (45, 18, 40, 10, 0, 32, 113);
INSERT INTO `test_question` VALUES (46, 18, 42, 1, 0, 31, 121);
INSERT INTO `test_question` VALUES (47, 19, 42, 1, 0, 33, 121);
INSERT INTO `test_question` VALUES (48, 19, 41, 1, 1, 33, 117);

-- ----------------------------
-- Table structure for test_score
-- ----------------------------
DROP TABLE IF EXISTS `test_score`;
CREATE TABLE `test_score`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `clazz_id` int(11) NULL DEFAULT NULL,
  `score` tinyint(4) NULL DEFAULT 0 COMMENT '按分数  最终得分',
  `is_right` tinyint(1) NULL DEFAULT NULL COMMENT '按对错录入1 正确 -1错误',
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按答案录入',
  `add_time` datetime NULL DEFAULT NULL,
  `edit_time` datetime NULL DEFAULT NULL,
  `editor_id` int(11) NULL DEFAULT NULL COMMENT '阅卷人',
  `is_cover` tinyint(1) NULL DEFAULT 0 COMMENT '覆盖起来 用于编辑之前的覆盖',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `question_id`(`question_id`) USING BTREE,
  INDEX `test_id`(`test_id`) USING BTREE,
  INDEX `student_test_id`(`student_id`, `test_id`, `question_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of test_score
-- ----------------------------
INSERT INTO `test_score` VALUES (9, 10003, 17, 36, 1, 0, -1, 'HEIDRICH', '2020-09-01 11:21:04', '2020-09-01 15:33:08', 3, 0);
INSERT INTO `test_score` VALUES (10, 10003, 16, 40, 1, 1, NULL, NULL, '2020-09-01 11:26:08', '2020-09-01 11:26:20', 3, 0);
INSERT INTO `test_score` VALUES (11, 10003, 17, 40, 1, 1, -1, 'A', '2020-09-01 12:30:44', '2020-09-01 15:27:06', 3, 0);
INSERT INTO `test_score` VALUES (12, 10004, 17, 36, 4, 15, 1, 'C', '2020-09-01 15:12:12', '2020-09-01 15:33:41', 3, 0);
INSERT INTO `test_score` VALUES (13, 10004, 17, 40, 4, 10, 1, 'B', '2020-09-01 15:12:16', '2020-09-01 15:33:04', 3, 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `mobile` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '1为正常，-1为禁止',
  `reg_time` datetime NULL DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表 用于登录' ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '123334455', 1, '2020-08-30 16:13:19');
INSERT INTO `user` VALUES (2, '13322555666', 1, '2020-08-30 16:14:33');

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `balance` decimal(8, 2) UNSIGNED NULL DEFAULT 0.00 COMMENT '虚拟余额',
  `point` int(11) NULL DEFAULT 0 COMMENT '积分',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uid`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_account
-- ----------------------------
INSERT INTO `user_account` VALUES (1, 1, 0.00, 11);
INSERT INTO `user_account` VALUES (2, 2, 0.00, 17);

-- ----------------------------
-- Table structure for user_account_log
-- ----------------------------
DROP TABLE IF EXISTS `user_account_log`;
CREATE TABLE `user_account_log`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '1 余额记录  2 积分记录',
  `stage` tinyint(1) NULL DEFAULT NULL,
  `amount` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT '金额',
  `remaining_point` int(11) NULL DEFAULT 0 COMMENT '剩余积分',
  `add_time` datetime NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_account_log
-- ----------------------------
INSERT INTO `user_account_log` VALUES (11, 2, 2, NULL, 2.00, 18, '2020-08-30 16:55:49', '心情好');
INSERT INTO `user_account_log` VALUES (12, 2, 2, NULL, -1.00, 17, '2020-08-30 16:56:14', '减去测试');
INSERT INTO `user_account_log` VALUES (13, 1, 2, NULL, -1.00, 0, '2020-09-02 14:10:52', '慷慨解囊看');
INSERT INTO `user_account_log` VALUES (14, 1, 2, NULL, 5.00, 5, '2020-09-02 14:11:03', '看见你');
INSERT INTO `user_account_log` VALUES (15, 1, 2, NULL, 9.00, 14, '2020-09-02 14:11:15', '开不开机了');
INSERT INTO `user_account_log` VALUES (16, 1, 2, NULL, -3.00, 11, '2020-09-02 14:11:27', 'i哦离开；，乱码');

-- ----------------------------
-- Table structure for user_favorite
-- ----------------------------
DROP TABLE IF EXISTS `user_favorite`;
CREATE TABLE `user_favorite`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `case_id` int(11) NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_favorite
-- ----------------------------
INSERT INTO `user_favorite` VALUES (4, 2632, 3, '2020-06-25 14:12:23');
INSERT INTO `user_favorite` VALUES (7, 2632, 6, '2020-07-01 19:45:31');

-- ----------------------------
-- Table structure for user_level
-- ----------------------------
DROP TABLE IF EXISTS `user_level`;
CREATE TABLE `user_level`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `need_points` int(10) UNSIGNED NOT NULL DEFAULT 0,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_level
-- ----------------------------
INSERT INTO `user_level` VALUES (1, '普通学霸', 0, NULL);
INSERT INTO `user_level` VALUES (2, '白银学霸', 500, NULL);
INSERT INTO `user_level` VALUES (3, '黄金学霸', 1000, NULL);
INSERT INTO `user_level` VALUES (4, '铂金学霸', 2000, NULL);
INSERT INTO `user_level` VALUES (5, '钻石学霸', 5000, NULL);
INSERT INTO `user_level` VALUES (6, '星耀学霸', 10000, NULL);
INSERT INTO `user_level` VALUES (7, '王者学霸', 20000, NULL);

-- ----------------------------
-- Table structure for user_log
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_log
-- ----------------------------
INSERT INTO `user_log` VALUES (1, 10, '测试', '2020-03-27 16:19:13');
INSERT INTO `user_log` VALUES (2, 11, '测试2', '2020-03-27 16:19:13');

-- ----------------------------
-- Table structure for vod
-- ----------------------------
DROP TABLE IF EXISTS `vod`;
CREATE TABLE `vod`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cover_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `video_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `upload_time` datetime NULL DEFAULT NULL,
  `editor` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vod
-- ----------------------------
INSERT INTO `vod` VALUES (2, 'index-4.mp4', '5285890807276244069', 'http://1400170034.vod2.myqcloud.com/faeed456vodcq1400170034/8f1011515285890807276244069/9PtRADTvPgMA.mp4', 'http://1400170034.vod2.myqcloud.com/faeed456vodcq1400170034/8f1011515285890807276244069/5285890807276244070.gif', '2020-09-07 10:41:01', '3');
INSERT INTO `vod` VALUES (3, 'index-4.mp4', '5285890807258566344', 'http://1252590776.vod2.myqcloud.com/e3b9992bvodgzp1252590776/57a5b1345285890807258566344/3jmjxjBOFtkA.mp4', 'http://1252590776.vod2.myqcloud.com/e3b9992bvodgzp1252590776/57a5b1345285890807258566344/5285890807258566345.gif', '2020-09-07 10:53:38', '3');
INSERT INTO `vod` VALUES (4, 'index-4.mp4', '5285890807327708303', 'http://1252590776.vod2.myqcloud.com/52ac860evodcq1252590776/bea13d4a5285890807327708303/LDfh6eft5fMA.mp4', 'http://1252590776.vod2.myqcloud.com/52ac860evodcq1252590776/bea13d4a5285890807327708303/5285890807327708304.gif', '2020-09-07 21:39:06', '3');
INSERT INTO `vod` VALUES (5, 'index-4.mp4', '5285890807293232848', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/8091f5525285890807293232848/EuEL3roF4hQA.mp4', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/8091f5525285890807293232848/5285890807293232849.gif', '2020-09-07 21:46:48', '3');
INSERT INTO `vod` VALUES (6, 'KB0L1 一年五班.m4v', '5285890807291725633', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/f236caef5285890807291725633/aU2QCoHRVgYA.m4v', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/f236caef5285890807291725633/5285890807291725634.jpg', '2020-09-08 07:08:48', '3');
INSERT INTO `vod` VALUES (7, 'KB1L1 三年五班.m4v', '5285890807291727917', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/f237f0365285890807291727917/pYeWcQKeipUA.m4v', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/f237f0365285890807291727917/5285890807291727918.jpg', '2020-09-08 07:14:07', '3');
INSERT INTO `vod` VALUES (8, 'KB1L1 三年一班.m4v', '5285890807291729210', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/f238eb2a5285890807291729210/HAMyacyUcF4A.m4v', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/f238eb2a5285890807291729210/5285890807291729211.jpg', '2020-09-08 07:16:55', '3');
INSERT INTO `vod` VALUES (9, 'index-4.mp4', '5285890807290198823', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/97bdf3735285890807290198823/3tkWioYBfUMA.mp4', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/97bdf3735285890807290198823/5285890807290198824.gif', '2020-09-08 09:34:55', '3');
INSERT INTO `vod` VALUES (10, 'G8l1 八年四班.m4v', '5285890807294364905', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/d018c0b35285890807294364905/xqCUuPu8VxkA.m4v', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/d018c0b35285890807294364905/5285890807294364906.jpg', '2020-09-08 12:24:10', '3');
INSERT INTO `vod` VALUES (11, 'G7L1七年九班.mp4', '5285890807343554592', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/5ee9f5715285890807343554592/L51akzuegu4A.mp4', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/5ee9f5715285890807343554592/5285890807343554593.png', '2020-09-09 16:57:11', '3');
INSERT INTO `vod` VALUES (12, 'G8L2 八年一班.mp4', '5285890807500406067', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/ad3e2da55285890807500406067/NYNdVI7m9Z4A.mp4', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/ad3e2da55285890807500406067/5285890807500406068.jpg', '2020-09-11 14:34:46', '3');
INSERT INTO `vod` VALUES (13, 'G8L3 八年八班.mp4', '5285890807660909711', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/b6f129505285890807660909711/DGtgXOlGKikA.mp4', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/b6f129505285890807660909711/5285890807660909712.jpg', '2020-09-17 10:59:28', '3');
INSERT INTO `vod` VALUES (14, 'KB0L3 一年七班.mp4', '5285890807658198979', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/207a4ee75285890807658198979/yoLmacKsKaYA.mp4', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/207a4ee75285890807658198979/5285890807658198980.jpg', '2020-09-17 11:03:56', '3');
INSERT INTO `vod` VALUES (15, 'KB1L3 三年三班.mp4', '5285890807663966551', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/9e4ac5785285890807663966551/HSGoS6q4ZzQA.mp4', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/9e4ac5785285890807663966551/5285890807663966552.jpg', '2020-09-17 13:32:17', '3');
INSERT INTO `vod` VALUES (16, 'G7L4 七年一班.mp4', '5285890807833642379', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/bb5844365285890807833642379/4F4yxlALAj0A.mp4', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/bb5844365285890807833642379/5285890807833642380.jpg', '2020-09-22 17:30:05', '3');
INSERT INTO `vod` VALUES (17, 'KB1L4 二年二班.mp4', '5285890807836929642', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/a926265a5285890807836929642/LLDAMxdUacMA.mp4', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/a926265a5285890807836929642/5285890807836929643.jpg', '2020-09-22 17:30:43', '3');
INSERT INTO `vod` VALUES (18, 'G8L4 八年五班.mp4', '5285890807833642853', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/bb5859335285890807833642853/NwggOUIHVlgA.mp4', 'http://1500001788.vod2.myqcloud.com/6c981c00vodcq1500001788/bb5859335285890807833642853/5285890807833642854.jpg', '2020-09-22 17:31:24', '3');

-- ----------------------------
-- Table structure for wechat_login
-- ----------------------------
DROP TABLE IF EXISTS `wechat_login`;
CREATE TABLE `wechat_login`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uid` int(11) NULL DEFAULT NULL,
  `open_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `union_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `login_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wechat_login
-- ----------------------------
INSERT INTO `wechat_login` VALUES (1, NULL, '', NULL, NULL);
INSERT INTO `wechat_login` VALUES (2, NULL, '0', NULL, '2020-05-21 16:44:29');
INSERT INTO `wechat_login` VALUES (3, NULL, '0', NULL, NULL);
INSERT INTO `wechat_login` VALUES (4, NULL, '0', NULL, NULL);
INSERT INTO `wechat_login` VALUES (5, 2636, '1', '', '2020-06-08 09:16:09');

-- ----------------------------
-- Table structure for wx_access
-- ----------------------------
DROP TABLE IF EXISTS `wx_access`;
CREATE TABLE `wx_access`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `openid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `unionid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `unsubscribe` tinyint(1) NULL DEFAULT 0 COMMENT '是否取消关注',
  `unsub_time` datetime NULL DEFAULT NULL COMMENT '取消关注事件',
  `staff_id` int(11) NULL DEFAULT NULL,
  `student_id` int(11) NULL DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  `gender` tinyint(1) NULL DEFAULT 0,
  `city` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `country` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `headimg` varchar(191) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `type` tinyint(1) NULL DEFAULT 1 COMMENT '1 学生 2 家长',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `openid`(`openid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信登录记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wx_access
-- ----------------------------
INSERT INTO `wx_access` VALUES (5, 'olGpVxJen9dW5hDvkQsLok-LGsWU', NULL, '2020-07-31 12:02:49', 1, '2020-08-01 15:37:27', NULL, NULL, '', 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `wx_access` VALUES (6, 'olGpVxGku7-6KIq8ZRz68nzlfE74', NULL, '2020-07-31 18:06:33', 0, NULL, NULL, NULL, '', 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `wx_access` VALUES (7, 'olGpVxKH-J2LdHaXumIVr8wA2pNE', NULL, '2020-08-01 15:37:44', 0, NULL, NULL, NULL, '', 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `wx_access` VALUES (8, 'olGpVxEwj6nKKxDLh7bGUDIG7tqY', NULL, '2020-08-01 21:13:35', 0, NULL, NULL, NULL, '', 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `wx_access` VALUES (10, 'olGpVxBAkvVSFn4U8gKNv_w_mc3I', NULL, '2020-08-03 11:12:55', 0, NULL, NULL, NULL, '', 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `wx_access` VALUES (11, 'olGpVxF7TqgCW96pycIMlfcp8TzA', NULL, '2020-08-07 18:36:18', 0, NULL, NULL, NULL, '', 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `wx_access` VALUES (12, 'olGpVxAmFoCTcZ-3b8emgLSKCsy0', NULL, '2020-08-10 17:12:16', 1, '2020-08-10 17:14:12', NULL, NULL, '', 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `wx_access` VALUES (13, 'olGpVxJsTTNPNHnYX4IWb9wV8IAg', NULL, '2020-08-11 20:27:17', 0, NULL, NULL, NULL, '', 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `wx_access` VALUES (14, 'olGpVxKWoJvc3iBv8mXr3KBOfB94', NULL, '2020-08-13 15:30:45', 0, NULL, NULL, NULL, '', 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `wx_access` VALUES (15, 'oXlScv5_yEIVo2zaYLYJQ5CIB1zk', NULL, '2020-09-17 08:57:55', 0, NULL, 3, 2, 'Ryan😀', 1, '大连', '辽宁', '中国', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q3auHgzwzM5iceUg7QITB95MUAPhvKwlz2jfSAyDVgRwwVhhPctBtH7vL7IcAHjgu4uodic8XySIXqql9FZdibe4Q/132', 1);
INSERT INTO `wx_access` VALUES (16, 'oXlScv5FAwi3WxPPBiFWPjo4OeV8', NULL, '2020-09-15 21:39:29', 0, NULL, 4, NULL, '', 0, NULL, NULL, NULL, NULL, 1);
INSERT INTO `wx_access` VALUES (19, 'oXlScvwZfUKVDp_r0x0YnDBJKMV4', NULL, '2020-09-18 15:47:33', 0, NULL, 8, 4, 'Susan', 2, '大连', '辽宁', '中国', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoCkJj81e48liagnOglDR1BWXYJzbuMmo5gsUG8wHic8npLibibY8XpJBPB0zwsksSibiax8RHlb8Zicqheg/132', 1);
INSERT INTO `wx_access` VALUES (20, 'oXlScv2a_khzs3c1llHdVqgSy5oM', NULL, '2020-09-19 17:25:49', 0, NULL, 12, 1, '韩老师13863615386', 2, '', '日内瓦', '瑞士', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJBPIBzfwqJDQzmBN3ZkHbKA44XHnmsOVBtia13wBLIUw6nAkDadMQ5jUK2ORTdPW8fWwOEKTBxjlw/132', 1);
INSERT INTO `wx_access` VALUES (21, 'oXlScv__ff7TbFrdrJGb2YZ-wRrE', NULL, '2020-09-23 07:31:09', 0, NULL, 2, NULL, '西西', 2, '潍坊', '山东', '中国', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKR8Z6FU9NH7W8GfYyz0RialD100LYAcaIw2T6PxGPmMX6twKZDCDyIGByNW6ibmnvgWG09pdnlxsQg/132', 1);
INSERT INTO `wx_access` VALUES (22, 'oXlScvxs-xQrihCuoJaNIqHIdoO4', NULL, '2020-09-23 07:31:36', 0, NULL, 1, NULL, 'Night', 1, '青岛', '山东', '中国', 'https://thirdwx.qlogo.cn/mmopen/vi_32/vic4I4elECsLLfPeBWASIiaqP7acAD3Ux4QEyDLeJUfDvbATlu4PPsLeRicStnSueYAYwWW5ELRtYJNMdt0Mc58zA/132', 1);
INSERT INTO `wx_access` VALUES (23, 'oXlScv8IH9dnrGQCTHHOpV5i77vk', NULL, '2020-09-23 07:56:47', 0, NULL, 11, NULL, '暖阳阳', 2, '潍坊', '山东', '中国', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLMW49J3jExoBcia3T0wSp2wm0vzN3N67B23Q9833djAu0SVibuVDAibTqVnb1xkP5jAFuqiakUdpHYVg/132', 1);
INSERT INTO `wx_access` VALUES (24, 'oXlScv9xGRZcCwZPM3ihsoWrGxnY', NULL, '2020-09-29 11:44:59', 0, NULL, NULL, 2, '窦俊亮', 1, '潍坊', '山东', '中国', 'https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epDwZ9fM2m5FgdALmn64UPCWNNupC50LwnJxAGvUmrxibhz08tVrAl6wqzicNhVT3dAqAc4lV9SDXRA/132', 1);

-- ----------------------------
-- Table structure for wx_menu
-- ----------------------------
DROP TABLE IF EXISTS `wx_menu`;
CREATE TABLE `wx_menu`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` int(11) NULL DEFAULT 0,
  `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'view网页 text文本 img图片 media_id永久素材',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容',
  `media_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '素材时有效',
  `sort_num` tinyint(4) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of wx_menu
-- ----------------------------
INSERT INTO `wx_menu` VALUES (7, 'Shool Info', 0, 'url', '', NULL, 0);
INSERT INTO `wx_menu` VALUES (8, 'Teachers', 7, 'media', 'Coming soon...', NULL, 0);
INSERT INTO `wx_menu` VALUES (9, 'Staff', 0, 'url', '', NULL, 1);
INSERT INTO `wx_menu` VALUES (10, 'Moments', 7, 'text', 'Coming soon...', NULL, 1);
INSERT INTO `wx_menu` VALUES (11, 'Student', 0, 'url', '', NULL, 2);
INSERT INTO `wx_menu` VALUES (12, 'Schedule', 9, 'url', 'http://sj.xinyangedu.com/wxauth.html?state=calendar', NULL, 2);
INSERT INTO `wx_menu` VALUES (13, 'Attendance', 9, 'url', 'http://sj.xinyangedu.com/wxauth.html?state=sign', NULL, 1);
INSERT INTO `wx_menu` VALUES (14, 'Others', 9, 'url', 'http://sj.xinyangedu.com/wxauth.html?state=staffcenter', NULL, 0);
INSERT INTO `wx_menu` VALUES (15, 'Courses', 7, 'text', 'Coming soon...', NULL, 2);
INSERT INTO `wx_menu` VALUES (16, 'My ESL', 11, 'url', 'http://sj.xinyangedu.com/wxauth.html?state=studentcenter', NULL, 0);

-- ----------------------------
-- Table structure for zone
-- ----------------------------
DROP TABLE IF EXISTS `zone`;
CREATE TABLE `zone`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `clazz_id` int(11) NOT NULL,
  `task_id` int(11) NULL DEFAULT NULL,
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '1 文字 2 语音 3 图片',
  `add_time` datetime NULL DEFAULT NULL,
  `verifier_id` int(11) NULL DEFAULT NULL COMMENT '打分人老师id',
  `verify_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '点评文字',
  `verify_score` tinyint(1) NULL DEFAULT 5,
  `verify_time` datetime NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '0 隐藏 1 显示',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文字内容',
  `attach` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件 图片或音频地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `clazz_id`(`clazz_id`, `status`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生发圈记录' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zone
-- ----------------------------
INSERT INTO `zone` VALUES (34, 10003, 40, 6, 2, '2020-09-16 09:39:03', 3, 'qweqwe', 1, NULL, 1, '说点什么1', 'http://www.xinyangedu.com/up/attach/20200508/afac18e6f20674abbd57c89c5fc80345.mp3');
INSERT INTO `zone` VALUES (35, 10003, 40, 6, 2, '2020-09-16 09:39:03', 3, 'dianxiyxia', 3, NULL, 1, '说点什么2', 'http://www.xinyangedu.com/up/attach/20200508/afac18e6f20674abbd57c89c5fc80345.mp3');
INSERT INTO `zone` VALUES (36, 10003, 40, 6, 1, '2020-09-16 09:39:03', 3, 'dfsdf', 1, NULL, 1, '说点什么', '挺不错');
INSERT INTO `zone` VALUES (37, 10003, 40, 6, 2, '2020-09-16 09:39:03', 3, '挺好的', 5, '2020-09-18 10:58:07', 1, '说点什么', 'http://www.xinyangedu.com/up/attach/20200508/afac18e6f20674abbd57c89c5fc80345.mp3');
INSERT INTO `zone` VALUES (38, 10003, 40, 6, 3, '2020-09-16 09:39:03', 3, '说的可以', 2, '2020-09-18 11:11:39', 1, '说点什么', '/up/20200915/e01d6555b92a5e6019abd94b287c23df.png,/up/20200915/e01d6555b92a5e6019abd94b287c23df.png,/up/20200915/e01d6555b92a5e6019abd94b287c23df.png,/up/20200915/e01d6555b92a5e6019abd94b287c23df.png');
INSERT INTO `zone` VALUES (39, 10003, 40, 7, 2, '2020-09-16 09:39:03', 3, 'qweqwe', 1, NULL, 1, '说点什么1', 'http://www.xinyangedu.com/up/attach/20200508/afac18e6f20674abbd57c89c5fc80345.mp3');
INSERT INTO `zone` VALUES (40, 10003, 40, 7, 2, '2020-09-16 09:39:03', 3, 'dianxiyxia', 3, NULL, 1, '说点什么2', 'http://www.xinyangedu.com/up/attach/20200508/afac18e6f20674abbd57c89c5fc80345.mp3');
INSERT INTO `zone` VALUES (41, 10003, 40, 7, 3, '2020-09-17 22:28:21', 10, '', 5, '2020-10-20 17:16:55', 1, '挺好', '/up/20200917\\ecee0e27430efa2eab1387032c94fb13.png');
INSERT INTO `zone` VALUES (42, 10003, 40, 7, 2, '2020-09-17 22:33:18', NULL, '', 5, NULL, 1, '录音', '/up/voice/OMPU1ND5.amr');
INSERT INTO `zone` VALUES (43, 10003, 40, 7, 3, '2020-09-22 08:32:25', NULL, '', 5, NULL, 1, 'i am god', '/up/20200922/683c4ad6418b1bc35156709f7b9a1266.png');
INSERT INTO `zone` VALUES (44, 10003, 40, 6, 3, '2020-09-22 08:41:00', NULL, '', 5, NULL, 1, 'iam good', '/up/20200922/57a74bb7040f504bf3c4c80ac90ada26.png');

-- ----------------------------
-- Table structure for zone_comment
-- ----------------------------
DROP TABLE IF EXISTS `zone_comment`;
CREATE TABLE `zone_comment`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `zone_id` int(11) NULL DEFAULT NULL,
  `publisher_id` int(11) NULL DEFAULT NULL COMMENT '发布者 id (学生id)',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `zone_id`(`zone_id`, `publisher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zone_comment
-- ----------------------------
INSERT INTO `zone_comment` VALUES (1, 5, 10003, '评论1', NULL);
INSERT INTO `zone_comment` VALUES (2, 5, 10004, '评论2', NULL);
INSERT INTO `zone_comment` VALUES (3, 5, 10003, '评论。。。', NULL);
INSERT INTO `zone_comment` VALUES (4, 4, 10004, '好评', NULL);
INSERT INTO `zone_comment` VALUES (5, 4, 10003, '赞一个', NULL);
INSERT INTO `zone_comment` VALUES (6, 33, 10003, NULL, '2020-09-16 18:00:42');
INSERT INTO `zone_comment` VALUES (8, 33, 10003, NULL, '2020-09-16 18:01:45');
INSERT INTO `zone_comment` VALUES (10, 28, 10003, 'tinghao', '2020-09-16 18:03:36');
INSERT INTO `zone_comment` VALUES (11, 38, 10003, 'adfasdf', '2020-09-16 18:16:37');
INSERT INTO `zone_comment` VALUES (12, 38, 10004, 'adfasdf3234', '2020-09-16 18:16:41');
INSERT INTO `zone_comment` VALUES (13, 38, 10004, '23234', '2020-09-16 18:18:30');

-- ----------------------------
-- Table structure for zone_like
-- ----------------------------
DROP TABLE IF EXISTS `zone_like`;
CREATE TABLE `zone_like`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `zone_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uid` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '老师是t开头，学生只是数字',
  `add_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `zone_id`(`zone_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zone_like
-- ----------------------------
INSERT INTO `zone_like` VALUES (10, '28', '10003', '2020-09-16 18:03:40');

-- ----------------------------
-- Table structure for zone_task
-- ----------------------------
DROP TABLE IF EXISTS `zone_task`;
CREATE TABLE `zone_task`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `clazz_id` int(11) NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文字内容',
  `images` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `editor_id` int(11) NULL DEFAULT NULL COMMENT '员工id',
  `add_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `clazz_id`(`clazz_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '发圈任务' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zone_task
-- ----------------------------
INSERT INTO `zone_task` VALUES (6, 40, '把第一课第一自然段录音发布', NULL, NULL, '2020-09-18 10:22:12');
INSERT INTO `zone_task` VALUES (7, 40, '手写一封英文信', NULL, NULL, '2020-09-18 10:22:14');

-- ----------------------------
-- Table structure for zone_task_student
-- ----------------------------
DROP TABLE IF EXISTS `zone_task_student`;
CREATE TABLE `zone_task_student`  (
  `task_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  INDEX `clazz_id`(`student_id`) USING BTREE,
  INDEX `task_id`(`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '发圈任务' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zone_task_student
-- ----------------------------
INSERT INTO `zone_task_student` VALUES (7, 10003);
INSERT INTO `zone_task_student` VALUES (7, 10003);
INSERT INTO `zone_task_student` VALUES (6, 10003);

SET FOREIGN_KEY_CHECKS = 1;
