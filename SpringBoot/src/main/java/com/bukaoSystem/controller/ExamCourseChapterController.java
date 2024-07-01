package com.bukaoSystem.controller;

import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamCourse;
import com.bukaoSystem.model.ExamCourseChapter;
import com.bukaoSystem.service.ExamCourseChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> deleteExamCourseChapter(@RequestBody ExamCourseChapter examCourseChapter) {
        try {
            examCourseChapterService.deleteExamCourseChapter(examCourseChapter.getId());
            return new ResponseEntity<>("User delete successfully", HttpStatus.CREATED);
        } catch (ForeignKeyConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/getByCourseId")
    public List<ExamCourseChapter> getExamCourseChapterByCourseId(@RequestBody ExamCourse examCourse) {
        return examCourseChapterService.getExamCourseChapterByCourseId(examCourse.getId());

    }
}