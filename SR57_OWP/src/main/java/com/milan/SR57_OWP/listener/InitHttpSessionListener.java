package com.milan.SR57_OWP.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.stereotype.Component;

import com.milan.SR57_OWP.controller.ShoppingCartController;
import com.milan.SR57_OWP.controller.WishListController;
import com.milan.SR57_OWP.controller.WorkoutController;
import com.milan.SR57_OWP.model.Workout;

@Component
public class InitHttpSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("Initializing Session HttpSessionListener...");
		
		HttpSession session  = event.getSession();
		System.out.println("Session User ID " + session.getId());
		
		List<Workout> forWishing = new ArrayList<Workout>();
		List<Workout> forOrdering = new ArrayList<Workout>();
		
		String usernameWL = "";
		String usernameSC = "";
		
		session.setAttribute(WorkoutController.WORKOUTS_FOR_WISHING, forWishing);
		session.setAttribute(WorkoutController.WORKOUTS_FOR_ORDERING, forOrdering);
		
		session.setAttribute(WishListController.WISH_LIST, usernameWL);
		session.setAttribute(ShoppingCartController.SHOPPING_CART, usernameSC);
		
		System.out.println("Success HttpSessionListener!");
	}
	
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("Deleting Session HttpSessionListener...");
		
		System.out.println("Success HttpSessionListener!");
	}
	
}
