package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamExamResources;
import com.bukaoSystem.service.ExamExamResourcesService;
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
public class ExamExamResourcesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamExamResourcesService examExamResourcesService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamExamResources examResources1;
    private ExamExamResources examResources2;

    @BeforeEach
    public void setup() {
        examResources1 = new ExamExamResources();
        examResources1.setExamId(1L);
        examResources1.setResourceId(1L);
        examResources1.setCreateTime("2023-01-01 00:00:00");

        examResources2 = new ExamExamResources();
        examResources2.setExamId(2L);
        examResources2.setResourceId(2L);
        examResources2.setCreateTime("2023-02-01 00:00:00");
    }

    @Test
    public void testGetAllExamExamResources() throws Exception {
        List<ExamExamResources> examResources = Arrays.asList(examResources1, examResources2);
        Mockito.when(examExamResourcesService.getAllExamExamResources()).thenReturn(examResources);

        mockMvc.perform(get("/bukaoSystem/examResources")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(examResources1.getExamId()))
                .andExpect(jsonPath("$[1].resourceId").value(examResources2.getResourceId()));
    }

    @Test
    public void testGetExamExamResourcesByExamId() throws Exception {
        List<ExamExamResources> examResources = Arrays.asList(examResources1);
        Mockito.when(examExamResourcesService.getExamExamResourcesByExamId(1L)).thenReturn(examResources);

        mockMvc.perform(get("/bukaoSystem/examResources/getByExamId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examResources1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(examResources1.getExamId()));
    }

    @Test
    public void testGetExamExamResourcesByResourceId() throws Exception {
        List<ExamExamResources> examResources = Arrays.asList(examResources2);
        Mockito.when(examExamResourcesService.getExamExamResourcesByResourceId(2L)).thenReturn(examResources);

        mockMvc.perform(get("/bukaoSystem/examResources/getByResourceId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examResources2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].resourceId").value(examResources2.getResourceId()));
    }

    @Test
    public void testCreateExamExamResources() throws Exception {
        mockMvc.perform(post("/bukaoSystem/examResources/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examResources1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateExamExamResources() throws Exception {
        mockMvc.perform(post("/bukaoSystem/examResources/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examResources1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteExamExamResources() throws Exception {
        mockMvc.perform(post("/bukaoSystem/examResources/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(examResources1)))
                .andExpect(status().isOk());
    }
}
