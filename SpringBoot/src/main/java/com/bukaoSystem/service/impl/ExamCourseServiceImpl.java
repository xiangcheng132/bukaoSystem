package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamCourseRepository;
import com.bukaoSystem.model.ExamCourse;
import com.bukaoSystem.service.ExamCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamCourseServiceImpl implements ExamCourseService {

    @Autowired
    private ExamCourseRepository examCourseRepository;

    @Override
    public void saveExamCourse(ExamCourse examCourse) {
        examCourseRepository.save(examCourse);
    }

    @Override
    public ExamCourse getExamCourseById(Long id) {
        return examCourseRepository.findById(id);
    }

    @Override
    public List<ExamCourse> getExamCourseByTeacherId(Long id) {
        return examCourseRepository.findByTeacherId(id);
    }

    @Override
    public List<ExamCourse> getAllExamCourses() {
        return examCourseRepository.findAll();
    }

    @Override
    public void updateExamCourse(Long id, ExamCourse examCourse) {
        examCourse.setId(id);
        examCourseRepository.update(examCourse);
    }

    @Override
    public void deleteExamCourse(Long id) {
        examCourseRepository.delete(id);
    }
}
