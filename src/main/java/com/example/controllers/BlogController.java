package com.example.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.common.PageWrapper;
import com.example.common.PostDTO;
import com.example.models.Post;
import com.example.services.PostService;


@Controller
public class BlogController {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BlogController.class);
	@Autowired
	PostService postService;
	
	@GetMapping({"/","/index"})
	public String welcomeHomePage(Model model, Pageable pageable, Principal principal){
		Page<Post> postPage = postService.findAll(pageable);
		PageWrapper<Post> page = new PageWrapper<Post>(postPage, "/index");
		model.addAttribute("posts", page.getContent());
		model.addAttribute("page", page);
	
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
	
	@GetMapping("/posts/view/search/{author}")
	public String listPostsOfOneAuthor(@PathVariable String author, Model model, Pageable pageable){
		Page<PostDTO> resultPage = postService.findAll(author, pageable);
		PageWrapper<PostDTO> page = new PageWrapper<PostDTO>(resultPage, "/index");
		model.addAttribute("posts", resultPage.getContent());
		model.addAttribute("page", page);
		
		return "post/index";
	}
}
