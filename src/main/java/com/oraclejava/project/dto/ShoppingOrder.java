package com.oraclejava.project.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.oraclejava.project.domain.BaseTimeEntity;

@Entity
@Table(name = "shopping_order")
public class ShoppingOrder extends BaseTimeEntity {

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ShoppingOrderGenerator")
	@SequenceGenerator(name = "ShoppingOrderGenerator", sequenceName = "order_seq", allocationSize = 1)
	private int order_id;

	private int order_del;
	private int product_id;
	private String order_add1;
	private String order_add2;
	private String order_add3;
	private String user_id;
	private Date order_date;
	private int order_price;
	

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getOrder_del() {
		return order_del;
	}

	public void setOrder_del(int order_del) {
		this.order_del = order_del;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getOrder_add1() {
		return order_add1;
	}

	public void setOrder_add1(String order_add1) {
		this.order_add1 = order_add1;
	}

	public String getOrder_add2() {
		return order_add2;
	}

	public void setOrder_add2(String order_add2) {
		this.order_add2 = order_add2;
	}

	public String getOrder_add3() {
		return order_add3;
	}

	public void setOrder_add3(String order_add3) {
		this.order_add3 = order_add3;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public int getOrder_price() {
		return order_price;
	}

	public void setOrder_price(int order_price) {
		this.order_price = order_price;
	}



	
	

}
