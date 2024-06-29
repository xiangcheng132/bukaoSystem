package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamTeacherCourseDao;
import com.bukaoSystem.model.ExamTeacherCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
        String sql = "INSERT INTO exam_teacher_course (teacherId, courseId, createTime) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, examTeacherCourse.getTeacherId(), examTeacherCourse.getCourseId(), examTeacherCourse.getCreateTime());
    }

    @Override
    public void updateExamTeacherCourse(ExamTeacherCourse examTeacherCourse) {
        String sql = "UPDATE exam_teacher_course SET createTime = ? WHERE teacherId = ? AND courseId = ?";
        jdbcTemplate.update(sql, examTeacherCourse.getCreateTime(), examTeacherCourse.getTeacherId(), examTeacherCourse.getCourseId());
    }

    @Override
    public void deleteExamTeacherCourse(Long teacherId, Long courseId) {
        String sql = "DELETE FROM exam_teacher_course WHERE teacherId = ? AND courseId = ?";
        jdbcTemplate.update(sql, teacherId, courseId);
    }
}
