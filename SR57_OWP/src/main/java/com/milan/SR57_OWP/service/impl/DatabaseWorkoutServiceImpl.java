package com.milan.SR57_OWP.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milan.SR57_OWP.dao.WorkoutDAO;
import com.milan.SR57_OWP.model.Trainer;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.model.WorkoutType;
import com.milan.SR57_OWP.service.WorkoutService;

@Service
public class DatabaseWorkoutServiceImpl implements WorkoutService{

	@Autowired
	private WorkoutDAO workoutDAO;
	
	@Override
	public Workout findOneById(Long id) {
		return workoutDAO.findOneById(id);
	}
	
	@Override
	public List<Workout> findAll() {
		return workoutDAO.findAll();
	}
	
	@Override
	public Workout saveWorkout(Workout workout) {
		workoutDAO.saveWorkout(workout);
		return workout;
	}
	
	@Override
	public List<Workout> saveWorkout(List<Workout> workouts) {
		return null;
	}
	
	@Override
	public Workout updateWorkout(Workout workout) {
		workoutDAO.updateWorkout(workout);
		return workout;
	}
	
	@Override
	public List<Workout> updateWorkout(List<Workout> workouts) {
		return null;
	}
	
	@Override
	public Workout deleteWorkout(Long id) {
		Workout workout = workoutDAO.findOneById(id);
		workoutDAO.deleteWorkout(id);
		return workout;
	}
	
	@Override
	public List<Workout> deleteAll(WorkoutType workoutType, Trainer trainer) {
		return null;
	}
	
	@Override
	public void delete(List<Long> ids) {

	}
	
	@Override
	public List<Workout> findByWorkoutTypeId(Long workoutTypeId) {
		return null;
	}
	
	@Override
	public List<Workout> findByTrainerId(Long trainerId) {
		return null;
	}
	
	@Override
	public List<Workout> find(String workoutName, Integer priceFrom, Integer priceTo, Integer averageGradeFrom, Integer averageGradeTo, Long workoutTypeId, String workoutGrouping, String workoutLevel) {
		List<Workout> workouts = workoutDAO.findAll();
		
		if (workoutName == null) {
			workoutName = "";
		}
		
		if (priceFrom == null) {
			priceFrom = 0;
		}
		
		if (priceTo == null) {
			priceTo = Integer.MAX_VALUE;
		}
		
		if (averageGradeFrom == null) {
			averageGradeFrom = 0;
		}
		
		if (averageGradeTo == null) {
			averageGradeTo = Integer.MAX_VALUE;
		}
		
		if (workoutTypeId == null) {
			workoutTypeId = 0L;
		}
		
		if (workoutGrouping == null) {
			workoutGrouping = "";
		}
		
		if (workoutLevel == null) {
			workoutLevel = "";
		}
		
		List<Workout> result = new ArrayList<>();
		for (Workout itWorkout: workouts) {
			
			if (!itWorkout.getWorkoutName().toLowerCase().contains(workoutName.toLowerCase())) {
				continue;
			}
			
			if (!(itWorkout.getPrice() >= priceFrom && itWorkout.getPrice() <= priceTo)) {
				continue;
			}
			
			if (!(itWorkout.getAverageGrade() >= averageGradeFrom && itWorkout.getAverageGrade() <= averageGradeTo)) {
				continue;
			}
			
			if (workoutTypeId > 0) { 
				boolean found = false;
				for (WorkoutType itWorkoutType: itWorkout.getWorkoutTypes()) {
					if (itWorkoutType.getId() == workoutTypeId) {
						found = true;
						break;
					}
				}
				if (!found) {
					continue;
				}
			}
			
			if (!itWorkout.getWorkoutGrouping().toLowerCase().contains(workoutGrouping.toLowerCase())) {
				continue;
			}
			
			if (!itWorkout.getWorkoutLevel().toLowerCase().contains(workoutLevel.toLowerCase())) {
				continue;
			}
			
			result.add(itWorkout);		
		}
		
		return result;	
	}
	
	@Override
	public List<Workout> findAllBought() {
		return workoutDAO.findAllBought();
	}
	
	
}
