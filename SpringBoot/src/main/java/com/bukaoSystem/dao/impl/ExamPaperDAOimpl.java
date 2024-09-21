package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamPaperDAO;
import com.bukaoSystem.model.ExamPaperDto;
import com.bukaoSystem.model.ExamResources;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamPaperDAOimpl implements ExamPaperDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private RowMapper<ExamResources> rowMapper = new RowMapper<ExamResources>() {
        @Override
        public ExamResources mapRow(ResultSet rs, int rowNum) throws SQLException {
            ExamResources examResources = new ExamResources();
            examResources.setId(rs.getLong("id"));
            examResources.setCourseId(rs.getLong("courseId"));
            examResources.setChapterId(rs.getLong("chapterId"));
            examResources.setQuestion(rs.getString("question"));
            examResources.setType(ExamResources.Type.valueOf(rs.getString("type")));
            try {
                examResources.setOptions(objectMapper.readTree(rs.getString("options")));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            examResources.setKey(rs.getString("key"));
            examResources.setAnalysis(rs.getString("analysis"));
            examResources.setScore(rs.getDouble("score"));
            examResources.setCreateTime(rs.getString("createTime"));
            return examResources;
        }
    };
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
        return jdbcTemplate.query(sql, new Object[]{Type, courseId, Type, courseId, count}, rowMapper);
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
    //添加对应数量的试卷和资源对应关系
    @Override
    public void saveExamPaperResource(Long examId, Long resourceId) {
        String sql = " INSERT INTO exam_exam_resources (examId, resourceId, createTime ) VALUES (?, ?, ?) ";
        jdbcTemplate.update(sql, examId, resourceId, Timestamp.valueOf(LocalDateTime.now()));
    }
    //添加对应试卷和班级对应关系
    @Override
    public void saveExamExamClass(Long examId, Long classId) {
        String sql = " INSERT INTO exam_exam_class (examId, classId,createTime) VALUES (?, ?,?) ";
        jdbcTemplate.update(sql, examId, classId, Timestamp.valueOf(LocalDateTime.now()));
    }
}
