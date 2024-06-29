package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamExamDao;
import com.bukaoSystem.model.ExamExam;
import com.bukaoSystem.service.ExamExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamExamServiceImpl implements ExamExamService {

    @Autowired
    private ExamExamDao examExamDao;

    @Override
    public List<ExamExam> getAllExamExams() {
        return examExamDao.findAll();
    }

    @Override
    public ExamExam getExamExamById(Long id) {
        return examExamDao.findById(id);
    }

    @Override
    @Transactional
    public void saveExamExam(ExamExam examExam) {
        examExamDao.save(examExam);
    }

    @Override
    @Transactional
    public void updateExamExam(ExamExam examExam) {
        examExamDao.update(examExam);
    }

    @Override
    @Transactional
    public void deleteExamExam(Long id) {
        examExamDao.delete(id);
    }
}
