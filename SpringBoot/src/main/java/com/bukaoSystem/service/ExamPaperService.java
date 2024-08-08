package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamExam;
import com.bukaoSystem.model.ExamPaperDto;
import com.bukaoSystem.model.ExamResources;

import java.util.List;


public interface ExamPaperService {

//    ExamExam createExamPaper(ExamExam examPaper, List<Long> resourceIds);
    //通过输入对应的章节，问题类型，题目数量获取相应的资源
//    List<ExamResources> getResources(Long courseId, List<Long> chapterIds, List<String> questions, int limit, List<Integer> score);
//    List<ExamResources> getRandomResources(Long courseId, Long chapterId, String questionType, int count);

    //
    int createExamPaper(ExamPaperDto examPaper);

    List<ExamResources> getRandomResources(Long courseId, String question, int count);

//    List<ExamResources> batchCopyResourcesWithScores(Long courseId, Long chapterId, String type, int count, int score);

//    List<ExamResources> batchCopyResources(Long courseId, String type, int count, int score);
}
