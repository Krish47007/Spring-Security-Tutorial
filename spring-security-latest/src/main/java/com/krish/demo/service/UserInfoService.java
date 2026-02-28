package com.krish.demo.service;

import com.krish.demo.entity.UserInfo;
import com.krish.demo.repository.UserInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService {

    private final UserInfoRepository repository;
    private PasswordEncoder passwordEncoder;
    public UserInfo addUser(UserInfo userInfo)
    {
        //Encrypt password
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return repository.save(userInfo);
    }

    public UserInfo getUser(long userId)
    {
        return repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s not found",userId)));

    }

}
