package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamCourseClassDAO;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamCourseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExamCourseClassDAOImpl implements ExamCourseClassDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ExamCourseClass> getAllExamCourseClasses() {
        String sql = "SELECT * FROM exam_course_class";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ExamCourseClass.class));
    }

    @Override
    public ExamCourseClass getExamCourseClassById(Long id) {
        String sql = "SELECT * FROM exam_course_class WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(ExamCourseClass.class));
    }

    @Override
    public void saveExamCourseClass(ExamCourseClass courseClass) {
        String sql = "INSERT INTO exam_course_class (id, class_id, course_id, create_time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, courseClass.getId(), courseClass.getClassId(), courseClass.getCourseId(), courseClass.getCreateTime());
    }

    @Override
    public void updateExamCourseClass(ExamCourseClass courseClass) {
        String sql = "UPDATE exam_course_class SET class_id = ?, course_id = ?, create_time = ? WHERE id = ?";
        jdbcTemplate.update(sql, courseClass.getClassId(), courseClass.getCourseId(), courseClass.getCreateTime(), courseClass.getId());
    }

    @Override
    public void deleteExamCourseClass(Long id) {
        String sql = "DELETE FROM exam_course_class WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除id: " + id + "的信息，该id下有关联信息。");
        }
    }
}
