package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamExamResourcesDao;
import com.bukaoSystem.model.ExamExamResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExamExamResourcesDaoImpl implements ExamExamResourcesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<ExamExamResources> rowMapper = new BeanPropertyRowMapper<>(ExamExamResources.class);


    @Override
    public List<ExamExamResources> getAllExamExamResources() {
        String sql = "SELECT * FROM exam_exam_resources";
        return jdbcTemplate.query(sql, rowMapper);
    }
    @Override
    public List<ExamExamResources> getExamExamResourcesById(Long id) {
        String sql = "SELECT * FROM exam_exam_resources WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id);
    }
    @Override
    public List<ExamExamResources> getExamExamResourcesByExamId(Long examId) {
        String sql = "SELECT * FROM exam_exam_resources WHERE examId = ?";
        return jdbcTemplate.query(sql, rowMapper, examId);
    }

    @Override
    public List<ExamExamResources> getExamExamResourcesByResourceId(Long resourceId) {
        String sql = "SELECT * FROM exam_exam_resources WHERE resourceId = ?";
        return jdbcTemplate.query(sql, rowMapper, resourceId);
    }

    @Override
    public void saveExamExamResources(ExamExamResources examExamResources) {
        String sql = "INSERT INTO exam_exam_resources (examId, resourceId, createTime) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, examExamResources.getExamId(), examExamResources.getResourceId(), examExamResources.getCreateTime());
    }

    @Override
    public void updateExamExamResources(ExamExamResources examExamResources) {
        String sql = "UPDATE exam_exam_resources SET createTime = ? WHERE examId = ? AND resourceId = ?";
        jdbcTemplate.update(sql, examExamResources.getCreateTime(), examExamResources.getExamId(), examExamResources.getResourceId());
    }

    @Override
    public void deleteExamExamResources(Long examId, Long resourceId) {
        String sql = "DELETE FROM exam_exam_resources WHERE examId = ? AND resourceId = ?";
        jdbcTemplate.update(sql, examId, resourceId);
    }
}
