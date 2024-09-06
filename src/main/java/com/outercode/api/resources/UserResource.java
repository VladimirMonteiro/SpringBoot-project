package com.outercode.api.resources;


import com.outercode.api.domain.User;
import com.outercode.api.domain.dto.UserDTO;
import com.outercode.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/user")
public class UserResource {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(userService.findById(id), UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = userService.findAll();
        List<UserDTO> userDTOList = users.stream().map(x -> mapper.map(x, UserDTO.class)).toList();
        return ResponseEntity.ok().body(userDTOList);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO user) {
        User newUser = userService.create(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(uri).build();


    }

    @PostMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO obj) {
        obj.setId(id);
        return ResponseEntity.ok().body(mapper.map(userService.update(obj), UserDTO.class));

    }
}
