package com.example.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.exceptions.CannotPerformOperationException;
import com.example.exceptions.InvalidHasException;

public class MyPasswordEncoder implements PasswordEncoder{
	private static final Logger logger = LoggerFactory.getLogger(MyPasswordEncoder.class);
	PasswordStorage ps = new PasswordStorage();
	
	@Override
	public String encode(CharSequence rawPassword) {
		byte[] salt = ps.createSalt();
		try {
			return ps.createHash(rawPassword.toString(), salt);
		} catch (CannotPerformOperationException e) {
			logger.info(e.getMessage());
		}
		
		return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		try {
			if(ps.verifyPassword(rawPassword.toString(), encodedPassword)){
				
				return true;
			}
		} catch (CannotPerformOperationException e) {
			logger.info(e.getMessage());
		} catch (InvalidHasException e) {
			logger.info(e.getMessage());
		}
		
		return false;
	}

}
