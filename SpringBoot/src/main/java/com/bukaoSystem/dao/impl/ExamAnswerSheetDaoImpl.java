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
        String sql = "SELECT * FROM exam_answer_sheet WHERE examId = ?";
        return jdbcTemplate.query(sql, rowMapper, examId);
    }

    @Override
    public List<ExamAnswerSheet> getExamAnswerSheetsByUserId(Long userId) {
        String sql = "SELECT * FROM exam_answer_sheet WHERE userId = ?";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    @Override
    public void saveExamAnswerSheet(ExamAnswerSheet examAnswerSheet) {
        String sql = "INSERT INTO exam_answer_sheet (examId, userId, score, createTime) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, examAnswerSheet.getExamId(), examAnswerSheet.getUserId(), examAnswerSheet.getScore(), examAnswerSheet.getCreateTime());
    }

    @Override
    public void updateExamAnswerSheet(ExamAnswerSheet examAnswerSheet) {
        String sql = "UPDATE exam_answer_sheet SET examId = ?, userId = ?, score = ?, createTime = ? WHERE id = ?";
        jdbcTemplate.update(sql, examAnswerSheet.getExamId(), examAnswerSheet.getUserId(), examAnswerSheet.getScore(), examAnswerSheet.getCreateTime(), examAnswerSheet.getId());
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
