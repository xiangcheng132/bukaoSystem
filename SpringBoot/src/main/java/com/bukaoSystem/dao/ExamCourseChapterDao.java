package com.bukaoSystem.dao;


import com.bukaoSystem.model.ExamCourseChapter;

import java.util.List;

public interface ExamCourseChapterDao {
    void saveExamCourseChapter(ExamCourseChapter examCourseChapter);
    void updateExamCourseChapter(ExamCourseChapter examCourseChapter);
    void deleteExamCourseChapter(Long id);
    ExamCourseChapter getExamCourseChapterById(Long id);
    List<ExamCourseChapter> getAllExamCourseChapters();
    List<ExamCourseChapter> getAllExamCourseChaptersByCourseId(Long id);
}
