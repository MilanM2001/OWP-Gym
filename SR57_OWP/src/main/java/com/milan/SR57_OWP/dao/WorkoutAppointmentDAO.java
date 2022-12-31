package com.milan.SR57_OWP.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import com.milan.SR57_OWP.model.WorkoutAppointment;

public interface WorkoutAppointmentDAO {

	public WorkoutAppointment findOneById(Long id);
	
	public List<WorkoutAppointment> findAll();
	
	public int saveWorkoutAppointment(WorkoutAppointment workoutAppointment);
	
	public int updateWorkoutAppointment(WorkoutAppointment workoutAppointment);

	public int deleteWorkoutAppointment(Long id);
	
	public List<WorkoutAppointment> find(Long hallId, Long workoutId, LocalDateTime dateAndTimeFrom, LocalDateTime dateAndTimeTo);
	
	public List<WorkoutAppointment> find(HashMap<String, Object> argumentMap);
	
}
