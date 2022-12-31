package com.milan.SR57_OWP.service;

import java.util.List;

import com.milan.SR57_OWP.model.MembershipCard;

public interface MembershipCardService {

	MembershipCard findOneById(Long id);
	
	MembershipCard saveMembershipCard(MembershipCard membershipCard);
	
	List<MembershipCard> saveMembershipCard(List<MembershipCard> membershipCard);
	
	MembershipCard updateMembershipCard(MembershipCard membershipCard);
	
	List<MembershipCard> updateMembershipCard(List<MembershipCard> membershipCrad);
	
	MembershipCard deleteMembershipCard(Long id);
	
	List<MembershipCard> deleteAll(MembershipCard membershipCard);
	
	List<MembershipCard> deleteAll(List<MembershipCard> membershipCard);
	
	void delete(List<Long> ids);
	
	List<MembershipCard> findAll();
	
}
