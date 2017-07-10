package com.example.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.exceptions.CannotPerformOperationException;
import com.example.exceptions.InvalidHasException;

public class PasswordStorage implements PasswordEncoder{
	private static final Logger logger = LoggerFactory.getLogger(PasswordStorage.class);
	public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

	// These constants may be changed without breaking existing hashes.
	public static final int SALT_BYTE_SIZE = 24;
	public static final int HASH_BYTE_SIZE = 18;
	public static final int PBKDF2_ITERATIONS = 64000;

	// These constants define the encoding and may not be changed.
	public static final int HASH_SECTIONS = 5;
	public static final int HASH_ALGORITHM_INDEX = 0;
	public static final int ITERATION_INDEX = 1;
	public static final int HASH_SIZE_INDEX = 2;
	public static final int SALT_INDEX = 3;
	public static final int PBKDF2_INDEX = 4;
	
	public  String createHash(String password, byte[] salt) throws CannotPerformOperationException{
		
		return createHash(password.toCharArray(),salt);
	}
	
	public  String createHash(char[] passwword, byte[] salt) throws CannotPerformOperationException{
		
		byte[] hash = pbkdf2(passwword, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		int hashSize = hash.length;
		
		String parts = "sha1:" + PBKDF2_ITERATIONS + ":" + hashSize +
						":" + toBase64(salt) +
						":" + toBase64(hash);
		
		return parts;
	}
	
	public  byte[] createSalt(){
		SecureRandom secureRandom = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		secureRandom.nextBytes(salt);
		logger.info(""+salt);
		return salt;
	}
	
	private  byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) 
			throws CannotPerformOperationException
	{
		try {
			PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes*8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
			
			return skf.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException ex) {
			
			throw new CannotPerformOperationException("Hash algorithm not supported.", ex);
		} catch (InvalidKeySpecException ex) {
			
			throw new CannotPerformOperationException("Invalid key spec.", ex);
		}
	}
	
	private  String toBase64(byte[] array){
		
		return DatatypeConverter.printBase64Binary(array);
	}
	
	
	
	
	public  boolean verifyPassword(String password, String correctHash)
			throws CannotPerformOperationException, InvalidHasException
	{
		
		return verifyPassword(password.toCharArray(), correctHash);
	}
	
	public  boolean verifyPassword(char[] password, String correctHash)
			throws CannotPerformOperationException, InvalidHasException
	{
		String[] params = correctHash.split(":");
		if(params.length != HASH_SECTIONS){
			throw new InvalidHasException("Fields are missing from the password hash");
		}
		
		if(!params[HASH_ALGORITHM_INDEX].equals("sha1")){
			throw new CannotPerformOperationException("Unsupported hash type.");
		}
		
		int iterations = 0;
		try {
			iterations = Integer.parseInt(params[ITERATION_INDEX]);
		} catch (NumberFormatException ex) {
			throw new InvalidHasException("Could not parse the iteration count as an integer", ex);
		}
		
		if(iterations < 1){
			throw new InvalidHasException("Invalid number of iterations. Must be >= 1");
		}
		
		byte[] salt = null;
		try {
			salt = fromBase64(params[SALT_INDEX]);
		} catch (IllegalArgumentException ex) {
			throw new InvalidHasException("Base64 decoding of salt failed.", ex);
		}
		
		byte[] hash = null;
		try {
			hash = fromBase64(params[PBKDF2_INDEX]);
		} catch (IllegalArgumentException ex) {
			throw new InvalidHasException("Base64 decoding of pbkdf2 output failed.");
		}
		
		int storeHashSize = 0;
		try {
			storeHashSize = Integer.parseInt(params[HASH_SIZE_INDEX]);
		} catch (NumberFormatException ex) {
			throw new InvalidHasException("Could parse hash size as an integer.", ex);
		}
		if(storeHashSize != hash.length) {
			throw new InvalidHasException("Hash length doesn't match stored hash length.");
		}
		
		byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
		
		return slowEquals(hash, testHash);
	}
	
	private  boolean slowEquals(byte[] a, byte[] b){
		int diff = a.length ^ b.length;
		for(int i = 0; i < a.length && i < b.length; i++)
			diff |= a[i]^b[i];
		
		return diff == 0;
	}
	
	private  byte[] fromBase64(String hex) throws IllegalArgumentException{
		
		return DatatypeConverter.parseBase64Binary(hex);
	}

	@Override
	public String encode(CharSequence rawPassword) {
		byte[] salt = createSalt();
		try {
			return createHash(rawPassword.toString(), salt);
		} catch (CannotPerformOperationException e) {
			logger.info(e.getMessage());
		}
		
		return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		try {
			if(verifyPassword(rawPassword.toString(), encodedPassword)){
				
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
