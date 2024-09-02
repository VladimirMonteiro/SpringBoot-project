package com.outercode.api.services.impl;

import com.outercode.api.domain.User;
import com.outercode.api.domain.dto.UserDTO;
import com.outercode.api.repositories.UserRepository;
import com.outercode.api.services.UserService;
import com.outercode.api.services.exceptions.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(UserDTO obj) {
        return userRepository.save(mapper.map(obj, User.class));
    }
}
