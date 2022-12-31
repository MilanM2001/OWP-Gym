package com.milan.SR57_OWP.model;

public class WorkoutType {

	private Long id;
	private String workoutTypeName;
	private String workoutTypeDescription;
	
	public WorkoutType() {}

	public WorkoutType(Long id, String workoutTypeName, String workoutTypeDescription) {
		super();
		this.id = id;
		this.workoutTypeName = workoutTypeName;
		this.workoutTypeDescription = workoutTypeDescription;
	}
	
	public WorkoutType(String workoutTypeName) {
		super();
		this.workoutTypeName = workoutTypeName;
	}

	public WorkoutType(Long id, String workoutTypeName) {
		super();
		this.id = id;
		this.workoutTypeName = workoutTypeName;
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
		WorkoutType other = (WorkoutType) obj;
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

	public String getWorkoutTypeName() {
		return workoutTypeName;
	}

	public void setWorkoutTypeName(String workoutTypeName) {
		this.workoutTypeName = workoutTypeName;
	}

	public String getWorkoutTypeDescription() {
		return workoutTypeDescription;
	}

	public void setWorkoutTypeDescription(String workoutTypeDescription) {
		this.workoutTypeDescription = workoutTypeDescription;
	}
	
	@Override
	public String toString() {
		return "WorkoutType [id=" + id + ", workoutTypeName=" + workoutTypeName + ", workoutTypeDescription=" + workoutTypeDescription + "]";
	}
	
}
