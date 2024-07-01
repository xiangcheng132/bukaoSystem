package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamExamResources;
import com.bukaoSystem.service.ExamExamResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/examResources")
public class ExamExamResourcesController {

    @Autowired
    private ExamExamResourcesService examExamResourcesService;

    @GetMapping
    public List<ExamExamResources> getAllExamExamResources() {
        return examExamResourcesService.getAllExamExamResources();
    }
    @PostMapping("/getById")
    public List<ExamExamResources> getExamExamResourcesById(@RequestBody ExamExamResources examExamResources) {
        return examExamResourcesService.getExamExamResourcesById(examExamResources.getId());
    }

    @PostMapping("/getByExamId")
    public List<ExamExamResources> getExamExamResourcesByExamId(@RequestBody ExamExamResources examExamResources) {
        return examExamResourcesService.getExamExamResourcesByExamId(examExamResources.getExamId());
    }

    @PostMapping("/getByResourceId")
    public List<ExamExamResources> getExamExamResourcesByResourceId(@RequestBody ExamExamResources examExamResources) {
        return examExamResourcesService.getExamExamResourcesByResourceId(examExamResources.getResourceId());
    }

    @PostMapping("/create")
    public void createExamExamResources(@RequestBody ExamExamResources examExamResources) {
        examExamResourcesService.saveExamExamResources(examExamResources);
    }

    @PostMapping("/update")
    public void updateExamExamResources(@RequestBody ExamExamResources examExamResources) {
        examExamResourcesService.updateExamExamResources(examExamResources);
    }

    @PostMapping("/delete")
    public void deleteExamExamResources(@RequestBody ExamExamResources examExamResources) {
        examExamResourcesService.deleteExamExamResources(examExamResources.getExamId(), examExamResources.getResourceId());
    }
}
