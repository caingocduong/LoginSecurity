package com.example.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
	@Query("Select p From posts p Order By p.date DESC")
	List<Post> findLatest5Posts(Pageable pageable);
}
