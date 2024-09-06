package com.outercode.api.resources;

import com.outercode.api.domain.User;
import com.outercode.api.domain.dto.UserDTO;
import com.outercode.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserResourceTest {

    public static final Integer ID = 1;
    public static final String NAME = "Valdir";
    public static final String EMAIL = "Valdir@gmail.com";
    public static final String PASSWORD = "1234";

    @InjectMocks
    private UserResource resource;

    @Mock
    private ModelMapper mapper;

    @Mock
    private UserService service;

    private User user;
    private UserDTO userDTO;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();

    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        Mockito.when(service.findById(Mockito.anyInt())).thenReturn(user);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
        assertEquals(200, response.getStatusCode().value());


    }


    @Test
    void whenFindAllThenReturnAnListOfUserDTO() {
        Mockito.when(service.findAll()).thenReturn(List.of(user));
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UserDTO.class, response.getBody().getFirst().getClass());

        assertEquals(ID, response.getBody().getFirst().getId());
        assertEquals(NAME, response.getBody().getFirst().getName());
        assertEquals(EMAIL, response.getBody().getFirst().getEmail());
        assertEquals(PASSWORD, response.getBody().getFirst().getPassword());
        assertEquals(200, response.getStatusCode().value());


    }

    @Test
    void whenCreateThenReturnCreated() {
        // Mockando o comportamento do serviço
        Mockito.when(service.create(Mockito.any())).thenReturn(user);

        // Executando a ação
        ResponseEntity<UserDTO> response = resource.create(userDTO);

        // Verificações
        assertNotNull(response.getHeaders().get("Location")); // Verifica se o cabeçalho "Location" está presente
        assertEquals(ResponseEntity.class, response.getClass()); // Verifica se a resposta é do tipo ResponseEntity
        assertEquals(201, response.getStatusCode().value()); // Verifica se o status retornado é 201

        // Verificação adicional para garantir que o método create foi chamado corretamente
        Mockito.verify(service, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        Mockito.when(service.update(Mockito.any())).thenReturn(user);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.update(ID, userDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
    }
    @Test
    void whenDeleteThenReturnSuccess() {
        // Mockando o comportamento do método delete para não fazer nada
        Mockito.doNothing().when(service).delete(Mockito.anyInt());

        // Executando a ação de deletar
        ResponseEntity<UserDTO> response = resource.delete(ID);

        // Verificando se o método delete foi chamado exatamente uma vez
        Mockito.verify(service, Mockito.times(1)).delete(Mockito.anyInt());

        // Verificando a classe da resposta
        assertEquals(ResponseEntity.class, response.getClass());

        // Verificando o status de NO_CONTENT (204)
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }



    public void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);

    }
}