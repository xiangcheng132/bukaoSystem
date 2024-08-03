package com.bukaoSystem.controller;

import com.bukaoSystem.exception.AccountAlreadyRegisteredException;
import com.bukaoSystem.exception.ForeignKeyConstraintViolationException;
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
    // 根据用户名获取特定用户
    @PostMapping("/getByUsername")
    public List<ExamUser> getUserByUsername(@RequestBody ExamUser user) {
        return examUserServiceImpl.getUserByusername(user.getUsername());
    }
    // 根据用户名和id获取特定用户
    @PostMapping("/getUserByIdAndUsername")
    public ExamUser getUserByIdAndUsername(@RequestBody ExamUser user) {
        return examUserServiceImpl.getUserByIdAndUsername(user.getId(),user.getUsername());
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
    public ResponseEntity<String> deleteUser(@RequestBody ExamUser user) {
        try {
            examUserServiceImpl.deleteUser(user.getId());
            return new ResponseEntity<>("successfully", HttpStatus.CREATED);
        } catch (ForeignKeyConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ExamUser login(@RequestBody ExamUser user) {
        return examUserServiceImpl.login(user.getAccount(),user.getPassword());
    }
}
