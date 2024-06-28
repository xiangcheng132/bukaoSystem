package com.bukaoSystem.dao;

import com.bukaoSystem.modal.ExamUser;

import java.util.List;

public interface ExamUserRepository {
    void save(ExamUser user);
    ExamUser findById(Long id);
    List<ExamUser> findAll();
    void update(ExamUser user);
    void delete(Long id);
}