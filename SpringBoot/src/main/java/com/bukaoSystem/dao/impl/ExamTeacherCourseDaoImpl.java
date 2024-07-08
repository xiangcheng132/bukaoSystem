package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamTeacherCourseDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamTeacherCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExamTeacherCourseDaoImpl implements ExamTeacherCourseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<ExamTeacherCourse> rowMapper = new BeanPropertyRowMapper<>(ExamTeacherCourse.class);

    @Override
    public List<ExamTeacherCourse> getAllExamTeacherCourses() {
        String sql = "SELECT * FROM exam_teacher_course";
        return jdbcTemplate.query(sql, rowMapper);
    }
    @Override
    public List<ExamTeacherCourse> getExamTeacherCoursesById(Long id) {
        String sql = "SELECT * FROM exam_teacher_course WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id);
    }

    @Override
    public List<ExamTeacherCourse> getExamTeacherCoursesByTeacherId(Long teacherId) {
        String sql = "SELECT * FROM exam_teacher_course WHERE teacherId = ?";
        return jdbcTemplate.query(sql, rowMapper, teacherId);
    }

    @Override
    public List<ExamTeacherCourse> getExamTeacherCoursesByCourseId(Long courseId) {
        String sql = "SELECT * FROM exam_teacher_course WHERE courseId = ?";
        return jdbcTemplate.query(sql, rowMapper, courseId);
    }

    @Override
    public void saveExamTeacherCourse(ExamTeacherCourse examTeacherCourse) {
        StringBuilder sql = new StringBuilder("INSERT INTO exam_teacher_course (teacherId, courseId");
        StringBuilder values = new StringBuilder(" VALUES (?, ?");
        List<Object> params = new ArrayList<>();
        params.add(examTeacherCourse.getTeacherId());
        params.add(examTeacherCourse.getCourseId());

        if (examTeacherCourse.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(examTeacherCourse.getCreateTime());
        }

        sql.append(")");
        values.append(")");
        sql.append(values);

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public void updateExamTeacherCourse(ExamTeacherCourse examTeacherCourse) {
        StringBuilder sql = new StringBuilder("UPDATE exam_teacher_course SET ");
        List<Object> params = new ArrayList<>();

        if (examTeacherCourse.getCreateTime() != null) {
            sql.append("createTime = ?");
            params.add(examTeacherCourse.getCreateTime());
        }

        sql.append(" WHERE teacherId = ? AND courseId = ?");
        params.add(examTeacherCourse.getTeacherId());
        params.add(examTeacherCourse.getCourseId());

        jdbcTemplate.update(sql.toString(), params.toArray());
    }


    @Override
    public void deleteExamTeacherCourse(Long teacherId, Long courseId) {
        String sql = "DELETE FROM exam_teacher_course WHERE teacherId = ? AND courseId = ?";
        try {
            jdbcTemplate.update(sql, teacherId, courseId);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除teacherId: " + teacherId +  "courseId:+ " + courseId + "的信息，该id下有关联信息。");
        }
    }
}
