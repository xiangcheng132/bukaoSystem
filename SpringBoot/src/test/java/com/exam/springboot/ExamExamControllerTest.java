package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamExam;
import com.bukaoSystem.service.impl.ExamExamServiceImpl;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ExamExamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamExamServiceImpl examExamServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamExam exam1;
    private ExamExam exam2;

    @BeforeEach
    public void setup() {
        exam1 = new ExamExam();
        exam1.setId(1L);
        exam1.setCourseId(1L);
        exam1.setName("Exam 1");
        exam1.setComment("Comment 1");
        exam1.setPlace("Place 1");
        exam1.setBeginTime(LocalDateTime.of(2024, 6, 28, 10, 0).toString());
        exam1.setEndTime(LocalDateTime.of(2024, 6, 28, 12, 0).toString());
        exam1.setCreateTime(LocalDateTime.of(2024, 6, 28, 9, 0).toString());

        exam2 = new ExamExam();
        exam2.setId(2L);
        exam2.setCourseId(2L);
        exam2.setName("Exam 2");
        exam2.setComment("Comment 2");
        exam2.setPlace("Place 2");
        exam2.setBeginTime(LocalDateTime.of(2024, 7, 1, 14, 0).toString());
        exam2.setEndTime(LocalDateTime.of(2024, 7, 1, 16, 0).toString());
        exam2.setCreateTime(null); // Testing null createTime
    }

    @Test
    public void testGetAllExams() throws Exception {
        List<ExamExam> exams = Arrays.asList(exam1, exam2);
        Mockito.when(examExamServiceImpl.getAllExamExams()).thenReturn(exams);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/exam"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(exam1.getName()))
                .andExpect(jsonPath("$[1].name").value(exam2.getName()));
    }

    @Test
    public void testGetExamById() throws Exception {
        Mockito.when(examExamServiceImpl.getExamExamById(1L)).thenReturn(exam1);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/exam/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(exam1.getName()));
    }

    @Test
    public void testCreateExam() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/exam/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exam1)))
                .andExpect(status().isOk());

        Mockito.verify(examExamServiceImpl, Mockito.times(1)).saveExamExam(Mockito.any(ExamExam.class));
    }

    @Test
    public void testUpdateExam() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/exam/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exam1)))
                .andExpect(status().isOk());

        Mockito.verify(examExamServiceImpl, Mockito.times(1)).updateExamExam(Mockito.any(ExamExam.class));
    }

    @Test
    public void testDeleteExam() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/exam/delete/1"))
                .andExpect(status().isOk());

        Mockito.verify(examExamServiceImpl, Mockito.times(1)).deleteExamExam(1L);
    }
}
