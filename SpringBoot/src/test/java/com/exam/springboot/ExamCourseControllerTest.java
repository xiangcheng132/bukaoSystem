package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.controller.ExamCourseController;
import com.bukaoSystem.model.ExamCourse;
import com.bukaoSystem.service.ExamCourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ExamCourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamCourseService examCourseService;

    private ExamCourse examCourse;
    private List<ExamCourse> examCourseList;

    @BeforeEach
    public void setUp() {
        examCourse = new ExamCourse();
        examCourse.setId(1L);
        examCourse.setName("Course 1");

        examCourseList = Arrays.asList(examCourse);
    }

    @Test
    public void testGetAllExamCourses() throws Exception {
        when(examCourseService.getAllExamCourses()).thenReturn(examCourseList);

        mockMvc.perform(get("/bukaoSystem/course")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(examCourse.getId()))
                .andExpect(jsonPath("$[0].name").value(examCourse.getName()));

        verify(examCourseService, times(1)).getAllExamCourses();
    }

    @Test
    public void testCreateExamCourse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String examCourseJson = objectMapper.writeValueAsString(examCourse);

        mockMvc.perform(post("/bukaoSystem/course/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(examCourseJson))
                .andExpect(status().isOk());

        verify(examCourseService, times(1)).saveExamCourse(any(ExamCourse.class));
    }

    @Test
    public void testGetExamCourseById() throws Exception {
        when(examCourseService.getExamCourseById(examCourse.getId())).thenReturn(examCourse);

        ObjectMapper objectMapper = new ObjectMapper();
        String examCourseJson = objectMapper.writeValueAsString(examCourse);

        mockMvc.perform(get("/bukaoSystem/course/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(examCourseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(examCourse.getId()))
                .andExpect(jsonPath("$.name").value(examCourse.getName()));

        verify(examCourseService, times(1)).getExamCourseById(examCourse.getId());
    }

    @Test
    public void testGetExamCourseByName() throws Exception {
        when(examCourseService.getExamCoursesByName(examCourse.getName())).thenReturn(examCourseList);

        ObjectMapper objectMapper = new ObjectMapper();
        String examCourseJson = objectMapper.writeValueAsString(examCourse);

        mockMvc.perform(get("/bukaoSystem/course/getByName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(examCourseJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(examCourse.getId()))
                .andExpect(jsonPath("$[0].name").value(examCourse.getName()));

        verify(examCourseService, times(1)).getExamCoursesByName(examCourse.getName());
    }

    @Test
    public void testUpdateExamCourse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String examCourseJson = objectMapper.writeValueAsString(examCourse);

        mockMvc.perform(post("/bukaoSystem/course/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(examCourseJson))
                .andExpect(status().isOk());

        verify(examCourseService, times(1)).updateExamCourse(any(ExamCourse.class));
    }

    @Test
    public void testDeleteExamCourse() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String examCourseJson = objectMapper.writeValueAsString(examCourse);

        mockMvc.perform(post("/bukaoSystem/course/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(examCourseJson))
                .andExpect(status().isOk());

        verify(examCourseService, times(1)).deleteExamCourse(examCourse.getId());
    }
}
