package com.milan.SR57_OWP.service;

import java.util.List;

import com.milan.SR57_OWP.model.Trainer;

public interface TrainerService {

	Trainer findOneById(Long id);
	
	List<Trainer> findAll();
	
	List<Trainer> find(Long[] ids);
	
	Trainer saveTrainer(Trainer trainer);
	
	List<Trainer> saveTrainer(List<Trainer> trainers);
	
	Trainer updateTrainer(Trainer trainer);
	
	List<Trainer> updateTrainer(List<Trainer> trainers);
	
	Trainer deleteTrainer(Long id);
	
	void delete(List<Long> ids);
	
	List<Trainer> find(String trainerName);
	
}
