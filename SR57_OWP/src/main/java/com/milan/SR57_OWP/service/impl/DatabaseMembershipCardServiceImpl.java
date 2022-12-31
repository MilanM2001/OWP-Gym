package com.milan.SR57_OWP.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milan.SR57_OWP.dao.MembershipCardDAO;
import com.milan.SR57_OWP.model.MembershipCard;
import com.milan.SR57_OWP.service.MembershipCardService;

@Service
public class DatabaseMembershipCardServiceImpl implements MembershipCardService{

	@Autowired
	private MembershipCardDAO membershipCardDAO;
	
	@Override
	public MembershipCard findOneById(Long id) {
		return membershipCardDAO.findOneById(id);
	}
	
	@Override
	public List<MembershipCard> findAll() {
		return membershipCardDAO.findAll();
	}
	
	@Override
	public MembershipCard saveMembershipCard(MembershipCard membershipCard) {
		membershipCardDAO.saveMembershipCard(membershipCard);
		return membershipCard;
	}
	
	@Override
	public List<MembershipCard> saveMembershipCard(List<MembershipCard> membershipCard) {
		return null;
	}
	
	@Override
	public MembershipCard updateMembershipCard(MembershipCard membershipCard) {
		membershipCardDAO.updateMembershipCard(membershipCard);
		return membershipCard;
	}
	
	@Override
	public List<MembershipCard> updateMembershipCard(List<MembershipCard> membershipCard) {
		return null;
	}
	
	@Override
	public MembershipCard deleteMembershipCard(Long id) {
		MembershipCard membershipCard = membershipCardDAO.findOneById(id);
		membershipCardDAO.deleteMembershipCard(id);
		return membershipCard;
	}
	
	@Override
	public List<MembershipCard> deleteAll(MembershipCard membershipCard) {
		return null;
	}
	
	@Override
	public List<MembershipCard> deleteAll(List<MembershipCard> membershipCard) {
		return null;
	}
	
	@Override
	public void delete(List<Long> ids) {
		
	}
	
}
