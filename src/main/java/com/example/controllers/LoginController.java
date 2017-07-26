package com.example.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.common.PasswordStorage;
import com.example.exceptions.CannotPerformOperationException;
import com.example.exceptions.TokenNotFoundException;
import com.example.exceptions.UserNotFoundException;
import com.example.models.User;
import com.example.services.MailService;
import com.example.services.UserService;

@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;
	
	@GetMapping("/login")
	public String login(@RequestParam(value="error",required=false)String error,
						@RequestParam(value="logout",required=false)String logout,
						HttpServletRequest request, Model model){
		if(error != null){
			model.addAttribute("error", "Invalid username or password.");
			String targetUrl = getRememberMeTargetUrlFromSession(request);
			if(StringUtils.hasText(targetUrl)){
				model.addAttribute("targetUrl",targetUrl);
				model.addAttribute("loginUpdate",true);
			}
		}
		
		if(logout != null){
			model.addAttribute("msg", "You've been logged out successfully.");
		}
		
		return "user/login";
	}
	
	
	
	@GetMapping("/reset_password")
	public String resetPassword(){
		
		return "user/resetPassword";
	}
	
	@PostMapping("/reset_password")
	public String resetPassword(@RequestParam("email") String userEmail, HttpServletRequest request) throws UserNotFoundException{
		User user = userService.findByEmail(userEmail);
		if(user == null){
			throw new UserNotFoundException();
		}
		String token = UUID.randomUUID().toString();
		String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		userService.updateResetTokenPasswordUser(token, user.getId());
		mailService.sendResetPasswordEmail(userEmail, user, url, token);
		
		return "user/resetPassword";
	}
	
	@GetMapping("/user/changePassword/{id}/{token}")
	public String changePassword(@PathVariable(value="id") int id, 
			@PathVariable(value="token") String token) throws UserNotFoundException, TokenNotFoundException{
		User user = userService.findById(id);
		if(user == null) {
			throw new UserNotFoundException();
		}
		if(!token.equals(user.getResetPasswordToken())) {
			throw new TokenNotFoundException();
		}
		
		
		
		return "user/changePassword";
	}
	
	@PostMapping(value="/changePassword/{id}/{token}", params={"id","token"})
	public String changePassword(@RequestParam(value="newPassword") String newPassword,
					@RequestParam(value="id") int id,
					@RequestParam(value="token") String token) throws UserNotFoundException, TokenNotFoundException{
		User user = userService.findById(id);
		if(user == null) {
			throw new UserNotFoundException();
		}
		if(!token.equals(user.getResetPasswordToken())) {
			throw new TokenNotFoundException();
		}
		try {
			PasswordStorage ps = new PasswordStorage();
			byte[] salt = ps.createSalt();
			String hash = ps.createHash(newPassword, salt);
			userService.updatePasswordUser(hash, salt.toString(), id);
			logger.info("salt "+salt.toString());
			logger.info("hash "+hash);
		} catch (CannotPerformOperationException e) {
			logger.info(e.getMessage());
		}
		return "user/changePassword";
	}
	
	@GetMapping("/changePasswordSuccessfully")
	public String changePasswordSuccessfully(){
		
		return "/user/changePasswordSuccessfully";
	}
	
	private String getRememberMeTargetUrlFromSession(HttpServletRequest request){
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		if(session != null) {
			targetUrl = session.getAttribute("targetUrl") == null ? "" : session.getAttribute("targetUrl").toString();
		}
		
		return targetUrl;
	}
}
