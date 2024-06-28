package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamUserRepository;
import com.bukaoSystem.model.ExamUser;
import com.bukaoSystem.service.ExamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamUserServiceImpl implements ExamUserService {

    @Autowired
    private ExamUserRepository examUserRepository;

    @Override
    public void saveUser(ExamUser user) {
        examUserRepository.save(user);
    }
    @Override
    public ExamUser getUserById(Long id) {
        return examUserRepository.findById(id);
    }
    @Override
    public List<ExamUser> getAllUsers() {
        return examUserRepository.findAll();
    }
    @Override
    public void updateUser(ExamUser user) {
        examUserRepository.update(user);
    }
    @Override
    public void deleteUser(Long id) {
        examUserRepository.delete(id);
    }
}
