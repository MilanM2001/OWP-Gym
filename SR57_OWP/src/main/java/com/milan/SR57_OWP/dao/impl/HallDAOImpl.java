package com.milan.SR57_OWP.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.milan.SR57_OWP.dao.HallDAO;
import com.milan.SR57_OWP.model.Hall;

@Repository
public class HallDAOImpl implements HallDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class HallRowMapper implements RowMapper<Hall> {
		
		/* private Map<Long, Hall> halls = new LinkedHashMap<>(); */
		
		@Override
		public Hall mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String hallLabel = resultSet.getString(index++);
			Integer capacity = resultSet.getInt(index++);
			
			Hall hall = new Hall(id, hallLabel, capacity);
			return hall;
			}
		
	}
		
	@Override
	public Hall findOneById(Long id) {
		String sql = "SELECT * FROM Halls WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new HallRowMapper(), id);
	}
	
	@Override
	public List<Hall> findAll() {
		String sql = "SELECT * FROM Halls";
		return jdbcTemplate.query(sql, new HallRowMapper());
	}
	
	@Transactional
	@Override
	public int saveHall(Hall hall) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO Halls(hallLabel, capacity) VALUES (?, ?)";
				
				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, hall.getHallLabel());
				preparedStatement.setDouble(index++, hall.getCapacity());
				
				return preparedStatement;
			}
			
		};
		
	GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
	boolean success = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
	return success?1:0;
	
	}
	
	@Override
	public int [] save(ArrayList<Hall> halls) {
		String sql = "INSERT INTO Halls (hallLabel, capacity) VALUES (?, ?)";
		
		return jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, halls.get(i).getHallLabel());
						ps.setInt(2, halls.get(i).getCapacity());
					}
					
					@Override
					public int getBatchSize() {
						return halls.size();
					}
				});
	}
	
	@Transactional
	@Override
	public int updateHall(Hall hall) {
		String sql = "UPDATE Halls SET capacity = ? WHERE id = ?";
		boolean success = jdbcTemplate.update(sql, hall.getCapacity(), hall.getId()) == 1;
		
		return success?1:0;
	}
	
	@Transactional
	@Override
	public int deleteHall(Long id) {
		String sql = "DELETE FROM Halls WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	@Override
	public List<Hall> find(String hallLabel, Integer capacityFrom, Integer capacityTo) {
		
		ArrayList<Object> argumentList = new ArrayList<Object>();
		
		String sql = "SELECT h.hallLabel, h.capacity FROM Halls h ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean haveArgument = false;
		
		if(hallLabel!=null) {
			hallLabel = "%" + hallLabel + "%";
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("h.hallLabel LIKE ?");
			haveArgument = true;
			argumentList.add(hallLabel);
		}
		
		if(capacityFrom!=null) {
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("h.capacity >= ?");
			haveArgument = true;
			argumentList.add(capacityFrom);
		}
		
		if(capacityTo!=null) {
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("h.capacity <= ?");
			haveArgument = true;
			argumentList.add(capacityTo);
		}
		
		if(haveArgument)
			sql=sql + whereSql.toString()+" ORDER BY h.id";
		else
			sql=sql + " ORDER BY h.id";
		System.out.println(sql);
		
		List<Hall> halls = jdbcTemplate.query(sql, argumentList.toArray(), new HallRowMapper());
		
		return halls;
	}
	
	
}
