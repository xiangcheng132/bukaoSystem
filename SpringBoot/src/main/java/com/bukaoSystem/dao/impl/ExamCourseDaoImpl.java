package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamCourseDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamCourse;
import com.bukaoSystem.model.ExamCourseDto;
import com.bukaoSystem.model.ExamUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamCourseDaoImpl implements ExamCourseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Long save(ExamCourse examCourse) {
        StringBuilder sql = new StringBuilder("INSERT INTO exam_course (name");
        StringBuilder values = new StringBuilder(" VALUES (?");

        List<Object> params = new ArrayList<>();
        params.add(examCourse.getName());

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

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }


    @Override
    public ExamCourse findById(Long id) {
        String sql = "SELECT * FROM exam_course WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, this::mapRowToExamCourse);
    }


    @Override
    public List<ExamCourse> findAll(ExamUser examUser) {
        String sql = "SELECT ec.* FROM exam_course ec " +
                "JOIN exam_teacher_course etc ON ec.id = etc.courseId " +
                "WHERE etc.teacherId = ?";
        return jdbcTemplate.query(sql, new Object[]{examUser.getId()}, this::mapRowToExamCourse);
    }
    @Override
    public List<ExamCourseDto> findAll() {
        String sql = "SELECT ec.*, eu.username,eu.id as tid FROM exam_course ec " +
                "LEFT JOIN exam_teacher_course etc ON ec.id = etc.courseId " +
                "LEFT JOIN exam_user eu ON etc.teacherId = eu.id";
        return jdbcTemplate.query(sql,this::mapRowToExamCourseDto);
    }

    @Override
    public List<ExamCourse> findByName(String name) {
        String sql = "SELECT * FROM exam_course where name = ?";
        return jdbcTemplate.query(sql, new Object[]{name},this::mapRowToExamCourse);
    }

    @Override
    public void update(ExamCourse examCourse) {
        StringBuilder sql = new StringBuilder("UPDATE exam_course SET name = ?");
        List<Object> params = new ArrayList<>();
        params.add(examCourse.getName());

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
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除id: " + id + "的信息，该id下有关联信息。");
        }
    }

    private ExamCourse mapRowToExamCourse(ResultSet rs, int rowNum) throws SQLException {
        ExamCourse examCourse = new ExamCourse();
        examCourse.setId(rs.getLong("id"));
        examCourse.setName(rs.getString("name"));
        examCourse.setComment(rs.getString("comment"));
        examCourse.setCreateTime(rs.getString("createTime"));
        return examCourse;
    }
    private ExamCourseDto mapRowToExamCourseDto(ResultSet rs, int rowNum) throws SQLException {
        ExamCourseDto examCourseDto = new ExamCourseDto();
        examCourseDto.setId(rs.getLong("id"));
        examCourseDto.setName(rs.getString("name"));
        examCourseDto.setComment(rs.getString("comment"));
        examCourseDto.setCreateTime(rs.getString("createTime"));
        examCourseDto.setUsername(rs.getString("username"));
        examCourseDto.setTid(rs.getString("tid"));
        return examCourseDto;
    }
}
