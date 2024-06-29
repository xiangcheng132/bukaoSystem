package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamExam;
import com.bukaoSystem.service.ExamExamService;
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
    private ExamExamService examExamService;

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
        exam1.setState(0);
        exam1.setBeginTime(LocalDateTime.of(2020, 1, 1, 10, 0).toString());
        exam1.setEndTime(LocalDateTime.of(2020, 1, 1, 12, 0).toString());
        exam1.setCreateTime("2020-01-01 00:00:00");

        exam2 = new ExamExam();
        exam2.setId(2L);
        exam2.setCourseId(2L);
        exam2.setName("Exam 2");
        exam2.setComment("Comment 2");
        exam2.setPlace("Place 2");
        exam2.setState(1);
        exam2.setBeginTime(LocalDateTime.of(2021, 1, 1, 10, 0).toString());
        exam2.setEndTime(LocalDateTime.of(2021, 1, 1, 12, 0).toString());
        exam2.setCreateTime("2021-01-01 00:00:00");
    }

    @Test
    public void testGetAllExamExams() throws Exception {
        List<ExamExam> exams = Arrays.asList(exam1, exam2);
        Mockito.when(examExamService.getAllExamExams()).thenReturn(exams);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/exam"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(exam1.getName()))
                .andExpect(jsonPath("$[1].name").value(exam2.getName()));
    }

    @Test
    public void testCreateExamExam() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/exam/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exam1)))
                .andExpect(status().isOk());

        Mockito.verify(examExamService, Mockito.times(1)).saveExamExam(Mockito.any(ExamExam.class));
    }

    @Test
    public void testGetExamExamById() throws Exception {
        Mockito.when(examExamService.getExamExamById(1L)).thenReturn(exam1);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/exam/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exam1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(exam1.getName()));
    }

    @Test
    public void testGetExamExamByCourseId() throws Exception {
        List<ExamExam> exams = Arrays.asList(exam1);
        Mockito.when(examExamService.getExamExamsByCourseId(1L)).thenReturn(exams);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/exam/getByCourseId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exam1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(exam1.getName()));
    }

    @Test
    public void testGetExamExamByName() throws Exception {
        List<ExamExam> exams = Arrays.asList(exam1);
        Mockito.when(examExamService.getExamExamsByName("Exam 1")).thenReturn(exams);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/exam/getByName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exam1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(exam1.getName()));
    }

    @Test
    public void testUpdateExamExam() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/exam/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exam1)))
                .andExpect(status().isOk());

        Mockito.verify(examExamService, Mockito.times(1)).updateExamExam(Mockito.any(ExamExam.class));
    }

    @Test
    public void testDeleteExamExam() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/exam/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exam1)))
                .andExpect(status().isOk());

        Mockito.verify(examExamService, Mockito.times(1)).deleteExamExam(exam1.getId());
    }
}
