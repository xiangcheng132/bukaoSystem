package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamResources;
import com.bukaoSystem.service.ExamResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/resources")
public class ExamResourcesController {

    @Autowired
    private ExamResourcesService examResourcesService;

    @GetMapping
    public List<ExamResources> getAllExamResources() {
        return examResourcesService.getExamResourcesByCourseId(null);
    }

    @GetMapping("/getByCourseId")
    public List<ExamResources> getExamResourcesByCourseId(@RequestBody ExamResources examResources) {
        return examResourcesService.getExamResourcesByCourseId(examResources.getCourseId());
    }

    @GetMapping("/getById")
    public ExamResources getExamResourcesById(@RequestBody ExamResources examResources) {
        return examResourcesService.getExamResourcesById(examResources.getId());
    }

    @PostMapping("/create")
    public void createExamResources(@RequestBody ExamResources examResources) {
        examResourcesService.saveExamResources(examResources);
    }

    @PostMapping("/update")
    public void updateExamResources(@RequestBody ExamResources examResources) {
        examResourcesService.updateExamResources(examResources);
    }

    @PostMapping("/delete")
    public void deleteExamResources(@RequestBody ExamResources examResources) {
        examResourcesService.deleteExamResources(examResources.getId());
    }
}
