package com.milan.SR57_OWP.dao;

import java.util.List;

import com.milan.SR57_OWP.model.User;

public interface UserDAO {

	public User findOneById(Long id);
	
	public User findOne(String userName);
	
	public User findOne(String userName, String password);
	
	public List<User> find(String userName, String userRole);
	
	public int saveUser(User user);
	
	public int updateUser(User user);
	
	public int updateUserPersonal(User user);
	
	public int deleteUser(Long id);
	
	public List<User> findAll();
	
	public List<User> findAllMembers();
	
}
