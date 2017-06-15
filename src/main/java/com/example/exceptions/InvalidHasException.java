package com.example.exceptions;

public class InvalidHasException extends Exception{
	
	private static final long serialVersionUID = -5501937052163998588L;

	public InvalidHasException(String message){
		super(message);
	}
	
	public InvalidHasException(String message, Throwable source){
		super(message, source);
	}
}
