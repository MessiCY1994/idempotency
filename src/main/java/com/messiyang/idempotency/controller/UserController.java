package com.messiyang.idempotency.controller;


import com.messiyang.idempotency.common.ServerResponse;
import com.messiyang.idempotency.pojo.User;
import com.messiyang.idempotency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users")
    public String getAll() {
        List<User> users = userService.getAll();
        return users.toString();
    }

    @GetMapping("{id}")
    public String getOne(@PathVariable Long id) {
        User user = userService.getOne(id);
        if (null != user) {
            return user.toString();
        } else {
            return "not exists";
        }
    }

    @PostMapping("add")
    public String add(@RequestBody User user) {
        userService.add(user);
        return "add success";
    }

    @PutMapping
    public String update(User user) {
        userService.update(user);
        return "nice";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "nice";
    }

    @PostMapping("login")
    public ServerResponse login(@RequestBody Map map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        return userService.login(username, password);
    }

}
