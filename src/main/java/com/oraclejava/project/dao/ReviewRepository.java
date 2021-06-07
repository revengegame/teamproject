package com.oraclejava.project.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.oraclejava.project.dto.Review;


@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer>{


}
