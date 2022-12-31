package com.milan.SR57_OWP.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.milan.SR57_OWP.model.Hall;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.model.WorkoutAppointment;

public interface WorkoutAppointmentService {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	WorkoutAppointment findOneById(Long id);
	
	List<WorkoutAppointment> findAll();
	
	WorkoutAppointment saveWorkoutAppointment(WorkoutAppointment workoutAppointment);
	
	List<WorkoutAppointment> saveWorkoutAppointment(List<WorkoutAppointment> workoutAppointments);
	
	WorkoutAppointment updateWorkoutAppointment(WorkoutAppointment workoutAppointment);
	
	List<WorkoutAppointment> updateWorkoutAppointment(List<WorkoutAppointment> workoutAppointments);
	
	WorkoutAppointment deleteWorkoutAppointment(Long id);
	
	List<WorkoutAppointment> deleteAll(Workout workout, Hall hall);
	
	List<WorkoutAppointment> deleteAll(List<Workout> workouts, List<Hall> halls);
	
	void delete(List<Long> ids);
	
	List<WorkoutAppointment> findByWorkoutId(Long workoutId);
	
	List<WorkoutAppointment> find(Long hallId, Long workoutId, LocalDateTime dateAndTimeFrom, LocalDateTime dateAndTimeTo);
	
}
