package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamClassTeacherDAO;
import com.bukaoSystem.model.ExamClassTeacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExamClassTeacherDAOImpl implements ExamClassTeacherDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ExamClassTeacher> getAllExamClassTeachers() {
        String sql = "SELECT * FROM exam_class_teacher";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ExamClassTeacher.class));
    }

    @Override
    public ExamClassTeacher getExamClassTeacherById(Long id) {
        String sql = "SELECT * FROM exam_class_teacher WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(ExamClassTeacher.class));
    }

    @Override
    public List<ExamClassTeacher> getExamClassTeachersByClassId(Long classId) {
        String sql = "SELECT * FROM exam_class_teacher WHERE class_id = ?";
        return jdbcTemplate.query(sql, new Object[]{classId}, new BeanPropertyRowMapper<>(ExamClassTeacher.class));
    }

    @Override
    public void saveExamClassTeacher(ExamClassTeacher examClassTeacher) {
        String sql = "INSERT INTO exam_class_teacher (class_id, teacher_id, create_time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, examClassTeacher.getClassId(), examClassTeacher.getTeacherId(), examClassTeacher.getCreateTime());
    }

    @Override
    public void updateExamClassTeacher(ExamClassTeacher examClassTeacher) {
        String sql = "UPDATE exam_class_teacher SET class_id = ?, teacher_id = ?, create_time = ? WHERE id = ?";
        jdbcTemplate.update(sql, examClassTeacher.getClassId(), examClassTeacher.getTeacherId(), examClassTeacher.getCreateTime(), examClassTeacher.getId());
    }

    @Override
    public void deleteExamClassTeacher(Long id) {
        String sql = "DELETE FROM exam_class_teacher WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
