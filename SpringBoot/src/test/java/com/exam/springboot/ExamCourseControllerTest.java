package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamCourse;
import com.bukaoSystem.service.ExamCourseService;
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
public class ExamCourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamCourseService examCourseService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamCourse course1;
    private ExamCourse course2;

    @BeforeEach
    public void setup() {
        course1 = new ExamCourse();
        course1.setId(1L);
        course1.setName("Course 1");
        course1.setchapter(1L);
        course1.setComment("Comment 1");
        course1.setCreateTime("2020-01-01 00:00:00");

        course2 = new ExamCourse();
        course2.setId(2L);
        course2.setName("Course 2");
        course2.setchapter(2L);
        course2.setComment("Comment 2");
        course2.setCreateTime("2021-01-01 00:00:00");
    }

    @Test
    public void testGetAllExamCourses() throws Exception {
        List<ExamCourse> courses = Arrays.asList(course1, course2);
        Mockito.when(examCourseService.getAllExamCourses()).thenReturn(courses);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/course"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(course1.getName()))
                .andExpect(jsonPath("$[1].name").value(course2.getName()));
    }

    @Test
    public void testCreateExamCourse() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/course/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course1)))
                .andExpect(status().isOk());

        Mockito.verify(examCourseService, Mockito.times(1)).saveExamCourse(Mockito.any(ExamCourse.class));
    }

    @Test
    public void testGetExamCourseById() throws Exception {
        Mockito.when(examCourseService.getExamCourseById(1L)).thenReturn(course1);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/course/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(course1.getName()));
    }

    @Test
    public void testGetExamCourseByChapter() throws Exception {
        List<ExamCourse> courses = Arrays.asList(course1, course2);
        Mockito.when(examCourseService.getExamCourseBychapter(course1.getchapter())).thenReturn(courses);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/course/getByChapterId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value(course1.getName()))
                .andExpect(jsonPath("$[1].name").value(course2.getName()));
    }

    @Test
    public void testUpdateExamCourse() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/course/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course1)))
                .andExpect(status().isOk());

        Mockito.verify(examCourseService, Mockito.times(1)).updateExamCourse(Mockito.any(ExamCourse.class));
    }

    @Test
    public void testDeleteExamCourse() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/course/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course1)))
                .andExpect(status().isOk());

        Mockito.verify(examCourseService, Mockito.times(1)).deleteExamCourse(course1.getId());
    }
}
