package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamClassTeacherDAO;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamClassTeacher;
import com.bukaoSystem.model.ExamClassTeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        String sql = "SELECT * FROM exam_class_teacher WHERE classId = ?";
        return jdbcTemplate.query(sql, new Object[]{classId}, new BeanPropertyRowMapper<>(ExamClassTeacher.class));
    }
    @Override
    public List<ExamClassTeacherDto> getExamClassTeachersnameByClassId(Long classId) {
        String sql = "SELECT ect.*, eu.username FROM exam_class_teacher ect " +
                "LEFT JOIN exam_user eu ON ect.teacherId = eu.id " +
                "WHERE ect.classId = ?";
        return jdbcTemplate.query(sql, new Object[]{classId}, new BeanPropertyRowMapper<>(ExamClassTeacherDto.class));
    }
    @Override
    public void saveExamClassTeacher(ExamClassTeacher examClassTeacher) {
        String sql = "INSERT INTO exam_class_teacher (classid, teacherid) VALUES (?, ?)";
        jdbcTemplate.update(sql, examClassTeacher.getClassId(), examClassTeacher.getTeacherId());
    }

    @Override
    public void updateExamClassTeacher(ExamClassTeacher examClassTeacher) {
        String sql = "UPDATE exam_class_teacher SET classid = ?, teacherid = ? WHERE id = ?";
        jdbcTemplate.update(sql, examClassTeacher.getClassId(), examClassTeacher.getTeacherId(), examClassTeacher.getId());
    }

    @Override
    public void deleteExamClassTeacher(Long id) {
        String sql = "DELETE FROM exam_class_teacher WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("无法删除id: " + id + "的信息，该id下有关联信息。");
        }
    }
}
