package com.barberia.api.controller;

import com.barberia.api.controller.request.CreateUserDTO;
import com.barberia.api.models.ERole;
import com.barberia.api.models.RoleEntity;
import com.barberia.api.models.UserEntity;
import com.barberia.api.persistence.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PrincipalController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/hello")
    public String hello(){
        return "Hello World Not Secured";
    }

    @GetMapping("/helloSecured")
    public String helloSecured(){
        return "Hello World Secured";
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){

        Set<RoleEntity> roles = createUserDTO.getRoles().stream() // Recibimos un set de String con los nombres de los roles y los convertimos en un Set<RoleEntity> para poderlo insertar en la BD
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role)) // devuelve el rol que tenga en la variable 'role'
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        userRepository.save(userEntity); // Guardar nuestro usuario en la BD

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id)); // Borrar por id y pasamos el id, lo tenemos que parsear a LONG
        return "Se ha borrado el user con id ".concat(id);
    }

}
