package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamAnswerSheetDetail;
import java.util.List;

public interface ExamAnswerSheetDetailService {
    List<ExamAnswerSheetDetail> getAllExamAnswerSheetDetails();
    List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsById(Long id);
    List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsByAnswerId(Long answerId);
    List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsByResourceId(Long resourceId);
    void saveExamAnswerSheetDetail(ExamAnswerSheetDetail examAnswerSheetDetail);
    void updateExamAnswerSheetDetail(ExamAnswerSheetDetail examAnswerSheetDetail);
    void deleteExamAnswerSheetDetail(Long id);
}
