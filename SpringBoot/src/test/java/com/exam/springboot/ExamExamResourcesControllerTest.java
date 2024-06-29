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

    private ExamExamResources resource1;
    private ExamExamResources resource2;

    @BeforeEach
    public void setup() {
        resource1 = new ExamExamResources();
        resource1.setId(1L);
        resource1.setExamId(101L);
        resource1.setResourceId(201L);
        resource1.setCreateTime("2020-01-01 00:00:00");

        resource2 = new ExamExamResources();
        resource2.setId(2L);
        resource2.setExamId(102L);
        resource2.setResourceId(202L);
        resource2.setCreateTime("2021-01-01 00:00:00");
    }

    @Test
    public void testGetAllExamExamResources() throws Exception {
        List<ExamExamResources> resources = Arrays.asList(resource1, resource2);
        Mockito.when(examExamResourcesService.getAllExamExamResources()).thenReturn(resources);

        mockMvc.perform(get("/bukaoSystem/examResources"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(resource1.getExamId()))
                .andExpect(jsonPath("$[1].examId").value(resource2.getExamId()));
    }

    @Test
    public void testGetExamExamResourcesById() throws Exception {
        List<ExamExamResources> resources = Arrays.asList(resource1);
        Mockito.when(examExamResourcesService.getExamExamResourcesById(1L)).thenReturn(resources);

        mockMvc.perform(get("/bukaoSystem/examResources/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(resource1.getExamId()));
    }

    @Test
    public void testGetExamExamResourcesByExamId() throws Exception {
        List<ExamExamResources> resources = Arrays.asList(resource1);
        Mockito.when(examExamResourcesService.getExamExamResourcesByExamId(101L)).thenReturn(resources);

        mockMvc.perform(get("/bukaoSystem/examResources/getByExamId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(resource1.getExamId()));
    }

    @Test
    public void testGetExamExamResourcesByResourceId() throws Exception {
        List<ExamExamResources> resources = Arrays.asList(resource1);
        Mockito.when(examExamResourcesService.getExamExamResourcesByResourceId(201L)).thenReturn(resources);

        mockMvc.perform(get("/bukaoSystem/examResources/getByResourceId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(resource1.getExamId()));
    }

    @Test
    public void testCreateExamExamResources() throws Exception {
        mockMvc.perform(post("/bukaoSystem/examResources/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk());

        Mockito.verify(examExamResourcesService, Mockito.times(1)).saveExamExamResources(Mockito.any(ExamExamResources.class));
    }

    @Test
    public void testUpdateExamExamResources() throws Exception {
        mockMvc.perform(post("/bukaoSystem/examResources/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk());

        Mockito.verify(examExamResourcesService, Mockito.times(1)).updateExamExamResources(Mockito.any(ExamExamResources.class));
    }

    @Test
    public void testDeleteExamExamResources() throws Exception {
        mockMvc.perform(post("/bukaoSystem/examResources/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk());

        Mockito.verify(examExamResourcesService, Mockito.times(1)).deleteExamExamResources(resource1.getExamId(), resource1.getResourceId());
    }
}
