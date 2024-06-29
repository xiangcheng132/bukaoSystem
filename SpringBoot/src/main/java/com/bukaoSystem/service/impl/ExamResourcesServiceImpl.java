package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamResourcesDao;
import com.bukaoSystem.model.ExamResources;
import com.bukaoSystem.service.ExamResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamResourcesServiceImpl implements ExamResourcesService {

    @Autowired
    private ExamResourcesDao examResourcesDao;

    @Override
    public void saveExamResources(ExamResources examResources) {
        examResourcesDao.save(examResources);
    }

    @Override
    public void updateExamResources(ExamResources examResources) {
        examResourcesDao.update(examResources);
    }

    @Override
    public void deleteExamResources(Long id) {
        examResourcesDao.delete(id);
    }

    @Override
    public ExamResources getExamResourcesById(Long id) {
        return examResourcesDao.findById(id);
    }

    @Override
    public List<ExamResources> getExamResourcesByCourseId(Long courseId) {
        return examResourcesDao.findByCourseId(courseId);
    }
}
