package com.milan.SR57_OWP.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.milan.SR57_OWP.model.ShoppingCart;
import com.milan.SR57_OWP.model.User;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.service.ShoppingCartService;
import com.milan.SR57_OWP.service.WorkoutService;

@Controller
@RequestMapping(value = "/shoppingCarts")
public class ShoppingCartController implements ServletContextAware {
	
	public static final String USER_KEY = "users";
	public static final String SHOPPING_CARTS_KEY = "shopping_carts";
	public static final String SHOPPING_CART = "shopping_cart";
	
	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private WorkoutService workoutService;
	
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
		List<ShoppingCart> shoppingCarts = shoppingCartService.findAll();
		
		ModelAndView result = new ModelAndView("shoppingCarts");
		result.addObject("shoppingCarts", shoppingCarts);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/orderWorkout")
	public void orderWorkout(@RequestParam(required=false) String username, HttpSession session, HttpServletResponse response) throws IOException {
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		
		ShoppingCart shoppingCart = shoppingCartService.findOneByUsername(registeredUser.getUserName());
		if (shoppingCart == null) {
			response.sendRedirect(bURL + "shoppingCarts");
		}
		List<Workout> forOrdering = (List<Workout>) session.getAttribute(WorkoutController.WORKOUTS_FOR_ORDERING);
		
		for (Workout workout : forOrdering) {
			if (shoppingCart != null) {
				shoppingCart.getOrderedWorkouts().add(workout);
				shoppingCart = shoppingCartService.update(shoppingCart);
			}
		}
		
		session.setAttribute(WorkoutController.WORKOUTS_FOR_ORDERING, new ArrayList<Workout>());
		
		response.sendRedirect(bURL + "workouts");
	}
	
	@GetMapping(value = "/details")
	@ResponseBody
	public ModelAndView details(@RequestParam String username) {
		ShoppingCart sc = shoppingCartService.findOneByUsername(username);
		
		ModelAndView result = new ModelAndView("shoppingCart");
		result.addObject("shoppingCart", sc);
		
		return result;
	}
	
	@PostMapping(value = "/unorderWorkout")
	public void unorderWorkout(@RequestParam Long idWorkout, @RequestParam String username, HttpServletResponse response) throws IOException { 
		ShoppingCart sc = shoppingCartService.findOneByUsername(username);
		
		if (sc != null) {
			Workout workout = workoutService.findOneById(idWorkout);
				for (Workout itWorkout : sc.getOrderedWorkouts()) {
					if (itWorkout.getId().equals(workout.getId())) {
						sc.getOrderedWorkouts().remove(itWorkout);
						break;
					}
				}
				
				sc = shoppingCartService.update(sc);
		}
		
		response.sendRedirect(bURL + "workouts");
	}

}
