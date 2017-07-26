package com.example.loginSecurity;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.controllers.BlogController;


@SpringBootApplication
@ComponentScan(basePackages={"com.example"})
@EnableJpaRepositories(basePackages="com.example.repositories")
@EntityScan("com.example.models")   
public class LoginSecurityApplication {

	public static void main(String[] args) {
		new File(BlogController.uploadingdir).mkdirs();
		SpringApplication.run(LoginSecurityApplication.class, args);
	}
}
