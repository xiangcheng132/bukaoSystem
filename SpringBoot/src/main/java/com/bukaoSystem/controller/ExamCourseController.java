package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamCourse;
import com.bukaoSystem.model.ExamUser;
import com.bukaoSystem.service.ExamCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/course")
public class ExamCourseController {

    @Autowired
    private ExamCourseService examCourseService;

    @PostMapping
    public List<ExamCourse> getAllExamCourses(@RequestBody ExamUser examUser) {
        return examCourseService.getAllExamCourses(examUser);
    }

    @PostMapping("/create")
    public void createExamCourse(@RequestBody ExamCourse examCourse) {
        examCourseService.saveExamCourse(examCourse);
    }

    @PostMapping("/postById")
    public ExamCourse getExamCourseById(@RequestBody ExamCourse examCourse) {
        return examCourseService.getExamCourseById(examCourse.getId());
    }

    @PostMapping("/postByName")
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
