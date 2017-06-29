package com.example.services;

import java.util.List;

import com.example.models.User;

public interface UserService {
	User findByEmail(String email);
	void saveUser(User user);
	List<User> findAll();
	void deleteUser(int id);
	User findById(int id);
}
