package com.outercode.api.services.impl;

import com.outercode.api.domain.User;
import com.outercode.api.repositories.UserRepository;
import com.outercode.api.services.UserService;
import com.outercode.api.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("User not found!"));
    }
}
