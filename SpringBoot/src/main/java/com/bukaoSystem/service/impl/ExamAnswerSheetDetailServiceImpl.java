package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamAnswerSheetDetailDao;
import com.bukaoSystem.model.ExamAnswerSheetDetail;
import com.bukaoSystem.service.ExamAnswerSheetDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamAnswerSheetDetailServiceImpl implements ExamAnswerSheetDetailService {

    @Autowired
    private ExamAnswerSheetDetailDao examAnswerSheetDetailDao;

    @Override
    public List<ExamAnswerSheetDetail> getAllExamAnswerSheetDetails() {
        return examAnswerSheetDetailDao.getAllExamAnswerSheetDetails();
    }

    @Override
    public List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsById(Long id) {
        return examAnswerSheetDetailDao.getExamAnswerSheetDetailsById(id);
    }

    @Override
    public List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsByAnswerId(Long answerId) {
        return examAnswerSheetDetailDao.getExamAnswerSheetDetailsByAnswerId(answerId);
    }

    @Override
    public List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsByResourceId(Long resourceId) {
        return examAnswerSheetDetailDao.getExamAnswerSheetDetailsByResourceId(resourceId);
    }

    @Override
    public void saveExamAnswerSheetDetail(ExamAnswerSheetDetail examAnswerSheetDetail) {
        examAnswerSheetDetailDao.saveExamAnswerSheetDetail(examAnswerSheetDetail);
    }

    @Override
    public void updateExamAnswerSheetDetail(ExamAnswerSheetDetail examAnswerSheetDetail) {
        examAnswerSheetDetailDao.updateExamAnswerSheetDetail(examAnswerSheetDetail);
    }

    @Override
    public void deleteExamAnswerSheetDetail(Long id) {
        examAnswerSheetDetailDao.deleteExamAnswerSheetDetail(id);
    }
    @Override
    public void reviewExamAnswerSheet(Long Id,String isTrue) {
        examAnswerSheetDetailDao.updateExamAnswerSheetReviewStatus(Id, isTrue);
    }
    @Override
    public void createOrupdateExamAnswerSheetDetail(ExamAnswerSheetDetail examAnswerSheetDetail){
        examAnswerSheetDetailDao.createOrupdateExamAnswerSheetDetail(examAnswerSheetDetail);
    }
}
