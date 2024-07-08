package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamExamResourcesDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamExamResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        StringBuilder sql = new StringBuilder("INSERT INTO exam_exam_resources (examId, resourceId");
        StringBuilder values = new StringBuilder(" VALUES (?, ?");
        List<Object> params = new ArrayList<>();
        params.add(examExamResources.getExamId());
        params.add(examExamResources.getResourceId());

        if (examExamResources.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examExamResources.getCreateTime());
        }

        sql.append(")");
        values.append(")");
        sql.append(values);

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public void updateExamExamResources(ExamExamResources examExamResources) {
        StringBuilder sql = new StringBuilder("UPDATE exam_exam_resources SET ");
        List<Object> params = new ArrayList<>();

        if (examExamResources.getCreateTime() != null) {
            sql.append("createTime = ?");
            params.add(examExamResources.getCreateTime());
        }

        sql.append(" WHERE examId = ? AND resourceId = ?");
        params.add(examExamResources.getExamId());
        params.add(examExamResources.getResourceId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void deleteExamExamResources(Long examId, Long resourceId) {
        String sql = "DELETE FROM exam_exam_resources WHERE examId = ? AND resourceId = ?";
        jdbcTemplate.update(sql, examId, resourceId);

        try {
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除examId: " + examId + "resourceId: " + resourceId + "的信息，该id下有关联信息。");
        }
    }
}
