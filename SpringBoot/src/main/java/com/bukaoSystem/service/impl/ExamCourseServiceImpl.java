package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamCourseDao;
import com.bukaoSystem.model.ExamCourse;
import com.bukaoSystem.model.ExamCourseDto;
import com.bukaoSystem.model.ExamUser;
import com.bukaoSystem.service.ExamCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamCourseServiceImpl implements ExamCourseService {

    @Autowired
    private ExamCourseDao examCourseDao;


    @Override
    public Long saveExamCourse(ExamCourse examCourse) {
        return examCourseDao.save(examCourse);
    }

    @Override
    public ExamCourse getExamCourseById(Long id) {
        return examCourseDao.findById(id);
    }


    @Override
    public List<ExamCourse> getAllExamCourses(ExamUser examUser) {
        return examCourseDao.findAll(examUser);
    }
    @Override
    public List<ExamCourseDto> getAllExamCourses() {
        return examCourseDao.findAll();
    }

    @Override
    public List<ExamCourse> getExamCoursesByName(String name) {
        return examCourseDao.findByName(name);
    }

    @Override
    public void updateExamCourse(ExamCourse examCourse) {
        examCourseDao.update(examCourse);
    }

    @Override
    public void deleteExamCourse(Long id) {
        examCourseDao.delete(id);
    }
}
