/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : myblog

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 06/10/2022 15:32:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `noteId` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `title` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '内容',
  `typeId` int(0) NULL DEFAULT NULL COMMENT '外键，从属tb_note_type',
  `pubTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '发布时间',
  `userId` int(0) NULL DEFAULT NULL COMMENT '用户Id',
  PRIMARY KEY (`noteId`) USING BTREE,
  INDEX `fk_note_ref_type`(`typeId`) USING BTREE,
  INDEX `fk_userId`(`userId`) USING BTREE,
  CONSTRAINT `fk_note_ref_type` FOREIGN KEY (`typeId`) REFERENCES `blog_type` (`typeId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (1, '上海一日游', '东方明珠地处黄浦江畔，背拥陆家嘴地区现代化建筑楼群，与隔江的外滩万国建筑博览群交相辉映', 2, '2020-08-23 05:10:35', 1);
INSERT INTO `blog` VALUES (2, '《百年孤独》', '《百年孤独》描写了布恩迪亚家族七代人的传奇故事，以及加勒比海沿岸小镇马孔多的百年兴衰，反映了拉丁美洲一个世纪以来风云变幻的历史。作品融入神话传说、民间故事、宗教典故等神秘因素，巧妙地糅合了现实与虚幻，展现出一个瑰丽的想象世界，成为20世纪重要的经典文学巨著之一。', 4, '2020-08-23 05:05:54', 1);
INSERT INTO `blog` VALUES (4, '《阿甘正传》', '傻傻的阿甘给我们上了精彩的一课。执著的信念，蓬勃生的希望，迸发青春的活力，燃烧无尽的热情，铭刻爱的永恒，描绘坚定地毅力，坚信美好的明天。', 5, '2020-09-23 05:05:50', 1);
INSERT INTO `blog` VALUES (5, '《肖生克的救赎》', '安迪是伟大的，一个人的自救，那是勇者，一个人自救的同时，又实现他人的救赎，那是圣人。芝华塔尼欧某个海岸边，有家小旅馆，旁边有一条旧小艇。上面站着一个人，是安迪。安迪，看，瑞德来了，该出发了。', 5, '2020-09-23 05:05:47', 1);
INSERT INTO `blog` VALUES (6, '山城重庆', '夜晚的重庆仿佛香港的维多利亚港湾，迷人深邃浪漫的气息不仅由你的心神而生发，江两岸的灯火阑珊让你不时想起唐朝诗人的名句来，船在灯火里游弋，光怪陆离的美让你惊诧。', 1, '2020-10-23 05:02:21', 1);
INSERT INTO `blog` VALUES (7, '泉城济南', '一个老城，有山有水，全在天底下晒着阳光，暖和安适地睡着，只等春风来把它们唤醒，这是不是个理想的境界。', 2, '2020-11-23 05:02:24', 1);
INSERT INTO `blog` VALUES (8, '《解忧杂货店》', '每个人都会站在人生的岔路口，但这时，我们应该怎样做出选择?我们不妨先来看看古时候的伟人是怎么做的。南宋末期，最后一个皇帝逃到了海上，以躲避元军。这时，他是像阿斗那样投降，虽然保住了命，但屈辱地活着;还是作为一国之主，英勇殉国?他选择了后者，守卫了中华民族的尊严。还有明朝的杨继康，他是选择加入奸臣严嵩的队伍，享受荣华富贵，还是拼死一搏，死劾严嵩?他选择了后者，从此流芳百世。所以，人生中必然会遇到选择，而做出重要的决定更是迈向成功的关键。', 4, '2020-11-23 05:02:26', 2);
INSERT INTO `blog` VALUES (9, '《白夜行》', '“我的天空里没有太阳，总是黑夜，但并不暗，因为有东西代替了太阳。虽然没有太阳那么明亮，但对我来说已经足够。凭借着这份光，我便能把黑夜当成白天。你明白吧？我从来就没有太阳，所以不怕失去。”', 4, '2020-12-23 05:02:28', 2);
INSERT INTO `blog` VALUES (11, '内蒙古烤全羊', '烤全羊是蒙古族传统名菜，为招待贵宾或举行重大庆典时的盛宴特制的佳肴；烤全羊起源于西北游牧民族，是蒙古民族的餐中之尊，在以前是蒙古贵族们才能享用的美食。吃烤全羊一定要配点高度白酒，不要用店里的小刀小叉的直接上手，一口肉一口酒，分分钟吃出来那种草原上的豪迈之感。', 3, '2020-12-25 02:22:49', 2);
INSERT INTO `blog` VALUES (12, '大扫除', '暑假一到，我便开启懒惰模式。整天宅在家里，足不出户，瘫在床上或沙发上看看电视，吃完饭就看电视，看完电视写会儿作业然后就睡。家长想让我运动，加之搬家也有半年之久了，便组织了一场大扫除。', 1, '2020-12-25 03:42:18', 1);
INSERT INTO `blog` VALUES (13, 'python入门', 'Python是一个高层次的结合了解释性、编译性、互动性和面向对象的脚本语言。Python的设计具有很强的可读性，相比其他语言经常使用英文关键字，其他语言的一些标点符号，它具有比其他语言更有特色语法结构。Python对初级程序员而言，是一种伟大的语言，它支持广泛的应用程序开发，从简单的文字处理到 WWW 浏览器再到游戏。', 6, '2020-12-25 03:45:32', 1);
INSERT INTO `blog` VALUES (14, 'servlet简介', 'Java Servlet 是运行在 Web 服务器或应用服务器上的程序，它是作为来自 Web 浏览器或其他 HTTP 客户端的请求和HTTP服务器上的数据库或应用程序之间的中间层。使用Servlet，您可以收集来自网页表单的用户输入，呈现来自数据库或者其他源的记录，还可以动态创建网页。Java 类库的全部功能对 Servlet 来说都是可用的。它可以通过 sockets 和 RMI 机制与 applets、数据库或其他软件进行交互。', 6, '2020-12-25 03:52:25', 2);
INSERT INTO `blog` VALUES (15, '好友聚会', '今天和高中最好的几个哥们聚餐，仿佛又回到了那个夏天。', 1, '2022-08-27 15:42:31', 1);
INSERT INTO `blog` VALUES (18, '迈巴赫', '迈巴赫品牌首创于二十世纪二十年代。被誉为“设计之王”的威廉·迈巴赫不但是戴姆勒·奔驰公司的三位主要创始人之一，更是世界首辆梅赛德斯-奔驰汽车的发明者之一。1919年，难舍汽车梦想的威廉·迈巴赫与其子卡尔·迈巴赫共同缔造了“迈巴赫”这一传奇品牌——一个象征着完美和昂贵的轿车。', 7, '2022-09-03 11:45:11', 1);
INSERT INTO `blog` VALUES (19, '《数据库课程设计》', '数据库课程设计实验是一门独立开设的实验课程。《数据库概论》课程设计实验对于巩固数据库知识，加强学生的实际动手能力和提高学生综合素质十分必要。本课程设计实验主要围绕两方面内容：数据库设计和基本数据库结构设计。通过本课程，使学生能应用数据库系统的理论，掌握数据库的设计方法及数据库的运用和实现技术。', 6, '2022-09-07 19:28:01', 1);
INSERT INTO `blog` VALUES (20, '博客登录账号与密码', '账号：admin\r\n密码：admin', 8, '2022-09-07 19:29:02', 1);
INSERT INTO `blog` VALUES (21, '夏都西宁', '这里是中国夏都，一座以不可替代、不可复制的美震撼你的城市；这里是中国优秀旅游城市，一座在自我超越中不断刷新旅游历史记录，抒写一笔笔精彩的城市；这里就是青海西宁，一座被2275米海拔高高擎起，飞向更高梦想的城市。行走在西宁，行走在青海，旅游胜景灿若星辰，世界顶级旅游景点以本真风骨挺立于天地之间，旷世绝伦；国家级旅游景点彰显出高原独有的雄浑之气、壮美之感，摄人心魄。更有那多民族风情摇曳生姿，交相辉映；多元文化流光溢彩，各领风骚……在这片神奇而伟大的土地上，旅游意味着随时与美不期而遇。', 2, '2022-09-07 19:36:21', 1);
INSERT INTO `blog` VALUES (22, '《银河补习班》', '        马皓文因一次意外事故而入狱，让他遗憾地错过了儿子七年的成长时光。用自己独特的教育方法和满满的爱给予儿子马飞自由成长的空间，教会儿子独立思考的能力和面对困难的勇气。马飞面临学业问题，尽管在学校看来儿子没有可塑之处，但马皓文从未放弃，鼓励孩子找到心中的梦想并为之努力。阎主任和马皓文立下赌约，打算用一个学期时间将马飞的学业提高，证明他不是不可救药的学生，“学渣”也是可造之材。', 5, '2022-09-07 19:41:09', 1);
INSERT INTO `blog` VALUES (23, '布加迪', '       布加迪车是古典老式车中保有量最多的汽车之一，以布加迪为品牌的车型在世界多个著名汽车博物馆中可以看到，而且性能上乘，车身造型新颖流畅，直至发动机的配置都独具特色。布加迪的徽标是以布加迪汽车的创始人埃托里·布加迪姓名的字母“EB”组成，周围一圈小圆点象征滚珠轴承，底色为红色。布加迪的产品，做工精湛，性能卓越，它的每一辆轿车都可誉为世界名车，1956年停产。1991年意大利工业家罗曼诺·阿蒂奥利买得布加迪商标所有权，在意大利重建布加迪汽车公司，重新生产高性能、高质量的运动车及轿车。布加迪总计生产汽车七千余辆。因其厂址地理位置国籍的变化，1909-1918年在德国境内；1919年-1956年在法国境内；1991年重新建厂后在意大利境内。', 7, '2022-09-07 20:32:24', 1);
INSERT INTO `blog` VALUES (24, '密码学基础', '密码学是研究如何隐密地传递信息的学科。在现代特别指对信息以及其传输的数学性研究，常被认为是数学和计算机科学的分支，和信息论也密切相关。著名的密码学者Ron Rivest解释道：“密码学是关于如何在敌人存在的环境中通讯”，自工程学的角度，这相当于密码学与纯数学的异同。密码学是信息安全等相关议题，如认证、访问控制的核心。密码学的首要目的是隐藏信息的涵义，并不是隐藏信息的存在。密码学也促进了计算机科学，特别是在于电脑与网络安全所使用的技术，如访问控制与信息的机密性。密码学已被应用在日常生活：包括自动柜员机的芯片卡、电脑使用者存取密码、电子商务等等。\r\n密码是通信双方按约定的法则进行信息特殊变换的一种重要保密手段。依照这些法则，变明文为密文，称为加密变换；变密文为明文，称为脱密变换。密码在早期仅对文字或数码进行加、脱密变换，随着通信技术的发展，对语音、图像、数据等都可实施加、脱密变换。', 6, '2022-09-07 20:42:46', 1);
INSERT INTO `blog` VALUES (25, '《白鹿原》', '       小说主要讲述了他们的下一代白孝文、鹿兆海、黑娃这一代人的生活：白家后代中规中矩，黑娃却从小就显现出不安分。长大后，白孝文继任族长，黑娃在外做长工，认识了东家的小老婆田小娥，他将她带回村后，受到村人的排斥。黑娃离开村子后投奔革命军，又成为土匪。在此期间鹿子霖、白孝文等都吸上了鸦片，将家败光，去异乡谋生。鹿三以儿媳田小娥为耻，最终杀了她，因终日被田小娥死时的情形折磨而死去。白孝文则在外重新振作，终有一番作为，白灵加入了共产党。一个家庭两代子孙，为争夺白鹿原的统治代代争斗不已。', 4, '2022-09-07 21:26:00', 1);
INSERT INTO `blog` VALUES (26, '111', '111', 4, '2022-09-08 09:29:44', 1);
INSERT INTO `blog` VALUES (27, '你好李焕英', '今天去和同学一起去看电影了。', 5, '2022-10-05 22:26:40', 1);

-- ----------------------------
-- Table structure for blog_type
-- ----------------------------
DROP TABLE IF EXISTS `blog_type`;
CREATE TABLE `blog_type`  (
  `typeId` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `typeName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '类别名，在同一个用户下唯一',
  PRIMARY KEY (`typeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blog_type
-- ----------------------------
INSERT INTO `blog_type` VALUES (1, '生活');
INSERT INTO `blog_type` VALUES (2, '旅游');
INSERT INTO `blog_type` VALUES (3, '美食');
INSERT INTO `blog_type` VALUES (4, '读书');
INSERT INTO `blog_type` VALUES (5, '观影');
INSERT INTO `blog_type` VALUES (6, '学习');
INSERT INTO `blog_type` VALUES (7, '汽车');
INSERT INTO `blog_type` VALUES (8, '密码');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键，自动增长',
  `uname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `upwd` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nick` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `head` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '头像',
  `mood` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '心情',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '仲夏之梦1', '仲夏之夜.jpg', '学而不思则罔，思而不学则殆。');
INSERT INTO `user` VALUES (2, 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '逐浪之夏', 'jay.jpg', '老当益壮，宁移白首之心；穷且意坚，不坠青云之志！');

SET FOREIGN_KEY_CHECKS = 1;
