package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamUser;

import java.util.List;

public interface  ExamUserService {
    public void saveUser(ExamUser user);
    public ExamUser getUserById(Long id);

    List<ExamUser> getUserByusername(String username);

    ExamUser getUserByIdAndUsername(Long id, String username);

    public List<ExamUser> getAllUsers();

    List<ExamUser> findAllStudent(Long classId);

    public void updateUser(ExamUser user);

    public void deleteUser(Long id);
}
