package com.example.services;


import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.models.Post;
import com.example.repositories.PostRepository;

@Service
@Transactional("transactionManager")
public class PostServiceImpl implements PostService{
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
	@Autowired
	private PostRepository postRepo;
	@Autowired
	EntityManager entityManager;
	
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

	@Override
	public Page<Post> findAll(Pageable pageable) {
		
		return postRepo.findAll(pageable);
	}

	@Override
	public Iterable<Post> listAllPosts() {
		
		return postRepo.findAll();
	}

}
