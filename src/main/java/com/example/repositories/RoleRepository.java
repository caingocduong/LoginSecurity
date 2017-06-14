package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByRole(String role);
}
