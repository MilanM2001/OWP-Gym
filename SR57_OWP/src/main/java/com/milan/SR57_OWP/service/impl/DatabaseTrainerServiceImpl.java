package com.milan.SR57_OWP.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milan.SR57_OWP.dao.TrainerDAO;
import com.milan.SR57_OWP.model.Trainer;
import com.milan.SR57_OWP.service.TrainerService;

@Service
public class DatabaseTrainerServiceImpl implements TrainerService {
	
	@Autowired 
	private TrainerDAO trainerDAO;
	
	@Override
	public Trainer findOneById(Long id) {
		return trainerDAO.findOneById(id);
	}
	
	@Override
	public List<Trainer> findAll() {
		return trainerDAO.findAll();
	}
	
	@Override
	public List<Trainer> find(Long[] ids) {
		List<Trainer> result = new ArrayList<>();
		for (Long id: ids) {
			Trainer trainer = trainerDAO.findOneById(id);
			result.add(trainer);
		}
		return result;
	}
	
	@Override
	public Trainer saveTrainer(Trainer trainer) {
		trainerDAO.saveTrainer(trainer);
		return trainer;
	}
	
	@Override
	public List<Trainer> saveTrainer(List<Trainer> trainers) {
		return null;
	}
	
	@Override
	public Trainer updateTrainer(Trainer trainer) {
		trainerDAO.updateTrainer(trainer);
		return trainer;
	}
	
	@Override
	public List<Trainer> updateTrainer(List<Trainer> trainers) {
		return null;
	}
	
	@Override
	public Trainer deleteTrainer(Long id) {
		Trainer trainer = findOneById(id);
		if (trainer != null) {
			trainerDAO.deleteTrainer(id);
		}
		return trainer;
	}
	
	@Override
	public void delete(List<Long> ids) {
		
	}
	
	@Override
	public List<Trainer> find(String trainerName) {
		List<Trainer> trainers = trainerDAO.findAll();
		
		if (trainerName == null) {
			trainerName = "";
		}
		
		List<Trainer> result = new ArrayList<>();
		for (Trainer itTrainer: trainers) {
			
			if (!itTrainer.getTrainerName().toLowerCase().contains(trainerName.toLowerCase())) {
				continue;
			}
			
			result.add(itTrainer);
		}
		
		return result;
		
	}

}
