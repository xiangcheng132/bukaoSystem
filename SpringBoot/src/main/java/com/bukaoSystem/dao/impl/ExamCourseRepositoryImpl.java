package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamCourseRepository;
import com.bukaoSystem.model.ExamCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamCourseRepositoryImpl implements ExamCourseRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(ExamCourse examCourse) {
        StringBuilder sql = new StringBuilder("INSERT INTO exam_course (name, chapter");
        StringBuilder values = new StringBuilder(" VALUES (?, ?");

        List<Object> params = new ArrayList<>();
        params.add(examCourse.getName());
        params.add(examCourse.getchapter());

        if (examCourse.getComment() != null) {
            sql.append(", comment");
            values.append(", ?");
            params.add(examCourse.getComment());
        }

        if (examCourse.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examCourse.getCreateTime());
        }

        sql.append(")");
        values.append(")");

        sql.append(values);
        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public ExamCourse findById(Long id) {
        String sql = "SELECT * FROM exam_course WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToExamCourse);
    }

    @Override
    public List<ExamCourse> findBychapter(Long id) {
        String sql = "SELECT * FROM exam_course WHERE chapter = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, this::mapRowToExamCourse);
    }


    @Override
    public List<ExamCourse> findAll() {
        String sql = "SELECT * FROM exam_course";
        return jdbcTemplate.query(sql, this::mapRowToExamCourse);
    }

    @Override
    public void update(ExamCourse examCourse) {
        StringBuilder sql = new StringBuilder("UPDATE exam_course SET name = ?, chapter = ?");
        List<Object> params = new ArrayList<>();
        params.add(examCourse.getName());
        params.add(examCourse.getchapter());

        if (examCourse.getComment() != null) {
            sql.append(", comment = ?");
            params.add(examCourse.getComment());
        }

        if (examCourse.getCreateTime() != null) {
            sql.append(", createTime = ?");
            params.add(examCourse.getCreateTime());
        }

        sql.append(" WHERE id = ?");
        params.add(examCourse.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM exam_course WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private ExamCourse mapRowToExamCourse(ResultSet rs, int rowNum) throws SQLException {
        ExamCourse examCourse = new ExamCourse();
        examCourse.setId(rs.getLong("id"));
        examCourse.setName(rs.getString("name"));
        examCourse.setchapter(rs.getLong("chapter"));
        examCourse.setComment(rs.getString("comment"));
        examCourse.setCreateTime(rs.getString("createTime"));
        return examCourse;
    }
}
