package com.bukaoSystem.controller;

import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
import com.bukaoSystem.model.ExamResources;
import com.bukaoSystem.service.ExamResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/resources")
public class ExamResourcesController {

    @Autowired
    private ExamResourcesService examResourcesService;

    @GetMapping
    public List<ExamResources> getAllExamResources() {
        return examResourcesService.getExamResourcesByCourseId(null,"ASC");
    }

    @PostMapping("/getByCourseId")
    public List<ExamResources> getExamResourcesByCourseId(@RequestBody ExamResources examResources,@RequestParam(defaultValue = "ASC") String sort) {
        return examResourcesService.getExamResourcesByCourseId(examResources.getCourseId(),sort);
    }

    @PostMapping("/getByChapterId")
    public List<ExamResources> getExamResourcesByChapterId(@RequestBody ExamResources examResources,@RequestParam(defaultValue = "ASC") String sort) {
        return examResourcesService.getExamResourcesByChapterId(examResources.getChapterId(),sort);
    }

    @PostMapping("/getById")
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
    public ResponseEntity<String> deleteExamResources(@RequestBody ExamResources examResources) {
        try {
            examResourcesService.deleteExamResources(examResources.getId());
            return new ResponseEntity<>("User delete successfully", HttpStatus.CREATED);
        } catch (ForeignKeyConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
