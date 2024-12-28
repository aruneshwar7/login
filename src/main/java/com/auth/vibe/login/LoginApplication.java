package com.auth.vibe.login;

import com.auth.vibe.login.model.AppRole;
import com.auth.vibe.login.model.RoleModel;
import com.auth.vibe.login.repo.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
	}

	@Bean
	public CommandLineRunner initRoles(RoleRepo roleRepository) {
		return args -> {
			if (roleRepository.findByRoleName("USER") == null) {
				RoleModel userRole = new RoleModel("ROLE_USER");
				roleRepository.save(userRole);
				System.out.println("Role 'USER' added to the database.");
			} else {
				System.out.println("Role 'USER' already exists in the database.");
			}
		};
	}

}
