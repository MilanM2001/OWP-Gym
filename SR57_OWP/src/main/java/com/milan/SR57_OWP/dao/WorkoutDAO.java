package com.milan.SR57_OWP.dao;

import java.util.List;

import com.milan.SR57_OWP.model.Workout;

public interface WorkoutDAO {

	public Workout findOneById(Long id);
	
	public int saveWorkout(Workout workout);
	
	public int updateWorkout(Workout workout);
	
	public int deleteWorkout(Long id);
	
	public List<Workout> findAll();
	
	public List<Workout> find(String workoutName, Integer priceFrom, Integer priceTo, Integer averageGradeFrom, Integer averageGradeTo, Long workoutTypeId, String workoutGrouping, String workoutLevel);
	
	public List<Workout> findAllBought();
	
}
