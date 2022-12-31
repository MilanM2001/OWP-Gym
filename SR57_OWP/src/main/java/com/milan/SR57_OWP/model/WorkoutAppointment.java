package com.milan.SR57_OWP.model;

import java.time.LocalDateTime;

public class WorkoutAppointment {

	private Long id;
	private Hall hall;
	private Workout workout;
	private LocalDateTime dateOfWorkout;
	
	public WorkoutAppointment() {}

	public WorkoutAppointment(Long id, Hall hall, Workout workout,
			LocalDateTime dateOfWorkout) {
		super();
		this.id = id;
		this.hall = hall;
		this.workout = workout;
		this.dateOfWorkout = dateOfWorkout;
	}

	public WorkoutAppointment(Hall hall, Workout workout, LocalDateTime dateOfWorkout) {
		super();
		this.hall = hall;
		this.workout = workout;
		this.dateOfWorkout = dateOfWorkout;
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
		WorkoutAppointment other = (WorkoutAppointment) obj;
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

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public Workout getWorkout() {
		return workout;
	}

	public void setWorkout(Workout workout) {
		this.workout = workout;
	}

	public LocalDateTime getDateOfWorkout() {
		return dateOfWorkout;
	}

	public void setDateOfWorkout(LocalDateTime dateOfWorkout) {
		this.dateOfWorkout = dateOfWorkout;
	}

	@Override
	public String toString() {
		return "WorkoutAppointment [id=" + id + ", hall=" + hall + ", workout=" + workout + ", dateOfWorkout="
				+ dateOfWorkout + "]";
	}

	
	
}
