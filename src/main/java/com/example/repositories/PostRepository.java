package com.example.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	Page<Post> findAll(Pageable pageable);
}
