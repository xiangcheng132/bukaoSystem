package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamCourseClassDAO;
import com.bukaoSystem.model.ExamCourseClass;
import com.bukaoSystem.service.ExamCourseClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamCourseClassServiceImpl implements ExamCourseClassService {

    @Autowired
    private ExamCourseClassDAO examCourseClassDAO;

    @Override
    public List<ExamCourseClass> getAllExamCourseClasses() {
        return examCourseClassDAO.getAllExamCourseClasses();
    }

    @Override
    public ExamCourseClass getExamCourseClassById(Long id) {
        return examCourseClassDAO.getExamCourseClassById(id);
    }

    @Override
    public void saveExamCourseClass(ExamCourseClass courseClass) {
        examCourseClassDAO.saveExamCourseClass(courseClass);
    }

    @Override
    public void updateExamCourseClass(ExamCourseClass courseClass) {
        examCourseClassDAO.updateExamCourseClass(courseClass);
    }

    @Override
    public void deleteExamCourseClass(Long id) {
        examCourseClassDAO.deleteExamCourseClass(id);
    }
}
