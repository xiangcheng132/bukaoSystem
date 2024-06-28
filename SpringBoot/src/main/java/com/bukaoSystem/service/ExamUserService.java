package com.bukaoSystem.service;

import com.bukaoSystem.model.ExamUser;

import java.util.List;

public interface  ExamUserService {
    public void saveUser(ExamUser user);
    public ExamUser getUserById(Long id);

    public List<ExamUser> getAllUsers();

    public void updateUser(ExamUser user);

    public void deleteUser(Long id);
}
