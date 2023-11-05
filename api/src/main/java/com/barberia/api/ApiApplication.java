package com.barberia.api;

import com.barberia.api.models.ERole;
import com.barberia.api.models.RoleEntity;
import com.barberia.api.models.UserEntity;
import com.barberia.api.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Bean
	CommandLineRunner init(){
		return args -> {

			UserEntity userEntity = UserEntity.builder()
					.email("facunditopro@gmail.com")
					.username("facundo1")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.ADMIN.name()))
							.build()))
					.build();

			UserEntity userEntity2 = UserEntity.builder()
					.email("leonel@gmail.com")
					.username("leonel")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.USER.name()))
							.build()))
					.build();

			UserEntity userEntity3 = UserEntity.builder()
					.email("invitado@gmail.com")
					.username("invitado")
					.password(passwordEncoder.encode("1234"))
					.roles(Set.of(RoleEntity.builder()
							.name(ERole.valueOf(ERole.INVITED.name()))
							.build()))
					.build();

			userRepository.save(userEntity);
			userRepository.save(userEntity2);
			userRepository.save(userEntity3);
		};
	}
}