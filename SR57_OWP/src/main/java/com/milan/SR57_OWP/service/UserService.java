package com.milan.SR57_OWP.service;

import java.util.List;

import com.milan.SR57_OWP.model.User;

public interface UserService {
	
	User findOneById(Long id);
	
	User findOne(String userName);
	
	User findOne(String userName, String password);
	
	List<User> find(String userName, String userRole);
	
	User saveUser(User user);
	
	User updateUser(User user);
	
	User updateUserPersonal(User user);
	
	User deleteUser(Long id);
	
	List<User> findAll();
	
	List<User> findAllMembers();
	
}
