package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamCourseChapter;

import java.util.List;

public interface ExamCourseChapterService {
    void saveExamCourseChapter(ExamCourseChapter examCourseChapter);
    void updateExamCourseChapter(ExamCourseChapter examCourseChapter);
    void deleteExamCourseChapter(Long id);
    ExamCourseChapter getExamCourseChapterById(Long id);
    List<ExamCourseChapter> getAllExamCourseChapters();

    List<ExamCourseChapter> getExamCourseChapterByCourseId(Long id);
}