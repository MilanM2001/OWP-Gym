package com.milan.SR57_OWP.dao;

import java.util.ArrayList;
import java.util.List;

import com.milan.SR57_OWP.model.Hall;

public interface HallDAO {

	public Hall findOneById(Long id);
	
	public List<Hall> findAll();
	
	public int saveHall(Hall hall);
	
	public int [] save(ArrayList<Hall> halls);
	
	public int updateHall(Hall hall);
	
	public int deleteHall(Long id);
	
	public List<Hall> find(String hallLabel, Integer capacityFrom, Integer capacityTo);
	
}
