package com.milan.SR57_OWP.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.milan.SR57_OWP.model.Comment;
import com.milan.SR57_OWP.model.Workout;

public interface CommentService {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	Comment findOneById(Long id);
	
	Comment findOneByCommentText(String commentText);
	
	Comment saveComment(Comment comment);
	
	List<Comment> saveComment(List<Comment> comment);
	
	Comment updateComment(Comment comment);
	
	List<Comment> updateComment(List<Comment> comment);
	
	Comment deleteComment(Long id);
	
	List<Comment> deleteAll(Workout workout);
	
	List<Comment> deleteAll(List<Workout> workout);
	
	void delete(List<Long> ids);
	
	List<Comment> findAll();
	
}
