package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamPaperDAO;
import com.bukaoSystem.model.ExamExam;
import com.bukaoSystem.model.ExamResources;
import com.bukaoSystem.service.ExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExamPaperServiceimpl implements ExamPaperService {

    @Autowired
    private ExamPaperDAO examPaperdao;
    //
    @Override
    public ExamExam createExamPaper(ExamExam examPaper, List<Long> resourceIds) {
        ExamExam savedExamPaper = examPaperdao.save(examPaper);
        resourceIds.forEach(resourceId -> {
            // 创建并保存 ExamPaperResource 关联表数据
            examPaperdao.saveExamPaperResource(savedExamPaper.getId(), resourceId);
        });
        return savedExamPaper;
    }
    //通过输入对应的章节，问题类型，题目数量获取相应的资源
    @Override
    public List<ExamResources> getResources(Long courseId, List<Long> chapterIds, List<String> questions, int limit, List<Integer> score) {
        // 根据传入的chapterIds和questions处理逻辑并返回结果
        // 这里需要实现你获取随机资源的逻辑
        List<ExamResources> resources = new ArrayList<>();
        for (int i = 0; i < chapterIds.size(); i++) {
            Long chapterId = chapterIds.get(i);
            String question = questions.get(i);
            int scores = score.get(i);

            // 根据chapterId和question获取资源并添加到资源列表
            List<ExamResources> chapterResources = batchCopyResourcesWithScores(courseId,chapterId,question,limit,scores);
            resources.addAll(chapterResources);
        }
        return resources;
    }
    @Override
    public List<ExamResources> getRandomResources(Long courseId, Long chapterId, String question, int count) {
        return examPaperdao.findRandomResourcesByChapterAndType(courseId,chapterId, question, count);
    }
    @Override
    public List<ExamResources> batchCopyResourcesWithScores(Long courseId, Long chapterId, String type, int count, int score) {
        return examPaperdao.batchCopyResourcesWithScores(courseId,chapterId,type,count,score);
    }
}

