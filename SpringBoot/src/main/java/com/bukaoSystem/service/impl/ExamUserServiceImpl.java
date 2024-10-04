package com.bukaoSystem.service.impl;

import com.bukaoSystem.dao.ExamUserDao;
import com.bukaoSystem.model.ExamUser;
import com.bukaoSystem.service.ExamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamUserServiceImpl implements ExamUserService {

    @Autowired
    private ExamUserDao examUserDao;

    @Override
    public Long saveUser(ExamUser user) {
        return examUserDao.save(user);
    }

    @Override
    public ExamUser getUserById(Long id) {
        return examUserDao.findById(id);
    }
    @Override
    public List<ExamUser> getUserByusername(String username) {
        return examUserDao.findByUsername(username);
    }
    @Override
    public ExamUser getUserByIdAndUsername(Long id, String username) {
        return examUserDao.findByIdAndUsername(id, username);
    }
    @Override
    public List<ExamUser> getAllUsers() {
        return examUserDao.findAll();
    }
    @Override
    public List<ExamUser> findAllStudent(Long classId) {
        return examUserDao.findAllStudent(classId);
    }
    @Override
    public void updateUser(ExamUser user) {
        examUserDao.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        examUserDao.delete(id);
    }

    public ExamUser login(String account, String password) {
        ExamUser examUser = examUserDao.login(account);
        if (examUser != null && examUser.getPassword().equals(password))
            return examUser;

        return null;
    }
}
