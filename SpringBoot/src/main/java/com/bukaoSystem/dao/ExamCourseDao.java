package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamCourse;
import java.util.List;

public interface ExamCourseDao {
    void save(ExamCourse examCourse);
    ExamCourse findById(Long id);
    List<ExamCourse> findAll();
    List<ExamCourse> findByName(String name);
    void update(ExamCourse examCourse);
    void delete(Long id);
}