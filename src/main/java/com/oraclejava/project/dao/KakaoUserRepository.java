package com.oraclejava.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oraclejava.project.dto.KakaoUser;

public interface KakaoUserRepository extends JpaRepository<KakaoUser, String>{

	KakaoUser findByUsername(String username);
	
	
	
}
