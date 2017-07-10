package com.example.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.PasswordStorage;
import com.example.common.UserStatus;
import com.example.exceptions.CannotPerformOperationException;
import com.example.models.Role;
import com.example.models.User;
import com.example.repositories.RoleRepository;
import com.example.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Override
	@Transactional(readOnly=true)
	public User findByEmail(String email) {

		return userRepo.findByEmail(email);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void saveUser(User user) throws RuntimeException{
		//Test Transactional
		/*Role role = new Role();
		role.setId(3);
		role.setRole("ADMIN_DATABASE");
		roleRepo.save(role);
		if(findByEmail(user.getEmail()) != null){
			throw new RuntimeException("ERROR!!!");
		}*/
		try {
			PasswordStorage ps = new PasswordStorage();
			byte[] salt = ps.createSalt();
			String hash = ps.createHash(user.getPassword(), salt);
			user.setPassword(hash);
			user.setSalt(salt.toString());
		} catch (CannotPerformOperationException e) {
			logger.info(e.getMessage());
		}
		user.setUserStatus(UserStatus.ACTIVE.name());
		Role userRole = roleRepo.findByRole("USER");
		user.setRole(userRole);
		userRepo.save(user);
	} 

	@Override
	public List<User> findAll() {

		return userRepo.findAll();
	}

	@Override
	public void deleteUser(int id) {
		userRepo.delete(id);
	}

	@Override
	public User findById(int id) {

		return userRepo.findOne(id);
	}

	@Override
	public void updateUser(int id,String username, String email, int role_id, String status) {
		userRepo.updateUser(id,username, email, role_id, status);
	}

}
