package com.milan.SR57_OWP.service;

import java.util.List;

import com.milan.SR57_OWP.model.Trainer;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.model.WorkoutType;

public interface WorkoutService {

	Workout findOneById(Long id);
	
	List<Workout> findAll();
	
	Workout saveWorkout(Workout workout);
	
	List<Workout> saveWorkout(List<Workout> workouts);
	
	Workout updateWorkout(Workout workout);
	
	List<Workout> updateWorkout(List<Workout> workouts);
	
	Workout deleteWorkout(Long id);
	
	List<Workout> deleteAll(WorkoutType workoutType, Trainer trainer);
	
	void delete(List<Long> ids);
	
	List<Workout> find(String workoutName, Integer priceFrom, Integer priceTo, Integer averageGradeFrom, Integer averageGradeTo, Long workoutTypeId, String workoutGrouping, String workoutLevel);
	
	List<Workout> findByWorkoutTypeId(Long workoutTypeId);
	
	List<Workout> findByTrainerId(Long trainerId);
	
	List<Workout> findAllBought();
	
}
