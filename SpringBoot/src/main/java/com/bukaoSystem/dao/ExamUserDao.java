package com.bukaoSystem.dao;

import com.bukaoSystem.model.ExamUser;

import java.util.List;

public interface ExamUserDao {
    Long save(ExamUser user);
    ExamUser findById(Long id);

    List<ExamUser> findByUsername(String username);

    ExamUser findByIdAndUsername(Long id, String username);
    List<ExamUser>  findAllStudent(Long classId);

    List<ExamUser> findAll();
    void update(ExamUser user);
    void delete(Long id);

    ExamUser login(String account);
}