package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamTeacherCourseDao;
import com.bukaoSystem.model.ExamTeacherCourse;
import com.bukaoSystem.service.ExamTeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamTeacherCourseServiceImpl implements ExamTeacherCourseService {

    @Autowired
    private ExamTeacherCourseDao examTeacherCourseDao;

    @Override
    public List<ExamTeacherCourse> getAllExamTeacherCourses() {
        return examTeacherCourseDao.getAllExamTeacherCourses();
    }

    @Override
    public List<ExamTeacherCourse> getExamTeacherCoursesByTeacherId(Long teacherId) {
        return examTeacherCourseDao.getExamTeacherCoursesByTeacherId(teacherId);
    }

    @Override
    public List<ExamTeacherCourse> getExamTeacherCoursesByCourseId(Long courseId) {
        return examTeacherCourseDao.getExamTeacherCoursesByCourseId(courseId);
    }

    @Override
    public void saveExamTeacherCourse(ExamTeacherCourse examTeacherCourse) {
        examTeacherCourseDao.saveExamTeacherCourse(examTeacherCourse);
    }

    @Override
    public void updateExamTeacherCourse(ExamTeacherCourse examTeacherCourse) {
        examTeacherCourseDao.updateExamTeacherCourse(examTeacherCourse);
    }

    @Override
    public void deleteExamTeacherCourse(Long teacherId, Long courseId) {
        examTeacherCourseDao.deleteExamTeacherCourse(teacherId, courseId);
    }
}
