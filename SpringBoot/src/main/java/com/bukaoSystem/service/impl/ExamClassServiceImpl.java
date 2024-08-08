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
    public void saveExamClass(ExamClass examClass) {
        examClassDao.save(examClass);
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
    public void updateExamClass(ExamClass examClass) {
        examClassDao.update(examClass);
    }

    @Override
    public void deleteExamClass(Long id) {
        examClassDao.delete(id);
    }

    @Override
    public List<ExamClassDto> getAllExamClassesAndteacher() {
        return null;
    }
}