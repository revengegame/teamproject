package com.oraclejava.project.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.oraclejava.project.dto.ShoppingUser;
import com.oraclejava.project.dto.ShoppingUserRole;

public interface ShoppingUserRoleRepository extends CrudRepository<ShoppingUser, Integer>{

	//단순 값 하나를 조회 
	@Query("select r.roleName from ShoppingUserRole r WHERE r.shoppingUser = :userId") 
	ShoppingUserRole findUserRole(String userId);
	
}
