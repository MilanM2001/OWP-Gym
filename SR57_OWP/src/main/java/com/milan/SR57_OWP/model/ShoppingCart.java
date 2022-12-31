package com.milan.SR57_OWP.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	private Long id;
	private String username;
	private List<Workout> orderedWorkouts;;
	
	public ShoppingCart(Long id, String username) {
		super();
		this.id = id;
		this.username = username;
		this.orderedWorkouts = new ArrayList<Workout>();
	}
	
	public ShoppingCart(String username) {
		super();
		this.username = username;
		this.orderedWorkouts = new ArrayList<Workout>();
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

	public List<Workout> getOrderedWorkouts() {
		return orderedWorkouts;
	}

	public void setOrderedWorkouts(List<Workout> orderedWorkouts) {
		this.orderedWorkouts = orderedWorkouts;
	}

	
	
	
	
}
