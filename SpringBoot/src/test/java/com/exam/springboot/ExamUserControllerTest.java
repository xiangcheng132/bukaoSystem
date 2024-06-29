package com.exam.springboot;

import com.bukaoSystem.Application;
import com.bukaoSystem.model.ExamUser;
import com.bukaoSystem.service.impl.ExamUserServiceImpl;
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
public class ExamUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamUserServiceImpl examUserServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private ExamUser user1;
    private ExamUser user2;

    @BeforeEach
    public void setup() {
        user1 = new ExamUser();
        user1.setId(1L);
        user1.setUsername("John Doe");
        user1.setAccount("johndoe");
        user1.setPassword("password123");
        user1.setRole("student");
        user1.setEmail("john.doe@example.com");
        user1.setPhone("1234567890");
        user1.setSex("male");
        user1.setCreateTime("2020-01-01 00:00:00");

        user2 = new ExamUser();
        user2.setId(2L);
        user2.setUsername("Jane Doe");
        user2.setAccount("janedoe");
        user2.setPassword("password123");
        user2.setRole("teacher");
        user2.setEmail("jane.doe@example.com");
        user2.setPhone("0987654321");
        user2.setSex("female");
        user2.setCreateTime("2020-01-01 00:00:00");
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<ExamUser> users = Arrays.asList(user1, user2);
        Mockito.when(examUserServiceImpl.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].username").value(user1.getUsername()))
                .andExpect(jsonPath("$[1].username").value(user2.getUsername()));
    }

    @Test
    public void testGetUserById() throws Exception {
        Mockito.when(examUserServiceImpl.getUserById(1L)).thenReturn(user1);

        mockMvc.perform(get("http://localhost:8080/bukaoSystem/users/getById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
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

        Mockito.verify(examUserServiceImpl, Mockito.times(1)).saveUser(Mockito.any(ExamUser.class));
    }

    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk());

        Mockito.verify(examUserServiceImpl, Mockito.times(1)).updateUser(Mockito.any(ExamUser.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(post("http://localhost:8080/bukaoSystem/users/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk());

        Mockito.verify(examUserServiceImpl, Mockito.times(1)).deleteUser(user1.getId());
    }
}
