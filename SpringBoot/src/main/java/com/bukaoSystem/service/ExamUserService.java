package com.bukaoSystem.service;

import com.bukaoSystem.dao.ExamUserRepository;
import com.bukaoSystem.model.ExamUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamUserService {

    @Autowired
    private ExamUserRepository examUserRepository;

    public void saveUser(ExamUser user) {
        examUserRepository.save(user);
    }

    public ExamUser getUserById(Long id) {
        return examUserRepository.findById(id);
    }

    public List<ExamUser> getAllUsers() {
        return examUserRepository.findAll();
    }

    public void updateUser(ExamUser user) {
        examUserRepository.update(user);
    }

    public void deleteUser(Long id) {
        examUserRepository.delete(id);
    }
}
