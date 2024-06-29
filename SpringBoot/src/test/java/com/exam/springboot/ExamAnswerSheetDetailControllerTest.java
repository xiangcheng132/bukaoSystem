package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamAnswerSheetDetail;
import com.bukaoSystem.service.ExamAnswerSheetDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ExamAnswerSheetDetailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamAnswerSheetDetailService examAnswerSheetDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamAnswerSheetDetail answerSheetDetail1;
    private ExamAnswerSheetDetail answerSheetDetail2;

    @BeforeEach
    public void setup() {
        answerSheetDetail1 = new ExamAnswerSheetDetail();
        answerSheetDetail1.setId(1L);
        answerSheetDetail1.setAnswerId(101L);
        answerSheetDetail1.setResourceId(201L);
        answerSheetDetail1.setUserKey("user1");
        answerSheetDetail1.setIsTrue(true);
        answerSheetDetail1.setCreateTime("2020-01-01 00:00:00");

        answerSheetDetail2 = new ExamAnswerSheetDetail();
        answerSheetDetail2.setId(2L);
        answerSheetDetail2.setAnswerId(102L);
        answerSheetDetail2.setResourceId(202L);
        answerSheetDetail2.setUserKey("user2");
        answerSheetDetail2.setIsTrue(false);
        answerSheetDetail2.setCreateTime("2021-01-01 00:00:00");
    }

    @Test
    public void testGetAllExamAnswerSheetDetails() throws Exception {
        List<ExamAnswerSheetDetail> answerSheetDetails = Arrays.asList(answerSheetDetail1, answerSheetDetail2);
        Mockito.when(examAnswerSheetDetailService.getAllExamAnswerSheetDetails()).thenReturn(answerSheetDetails);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/answerSheetDetail"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userKey").value(answerSheetDetail1.getUserKey()))
                .andExpect(jsonPath("$[1].userKey").value(answerSheetDetail2.getUserKey()));
    }

    @Test
    public void testGetExamAnswerSheetDetailsById() throws Exception {
        List<ExamAnswerSheetDetail> answerSheetDetails = Arrays.asList(answerSheetDetail1);
        Mockito.when(examAnswerSheetDetailService.getExamAnswerSheetDetailsById(1L)).thenReturn(answerSheetDetails);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/answerSheetDetail/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSheetDetail1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userKey").value(answerSheetDetail1.getUserKey()));
    }

    @Test
    public void testGetExamAnswerSheetDetailsByAnswerId() throws Exception {
        List<ExamAnswerSheetDetail> answerSheetDetails = Arrays.asList(answerSheetDetail1);
        Mockito.when(examAnswerSheetDetailService.getExamAnswerSheetDetailsByAnswerId(101L)).thenReturn(answerSheetDetails);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/answerSheetDetail/getByAnswerId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSheetDetail1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userKey").value(answerSheetDetail1.getUserKey()));
    }

    @Test
    public void testGetExamAnswerSheetDetailsByResourceId() throws Exception {
        List<ExamAnswerSheetDetail> answerSheetDetails = Arrays.asList(answerSheetDetail1);
        Mockito.when(examAnswerSheetDetailService.getExamAnswerSheetDetailsByResourceId(201L)).thenReturn(answerSheetDetails);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/answerSheetDetail/getByResourceId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSheetDetail1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userKey").value(answerSheetDetail1.getUserKey()));
    }

    @Test
    public void testCreateExamAnswerSheetDetail() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/answerSheetDetail/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSheetDetail1)))
                .andExpect(status().isOk());

        Mockito.verify(examAnswerSheetDetailService, Mockito.times(1)).saveExamAnswerSheetDetail(Mockito.any(ExamAnswerSheetDetail.class));
    }

    @Test
    public void testUpdateExamAnswerSheetDetail() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/answerSheetDetail/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSheetDetail1)))
                .andExpect(status().isOk());

        Mockito.verify(examAnswerSheetDetailService, Mockito.times(1)).updateExamAnswerSheetDetail(Mockito.any(ExamAnswerSheetDetail.class));
    }

    @Test
    public void testDeleteExamAnswerSheetDetail() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/answerSheetDetail/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSheetDetail1)))
                .andExpect(status().isOk());

        Mockito.verify(examAnswerSheetDetailService, Mockito.times(1)).deleteExamAnswerSheetDetail(answerSheetDetail1.getId());
    }
}
