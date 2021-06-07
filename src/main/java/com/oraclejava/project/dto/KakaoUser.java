package com.oraclejava.project.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.oraclejava.project.domain.BaseTimeEntity;

@Entity
@Table(name="kakao_user")
public class KakaoUser extends BaseTimeEntity{
	
	@Id
	@Column(name="kakao_user_id")
	private String username;

	@Column(name="user_name")
	private String nickname;
	
	@Column(name="user_email")
	private String userEmail;
	
	private String access_token;
		
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="kakao_user_id")
	private List<ShoppingUserRole> userRole;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public List<ShoppingUserRole> getUserRole() {
		return userRole;
	}
	public void setUserRole(List<ShoppingUserRole> userRole) {
		this.userRole = userRole;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
