package com.bukaoSystem.controller;

import com.bukaoSystem.exception.AccountAlreadyRegisteredException;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamExam;
import com.bukaoSystem.model.ExamUser;
import com.bukaoSystem.service.ExamExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/exam")
public class ExamExamController {

    @Autowired
    private ExamExamService examExamService;

    @GetMapping
    public List<ExamExam> getAllExamExams() {
        return examExamService.getAllExamExams();
    }

    @PostMapping("/getById")
    public ExamExam getExamExamById(@RequestBody ExamExam examExam) {
        return examExamService.getExamExamById(examExam.getId());
    }

    @PostMapping("/getByCourseId")
    public List<ExamExam> getExamExamByCourseId(@RequestBody ExamExam examExam) {
        return examExamService.getExamExamsByCourseId(examExam.getCourseId());
    }

    @PostMapping("/getByName")
    public List<ExamExam> getExamExamByName(@RequestBody ExamExam examExam) {
        return examExamService.getExamExamsByName(examExam.getName());
    }

    @PostMapping("/create")
    public void createExamExam(@RequestBody ExamExam examExam) {
        examExamService.saveExamExam(examExam);
    }

    @PostMapping("/update")
    public void updateExamExam(@RequestBody ExamExam examExam) {
        examExamService.updateExamExam(examExam);
    }

    @PostMapping("/delete")
    public ResponseEntity<String>  deleteExamExam(@RequestBody ExamExam examExam) {
        try {
            examExamService.deleteExamExam(examExam.getId());
            return new ResponseEntity<>("User delete successfully", HttpStatus.CREATED);
        } catch (ForeignKeyConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/getByUserId")
    public List<ExamExam> getExamExamByUserId(@RequestBody ExamUser examUser) {
        return examExamService.getExamExamsByUserId(examUser.getId());
    }
}
