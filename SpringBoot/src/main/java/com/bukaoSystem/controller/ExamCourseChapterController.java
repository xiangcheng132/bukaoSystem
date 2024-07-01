package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamCourseChapter;
import com.bukaoSystem.service.ExamCourseChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/courseChapter")
public class ExamCourseChapterController {

    @Autowired
    private ExamCourseChapterService examCourseChapterService;

    @GetMapping
    public List<ExamCourseChapter> getAllExamCourseChapters() {
        return examCourseChapterService.getAllExamCourseChapters();
    }

    @PostMapping("/getById")
    public ExamCourseChapter getExamCourseChapterById(@RequestBody ExamCourseChapter examCourseChapter) {
        return examCourseChapterService.getExamCourseChapterById(examCourseChapter.getId());
    }

    @PostMapping("/create")
    public void createExamCourseChapter(@RequestBody ExamCourseChapter examCourseChapter) {
        examCourseChapterService.saveExamCourseChapter(examCourseChapter);
    }

    @PostMapping("/update")
    public void updateExamCourseChapter(@RequestBody ExamCourseChapter examCourseChapter) {
        examCourseChapterService.updateExamCourseChapter(examCourseChapter);
    }

    @PostMapping("/delete")
    public void deleteExamCourseChapter(@RequestBody ExamCourseChapter examCourseChapter) {
        examCourseChapterService.deleteExamCourseChapter(examCourseChapter.getId());
    }
}