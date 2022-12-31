package com.milan.SR57_OWP.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
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

import com.milan.SR57_OWP.model.ShoppingCart;
import com.milan.SR57_OWP.model.User;
import com.milan.SR57_OWP.model.WishList;
import com.milan.SR57_OWP.service.ShoppingCartService;
import com.milan.SR57_OWP.service.UserService;
import com.milan.SR57_OWP.service.WishListService;

@Controller
@RequestMapping(value = "/users")
public class UserController implements ServletContextAware {

	public static final String USER_KEY = "user";
	
	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@Autowired 
	private UserService userService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private WishListService wishListService;
	
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
			@RequestParam(required=false) String userName,
			@RequestParam(required=false) String userRole,
			HttpSession session) throws IOException {
		if (userName!=null && userName.trim().equals(""))
			userName=null;
		
		if (userRole!=null && userRole.trim().equals(""))
			userRole=null;
		
		List<User> users = userService.find(userName, userRole);
		
		ModelAndView result = new ModelAndView("users");
		result.addObject("users", users);
		
		return result;
	}
	
	@GetMapping(value = "/login")
	public void getLogin(@RequestParam(required = false) String userName, @RequestParam(required = false) String password,
	 HttpSession session, HttpServletResponse response) throws IOException {
		postLogin(userName, password, session, response);
	}
	
	@PostMapping(value = "/login")
	@ResponseBody
	public void postLogin(@RequestParam(required = false) String userName, @RequestParam(required = false) String password,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		User user = userService.findOne(userName, password);
		String error = "";
		
		if (user == null || user.isActive())
			error = "Incorrect Login Information or Your Account is Blocked";
		
		if (!error.equals("")) {
			PrintWriter out;
			out = response.getWriter();
			File htmlFile = new File("C:\\Users\\milju\\OneDrive\\Desktop\\sr57_owp_projekat\\SR57_OWP\\src\\main\\resources\\static\\error.html");
			Document doc = Jsoup.parse(htmlFile, "UTF-8");
			
			Element body = doc.select("body").first();
			
			if(!error.equals("")) {
				Element divError = new Element(Tag.valueOf("div"), "").text(error);
				body.appendChild(divError);
			}
			
			Element loginForm = new Element(Tag.valueOf("form"), "").attr("method", "post").attr("action", "users/login");
			Element table = new Element(Tag.valueOf("table"), "");
			Element caption = new Element(Tag.valueOf("caption"), "").text("User Login");
			Element trUserName = new Element(Tag.valueOf("tr"), "");
			Element thUserName = new Element(Tag.valueOf("th"), "").text("Username:");
			Element tdUserName = new Element(Tag.valueOf("td"), "").appendChild(new Element(Tag.valueOf("input"), "").attr("type", "text").attr("name", "userName"));
			Element trPassword = new Element(Tag.valueOf("tr"), "");
			Element thPassword = new Element(Tag.valueOf("th"), "").text("Password:");
			Element tdPassword = new Element(Tag.valueOf("td"), "").appendChild(new Element(Tag.valueOf("input"), "").attr("type", "password").attr("name", "password"));
			Element trSubmit = new Element(Tag.valueOf("tr"), "");
			Element thSubmit = new Element(Tag.valueOf("th"), "");
			Element tdSubmit = new Element(Tag.valueOf("td"), "").appendChild(new Element(Tag.valueOf("input"), "").attr("type", "submit").attr("value", "Login"));
			
			trUserName.appendChild(thUserName);
			trUserName.appendChild(tdUserName);
			trPassword.appendChild(thPassword);
			trPassword.appendChild(tdPassword);
			trSubmit.appendChild(thSubmit);
			trSubmit.appendChild(tdSubmit);

			table.appendChild(caption);
			table.appendChild(trUserName);
			table.appendChild(trPassword);
			table.appendChild(trSubmit);
			
			loginForm.appendChild(table);

			body.appendChild(loginForm);
			
			out.write(doc.html());
			return;
		}
		
		if (session.getAttribute(USER_KEY) != null)
			error = "User is already Registered, please Log out first<br/>";
		
		if(!error.equals("")) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			out = response.getWriter();
			
			StringBuilder retVal = new StringBuilder();
			retVal.append("<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "	<meta charset=\"UTF-8\">\r\n"
					+ "	<base href=\"/SR57_OWP/\">	\r\n" + "	<title>User Login</title>\r\n"
					+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StyleForm.css\"/>\r\n"
					+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StyleHorizontalMenu.css\"/>\r\n"
					+ "</head>\r\n" + "<body>\r\n" + "	<ul>\r\n"
					+ "		<li><a href=\"registration.html\">Register</a></li>\r\n" + "	</ul>\r\n");
			if (!error.equals(""))
				retVal.append("	<div>" + error + "</div>\r\n");
			retVal.append("	<a href=\"login.html\">Return</a>\r\n" + "	<br/>\r\n" + "</body>\r\n" + "</html>"
				+ "	<a href=\"users/logout\">Logout</a>\r\n" + "	<br/>\r\n" + "</body>\r\n" + "</html>");

			out.write(retVal.toString());
			return;
		}
		
		session.setAttribute(USER_KEY, user);
		
		response.sendRedirect(bURL + "workouts");
	}
	
	@GetMapping(value="/logout")
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {	
		//User user = (User) request.getSession().getAttribute(USER_KEY);
		request.getSession().removeAttribute(USER_KEY);
		request.getSession().invalidate();
		response.sendRedirect(bURL+"login.html");
	}
	
	@PostMapping(value = "/registration")
	public void registration(@RequestParam(required=false) Long id, @RequestParam String userName, @RequestParam String password,
			@RequestParam String email, @RequestParam String firstName, @RequestParam String lastName,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth, @RequestParam(required = true) String address,
			@RequestParam String phoneNumber, @RequestParam(required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfRegistration,
			@RequestParam(required=false) String userRole, HttpSession session, HttpServletResponse response) throws IOException {
		User user = new User(userName, password, email, firstName, lastName, dateOfBirth, address, phoneNumber, LocalDate.now(), "Member");
		userService.saveUser(user);
		ShoppingCart shoppingCart = new ShoppingCart(userName);
		shoppingCartService.save(shoppingCart);
		WishList wishList = new WishList(userName);
		wishListService.save(wishList);
		
		response.sendRedirect(bURL + "login.html");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		User user = userService.findOneById(id);
		
		ModelAndView result = new ModelAndView("user"); 
		result.addObject("user", user); 

		return result;
	}
	
	@GetMapping(value="/detailsPersonal")
	@ResponseBody
	public ModelAndView detailsPersonal(@RequestParam Long id) {	
		User user = userService.findOneById(id);
		
		ModelAndView result = new ModelAndView("userInfo"); 
		result.addObject("user", user); 

		return result;
	}
	
	//Menjanje samo uloge Korisnika od strane Admina
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void edit(@RequestParam Long id, @RequestParam(required=false) String userName, @RequestParam(required=false) String password, @RequestParam(required=false) String email, @RequestParam(required=false) String firstName, @RequestParam(required=false) String lastName,
					 @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate dateOfBirth, @RequestParam(required=false) String address, @RequestParam(required=false) String phoneNumber, @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate dateOfRegistration,
					 @RequestParam String userRole, @RequestParam(required=false) String active, HttpServletResponse response, HttpSession session) throws IOException {	
		User user = userService.findOneById(id);
		User registeredUser = (User) session.getAttribute(UserController.USER_KEY);
		if(user != null) {
			if(userName != null && !userName.trim().equals(""))
				user.setUserName(userName);
			if(password != null && !password.trim().equals(""))
				user.setPassword(password);
			if(email != null && !email.trim().equals(""))
				user.setEmail(email);
			if(firstName != null && !firstName.trim().equals(""))
				user.setFirstName(firstName);
			if(lastName != null && !lastName.trim().equals(""))
				user.setLastName(lastName);
			if(dateOfBirth != null)
				user.setDateOfBirth(dateOfBirth);
			if(address != null && !address.trim().equals(""))
				user.setAddress(address);
			if(phoneNumber != null && !phoneNumber.trim().equals(""))
				user.setPhoneNumber(phoneNumber);
			if(dateOfRegistration != null)
				user.setDateOfRegistration(dateOfRegistration);
			if(userRole != null)
				user.setUserRole(userRole);
			if (registeredUser.getUserRole().equals("Administrator")) {
				user.setActive(active != null);
			}
		
		}
		
		User saved = userService.updateUser(user);
		response.sendRedirect(bURL+"users");
		
	}
	
	//Menjanje licnih informacija
	@SuppressWarnings("unused")
	@PostMapping(value="/editPersonal")
	public void editPersonal(@RequestParam Long id, @RequestParam(required=false) String userName, @RequestParam String password, @RequestParam String email, @RequestParam String firstName, @RequestParam String lastName,
					 @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate dateOfBirth, @RequestParam String address, @RequestParam String phoneNumber, @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate dateOfRegistration,
					 HttpServletResponse response) throws IOException {	
		User user = userService.findOneById(id);

		if(user != null) {
			if(userName != null && !userName.trim().equals(""))
				user.setUserName(userName);
			if(password != null && !password.trim().equals(""))
				user.setPassword(password);
			if(email != null && !email.trim().equals(""))
				user.setEmail(email);
			if(firstName != null && !firstName.trim().equals(""))
				user.setFirstName(firstName);
			if(lastName != null && !lastName.trim().equals(""))
				user.setLastName(lastName);
			if(dateOfBirth != null)
				user.setDateOfBirth(dateOfBirth);
			if(address != null && !address.trim().equals(""))
				user.setAddress(address);
			if(phoneNumber != null && !phoneNumber.trim().equals(""))
				user.setPhoneNumber(phoneNumber);
			if(dateOfRegistration != null)
				user.setDateOfRegistration(dateOfRegistration);
			
			}
		
		User saved = userService.updateUserPersonal(user);
		response.sendRedirect(bURL+"workouts");
		}

	
	@PostMapping(value="/delete")
	public void deleteUser(@RequestParam Long id, HttpServletResponse response) throws IOException {				
		userService.deleteUser(id);

		response.sendRedirect(bURL+"users");
	}
	
	@GetMapping(value="/userList")
	@ResponseBody
	public Map<String, Object> getUserList(HttpSession session, HttpServletResponse response) {
		List<User> users = userService.findAllMembers();
		
		Map<String, Object> answer = new LinkedHashMap<>();
		answer.put("status", "ok");
		answer.put("users", users);
		return answer;
	}
	
}
