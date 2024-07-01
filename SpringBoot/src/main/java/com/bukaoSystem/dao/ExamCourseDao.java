package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamCourse;
import com.bukaoSystem.model.ExamUser;

import java.util.List;

public interface ExamCourseDao {
    void save(ExamCourse examCourse);
    ExamCourse findById(Long id);
    List<ExamCourse> findAll(ExamUser examUser);
    List<ExamCourse> findByName(String name);
    void update(ExamCourse examCourse);
    void delete(Long id);
}