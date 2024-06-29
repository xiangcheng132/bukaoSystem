package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamClass;
import com.bukaoSystem.service.ExamClassService;
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
public class ExamClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamClassService examClassService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamClass class1;
    private ExamClass class2;

    @BeforeEach
    public void setup() {
        class1 = new ExamClass();
        class1.setId(1L);
        class1.setName("Class 1");
        class1.setComment("Class 1 Comment");
        class1.setCreateTime("2020-01-01 00:00:00");

        class2 = new ExamClass();
        class2.setId(2L);
        class2.setName("Class 2");
        class2.setComment("Class 2 Comment");
        class2.setCreateTime("2021-01-01 00:00:00");
    }

    @Test
    public void testGetAllExamClasses() throws Exception {
        List<ExamClass> classes = Arrays.asList(class1, class2);
        Mockito.when(examClassService.getAllExamClasses()).thenReturn(classes);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/class"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(class1.getName()))
                .andExpect(jsonPath("$[1].name").value(class2.getName()));
    }

    @Test
    public void testGetExamClassById() throws Exception {
        Mockito.when(examClassService.getExamClassById(1L)).thenReturn(class1);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/class/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(class1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(class1.getName()));
    }

    @Test
    public void testCreateExamClass() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/class/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(class1)))
                .andExpect(status().isOk());

        Mockito.verify(examClassService, Mockito.times(1)).saveExamClass(Mockito.any(ExamClass.class));
    }

    @Test
    public void testUpdateExamClass() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/class/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(class1)))
                .andExpect(status().isOk());

        Mockito.verify(examClassService, Mockito.times(1)).updateExamClass(Mockito.any(ExamClass.class));
    }

    @Test
    public void testDeleteExamClass() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/class/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(class1)))
                .andExpect(status().isOk());

        Mockito.verify(examClassService, Mockito.times(1)).deleteExamClass(class1.getId());
    }
}
