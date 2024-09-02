package com.outercode.api.services;

import com.outercode.api.domain.User;
import com.outercode.api.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO obj);
}
