package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamResourcesDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamResources;
import com.bukaoSystem.model.ExamResources.Type;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            examResources.setChapterId(rs.getLong("chapterId"));
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
        StringBuilder sql = new StringBuilder("INSERT INTO exam_resources (courseId, chapterId, question, `key`, analysis, score");
        StringBuilder values = new StringBuilder(" VALUES (?, ?, ?, ?, ?, ?");

        List<Object> params = new ArrayList<>();
        params.add(examResources.getCourseId());
        params.add(examResources.getChapterId());
        params.add(examResources.getQuestion());
        params.add(examResources.getKey());
        params.add(examResources.getAnalysis());
        params.add(examResources.getScore());

        if (examResources.getType() != null) {
            sql.append(", type");
            values.append(", ?");
            params.add(examResources.getType().name());
        }

        if (examResources.getOptions() != null) {
            sql.append(", options");
            values.append(", ?");
            String jsonString = examResources.getOptions().toString();
            // 去除头尾引号
            if (jsonString.startsWith("\"") && jsonString.endsWith("\"")) {
                jsonString = jsonString.substring(1, jsonString.length() - 1);
            }

            // 去除反斜杠
            jsonString = jsonString.replace("\\\"", "\"");
            params.add(jsonString);
        }

        if (examResources.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examResources.getCreateTime());
        }

        sql.append(")");
        values.append(")");

        sql.append(values);
        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void update(ExamResources examResources) {
        StringBuilder sql = new StringBuilder("UPDATE exam_resources SET courseId = ?, chapterId = ?, question = ?, `key` = ?, analysis = ?, score = ?");
        List<Object> params = new ArrayList<>();
        params.add(examResources.getCourseId());
        params.add(examResources.getChapterId());
        params.add(examResources.getQuestion());
        params.add(examResources.getKey());
        params.add(examResources.getAnalysis());
        params.add(examResources.getScore());

        if (examResources.getType() != null) {
            sql.append(", type = ?");
            params.add(examResources.getType().name());
        }

        if (examResources.getOptions() != null) {
            sql.append(", options = ?");
            String jsonString = examResources.getOptions().toString();
            // 去除头尾引号
            if (jsonString.startsWith("\"") && jsonString.endsWith("\"")) {
                jsonString = jsonString.substring(1, jsonString.length() - 1);
            }

            // 去除反斜杠
            jsonString = jsonString.replace("\\\"", "\"");
            params.add(jsonString);
        }

        if (examResources.getCreateTime() != null) {
            sql.append(", createTime = ?");
            params.add(examResources.getCreateTime());
        }

        sql.append(" WHERE id = ?");
        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM exam_resources WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除id: " + id + "的信息，该id下有关联信息。");
        }
    }

    @Override
    public ExamResources findById(Long id) {
        String sql = "SELECT * FROM exam_resources WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, rowMapper);
    }
    @Override
    public List<ExamResources> findAll() {
        String sql = "SELECT * FROM exam_resources ORDER BY id";
        return jdbcTemplate.query(sql, rowMapper);
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
