package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamTeacherCourse;
import com.bukaoSystem.service.ExamTeacherCourseService;
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
public class ExamTeacherCourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamTeacherCourseService examTeacherCourseService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamTeacherCourse teacherCourse1;
    private ExamTeacherCourse teacherCourse2;

    @BeforeEach
    public void setup() {
        teacherCourse1 = new ExamTeacherCourse();
        teacherCourse1.setTeacherId(1L);
        teacherCourse1.setCourseId(1L);
        teacherCourse1.setCreateTime("2023-01-01 00:00:00");

        teacherCourse2 = new ExamTeacherCourse();
        teacherCourse2.setTeacherId(2L);
        teacherCourse2.setCourseId(2L);
        teacherCourse2.setCreateTime("2023-02-01 00:00:00");
    }

    @Test
    public void testGetAllExamTeacherCourses() throws Exception {
        List<ExamTeacherCourse> teacherCourses = Arrays.asList(teacherCourse1, teacherCourse2);
        Mockito.when(examTeacherCourseService.getAllExamTeacherCourses()).thenReturn(teacherCourses);

        mockMvc.perform(get("/bukaoSystem/teacherCourse"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].teacherId").value(teacherCourse1.getTeacherId()))
                .andExpect(jsonPath("$[1].teacherId").value(teacherCourse2.getTeacherId()));
    }

    @Test
    public void testGetExamTeacherCoursesByTeacherId() throws Exception {
        List<ExamTeacherCourse> teacherCourses = Arrays.asList(teacherCourse1);
        Mockito.when(examTeacherCourseService.getExamTeacherCoursesByTeacherId(1L)).thenReturn(teacherCourses);

        mockMvc.perform(get("/bukaoSystem/teacherCourse/getByTeacherId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherCourse1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].teacherId").value(teacherCourse1.getTeacherId()));
    }

    @Test
    public void testGetExamTeacherCoursesByCourseId() throws Exception {
        List<ExamTeacherCourse> teacherCourses = Arrays.asList(teacherCourse2);
        Mockito.when(examTeacherCourseService.getExamTeacherCoursesByCourseId(2L)).thenReturn(teacherCourses);

        mockMvc.perform(get("/bukaoSystem/teacherCourse/getByCourseId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherCourse2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].courseId").value(teacherCourse2.getCourseId()));
    }

    @Test
    public void testCreateExamTeacherCourse() throws Exception {
        mockMvc.perform(post("/bukaoSystem/teacherCourse/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherCourse1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateExamTeacherCourse() throws Exception {
        mockMvc.perform(post("/bukaoSystem/teacherCourse/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherCourse1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteExamTeacherCourse() throws Exception {
        mockMvc.perform(post("/bukaoSystem/teacherCourse/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacherCourse1)))
                .andExpect(status().isOk());
    }
}
