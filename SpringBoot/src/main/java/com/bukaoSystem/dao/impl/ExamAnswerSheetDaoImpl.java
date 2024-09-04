package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamAnswerSheetDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamAnswerSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ExamAnswerSheetDaoImpl implements ExamAnswerSheetDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<ExamAnswerSheet> rowMapper = new BeanPropertyRowMapper<>(ExamAnswerSheet.class);

    @Override
    public List<ExamAnswerSheet> getAllExamAnswerSheets() {
        String sql = "SELECT * FROM exam_answer_sheet";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<ExamAnswerSheet> getExamAnswerSheetsById(Long id) {
        String sql = "SELECT * FROM exam_answer_sheet WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id);
    }

    @Override
    public List<ExamAnswerSheet> getExamAnswerSheetsByExamId(Long examId) {
        String sql = "SELECT eas.*, u.username " +
                "FROM exam_answer_sheet eas " +
                "JOIN exam_user u ON eas.userId = u.id " +
                "WHERE eas.examId = ?";
        return jdbcTemplate.query(sql, rowMapper, examId);
    }

    @Override
    public List<ExamAnswerSheet> getExamAnswerSheetsByUserId(Long userId) {
        String sql = "SELECT * FROM exam_answer_sheet WHERE userId = ?";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    @Override
    public Long saveExamAnswerSheet(ExamAnswerSheet examAnswerSheet) {
        StringBuilder sql = new StringBuilder("INSERT INTO exam_answer_sheet (examId, userId, score");
        StringBuilder values = new StringBuilder(" VALUES (?, ?, ?");
        List<Object> params = new ArrayList<>();
        params.add(examAnswerSheet.getExamId());
        params.add(examAnswerSheet.getUserId());
        params.add(examAnswerSheet.getScore());

        if (examAnswerSheet.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examAnswerSheet.getCreateTime());
        }

        sql.append(")");
        values.append(")");
        sql.append(values);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }


    @Override
    public void updateExamAnswerSheet(ExamAnswerSheet examAnswerSheet) {
        StringBuilder sql = new StringBuilder("UPDATE exam_answer_sheet SET examId = ?, userId = ?, score = ?");
        List<Object> params = new ArrayList<>();
        params.add(examAnswerSheet.getExamId());
        params.add(examAnswerSheet.getUserId());
        params.add(examAnswerSheet.getScore());

        if (examAnswerSheet.getCreateTime() != null) {
            sql.append(", createTime = ?");
            params.add(examAnswerSheet.getCreateTime());
        }

        sql.append(" WHERE id = ?");
        params.add(examAnswerSheet.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void deleteExamAnswerSheet(Long id) {
        String sql = "DELETE FROM exam_answer_sheet WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除id: " + id + "的信息，该id下有关联信息。");
        }
    }
    @Override
    public Long saveOrUpdateExamAnswerSheet(ExamAnswerSheet examAnswerSheet) {
        StringBuilder sql = new StringBuilder("INSERT INTO exam_answer_sheet (examId, userId, score");
        StringBuilder values = new StringBuilder(" VALUES (?, ?, ?");
        List<Object> params = new ArrayList<>();
        params.add(examAnswerSheet.getExamId());
        params.add(examAnswerSheet.getUserId());
        params.add(examAnswerSheet.getScore());

        if (examAnswerSheet.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examAnswerSheet.getCreateTime());
        }

        sql.append(")");
        values.append(")");
        sql.append(values);
        sql.append(" ON DUPLICATE KEY UPDATE score = VALUES(score), createTime = VALUES(createTime)");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            return ps;
        }, keyHolder);

        // 如果执行插入操作则rowsAffected==1，返回生成的主键
        if (rowsAffected == 1) {
            return keyHolder.getKey().longValue();
        } else {
            // 如果是更新操作则rowsAffected==2，查询现有记录的主键 ID
            return getExistingId(examAnswerSheet.getExamId(), examAnswerSheet.getUserId());
        }
    }

    private Long getExistingId(Long examId, Long userId) {
        // 根据 examId 和 userId 查询现有记录的主键 id
        String sql = "SELECT id FROM exam_answer_sheet WHERE examId = ? AND userId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{examId, userId}, Long.class);
    }

    @Override
    public List<ExamAnswerSheet> findAnswerSheetsByTeacherId(Long teacherId) {
        //复杂版本
//        String sql = "SELECT eas.*, eu.username " +
//                "FROM exam_answer_sheet eas " +
//                "JOIN exam_user eu ON eas.userId = eu.id " +
//                "WHERE eas.examId IN (" +
//                "  SELECT DISTINCT eec.examId " +
//                "  FROM exam_teacher_course etc " +
//                "  JOIN exam_course_class ecc ON etc.courseId = ecc.courseId " +
//                "  JOIN exam_exam_class eec ON ecc.classId = eec.classId " +
//                "  WHERE etc.teacherId = ?" +
//                ")";

        String sql = "SELECT eas.*, eu.username " +
                "FROM exam_teacher_course etc " +
                "JOIN exam_exam ee ON etc.courseId = ee.courseId " +
                "JOIN exam_answer_sheet eas ON ee.id = eas.examId " +
                "JOIN exam_user eu ON eas.userId = eu.id " +
                "WHERE etc.teacherId = ?";

        return jdbcTemplate.query(sql, new Object[]{teacherId}, new BeanPropertyRowMapper<>(ExamAnswerSheet.class));
    }

}
