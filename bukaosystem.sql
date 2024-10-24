/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : bukaosystem

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 24/10/2024 21:34:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for exam_answer_sheet
-- ----------------------------
DROP TABLE IF EXISTS `exam_answer_sheet`;
CREATE TABLE `exam_answer_sheet`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `examId` int UNSIGNED NOT NULL,
  `userId` int UNSIGNED NOT NULL,
  `score` int UNSIGNED NULL DEFAULT NULL,
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `isGraded` int UNSIGNED NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_exam_user`(`examId`, `userId`) USING BTREE,
  INDEX `as_userId`(`userId`) USING BTREE,
  CONSTRAINT `as_userId` FOREIGN KEY (`userId`) REFERENCES `exam_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `exam_answer_sheet_ibfk_1` FOREIGN KEY (`examId`) REFERENCES `exam_exam` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_answer_sheet
-- ----------------------------
INSERT INTO `exam_answer_sheet` VALUES (1, 2, 3, 80, '2024-09-04 00:55:51', 1);
INSERT INTO `exam_answer_sheet` VALUES (5, 2, 4, 89, '2024-09-05 00:13:28', 0);
INSERT INTO `exam_answer_sheet` VALUES (10, 2, 5, 91, '2024-09-05 00:24:04', 0);
INSERT INTO `exam_answer_sheet` VALUES (18, 2, 6, 98, '2024-09-05 00:24:56', 0);
INSERT INTO `exam_answer_sheet` VALUES (21, 2, 8, 99, '2024-09-05 00:25:57', 0);
INSERT INTO `exam_answer_sheet` VALUES (23, 2, 9, 99, '2024-09-05 00:26:02', 0);

-- ----------------------------
-- Table structure for exam_answer_sheet_detail
-- ----------------------------
DROP TABLE IF EXISTS `exam_answer_sheet_detail`;
CREATE TABLE `exam_answer_sheet_detail`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `answerId` int UNSIGNED NOT NULL,
  `resourceId` int UNSIGNED NOT NULL,
  `userKey` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `isTrue` enum('对','错','半对半错','未知') CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '未知',
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_answerId_resourceId`(`answerId`, `resourceId`) USING BTREE,
  INDEX `asd_resourceId`(`resourceId`) USING BTREE,
  CONSTRAINT `asd_answerId` FOREIGN KEY (`answerId`) REFERENCES `exam_answer_sheet` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `asd_resourceId` FOREIGN KEY (`resourceId`) REFERENCES `exam_resources` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_answer_sheet_detail
-- ----------------------------
INSERT INTO `exam_answer_sheet_detail` VALUES (1, 1, 1, 'ces', '对', '2024-07-02 10:59:34');
INSERT INTO `exam_answer_sheet_detail` VALUES (2, 1, 2, '80', '对', '2024-09-04 01:08:00');
INSERT INTO `exam_answer_sheet_detail` VALUES (3, 1, 3, 'ce', '对', '2024-07-02 11:01:08');
INSERT INTO `exam_answer_sheet_detail` VALUES (4, 1, 4, '谢', '错', '2024-09-04 01:08:46');
INSERT INTO `exam_answer_sheet_detail` VALUES (8, 1, 5, '谢', '未知', '2024-09-05 00:13:08');
INSERT INTO `exam_answer_sheet_detail` VALUES (10, 1, 6, '5555', '未知', '2024-09-04 01:18:27');

-- ----------------------------
-- Table structure for exam_class
-- ----------------------------
DROP TABLE IF EXISTS `exam_class`;
CREATE TABLE `exam_class`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `comment` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_class
-- ----------------------------
INSERT INTO `exam_class` VALUES (1, 'Class A', 'calss A', '2020-01-01 00:00:00');
INSERT INTO `exam_class` VALUES (2, 'Class 1', 'Class 1 Comment', '2020-01-01 00:00:00');

-- ----------------------------
-- Table structure for exam_class_student
-- ----------------------------
DROP TABLE IF EXISTS `exam_class_student`;
CREATE TABLE `exam_class_student`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `classId` int UNSIGNED NOT NULL,
  `studentId` int UNSIGNED NOT NULL,
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `cs_stuId`(`studentId`) USING BTREE,
  INDEX `cs_classId`(`classId`) USING BTREE,
  CONSTRAINT `cs_classId` FOREIGN KEY (`classId`) REFERENCES `exam_class` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cs_stuId` FOREIGN KEY (`studentId`) REFERENCES `exam_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_class_student
-- ----------------------------
INSERT INTO `exam_class_student` VALUES (1, 1, 3, '2024-07-01 14:10:30');
INSERT INTO `exam_class_student` VALUES (2, 2, 3, '2024-07-01 14:13:48');

-- ----------------------------
-- Table structure for exam_class_teacher
-- ----------------------------
DROP TABLE IF EXISTS `exam_class_teacher`;
CREATE TABLE `exam_class_teacher`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `classId` int UNSIGNED NOT NULL,
  `teacherId` int UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ct_classId`(`classId`) USING BTREE,
  INDEX `ct_teacherId`(`teacherId`) USING BTREE,
  CONSTRAINT `ct_classId` FOREIGN KEY (`classId`) REFERENCES `exam_class` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ct_teacherId` FOREIGN KEY (`teacherId`) REFERENCES `exam_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_class_teacher
-- ----------------------------

-- ----------------------------
-- Table structure for exam_course
-- ----------------------------
DROP TABLE IF EXISTS `exam_course`;
CREATE TABLE `exam_course`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `comment` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_course
-- ----------------------------
INSERT INTO `exam_course` VALUES (1, 'course1', NULL, '2024-06-29 09:46:26');
INSERT INTO `exam_course` VALUES (2, 'course2', NULL, '2024-06-29 09:46:39');
INSERT INTO `exam_course` VALUES (4, '计算机导论', NULL, '2024-07-08 09:12:51');
INSERT INTO `exam_course` VALUES (5, '计算机导论2', NULL, '2024-07-08 09:12:56');
INSERT INTO `exam_course` VALUES (6, '计算机导论3', NULL, '2024-07-08 09:21:09');

-- ----------------------------
-- Table structure for exam_course_chapter
-- ----------------------------
DROP TABLE IF EXISTS `exam_course_chapter`;
CREATE TABLE `exam_course_chapter`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `courseId` int UNSIGNED NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `cc_crouseId`(`courseId`) USING BTREE,
  CONSTRAINT `cc_crouseId` FOREIGN KEY (`courseId`) REFERENCES `exam_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_course_chapter
-- ----------------------------
INSERT INTO `exam_course_chapter` VALUES (1, 1, 'test1');
INSERT INTO `exam_course_chapter` VALUES (2, 2, 'test2');
INSERT INTO `exam_course_chapter` VALUES (3, 1, 'test3');

-- ----------------------------
-- Table structure for exam_course_class
-- ----------------------------
DROP TABLE IF EXISTS `exam_course_class`;
CREATE TABLE `exam_course_class`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `classId` int UNSIGNED NOT NULL,
  `courseId` int UNSIGNED NOT NULL,
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `cc_classId`(`classId`) USING BTREE,
  INDEX `cc_courseId`(`courseId`) USING BTREE,
  CONSTRAINT `cc_classId` FOREIGN KEY (`classId`) REFERENCES `exam_class` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `cc_courseId` FOREIGN KEY (`courseId`) REFERENCES `exam_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_course_class
-- ----------------------------
INSERT INTO `exam_course_class` VALUES (1, 1, 1, '2024-09-03 16:15:25');
INSERT INTO `exam_course_class` VALUES (2, 1, 2, '2024-09-03 16:15:31');
INSERT INTO `exam_course_class` VALUES (3, 2, 2, '2024-09-03 16:15:40');

-- ----------------------------
-- Table structure for exam_exam
-- ----------------------------
DROP TABLE IF EXISTS `exam_exam`;
CREATE TABLE `exam_exam`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `courseId` int UNSIGNED NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `comment` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `place` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `state` int UNSIGNED NULL DEFAULT 0,
  `beginTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `exam_courseId`(`courseId`) USING BTREE,
  CONSTRAINT `exam_courseId` FOREIGN KEY (`courseId`) REFERENCES `exam_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_exam
-- ----------------------------
INSERT INTO `exam_exam` VALUES (2, 1, 'exam2', 'test2', '1', 0, '2024-07-01 14:12:05', '2024-07-02 10:12:07', '2024-07-01 14:12:11');
INSERT INTO `exam_exam` VALUES (3, 2, 'exam3', 'exam3', NULL, 0, '2024-08-27 08:44:57', '2024-08-29 08:45:02', '2024-08-19 08:45:04');
INSERT INTO `exam_exam` VALUES (4, 4, 'exam4', 'exam4', NULL, 0, '2024-08-26 08:45:17', '2024-08-23 08:45:20', '2024-08-19 08:45:22');

-- ----------------------------
-- Table structure for exam_exam_class
-- ----------------------------
DROP TABLE IF EXISTS `exam_exam_class`;
CREATE TABLE `exam_exam_class`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `examId` int UNSIGNED NOT NULL,
  `classId` int UNSIGNED NOT NULL,
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ecMid_classId`(`classId`) USING BTREE,
  INDEX `ecMid_examId`(`examId`) USING BTREE,
  CONSTRAINT `ecMid_classId` FOREIGN KEY (`classId`) REFERENCES `exam_class` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ecMid_examId` FOREIGN KEY (`examId`) REFERENCES `exam_exam` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_exam_class
-- ----------------------------
INSERT INTO `exam_exam_class` VALUES (2, 2, 1, '2024-07-01 14:13:15');
INSERT INTO `exam_exam_class` VALUES (3, 2, 2, '2024-07-01 14:13:37');
INSERT INTO `exam_exam_class` VALUES (4, 3, 1, '2024-08-19 08:45:37');
INSERT INTO `exam_exam_class` VALUES (5, 4, 1, '2024-08-19 08:45:45');

-- ----------------------------
-- Table structure for exam_exam_resources
-- ----------------------------
DROP TABLE IF EXISTS `exam_exam_resources`;
CREATE TABLE `exam_exam_resources`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `examId` int UNSIGNED NOT NULL,
  `resourceId` int UNSIGNED NOT NULL,
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `erMid_examId`(`examId`) USING BTREE,
  INDEX `erMid_resourceId`(`resourceId`) USING BTREE,
  CONSTRAINT `erMid_examId` FOREIGN KEY (`examId`) REFERENCES `exam_exam` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `erMid_resourceId` FOREIGN KEY (`resourceId`) REFERENCES `exam_resources` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_exam_resources
-- ----------------------------
INSERT INTO `exam_exam_resources` VALUES (1, 2, 1, '2024-07-02 10:58:37');
INSERT INTO `exam_exam_resources` VALUES (2, 2, 2, '2024-07-02 10:58:45');
INSERT INTO `exam_exam_resources` VALUES (3, 2, 3, '2024-07-02 10:58:48');
INSERT INTO `exam_exam_resources` VALUES (4, 2, 4, '2024-07-02 10:58:53');

-- ----------------------------
-- Table structure for exam_resources
-- ----------------------------
DROP TABLE IF EXISTS `exam_resources`;
CREATE TABLE `exam_resources`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `courseId` int UNSIGNED NOT NULL,
  `chapterId` int UNSIGNED NOT NULL,
  `question` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `type` enum('single_choice','true_false','completion','bigquestion') CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT 'bigquestion',
  `options` json NULL,
  `key` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `analysis` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `score` double NULL DEFAULT 2,
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `re_chapter`(`chapterId`) USING BTREE,
  INDEX `re_courseId`(`courseId`) USING BTREE,
  CONSTRAINT `re_chapter` FOREIGN KEY (`chapterId`) REFERENCES `exam_course_chapter` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `re_courseId` FOREIGN KEY (`courseId`) REFERENCES `exam_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_resources
-- ----------------------------
INSERT INTO `exam_resources` VALUES (1, 1, 1, 'test', 'bigquestion', NULL, 'ces', NULL, 2, '2024-07-02 10:57:29');
INSERT INTO `exam_resources` VALUES (2, 1, 2, 'test2', 'bigquestion', NULL, '123', NULL, 2, '2024-07-02 10:57:41');
INSERT INTO `exam_resources` VALUES (3, 1, 2, 'test3', 'bigquestion', NULL, 'ce', NULL, 2, '2024-07-02 10:57:54');
INSERT INTO `exam_resources` VALUES (4, 1, 3, 'test4', 'bigquestion', '1', '12', NULL, 3, '2024-07-02 10:58:10');
INSERT INTO `exam_resources` VALUES (5, 1, 2, 'What is the capital of France?', 'single_choice', '{\"A\": \"Berlin\", \"B\": \"Madrid\", \"C\": \"Paris\", \"D\": \"Rome\"}', 'C', 'The capital of France is Paris.', 5, NULL);
INSERT INTO `exam_resources` VALUES (6, 1, 2, 'What is the capital of France?', 'single_choice', '{\"A\": \"Berlin\", \"B\": \"Madrid\", \"C\": \"Paris\", \"D\": \"Rome\"}', 'C', 'The capital of France is Paris.', 5, NULL);
INSERT INTO `exam_resources` VALUES (7, 1, 2, 'What is the capital of France?', NULL, NULL, 'C', 'The capital of France is Paris.', 5, NULL);
INSERT INTO `exam_resources` VALUES (8, 1, 2, 'This is a question', 'single_choice', '{\"A\": \"是一个好东西\", \"B\": \"是一个坏东西\", \"C\": \"是一个不好不坏东西\", \"D\": \"以上都不是\"}', 'A', 'This is an analysis', 5, '2023-07-03 00:00:00');
INSERT INTO `exam_resources` VALUES (9, 1, 2, 'This is a question', 'single_choice', '{\"A\": \"是一个好东西\", \"B\": \"是一个坏东西\", \"C\": \"是一个不好不坏东西\", \"D\": \"以上都不是\"}', 'A', 'This is an analysis', 5, '2023-07-03 00:00:00');
INSERT INTO `exam_resources` VALUES (10, 1, 2, 'This is a question', 'single_choice', '{\"A\": \"是一个好东西\", \"B\": \"是一个坏东西\", \"C\": \"是一个不好不坏东西\", \"D\": \"以上都不是\"}', 'A', 'This is an analysis', 5, '2023-07-03 00:00:00');
INSERT INTO `exam_resources` VALUES (11, 1, 2, 'This is a question', 'single_choice', '{\"A\": \"是一个好东西\", \"B\": \"是一个坏东西\", \"C\": \"是一个不好不坏东西\", \"D\": \"以上都不是\"}', 'A', 'This is an analysis', 5, '2023-07-03 00:00:00');

-- ----------------------------
-- Table structure for exam_teacher_course
-- ----------------------------
DROP TABLE IF EXISTS `exam_teacher_course`;
CREATE TABLE `exam_teacher_course`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `teacherId` int UNSIGNED NOT NULL,
  `courseId` int UNSIGNED NOT NULL,
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tc_crouseId`(`courseId`) USING BTREE,
  INDEX `tc_teacherId`(`teacherId`) USING BTREE,
  CONSTRAINT `tc_crouseId` FOREIGN KEY (`courseId`) REFERENCES `exam_course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tc_teacherId` FOREIGN KEY (`teacherId`) REFERENCES `exam_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_teacher_course
-- ----------------------------
INSERT INTO `exam_teacher_course` VALUES (3, 2, 1, '2024-07-01 15:33:27');
INSERT INTO `exam_teacher_course` VALUES (4, 2, 2, '2024-08-19 08:43:58');
INSERT INTO `exam_teacher_course` VALUES (5, 2, 4, '2024-08-19 08:44:03');
INSERT INTO `exam_teacher_course` VALUES (6, 2, 5, '2024-08-19 08:44:08');

-- ----------------------------
-- Table structure for exam_user
-- ----------------------------
DROP TABLE IF EXISTS `exam_user`;
CREATE TABLE `exam_user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `role` enum('student','teacher','admin') CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sex` enum('男','女','未知') CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '未知',
  `createTime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_user
-- ----------------------------
INSERT INTO `exam_user` VALUES (2, 'test8', 'test8', '123456123', 'teacher', '123456789@qq.com', '15088949856', '女', '2024-06-28 16:54:43');
INSERT INTO `exam_user` VALUES (3, 'test4', 'test4', '123456', 'student', '123456789@qq.com', '15088949856', '未知', '2024-06-28 16:54:46');
INSERT INTO `exam_user` VALUES (4, 'test5', 'test5', '123456', 'student', '123456789@qq.com', '15088949856', '未知', '2024-06-28 16:58:56');
INSERT INTO `exam_user` VALUES (5, 'test1', 'test1', '123456', 'student', '123456789@qq.com', '15088949856', '女', '2024-06-28 18:28:35');
INSERT INTO `exam_user` VALUES (6, 'test2', 'test2', '123456', 'teacher', '123456789@qq.com', '15088949856', '未知', '2024-06-28 18:30:37');
INSERT INTO `exam_user` VALUES (8, 'test4', 'test23', 'password456', 'teacher', 'jane.doe@example.com', '987-654-3210', '男', '2023-02-15 09:30:00');
INSERT INTO `exam_user` VALUES (9, 'test8', 'test8', '123456', 'teacher', '123456789@qq.com', '15088949856', '女', '2024-06-28 16:54:43');

SET FOREIGN_KEY_CHECKS = 1;
