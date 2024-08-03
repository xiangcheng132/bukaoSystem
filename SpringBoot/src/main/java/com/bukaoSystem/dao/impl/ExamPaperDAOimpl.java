package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamPaperDAO;
import com.bukaoSystem.model.ExamExam;
import com.bukaoSystem.model.ExamResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ExamPaperDAOimpl implements ExamPaperDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    //创建新试卷
    @Override
    public ExamExam save(ExamExam examPaper) {
        String sql = "INSERT INTO exam_exam (courseid, name, comment, place, state, beginTime, endTime, createTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, examPaper.getCourseId());
            ps.setString(2, examPaper.getName());
            ps.setString(3, examPaper.getComment());
            ps.setString(4, examPaper.getPlace());
            ps.setInt(5, examPaper.getState());
            ps.setTimestamp(6, Timestamp.valueOf(examPaper.getBeginTime()));
            ps.setTimestamp(7, Timestamp.valueOf(examPaper.getEndTime()));
            ps.setTimestamp(8, Timestamp.valueOf(examPaper.getCreateTime()));
            return ps;
        }, keyHolder);
        return examPaper;
    }

    //返回对应数量的随机的资源题目
    public List<ExamResources> findRandomResourcesByChapterAndType(Long courseId,Long chapterId, String Type, int count) {
        String sql = "SELECT DISTINCT question, * FROM exam_resources WHERE chapterId = ? AND type = ? AND courseId = ? ORDER BY RAND() LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{chapterId,Type,courseId ,count}, new BeanPropertyRowMapper<>(ExamResources.class));
    }
    @Override
    // 批量复制资源并修改分值
    public List<ExamResources> batchCopyResourcesWithScores(Long courseId, Long chapterId, String type, int count, int score) {
        // 查询随机资源
        List<ExamResources> resources = findRandomResourcesByChapterAndType(courseId, chapterId, type, count);

        if (resources.isEmpty()) {
            return null;
        }

        // 使用 Set 来去重 question 字段
        Set<String> uniqueQuestions = new HashSet<>();
        List<Object[]> batchArgs = new ArrayList<>();

        // 验证数据库内无相同题目字段值且分数字段值与传入的分数参数值相同的记录
        String checkSql = "SELECT COUNT(*) FROM exam_resources WHERE question = ? AND score = ?";

        for (ExamResources resource : resources) {
            if (uniqueQuestions.add(resource.getQuestion())) {
                List<Integer> existingCounts = jdbcTemplate.queryForList(checkSql, new Object[]{resource.getQuestion(), score}, Integer.class);
                int existingCount = existingCounts.isEmpty() ? 0 : existingCounts.get(0);

                if (existingCount == 0) {
                    // 复制原始资源的属性，并设置新的成绩
                    Object[] params = {
                            resource.getCourseId(), // 复制课程 ID
                            resource.getChapterId(), // 复制章节 ID
                            resource.getQuestion(), // 复制问题
                            resource.getType(), // 复制类型
                            resource.getOptions(),// 复制选择题选项
                            resource.getKey(),// 复制答案
                            resource.getAnalysis(),// 复制解析
                            score // 新的分值
                    };
                    batchArgs.add(params);
                }
            }
        }

        // 执行批量插入
        if (!batchArgs.isEmpty()) {
            String sql = "INSERT INTO exam_resources (courseId, chapterId, question, type, options, key, analysis, score) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.batchUpdate(sql, batchArgs);
        }
        // 返回最开始查询到的资源
        return resources;
    }

    //添加对应数量的试卷和资源对应关系
    @Override
    public void saveExamPaperResource(Long examId, Long resourceId) {
        String sql = "INSERT INTO exam_exam_resources (examid, resourceid, createTime) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, examId, resourceId, Timestamp.valueOf(LocalDateTime.now()));
    }

}
