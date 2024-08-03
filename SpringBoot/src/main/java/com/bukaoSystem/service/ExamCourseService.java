package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamCourse;
import com.bukaoSystem.model.ExamCourseDto;
import com.bukaoSystem.model.ExamUser;

import java.util.List;

public interface ExamCourseService {
    Long saveExamCourse(ExamCourse examCourse);
    ExamCourse getExamCourseById(Long id);

    List<ExamCourse> getAllExamCourses(ExamUser examUser);

    List<ExamCourseDto> getAllExamCourses();

    List<ExamCourse> getExamCoursesByName(String name);
    void updateExamCourse(ExamCourse examCourse);
    void deleteExamCourse(Long id);
}
