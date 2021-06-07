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

import lombok.NoArgsConstructor;
/*import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString*/
@NoArgsConstructor
@Entity
@Table(name="shopping_user")
public class ShoppingUser extends BaseTimeEntity{
	
	@Id
	@Column(name="user_id")
	private String user_id;
	@Column(name="user_pass")
	private String user_pass;
	@Column(name="user_name")
	private String user_name;
	@Column(name="user_email")
	private String user_email;
	private String user_phone;
	@Column(name="user_add1")
	private String user_add1;
	@Column(name="user_add2")
	private String user_add2;
	@Column(name="user_add3")
	private String user_add3;
	private String user_regnum;
	private int user_div;
	
	// mappedBy = "shoppingUser" = class name이며, 앞엔 소문자
	/*
	 * @OneToMany(mappedBy = "shoppingUser", cascade = CascadeType.ALL,
	 * fetch=FetchType.EAGER) private List<ShoppingUserRole> userRole;
	 */
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private List<ShoppingUserRole> userRole;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pass() {
		return user_pass;
	}
	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_add1() {
		return user_add1;
	}
	public void setUser_add1(String user_add1) {
		this.user_add1 = user_add1;
	}
	public String getUser_add2() {
		return user_add2;
	}
	public void setUser_add2(String user_add2) {
		this.user_add2 = user_add2;
	}
	public String getUser_add3() {
		return user_add3;
	}
	public void setUser_add3(String user_add3) {
		this.user_add3 = user_add3;
	}
	public String getUser_regnum() {
		return user_regnum;
	}
	public void setUser_regnum(String user_regnum) {
		this.user_regnum = user_regnum;
	}
	public List<ShoppingUserRole> getUserRole() {
		return userRole;
	}
	public void setUserRole(List<ShoppingUserRole> userRole) {
		this.userRole = userRole;
	}
	public int getUser_div() {
		return user_div;
	}
	public void setUser_div(int user_div) {
		this.user_div = user_div;
	}
	@Override
	public String toString() {
		return "ShoppingUser [user_id=" + user_id + ", user_pass=" + user_pass + ", user_name=" + user_name
				+ ", user_email=" + user_email + ", user_phone=" + user_phone + ", user_add1=" + user_add1
				+ ", user_add2=" + user_add2 + ", user_add3=" + user_add3 + ", user_regnum=" + user_regnum
				+ ", user_div=" + user_div + ", userRole=" + userRole + "]";
	}
	
}
