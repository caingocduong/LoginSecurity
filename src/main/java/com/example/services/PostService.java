package com.example.services;

import java.util.List;

import com.example.models.Post;

public interface PostService {
	List<Post> findAll();
	List<Post> findLatest5(int pageNumber);
	Post findById(int id);
	Post createPost(Post post);
	Post edit(Post post);
	void deleteById(int id);
}
