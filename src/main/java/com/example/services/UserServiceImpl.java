package com.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.common.PasswordStorage;
import com.example.controllers.LoginController;
import com.example.exceptions.CannotPerformOperationException;
import com.example.models.Role;
import com.example.models.User;
import com.example.repositories.RoleRepository;
import com.example.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public User findByEmail(String email) {
		
		return userRepo.findByEmail(email);
	}
	
	@Override
	public void saveUser(User user){
		try {
			user.setPassword(new PasswordStorage().createHash(user.getPassword()));
		} catch (CannotPerformOperationException e) {
			logger.info(e.getMessage());
		}
		user.setActive(1);
		Role userRole = roleRepo.findByRole("USER");
		user.setRole(userRole);
		userRepo.save(user);
	}

}
