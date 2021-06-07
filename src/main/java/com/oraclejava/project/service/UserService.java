package com.oraclejava.project.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oraclejava.project.dao.KakaoUserRepository;
import com.oraclejava.project.dao.ShoppingUserRepository;
import com.oraclejava.project.dto.KakaoUser;
import com.oraclejava.project.dto.ShoppingUser;
import com.oraclejava.project.dto.ShoppingUserRole;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService{
	
	@Autowired
	private ShoppingUserRepository userRepository;
	@Autowired
	private KakaoUserRepository kakaoUserRepository;
	
	// 로그를 남겨줌
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	// 아이디 중복체크
	public int idCheck(String user_id) {
		ShoppingUser user = userRepository.findByUserId(user_id);
		
		if(user != null) { // 중복
			return 1;
		} 
		return 0;	// 중복 아님
	}
	
	// 로그인 정보
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {

		ShoppingUser shoppingUser= userRepository.findByUserId(userid);
		
		// 해당 아이디에 해당하는 정보가 없으면 UsernameNotFoundException이 발생
        if(shoppingUser == null) {
            throw new UsernameNotFoundException("아이디 및 비밀번호를 확인해주세요.");
        }
        
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		List<ShoppingUserRole> userRole = shoppingUser.getUserRole();
		
		for(ShoppingUserRole role : userRole) {
            logger.info(role.getRoleName());
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
				
		return new User(shoppingUser.getUser_id(), shoppingUser.getUser_pass(), authorities);
	}
	
	// 모든 회원
	public Page<ShoppingUser> getAllUserList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        
        pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "createdDate"); // <- Sort 추가

        return userRepository.findAll(pageable);
    }
	
	// 셀러 회원
	public Page<ShoppingUser> getSellerUserList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        
        pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "createdDate"); // <- Sort 추가

        return userRepository.findUserDiv2(pageable);
    }
	
	// 일반 회원
	public Page<ShoppingUser> getUserList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        
        pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "createdDate"); // <- Sort 추가

        return userRepository.findUserDiv1(pageable);
    }
	
	// 모든 회원
	public Page<KakaoUser> getKakaoUserList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        
        pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "createdDate"); // <- Sort 추가

        return kakaoUserRepository.findAll(pageable);
    }
	
}