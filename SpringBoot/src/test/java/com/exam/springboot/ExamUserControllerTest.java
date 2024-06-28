package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.controller.ExamUserController;
import com.bukaoSystem.model.ExamUser;
import com.bukaoSystem.service.ExamUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ExamUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamUserService examUserService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamUser user1;
    private ExamUser user2;

    @BeforeEach
    public void setup() {
        user1 = new ExamUser();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setAccount("account1");
        user1.setPassword("password1");
        user1.setRole("teacher");
        user1.setEmail("user1@example.com");
        user1.setPhone("1234567890");
        user1.setSex("男");
        user1.setCreateTime(LocalDateTime.now().toString());

        user2 = new ExamUser();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setAccount("account2");
        user2.setPassword("password2");
        user2.setRole("student");
        user2.setEmail("user2@example.com");
        user2.setPhone("0987654321");
        user2.setSex("女");
        user2.setCreateTime(LocalDateTime.now().toString());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<ExamUser> users = Arrays.asList(user1, user2);
        Mockito.when(examUserService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value(user1.getUsername()))
                .andExpect(jsonPath("$[1].username").value(user2.getUsername()));
    }

    @Test
    public void testGetUserById() throws Exception {
        Mockito.when(examUserService.getUserById(1L)).thenReturn(user1);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(user1.getUsername()));
    }

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk());

        Mockito.verify(examUserService, Mockito.times(1)).saveUser(Mockito.any(ExamUser.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk());

        Mockito.verify(examUserService, Mockito.times(1)).updateUser(Mockito.any(ExamUser.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/users/delete/1"))
                .andExpect(status().isOk());

        Mockito.verify(examUserService, Mockito.times(1)).deleteUser(1L);
    }
}
