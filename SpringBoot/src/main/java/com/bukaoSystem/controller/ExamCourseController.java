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

    @GetMapping("/{id}")
    public ExamCourse getExamCourseById(@PathVariable Long id) {
        return examCourseService.getExamCourseById(id);
    }

    @GetMapping("/teacher/{id}")
    public List<ExamCourse> getExamCourseByTeacherId(@PathVariable Long id) {
        return examCourseService.getExamCourseByTeacherId(id);
    }


    @PostMapping("/update/{id}")
    public void updateExamCourse(@PathVariable Long id, @RequestBody ExamCourse examCourse) {
        examCourseService.updateExamCourse(id, examCourse);
    }

    @PostMapping("/delete/{id}")
    public void deleteExamCourse(@PathVariable Long id) {
        examCourseService.deleteExamCourse(id);
    }
}
