package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.Role;
import com.example.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	RoleRepository roleRepo;
	
	@Override
	public List<Role> findAll() {
		
		return roleRepo.findAll();
	}

}
