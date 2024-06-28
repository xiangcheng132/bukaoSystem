package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamCourse;
import java.util.List;

public interface ExamCourseService {
    void saveExamCourse(ExamCourse examCourse);
    ExamCourse getExamCourseById(Long id);

    List<ExamCourse> getExamCourseByTeacherId(Long id);

    List<ExamCourse> getAllExamCourses();
    void updateExamCourse(Long id, ExamCourse examCourse);
    void deleteExamCourse(Long id);
}
