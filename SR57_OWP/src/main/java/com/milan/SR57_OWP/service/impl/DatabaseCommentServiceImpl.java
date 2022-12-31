package com.milan.SR57_OWP.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milan.SR57_OWP.dao.CommentDAO;
import com.milan.SR57_OWP.model.Comment;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.service.CommentService;

@Service
public class DatabaseCommentServiceImpl implements CommentService{

	@Autowired
	private CommentDAO commentDAO;
	
	@Override
	public Comment findOneById(Long id) {
		return commentDAO.findOneById(id);
	}
	
	@Override
	public Comment findOneByCommentText(String commentText) {
		return commentDAO.findOneByCommentText(commentText);
	}
	
	@Override
	public List<Comment> findAll() {
		return commentDAO.findAll();
	}
	
	@Override
	public Comment saveComment(Comment comment) {
		commentDAO.saveComment(comment);
		return comment;
	}
	
	@Override
	public List<Comment> saveComment(List<Comment> comment) {
		return null;
	}
	
	@Override
	public Comment updateComment(Comment comment) {
		commentDAO.updateComment(comment);
		return comment;
	}
	
	@Override
	public List<Comment> updateComment(List<Comment> comment) {
		return null;
	}
	
	@Override
	public Comment deleteComment(Long id) {
		Comment comment = commentDAO.findOneById(id);
		commentDAO.deleteComment(id);
		return comment;
	}
	
	@Override
	public List<Comment> deleteAll(Workout workout) {
		return null;
	}
	
	@Override
	public List<Comment> deleteAll(List<Workout> workout) {
		return null;
	}
	
	@Override
	public void delete(List<Long> ids) {
		
	}
	
}
