package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamCourse;
import com.bukaoSystem.service.ExamCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/course")
public class ExamCourseController {

    @Autowired
    private ExamCourseService examCourseService;

    @GetMapping
    public List<ExamCourse> getAllExamCourses() {
        return examCourseService.getAllExamCourses();
    }

    @PostMapping("/create")
    public void createExamCourse(@RequestBody ExamCourse examCourse) {
        examCourseService.saveExamCourse(examCourse);
    }

    @GetMapping("/getById")
    public ExamCourse getExamCourseById(@RequestBody ExamCourse examCourse) {
        return examCourseService.getExamCourseById(examCourse.getId());
    }

    @GetMapping("/getByName")
    public List<ExamCourse> getExamCourseByName(@RequestBody ExamCourse examCourse) {
        return examCourseService.getExamCoursesByName(examCourse.getName());
    }

    @PostMapping("/update")
    public void updateExamCourse(@RequestBody ExamCourse examCourse) {
        examCourseService.updateExamCourse(examCourse);
    }

    @PostMapping("/delete")
    public void deleteExamCourse(@RequestBody ExamCourse examCourse) {
        examCourseService.deleteExamCourse(examCourse.getId());
    }
}
