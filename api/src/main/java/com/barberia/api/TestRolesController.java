package com.barberia.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRolesController {

    @GetMapping("/accessAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String accesAdmin(){
        return "Hola, has accedido como ADMINISTRADOR";
    }
    @GetMapping("/accessUser")
    @PreAuthorize("hasRole('USER')")
    public String accessUser(){
        return "Hola, has accedido como USUARIO";
    }
    @GetMapping("/accessInvited")
    @PreAuthorize("hasRole('INVITED')")
    public String accesInvited(){
        return "Hola, has accedido como INVITADO";
    }

}
