package com.example.exceptions;

public class CannotPerformOperationException extends Exception{

	private static final long serialVersionUID = 1609082467901649213L;
	
	public CannotPerformOperationException(String message){
		super(message);
	}
	
	public CannotPerformOperationException(String message, Throwable source){
		super(message, source);
	}
}
