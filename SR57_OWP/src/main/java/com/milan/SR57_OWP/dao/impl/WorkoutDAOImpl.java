package com.milan.SR57_OWP.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.milan.SR57_OWP.dao.TrainerDAO;
import com.milan.SR57_OWP.dao.WorkoutDAO;
import com.milan.SR57_OWP.dao.WorkoutTypeDAO;
import com.milan.SR57_OWP.model.Trainer;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.model.WorkoutType;

@Repository
public class WorkoutDAOImpl implements WorkoutDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private WorkoutTypeDAO workoutTypeDAO;
	
	@Autowired TrainerDAO trainerDAO;
	
	private class WorkoutWorkoutTypeRowCallBackHandler implements RowCallbackHandler {
		
		private Map<Long, Workout> workouts = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String workoutName = resultSet.getString(index++);
			String workoutDescription = resultSet.getString(index++);
			String picture = resultSet.getString(index++);
			int price = resultSet.getInt(index++);
			String workoutGrouping = resultSet.getString(index++);
			String workoutLevel = resultSet.getString(index++);
			Double workoutLength = resultSet.getDouble(index++);
			int averageGrade = resultSet.getInt(index++);
			
			Workout workout = workouts.get(id);
			if (workout == null) {
				workout = new Workout(id, workoutName, workoutDescription, picture, price, workoutGrouping, workoutLevel, workoutLength, averageGrade);
				workouts.put(workout.getId(), workout);
			}
			
			Long workoutTypeId = resultSet.getLong(index++);
			String workoutTypeName = resultSet.getString(index++);
			String workoutTypeDescription = resultSet.getString(index++);
			
			Long trainerId = resultSet.getLong(index++);
			String trainerName = resultSet.getString(index++);
			
			WorkoutType workoutType = new WorkoutType(workoutTypeId, workoutTypeName, workoutTypeDescription);
			Trainer trainer = new Trainer(trainerId, trainerName);
			workout.getWorkoutTypes().add(workoutType);	
			workout.getTrainers().add(trainer);
		}
		
		public List<Workout> getWorkouts() {
			return new ArrayList<>(workouts.values());
		}
		
	}

	@Override
	public Workout findOneById(Long id) {
		String sql = 
				"SELECT work.id, work.workoutName, work.workoutDescription, work.picture, work.price, work.workoutGrouping, work.workoutLevel, work.workoutLength, work.averageGrade, type.id, type.workoutTypeName, type.workoutTypeDescription, train.id, train.trainerName FROM Workouts work " +
				"LEFT JOIN workoutworkouttype wwt ON wwt.workoutId = work.id " +
				"LEFT JOIN workouttype type ON wwt.workoutTypeId = type.id " +
				"LEFT JOIN workouttrainer wtt ON wtt.workoutTrainerId = work.id " +
				"LEFT JOIN trainers train ON wtt.trainerId = train.id " +
				"WHERE work.id = ? " +
				"ORDER BY work.id";
		
		WorkoutWorkoutTypeRowCallBackHandler rowCallBackHandler = new WorkoutWorkoutTypeRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallBackHandler, id);
		
		return rowCallBackHandler.getWorkouts().get(0);
	}
	
	@Override
	public List<Workout> findAll() {
		String sql = 
				"SELECT work.id, work.workoutName, work.workoutDescription, work.picture, work.price, work.workoutGrouping, work.workoutLevel, work.workoutLength, work.averageGrade, type.id, type.workoutTypeName, type.workoutTypeDescription, train.id, train.trainerName FROM Workouts work " +
				"LEFT JOIN workoutworkouttype wwt ON wwt.workoutId = work.id " +
				"LEFT JOIN workouttype type ON wwt.workoutTypeId = type.id " +
				"LEFT JOIN workouttrainer wtt ON wtt.workoutTrainerId = work.id " +
				"LEFT JOIN trainers train ON wtt.trainerId = train.id " +
				"ORDER BY work.id";
		
		WorkoutWorkoutTypeRowCallBackHandler rowCallBackHandler = new WorkoutWorkoutTypeRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallBackHandler);
		
		return rowCallBackHandler.getWorkouts();
	}
	
	@Transactional
	@Override
	public int saveWorkout(Workout workout) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO Workouts(workoutName, workoutDescription, picture, price, workoutGrouping, workoutLevel, workoutLength, averageGrade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
				
				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, workout.getWorkoutName());
				preparedStatement.setString(index++, workout.getWorkoutDescription());
				preparedStatement.setString(index++, workout.getPicture());
				preparedStatement.setInt(index++, workout.getPrice());
				preparedStatement.setString(index++, workout.getWorkoutGrouping());
				preparedStatement.setString(index++, workout.getWorkoutLevel());
				preparedStatement.setDouble(index++, workout.getWorkoutLength());
				preparedStatement.setInt(index++, workout.getAverageGrade());
				
				return preparedStatement;
			}
			
	};
	
	GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	boolean success = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
	
	if (success) {
		String sql = "INSERT INTO workoutworkouttype (workoutId, workoutTypeId) VALUES (?, ?)";
		for (WorkoutType itWorkoutType: workout.getWorkoutTypes()) {
			success = success && jdbcTemplate.update(sql, keyHolder.getKey(), itWorkoutType.getId()) == 1;
		}
		String sql2 = "INSERT INTO workouttrainer (workoutTrainerId, trainerId) VALUES (?, ?)";
		for (Trainer itTrainer: workout.getTrainers()) {
			success = success && jdbcTemplate.update(sql2, keyHolder.getKey(), itTrainer.getId()) == 1;
		}
	}

	return success?1:0;
	
	}
	
	@Transactional
	@Override    
	public int updateWorkout(Workout workout) {
		
		String sql = "DELETE FROM workoutworkouttype WHERE workoutId = ?";
		jdbcTemplate.update(sql, workout.getId());
		
		String sql2 = "DELETE FROM workouttrainer WHERE workoutTrainerId = ?";
		jdbcTemplate.update(sql2, workout.getId());
	
		boolean success = true;
		
		for (WorkoutType itWorkoutType: workout.getWorkoutTypes()) {	
			sql = "INSERT INTO workoutworkouttype (workoutId, workoutTypeId) VALUES (?, ?)";
			success = success &&  jdbcTemplate.update(sql, workout.getId(), itWorkoutType.getId()) == 1;
		}
		
		for (Trainer itTrainer: workout.getTrainers()) {
			sql2 = "INSERT Into workouttrainer (workoutTrainerId, trainerId) VALUES (?, ?)";
			success = success && jdbcTemplate.update(sql2, workout.getId(), itTrainer.getId()) == 1;
		}

		sql = "UPDATE Workouts SET workoutName = ?, workoutDescription = ?, picture = ?, price = ?, workoutGrouping = ?, workoutLevel = ?, workoutLength = ?, averageGrade = ? WHERE id = ?";	
		success = success &&  jdbcTemplate.update(sql, workout.getWorkoutName(), workout.getWorkoutDescription(), workout.getPicture(), workout.getPrice(), workout.getWorkoutGrouping(), workout.getWorkoutLevel(), workout.getWorkoutLength(), workout.getAverageGrade(), workout.getId()) == 1;
		
		return success?1:0;
	}
	
	@Transactional
	@Override
	public int deleteWorkout(Long id) {
		String sql = "DELETE FROM workoutworkouttype WHERE workoutId = ?";
		jdbcTemplate.update(sql, id);
		
		String sql2 = "DELETE FROM workouttrainer WHERE workoutTrainerId = ?";
		jdbcTemplate.update(sql2, id);
		
		sql = "DELETE FROM workouts WHERE id = ?";
		return jdbcTemplate.update(sql, id);	
	}
	
	private class WorkoutRowMapper implements RowMapper<Workout> {

		@Override
		public Workout mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long workoutId = rs.getLong(index++);
			String workoutName = rs.getString(index++);
			Integer workoutPrice = rs.getInt(index++);
			Integer workoutAverageGrade = rs.getInt(index++);

			Workout workout = new Workout(workoutId, workoutName, workoutPrice, workoutAverageGrade);
			return workout;
		}

	}
	
	@Override
	public List<Workout> find(String workoutName, Integer priceFrom, Integer priceTo, Integer averageGradeFrom, Integer averageGradeTo, Long workoutTypeId, String workoutGrouping, String workoutLevel) {
		
		ArrayList<Object> argumentList = new ArrayList<Object>();
		
		String sql = "SELECT work.id, work.workoutName, work.price, work.workoutGrouping, work.workoutLevel, work.averageGrade FROM Workouts work ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean haveArgument = false;
		
		if(workoutName!=null) {
			workoutName = "%" + workoutName + "%";
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("work.workoutName LIKE ?");
			haveArgument = true;
			argumentList.add(workoutName);
		}
		
		if(priceFrom!=null) {
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("work.price >= ?");
			haveArgument = true;
			argumentList.add(priceFrom);
		}
		
		if(priceTo!=null) {
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("work.price <= ?");
			haveArgument = true;
			argumentList.add(priceTo);
		}
		
		if(workoutGrouping!=null) {
			workoutGrouping = "%" + workoutGrouping + "%";
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("work.workoutGrouping LIKE ?");
			haveArgument = true;
			argumentList.add(workoutGrouping);
		}
		
		if(workoutLevel!=null) {
			workoutLevel = "%" + workoutLevel + "%";
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("work.workoutLevel LIKE ?");
			haveArgument = true;
			argumentList.add(workoutLevel);
		}
		
		if(averageGradeFrom!=null) {
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("work.averageGrade >= ?");
			haveArgument = true;
			argumentList.add(averageGradeFrom);
		}
		
		if(averageGradeTo!=null) {
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("work.AverageGrade <= ?");
			haveArgument = true;
			argumentList.add(averageGradeTo);
		}
		
		if(haveArgument)
			sql=sql + whereSql.toString()+" ORDER BY work.id";
		else
			sql=sql + " ORDER BY work.id";
		System.out.println(sql);
		
		List<Workout> workouts = jdbcTemplate.query(sql, argumentList.toArray(), new WorkoutRowMapper());
		for(Workout workout : workouts) {
			
			workout.setWorkoutTypes(findWorkoutWorkoutType(workout.getId(), null));
		}
		
		if(workoutTypeId!=null)
			for (Iterator iterator = workouts.iterator(); iterator.hasNext();) {
				Workout workout = (Workout) iterator.next();
				boolean forDeleting = true;
				for (WorkoutType workoutType : workout.getWorkoutTypes()) {
					if(workoutType.getId() == workoutTypeId) {
						forDeleting = false;
						break;
					}
				}
				if(forDeleting)
					iterator.remove();
			}
		
		return workouts;
	}
	
	private class WorkoutWorkoutTypeRowMapper implements RowMapper<Long []> {

		@Override
		public Long [] mapRow(ResultSet rs, int rowNum) throws SQLException {
			int index = 1;
			Long workoutId = rs.getLong(index++);
			Long workoutTypeId = rs.getLong(index++);

			Long [] workoutWorkoutType = {workoutId, workoutTypeId};
			return workoutWorkoutType;
		}
	}
	
	private List<WorkoutType> findWorkoutWorkoutType(Long workoutId, Long workoutTypeId) {
		
		List<WorkoutType> typesOfWorkout = new ArrayList<WorkoutType>();
		
		ArrayList<Object> argumentList = new ArrayList<Object>();
		
		String sql = 
				"SELECT wwt.workoutId, wwt.workoutTypeId FROM workoutworkouttype wwt ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean hasArguments = false;
		
		if(workoutId!=null) {
			if(hasArguments)
				whereSql.append(" AND ");
			whereSql.append("wwt.workoutId = ?");
			hasArguments = true;
			argumentList.add(workoutId);
		}
		
		if(workoutTypeId!=null) {
			if(hasArguments)
				whereSql.append(" AND ");
			whereSql.append("wwt.workoutTypeId = ?");
			hasArguments = true;
			argumentList.add(workoutTypeId);
		}

		if(hasArguments)
			sql=sql + whereSql.toString()+" ORDER BY wwt.workoutId";
		else
			sql=sql + " ORDER BY wwt.workoutId";
		System.out.println(sql);
		
		List<Long[]> workoutTypes = jdbcTemplate.query(sql, argumentList.toArray(), new WorkoutWorkoutTypeRowMapper()); 
				
		for (Long[] fz : workoutTypes) {
			typesOfWorkout.add(workoutTypeDAO.findOneById(fz[1]));
		}
		return typesOfWorkout;
	}
	
	@Override
	public List<Workout> findAllBought() {
		String sql = 
				"SELECT work.id, work.workoutName, work.workoutDescription, work.picture, work.price, work.workoutGrouping, work.workoutLevel, work.workoutLength, work.averageGrade, type.id, type.workoutTypeName, type.workoutTypeDescription, train.id, train.trainerName, cart.workoutId, cart.shoppingCartId FROM Workouts work " +
						"LEFT JOIN workoutworkouttype wwt ON wwt.workoutId = work.id " +
						"LEFT JOIN workouttype type ON wwt.workoutTypeId = type.id " +
						"LEFT JOIN workouttrainer wtt ON wtt.workoutTrainerId = work.id " + 
						"LEFT JOIN trainers train ON wtt.trainerId = train.id " + 
		                "LEFT JOIN workoutsshoppingcarts cart ON cart.workoutId = work.id " +
		                "WHERE work.id LIKE cart.workoutId " +
						"ORDER BY work.id";
		
		WorkoutWorkoutTypeRowCallBackHandler rowCallBackHandler = new WorkoutWorkoutTypeRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallBackHandler);
		
		return rowCallBackHandler.getWorkouts();
	}
	
}
