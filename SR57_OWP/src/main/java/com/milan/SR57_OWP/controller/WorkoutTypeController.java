package com.milan.SR57_OWP.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.milan.SR57_OWP.model.User;
import com.milan.SR57_OWP.model.WorkoutType;
import com.milan.SR57_OWP.service.WorkoutTypeService;

@Controller
@RequestMapping(value = "/workoutTypes")
public class WorkoutTypeController implements ServletContextAware {
	
	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@Autowired
	private WorkoutTypeService workoutTypeService;
	
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@GetMapping
	public ModelAndView index(@RequestParam(required=false) String workoutTypeName,
							  @RequestParam(required=false) String workoutTypeDescription,
							  HttpSession session) {
		if (workoutTypeName!=null && workoutTypeName.trim().equals(""))
				workoutTypeName=null;
		
		List<WorkoutType> workoutTypes = workoutTypeService.find(workoutTypeName, workoutTypeDescription);
		
		ModelAndView result = new ModelAndView("workoutTypes");
		result.addObject("workoutTypes", workoutTypes);
		
		return result;
	}
	
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response, HttpSession session) throws IOException {
		User user= (User) session.getAttribute(UserController.USER_KEY);
		if (user == null || !user.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL+"workoutTypes");
			return;
		}
		
		workoutTypeService.deleteWorkoutType(id);
		response.sendRedirect(bURL+"workoutTypes");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id, HttpSession session) throws IOException {
		WorkoutType workoutType = workoutTypeService.findOneById(id);
		
		ModelAndView result = new ModelAndView("workoutType");
		result.addObject("workoutType", workoutType);
		
		return result;
	}
	
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id, @RequestParam String workoutTypeName, @RequestParam String workoutTypeDescription, HttpServletResponse response, HttpSession session) throws IOException {
		User user= (User) session.getAttribute(UserController.USER_KEY);
		if (user == null || !user.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL+"workoutTypes");
			return;
		}
		
		WorkoutType workoutType = workoutTypeService.findOneById(id);
		if (workoutType == null) {
			response.sendRedirect(bURL+"workoutTypes");
			return;
		}
		if (workoutTypeName == null || workoutTypeName.equals("")) {
			response.sendRedirect(bURL+"workoutTypes/details?id=" + id);
			return;
		}
		if (workoutTypeDescription == null || workoutTypeDescription.equals("")) {
			response.sendRedirect(bURL+"workoutTypes/details?id=" + id);
		}
		
		workoutType.setWorkoutTypeName(workoutTypeName);
		workoutType.setWorkoutTypeDescription(workoutTypeDescription);
		
		workoutTypeService.updateWorkoutType(workoutType);
		response.sendRedirect(bURL+"workoutTypes");
	}
	
	@GetMapping(value = "/workoutTypeList")
	@ResponseBody
	public Map<String, Object> getWorkoutTypeList(HttpSession session, HttpServletResponse response){
		List<WorkoutType> workoutTypes = workoutTypeService.findAll();
		
		Map<String, Object> answer = new LinkedHashMap<>();
		answer.put("status", "ok");
		answer.put("workoutTypes", workoutTypes);
		return answer;
	}
	
}
