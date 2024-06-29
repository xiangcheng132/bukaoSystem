package com.bukaoSystem.dao.impl;

import com.bukaoSystem.dao.ExamCourseChapterDao;
import com.bukaoSystem.model.ExamCourseChapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ExamCourseChapterDaoImpl implements ExamCourseChapterDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class ExamCourseChapterMapper implements RowMapper<ExamCourseChapter> {
        public ExamCourseChapter mapRow(ResultSet rs, int rowNum) throws SQLException {
            ExamCourseChapter examCourseChapter = new ExamCourseChapter();
            examCourseChapter.setId(rs.getLong("id"));
            examCourseChapter.setCourseId(rs.getLong("courseId"));
            examCourseChapter.setName(rs.getString("name"));
            return examCourseChapter;
        }
    }

    @Override
    public void saveExamCourseChapter(ExamCourseChapter examCourseChapter) {
        String sql = "INSERT INTO exam_course_chapter (courseId, name) VALUES (?, ?)";
        jdbcTemplate.update(sql, examCourseChapter.getCourseId(), examCourseChapter.getName());
    }

    @Override
    public void updateExamCourseChapter(ExamCourseChapter examCourseChapter) {
        String sql = "UPDATE exam_course_chapter SET courseId = ?, name = ? WHERE id = ?";
        jdbcTemplate.update(sql, examCourseChapter.getCourseId(), examCourseChapter.getName(), examCourseChapter.getId());
    }

    @Override
    public void deleteExamCourseChapter(Long id) {
        String sql = "DELETE FROM exam_course_chapter WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public ExamCourseChapter getExamCourseChapterById(Long id) {
        String sql = "SELECT * FROM exam_course_chapter WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ExamCourseChapterMapper());
    }

    @Override
    public List<ExamCourseChapter> getAllExamCourseChapters() {
        String sql = "SELECT * FROM exam_course_chapter";
        return jdbcTemplate.query(sql, new ExamCourseChapterMapper());
    }
}