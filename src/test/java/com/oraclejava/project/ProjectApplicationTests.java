package com.oraclejava.project;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.oraclejava.project.dao.ShoppingUserRepository;
import com.oraclejava.project.dto.ShoppingUser;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	ShoppingUserRepository repository;
	
	ShoppingUser user;
	
	@Test
	void contextLoads() {
		
		String userEmail = "jej1728@daum.net";
		String username = "jyh930213";
		
		repository.findUserPwCheck(userEmail, username);
		
		
	}

}
