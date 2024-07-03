package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamAnswerSheetDetailDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamAnswerSheetDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        StringBuilder sql = new StringBuilder("INSERT INTO exam_answer_sheet_detail (answerId, resourceId, userKey");
        StringBuilder values = new StringBuilder(" VALUES (?, ?, ?");

        List<Object> params = new ArrayList<>();
        params.add(examAnswerSheetDetail.getAnswerId());
        params.add(examAnswerSheetDetail.getResourceId());
        params.add(examAnswerSheetDetail.getUserKey());

        if (examAnswerSheetDetail.getIsTrue() != null) {
            sql.append(", isTrue");
            values.append(", ?");
            params.add(examAnswerSheetDetail.getIsTrue());
        }

        if (examAnswerSheetDetail.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examAnswerSheetDetail.getCreateTime());
        }

        sql.append(")");
        values.append(")");

        sql.append(values);
        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void updateExamAnswerSheetDetail(ExamAnswerSheetDetail examAnswerSheetDetail) {
        StringBuilder sql = new StringBuilder("UPDATE exam_answer_sheet_detail SET answerId = ?, resourceId = ?, userKey = ?");
        List<Object> params = new ArrayList<>();
        params.add(examAnswerSheetDetail.getAnswerId());
        params.add(examAnswerSheetDetail.getResourceId());
        params.add(examAnswerSheetDetail.getUserKey());

        if (examAnswerSheetDetail.getIsTrue() != null) {
            sql.append(", isTrue = ?");
            params.add(examAnswerSheetDetail.getIsTrue());
        }

        if (examAnswerSheetDetail.getCreateTime() != null) {
            sql.append(", createTime = ?");
            params.add(examAnswerSheetDetail.getCreateTime());
        }

        sql.append(" WHERE id = ?");
        params.add(examAnswerSheetDetail.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void deleteExamAnswerSheetDetail(Long id) {
        String sql = "DELETE FROM exam_answer_sheet_detail WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除id: " + id + "的信息，该id下有关联信息。");
        }
    }
}
