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
import java.util.Map;

@Repository
public class ExamExamResourcesDaoImpl implements ExamExamResourcesDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<ExamExamResources> rowMapper = new BeanPropertyRowMapper<>(ExamExamResources.class);

    //得到试卷详情
    @Override
    public List<ExamExamResources> getAllExamExamResources() {
        String sql = "SELECT * FROM exam_exam_resources";
        return jdbcTemplate.query(sql, rowMapper);
    }
    //根据id得到试卷详情
    @Override
    public List<ExamExamResources> getExamExamResourcesById(Long id) {
        String sql = "SELECT * FROM exam_exam_resources WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id);
    }
    //根据试卷id得到试卷资源id，课程id详情
    @Override
    public List<ExamExamResources> getExamExamResourcesByExamId(Long examId) {
        String sql = "SELECT * FROM exam_exam_resources WHERE examId = ?";
        return jdbcTemplate.query(sql, rowMapper, examId);
    }
    @Override
    public List<Map<String, Object>> getExamResourcesByExamId(Long examId) {
        String sql = "SELECT eer.*, er.* " +
                "FROM exam_exam_resources eer " +
                "JOIN exam_resources er ON eer.resourceId = er.id " +
                "WHERE eer.examId = ?";

        return jdbcTemplate.queryForList(sql, examId);
    }
    //根据资源id得到哪些试卷有该资源（试卷id，课程id）
    @Override
    public List<ExamExamResources> getExamExamResourcesByResourceId(Long resourceId) {
        String sql = "SELECT * FROM exam_exam_resources WHERE resourceId = ?";
        return jdbcTemplate.query(sql, rowMapper, resourceId);
    }
    //根据试卷id得到试卷详情（资源id，课程id）
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
    //更新
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
    //删除


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
