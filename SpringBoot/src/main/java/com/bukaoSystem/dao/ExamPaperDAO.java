package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamExam;
import com.bukaoSystem.model.ExamPaperDto;
import com.bukaoSystem.model.ExamResources;

import java.util.List;

public interface ExamPaperDAO {
//    List<ExamResources> findRandomResourcesByChapterAndType(Long courseId,Long chapterId, String questionType, int count);

    //添加试卷
    Long save(ExamPaperDto examExam);

    //返回对应数量的随机的资源题目
    List<ExamResources> findRandomResourcesByChapterAndType(Long courseId, String Type, int count);

    // 批量复制资源并修改分值
//    List<ExamResources> batchCopyResourcesWithScores(Long courseId, Long chapterId, String type, int count, int score);

    void saveExamPaperResource(Long examId, Long resourceId);

    //添加对应试卷和班级对应关系
    void saveExamExamClass(Long examId, Long classId);
}
