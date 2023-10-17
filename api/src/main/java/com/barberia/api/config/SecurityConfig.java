package com.barberia.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Configuration 1
/*    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception { // Configurar la seguridad
        return httpSecurity // Creamos la configuracion, parametros, metodos de configuracion y construimos el objeto de http security
                .authorizeHttpRequests() // Configurar cuales urls son protegidas
                    .requestMatchers("/cliente/obtenerTodos").permitAll() // Configurar cuales urls NO SON protegidas / permitir a cualquiera el acceso
                    .anyRequest().authenticated() // Cualquier petición o endpoint tiene que estar autenticado
                .and()
                .formLogin().permitAll() // Permitir a tod0 el mundo que pueda acceder al formulario
                .and()
                .build(); // retorna el SecurityFilterChain / construimos
    }

 */

    // Configuration 2
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception { // Configurar la seguridad
        return  httpSecurity
                .authorizeHttpRequests( auth ->{
                    auth.requestMatchers("/cliente/obtenerTodos").permitAll(); // URL que permitimos
                    auth.anyRequest().authenticated();
                }) // Podemos enviar funciones lambda
                .formLogin()
                    .successHandler(successHandler()) // URL a donde se va a ir despues de iniciar sesión
                    .permitAll()
                .and()
                .sessionManagement() // Configurar el comportamiento de las sesiones
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // ALWAYS (Crear sesión si no existe o usar la existente)
                                                                         // - IF_REQUIRED (Si la sessión no existe, la va a crear. Es mas estricto que ALWAYS)
                                                                         // - NEVER (No crea ninguna sesión, pero si existe una la va a utilizar, si no existe tramita la solicitud sin sesión)
                                                                         // - STATELESS (Trabaja todas las solicitudes de forma independiente y no guarda ningun tipo de dato)
                    .invalidSessionUrl("/login") // Si una sesión es invalida (no se logra autenticar, no se logra crear, etc) se redigira al login
                    .maximumSessions(1) // Numero maximo de sesiones que va a tener cada usuario (lo mas común 1 sola). Se permitiran más en aplicaciones multiplataformas.
                    .expiredUrl("/login") // Si el usuario esta inactivo y se cumple el tiempo de expiración de la sesión, lo redirigimos al login.
                .and()
                .sessionFixation() // Vulnerabilidad que tienen las app web cuando trabajamos con sesiones (cada una tiene un id).
                    .migrateSession() // Cuando se detecta que se trata de hacer un ataque de fijación de sesion, spring security genera otro ID de sesión.
                                      // Cuando genera otro ID de sesión, genera otra sesión, hace un copy paste de los datos en la nueva sesion y garantiza que el usuario no pierda su informacion.
                    //.newSession() // Hace lo mismo que migrateSession pero no copia datos, crea una sesión nueva y en blanco.
                                  // Tambien existe .none() (NO RECOMENDABLE)
                .and()
                .build();
    }

    public AuthenticationSuccessHandler successHandler(){
        return ((request, response, authentication) -> {
           response.sendRedirect("/cliente/obtenerPorId/2");
        });
    }
}

// SEGUIR VIENDO EN EL IZO: https://www.youtube.com/watch?v=pmSJTrOWi7w MINUTO 52:20
// http://localhost:8081/cliente/obtenerTodos?continue
//
//http://localhost:8081/cliente/obtenerPorId/2
//
//http://localhost:8081/logout