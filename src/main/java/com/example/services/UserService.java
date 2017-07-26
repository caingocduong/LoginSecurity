package com.example.services;

import java.util.List;

import com.example.models.User;

public interface UserService {
	User findByEmail(String email);
	void saveUser(User user);
	List<User> findAll();
	void deleteUser(int id);
	User findById(int id);
	void updateUser(int id,String username, String email, int role_id, String status);
	void updatePasswordUser(String password,String salt, int id);
	void updateResetTokenPasswordUser(String token, int id);
	void updateStatusUser(String status, int id);
}
