package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamExam;
import com.bukaoSystem.model.ExamResources;

import java.util.List;

public interface ExamPaperDAO {
    ExamExam save(ExamExam examPaper);
    List<ExamResources> findRandomResourcesByChapterAndType(Long courseId,Long chapterId, String questionType, int count);

    // 批量复制资源并修改分值
    List<ExamResources> batchCopyResourcesWithScores(Long courseId, Long chapterId, String type, int count, int score);

    void saveExamPaperResource(Long examId, Long resourceId);
}
