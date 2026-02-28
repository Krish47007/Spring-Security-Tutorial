package com.krish.demo.controller;

import com.krish.demo.entity.UserInfo;
import com.krish.demo.service.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserInfoController {

    private final UserInfoService service;

    @PostMapping("/add")
    public UserInfo addUser(@RequestBody UserInfo userInfo)
    {
        return service.addUser(userInfo);
    }
}
