package com.milan.SR57_OWP.dao;

import java.util.List;

import com.milan.SR57_OWP.model.ShoppingCart;

public interface ShoppingCartDAO {
	
	public ShoppingCart findOneById(Long id);
	
	public ShoppingCart findOneByUsername(String username);
	
	public List<ShoppingCart> findAll();
	
	public int save(ShoppingCart shoppingCart);
	
	public int update(ShoppingCart shoppingCart);
	
	public int delete(Long id); 

}
