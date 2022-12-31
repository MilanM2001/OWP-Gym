package com.milan.SR57_OWP.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.milan.SR57_OWP.dao.WorkoutTypeDAO;
import com.milan.SR57_OWP.model.WorkoutType;

@Repository	
@Primary
public class WorkoutTypeDAOImpl implements WorkoutTypeDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class WorkoutTypeRowMapper implements RowMapper<WorkoutType> {
		
		@Override
		public WorkoutType mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String workoutTypeName = resultSet.getString(index++);
			String workoutTypeDescription = resultSet.getString(index++);
			
			WorkoutType workoutType = new WorkoutType(id, workoutTypeName, workoutTypeDescription);
			return workoutType;
			}
			
	}
	
	@Override
	public WorkoutType findOneById(Long id) {
		String sql = "SELECT id, workoutTypeName, workoutTypeDescription FROM workoutType WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new WorkoutTypeRowMapper(), id);
	}
	
	@Override
	public List<WorkoutType> findAll() {
		String sql = "SELECT id, workoutTypeName, workoutTypeDescription FROM workoutType";
		return jdbcTemplate.query(sql, new WorkoutTypeRowMapper());
	}
	
	@Override
	public List<WorkoutType> find(String workoutTypeName) {
		workoutTypeName = "%" + workoutTypeName + "%";
		String sql = "SELECT id, workoutTypeName FROM workoutType WHERE workoutTypeName LIKE ?";
		return jdbcTemplate.query(sql, new WorkoutTypeRowMapper(), workoutTypeName);
	}
	
	@Transactional
	@Override
	public int saveWorkoutType(WorkoutType workoutType) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO WorkoutType(workoutTypeName, workoutTypeDescription) VALUES (?, ?)";
				
				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, workoutType.getWorkoutTypeName());
				preparedStatement.setString(index++, workoutType.getWorkoutTypeDescription());
				
				return preparedStatement;
			}
			
		};
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean success = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return success?1:0;

	}
	
	@Override
	public int [] save(ArrayList<WorkoutType> workoutTypes) {
		String sql = "INSERT INTO workoutType (workoutTypeName, workoutTypeDescription) VALUES (?, ?)";
		
		return jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, workoutTypes.get(i).getWorkoutTypeName());
						ps.setString(2, workoutTypes.get(i).getWorkoutTypeDescription());
					}
					
					@Override
					public int getBatchSize() {
						return workoutTypes.size();
					}
				});
	}
	
	@Transactional
	@Override
	public int updateWorkoutType(WorkoutType workoutType) {
		String sql = "UPDATE WorkoutType SET workoutTypeName = ?, workoutTypeDescription = ? WHERE id = ?";
		boolean success = jdbcTemplate.update(sql, workoutType.getWorkoutTypeName(), workoutType.getWorkoutTypeDescription(), workoutType.getId()) == 1;
		
		return success?1:0;
	}
	
	@Transactional
	@Override
	public int deleteWorkoutType(Long id) {
		String sql = "DELETE FROM WorkoutType WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	@Override
	public List<WorkoutType> find(String workoutTypeName, String workoutTypeDescription) {
		
		ArrayList<Object> argumentList = new ArrayList<Object>();
		
		String sql = "SELECT wt.workoutTypeName, wt.workoutTypeDescription FROM WorkoutType wt ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean haveArgument = false;
		
		if(workoutTypeName!=null) {
			workoutTypeName = "%" + workoutTypeName + "%";
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("wt.workoutTypeName LIKE ?");
			haveArgument = true;
			argumentList.add(workoutTypeName);
		}
		
		if(workoutTypeDescription!=null) {
			workoutTypeDescription = "%" + workoutTypeDescription + "%";
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("wt.workoutTypeDescription LIKE ?");
			haveArgument = true;
			argumentList.add(workoutTypeDescription);
		}
		
		if(haveArgument)
			sql=sql + whereSql.toString()+" ORDER BY wt.id";
		else
			sql=sql + " ORDER BY wt.id";
		System.out.println(sql);
		
		List<WorkoutType> workoutTypes = jdbcTemplate.query(sql, argumentList.toArray(), new WorkoutTypeRowMapper());
		
		return workoutTypes;
		
	}
	
}
