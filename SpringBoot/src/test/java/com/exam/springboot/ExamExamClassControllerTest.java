package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamExamClass;
import com.bukaoSystem.service.ExamExamClassService;
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
public class ExamExamClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamExamClassService examExamClassService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamExamClass examClass1;
    private ExamExamClass examClass2;

    @BeforeEach
    public void setup() {
        examClass1 = new ExamExamClass();
        examClass1.setExamId(1L);
        examClass1.setClassId(1L);
        examClass1.setCreateTime("2023-01-01 00:00:00");

        examClass2 = new ExamExamClass();
        examClass2.setExamId(2L);
        examClass2.setClassId(2L);
        examClass2.setCreateTime("2023-02-01 00:00:00");
    }

    @Test
    public void testGetAllExamExamClasses() throws Exception {
        List<ExamExamClass> examClasses = Arrays.asList(examClass1, examClass2);
        Mockito.when(examExamClassService.getAllExamExamClasses()).thenReturn(examClasses);

        mockMvc.perform(get("/bukaoSystem/examClass")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(examClass1.getExamId()))
                .andExpect(jsonPath("$[1].examId").value(examClass2.getExamId()));
    }

    @Test
    public void testGetExamExamClassesByExamId() throws Exception {
        List<ExamExamClass> examClasses = Arrays.asList(examClass1);
        Mockito.when(examExamClassService.getExamExamClassesByExamId(1L)).thenReturn(examClasses);

        mockMvc.perform(get("/bukaoSystem/examClass/getByExamId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examClass1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(examClass1.getExamId()));
    }

    @Test
    public void testGetExamExamClassesByClassId() throws Exception {
        List<ExamExamClass> examClasses = Arrays.asList(examClass2);
        Mockito.when(examExamClassService.getExamExamClassesByClassId(2L)).thenReturn(examClasses);

        mockMvc.perform(get("/bukaoSystem/examClass/getByClassId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examClass2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].classId").value(examClass2.getClassId()));
    }

    @Test
    public void testCreateExamExamClass() throws Exception {
        mockMvc.perform(post("/bukaoSystem/examClass/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examClass1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateExamExamClass() throws Exception {
        mockMvc.perform(post("/bukaoSystem/examClass/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examClass1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteExamExamClass() throws Exception {
        mockMvc.perform(post("/bukaoSystem/examClass/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examClass1)))
                .andExpect(status().isOk());
    }
}
