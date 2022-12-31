package com.milan.SR57_OWP.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.milan.SR57_OWP.dao.WorkoutAppointmentDAO;
import com.milan.SR57_OWP.model.Hall;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.model.WorkoutAppointment;

@Repository
public class WorkoutAppointmentDAOImpl implements WorkoutAppointmentDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class WorkoutAppointmentRowMapper implements RowMapper<WorkoutAppointment> {
		
		@Override
		public WorkoutAppointment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			LocalDateTime dateOfWorkout = resultSet.getTimestamp(index++).toLocalDateTime();
			
			Long hallId = resultSet.getLong(index++);
			String hallLabel = resultSet.getString(index++);
			Integer capacity = resultSet.getInt(index++);
			Hall hall = new Hall(hallId, hallLabel, capacity);
			
			Long workoutId = resultSet.getLong(index++);
			String workoutName = resultSet.getString(index++);
			String workoutDescription = resultSet.getString(index++);
			String picture = resultSet.getString(index++);
			Integer price = resultSet.getInt(index++);
			String workoutGrouping = resultSet.getString(index++);
			String workoutLevel = resultSet.getString(index++);
			Double workoutLength = resultSet.getDouble(index++);
			Integer averageGrade = resultSet.getInt(index++);
			Workout workout = new Workout(workoutId, workoutName, workoutDescription, picture, price, workoutGrouping, workoutLevel, workoutLength, averageGrade);
			
			WorkoutAppointment workoutAppointment = new WorkoutAppointment(id, hall, workout, dateOfWorkout);
			return workoutAppointment;
			
		}
	}
	
	@Override
	public WorkoutAppointment findOneById(Long id) {
		String sql = 
				"SELECT wa.id, wa.dateOfWorkout, h.id, h.hallLabel, h.capacity, w.id, w.workoutName, w.workoutDescription, w.picture, w.price, w.workoutGrouping, w.workoutLevel, w.workoutLength, w.averageGrade FROM workoutAppointments wa " +
				"LEFT JOIN halls h ON wa.hallId = h.id " +
				"LEFT JOIN workouts w ON wa.workoutId = w.id " +
				"WHERE wa.id = ? " + 
				"ORDER BY wa.id";
		return jdbcTemplate.queryForObject(sql, new WorkoutAppointmentRowMapper(), id);
	}
	
	@Override
	public List<WorkoutAppointment> findAll() {
		String sql = 
				"SELECT wa.id, wa.dateOfWorkout, h.id, h.hallLabel, h.capacity, w.id, w.workoutName, w.workoutDescription, w.picture, w.price, w.workoutGrouping, w.workoutLevel, w.workoutLength, w.averageGrade FROM workoutAppointments wa " +
						"LEFT JOIN halls h ON wa.hallId = h.id " +
						"LEFT JOIN workouts w ON wa.workoutId = w.id " +
						"ORDER BY wa.id";
		return jdbcTemplate.query(sql, new WorkoutAppointmentRowMapper());
	}
	
	@Override
	public int saveWorkoutAppointment(WorkoutAppointment workoutAppointment) {
		String sql = "INSERT INTO workoutAppointments (hallId, workoutId, dateOfWorkout) VALUES (?, ?, ?)";
		return jdbcTemplate.update(sql, workoutAppointment.getHall().getId(), workoutAppointment.getWorkout().getId(), workoutAppointment.getDateOfWorkout());
	}
	
	@Override
	public int updateWorkoutAppointment(WorkoutAppointment workoutAppointment) {
		String sql = "UPDATE workoutAppointments SET hallId = ?, workoutId = ?, dateOfWorkout = ? WHERE id  = ?";
		return jdbcTemplate.update(sql, workoutAppointment.getHall().getId(), workoutAppointment.getWorkout().getId(), workoutAppointment.getDateOfWorkout(), workoutAppointment.getId());
	}
	
	@Override
	public int deleteWorkoutAppointment(Long id) {
		String sql = "DELETE FROM workoutAppointments WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	@Override
	public List<WorkoutAppointment> find(Long hallId, Long workoutId, LocalDateTime dateAndTimeFrom, LocalDateTime dateAndTimeTo) {
		
		ArrayList<Object> argumentList = new ArrayList<Object>();
		
		String sql = "SELECT wa.id, wa.dateOfWorkout, h.id, h.hallLabel, h.capacity, w.id, w.workoutName, w.workoutDescription, w.picture, w.price, w.workoutGrouping, w.workoutLevel, w.workoutLength, w.averageGrade FROM workoutAppointments wa " +
				"LEFT JOIN halls h ON wa.hallId = h.id " +
				"LEFT JOIN workouts w ON wa.workoutId = w.id ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean hasArguments = false;
		
		if(hallId!=null) {
			if(hasArguments)
				whereSql.append(" AND ");
			whereSql.append("wa.hallId = ?");
			hasArguments = true;
			argumentList.add(hallId);
		}
		
		if(workoutId!=null) {
			if(hasArguments)
				whereSql.append(" AND ");
			whereSql.append("wa.workoutId = ?");
			hasArguments = true;
			argumentList.add(workoutId);
		}
		
		if(dateAndTimeFrom!=null) {
			if(hasArguments)
				whereSql.append(" AND ");
			whereSql.append("wa.dateOfWorkout >= ?");
			hasArguments = true;
			argumentList.add(dateAndTimeFrom);
		}
		
		if(dateAndTimeTo!=null) {
			if(hasArguments)
				whereSql.append(" AND ");
			whereSql.append("wa.dateOfWorkout <= ?");
			hasArguments = true;
			argumentList.add(dateAndTimeTo);
		}
		
		if(hasArguments)
			sql=sql + whereSql.toString()+" ORDER BY wa.id";
		else
			sql=sql + " ORDER BY wa.id";
		System.out.println(sql);
		
		return jdbcTemplate.query(sql, argumentList.toArray(), new WorkoutAppointmentRowMapper());
	}
	
	@Override
	public List<WorkoutAppointment> find(HashMap<String, Object> argumentMap) {
		
		ArrayList<Object> argumentList = new ArrayList<Object>();
		
		String sql = "SELECT wa.id, wa.dateOfWorkout, h.id, h.hallLabel, h.capacity, w.id, w.workoutName, w.workoutDescription, w.picture, w.price, w.workoutGrouping, w.workoutLevel, w.workoutLength, w.averageGrade FROM workoutAppointments wa " +
				"LEFT JOIN halls h ON wa.hallId = h.id " +
				"LEFT JOIN workouts w ON wa.workoutId = w.id ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean hasArguments = false;
		
		if(argumentMap.containsKey("hallId")) {
			if(hasArguments)
				whereSql.append(" AND ");
			whereSql.append("wa.hallId = ?");
			hasArguments = true;
			argumentList.add(argumentMap.get("hallId"));
		}
		
		if(argumentMap.containsKey("workoutId")) {
			if(hasArguments)
				whereSql.append(" AND ");
			whereSql.append("wa.workoutId = ?");
			hasArguments = true;
			argumentList.add(argumentMap.get("workoutId"));
		}
		
		if(argumentMap.containsKey("dateAndTimeFrom")) {
			if (hasArguments)
				whereSql.append(" AND ");
			whereSql.append("wa.dateOfWorkout >= ?");
			hasArguments = true;
			argumentList.add(argumentMap.get("dateAndTimeFrom"));
		}
		
		if(argumentMap.containsKey("dateAndTimeTo")) {
			if (hasArguments)
				whereSql.append(" AND ");
			whereSql.append("wa.dateOfWorkout <= ?");
			hasArguments = true;
			argumentList.add(argumentMap.get("dateAndTimeTo"));
		}
		
		if(hasArguments)
			sql=sql + whereSql.toString()+" ORDER BY wa.id";
		else
			sql=sql + " ORDER BY wa.id";
		System.out.println(sql);
		
		return jdbcTemplate.query(sql, argumentList.toArray(), new WorkoutAppointmentRowMapper());	
	}
	
}
