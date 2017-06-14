package com.example.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private int id;
	
	@Column(name="email")
	@Email(message="Please provide a valid email.")
	@NotEmpty(message="Please provide an email.")
	private String email;
	
	@Column(name="password")
	@Length(min=6, message="Your password must have at least 6 characters.")
	@NotEmpty(message="Please provide password.")
	private String password;
	
	@Column(name="user_name")
	@NotEmpty(message="Please provide user name.")
	private String username;
	
	@Column(name="active")
	private int active;
	@ManyToOne
	@JoinColumn(name="roles_role_id")
	private Role role;
}
