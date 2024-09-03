package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamAnswerSheetDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamAnswerSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public void saveExamAnswerSheet(ExamAnswerSheet examAnswerSheet) {
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

        jdbcTemplate.update(sql.toString(), params.toArray());
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

}
