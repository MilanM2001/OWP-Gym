package com.milan.SR57_OWP.model;

import java.time.LocalDateTime;


public class Comment {

    private Long id;
    private String commentText;
    private Integer commentGrade;
    private LocalDateTime dateOfCreation;
    private String username;
    private Workout workout;
    private String commentStatus; //enum
    private boolean anonymous;
    
    public Comment() {}

    public Comment(Long id, String commentText, int commentGrade, LocalDateTime dateOfCreation, String username,
            Workout workout, String commentStatus, boolean anonymous) {
        super();
        this.id = id;
        this.commentText = commentText;
        this.commentGrade = commentGrade;
        this.dateOfCreation = dateOfCreation;
        this.username = username;
        this.workout = workout;
        this.commentStatus = commentStatus;
        this.anonymous = anonymous;
    }

    public Comment(String commentText, int commentGrade, LocalDateTime dateOfCreation, String username, Workout workout,
            String commentStatus, boolean anonymous) {
        super();
        this.commentText = commentText;
        this.commentGrade = commentGrade;
        this.dateOfCreation = dateOfCreation;
        this.username = username;
        this.workout = workout;
        this.commentStatus = commentStatus;
        this.anonymous = anonymous;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Comment other = (Comment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getCommentGrade() {
        return commentGrade;
    }

    public void setCommentGrade(Integer commentGrade) {
        this.commentGrade = commentGrade;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

}
