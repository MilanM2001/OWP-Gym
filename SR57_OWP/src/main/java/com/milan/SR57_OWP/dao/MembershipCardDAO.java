package com.milan.SR57_OWP.dao;

import java.util.List;

import com.milan.SR57_OWP.model.MembershipCard;

public interface MembershipCardDAO {

	public MembershipCard findOneById(Long id);
	
	public int saveMembershipCard(MembershipCard membershipCard);
	
	public int updateMembershipCard(MembershipCard membershipCard);
	
	public int deleteMembershipCard(Long id);
	
	public List<MembershipCard> findAll();
	
}
