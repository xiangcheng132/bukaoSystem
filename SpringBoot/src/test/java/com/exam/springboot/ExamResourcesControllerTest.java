package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamResources;
import com.bukaoSystem.service.ExamResourcesService;
import com.fasterxml.jackson.databind.JsonNode;
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
public class ExamResourcesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamResourcesService examResourcesService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamResources resource1;
    private ExamResources resource2;

    @BeforeEach
    public void setup() {
        resource1 = new ExamResources();
        resource1.setId(1L);
        resource1.setCourseId(1L);
        resource1.setChapterId(1L);
        resource1.setQuestion("What is the capital of France?");
        resource1.setType(ExamResources.Type.single_choice);
        resource1.setOptions(objectMapper.createObjectNode().put("A", "Paris").put("B", "London").put("C", "Berlin").put("D", "Madrid"));
        resource1.setKey("A");
        resource1.setAnalysis("Paris is the capital of France.");
        resource1.setScore(1.0);
        resource1.setCreateTime("2020-01-01 00:00:00");

        resource2 = new ExamResources();
        resource2.setId(2L);
        resource2.setCourseId(2L);
        resource2.setChapterId(2L);
        resource2.setQuestion("What is the capital of Germany?");
        resource2.setType(ExamResources.Type.single_choice);
        resource2.setOptions(objectMapper.createObjectNode().put("A", "Paris").put("B", "London").put("C", "Berlin").put("D", "Madrid"));
        resource2.setKey("C");
        resource2.setAnalysis("Berlin is the capital of Germany.");
        resource2.setScore(1.0);
        resource2.setCreateTime("2021-01-01 00:00:00");
    }

    @Test
    public void testGetAllExamResources() throws Exception {
        List<ExamResources> resources = Arrays.asList(resource1, resource2);
        Mockito.when(examResourcesService.getExamResourcesByCourseId(null,"ASC")).thenReturn(resources);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/resources"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].question").value(resource1.getQuestion()))
                .andExpect(jsonPath("$[1].question").value(resource2.getQuestion()));
    }

    @Test
    public void testGetExamResourcesByCourseId() throws Exception {
        List<ExamResources> resources = Arrays.asList(resource1);
        Mockito.when(examResourcesService.getExamResourcesByCourseId(1L, "ASC")).thenReturn(resources);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/resources/getByCourseId")
                        .param("sort", "ASC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].question").value(resource1.getQuestion()));
    }

    @Test
    public void testGetExamResourcesByChapterId() throws Exception {
        List<ExamResources> resources = Arrays.asList(resource1);
        Mockito.when(examResourcesService.getExamResourcesByChapterId(1L, "ASC")).thenReturn(resources);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/resources/getByChapterId")
                        .param("sort", "ASC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].question").value(resource1.getQuestion()));
    }

    @Test
    public void testGetExamResourcesById() throws Exception {
        Mockito.when(examResourcesService.getExamResourcesById(1L)).thenReturn(resource1);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/resources/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.question").value(resource1.getQuestion()));
    }

    @Test
    public void testCreateExamResources() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/resources/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk());

        Mockito.verify(examResourcesService, Mockito.times(1)).saveExamResources(Mockito.any(ExamResources.class));
    }

    @Test
    public void testUpdateExamResources() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/resources/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk());

        Mockito.verify(examResourcesService, Mockito.times(1)).updateExamResources(Mockito.any(ExamResources.class));
    }

    @Test
    public void testDeleteExamResources() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/resources/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk());

        Mockito.verify(examResourcesService, Mockito.times(1)).deleteExamResources(resource1.getId());
    }
}
