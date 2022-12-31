package com.milan.SR57_OWP.service;

import java.util.List;

import com.milan.SR57_OWP.model.WishList;

public interface WishListService {

	WishList findOneById(Long id);
	
	List<WishList> findAll();
	
	WishList save(WishList wishList);
	
	WishList update(WishList wishList);
	
	WishList delete(Long id);
	
	WishList findOneByUsername(String username);
	
}
