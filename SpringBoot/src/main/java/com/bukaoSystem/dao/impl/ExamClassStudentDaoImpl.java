package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamClassStudentDao;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamClassStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExamClassStudentDaoImpl implements ExamClassStudentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<ExamClassStudent> rowMapper = new BeanPropertyRowMapper<>(ExamClassStudent.class);

    @Override
    public List<ExamClassStudent> getAllExamClassStudents() {
        String sql = "SELECT * FROM exam_class_student";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<ExamClassStudent> getExamClassStudentsById(Long id) {
        String sql = "SELECT * FROM exam_class_student WHERE id = ?";
        return jdbcTemplate.query(sql, rowMapper, id);
    }

    @Override
    public List<ExamClassStudent> getExamClassStudentsByClassId(Long classId) {
        String sql = "SELECT * FROM exam_class_student WHERE classId = ?";
        return jdbcTemplate.query(sql, rowMapper, classId);
    }

    @Override
    public List<ExamClassStudent> getExamClassStudentsByStudentId(Long studentId) {
        String sql = "SELECT * FROM exam_class_student WHERE studentId = ?";
        return jdbcTemplate.query(sql, rowMapper, studentId);
    }

    @Override
    public void saveExamClassStudent(ExamClassStudent examClassStudent) {
        String sql = "INSERT INTO exam_class_student (classId, studentId, createTime) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, examClassStudent.getClassId(), examClassStudent.getStudentId(), examClassStudent.getCreateTime());
    }

    @Override
    public void updateExamClassStudent(ExamClassStudent examClassStudent) {
        String sql = "UPDATE exam_class_student SET classId = ?, studentId = ?, createTime = ? WHERE id = ?";
        jdbcTemplate.update(sql, examClassStudent.getClassId(), examClassStudent.getStudentId(), examClassStudent.getCreateTime(), examClassStudent.getId());
    }

    @Override
    public void deleteExamClassStudent(Long id) {
        String sql = "DELETE FROM exam_class_student WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除id: " + id + "的信息，该id下有关联信息。");
        }
    }
}
