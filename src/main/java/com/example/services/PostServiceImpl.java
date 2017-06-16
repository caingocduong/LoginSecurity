package com.example.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.models.Post;
import com.example.repositories.PostRepository;

@Service
public class PostServiceImpl implements PostService{
	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
	@Autowired
	private PostRepository postRepo;
	@Autowired
	EntityManager entityManager;
	
	@Override
	public List<Post> findAll() {
		
		return postRepo.findAll();
	}

	@Override
	public List<Post> findLatest5(int pageNumber) {
		int pageSize = 3;
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countRecords = criteriaBuilder.createQuery(Long.class);
		countRecords.select(criteriaBuilder.count(countRecords.from(Post.class)));
		Long count = entityManager.createQuery(countRecords).getSingleResult();
		
		CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
		Root<Post> from = criteriaQuery.from(Post.class);
		CriteriaQuery<Post> select = criteriaQuery.select(from);
		
		TypedQuery<Post> typedQuery = entityManager.createQuery(select);
		typedQuery.setFirstResult((pageNumber-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		
		logger.info("----> "+typedQuery.getResultList());
		return typedQuery.getResultList();
		
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
