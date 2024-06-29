package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamAnswerSheetDetailDao;
import com.bukaoSystem.model.ExamAnswerSheetDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExamAnswerSheetDetailDaoImpl implements ExamAnswerSheetDetailDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<ExamAnswerSheetDetail> rowMapper = new BeanPropertyRowMapper<>(ExamAnswerSheetDetail.class);

    @Override
    public List<ExamAnswerSheetDetail> getAllExamAnswerSheetDetails() {
        String sql = "SELECT * FROM exam_answer_sheet_detail";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsById(Long id) {
        String sql = "SELECT * FROM exam_answer_sheet_detail WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id);
    }

    @Override
    public List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsByAnswerId(Long answerId) {
        String sql = "SELECT * FROM exam_answer_sheet_detail WHERE answerId = ?";
        return jdbcTemplate.query(sql, rowMapper, answerId);
    }

    @Override
    public List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsByResourceId(Long resourceId) {
        String sql = "SELECT * FROM exam_answer_sheet_detail WHERE resourceId = ?";
        return jdbcTemplate.query(sql, rowMapper, resourceId);
    }

    @Override
    public void saveExamAnswerSheetDetail(ExamAnswerSheetDetail examAnswerSheetDetail) {
        String sql = "INSERT INTO exam_answer_sheet_detail (answerId, resourceId, userKey, isTrue, createTime) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, examAnswerSheetDetail.getAnswerId(), examAnswerSheetDetail.getResourceId(), examAnswerSheetDetail.getUserKey(), examAnswerSheetDetail.getIsTrue(), examAnswerSheetDetail.getCreateTime());
    }

    @Override
    public void updateExamAnswerSheetDetail(ExamAnswerSheetDetail examAnswerSheetDetail) {
        String sql = "UPDATE exam_answer_sheet_detail SET answerId = ?, resourceId = ?, userKey = ?, isTrue = ?, createTime = ? WHERE id = ?";
        jdbcTemplate.update(sql, examAnswerSheetDetail.getAnswerId(), examAnswerSheetDetail.getResourceId(), examAnswerSheetDetail.getUserKey(), examAnswerSheetDetail.getIsTrue(), examAnswerSheetDetail.getCreateTime(), examAnswerSheetDetail.getId());
    }

    @Override
    public void deleteExamAnswerSheetDetail(Long id) {
        String sql = "DELETE FROM exam_answer_sheet_detail WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
