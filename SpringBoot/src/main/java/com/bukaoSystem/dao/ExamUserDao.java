package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamUser;

import java.util.List;

public interface ExamUserDao {
    void save(ExamUser user);
    ExamUser findById(Long id);
    List<ExamUser> findAll();
    void update(ExamUser user);
    void delete(Long id);
}