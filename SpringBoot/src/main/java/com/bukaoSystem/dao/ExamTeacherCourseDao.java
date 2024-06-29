package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamTeacherCourse;
import java.util.List;

public interface ExamTeacherCourseDao {
    List<ExamTeacherCourse> getAllExamTeacherCourses();
    List<ExamTeacherCourse> getExamTeacherCoursesByTeacherId(Long teacherId);
    List<ExamTeacherCourse> getExamTeacherCoursesByCourseId(Long courseId);
    void saveExamTeacherCourse(ExamTeacherCourse examTeacherCourse);
    void updateExamTeacherCourse(ExamTeacherCourse examTeacherCourse);
    void deleteExamTeacherCourse(Long teacherId, Long courseId);
}
