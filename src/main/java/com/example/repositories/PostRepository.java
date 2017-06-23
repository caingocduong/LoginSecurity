package com.example.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.example.models.Post;
import com.querydsl.core.types.Predicate;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>, QueryDslPredicateExecutor<Post>{
	Page<Post> findAll(Pageable pageable);
	Page<Post> findAll(Predicate predicate, Pageable pageable);
}
