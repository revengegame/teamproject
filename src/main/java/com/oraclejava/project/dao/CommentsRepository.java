package com.oraclejava.project.dao;

import org.springframework.data.repository.CrudRepository;

import com.oraclejava.project.dto.Comments;



public interface CommentsRepository extends CrudRepository<Comments, Integer>{

}
