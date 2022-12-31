		package com.milan.SR57_OWP.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.milan.SR57_OWP.dao.CommentDAO;
import com.milan.SR57_OWP.model.Comment;
import com.milan.SR57_OWP.model.Workout;

@Repository
public class CommentDAOImpl implements CommentDAO{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class CommentRowMapper implements RowMapper<Comment> {
		
		@Override
		public Comment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String commentText = resultSet.getString(index++);
			Integer commentGrade = resultSet.getInt(index++);
			LocalDateTime dateOfCreation = resultSet.getTimestamp(index++).toLocalDateTime();
			String username = resultSet.getString(index++);
			
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
			
			String commentStatus = resultSet.getString(index++);
			boolean anonymous = resultSet.getBoolean(index++);
			
			Comment comment = new Comment(id, commentText, commentGrade, dateOfCreation, username, workout, commentStatus, anonymous);
			return comment;
			
		}
	}
	
	@Override
	public Comment findOneById(Long id) {
		String sql = 
				"SELECT c.id, c.commentText, c.commentGrade, c.dateOfCreation, c.username, w.id, w.workoutName, w.workoutDescription, w.picture, w.price, w.workoutGrouping, w.workoutLevel, w.workoutLength, w.averageGrade, c.commentStatus, c.anonymous FROM comments c " +
				"LEFT JOIN workouts w ON c.workoutId = w.id " +
				"WHERE c.id = ? " +
				"ORDER BY c.id";
		return jdbcTemplate.queryForObject(sql, new CommentRowMapper(), id);
	}
	
	@Override
	public Comment findOneByCommentText(String commentText) {
		String sql = 
				"SELECT c.id, c.commentText, c.commentGrade, c.dateOfCreation, c.username, w.id, w.workoutName, w.workoutDescription, w.picture, w.price, w.workoutGrouping, w.workoutLevel, w.workoutLength, w.averageGrade, c.commentStatus, c.anonymous FROM comments c " +
				"LEFT JOIN workouts w ON c.workoutId = w.id " +
				"WHERE c.commentText = ? " +
				"ORDER BY c.id";
		return jdbcTemplate.queryForObject(sql, new CommentRowMapper(), commentText);
	}
	
	@Override
	public List<Comment> findAll() {
		String sql = 
				"SELECT c.id, c.commentText, c.commentGrade, c.dateOfCreation, c.username, w.id, w.workoutName, w.workoutDescription, w.picture, w.price, w.workoutGrouping, w.workoutLevel, w.workoutLength, w.averageGrade, c.commentStatus, c.anonymous FROM comments c " +
				"LEFT JOIN workouts w ON c.workoutId = w.id " +
				"ORDER BY c.id";
		return jdbcTemplate.query(sql, new CommentRowMapper());
	}
	
	@Transactional
	@Override
	public int saveComment(Comment comment) {
		String sql = "INSERT INTO comments (commentText, commentGrade, dateOfCreation, username, workoutId, commentStatus, anonymous) VALUES (?, ?, ?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, comment.getCommentText(), comment.getCommentGrade(), comment.getDateOfCreation(), comment.getUsername(), comment.getWorkout().getId(), comment.getCommentStatus(), comment.isAnonymous());	
	}
	
	@Transactional
	@Override
	public int updateComment(Comment comment) {
		String sql = "UPDATE comments SET commentText = ?, commentGrade = ?, dateOfCreation = ?, username = ?, workoutId = ?, commentStatus = ?, anonymous = ? WHERE id = ?";
		return jdbcTemplate.update(sql, comment.getCommentText(), comment.getCommentGrade(), comment.getDateOfCreation(), comment.getUsername(), comment.getWorkout().getId(), comment.getCommentStatus(), comment.isAnonymous(), comment.getId());
	}
	
	@Transactional
	@Override
	public int deleteComment(Long id) {
		String sql = "DELETE FROM comments WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
}
