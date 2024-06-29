package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamCourseClass;
import com.bukaoSystem.service.ExamCourseClassService;
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
public class ExamCourseClassTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamCourseClassService examCourseClassService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamCourseClass courseClass1;
    private ExamCourseClass courseClass2;

    @BeforeEach
    public void setup() {
        courseClass1 = new ExamCourseClass();
        courseClass1.setId(1L);
        courseClass1.setClassId(1L);
        courseClass1.setCourseId(1L);
        courseClass1.setCreateTime("2020-01-01 00:00:00");

        courseClass2 = new ExamCourseClass();
        courseClass2.setId(2L);
        courseClass2.setClassId(2L);
        courseClass2.setCourseId(2L);
        courseClass2.setCreateTime("2021-01-01 00:00:00");
    }

    @Test
    public void testGetAllExamCourseClasses() throws Exception {
        List<ExamCourseClass> courseClasses = Arrays.asList(courseClass1, courseClass2);
        Mockito.when(examCourseClassService.getAllExamCourseClasses()).thenReturn(courseClasses);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/courseClass"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].classId").value(courseClass1.getClassId()))
                .andExpect(jsonPath("$[1].classId").value(courseClass2.getClassId()));
    }

    @Test
    public void testGetExamCourseClassById() throws Exception {
        Mockito.when(examCourseClassService.getExamCourseClassById(1L)).thenReturn(courseClass1);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/courseClass/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseClass1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.classId").value(courseClass1.getClassId()));
    }

    @Test
    public void testCreateExamCourseClass() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/courseClass/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseClass1)))
                .andExpect(status().isOk());

        Mockito.verify(examCourseClassService, Mockito.times(1)).saveExamCourseClass(Mockito.any(ExamCourseClass.class));
    }

    @Test
    public void testUpdateExamCourseClass() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/courseClass/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseClass1)))
                .andExpect(status().isOk());

        Mockito.verify(examCourseClassService, Mockito.times(1)).updateExamCourseClass(Mockito.any(ExamCourseClass.class));
    }

    @Test
    public void testDeleteExamCourseClass() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/courseClass/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseClass1)))
                .andExpect(status().isOk());

        Mockito.verify(examCourseClassService, Mockito.times(1)).deleteExamCourseClass(courseClass1.getId());
    }
}
