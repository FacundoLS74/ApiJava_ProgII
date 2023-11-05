package com.barberia.api.service;

import com.barberia.api.models.UserEntity;
import com.barberia.api.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                                               .orElseThrow(() -> new UsernameNotFoundException("El usuario" + username + " no existe."));

        // Permisos del usuario
        Collection<? extends GrantedAuthority> authorities = userEntity.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName().name()))) // creamos una autorizacion que necesita springsecurity
                .collect(Collectors.toSet());

        return new User(userEntity.getUsername(), // Retorna un user de Spring Security
                userEntity.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
