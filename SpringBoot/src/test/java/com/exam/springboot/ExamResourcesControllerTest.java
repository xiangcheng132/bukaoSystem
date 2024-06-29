package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamResources;
import com.bukaoSystem.service.impl.ExamResourcesServiceImpl;
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
    private ExamResourcesServiceImpl examResourcesServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamResources resource1;
    private ExamResources resource2;

    @BeforeEach
    public void setup() throws Exception {
        resource1 = new ExamResources();
        resource1.setId(1L);
        resource1.setCourseId(101L);
        resource1.setQuestion("What is the capital of France?");
        resource1.setType(ExamResources.Type.SINGLE_CHOICE);

        JsonNode options1 = objectMapper.readTree("{\"A\":\"Paris\",\"B\":\"London\",\"C\":\"Berlin\",\"D\":\"Madrid\"}");
        resource1.setOptions(options1);

        resource1.setKey("A");
        resource1.setAnalysis("Paris is the capital of France.");
        resource1.setScore(5.0);
        resource1.setCreateTime("2024-06-28 18:30:00");

        resource2 = new ExamResources();
        resource2.setId(2L);
        resource2.setCourseId(102L);
        resource2.setQuestion("Is the earth flat?");
        resource2.setType(ExamResources.Type.TRUE_FALSE);

        JsonNode options2 = objectMapper.nullNode();
        resource2.setOptions(options2);

        resource2.setKey("False");
        resource2.setAnalysis("The earth is round.");
        resource2.setScore(5.0);
        resource2.setCreateTime("2024-06-28 18:30:00");
    }


    @Test
    public void testGetResourceById() throws Exception {
        Mockito.when(examResourcesServiceImpl.getExamResourcesById(1L)).thenReturn(resource1);

        mockMvc.perform(get("/bukaoSystem/resources/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.question").value(resource1.getQuestion()));
    }

    @Test
    public void testCreateResource() throws Exception {
        mockMvc.perform(post("/bukaoSystem/resources/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk());

        Mockito.verify(examResourcesServiceImpl, Mockito.times(1)).saveExamResources(Mockito.any(ExamResources.class));
    }

    @Test
    public void testUpdateResource() throws Exception {
        mockMvc.perform(post("/bukaoSystem/resources/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resource1)))
                .andExpect(status().isOk());

        Mockito.verify(examResourcesServiceImpl, Mockito.times(1)).updateExamResources(Mockito.any(ExamResources.class));
    }

    @Test
    public void testDeleteResource() throws Exception {
        mockMvc.perform(post("/bukaoSystem/resources/delete/1"))
                .andExpect(status().isOk());

        Mockito.verify(examResourcesServiceImpl, Mockito.times(1)).deleteExamResources(1L);
    }
}
