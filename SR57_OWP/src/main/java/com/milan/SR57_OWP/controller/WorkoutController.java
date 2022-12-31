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

import com.milan.SR57_OWP.model.Comment;
import com.milan.SR57_OWP.model.Trainer;
import com.milan.SR57_OWP.model.User;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.model.WorkoutType;
import com.milan.SR57_OWP.service.CommentService;
import com.milan.SR57_OWP.service.TrainerService;
import com.milan.SR57_OWP.service.WorkoutService;
import com.milan.SR57_OWP.service.WorkoutTypeService;

@Controller
@RequestMapping(value="/workouts")
public class WorkoutController implements ServletContextAware{
	
	public static final String WORKOUTS_FOR_WISHING = "workouts_for_wishing";
	public static final String WORKOUTS_FOR_ORDERING = "workouts_for_ordering";
	
	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@Autowired
	private WorkoutService workoutService;
	
	@Autowired
	private WorkoutTypeService workoutTypeService;
	
	@Autowired
	private TrainerService trainerService;
	 
	@Autowired
	private CommentService commentService;
	
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@GetMapping
	public ModelAndView index(
			@RequestParam(required=false) String picture,
			@RequestParam(required=false) String workoutName,
			@RequestParam(required=false) Integer priceFrom,
			@RequestParam(required=false) Integer priceTo,
			@RequestParam(required=false) Integer averageGradeFrom,
			@RequestParam(required=false) Integer averageGradeTo,
			@RequestParam(required=false) Long workoutTypeId,
			@RequestParam(required=false) String workoutGrouping,
			@RequestParam(required=false) String workoutLevel,
			HttpSession session) throws IOException {
		if (workoutName!=null && workoutName.trim().equals(""))
			workoutName=null;
		
		if (workoutGrouping!=null && workoutGrouping.trim().equals(""))
			workoutGrouping=null;
		
		if (workoutLevel!=null && workoutLevel.trim().equals(""))
			workoutLevel=null;
		
		List<Workout> workouts = workoutService.find(workoutName, priceFrom, priceTo, averageGradeFrom, averageGradeTo, workoutTypeId, workoutGrouping, workoutLevel);
		List<WorkoutType> workoutTypes = workoutTypeService.findAll();
		
		ModelAndView result = new ModelAndView("workouts");
		result.addObject("workouts", workouts);
		result.addObject("workoutTypes", workoutTypes);
		
		return result;
	}
	
	@GetMapping(value="/add")
	public ModelAndView create(HttpSession session, HttpServletResponse response) throws IOException {
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		if (registeredUser == null || !registeredUser.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL + "workouts");
			return null; 
			}
		
		List<WorkoutType> workoutTypes = workoutTypeService.findAll();
		List<Trainer> trainers = trainerService.findAll();
	
		ModelAndView result = new ModelAndView("addWorkouts");
		result.addObject("workoutTypes", workoutTypes);
		result.addObject("trainers", trainers);
		
		return result;
	}
	
	@PostMapping(value="/add")
	public void create(@RequestParam String workoutName, @RequestParam(name="trainerId") Long[] trainerIds, @RequestParam String workoutDescription,
			@RequestParam(required=false) String picture, @RequestParam(name="workoutTypeId") Long[] workoutTypeIds, @RequestParam int price, 
			@RequestParam String workoutGrouping, @RequestParam String workoutLevel,
			@RequestParam double workoutLength, @RequestParam(required=false) Integer averageGrade, HttpSession session, HttpServletResponse response) throws IOException {
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		if (registeredUser == null || !registeredUser.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL + "workouts");
			return; 
		}
		
		if (workoutName == null || workoutName.equals("") ||
				workoutDescription == null || workoutDescription.equals("") ||
				price < 25 || workoutLength < 10) {
				response.sendRedirect(bURL+"workouts/add");
				return;
			}
		
		Workout workout = new Workout(workoutName, workoutDescription, "picc", price, workoutGrouping, workoutLevel, workoutLength, 1);
		workout.setWorkoutTypes(workoutTypeService.find(workoutTypeIds));
		workout.setTrainers(trainerService.find(trainerIds));
		workoutService.saveWorkout(workout);
		
		response.sendRedirect(bURL + "workouts");
	}
	
	@GetMapping(value="/details")
	public ModelAndView details(@RequestParam Long id, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Workout workout = workoutService.findOneById(id);
		List<WorkoutType> workoutTypes = workoutTypeService.findAll();
		List<Trainer> trainers = trainerService.findAll();
		List<Comment> comments = commentService.findAll();
		
		ModelAndView result = new ModelAndView("workout");
		result.addObject("workout", workout);
		result.addObject("workoutTypes", workoutTypes);
		result.addObject("trainers", trainers);
		result.addObject("comments", comments);
		
		return result;
	}
	
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id, @RequestParam String workoutName, @RequestParam(name="trainerId") Long[] trainerIds, @RequestParam String workoutDescription, @RequestParam(required=false) String picture,
					 @RequestParam(name="workoutTypeId", required=false) Long[] workoutTypeIds, @RequestParam int price, @RequestParam String workoutGrouping, @RequestParam String workoutLevel, @RequestParam Double workoutLength, @RequestParam int averageGrade,
					 HttpSession session, HttpServletResponse response) throws IOException {	
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		if (registeredUser == null || !registeredUser.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL+"workouts");
			return; 
		}
		
		Workout workout = workoutService.findOneById(id);
		if (workout == null) {
			response.sendRedirect(bURL+"workouts");
			return;
		}
		if (workoutName == null || workoutName.equals("") ||
			workoutDescription == null || workoutDescription.equals("") ||
			price < 25 || workoutLength < 10) {
			response.sendRedirect(bURL+"workouts/details?id=" + id);
			return;
		}
		
		workout.setWorkoutName(workoutName);
		workout.setTrainers(trainerService.find(trainerIds));
		workout.setWorkoutDescription(workoutDescription);
		workout.setPicture(picture);
		workout.setWorkoutTypes(workoutTypeService.find(workoutTypeIds));
		workout.setPrice(price);
		workout.setWorkoutGrouping(workoutGrouping);
		workout.setWorkoutLevel(workoutLevel);
		workout.setWorkoutLength(workoutLength);
		workout.setAverageGrade(averageGrade);
		
		workoutService.updateWorkout(workout);
		response.sendRedirect(bURL+"workouts");
		
	}
	
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpSession session, HttpServletResponse response) throws IOException {		
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		if (registeredUser == null || !registeredUser.getUserRole().equals("Administrator")) {
			response.sendRedirect(bURL + "workouts");
			return; 
		}
		
		workoutService.deleteWorkout(id);
		
		response.sendRedirect(bURL+"workouts");
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value="/wished/add")
	@ResponseBody
	public void addToWished(@RequestParam Long idWorkout, HttpSession session, HttpServletResponse response) throws IOException {
		List<Workout> forWishing = (List<Workout>) session.getAttribute(WORKOUTS_FOR_WISHING);
		Workout workout = workoutService.findOneById(idWorkout);
		forWishing.add(workout);
		
		response.sendRedirect(bURL + "forWishing.html");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value="/wished")
	@ResponseBody
	public List<Workout> addWished(HttpSession session) {
		return (List<Workout>) session.getAttribute(WORKOUTS_FOR_WISHING);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value="/wished/remove")
	@ResponseBody
	public void removeFromWished(@RequestParam Long idWorkout, HttpSession session, HttpServletResponse response) throws IOException {
		List<Workout> forWishing = (List<Workout>) session.getAttribute(WORKOUTS_FOR_WISHING);
		
		for (Workout workout : forWishing) {
			if (workout.getId().equals(idWorkout)) {
				forWishing.remove(workout);
				break;
			}
		}
		response.sendRedirect(bURL+"forWishing.html");
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value="/ordered/add")
	@ResponseBody
	public void addToOrdered(@RequestParam Long idWorkout, HttpSession session, HttpServletResponse response) throws IOException {
		List<Workout> forOrdering = (List<Workout>) session.getAttribute(WORKOUTS_FOR_ORDERING);
		Workout workout = workoutService.findOneById(idWorkout);
		forOrdering.add(workout);
		
		response.sendRedirect(bURL + "forOrdering.html");
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value="/ordered")
	@ResponseBody
	public List<Workout> addOrdered(HttpSession session) {
		return (List<Workout>) session.getAttribute(WORKOUTS_FOR_ORDERING);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value="/ordered/remove")
	@ResponseBody
	public void removeFromOrdered(@RequestParam Long idWorkout, HttpSession session, HttpServletResponse response) throws IOException {
		List<Workout> forOrdering = (List<Workout>) session.getAttribute(WORKOUTS_FOR_ORDERING);
		
		for (Workout workout : forOrdering) {
			if (workout.getId().equals(idWorkout)) {
				forOrdering.remove(workout);
				break;
			}
		}
		response.sendRedirect(bURL+"forOrdering.html");
	}

}
