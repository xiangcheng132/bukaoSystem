package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamClassTeacher;
import com.bukaoSystem.service.ExamClassTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/classTeacher")
public class ExamClassTeacherController {

    @Autowired
    private ExamClassTeacherService examClassTeacherService;

    @GetMapping
    public List<ExamClassTeacher> getAllExamClassTeachers() {
        return examClassTeacherService.getAllExamClassTeachers();
    }

    @GetMapping("/getById")
    public ExamClassTeacher getExamClassTeacherById(@RequestBody ExamClassTeacher examClassTeacher) {
        return examClassTeacherService.getExamClassTeacherById(examClassTeacher.getId());
    }

    @GetMapping("/getByClassId")
    public List<ExamClassTeacher> getExamClassTeachersByClassId(@RequestBody ExamClassTeacher examClassTeacher) {
        return examClassTeacherService.getExamClassTeachersByClassId(examClassTeacher.getClassId());
    }

    @PostMapping("/create")
    public void createExamClassTeacher(@RequestBody ExamClassTeacher examClassTeacher) {
        examClassTeacherService.saveExamClassTeacher(examClassTeacher);
    }

    @PostMapping("/update")
    public void updateExamClassTeacher(@RequestBody ExamClassTeacher examClassTeacher) {
        examClassTeacherService.updateExamClassTeacher(examClassTeacher);
    }

    @PostMapping("/delete")
    public void deleteExamClassTeacher(@RequestBody ExamClassTeacher examClassTeacher) {
        examClassTeacherService.deleteExamClassTeacher(examClassTeacher.getId());
    }
}
