package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamExamClassDao;
import com.bukaoSystem.model.ExamExamClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExamExamClassDaoImpl implements ExamExamClassDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<ExamExamClass> rowMapper = new BeanPropertyRowMapper<>(ExamExamClass.class);

    @Override
    public List<ExamExamClass> getAllExamExamClasses() {
        String sql = "SELECT * FROM exam_exam_class";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<ExamExamClass> getExamExamClassesByExamId(Long examId) {
        String sql = "SELECT * FROM exam_exam_class WHERE examId = ?";
        return jdbcTemplate.query(sql, rowMapper, examId);
    }

    @Override
    public List<ExamExamClass> getExamExamClassesByClassId(Long classId) {
        String sql = "SELECT * FROM exam_exam_class WHERE classId = ?";
        return jdbcTemplate.query(sql, rowMapper, classId);
    }

    @Override
    public void saveExamExamClass(ExamExamClass examExamClass) {
        String sql = "INSERT INTO exam_exam_class (examId, classId, createTime) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, examExamClass.getExamId(), examExamClass.getClassId(), examExamClass.getCreateTime());
    }

    @Override
    public void updateExamExamClass(ExamExamClass examExamClass) {
        String sql = "UPDATE exam_exam_class SET createTime = ? WHERE examId = ? AND classId = ?";
        jdbcTemplate.update(sql, examExamClass.getCreateTime(), examExamClass.getExamId(), examExamClass.getClassId());
    }

    @Override
    public void deleteExamExamClass(Long examId, Long classId) {
        String sql = "DELETE FROM exam_exam_class WHERE examId = ? AND classId = ?";
        jdbcTemplate.update(sql, examId, classId);
    }
}
