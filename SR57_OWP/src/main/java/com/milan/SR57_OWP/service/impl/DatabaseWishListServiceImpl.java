package com.milan.SR57_OWP.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milan.SR57_OWP.dao.WishListDAO;
import com.milan.SR57_OWP.model.WishList;
import com.milan.SR57_OWP.service.WishListService;

@Service
public class DatabaseWishListServiceImpl implements WishListService{
	
	@Autowired
	private WishListDAO wishListDAO;
	
	@Override
	public WishList findOneById(Long id) {
		return wishListDAO.findOneById(id);
	}

	@Override
	public List<WishList> findAll() {
		return wishListDAO.findAll();
	}

	@Override
	public WishList save(WishList wishList) {
		wishListDAO.save(wishList);
		return wishList;
	}

	@Override
	public WishList update(WishList wishList) {
		wishListDAO.update(wishList);
		return wishList;
	}

	@Override
	public WishList delete(Long id) {
		WishList wishList = wishListDAO.findOneById(id);
		wishListDAO.delete(id);
		return wishList;
	}

	@Override
	public WishList findOneByUsername(String username) {
		return wishListDAO.findOneByUsername(username);
	}
	
}
