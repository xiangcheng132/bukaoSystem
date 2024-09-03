package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamCourseClassDAO;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamCourseClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        StringBuilder sql = new StringBuilder("INSERT INTO exam_course_class (id, class_id, course_id");
        StringBuilder values = new StringBuilder(" VALUES (?, ?, ?");
        List<Object> params = new ArrayList<>();
        params.add(courseClass.getId());
        params.add(courseClass.getClassId());
        params.add(courseClass.getCourseId());

        if (courseClass.getCreateTime() != null) {
            sql.append(", createTime");
            values.append(", ?");
            params.add(courseClass.getCreateTime());
        }

        sql.append(")");
        values.append(")");
        sql.append(values);

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public void updateExamCourseClass(ExamCourseClass courseClass) {
        StringBuilder sql = new StringBuilder("UPDATE exam_course_class SET class_id = ?, course_id = ?");
        List<Object> params = new ArrayList<>();
        params.add(courseClass.getClassId());
        params.add(courseClass.getCourseId());

        if (courseClass.getCreateTime() != null) {
            sql.append(", createTime = ?");
            params.add(courseClass.getCreateTime());
        }

        sql.append(" WHERE id = ?");
        params.add(courseClass.getId());

        jdbcTemplate.update(sql.toString(), params.toArray());
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
    @Override
    public List<ExamCourseClass> getExamCourseClassesByCourseId(Long courseId) {
        String sql = "SELECT ecc.*, ec.name as className, c.name as courseName " +
                "FROM exam_course_class ecc " +
                "JOIN exam_class ec ON ecc.classId = ec.id " +
                "JOIN exam_course c ON ecc.courseId = c.id " +
                "WHERE ecc.courseId = ?";

        return jdbcTemplate.query(sql, new Object[]{courseId}, new BeanPropertyRowMapper<>(ExamCourseClass.class));
    }



    @Override
    public List<ExamCourseClass> getExamCourseClassesByClassId(Long classId){
        String sql = "SELECT ecc.*, ec.name as className, c.name as courseName " +
                "FROM exam_course_class ecc " +
                "JOIN exam_class ec ON ecc.classId = ec.id " +
                "JOIN exam_course c ON ecc.courseId = c.id " +
                "WHERE ecc.classId = ?";
        return jdbcTemplate.query(sql, new Object[]{classId}, new BeanPropertyRowMapper<>(ExamCourseClass.class));
    }
}
