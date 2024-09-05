package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamClassDao;
import com.bukaoSystem.model.ExamClass;
import com.bukaoSystem.model.ExamClassDto;
import com.bukaoSystem.service.ExamClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamClassServiceImpl implements ExamClassService {

    @Autowired
    private ExamClassDao examClassDao;

    @Override
    public Long saveExamClass(ExamClass examClass) {
        return examClassDao.save(examClass);
    }

    @Override
    public ExamClass getExamClassById(Long id) {
        return examClassDao.findById(id);
    }

    @Override
    public List<ExamClass> getAllExamClasses() {
        return examClassDao.findAll();
    }
    @Override
    public List<ExamClassDto> getAllwithteacher() {
        return examClassDao.findAllwithteacher();
    }
    @Override
    public List<ExamClassDto> getAllwithTeacherById(Long classId) {
        return examClassDao.findAllwithTeacherById(classId);
    }
    @Override
    public void updateExamClass(ExamClass examClass) {
        examClassDao.update(examClass);
    }

    @Override
    public void deleteExamClass(Long id) {
        examClassDao.delete(id);
    }
}