package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamCourseClass;

import java.util.List;

public interface ExamCourseClassService {
    List<ExamCourseClass> getAllExamCourseClasses();
    ExamCourseClass getExamCourseClassById(Long id);
    void saveExamCourseClass(ExamCourseClass courseClass);
    void updateExamCourseClass(ExamCourseClass courseClass);
    void deleteExamCourseClass(Long id);
}
