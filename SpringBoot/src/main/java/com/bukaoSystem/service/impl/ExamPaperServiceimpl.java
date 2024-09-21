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
        if (examPaper == null || examPaper.getCourseId() == null) {
            throw new IllegalArgumentException("试卷数据或课程ID不能为空");
        }
        try {
            Long savedExamPaperId = examPaperdao.save(examPaper);
            examPaperdao.saveExamExamClass(savedExamPaperId, examPaper.getClassId());
            if (savedExamPaperId == null) {
                throw new RuntimeException("保存试卷失败");
            }

            int singleChoiceCount = examPaper.getSingleChoiceCount();
            int trueFalseCount = examPaper.getTrueFalseCount();
            int fillInBlankCount = examPaper.getFillInBlankCount();
            int bigquestionCount = examPaper.getBigquestionCount();

            String type1 = "single_choice";
            String type2 = "true_false";
            String type3 = "completion";
            String type4 = "bigquestion";

            List<ExamResources> singleChoiceresources = getRandomResources(examPaper.getCourseId(), type1, singleChoiceCount);
            if (singleChoiceresources == null) {
                singleChoiceresources = new ArrayList<>();
            }
            List<ExamResources> trueFalseresources = getRandomResources(examPaper.getCourseId(), type2, trueFalseCount);
            if (trueFalseresources == null) {
                trueFalseresources = new ArrayList<>();
            }
            List<ExamResources> fillInBlankresources = getRandomResources(examPaper.getCourseId(), type3, fillInBlankCount);
            if (fillInBlankresources == null) {
                fillInBlankresources = new ArrayList<>();
            }
            List<ExamResources> bigquestionresources = getRandomResources(examPaper.getCourseId(), type4, bigquestionCount);
            if (bigquestionresources == null) {
                bigquestionresources = new ArrayList<>();
            }

            List<ExamResources> allResources = new ArrayList<>();
            allResources.addAll(singleChoiceresources);
            allResources.addAll(trueFalseresources);
            allResources.addAll(fillInBlankresources);
            allResources.addAll(bigquestionresources);

            if (allResources.isEmpty()) {
                throw new IllegalArgumentException("没有可关联的试题资源");
            }

            for (ExamResources resource : allResources) {
                // 打印调试SQL插入语句
                System.out.println("正在插入资源，试卷ID: " + savedExamPaperId + ", 资源ID: " + resource.getId());
                examPaperdao.saveExamPaperResource(savedExamPaperId, resource.getId());
            }
            return 1; // 添加成功，返回1
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("创建试卷时发生错误: " + e.getMessage());
            return 0; // 添加失败，返回0
        }
    }

//        try {
//            Long savedExamPaperId = examPaperdao.save(examPaper);
//            int singleChoiceCount = examPaper.getSingleChoiceCount();
//            int trueFalseCount = examPaper.getTrueFalseCount();
//            int fillInBlankCount = examPaper.getFillInBlankCount();
//            int bigquestionCount = examPaper.getBigquestionCount();
//            String type1 = "single_choice";
//            String type2 = "true_false";
//            String type3 = "completion";
//            String type4 = "bigquestion";
//            List<ExamResources> singleChoiceresources = getRandomResources(examPaper.getCourseId(), type1, singleChoiceCount);
//            List<ExamResources> trueFalseresources = getRandomResources(examPaper.getCourseId(), type2, trueFalseCount);
//            List<ExamResources> fillInBlankresources = getRandomResources(examPaper.getCourseId(), type3, fillInBlankCount);
//            List<ExamResources> bigquestionresources = getRandomResources(examPaper.getCourseId(), type4, bigquestionCount);
//
//            // 合并所有资源
//            List<ExamResources> allResources = new ArrayList<>();
//            allResources.addAll(singleChoiceresources);
//            allResources.addAll(trueFalseresources);
//            allResources.addAll(fillInBlankresources);
//            allResources.addAll(bigquestionresources);
//
//            // 依次关联表数据
//            allResources.forEach(resource -> {
//                examPaperdao.saveExamPaperResource(savedExamPaperId, resource.getId());
//            });
//            return 1; // 添加成功，返回1
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0; // 添加失败，返回0
//        }
//    }

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
    @Override
    public Long saveexam(ExamPaperDto examExam) {
        return examPaperdao.save(examExam);
    }
    @Override
    public void saveexamclass(Long examId, Long classId) {
        examPaperdao.saveExamExamClass(examId, classId);
    }
}

