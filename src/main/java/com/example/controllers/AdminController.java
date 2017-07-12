package com.example.controllers;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.common.UserStatus;
import com.example.exceptions.EmailExistedException;
import com.example.exceptions.UserAccessDeniedException;
import com.example.exceptions.UserNotFoundException;
import com.example.models.Role;
import com.example.models.User;
import com.example.services.RoleService;
import com.example.services.UserService;

@Controller
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@ModelAttribute("allRoles")
	public List<Role> getAllRole(){
		
		return roleService.findAll();
	}
	@ModelAttribute("allUsers")
	public List<User> getAllUser(){
		
		return this.userService.findAll();
	}
	@ModelAttribute("allStatus")
	public List<UserStatus> getAllStatus(){
		List<UserStatus> userStatus = Arrays.asList(UserStatus.values());
		
		return userStatus;
	}
	
	@RequestMapping("/admin/home")
	public String adminHome(Model model) throws Exception{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByEmail(auth.getName());
		if(user == null){
			
			throw new UserNotFoundException();
		} else {
			//logger.info("->>>>>>"+auth.getAuthorities()); 
			if(auth.getAuthorities().toString().equals("[ADMIN]")){
				model.addAttribute("username","Hello "+user.getUsername()+"("+user.getEmail()+")");

				return "admin/home";
			}
			
			throw new UserAccessDeniedException();
		}
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public String handleUserNotFoundException(){
		
		return "user/login";
	}
	
	@ExceptionHandler(UserAccessDeniedException.class)
	public String handleUserAccessDeniedException(){
		
		return "admin/access-denied";
	}
	
	@RequestMapping(value="/admin/home", params={"deleteUser"})
	public String deleteUser(final User user, final HttpServletRequest req){
		final Integer userId= Integer.valueOf(req.getParameter("deleteUser"));
		userService.deleteUser(userId.intValue());
		
		return "redirect:/admin/home";
	}
	
	@RequestMapping(value="/admin/home", params={"edit"})
	public String editUser(final User user,HttpServletRequest req, final Model model){
		final Integer userId= Integer.valueOf(req.getParameter("edit"));
		User u = userService.findById(userId.intValue()); 
		model.addAttribute("user",u);
		
		return "admin/home";
	}
	
	@RequestMapping(value="/admin/home", params={"save"})
	public String saveUser(@ModelAttribute(value="user") User user) throws Exception{
		List<User> users = userService.findAll();
		for(User u : users){
			if(u.getId()!=user.getId()&&u.getEmail().equals(user.getEmail())){
				throw new EmailExistedException();
			}
		}
		userService.updateUser(user.getId(),user.getUsername(), user.getEmail(), user.getRole().getId(), user.getUserStatus());
		
		return "redirect:/admin/home";
	}
	@ExceptionHandler(EmailExistedException.class)
	public String handleEmailExistedException(Model model){
		model.addAttribute("emailError", "Email have existed. Edit have failed.");
		
		return "admin/home";
	}
	
	private boolean isRememberMeAuthenticated(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null){
			
			return false;
		}
		
		return RememberMeAuthenticationToken.class.isAssignableFrom(auth.getClass());
	}
	
	private void setRememberMeTargetUrlToSession(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session != null){
			session.setAttribute("targetUrl", "/admin/home");
		}
	}
	
}
