package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamClassRepository;
import com.bukaoSystem.model.ExamClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamClassRepositoryImpl implements ExamClassRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(ExamClass examClass) {
        StringBuilder sql = new StringBuilder("INSERT INTO exam_class (name");
        StringBuilder values = new StringBuilder(" VALUES (?");
        List<Object> params = new ArrayList<>();
        params.add(examClass.getName());

        if (examClass.getComment() != null) {
            sql.append(", comment");
            values.append(", ?");
            params.add(examClass.getComment());
        }

        if (examClass.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examClass.getCreateTime());
        }

        sql.append(")");
        values.append(")");

        sql.append(values);

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public ExamClass findById(Long id) {
        String sql = "SELECT * FROM exam_class WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToExamClass);
    }

    @Override
    public List<ExamClass> findAll() {
        String sql = "SELECT * FROM exam_class";
        return jdbcTemplate.query(sql, this::mapRowToExamClass);
    }

    @Override
    public void update(ExamClass examClass) {
        StringBuilder sql = new StringBuilder("UPDATE exam_class SET name = ?");
        List<Object> params = new ArrayList<>();
        params.add(examClass.getName());

        if (examClass.getComment() != null) {
            sql.append(", comment = ?");
            params.add(examClass.getComment());
        }

        if (examClass.getCreateTime() != null) {
            sql.append(", createTime = ?");
            params.add(examClass.getCreateTime());
        }

        sql.append(" WHERE id = ?");
        params.add(examClass.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM exam_class WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private ExamClass mapRowToExamClass(ResultSet rs, int rowNum) throws SQLException {
        return new ExamClass(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("comment"),
                rs.getString("createTime")
        );
    }
}