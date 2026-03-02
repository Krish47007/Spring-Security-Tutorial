package com.krish.demo.controller;

import com.krish.demo.dto.AuthRequest;
import com.krish.demo.entity.UserInfo;
import com.krish.demo.service.JwtService;
import com.krish.demo.service.UserInfoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserInfoController {

    private final UserInfoService service;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @PostMapping("/add")
    public UserInfo addUser(@RequestBody UserInfo userInfo)
    {
        return service.addUser(userInfo);
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestBody AuthRequest authRequest)
    {
       // Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword());
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
                                                        authRequest.getPassword()));
        if(auth.isAuthenticated())
        {
            return jwtService.generateToken(authRequest.getUserName());
        }
        else
            throw new EntityNotFoundException("Invalid credentials!!!");

    }
}
