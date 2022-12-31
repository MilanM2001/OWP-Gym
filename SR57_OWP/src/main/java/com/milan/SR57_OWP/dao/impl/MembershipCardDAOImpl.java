package com.milan.SR57_OWP.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.milan.SR57_OWP.dao.MembershipCardDAO;
import com.milan.SR57_OWP.model.MembershipCard;

@Repository
public class MembershipCardDAOImpl implements MembershipCardDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class MembershipCardRowMapper implements RowMapper<MembershipCard> {
		
		@Override
		public MembershipCard mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			Integer discount = resultSet.getInt(index++);
			Double cardPoints = resultSet.getDouble(index++);
			String cardUsername = resultSet.getString(index++);
			String cardStatus = resultSet.getString(index++);
			
			MembershipCard membershipCard = new MembershipCard(id, discount, cardPoints, cardUsername, cardStatus);
			return membershipCard;
			
		}
		
	}
	
	@Override
	public MembershipCard findOneById(Long id) {
		String sql = 
				"SELECT card.id, card.discount, card.cardPoints, card.cardUsername, card.cardStatus FROM membershipCards card " +
				"WHERE card.id = ? " +
				"ORDER BY card.id";
		return jdbcTemplate.queryForObject(sql, new MembershipCardRowMapper(), id);
	}
	
	@Override
	public List<MembershipCard> findAll() {
		String sql = 
				"SELECT card.id, card.discount, card.cardPoints, card.cardUsername, card.cardStatus FROM membershipCards card " +
				"ORDER BY card.id";
		return jdbcTemplate.query(sql, new MembershipCardRowMapper());
	}
	
	@Transactional
	@Override
	public int saveMembershipCard(MembershipCard membershipCard) {
		String sql = "INSERT INTO membershipCards (discount, cardPoints, cardUsername, cardStatus) VALUES (?, ?, ?, ?)";
		return jdbcTemplate.update(sql, membershipCard.getDiscount(), membershipCard.getCardPoints(), membershipCard.getCardUsername(), membershipCard.getCardStatus());
	}
	
	@Transactional
	@Override
	public int updateMembershipCard(MembershipCard membershipCard) {
		String sql = "UPDATE membershipCards SET discount = ?, cardPoints = ?, cardUsername = ?, cardStatus = ? WHERE id = ?";
		return jdbcTemplate.update(sql, membershipCard.getDiscount(), membershipCard.getCardPoints(), membershipCard.getCardUsername(), membershipCard.getCardStatus(), membershipCard.getId());
	}
	
	@Transactional
	@Override
	public int deleteMembershipCard(Long id) {
		String sql = "DELETE FROM membershipCards WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	
}
