package com.example.exceptions;

public class TokenNotFoundException extends Exception{

	private static final long serialVersionUID = -4362432738178418636L;
	public TokenNotFoundException(){
		super("Token not found.");
	}
}
