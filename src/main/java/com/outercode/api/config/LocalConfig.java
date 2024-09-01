package com.outercode.api.config;

import com.outercode.api.domain.User;
import com.outercode.api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void startDB(){
        User u1 = new User(null, "vlaimir", "teste@gmail.com", "12344");
        User u2 = new User(null, "Pedro", "teste2@gmail.com", "123");

        userRepository.saveAll(Arrays.asList(u1,u2));
    }
}
