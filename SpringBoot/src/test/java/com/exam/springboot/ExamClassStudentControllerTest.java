package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamClassStudent;
import com.bukaoSystem.service.ExamClassStudentService;
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
public class ExamClassStudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamClassStudentService examClassStudentService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamClassStudent classStudent1;
    private ExamClassStudent classStudent2;

    @BeforeEach
    public void setup() {
        classStudent1 = new ExamClassStudent();
        classStudent1.setId(1L);
        classStudent1.setClassId(101L);
        classStudent1.setStudentId(201L);
        classStudent1.setCreateTime("2020-01-01 00:00:00");

        classStudent2 = new ExamClassStudent();
        classStudent2.setId(2L);
        classStudent2.setClassId(102L);
        classStudent2.setStudentId(202L);
        classStudent2.setCreateTime("2021-01-01 00:00:00");
    }

    @Test
    public void testGetAllExamClassStudents() throws Exception {
        List<ExamClassStudent> classStudents = Arrays.asList(classStudent1, classStudent2);
        Mockito.when(examClassStudentService.getAllExamClassStudents()).thenReturn(classStudents);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/classStudent"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].classId").value(classStudent1.getClassId()))
                .andExpect(jsonPath("$[1].classId").value(classStudent2.getClassId()));
    }

    @Test
    public void testGetExamClassStudentsById() throws Exception {
        List<ExamClassStudent> classStudents = Arrays.asList(classStudent1);
        Mockito.when(examClassStudentService.getExamClassStudentsById(1L)).thenReturn(classStudents);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/classStudent/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classStudent1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].classId").value(classStudent1.getClassId()));
    }

    @Test
    public void testGetExamClassStudentsByClassId() throws Exception {
        List<ExamClassStudent> classStudents = Arrays.asList(classStudent1);
        Mockito.when(examClassStudentService.getExamClassStudentsByClassId(101L)).thenReturn(classStudents);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/classStudent/getByClassId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classStudent1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].classId").value(classStudent1.getClassId()));
    }

    @Test
    public void testGetExamClassStudentsByStudentId() throws Exception {
        List<ExamClassStudent> classStudents = Arrays.asList(classStudent1);
        Mockito.when(examClassStudentService.getExamClassStudentsByStudentId(201L)).thenReturn(classStudents);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/classStudent/getByStudentId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classStudent1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].classId").value(classStudent1.getClassId()));
    }

    @Test
    public void testCreateExamClassStudent() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/classStudent/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classStudent1)))
                .andExpect(status().isOk());

        Mockito.verify(examClassStudentService, Mockito.times(1)).saveExamClassStudent(Mockito.any(ExamClassStudent.class));
    }

    @Test
    public void testUpdateExamClassStudent() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/classStudent/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classStudent1)))
                .andExpect(status().isOk());

        Mockito.verify(examClassStudentService, Mockito.times(1)).updateExamClassStudent(Mockito.any(ExamClassStudent.class));
    }

    @Test
    public void testDeleteExamClassStudent() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/classStudent/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classStudent1)))
                .andExpect(status().isOk());

        Mockito.verify(examClassStudentService, Mockito.times(1)).deleteExamClassStudent(classStudent1.getId());
    }
}
