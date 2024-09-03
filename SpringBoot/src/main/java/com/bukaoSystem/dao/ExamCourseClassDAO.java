package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamCourseClass;

import java.util.List;

public interface ExamCourseClassDAO {
    List<ExamCourseClass> getAllExamCourseClasses();
    ExamCourseClass getExamCourseClassById(Long id);
    void saveExamCourseClass(ExamCourseClass courseClass);
    void updateExamCourseClass(ExamCourseClass courseClass);
    void deleteExamCourseClass(Long id);
    List<ExamCourseClass> getExamCourseClassesByCourseId(Long courseId);
    List<ExamCourseClass> getExamCourseClassesByClassId(Long classId);
}
