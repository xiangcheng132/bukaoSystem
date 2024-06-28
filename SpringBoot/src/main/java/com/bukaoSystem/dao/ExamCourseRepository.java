package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamCourse;
import java.util.List;

public interface ExamCourseRepository {
    void save(ExamCourse examCourse);
    ExamCourse findById(Long id);
    List<ExamCourse> findByTeacherId(Long id);
    List<ExamCourse> findAll();
    void update(ExamCourse examCourse);
    void delete(Long id);
}