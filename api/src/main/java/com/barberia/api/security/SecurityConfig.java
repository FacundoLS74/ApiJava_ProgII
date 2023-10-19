package com.barberia.api.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // COMPORTAMIENTO DE ACCESO A NUESTROS ENDPOINTS
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(config -> config.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/hello").permitAll(); // Permite acceder al endpoint a todo el mundo
                    auth.anyRequest().authenticated(); // cualquier ruta tiene que estar autenticado el usuario para acceder
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .httpBasic() // AUTENTICACION BÁSICA, SE HACE CON UN USUARIO EN MEMORIA
                .and()
                .build();
    }
    @Bean
    UserDetailsService userDetailsService(){ // USUARIO CREADO EN MEMORIA, para que funcione tiene que ser administrado por un objeto que administre la autenticacion, en este caso, AuthenticationManager
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("facundo")
                .password("1234")
                .roles()
                .build());
        return manager;
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance(); // No encripta la contraseña, es para evitar el error de AuthenticationManager
    }

    @Bean // Objeto que administra la autenticacion en la aplicación, exije manejar un PasswordEncoder porque no deja pasar usuarios que no tengan contraseñas encriptadas o alguna politica de encriptacion de contraseñas
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

}
