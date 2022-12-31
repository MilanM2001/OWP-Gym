package com.milan.SR57_OWP.model;

import java.util.ArrayList;
import java.util.List;

public class Workout {

	private Long id;
	private String workoutName;

	private List<Trainer> trainers = new ArrayList<>();
	
	private String workoutDescription;
	private String picture;
	
	private List<WorkoutType> workoutTypes = new ArrayList<>();
	
	private int price;
	private String workoutGrouping; 
	private String workoutLevel;		
	private double workoutLength;
	private int averageGrade;
	/* private boolean wished; */
	
	public Workout() {}

	public Workout(Long id, String workoutName, String workoutDescription, String picture,
			int price, String workoutGrouping, String workoutLevel,
			double workoutLength, int averageGrade) {
		super();
		this.id = id;
		this.workoutName = workoutName;
		this.workoutDescription = workoutDescription;
		this.picture = picture;
		this.price = price;
		this.workoutGrouping = workoutGrouping;
		this.workoutLevel = workoutLevel;
		this.workoutLength = workoutLength;
		this.averageGrade = averageGrade;
	}

	public Workout(String workoutName, String workoutDescription, String picture,
			int price, String workoutGrouping, String workoutLevel,
			double workoutLength, int averageGrade) {
		super();
		this.workoutName = workoutName;
		this.workoutDescription = workoutDescription;
		this.picture = picture;
		this.price = price;
		this.workoutGrouping = workoutGrouping;
		this.workoutLevel = workoutLevel;
		this.workoutLength = workoutLength;
		this.averageGrade = averageGrade;
	}
	
	public Workout(Long id, String workoutName, int price, int averageGrade) {
		super();
		this.id = id;
		this.workoutName = workoutName;
		this.price = price;
		this.averageGrade = averageGrade;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result + ((id == null) ? 0 : id.hashCode());
		return 31 + id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Workout other = (Workout) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getWorkoutName() {
		return workoutName;
	}

	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}
	
	public List<Trainer> getTrainers() {
		return trainers;
	}
	
	public void setTrainers(List<Trainer> trainers) {
		this.trainers.clear();
		this.trainers.addAll(trainers);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkoutDescription() {
		return workoutDescription;
	}

	public void setWorkoutDescription(String workoutDescription) {
		this.workoutDescription = workoutDescription;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<WorkoutType> getWorkoutTypes() {
		return workoutTypes;
	}
	
	public void setWorkoutTypes(List<WorkoutType> workoutTypes) {
		this.workoutTypes.clear();
		this.workoutTypes.addAll(workoutTypes);
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getWorkoutGrouping() {
		return workoutGrouping;
	}

	public void setWorkoutGrouping(String workoutGrouping) {
		this.workoutGrouping = workoutGrouping;
	}

	public String getWorkoutLevel() {
		return workoutLevel;
	}

	public void setWorkoutLevel(String workoutLevel) {
		this.workoutLevel = workoutLevel;
	}

	public double getWorkoutLength() {
		return workoutLength;
	}

	public void setWorkoutLength(double workoutLength) {
		this.workoutLength = workoutLength;
	}

	public int getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(int averageGrade) {
		this.averageGrade = averageGrade;
	}
	
	/*
	 * public boolean isWished() { return wished; }
	 * 
	 * public void setWished(boolean wished) { this.wished = wished; }
	 */

	@Override
	public String toString() {
		return "Workout [id=" + id + ", workoutName=" + workoutName + ", price=" + price + ", workoutGrouping="
				+ workoutGrouping + ", workoutLevel=" + workoutLevel + ", averageGrade=" + averageGrade + "]";
	}
	
}
