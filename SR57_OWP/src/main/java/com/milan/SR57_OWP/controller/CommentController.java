package com.milan.SR57_OWP.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.milan.SR57_OWP.model.Comment;
import com.milan.SR57_OWP.model.User;
import com.milan.SR57_OWP.model.Workout;
import com.milan.SR57_OWP.service.CommentService;
import com.milan.SR57_OWP.service.WorkoutService;

@Controller
@RequestMapping(value = "/comments")
public class CommentController implements ServletContextAware {
	
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
	
	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@Autowired
	private WorkoutService workoutService;
	
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
	public ModelAndView index() {
		List<Comment> comments = commentService.findAll();
		
		ModelAndView result = new ModelAndView("comments");
		result.addObject("comments", comments);
		
		return result;
	}
	
	@GetMapping(value="/add")
	public ModelAndView add(@RequestParam(name="workoutId", required=false) Long workoutId, HttpSession session, HttpServletResponse response) throws IOException {
		
		List<Workout> workouts = workoutService.findAllBought();
		
		ModelAndView result = new ModelAndView("addComment");
		result.addObject("workouts", workouts);
		
		return result;
	}
	
	@PostMapping(value="/add")
	public void create(@RequestParam String commentText, @RequestParam Integer commentGrade, @RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfCreation, @RequestParam(required=false) String username, @RequestParam Long workoutId, @RequestParam(required=false) String commentStatus, @RequestParam(required=false) Boolean anonymous, HttpSession session, HttpServletResponse response) throws IOException {
	
		Workout workout = workoutService.findOneById(workoutId);	
		if (workout == null) {
			response.sendRedirect(bURL+"workouts");
			return;
		}
		
		User user = (User) session.getAttribute(UserController.USER_KEY);
		
		Comment comment = new Comment(commentText, commentGrade, LocalDateTime.now(), user.getUserName(), workout, "Waiting", anonymous);
		commentService.saveComment(comment);
		
		response.sendRedirect(bURL + "workouts");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam String commentText, HttpSession session, HttpServletResponse response) throws IOException {
		Comment comment = commentService.findOneByCommentText(commentText);
		
		ModelAndView result = new ModelAndView("comment");
		result.addObject("comment", comment);
		
		return result;
	}
	
	@PostMapping(value="/edit")
	public void edit(@RequestParam Long id, @RequestParam String commentStatus, HttpServletResponse response, HttpSession session) throws IOException {
		Comment comment = commentService.findOneById(id);
	
		comment.setCommentStatus(commentStatus);
		
		commentService.updateComment(comment);
		response.sendRedirect(bURL+"comments");
	}
	
}
