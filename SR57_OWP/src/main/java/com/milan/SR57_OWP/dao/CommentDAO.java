package com.milan.SR57_OWP.dao;

import java.util.List;

import com.milan.SR57_OWP.model.Comment;

public interface CommentDAO {

	public Comment findOneById(Long id);
	
	public Comment findOneByCommentText(String commentText);
	
	public int saveComment(Comment comment);
	
	public int updateComment(Comment comment);
	
	public int deleteComment(Long id);
	
	public List<Comment> findAll();
	
}
