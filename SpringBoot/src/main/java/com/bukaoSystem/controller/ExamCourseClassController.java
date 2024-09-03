package com.bukaoSystem.controller;

import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamCourseClass;
import com.bukaoSystem.service.ExamCourseClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/courseClass")
public class ExamCourseClassController {

    @Autowired
    private ExamCourseClassService examCourseClassService;

    @GetMapping
    public List<ExamCourseClass> getAllExamCourseClasses() {
        return examCourseClassService.getAllExamCourseClasses();
    }

    @PostMapping("/getById")
    public ExamCourseClass getExamCourseClassById(@RequestBody ExamCourseClass examCourseClass) {
        return examCourseClassService.getExamCourseClassById(examCourseClass.getId());
    }

    @PostMapping("/create")
    public void createExamCourseClass(@RequestBody ExamCourseClass examCourseClass) {
        examCourseClassService.saveExamCourseClass(examCourseClass);
    }

    @PostMapping("/update")
    public void updateExamCourseClass(@RequestBody ExamCourseClass examCourseClass) {
        examCourseClassService.updateExamCourseClass(examCourseClass);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteExamCourseClass(@RequestBody ExamCourseClass examCourseClass) {
        try {
            examCourseClassService.deleteExamCourseClass(examCourseClass.getId());
            return new ResponseEntity<>("successfully", HttpStatus.CREATED);
        } catch (ForeignKeyConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/getByClassId")
    public List<ExamCourseClass> getExamCourseClassByClassId(@RequestBody ExamCourseClass examCourseClass) {
        return examCourseClassService.getExamCourseClassesByClassId(examCourseClass.getClassId());
    }
    @PostMapping("/getByCourseId")
    public List<ExamCourseClass> getExamCourseClassByCourseId(@RequestBody ExamCourseClass examCourseClass) {
        return examCourseClassService.getExamCourseClassesByCourseId(examCourseClass.getCourseId());
    }
}
