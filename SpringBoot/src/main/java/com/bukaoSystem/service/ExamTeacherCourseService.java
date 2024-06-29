package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamTeacherCourse;

import java.util.List;

public interface ExamTeacherCourseService {
    List<ExamTeacherCourse> getAllExamTeacherCourses();
    List<ExamTeacherCourse> getExamTeacherCoursesByTeacherId(Long teacherId);
    List<ExamTeacherCourse> getExamTeacherCoursesById(Long id);

    List<ExamTeacherCourse> getExamTeacherCoursesByCourseId(Long courseId);
    void saveExamTeacherCourse(ExamTeacherCourse examTeacherCourse);
    void updateExamTeacherCourse(ExamTeacherCourse examTeacherCourse);
    void deleteExamTeacherCourse(Long teacherId, Long courseId);
}
