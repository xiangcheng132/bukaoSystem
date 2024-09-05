package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamPaperDAO;
import com.bukaoSystem.model.ExamPaperDto;
import com.bukaoSystem.model.ExamResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamPaperDAOimpl implements ExamPaperDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //添加试卷
    @Override
    public Long save(ExamPaperDto examExam) {
        // 构建插入 SQL 语句
        StringBuilder sql = new StringBuilder("INSERT INTO exam_exam (courseId, name, comment, place, state, beginTime, endTime, createTime)");
        StringBuilder values = new StringBuilder(" VALUES (?, ?, ?, ?, 0, ?, ?, ?)");

        // 构建参数列表
        List<Object> params = new ArrayList<>();
        params.add(examExam.getCourseId());
        params.add(examExam.getName());
        params.add(examExam.getComment());
        params.add(examExam.getPlace());
        params.add(examExam.getBeginTime());
        params.add(examExam.getEndTime());
        // 当前时间作为 createTime
        params.add(Timestamp.valueOf(LocalDateTime.now()));

        // 拼接完整 SQL
        sql.append(values);

        // 打印生成的 SQL 以便调试
        System.out.println("Generated SQL: " + sql);
        System.out.println("Params: " + params);

        // 使用 KeyHolder 获取自增主键
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            return ps;
        }, keyHolder);

        // 检查是否成功生成 ID
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().longValue();
        } else {
            throw new RuntimeException("Creating exam failed, no ID obtained.");
        }
    }



    @Override
    //返回对应数量的随机的资源题目
    public List<ExamResources> findRandomResourcesByChapterAndType(Long courseId, String Type, int count) {
        // 使用子查询来去重 question 字段，并获取所有列
        String sql = "SELECT * " +
                "FROM exam_resources er " +
                "WHERE er.question IN (" +
                "    SELECT DISTINCT question " +
                "    FROM exam_resources " +
                "    WHERE type = ? AND courseId = ?" +
                ") AND er.type = ? AND er.courseId = ? " +
                "ORDER BY RAND() " +
                "LIMIT ?";

        return jdbcTemplate.query(sql, new Object[]{Type, courseId, Type, courseId, count}, new BeanPropertyRowMapper<>(ExamResources.class));
//        return jdbcTemplate.query(sql, new Object[]{Type,courseId ,count}, new BeanPropertyRowMapper<>(ExamResources.class));
    }

    public void batchCopyResources(List<ExamResources> resources) {
        if (resources == null || resources.isEmpty()) {
            return;
        }
        List<Object[]> batchArgs = new ArrayList<>();
        for (ExamResources resource : resources) {
            Object[] params = {
                    resource.getCourseId(), // 复制课程 ID
                    resource.getChapterId(), // 复制章节 ID
                    resource.getQuestion(), // 复制问题
                    resource.getType(), // 复制类型
                    resource.getOptions(),// 复制选择题选项
                    resource.getKey(),// 复制答案
                    resource.getAnalysis(),// 复制解析
                    resource.getScore()// 复制分值
            };
            batchArgs.add(params);
        }
        if (!batchArgs.isEmpty()) {
            String sql = "INSERT INTO exam_resources (courseId, chapterId, question, type, options, key, analysis, score) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.batchUpdate(sql, batchArgs);
        }
    }

//    @Override
//    // 批量复制资源并修改分值
//    public List<ExamResources> batchCopyResourcesWithScores(Long courseId, Long chapterId, String type, int count, int score) {
//        // 查询随机资源
//        List<ExamResources> resources = findRandomResourcesByChapterAndType(courseId, chapterId, type, count);
//
//        if (resources.isEmpty()) {
//            return null;
//        }
//
//        // 使用 Set 来去重 question 字段
//        Set<String> uniqueQuestions = new HashSet<>();
//        List<Object[]> batchArgs = new ArrayList<>();
//
//        // 验证数据库内无相同题目字段值且分数字段值与传入的分数参数值相同的记录
//        String checkSql = "SELECT COUNT(*) FROM exam_resources WHERE question = ? AND score = ?";
//
//        for (ExamResources resource : resources) {
//            if (uniqueQuestions.add(resource.getQuestion())) {
//                List<Integer> existingCounts = jdbcTemplate.queryForList(checkSql, new Object[]{resource.getQuestion(), score}, Integer.class);
//                int existingCount = existingCounts.isEmpty() ? 0 : existingCounts.get(0);
//
//                if (existingCount == 0) {
//                    // 复制原始资源的属性，并设置新的成绩
//                    Object[] params = {
//                            resource.getCourseId(), // 复制课程 ID
//                            resource.getChapterId(), // 复制章节 ID
//                            resource.getQuestion(), // 复制问题
//                            resource.getType(), // 复制类型
//                            resource.getOptions(),// 复制选择题选项
//                            resource.getKey(),// 复制答案
//                            resource.getAnalysis(),// 复制解析
//                            score // 新的分值
//                    };
//                    batchArgs.add(params);
//                }
//            }
//        }
//
//        // 执行批量插入
//        if (!batchArgs.isEmpty()) {
//            String sql = "INSERT INTO exam_resources (courseId, chapterId, question, type, options, key, analysis, score) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//            jdbcTemplate.batchUpdate(sql, batchArgs);
//        }
//        // 返回最开始查询到的资源
//        return resources;
//    }

    //添加对应数量的试卷和资源对应关系
    @Override
    public void saveExamPaperResource(Long examId, Long resourceId) {
        String sql = " INSERT INTO exam_exam_resources (examId, resourceId, createTime ) VALUES (?, ?, ?) ";
        jdbcTemplate.update(sql, examId, resourceId, Timestamp.valueOf(LocalDateTime.now()));
    }
}
