package com.bukaoSystem.controller;

import com.bukaoSystem.exception.AccountAlreadyRegisteredException;
import com.bukaoSystem.model.ExamUser;
import com.bukaoSystem.service.impl.ExamUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/users")
public class ExamUserController {

    @Autowired
    private ExamUserServiceImpl examUserServiceImpl;

    // 获取所有用户
    @GetMapping
    public List<ExamUser> getAllUsers() {
        return examUserServiceImpl.getAllUsers();
    }

    // 根据ID获取特定用户
    @PostMapping("/getById")
    public ExamUser getUserById(@RequestBody ExamUser user) {
        return examUserServiceImpl.getUserById(user.getId());
    }

    // 创建用户
    @PostMapping("/create")
    public ResponseEntity<String> createExamUser(@RequestBody ExamUser examUser) {
        try {
            examUserServiceImpl.saveUser(examUser);
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } catch (AccountAlreadyRegisteredException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    // 更新用户
    @PostMapping("/update")
    public void updateUser(@RequestBody ExamUser user) {
        examUserServiceImpl.updateUser(user);
    }

    // 删除用户
    @PostMapping("/delete")
    public void deleteUser(@RequestBody ExamUser user) {
        examUserServiceImpl.deleteUser(user.getId());
    }

    @PostMapping("/login")
    public ExamUser login(@RequestBody ExamUser user) {
        return examUserServiceImpl.login(user.getAccount());
    }
}
