package com.milan.SR57_OWP.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.milan.SR57_OWP.model.Hall;
import com.milan.SR57_OWP.model.User;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.model.WorkoutAppointment;
import com.milan.SR57_OWP.service.HallService;
import com.milan.SR57_OWP.service.WorkoutAppointmentService;
import com.milan.SR57_OWP.service.WorkoutService;

@Controller
@RequestMapping(value = "/workoutAppointments")
public class WorkoutAppointmentController {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
	public static final String minDate = LocalDate.MIN.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	public static final String maxDate = LocalDate.MAX.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
	public static final String minTime = LocalTime.MIN.format(DateTimeFormatter.ofPattern("HH:mm"));
	public static final String maxTime = LocalTime.MAX.format(DateTimeFormatter.ofPattern("HH:mm"));
	
	@Autowired
	private WorkoutAppointmentService workoutAppointmentService;
	
	@Autowired
	private HallService hallService;
	
	@Autowired
	private WorkoutService workoutService;
	
	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@PostConstruct
	public void init() {
		bURL = servletContext.getContextPath() + "/";
	}
	
	@GetMapping
	public ModelAndView Index(
			@RequestParam(required=false) Long hallId,
			@RequestParam(required=false) Long workoutId,
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate dateFrom,
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime timeFrom,
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate dateTo,
			@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime timeTo,
			HttpSession session) throws IOException {
		
			LocalDateTime dateAndTimeFrom = null;
			if(dateFrom!=null || timeFrom!=null)
				dateAndTimeFrom = LocalDateTime.of(dateFrom, timeFrom);
			LocalDateTime dateAndTimeTo = null;
			if(dateTo!=null || timeTo!=null)
				dateAndTimeTo = LocalDateTime.of(dateTo, timeTo);
	
			List<WorkoutAppointment> workoutAppointments = workoutAppointmentService.find(hallId, workoutId, dateAndTimeFrom, dateAndTimeTo);
			List<Workout> workouts = workoutService.findAll();
			List<Hall> halls = hallService.findAll();
			
			ModelAndView result = new ModelAndView("workoutAppointments");
			result.addObject("workoutAppointments", workoutAppointments);
			result.addObject("workouts", workouts);
			result.addObject("halls", halls);
			
			return result;
	}
	
	@GetMapping(value="/add")
	public ModelAndView add(
			@RequestParam(name="hallId", required=false) Long hallId,
			@RequestParam(name="workoutId", required=false) Long workoutId,
			HttpSession session, HttpServletResponse response) throws IOException {
		
			User user = (User) session.getAttribute(UserController.USER_KEY);
			if (user == null || user.getUserRole().equals("Administrator") == false) {
				response.sendRedirect(bURL+"workoutAppointments");
				return null;
			}
			
			List<Hall> halls = hallService.findAll();
			List<Workout> workouts = workoutService.findAll();
			
			ModelAndView result = new ModelAndView("addWorkoutAppointments");
			result.addObject("halls", halls);
			result.addObject("workouts", workouts);
			
			return result;
			}
			
	@PostMapping(value="/add")
	public void add(
			@RequestParam Long hallId, @RequestParam Long workoutId,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime time,
			HttpSession session, HttpServletResponse response) throws IOException {
		
			User user = (User) session.getAttribute(UserController.USER_KEY);
			if (user == null || !user.getUserRole().equals("Administrator")) {
				response.sendRedirect(bURL+"workoutAppointments");
				return;
			}
			
			LocalDateTime dateOfWorkout = LocalDateTime.of(date, time);
			
			Hall hall = hallService.findOneById(hallId);
			if (hall == null) {
				response.sendRedirect(bURL+"workoutAppointments");
				return;
			}
			
			Workout workout = workoutService.findOneById(workoutId);
			if (workout == null) {
				response.sendRedirect(bURL+"workoutAppointments");
				return;
			}
			
			WorkoutAppointment workoutAppointment = new WorkoutAppointment(hall, workout, dateOfWorkout);
			workoutAppointmentService.saveWorkoutAppointment(workoutAppointment);
			
			response.sendRedirect(bURL+"workoutAppointments");
	}
	
	@PostMapping(value="/edit")
	public void edit(
			@RequestParam Long id,
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date, 
			@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.TIME) LocalTime time,
			@RequestParam Long hallId, @RequestParam Long workoutId,
			HttpSession session, HttpServletResponse response) throws IOException {
		
			User user = (User) session.getAttribute(UserController.USER_KEY);
			if (user == null || !user.getUserRole().equals("Administrator")) {
				response.sendRedirect(bURL+"workoutAppointments");
				return;
			}
			
			WorkoutAppointment workoutAppointment = workoutAppointmentService.findOneById(id);
			if (workoutAppointment == null) {
				response.sendRedirect(bURL+"workoutAppointments");
				return;
			}
			
			LocalDateTime dateOfWorkout = LocalDateTime.of(date, time);
			
			Hall hall = hallService.findOneById(hallId);
			if (hall == null) {
				response.sendRedirect(bURL+"workoutAppointments");
				return;
			}
			
			Workout workout = workoutService.findOneById(workoutId);
			if (workout == null) {
				response.sendRedirect(bURL+"workoutAppointments");
				return;
			}
			
			workoutAppointment.setDateOfWorkout(dateOfWorkout);
			workoutAppointment.setHall(hall);
			workoutAppointment.setWorkout(workout);
			
			workoutAppointmentService.updateWorkoutAppointment(workoutAppointment);
			
			response.sendRedirect(bURL+"workoutAppointments");
			}
	
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id,
			HttpSession session, HttpServletResponse response) throws IOException {
		
			User user = (User) session.getAttribute(UserController.USER_KEY);
			if (user == null || !user.getUserRole().equals("Administrator")) {
				response.sendRedirect(bURL+"workoutAppointments");
				return;
			}
			
			workoutAppointmentService.deleteWorkoutAppointment(id);
			
			response.sendRedirect(bURL+"workoutAppointments");
			
	}
	
	@GetMapping(value="/details")
	public ModelAndView details(@RequestParam Long id,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		WorkoutAppointment workoutAppointment = workoutAppointmentService.findOneById(id);
		
		if (workoutAppointment == null) {
			response.sendRedirect(bURL+"workoutAppointments");
			return null;
		}
		
		List<Hall> halls = hallService.findAll();
		List<Workout> workouts = workoutService.findAll();
		
		ModelAndView result = new ModelAndView("workoutAppointment");
		result.addObject("workoutAppointment", workoutAppointment);
		result.addObject("halls", halls);
		result.addObject("workouts", workouts);
		
		return result;
	}
			
	
	
	

}
