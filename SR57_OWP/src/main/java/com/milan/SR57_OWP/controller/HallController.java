package com.milan.SR57_OWP.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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

import com.milan.SR57_OWP.model.Hall;
import com.milan.SR57_OWP.model.User;
import com.milan.SR57_OWP.service.HallService;

@Controller
@RequestMapping(value = "/halls")
public class HallController implements ServletContextAware {

	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@Autowired
	private HallService hallService;
	
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@GetMapping
	public ModelAndView index(
			@RequestParam(required=false) String hallLabel,
			@RequestParam(required=false) Integer capacityFrom,
			@RequestParam(required=false) Integer capacityTo,
			HttpSession session) {
		if(hallLabel!=null && hallLabel.trim().equals(""))
			hallLabel=null;
		
		List<Hall> halls = hallService.find(hallLabel, capacityFrom, capacityTo);
		
		ModelAndView result = new ModelAndView("halls");
		result.addObject("halls", halls);
		
		return result;
	}
	
	@GetMapping(value="/add")
	public ModelAndView add(HttpSession session, HttpServletResponse response) throws IOException {
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		if (registeredUser == null || !registeredUser.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL + "halls");
			return null; 
			}
		
		List<Hall> halls = hallService.findAll();
		
		ModelAndView result = new ModelAndView("addHalls");
		result.addObject("halls", halls);
		
		return result;
	}
	
	@PostMapping(value = "/add")
	public void add(@RequestParam String hallLabel, @RequestParam int capacity, HttpServletResponse response, HttpSession session) throws IOException {
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		if (registeredUser == null || !registeredUser.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL+"halls");
			return; 
		}
		
		Hall hall = new Hall(hallLabel, capacity);
		hallService.saveHall(hall);
		
		response.sendRedirect(bURL+"halls");
	}
	
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response, HttpSession session) throws IOException {
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		if (registeredUser == null || !registeredUser.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL+"halls");
			return; 
		}
		
		hallService.deleteHall(id);
		
		response.sendRedirect(bURL+"halls");
	}
	
	@GetMapping(value = "/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Hall hall = hallService.findOneById(id);
		
		ModelAndView result = new ModelAndView("hall");
		result.addObject("hall", hall);
		
		return result;
	}
	
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id, @RequestParam(required=false) String hallLabel, @RequestParam int capacity, HttpServletResponse response, HttpSession session) throws IOException {
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		if (registeredUser == null || !registeredUser.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL+"halls");
			return; 
		}
		
		Hall hall = hallService.findOneById(id);
		if (hall == null) {
			response.sendRedirect(bURL+"halls");
			return;
		}
		
		hall.setHallLabel(hallLabel);
		hall.setCapacity(capacity);
		
		hallService.updateHall(hall);
		response.sendRedirect(bURL+"halls");
		
	}
	
}
