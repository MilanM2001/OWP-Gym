package com.milan.SR57_OWP.dao;

import java.util.ArrayList;
import java.util.List;

import com.milan.SR57_OWP.model.Trainer;


public interface TrainerDAO {
	
	public Trainer findOneById(Long id);
	
	public List<Trainer> findAll();

	public int saveTrainer(Trainer trainer);
	
	public int [] save(ArrayList<Trainer> trainer);
	
	public int updateTrainer(Trainer trainer);
	
	public int deleteTrainer(Long id);
	
	public List<Trainer> find(String trainerName);
	
	public List<Trainer> findByName(String trainerName);

}
