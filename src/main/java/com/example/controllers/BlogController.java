package com.example.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.models.Post;
import com.example.services.PostService;


@Controller
public class BlogController {
	@Autowired
	PostService postService;
	
	@GetMapping({"/","/index"})
	public String welcomeHomePage(@RequestParam(name="p",defaultValue="1") int pageNumber
			,Model model, Principal principal){
		List<Post> latest5Posts = postService.findLatest5(pageNumber);
		model.addAttribute("latest5Posts", latest5Posts);
		if(principal != null){
			model.addAttribute("username",principal.getName());
		} else {
			model.addAttribute("username","User");
		}
		return "post/index";
	}
	
	@GetMapping("/posts/view/{id}")
	public String detailPost(@PathVariable("id") int id, Model model){
		Post post = postService.findById(id);
		
		model.addAttribute("post",post);
		
		return "post/view";
	}
}
