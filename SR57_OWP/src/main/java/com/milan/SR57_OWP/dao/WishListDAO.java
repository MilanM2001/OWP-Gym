package com.milan.SR57_OWP.dao;

import java.util.List;

import com.milan.SR57_OWP.model.WishList;

public interface WishListDAO {
	
	public WishList findOneById(Long id);
	
	public WishList findOneByUsername(String username);
	
	public List<WishList> findAll();
	
	public int save(WishList wishList);
	
	public int update(WishList wishList);
	
	public int delete(Long id);

}
