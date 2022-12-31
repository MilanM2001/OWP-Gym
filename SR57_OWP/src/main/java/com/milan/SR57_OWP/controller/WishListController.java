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

import com.milan.SR57_OWP.model.User;
import com.milan.SR57_OWP.model.WishList;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.service.WishListService;
import com.milan.SR57_OWP.service.WorkoutService;

@Controller
@RequestMapping(value = "/wishLists")
public class WishListController implements ServletContextAware {

	public static final String USER_KEY = "users";
	public static final String WISH_LISTS_KEY = "wish_lists";
	public static final String WISH_LIST = "wish_list";

	@Autowired
	private ServletContext servletContext;
	private String bURL;

	@Autowired
	private WishListService wishListService;

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
		List<WishList> wishLists = wishListService.findAll();

		ModelAndView result = new ModelAndView("wishLists");
		result.addObject("wishLists", wishLists);

		return result;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/wishWorkout")
	public void wishWorkout(@RequestParam(required=false) String username, HttpSession session, HttpServletResponse response) throws IOException {
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		
		WishList wishList = wishListService.findOneByUsername(registeredUser.getUserName());
		if (wishList == null) {
			response.sendRedirect(bURL + "wishLists");
		}
		List<Workout> forWishing = (List<Workout>) session.getAttribute(WorkoutController.WORKOUTS_FOR_WISHING);

		for (Workout workout : forWishing) {
			if (wishList != null) {
				wishList.getWishedWorkouts().add(workout);
				wishList = wishListService.update(wishList);
			}
		}

		session.setAttribute(WorkoutController.WORKOUTS_FOR_WISHING, new ArrayList<Workout>());

		response.sendRedirect(bURL + "workouts");
	}

	@GetMapping(value = "/details")
	@ResponseBody
	public ModelAndView details(@RequestParam String username) {
		WishList wl = wishListService.findOneByUsername(username);

		ModelAndView result = new ModelAndView("wishList");
		result.addObject("wishList", wl);

		return result;
	}

	@PostMapping(value = "/unwishWorkout")
	public void unwishWorkout(@RequestParam Long idWorkout, @RequestParam String username, HttpServletResponse response)
			throws IOException {
		WishList wl = wishListService.findOneByUsername(username);

		if (wl != null) {
			Workout workout = workoutService.findOneById(idWorkout);
			for (Workout itWorkout : wl.getWishedWorkouts()) {
				if (itWorkout.getId().equals(workout.getId())) {
					wl.getWishedWorkouts().remove(itWorkout);
					break;
				}
			}

			wl = wishListService.update(wl);
			/*
			 * if (wl != null) { workout.setWished(false);
			 * workoutService.updateWorkout(workout); }
			 */
		}

		response.sendRedirect(bURL + "wishLists");
	}

}
