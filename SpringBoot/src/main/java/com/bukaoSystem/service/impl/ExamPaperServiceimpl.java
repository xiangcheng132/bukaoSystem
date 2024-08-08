package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamPaperDAO;
import com.bukaoSystem.model.ExamPaperDto;
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
    public int createExamPaper(ExamPaperDto examPaper) {
        try {
            Long savedExamPaperId = examPaperdao.save(examPaper);
            int singleChoiceCount = examPaper.getSingleChoiceCount();
            int trueFalseCount = examPaper.getTrueFalseCount();
            int fillInBlankCount = examPaper.getFillInBlankCount();
            int bigquestionCount = examPaper.getBigquestionCount();
            String type1 = "single_choice";
            String type2 = "true_false";
            String type3 = "completion";
            String type4 = "bigquestion";
            List<ExamResources> singleChoiceresources = getRandomResources(examPaper.getCourseId(), type1, singleChoiceCount);
            List<ExamResources> trueFalseresources = getRandomResources(examPaper.getCourseId(), type2, trueFalseCount);
            List<ExamResources> fillInBlankresources = getRandomResources(examPaper.getCourseId(), type3, fillInBlankCount);
            List<ExamResources> bigquestionresources = getRandomResources(examPaper.getCourseId(), type4, bigquestionCount);
            // 合并所有资源
            List<ExamResources> allResources = new ArrayList<>();
            allResources.addAll(singleChoiceresources);
            allResources.addAll(trueFalseresources);
            allResources.addAll(fillInBlankresources);
            allResources.addAll(bigquestionresources);

            // 依次关联表数据
            allResources.forEach(resource -> {
                examPaperdao.saveExamPaperResource(savedExamPaperId, resource.getId());
            });

            return 1; // 添加成功，返回1
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // 添加失败，返回0
        }
    }

//    public List<ExamResources> getResources(Long courseId, List<Long> chapterIds, List<String> questions, int limit, List<Integer> score) {
//        // 根据传入的chapterIds和questions处理逻辑并返回结果
//        // 这里需要实现你获取随机资源的逻辑
//        List<ExamResources> resources = new ArrayList<>();
//        for (int i = 0; i < chapterIds.size(); i++) {
//            Long chapterId = chapterIds.get(i);
//            String question = questions.get(i);
//            int scores = score.get(i);
//
//            // 根据chapterId和question获取资源并添加到资源列表
//            List<ExamResources> chapterResources = batchCopyResourcesWithScores(courseId,chapterId,question,limit,scores);
//            resources.addAll(chapterResources);
//        }
//        return resources;
//    }
    @Override
    public List<ExamResources> getRandomResources(Long courseId, String question, int count) {
        return examPaperdao.findRandomResourcesByChapterAndType(courseId, question, count);
    }
//    @Override
//    public List<ExamResources> batchCopyResources(Long courseId, String type, int count) {
//        return examPaperdao.batchCopyResources(courseId,type,count);
//    }
}

