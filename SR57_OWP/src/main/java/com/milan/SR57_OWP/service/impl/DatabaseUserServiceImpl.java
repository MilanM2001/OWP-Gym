package com.milan.SR57_OWP.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milan.SR57_OWP.dao.UserDAO;
import com.milan.SR57_OWP.model.User;
import com.milan.SR57_OWP.service.UserService;

@Service
public class DatabaseUserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public User findOneById(Long id) {
		return userDAO.findOneById(id);
	}
	
	@Override
	public User findOne(String userName, String password) {
		return userDAO.findOne(userName, password);
	}
	
	@Override
	public User findOne(String userName) {
		return userDAO.findOne(userName);
	}
	
	@Override
	public List<User> find(String userName, String userRole) {
		List<User> users = userDAO.findAll();
		
		if (userName == null) {
			userName = "";
		}
		
		if (userRole == null) {
			userRole = "";
		}
		
		List<User> result = new ArrayList<>();
		
		for (User itUser: users) {
			
			if (!itUser.getUserName().toLowerCase().contains(userName.toLowerCase())) {
				continue;
			}
			
			if (!itUser.getUserRole().toLowerCase().contains(userRole.toLowerCase())) {
				continue;
			}
			
			result.add(itUser);
			
		}
		
		return result;
	}
	
	@Override
	public List<User> findAll() {
		return userDAO.findAll();
	}
	
	@Override
	public List<User> findAllMembers() {
		return userDAO.findAllMembers();
	}
	
	@Override
	public User saveUser(User user) {
		userDAO.saveUser(user);
		return user;
	}
	
	@Override
	public User updateUser(User user) {
		userDAO.updateUser(user);
		return user;
	}
	
	@Override
	public User updateUserPersonal(User user) {
		userDAO.updateUserPersonal(user);
		return user;
	}
	
	@Override
	public User deleteUser(Long id) {
		User user = userDAO.findOneById(id);
		userDAO.deleteUser(id);
		return user;
	}
	
}
