package com.bukaoSystem.controller;

import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamCourse;
import com.bukaoSystem.model.ExamCourseDto;
import com.bukaoSystem.model.ExamUser;
import com.bukaoSystem.service.ExamCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/getAll")
    public List<ExamCourseDto> getAllExamCourses() {
        return examCourseService.getAllExamCourses();
    }

    @PostMapping("/create")
    public Long createExamCourse(@RequestBody ExamCourse examCourse) {
        return examCourseService.saveExamCourse(examCourse);
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
    public ResponseEntity<String> deleteExamCourse(@RequestBody ExamCourse examCourse) {
        try {
            examCourseService.deleteExamCourse(examCourse.getId());
            return new ResponseEntity<>("successfully", HttpStatus.CREATED);
        } catch (ForeignKeyConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
