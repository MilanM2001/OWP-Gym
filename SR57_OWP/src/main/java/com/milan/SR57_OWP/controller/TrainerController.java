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

import com.milan.SR57_OWP.model.Trainer;
import com.milan.SR57_OWP.model.User;
import com.milan.SR57_OWP.service.TrainerService;

@Controller
@RequestMapping(value="/trainers")
public class TrainerController implements ServletContextAware{

	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@Autowired
	private TrainerService trainerService;
	
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@GetMapping
	public ModelAndView index(@RequestParam(required=false) String trainerName,
			HttpSession session) {
		if (trainerName!=null && trainerName.trim().equals(""))
			trainerName=null;
		
		List<Trainer> trainers = trainerService.find(trainerName);
		
		ModelAndView result = new ModelAndView("trainers");
		result.addObject("trainers", trainers);
		
		return result;
	}
	
	@GetMapping(value="/add")
	public ModelAndView add(HttpSession session, HttpServletResponse response) throws IOException {
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		if (registeredUser == null || !registeredUser.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL + "trainers");
			return null; 
			}
		
		List<Trainer> trainers = trainerService.findAll();
		
		ModelAndView result = new ModelAndView("addTrainers");
		result.addObject("trainers", trainers);
		
		return result;
	}
	
	@PostMapping(value = "/add")
	public void add(@RequestParam String trainerName, HttpServletResponse response, HttpSession session) throws IOException {
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		if (registeredUser == null || !registeredUser.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL+"trainers");
			return; 
		}
		
		Trainer trainer = new Trainer(trainerName);
		trainerService.saveTrainer(trainer);
		
		response.sendRedirect(bURL+"trainers");
	}
	
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response, HttpSession session) throws IOException {
		User user= (User) session.getAttribute(UserController.USER_KEY);
		if (user == null || !user.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL+"trainers");
			return;
		}
		
		trainerService.deleteTrainer(id);
		response.sendRedirect(bURL+"trainers");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id, HttpSession session) throws IOException {
		Trainer trainer = trainerService.findOneById(id);
		
		ModelAndView result = new ModelAndView("trainer");
		result.addObject("trainer", trainer);
		
		return result;
	}
	
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id, @RequestParam String trainerName, HttpServletResponse response, HttpSession session) throws IOException {
		User user= (User) session.getAttribute(UserController.USER_KEY);
		if (user == null || !user.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL+"trainers");
			return;
		}
		
		Trainer trainer = trainerService.findOneById(id);
		if (trainer == null) {
			response.sendRedirect(bURL+"trainers");
			return;
		}
		if (trainerName == null || trainerName.equals("")) {
			response.sendRedirect(bURL+"trainers/details?id=" + id);
			return;
		}
		
		trainer.setTrainerName(trainerName);
		
		trainerService.updateTrainer(trainer);
		response.sendRedirect(bURL+"trainers");
	}
	
}
