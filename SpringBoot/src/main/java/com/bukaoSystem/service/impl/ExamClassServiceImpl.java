package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamClassRepository;
import com.bukaoSystem.model.ExamClass;
import com.bukaoSystem.service.ExamClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamClassServiceImpl implements ExamClassService {

    @Autowired
    private ExamClassRepository examClassRepository;

    @Override
    public void saveExamClass(ExamClass examClass) {
        examClassRepository.save(examClass);
    }

    @Override
    public ExamClass getExamClassById(Long id) {
        return examClassRepository.findById(id);
    }

    @Override
    public List<ExamClass> getAllExamClasses() {
        return examClassRepository.findAll();
    }

    @Override
    public void updateExamClass(ExamClass examClass) {
        examClassRepository.update(examClass);
    }

    @Override
    public void deleteExamClass(Long id) {
        examClassRepository.delete(id);
    }
}