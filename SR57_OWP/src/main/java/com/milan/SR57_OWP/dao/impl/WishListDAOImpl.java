package com.milan.SR57_OWP.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.milan.SR57_OWP.model.User;
import com.milan.SR57_OWP.model.WishList;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.dao.UserDAO;
import com.milan.SR57_OWP.dao.WishListDAO;
import com.milan.SR57_OWP.dao.WorkoutDAO;

@Repository
public class WishListDAOImpl implements WishListDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired UserDAO userDAO;
	
	@Autowired
	private WorkoutDAO workoutDAO;
	
	private class WishListRowCallBackHandler implements RowCallbackHandler {
		
		private Map<Long, WishList> wishLists = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String username = resultSet.getString(index++);
			
			WishList wishList = wishLists.get(id);
			if (wishList == null) {
				wishList = new WishList(id, username);
				wishLists.put(wishList.getId(), wishList);
			}
			
			Long workoutId = resultSet.getLong(index++);
			if (workoutId != 0) {
				Workout workout = workoutDAO.findOneById(workoutId);
				wishList.getWishedWorkouts().add(workout);
			}
		}
		
		public List<WishList> getWishLists() {
			return new ArrayList<>(wishLists.values());
		}
		
	}
	
	@Override
	public WishList findOneById(Long id) {
		String sql = 
				"SELECT wl.id, wl.username, w.id FROM wishLists wl " +
				"LEFT JOIN workoutsWishLists wwl ON wwl.wishListId = wl.id " +
				"LEFT JOIN workouts w ON wwl.workoutId = w.id " +
				"WHERE wl.id = ? " +
				"ORDER BY wl.id";
		
		WishListRowCallBackHandler rowCallbackHandler = new WishListRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);
		
		return rowCallbackHandler.getWishLists().get(0);
	}

	@Override
	public WishList findOneByUsername(String username) {
		String sql = 
				"SELECT wl.id, wl.username, w.id FROM wishLists wl " +
				"LEFT JOIN workoutsWishLists wwl ON wwl.wishListId = wl.id " +
				"LEFT JOIN workouts w ON wwl.workoutId = w.id " +
				"WHERE wl.username = ? " +
				"ORDER BY wl.id";

		WishListRowCallBackHandler rowCallbackHandler = new WishListRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, username);
		
		return rowCallbackHandler.getWishLists().get(0);
	}
	
	@Override
	public List<WishList> findAll() {
		String sql = 
				"SELECT wl.id, wl.username, w.id FROM wishLists wl " +
				"LEFT JOIN workoutsWishLists wwl ON wwl.wishListId = wl.id " +
				"LEFT JOIN workouts w ON wwl.workoutId = w.id " +
				"ORDER BY wl.id";
		
		WishListRowCallBackHandler rowCallbackHandler = new WishListRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);
		
		return rowCallbackHandler.getWishLists();
	}
	
	@Transactional
	@Override
	public int save(WishList wishList) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO wishLists (username) VALUES (?)";
				
				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, wishList.getUsername());
				
				return preparedStatement;
			}
			
		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean success = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return success?1:0;
	}
	
	@Transactional
	@Override
	public int update(WishList wishList) {
		
		String sql = "DELETE FROM workoutsWishLists WHERE wishListId = ?";
		jdbcTemplate.update(sql, wishList.getId());
		
		boolean success = true;
		sql = "INSERT INTO workoutsWishLists (workoutId, wishListId) VALUES (?, ?)";
		for (Workout itWorkout: wishList.getWishedWorkouts()) {
			success = success && jdbcTemplate.update(sql, itWorkout.getId(), wishList.getId()) == 1;
		}
		
		sql = "UPDATE wishLists SET username = ? WHERE id = ?";
		success = jdbcTemplate.update(sql, wishList.getUsername(), wishList.getId()) == 1;
		
		return success?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM wishLists WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	
}
