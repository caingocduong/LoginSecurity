package com.example.services;

import com.example.models.User;

public interface MailService {
	void sendResetPasswordEmail(String email, User user, String url, String token);
	void sendActivationAccountEmail(String email, User user, String url, String token);
}
