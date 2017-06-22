package com.example.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.example.common.UserExceptionMessages;

import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private int id;
	
	@Column(name="email")
	@Email(message=UserExceptionMessages.INVALID_EMAIL_MESSAGE)
	@NotEmpty(message=UserExceptionMessages.EMPTY_EMAIL_MESSAGE)
	private String email;
	
	@Column(name="password") 
	@Length(min=6, message=UserExceptionMessages.INVALID_LENGTH_PASSWORD_MESSAGE)
	@NotEmpty(message=UserExceptionMessages.EMPTY_PASSWORD_MESSAGE)
	private String password;
	
	@Column(name="user_name")
	@NotEmpty(message=UserExceptionMessages.EMPTY_USERNAME_MESSAGE)
	private String username;
	
	@Column
	private String salt;
	
	@Column(name="user_status")
	private String userStatus;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="roles_role_id")
	private Role role;
}
