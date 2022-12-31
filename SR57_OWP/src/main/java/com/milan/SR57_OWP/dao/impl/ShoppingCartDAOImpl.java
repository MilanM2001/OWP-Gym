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

import com.milan.SR57_OWP.dao.ShoppingCartDAO;
import com.milan.SR57_OWP.dao.WorkoutDAO;
import com.milan.SR57_OWP.model.ShoppingCart;
import com.milan.SR57_OWP.model.Workout;

@Repository
public class ShoppingCartDAOImpl implements ShoppingCartDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private WorkoutDAO workoutDAO;
	
	private class ShoppingCartRowCallBackHandler implements RowCallbackHandler {
		
		private Map<Long, ShoppingCart> shoppingCarts = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String username = resultSet.getString(index++);
			
			ShoppingCart shoppingCart = shoppingCarts.get(id);
			if (shoppingCart == null) {
				shoppingCart = new ShoppingCart(id, username);
				shoppingCarts.put(shoppingCart.getId(), shoppingCart);
			}
			
			Long workoutId = resultSet.getLong(index++);
			if (workoutId != 0) {
				Workout workout = workoutDAO.findOneById(workoutId);
				shoppingCart.getOrderedWorkouts().add(workout);
			}
		}
		
		public List<ShoppingCart> getShoppingCarts() {
			return new ArrayList<>(shoppingCarts.values());
		}
		
	}
	
	@Override
	public ShoppingCart findOneById(Long id) {
		String sql = 
				"SELECT sc.id, sc.username, w.id FROM shoppingCarts sc " +
				"LEFT JOIN workoutsShoppingCarts wsc ON wsc.shoppingCartId = sc.id " +
				"LEFT JOIN workouts w ON wsc.workoutId = w.id " +
				"WHERE sc.id = ? " +
				"ORDER BY sc.id";
		
		ShoppingCartRowCallBackHandler rowCallbackHandler = new ShoppingCartRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);
		
		return rowCallbackHandler.getShoppingCarts().get(0);
	}
	
	@Override
	public ShoppingCart findOneByUsername(String username) {
		String sql = 
				"SELECT sc.id, sc.username, w.id FROM shoppingCarts sc " +
				"LEFT JOIN workoutsShoppingCarts wsc ON wsc.shoppingCartId = sc.id " +
				"LEFT JOIN workouts w ON wsc.workoutId = w.id " +
				"WHERE sc.username = ? " +
				"ORDER BY sc.id";
		
		ShoppingCartRowCallBackHandler rowCallbackHandler = new ShoppingCartRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, username);
		
		return rowCallbackHandler.getShoppingCarts().get(0);
	}
	
	@Override
	public List<ShoppingCart> findAll() {
		String sql = 
				"SELECT sc.id, sc.username, w.id FROM shoppingCarts sc " +
				"LEFT JOIN workoutsShoppingCarts wsc ON wsc.shoppingCartId = sc.id " +
				"LEFT JOIN workouts w ON wsc.workoutId = w.id " +
				"ORDER BY sc.id";
		
		ShoppingCartRowCallBackHandler rowCallbackHandler = new ShoppingCartRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);
		
		return rowCallbackHandler.getShoppingCarts();
	}
	
	@Transactional
	@Override
	public int save(ShoppingCart shoppingCart) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO shoppingCarts (username) VALUES (?)";
				
				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, shoppingCart.getUsername());
				
				return preparedStatement;
			}
		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean success = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return success?1:0;
	}
	
	@Transactional
	@Override
	public int update(ShoppingCart shoppingCart) {
		
		String sql = "DELETE FROM workoutsShoppingCarts WHERE shoppingCartId = ?";
		jdbcTemplate.update(sql, shoppingCart.getId());
		
		boolean success = true;
		sql = "INSERT INTO workoutsShoppingCarts (workoutId, shoppingCartId) VALUES (?, ?)";
		for (Workout itWorkout: shoppingCart.getOrderedWorkouts()) {
			success = success && jdbcTemplate.update(sql, itWorkout.getId(), shoppingCart.getId()) == 1;
		}
		
		sql = "UPDATE shoppingCarts set username = ? WHERE id = ?";
		success = jdbcTemplate.update(sql, shoppingCart.getUsername(), shoppingCart.getId()) == 1;
		
		return success?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM shoppingCarts WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}
