package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamUser;

import java.util.List;

public interface  ExamUserService {
    Long saveUser(ExamUser user);
    ExamUser getUserById(Long id);

    List<ExamUser> getUserByusername(String username);

    ExamUser getUserByIdAndUsername(Long id, String username);

    List<ExamUser> getAllUsers();

    List<ExamUser> findAllStudent(Long classId);

    void updateUser(ExamUser user);

    void deleteUser(Long id);
}
