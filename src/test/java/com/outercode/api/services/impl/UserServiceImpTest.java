package com.outercode.api.services.impl;

import com.outercode.api.domain.User;
import com.outercode.api.domain.dto.UserDTO;
import com.outercode.api.repositories.UserRepository;
import com.outercode.api.services.exceptions.DataIntegratyViolation;
import com.outercode.api.services.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImpTest {

    public static final Integer ID = 1;
    public static final String NAME = "Valdir";
    public static final String EMAIL = "Valdir@gmail.com";
    public static final String PASSWORD = "1234";
    @InjectMocks
    private UserServiceImp service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> userOptional;

    @BeforeEach
    public void setUp () {
        MockitoAnnotations.openMocks(this);
        startUser();
    }



    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(userOptional);

        User response = service.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenThrow(new UserNotFoundException("User not found."));

        try {
            service.findById(ID);
        } catch (Exception exception) {
            assertEquals(UserNotFoundException.class, exception.getClass());
            assertEquals("User not found.", exception.getMessage() );

        }

    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.getFirst().getClass());
        assertEquals(ID, response.getFirst().getId());
        assertEquals(EMAIL, response.getFirst().getEmail());
        assertEquals(NAME, response.getFirst().getName());
        assertEquals(PASSWORD, response.getFirst().getPassword());



    }

    @Test
    void whenCreateThenReturnSuccess() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        User response = service.create(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(NAME, response.getName());
        assertEquals(PASSWORD, response.getPassword());

    }

    @Test
    void whenCreateThenReturnIntegratyViolationException() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString()))
                .thenReturn(userOptional);


        try {
            userOptional.get().setId(2);
            service.create(userDTO);
        } catch (Exception exception) {
            assertEquals(DataIntegratyViolation.class, exception.getClass());
            assertEquals("User already exists", exception.getMessage());




        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        User response = service.update(userDTO);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(NAME, response.getName());
        assertEquals(PASSWORD, response.getPassword());

    }

    @Test
    void whenUpdateThenReturnIntegratyViolationException() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString()))
                .thenReturn(userOptional);


        try {
            userOptional.get().setId(2);
            service.create(userDTO);
        } catch (Exception exception) {
            assertEquals(DataIntegratyViolation.class, exception.getClass());
            assertEquals("User already exists", exception.getMessage());




        }
    }

    @Test
    void deleteWithSuccess() {
        Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(userOptional);
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyInt());

        service.delete(ID);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(Mockito.anyInt());
    }

    public void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        userOptional = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}