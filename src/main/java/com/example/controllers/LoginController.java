package com.example.controllers;



import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.models.User;
import com.example.services.UserService;

@Controller
public class LoginController {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login(){

		return "user/login";
	}

	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public String registration(Model model){
		model.addAttribute("user",new User());
		
		return "user/registration";
	}

	@RequestMapping(value="/registration",method=RequestMethod.POST)
	public String createNewUser(@Valid @ModelAttribute(value="user") User user, BindingResult bindingResult, Model model){
		User userExists = userService.findByEmail(user.getEmail());
		if(userExists != null){
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}

		if(bindingResult.hasErrors()){
			model.addAttribute("messageError","Please fill the form correctly.");

			return "user/registration";
		} else {
			userService.saveUser(user);
			model.addAttribute("messageSuccess","User has been registerd successfully.");
			model.addAttribute("user",user);
			return "user/registration";
		}
	}
	
}
