package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamExamResourcesDao;
import com.bukaoSystem.model.ExamExamResources;
import com.bukaoSystem.service.ExamExamResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamExamResourcesServiceImpl implements ExamExamResourcesService {

    @Autowired
    private ExamExamResourcesDao examExamResourcesDao;

    @Override
    public List<ExamExamResources> getAllExamExamResources() {
        return examExamResourcesDao.getAllExamExamResources();
    }

    @Override
    public List<ExamExamResources> getExamExamResourcesByExamId(Long examId) {
        return examExamResourcesDao.getExamExamResourcesByExamId(examId);
    }

    @Override
    public List<ExamExamResources> getExamExamResourcesById(Long Id) {
        return examExamResourcesDao.getExamExamResourcesById(Id);
    }
    @Override
    public List<ExamExamResources> getExamExamResourcesByResourceId(Long resourceId) {
        return examExamResourcesDao.getExamExamResourcesByResourceId(resourceId);
    }

    @Override
    public void saveExamExamResources(ExamExamResources examExamResources) {
        examExamResourcesDao.saveExamExamResources(examExamResources);
    }

    @Override
    public void updateExamExamResources(ExamExamResources examExamResources) {
        examExamResourcesDao.updateExamExamResources(examExamResources);
    }

    @Override
    public void deleteExamExamResources(Long examId, Long resourceId) {
        examExamResourcesDao.deleteExamExamResources(examId, resourceId);
    }
}
