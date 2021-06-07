package com.oraclejava.project.dao;

import org.springframework.data.repository.CrudRepository;

import com.oraclejava.project.dto.Board;



public interface BoardRepository extends CrudRepository<Board, Integer>{

}
