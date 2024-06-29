package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamClassTeacher;
import com.bukaoSystem.service.ExamClassTeacherService;
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
public class ExamClassTeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamClassTeacherService examClassTeacherService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamClassTeacher classTeacher1;
    private ExamClassTeacher classTeacher2;

    @BeforeEach
    public void setup() {
        classTeacher1 = new ExamClassTeacher();
        classTeacher1.setId(1L);
        classTeacher1.setClassId(101L);
        classTeacher1.setTeacherId(201L);

        classTeacher2 = new ExamClassTeacher();
        classTeacher2.setId(2L);
        classTeacher2.setClassId(102L);
        classTeacher2.setTeacherId(202L);
    }

    @Test
    public void testGetAllExamClassTeachers() throws Exception {
        List<ExamClassTeacher> classTeachers = Arrays.asList(classTeacher1, classTeacher2);
        Mockito.when(examClassTeacherService.getAllExamClassTeachers()).thenReturn(classTeachers);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/classTeacher"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].classId").value(classTeacher1.getClassId()))
                .andExpect(jsonPath("$[1].classId").value(classTeacher2.getClassId()));
    }

    @Test
    public void testGetExamClassTeacherById() throws Exception {
        Mockito.when(examClassTeacherService.getExamClassTeacherById(1L)).thenReturn(classTeacher1);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/classTeacher/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classTeacher1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.classId").value(classTeacher1.getClassId()));
    }

    @Test
    public void testGetExamClassTeachersByClassId() throws Exception {
        List<ExamClassTeacher> classTeachers = Arrays.asList(classTeacher1);
        Mockito.when(examClassTeacherService.getExamClassTeachersByClassId(101L)).thenReturn(classTeachers);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/classTeacher/getByClassId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classTeacher1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].classId").value(classTeacher1.getClassId()));
    }

    @Test
    public void testCreateExamClassTeacher() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/classTeacher/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classTeacher1)))
                .andExpect(status().isOk());

        Mockito.verify(examClassTeacherService, Mockito.times(1)).saveExamClassTeacher(Mockito.any(ExamClassTeacher.class));
    }

    @Test
    public void testUpdateExamClassTeacher() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/classTeacher/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classTeacher1)))
                .andExpect(status().isOk());

        Mockito.verify(examClassTeacherService, Mockito.times(1)).updateExamClassTeacher(Mockito.any(ExamClassTeacher.class));
    }

    @Test
    public void testDeleteExamClassTeacher() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/classTeacher/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classTeacher1)))
                .andExpect(status().isOk());

        Mockito.verify(examClassTeacherService, Mockito.times(1)).deleteExamClassTeacher(classTeacher1.getId());
    }
}
