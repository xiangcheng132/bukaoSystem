package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamAnswerSheet;
import com.bukaoSystem.service.ExamAnswerSheetService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ExamAnswerSheetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamAnswerSheetService examAnswerSheetService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamAnswerSheet answerSheet1;
    private ExamAnswerSheet answerSheet2;

    @BeforeEach
    public void setup() {
        answerSheet1 = new ExamAnswerSheet();
        answerSheet1.setId(1L);
        answerSheet1.setExamId(101L);
        answerSheet1.setUserId(201L);
        answerSheet1.setScore(85);
        answerSheet1.setCreateTime("2020-01-01 00:00:00");

        answerSheet2 = new ExamAnswerSheet();
        answerSheet2.setId(2L);
        answerSheet2.setExamId(102L);
        answerSheet2.setUserId(202L);
        answerSheet2.setScore(90);
        answerSheet2.setCreateTime("2021-01-01 00:00:00");
    }

    @Test
    public void testGetAllExamAnswerSheets() throws Exception {
        List<ExamAnswerSheet> answerSheets = Arrays.asList(answerSheet1, answerSheet2);
        Mockito.when(examAnswerSheetService.getAllExamAnswerSheets()).thenReturn(answerSheets);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/answerSheet"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(answerSheet1.getExamId()))
                .andExpect(jsonPath("$[1].examId").value(answerSheet2.getExamId()));
    }

    @Test
    public void testGetExamAnswerSheetsById() throws Exception {
        List<ExamAnswerSheet> answerSheets = Arrays.asList(answerSheet1);
        Mockito.when(examAnswerSheetService.getExamAnswerSheetsById(1L)).thenReturn(answerSheets);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/answerSheet/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSheet1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(answerSheet1.getExamId()));
    }

    @Test
    public void testGetExamAnswerSheetsByExamId() throws Exception {
        List<ExamAnswerSheet> answerSheets = Arrays.asList(answerSheet1);
        Mockito.when(examAnswerSheetService.getExamAnswerSheetsByExamId(101L)).thenReturn(answerSheets);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/answerSheet/getByExamId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSheet1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(answerSheet1.getExamId()));
    }

    @Test
    public void testGetExamAnswerSheetsByUserId() throws Exception {
        List<ExamAnswerSheet> answerSheets = Arrays.asList(answerSheet1);
        Mockito.when(examAnswerSheetService.getExamAnswerSheetsByUserId(201L)).thenReturn(answerSheets);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/answerSheet/getByUserId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSheet1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].examId").value(answerSheet1.getExamId()));
    }

    @Test
    public void testCreateExamAnswerSheet() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/answerSheet/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSheet1)))
                .andExpect(status().isOk());

        Mockito.verify(examAnswerSheetService, Mockito.times(1)).saveExamAnswerSheet(Mockito.any(ExamAnswerSheet.class));
    }

    @Test
    public void testUpdateExamAnswerSheet() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/answerSheet/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSheet1)))
                .andExpect(status().isOk());

        Mockito.verify(examAnswerSheetService, Mockito.times(1)).updateExamAnswerSheet(Mockito.any(ExamAnswerSheet.class));
    }

    @Test
    public void testDeleteExamAnswerSheet() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/answerSheet/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(answerSheet1)))
                .andExpect(status().isOk());

        Mockito.verify(examAnswerSheetService, Mockito.times(1)).deleteExamAnswerSheet(answerSheet1.getId());
    }
}
