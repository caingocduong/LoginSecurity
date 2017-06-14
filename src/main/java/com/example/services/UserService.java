package com.example.services;

import com.example.models.User;

public interface UserService {
	User findByEmail(String email);
	void saveUser(User user);
}
