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
        examClass1.setId(1L);
        examClass1.setExamId(101L);
        examClass1.setClassId(201L);
        examClass1.setCreateTime("2020-01-01 00:00:00");

        examClass2 = new ExamExamClass();
        examClass2.setId(2L);
        examClass2.setExamId(102L);
        examClass2.setClassId(202L);
        examClass2.setCreateTime("2021-01-01 00:00:00");
    }

    @Test
    public void testGetAllExamExamClasses() throws Exception {
        List<ExamExamClass> examClasses = Arrays.asList(examClass1, examClass2);
        Mockito.when(examExamClassService.getAllExamExamClasses()).thenReturn(examClasses);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/examClass"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(examClass1.getExamId()))
                .andExpect(jsonPath("$[1].examId").value(examClass2.getExamId()));
    }

    @Test
    public void testGetExamExamClassesById() throws Exception {
        Mockito.when(examExamClassService.getExamExamClassesById(1L)).thenReturn(Arrays.asList(examClass1));

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/examClass/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examClass1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(examClass1.getExamId()));
    }

    @Test
    public void testGetExamExamClassesByExamId() throws Exception {
        List<ExamExamClass> examClasses = Arrays.asList(examClass1);
        Mockito.when(examExamClassService.getExamExamClassesByExamId(101L)).thenReturn(examClasses);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/examClass/getByExamId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examClass1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(examClass1.getExamId()));
    }

    @Test
    public void testGetExamExamClassesByClassId() throws Exception {
        List<ExamExamClass> examClasses = Arrays.asList(examClass1);
//        Mockito.when(examExamClassService.getExamExamClassesByClassId(201L)).thenReturn(examClasses);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/examClass/getByClassId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examClass1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(examClass1.getExamId()));
    }

    @Test
    public void testCreateExamExamClass() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/examClass/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examClass1)))
                .andExpect(status().isOk());

        Mockito.verify(examExamClassService, Mockito.times(1)).saveExamExamClass(Mockito.any(ExamExamClass.class));
    }

    @Test
    public void testUpdateExamExamClass() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/examClass/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examClass1)))
                .andExpect(status().isOk());

        Mockito.verify(examExamClassService, Mockito.times(1)).updateExamExamClass(Mockito.any(ExamExamClass.class));
    }

    @Test
    public void testDeleteExamExamClass() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/examClass/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examClass1)))
                .andExpect(status().isOk());

        Mockito.verify(examExamClassService, Mockito.times(1)).deleteExamExamClass(examClass1.getExamId(), examClass1.getClassId());
    }
}
