package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="Update users Set user_name = :username, email = :email, roles_role_id = :role_id, user_status = :status Where user_id = :id"
			,nativeQuery=true)
	void updateUser(@Param("id") int id,@Param("username")String username,@Param("email")String email,
					@Param("role_id")int role_id,@Param("status")String status);
	User findByEmail(String email);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="Update users Set reset_password_token = :token Where user_id = :id", nativeQuery=true)
	void updateUser(@Param("token") String token, @Param("id") int id);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="Update users Set password = :password, salt = :salt Where user_id = :id", nativeQuery=true)
	void updateUser(@Param("password") String password,@Param("salt") String salt , @Param("id") int id);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="Update users Set user_status = :status Where user_id = :id", nativeQuery=true)
	void updateStatusUser(@Param("status") String status, @Param("id") int id);
}
