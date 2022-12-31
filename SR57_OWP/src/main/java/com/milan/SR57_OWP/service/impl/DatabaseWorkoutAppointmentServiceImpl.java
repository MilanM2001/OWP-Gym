package com.milan.SR57_OWP.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milan.SR57_OWP.dao.WorkoutAppointmentDAO;
import com.milan.SR57_OWP.model.Hall;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.model.WorkoutAppointment;
import com.milan.SR57_OWP.service.WorkoutAppointmentService;

@Service
public class DatabaseWorkoutAppointmentServiceImpl implements WorkoutAppointmentService{

	@Autowired
	private WorkoutAppointmentDAO workoutAppointmentDAO;
	
	@Override
	public WorkoutAppointment findOneById(Long id) {
		return workoutAppointmentDAO.findOneById(id);
	}
	
	@Override
	public List<WorkoutAppointment> findAll() {
		return workoutAppointmentDAO.findAll();
	}
	
	@Override
	public WorkoutAppointment saveWorkoutAppointment(WorkoutAppointment workoutAppointment) {
		workoutAppointmentDAO.saveWorkoutAppointment(workoutAppointment);
		return workoutAppointment;
	}
	
	@Override
	public List<WorkoutAppointment> saveWorkoutAppointment(List<WorkoutAppointment> workoutAppointments) {
		return null;
	}
	
	@Override
	public WorkoutAppointment updateWorkoutAppointment(WorkoutAppointment workoutAppointment) {
		workoutAppointmentDAO.updateWorkoutAppointment(workoutAppointment);
		return workoutAppointment;
	}
	
	@Override
	public List<WorkoutAppointment> updateWorkoutAppointment(List<WorkoutAppointment> workoutAppointments) {
		return null;
	}
	
	@Override
	public WorkoutAppointment deleteWorkoutAppointment(Long id) {
		WorkoutAppointment workoutAppointment = findOneById(id);
		if (workoutAppointment != null) {
			workoutAppointmentDAO.deleteWorkoutAppointment(id);
		}
		return workoutAppointment;
	}
	
	@Override
	public List<WorkoutAppointment> deleteAll(Workout workout, Hall hall) {
		return null;
	}
	
	@Override
	public List<WorkoutAppointment> deleteAll(List<Workout> workouts, List<Hall> halls) {
		return null;
	}
	
	@Override
	public void delete(List<Long> ids) {
	}
	
	@Override
	public List<WorkoutAppointment> find(Long hallId, Long workoutId, LocalDateTime dateAndTimeFrom, LocalDateTime dateAndTimeTo) {
		return workoutAppointmentDAO.find(hallId, workoutId, dateAndTimeFrom, dateAndTimeTo);
	}
	
	@Override
	public List<WorkoutAppointment> findByWorkoutId(Long workoutId) {
		return null;
	}
	
}
