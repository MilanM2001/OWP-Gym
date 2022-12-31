package com.milan.SR57_OWP.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.milan.SR57_OWP.model.MembershipCard;
import com.milan.SR57_OWP.model.User;
import com.milan.SR57_OWP.service.MembershipCardService;

@Controller
@RequestMapping(value = "/membershipCards")
public class MembershipCardController implements ServletContextAware {
	
	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@Autowired
	private MembershipCardService membershipCardService;
	
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@GetMapping
	public ModelAndView index() {
		List<MembershipCard> membershipCards = membershipCardService.findAll();
		
		ModelAndView result = new ModelAndView("membershipCards");
		result.addObject("membershipCards", membershipCards);
		
		return result;
	}
	
	@GetMapping(value="/add")
	public ModelAndView add(HttpSession session, HttpServletResponse response) throws IOException {
		ModelAndView result = new ModelAndView("addMembershipCard");
		
		return result;
	}
	
	@PostMapping(value="/add")
	public void create(@RequestParam Integer discount, @RequestParam Double cardPoints, 
					   @RequestParam String cardUsername, @RequestParam String cardStatus, 
					   HttpSession session, HttpServletResponse response) throws IOException {
		User user = (User) session.getAttribute(UserController.USER_KEY);
		
		MembershipCard membershipCard = new MembershipCard(0, 10.0, user.getUserName(), "Waiting");
		membershipCardService.saveMembershipCard(membershipCard);
		
		response.sendRedirect(bURL + "membershipCards");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id, HttpServletResponse response, HttpSession session) throws IOException {
		MembershipCard membershipCard = membershipCardService.findOneById(id);
		
		ModelAndView result = new ModelAndView("membershipCard");
		result.addObject("membershipCard", membershipCard);
		
		return result;
	}
	
	@PostMapping(value="/edit")
	public void edit(@RequestParam Long id, @RequestParam String cardStatus, HttpServletResponse response, HttpSession session) throws IOException {
		MembershipCard membershipCard = membershipCardService.findOneById(id);
		
		membershipCard.setCardStatus(cardStatus);
		
		membershipCardService.updateMembershipCard(membershipCard);
		response.sendRedirect(bURL+"membershipCards");
	}
	
}
		

