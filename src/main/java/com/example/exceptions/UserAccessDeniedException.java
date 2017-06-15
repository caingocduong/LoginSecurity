package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Access denied")//401
public class UserAccessDeniedException extends Exception{

	private static final long serialVersionUID = 1370075835719460998L;
	
	public  UserAccessDeniedException() {}
}
