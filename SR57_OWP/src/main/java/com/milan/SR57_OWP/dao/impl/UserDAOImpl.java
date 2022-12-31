package com.milan.SR57_OWP.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.milan.SR57_OWP.dao.UserDAO;
import com.milan.SR57_OWP.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class UserRowCallBackHandler implements RowCallbackHandler {
		
		private Map<Long, User> users = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String userName = resultSet.getString(index++);
			String password = resultSet.getString(index++);
			String email = resultSet.getString(index++);
			String firstName = resultSet.getString(index++);
			String lastName = resultSet.getString(index++);
			LocalDate dateOfBirth = resultSet.getTimestamp(index++).toLocalDateTime().toLocalDate();
			String address = resultSet.getString(index++);
			String phoneNumber = resultSet.getString(index++);
			LocalDate dateOfRegistration = resultSet.getTimestamp(index++).toLocalDateTime().toLocalDate();
			String userRole = resultSet.getString(index++);
			boolean active = resultSet.getBoolean(index++);

			User user = users.get(id);
			if(user == null) {
			   user = new User(id, userName, password, email, firstName, lastName, dateOfBirth, address, phoneNumber, dateOfRegistration, userRole, active);
			   users.put(user.getId(), user);
			}

		}
		
		public List<User> getUsers() {
			return new ArrayList<>(users.values());
		}
		
	}
	
	@Override
	public User findOneById(Long id) {
		String sql = 
				"SELECT u.id, u.userName, u.password, u.email, u.firstName, u.lastName, u.dateOfBirth, u.address, u.phoneNumber, u.dateOfRegistration, u.userRole, u.active FROM Users u " +
				"WHERE u.id = ? " +
				"ORDER BY u.id";
		
		UserRowCallBackHandler rowCallBackHandler = new UserRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallBackHandler, id);
		
		return rowCallBackHandler.getUsers().get(0);
	}
	
	@Override
	public User findOne(String userName) {
		String sql = 
				"SELECT u.id, u.userName, u.password, u.email, u.firstName, u.lastName, u.dateOfBirth, u.address, u.phoneNumber, u.dateOfRegistration, u.userRole, u.active FROM Users u " +
				"WHERE u.userName = ? " +
				"ORDER BY u.id";
		
		UserRowCallBackHandler rowCallBackHandler = new UserRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallBackHandler, userName);
		
		return rowCallBackHandler.getUsers().get(0);
	}
	
	@Override
	public User findOne(String userName, String password) {
		String sql = 
				"SELECT u.id, u.userName, u.password, u.email, u.firstName, u.lastName, u.dateOfBirth, u.address, u.phoneNumber, u.dateOfRegistration, u.userRole, u.active FROM Users u " +
				"WHERE u.userName = ? AND " +
				"u.password = ? " +
				"ORDER BY u.id";
		
		UserRowCallBackHandler rowCallBackHandler = new UserRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallBackHandler, userName, password);
		
		if(rowCallBackHandler.getUsers().size() == 0) {
			return null;
		}
		
		return rowCallBackHandler.getUsers().get(0);
	}
	
	@Override
	public List<User> findAll() {
		String sql = 
				"SELECT u.id, u.userName, u.password, u.email, u.firstName, u.lastName, u.dateOfBirth, u.address, u.phoneNumber, u.dateOfRegistration, u.userRole, u.active FROM Users u " +
						"ORDER BY u.id";
		
		UserRowCallBackHandler rowCallBackHandler = new UserRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallBackHandler);

		return rowCallBackHandler.getUsers();
	}
	
	@Override
	public List<User> findAllMembers() {
		String sql = 
				"SELECT u.id, u.userName, u.password, u.email, u.firstName, u.lastName, u.dateOfBirth, u.address, u.phoneNumber, u.dateOfRegistration, u.userRole, u.active FROM Users u " +
						"WHERE u.userRole LIKE 'Member' " +
						"ORDER BY u.id";
		
		UserRowCallBackHandler rowCallBackHandler = new UserRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallBackHandler);

		return rowCallBackHandler.getUsers();
	}
	
	@Transactional
	@Override
	public int saveUser(User user) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO Users(userName, password, email, firstName, lastName, dateOfBirth, address, phoneNumber, dateOfRegistration, userRole, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				
				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, user.getUserName());
				preparedStatement.setString(index++, user.getPassword());
				preparedStatement.setString(index++, user.getEmail());
				preparedStatement.setString(index++, user.getFirstName());
				preparedStatement.setString(index++, user.getLastName());
				preparedStatement.setTimestamp(index++, Timestamp.valueOf(user.getDateOfBirth().atStartOfDay()));
				preparedStatement.setString(index++, user.getAddress());
				preparedStatement.setString(index++, user.getPhoneNumber());
				preparedStatement.setTimestamp(index++, Timestamp.valueOf(user.getDateOfRegistration().atStartOfDay()));
				preparedStatement.setString(index++, user.getUserRole());
				preparedStatement.setBoolean(index++, user.isActive());
				
				return preparedStatement;
			}
			
	};
	GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	boolean success = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
	return success?1:0;
	
	}
	
	@Transactional
	@Override
	public int updateUser(User user) {
		String sql = "UPDATE Users SET userRole = ?, active = ? WHERE id = ?";	
		boolean success = jdbcTemplate.update(sql, user.getUserRole(), user.isActive(), user.getId()) == 1;
		
		return success?1:0;
	}
	
	@Transactional
	@Override
	public int updateUserPersonal(User user) {
		String sql = "UPDATE Users SET userName = ?, password = ?, email = ?, firstName = ?, lastName = ?, dateOfBirth = ?, address = ?, phoneNumber = ?, dateOfRegistration = ?, active = ? WHERE id = ?";
		boolean success = jdbcTemplate.update(sql, user.getUserName(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getAddress(), user.getPhoneNumber(), user.getDateOfRegistration(), user.isActive(), user.getId()) == 1;
		
		return success?1:0;
	}
	
	@Transactional
	@Override
	public int deleteUser(Long id) {
		String sql = "DELETE FROM Users WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	private class UserRowMapper implements RowMapper<User> {
		
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long userId = rs.getLong(index++);
			String userName = rs.getString(index++);
			String userRole = rs.getString(index++);
			
			User user = new User(userId, userName, userRole);
			return user;
		}
	}
	
	@Override
	public List<User> find(String userName, String userRole) {
		
		ArrayList<Object> argumentList = new ArrayList<Object>();
		
		String sql = "SELECT u.id, u.userName, u.userRole FROM Users u ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean haveArgument = false;
		
		if(userName!=null) {
			userName = "%" + userName + "%";
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("u.userName LIKE ?");
			haveArgument = true;
			argumentList.add(userName);
		}
		
		if(userRole!=null) {
			userRole = "%" + userRole + "%";
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("u.useRole LIKE ?");
			haveArgument = true;
			argumentList.add(userRole);
		}
		
		if(haveArgument)
			sql=sql + whereSql.toString()+" ORDER BY u.id";
		else
			sql=sql + " ORDER BY u.id";
		System.out.println(sql);
		
		List<User> users = jdbcTemplate.query(sql, argumentList.toArray(), new UserRowMapper());
		
		return users;
	}
	
}
