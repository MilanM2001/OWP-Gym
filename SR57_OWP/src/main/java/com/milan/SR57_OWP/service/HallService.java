package com.milan.SR57_OWP.service;

import java.util.List;

import com.milan.SR57_OWP.model.Hall;

public interface HallService {

	Hall findOneById(Long id);
	
	List<Hall> findAll();
	
	Hall saveHall(Hall hall);
	
	List<Hall> saveHall(List<Hall> halls);
	
	Hall updateHall(Hall hall);
	
	List<Hall> updateHall(List<Hall> halls);
	
	Hall deleteHall(Long id);
	
	List<Hall> find(String hallLabel, Integer capacityFrom, Integer capacityTo);
	
}
