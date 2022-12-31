package com.milan.SR57_OWP.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milan.SR57_OWP.dao.ShoppingCartDAO;
import com.milan.SR57_OWP.model.ShoppingCart;
import com.milan.SR57_OWP.service.ShoppingCartService;

@Service
public class DatabaseShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ShoppingCartDAO shoppingCartDAO;
	
	@Override
	public ShoppingCart findOneById(Long id) {
		return shoppingCartDAO.findOneById(id);
	}
	
	@Override
	public List<ShoppingCart> findAll() {
		return shoppingCartDAO.findAll();
	}
	
	@Override
	public ShoppingCart save(ShoppingCart shoppingCart) {
		shoppingCartDAO.save(shoppingCart);
		return shoppingCart;
	}
	
	@Override
	public ShoppingCart update(ShoppingCart shoppingCart) {
		shoppingCartDAO.update(shoppingCart);
		return shoppingCart;
	}
	
	@Override
	public ShoppingCart delete(Long id) {
		ShoppingCart shoppingCart = shoppingCartDAO.findOneById(id);
		shoppingCartDAO.delete(id);
		return shoppingCart;
	}
	
	@Override
	public ShoppingCart findOneByUsername(String username) {
		return shoppingCartDAO.findOneByUsername(username);
	}
	
}
