package com.example.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.models.Post;

public final class PostMapper {
	public static List<PostDTO> mapEntitiesIntoDTOs(Iterable<Post> entities){
		List<PostDTO> dtos = new ArrayList<>();
		
		entities.forEach(e -> dtos.add(mapEntityIntoDTO(e)));
		
		return dtos;
	}
	
	public static PostDTO mapEntityIntoDTO(Post entity){
		PostDTO dto = new PostDTO();
		
		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setBody(entity.getBody());
		dto.setDate(entity.getDate());
		dto.setAuthor(entity.getAuthor());
		
		return dto;
	}
	
	public static Page<PostDTO> mapEntityPageIntoPage(Pageable pageable, Page<Post> source){
		List<PostDTO> dtos = mapEntitiesIntoDTOs(source.getContent());
		
		return new PageImpl<>(dtos, pageable, source.getTotalElements());
	}
}
