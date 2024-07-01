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
    public void saveUser(ExamUser user) {
        examUserDao.save(user);
    }
    @Override
    public ExamUser getUserById(Long id) {
        return examUserDao.findById(id);
    }
    @Override
    public List<ExamUser> getAllUsers() {
        return examUserDao.findAll();
    }
    @Override
    public void updateUser(ExamUser user) {
        examUserDao.update(user);
    }
    @Override
    public void deleteUser(Long id) {
        examUserDao.delete(id);
    }

    public boolean login(String account,String password) {
        ExamUser examUser = examUserDao.login(account);
        if (examUser == null)
            return false;
        if (examUser.getPassword().equals(password))
            return true;

        return false;
    }
}
