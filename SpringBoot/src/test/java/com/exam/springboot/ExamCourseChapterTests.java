package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamCourseChapter;
import com.bukaoSystem.service.ExamCourseChapterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ExamCourseChapterTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamCourseChapterService examCourseChapterService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamCourseChapter chapter1;
    private ExamCourseChapter chapter2;

    @BeforeEach
    public void setup() {
        chapter1 = new ExamCourseChapter();
        chapter1.setId(1L);
        chapter1.setCourseId(1L);
        chapter1.setName("Chapter 1");

        chapter2 = new ExamCourseChapter();
        chapter2.setId(2L);
        chapter2.setCourseId(1L);
        chapter2.setName("Chapter 2");
    }

    @Test
    public void testGetAllExamCourseChapters() throws Exception {
        List<ExamCourseChapter> chapters = Arrays.asList(chapter1, chapter2);
        Mockito.when(examCourseChapterService.getAllExamCourseChapters()).thenReturn(chapters);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/courseChapter"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(chapter1.getName()))
                .andExpect(jsonPath("$[1].name").value(chapter2.getName()));
    }

    @Test
    public void testGetExamCourseChapterById() throws Exception {
        Mockito.when(examCourseChapterService.getExamCourseChapterById(1L)).thenReturn(chapter1);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/courseChapter/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chapter1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(chapter1.getName()));
    }

    @Test
    public void testCreateExamCourseChapter() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/courseChapter/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chapter1)))
                .andExpect(status().isOk());

        Mockito.verify(examCourseChapterService, Mockito.times(1)).saveExamCourseChapter(Mockito.any(ExamCourseChapter.class));
    }

    @Test
    public void testUpdateExamCourseChapter() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/courseChapter/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chapter1)))
                .andExpect(status().isOk());

        Mockito.verify(examCourseChapterService, Mockito.times(1)).updateExamCourseChapter(Mockito.any(ExamCourseChapter.class));
    }

    @Test
    public void testDeleteExamCourseChapter() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/courseChapter/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chapter1)))
                .andExpect(status().isOk());

        Mockito.verify(examCourseChapterService, Mockito.times(1)).deleteExamCourseChapter(chapter1.getId());
    }
}
