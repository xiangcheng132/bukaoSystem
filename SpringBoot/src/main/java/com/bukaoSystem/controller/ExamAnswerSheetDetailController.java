package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamAnswerSheetDetail;
import com.bukaoSystem.service.ExamAnswerSheetDetailService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void deleteExamAnswerSheetDetail(@RequestBody ExamAnswerSheetDetail examAnswerSheetDetail) {
        examAnswerSheetDetailService.deleteExamAnswerSheetDetail(examAnswerSheetDetail.getId());
    }
}

