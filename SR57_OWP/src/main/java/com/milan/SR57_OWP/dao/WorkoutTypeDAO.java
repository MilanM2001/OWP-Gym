package com.milan.SR57_OWP.dao;

import java.util.ArrayList;
import java.util.List;

import com.milan.SR57_OWP.model.WorkoutType;

public interface WorkoutTypeDAO {

	public WorkoutType findOneById(Long id);
	
	public List<WorkoutType> findAll();

	public int saveWorkoutType(WorkoutType workoutType);
	
	public int [] save(ArrayList<WorkoutType> workoutTypes);
	
	public int updateWorkoutType(WorkoutType workoutType);
	
	public int deleteWorkoutType(Long id);
	
	public List<WorkoutType> find(String workoutTypeName, String workoutTypeDescription);
	
	public List<WorkoutType> find(String workoutTypeName);
	
}
