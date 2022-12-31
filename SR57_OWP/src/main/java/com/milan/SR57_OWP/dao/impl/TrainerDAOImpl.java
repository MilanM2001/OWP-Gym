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

import com.milan.SR57_OWP.dao.TrainerDAO;
import com.milan.SR57_OWP.model.Trainer;

@Repository
@Primary
public class TrainerDAOImpl implements TrainerDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class TrainerRowMapper implements RowMapper<Trainer> {
		
		@Override
		public Trainer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String trainerName = resultSet.getString(index++);
			
			Trainer trainer = new Trainer(id, trainerName);
			return trainer;
		}
	}
	
	@Override
	public Trainer findOneById(Long id) {
		String sql = "SELECT tr.id, tr.trainerName FROM trainers tr WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new TrainerRowMapper(), id);
	}
	
	@Override
	public List<Trainer> findAll() {
		String sql = "SELECT tr.id, tr.trainerName FROM trainers tr";
		return jdbcTemplate.query(sql, new TrainerRowMapper());
	}
	
	@Override
	public List<Trainer> findByName(String trainerName) {
		trainerName = "%" + trainerName + "%";
		String sql = "SELECT id, trainerName FROM trainers WHERE trainerName LIKE ?";
		return jdbcTemplate.query(sql, new TrainerRowMapper(), trainerName);
	}
	
	@Transactional
	@Override
	public int saveTrainer(Trainer trainer) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO trainers(trainerName) VALUES (?)";
				
				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, trainer.getTrainerName());
				
				return preparedStatement;
			}
			
		};
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean success = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return success?1:0;

	}
	
	@Override
	public int [] save(ArrayList<Trainer> trainers) {
		String sql = "INSERT INTO trainers (trainerName) VALUES (?)";
		
		return jdbcTemplate.batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, trainers.get(i).getTrainerName());
					}
					
					@Override
					public int getBatchSize() {
						return trainers.size();
					}
				});
	}
	
	@Transactional
	@Override
	public int updateTrainer(Trainer trainer) {
		String sql = "UPDATE trainers SET trainerName = ? WHERE id = ?";
		boolean success = jdbcTemplate.update(sql, trainer.getTrainerName(), trainer.getId()) == 1;
		
		return success?1:0;
	}
	
	@Transactional
	@Override
	public int deleteTrainer(Long id) {
		String sql = "DELETE FROM trainers WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	@Override
	public List<Trainer> find(String trainerName) {
		
		ArrayList<Object> argumentList = new ArrayList<Object>();
		
		String sql = "SELECT tr.trainerName FROM trainers tr ";
		
		StringBuffer whereSql = new StringBuffer(" WHERE ");
		boolean haveArgument = false;
		
		if(trainerName!=null) {
			trainerName = "%" + trainerName + "%";
			if(haveArgument)
				whereSql.append(" AND ");
			whereSql.append("tr.trainerName LIKE ?");
			haveArgument = true;
			argumentList.add(trainerName);
		}
		
		if(haveArgument)
			sql=sql + whereSql.toString()+" ORDER BY tr.id";
		else
			sql=sql + " ORDER BY tr.id";
		System.out.println(sql);
		
		List<Trainer> trainers = jdbcTemplate.query(sql, argumentList.toArray(), new TrainerRowMapper());
		
		return trainers;
		
	}
	
	
	
	
}
