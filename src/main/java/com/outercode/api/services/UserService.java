package com.outercode.api.services;

import com.outercode.api.domain.User;

public interface UserService {

    User findById(Integer id);
}
