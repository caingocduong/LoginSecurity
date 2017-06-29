package com.example.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.exceptions.UserAccessDeniedException;
import com.example.exceptions.UserNotFoundException;
import com.example.models.User;
import com.example.services.UserService;

@Controller
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private UserService userService;
	
	@ModelAttribute("allUsers")
	public List<User> getAllUser(){
		
		return this.userService.findAll();
	}
	
	@GetMapping("/admin/home")
	public String adminHome(Model model) throws Exception{
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User user = userService.findByEmail(auth.getName());
//		if(user == null){
//			
//			throw new UserNotFoundException();
//		} else {
//			//logger.info("->>>>>>"+auth.getAuthorities()); 
//			if(auth.getAuthorities().toString().equals("[ADMIN]")){
//				model.addAttribute("username","Hello "+user.getUsername()+"("+user.getEmail()+")");
//
//				return "admin/home";
//			}
//			
//			throw new UserAccessDeniedException();
//		}
		return "admin/home";
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
	public String deleteUser(final User user, final BindingResult bindingResult, final HttpServletRequest req){
		final Integer userId= Integer.valueOf(req.getParameter("deleteUser"));
		userService.deleteUser(userId.intValue());
		
		return "redirect:/admin/home";
	}
	
	@RequestMapping(value="/admin/home", params={"edit"})
	public String editUser(final User user, final BindingResult bindingResult,HttpServletRequest req, final Model model){
		final Integer userId= Integer.valueOf(req.getParameter("edit"));
		User u = userService.findById(userId.intValue()); 
		model.addAttribute("user",u);
		
		return "admin/home";
	}
	
	@RequestMapping(value="/admin/home", params={"save"})
	public String saveUser(final BindingResult bindingResult, @ModelAttribute(value="user") User user){
		logger.info("aaa");
		return "redirect:/admin/home";
	}
}
