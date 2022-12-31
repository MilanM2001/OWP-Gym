package com.milan.SR57_OWP.service;

import java.util.List;

import com.milan.SR57_OWP.model.ShoppingCart;

public interface ShoppingCartService {
	
	ShoppingCart findOneById(Long id);
	
	List<ShoppingCart> findAll();
	
	ShoppingCart save(ShoppingCart shoppingCart);
	
	ShoppingCart update(ShoppingCart shoppingCart);
	
	ShoppingCart delete(Long id);
	
	ShoppingCart findOneByUsername(String username);

}
