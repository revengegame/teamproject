package com.oraclejava.project.dto;

import java.io.Serializable;

//import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.oraclejava.project.domain.BaseTimeEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "wishlist")
@IdClass(ShoppingCart.class)
public class ShoppingCart extends BaseTimeEntity implements Serializable {

	@Id
	@Column(name = "product_id")
	private int product_id;
	
	@Id
	@Column(name = "user_id")
	private String user_id;
	
	private int wishlist_count;
	
//	@ManyToOne
//	@JoinColumn(name = "product_id", insertable = false, updatable = false)
//	private Product product;

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getWishlist_count() {
		return wishlist_count;
	}

	public void setWishlist_count(int wishlist_count) {
		this.wishlist_count = wishlist_count;
	}

//	public Product getProduct() {
//		return product;
//	}
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}

	

}
