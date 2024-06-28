package com.bukaoSystem.controller;

import com.bukaoSystem.model.ExamUser;
import com.bukaoSystem.service.ExamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bukaoSystem/users")
public class ExamUserController {

    @Autowired
    private ExamUserService examUserService;

    // 获取所有用户
    @GetMapping
    public List<ExamUser> getAllUsers() {
        return examUserService.getAllUsers();
    }

    // 根据ID获取特定用户
    @GetMapping("/{id}")
    public ExamUser getUserById(@PathVariable Long id) {
        return examUserService.getUserById(id);
    }

    // 创建用户
    @PostMapping("/create")
    public void createUser(@RequestBody ExamUser user) {
        examUserService.saveUser(user);
    }

    // 更新用户
    @PostMapping("/update/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody ExamUser user) {
        user.setId(id); // 确保用户ID正确
        examUserService.updateUser(user);
    }

    // 删除用户
    @PostMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        examUserService.deleteUser(id);
    }
}
