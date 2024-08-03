package com.bukaoSystem.controller;

import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamAnswerSheetDetail;
import com.bukaoSystem.service.ExamAnswerSheetDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/answerSheetDetail")
public class ExamAnswerSheetDetailController {

    @Autowired
    private ExamAnswerSheetDetailService examAnswerSheetDetailService;

    @GetMapping
    public List<ExamAnswerSheetDetail> getAllExamAnswerSheetDetails() {
        return examAnswerSheetDetailService.getAllExamAnswerSheetDetails();
    }

    @PostMapping("/getById")
    public List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsById(@RequestBody ExamAnswerSheetDetail examAnswerSheetDetail) {
        return examAnswerSheetDetailService.getExamAnswerSheetDetailsById(examAnswerSheetDetail.getId());
    }

    @PostMapping("/getByAnswerId")
    public List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsByAnswerId(@RequestBody ExamAnswerSheetDetail examAnswerSheetDetail) {
        return examAnswerSheetDetailService.getExamAnswerSheetDetailsByAnswerId(examAnswerSheetDetail.getAnswerId());
    }

    @PostMapping("/getByResourceId")
    public List<ExamAnswerSheetDetail> getExamAnswerSheetDetailsByResourceId(@RequestBody ExamAnswerSheetDetail examAnswerSheetDetail) {
        return examAnswerSheetDetailService.getExamAnswerSheetDetailsByResourceId(examAnswerSheetDetail.getResourceId());
    }

    @PostMapping("/create")
    public void createExamAnswerSheetDetail(@RequestBody ExamAnswerSheetDetail examAnswerSheetDetail) {
        examAnswerSheetDetailService.saveExamAnswerSheetDetail(examAnswerSheetDetail);
    }
    @PostMapping("/update")
    public void updateExamAnswerSheetDetail(@RequestBody ExamAnswerSheetDetail examAnswerSheetDetail) {
        examAnswerSheetDetailService.updateExamAnswerSheetDetail(examAnswerSheetDetail);
    }
    @PostMapping("/delete")
    public ResponseEntity<String> deleteExamAnswerSheetDetail(@RequestBody ExamAnswerSheetDetail examAnswerSheetDetail) {
        try {
            examAnswerSheetDetailService.deleteExamAnswerSheetDetail(examAnswerSheetDetail.getId());
            return new ResponseEntity<>("successfully", HttpStatus.CREATED);
        } catch (ForeignKeyConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/review")
    public void reviewExamAnswerSheet(@RequestParam Long id,@RequestParam String isTrue) {
        examAnswerSheetDetailService.reviewExamAnswerSheet(id,isTrue);
    }
}

