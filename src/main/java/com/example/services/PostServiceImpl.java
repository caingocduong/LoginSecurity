package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.models.Post;
import com.example.repositories.PostRepository;

@Service
public class PostServiceImpl implements PostService{
	@Autowired
	private PostRepository postRepo;
	
	@Override
	public List<Post> findAll() {
		
		return postRepo.findAll();
	}

	@Override
	public List<Post> findLatest5() {
		
		return postRepo.findLatest5Posts(new PageRequest(0, 5));
	}

	@Override
	public Post findById(int id) {
		
		return postRepo.findOne(id);
	}

	@Override
	public Post createPost(Post post) {
		
		return postRepo.save(post);
	}

	@Override
	public Post edit(Post post) {
		
		return postRepo.save(post);
	}

	@Override
	public void deleteById(int id) {
		postRepo.delete(postRepo.findOne(id));
	}

}
