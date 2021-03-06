package com.example.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.common.PostDTO;
import com.example.models.Post;

public interface PostService {
	Post findById(int id);
	Post createPost(Post post);
	Post edit(Post post);
	void save(Post post);
	void deleteById(int id);
	Page<Post> findAll(Pageable pageable);
	Iterable<Post> listAllPosts();
	Page<PostDTO> findAll(String author, Pageable pageable);
}
