package com.oraclejava.project.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="shopping_userrole")
public class ShoppingUserRole{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userRoleGenerator")
	@SequenceGenerator(name = "userRoleGenerator", sequenceName = "shopping_userrole_seq", allocationSize = 1)
	@Column(name = "user_rolediv")
	private int roleDiv;
	
	@Column(name = "user_rolename")
	private String roleName;
	
	@ManyToOne
	@JoinColumn(name = "user_id") // DB Column 써주기
	private ShoppingUser shoppingUser;
	
	@ManyToOne
	@JoinColumn(name = "kakao_user_id") // DB Column 써주기
	private KakaoUser kakaoUser;
		
	public ShoppingUser getShoppingUser() {
		return shoppingUser;
	}
	public void setShoppingUser(ShoppingUser shoppingUser) {
		this.shoppingUser = shoppingUser;
	}
	public KakaoUser getKakaoUser() {
		return kakaoUser;
	}
	public void setKakaoUser(KakaoUser kakaoUser) {
		this.kakaoUser = kakaoUser;
	}
	public int getRoleDiv() {
		return roleDiv;
	}
	public void setRoleDiv(int roleDiv) {
		this.roleDiv = roleDiv;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
