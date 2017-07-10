package com.example.exceptions;

public class EmailExistedException extends Exception{

	private static final long serialVersionUID = -759417932236516227L;
	
	public EmailExistedException(){
		super("Email have existed!!!");
	}
}
