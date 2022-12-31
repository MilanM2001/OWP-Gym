package com.milan.SR57_OWP.model;

import java.util.ArrayList;
import java.util.List;

public class WishList {

	private Long id;
	private String username;
	private List<Workout> wishedWorkouts;
	
	public WishList() {
		this.wishedWorkouts = new ArrayList<Workout>();
	}

	public WishList(Long id, String username, List<Workout> wishedWorkouts) {
		super();
		this.id = id;
		this.username = username;
		this.wishedWorkouts = wishedWorkouts;
	}

	public WishList(Long id, String username) {
		super();
		this.id = id;
		this.username = username;
		this.wishedWorkouts = new ArrayList<Workout>();
	}

	public WishList(String username) {
		super();
		this.username = username;
		this.wishedWorkouts = new ArrayList<Workout>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Workout> getWishedWorkouts() {
		return wishedWorkouts;
	}

	public void setWishedWorkouts(List<Workout> wishedWorkouts) {
		this.wishedWorkouts = wishedWorkouts;
	}
	
	
	
}
