package com.milan.SR57_OWP.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milan.SR57_OWP.dao.WorkoutTypeDAO;
import com.milan.SR57_OWP.model.WorkoutType;
import com.milan.SR57_OWP.service.WorkoutTypeService;

@Service
public class DatabaseWorkoutTypeServiceImpl implements WorkoutTypeService{

	@Autowired 
	private WorkoutTypeDAO workoutTypeDAO;
	
	@Override
	public WorkoutType findOneById(Long id) {
		return workoutTypeDAO.findOneById(id);
	}
	
	@Override
	public List<WorkoutType> findAll() {
		return workoutTypeDAO.findAll();
	}
	
	@Override
	public List<WorkoutType> find(Long[] ids) {
		List<WorkoutType> result = new ArrayList<>();
		for (Long id: ids) {
			WorkoutType workoutType = workoutTypeDAO.findOneById(id);
			result.add(workoutType);
		}
		return result;
	}
	
	@Override
	public WorkoutType saveWorkoutType(WorkoutType workoutType) {
		workoutTypeDAO.saveWorkoutType(workoutType);
		return workoutType;
	}
	
	@Override
	public List<WorkoutType> saveWorkoutType(List<WorkoutType> workoutTypes) {
		return null;
	}
	
	@Override
	public WorkoutType updateWorkoutType(WorkoutType workoutType) {
		workoutTypeDAO.updateWorkoutType(workoutType);
		return workoutType;
	}
	
	@Override
	public List<WorkoutType> updateWorkoutType(List<WorkoutType> workoutTypes) {
		return null;
	}
	
	@Override
	public WorkoutType deleteWorkoutType(Long id) {
		WorkoutType workoutType = findOneById(id);
		if (workoutType != null) {
			workoutTypeDAO.deleteWorkoutType(id);
		}
		return workoutType;
	}
	
	@Override
	public void delete(List<Long> ids) {
		
	}
	
	@Override
	public List<WorkoutType> find(String workoutTypeName, String workoutTypeDescription) {
		List<WorkoutType> workoutTypes = workoutTypeDAO.findAll();
		
		if (workoutTypeName == null) {
			workoutTypeName = "";
		}
		
		if (workoutTypeDescription == null) {
			workoutTypeDescription = "";
		}
		
		List<WorkoutType> result = new ArrayList<>();
		for (WorkoutType itWorkoutType: workoutTypes) {
			
			if (!itWorkoutType.getWorkoutTypeName().toLowerCase().contains(workoutTypeName.toLowerCase())) {
				continue;
			}
			
			if (!itWorkoutType.getWorkoutTypeDescription().toLowerCase().contains(workoutTypeDescription.toLowerCase())) {
				continue;
			}
			
			result.add(itWorkoutType);
		}
		
		return result;
		
	}
	
	@Override
	public List<WorkoutType> find(String workoutTypeName) {
		if (workoutTypeName == null) {
			return workoutTypeDAO.findAll();
		}
		return workoutTypeDAO.find(workoutTypeName);
	}
	
}
