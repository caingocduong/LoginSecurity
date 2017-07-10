package com.example.services;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.PostDTO;
import com.example.common.PostMapper;
import com.example.models.Post;
import com.example.repositories.PostRepository;
import com.querydsl.core.types.Predicate;
import static com.example.common.PostPredicate.searchPostByAuthorIgnoreCase;

@Service
public class PostServiceImpl implements PostService{
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
	@Autowired
	private PostRepository postRepo;
	@Autowired
	EntityManager entityManager;
	
	@Override
	@Transactional(readOnly=true)
	public Post findById(int id) {
		
		return postRepo.findOne(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Post createPost(Post post) {
		
		return postRepo.save(post);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Post edit(Post post) {
		
		return postRepo.save(post);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteById(int id) {
		postRepo.delete(postRepo.findOne(id));
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Post> findAll(Pageable pageable) {
		
		return postRepo.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Iterable<Post> listAllPosts() {
		
		return postRepo.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<PostDTO> findAll(String author, Pageable pageable) {
		Predicate predicate = searchPostByAuthorIgnoreCase(author);
		Page<Post> pageResult = postRepo.findAll(predicate, pageable);
		return PostMapper.mapEntityPageIntoPage(pageable, pageResult);
	}

	@Override
	public void save(Post post) {
		postRepo.save(post);
	}

}
