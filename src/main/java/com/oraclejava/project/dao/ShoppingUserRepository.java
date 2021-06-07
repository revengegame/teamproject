package com.oraclejava.project.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.oraclejava.project.dto.ShoppingUser;

public interface ShoppingUserRepository extends JpaRepository<ShoppingUser, String>{

	 
	//ShoppingUser findByUsername(String username);
	@Query("select s from ShoppingUser s WHERE s.user_id = :user_id") 
	ShoppingUser findByUserId(String user_id);
	
	// s.userpass = :password 앞에 있는게 entity 변수 이름
		
	// 단순 값 하나를 조회
	// 이메일 중복 조회
	@Query("select s.user_email from ShoppingUser s WHERE s.user_email = :user_email") 
	String findUserMail(String user_email);
	
	// 사업자 중복 조회
	@Query("select s.user_regnum from ShoppingUser s WHERE s.user_regnum = :user_reg") 
	String findUserReg(String user_reg);
	
	// 아이디 찾기
	@Query("select s.user_id from ShoppingUser s WHERE s.user_email = :user_email and s.user_name = :user_name") 
	String findUserMailCheck(String user_email, String user_name);
	
	// 비밀번호 찾기
	@Query("select s from ShoppingUser s WHERE s.user_email = :user_email and s.user_id = :user_id") 
	ShoppingUser findUserPwCheck(String user_email, String user_id);
	
	// 비밀번호 찾기 후 비밀번호 변경
	@Modifying 
	@Query("UPDATE ShoppingUser s set s.user_pass = :password where s.user_id = :user_id")
	void changePwd(String password, String user_id) throws Exception;
	
	// 일반회원
	@Query("select count(s) from ShoppingUser s where s.user_div = 1")
	int userCount();
	
	// 셀러회원
	@Query("select count(s) from ShoppingUser s where s.user_div = 2")
	int sellerCount();
	
	// 일반회원
	@Query("select s from ShoppingUser s where s.user_div = 1")
	Page<ShoppingUser> findUserDiv1(Pageable pageable);
	
	// 판매회원
	@Query("select s from ShoppingUser s where s.user_div = 2")
	Page<ShoppingUser> findUserDiv2(Pageable pageable);
	
	// 사용자 수정
	@Transactional
	@Modifying 
	@Query("UPDATE ShoppingUser s set s.user_add1 = :user_add1, s.user_add2=:user_add2, s.user_add3=:user_add3, s.user_pass = :user_pass, s.user_name = :user_name, s.user_phone = :user_phone where s.user_id = :user_id") 
	void findUpdate(String user_add1, String user_add2, String user_add3, String user_pass, String user_name, String user_phone, String user_id);
	
}
