package com.milan.SR57_OWP.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milan.SR57_OWP.dao.HallDAO;
import com.milan.SR57_OWP.model.Hall;
import com.milan.SR57_OWP.service.HallService;

@Service
public class DatabaseHallServiceImpl implements HallService {

	@Autowired
	private HallDAO hallDAO;
	
	@Override
	public Hall findOneById(Long id) {
		return hallDAO.findOneById(id);
	}
	
	@Override
	public List<Hall> findAll() {
		return hallDAO.findAll();
	}
	
	@Override
	public Hall saveHall(Hall hall) {
		hallDAO.saveHall(hall);
		return hall;
	}
	
	@Override
	public List<Hall> saveHall(List<Hall> halls) {
		return null;
	}
	
	@Override
	public Hall updateHall(Hall hall) {
		hallDAO.updateHall(hall);
		return hall;
	}
	
	@Override
	public List<Hall> updateHall(List<Hall> halls) {
		return null;
	}
	
	@Override
	public Hall deleteHall(Long id) {
		Hall hall = hallDAO.findOneById(id);
		hallDAO.deleteHall(id);
		return hall;
	}
	
	@Override
	public List<Hall> find(String hallLabel, Integer capacityFrom, Integer capacityTo) {
		List<Hall> halls = hallDAO.findAll();
		
		if (hallLabel == null) {
			hallLabel = "";
		}
		
		if (capacityFrom == null) {
			capacityFrom = 0;
		}
		
		if (capacityTo == null) {
			capacityTo = Integer.MAX_VALUE;
		}
		
		List<Hall> result = new ArrayList<>();
		for (Hall itHall: halls) {
			
			if (!itHall.getHallLabel().toLowerCase().contains(hallLabel.toLowerCase())) {
				continue;
			}
			
			if (!(itHall.getCapacity() >= capacityFrom && itHall.getCapacity() <= capacityTo)) {
				continue;
			}
			
			result.add(itHall);
		}
		
		return result;
	}
	
}
