package com.milan.SR57_OWP.service;

import java.util.List;

import com.milan.SR57_OWP.model.WorkoutType;

public interface WorkoutTypeService {

	WorkoutType findOneById(Long id);
	
	List<WorkoutType> findAll();
	
	List<WorkoutType> find(Long[] ids);
	
	WorkoutType saveWorkoutType(WorkoutType workoutType);
	
	List<WorkoutType> saveWorkoutType(List<WorkoutType> workoutTypes);
	
	WorkoutType updateWorkoutType(WorkoutType workoutType);
	
	List<WorkoutType> updateWorkoutType(List<WorkoutType> workoutTypes);
	
	WorkoutType deleteWorkoutType(Long id);
	
	void delete(List<Long> ids);
	
	List<WorkoutType> find(String workoutTypeName, String workoutTypeDescription);
	
	List<WorkoutType> find(String workoutTypeName);
	
}
