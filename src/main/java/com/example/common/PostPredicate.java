package com.example.common;

import com.example.models.QPost;
import com.querydsl.core.types.Predicate;

public class PostPredicate {
	private PostPredicate(){}
	
	public static Predicate searchPostByAuthorIgnoreCase(String author){
		if(author == null || author.isEmpty()){
			
			return QPost.post.isNotNull();
		} else {
			
			return QPost.post.author.containsIgnoreCase(author);
		}
	}
}
