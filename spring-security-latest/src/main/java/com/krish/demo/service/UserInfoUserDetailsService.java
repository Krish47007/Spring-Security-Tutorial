package com.krish.demo.service;

import com.krish.demo.dto.UserInfoUserDetails;
import com.krish.demo.entity.UserInfo;
import com.krish.demo.repository.UserInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByName(username)
                        .map(UserInfoUserDetails::new)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with name %s doesn't exist",username)));


    }
}
