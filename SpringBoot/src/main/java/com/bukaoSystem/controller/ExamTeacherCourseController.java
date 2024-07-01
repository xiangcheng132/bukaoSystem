package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamTeacherCourse;
import com.bukaoSystem.service.ExamTeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/teacherCourse")
public class ExamTeacherCourseController {

    @Autowired
    private ExamTeacherCourseService examTeacherCourseService;

    @GetMapping
    public List<ExamTeacherCourse> getAllExamTeacherCourses() {
        return examTeacherCourseService.getAllExamTeacherCourses();
    }

    @PostMapping("/getById")
    public List<ExamTeacherCourse> getExamTeacherCoursesById(@RequestBody ExamTeacherCourse examTeacherCourse) {
        return examTeacherCourseService.getExamTeacherCoursesById(examTeacherCourse.getId());
    }

    @PostMapping("/getByTeacherId")
    public List<ExamTeacherCourse> getExamTeacherCoursesByTeacherId(@RequestBody ExamTeacherCourse examTeacherCourse) {
        return examTeacherCourseService.getExamTeacherCoursesByTeacherId(examTeacherCourse.getTeacherId());
    }

    @PostMapping("/getByCourseId")
    public List<ExamTeacherCourse> getExamTeacherCoursesByCourseId(@RequestBody ExamTeacherCourse examTeacherCourse) {
        return examTeacherCourseService.getExamTeacherCoursesByCourseId(examTeacherCourse.getCourseId());
    }

    @PostMapping("/create")
    public void createExamTeacherCourse(@RequestBody ExamTeacherCourse examTeacherCourse) {
        examTeacherCourseService.saveExamTeacherCourse(examTeacherCourse);
    }

    @PostMapping("/update")
    public void updateExamTeacherCourse(@RequestBody ExamTeacherCourse examTeacherCourse) {
        examTeacherCourseService.updateExamTeacherCourse(examTeacherCourse);
    }

    @PostMapping("/delete")
    public void deleteExamTeacherCourse(@RequestBody ExamTeacherCourse examTeacherCourse) {
        examTeacherCourseService.deleteExamTeacherCourse(examTeacherCourse.getTeacherId(), examTeacherCourse.getCourseId());
    }
}
