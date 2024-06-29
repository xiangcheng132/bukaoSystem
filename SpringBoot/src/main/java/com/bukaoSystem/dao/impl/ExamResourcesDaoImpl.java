package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamResourcesDao;
import com.bukaoSystem.model.ExamResources;
import com.bukaoSystem.model.ExamResources.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ExamResourcesDaoImpl implements ExamResourcesDao {

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
            examResources.setQuestion(rs.getString("question"));
            examResources.setType(Type.valueOf(rs.getString("type")));
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

    @Override
    public void save(ExamResources examResources) {
        String sql = "INSERT INTO exam_resources (courseId, question, type, options, key, analysis, score, createTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                examResources.getCourseId(),
                examResources.getQuestion(),
                examResources.getType() != null ? examResources.getType().name() : null,
                examResources.getOptions() != null ? examResources.getOptions().toString() : null,
                examResources.getKey(),
                examResources.getAnalysis(),
                examResources.getScore(),
                examResources.getCreateTime());
    }

    @Override
    public void update(ExamResources examResources) {
        String sql = "UPDATE exam_resources SET courseId = ?, question = ?, type = ?, options = ?, key = ?, analysis = ?, score = ?, createTime = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                examResources.getCourseId(),
                examResources.getQuestion(),
                examResources.getType() != null ? examResources.getType().name() : null,
                examResources.getOptions() != null ? examResources.getOptions().toString() : null,
                examResources.getKey(),
                examResources.getAnalysis(),
                examResources.getScore(),
                examResources.getCreateTime(),
                examResources.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM exam_resources WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public ExamResources findById(Long id) {
        String sql = "SELECT * FROM exam_resources WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
    }

    @Override
    public List<ExamResources> findByCourseId(Long courseId, String sort) {
        String sql = "SELECT * FROM exam_resources WHERE courseId = ? ORDER BY id " + sort;
        return jdbcTemplate.query(sql, new Object[]{courseId}, rowMapper);
    }

    @Override
    public List<ExamResources> findByChapterId(Long chapterId, String sort) {
        String sql = "SELECT * FROM exam_resources WHERE chapterId = ? ORDER BY courseId " + sort;
        return jdbcTemplate.query(sql, new Object[]{chapterId}, rowMapper);
    }
}
