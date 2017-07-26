package com.example.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.common.UserStatus;
import com.example.exceptions.TokenNotFoundException;
import com.example.exceptions.UserNotFoundException;
import com.example.models.User;
import com.example.services.MailService;
import com.example.services.UserService;

@Controller
public class RegistrationController {
	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	@Autowired
	UserService userService;
	@Autowired
	MailService mailService;
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public String registration(Model model){
		model.addAttribute("user",new User());
		
		return "user/registration";
	}

	@RequestMapping(value="/registration",method=RequestMethod.POST)
	public String createNewUser(@Valid @ModelAttribute(value="user") User user, 
			BindingResult bindingResult, 
			Model model, HttpServletRequest request){
		User userExists = userService.findByEmail(user.getEmail());
		if(userExists != null){
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}

		if(bindingResult.hasErrors()){
			model.addAttribute("messageError","Please fill the form correctly.");

			return "user/registration";
		} else {
			String token = UUID.randomUUID().toString();
			String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			user.setResetPasswordToken(token);
			userService.saveUser(user);
			mailService.sendActivationAccountEmail(user.getEmail(), user, url, token);
			model.addAttribute("registerSuccessfully", "You have registed successfully.");
			model.addAttribute("user",user);
			
			return "user/registration";
		}
	}
	
	@RequestMapping(value="/activationSuccessful/{id}/{token}", method=RequestMethod.GET)
	public String activeAccountGet(@PathVariable("id") int id,
			@PathVariable("token") String token) throws UserNotFoundException, TokenNotFoundException{
		User user = userService.findById(id);
		if(user == null) {
			throw new UserNotFoundException();
		}
		if(!token.equals(user.getResetPasswordToken())){
			throw new TokenNotFoundException();
		}
		userService.updateStatusUser(UserStatus.ACTIVE.name(), id);
		
		return "user/activationSuccessful";
	}
}	
