package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamExamClassDao;
import com.bukaoSystem.model.ExamExamClass;
import com.bukaoSystem.service.ExamExamClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExamExamClassServiceImpl implements ExamExamClassService {

    @Autowired
    private ExamExamClassDao examExamClassDao;

    @Override
    public List<ExamExamClass> getAllExamExamClasses() {
        return examExamClassDao.getAllExamExamClasses();
    }

    @Override
    public List<ExamExamClass> getExamExamClassesById(Long id) {
        return examExamClassDao.getExamExamClassesById(id);
    }
    @Override
    public List<ExamExamClass> getExamExamClassesByExamId(Long examId) {
        return examExamClassDao.getExamExamClassesByExamId(examId);
    }

    @Override
    public List<Map<String, Object>> getExamExamClassesByClassId(Long classId) {
        return examExamClassDao.getExamExamClassesByClassId(classId);
    }

    @Override
    public void saveExamExamClass(ExamExamClass examExamClass) {
        examExamClassDao.saveExamExamClass(examExamClass);
    }

    @Override
    public void updateExamExamClass(ExamExamClass examExamClass) {
        examExamClassDao.updateExamExamClass(examExamClass);
    }

    @Override
    public void deleteExamExamClass(Long examId, Long classId) {
        examExamClassDao.deleteExamExamClass(examId, classId);
    }
}
