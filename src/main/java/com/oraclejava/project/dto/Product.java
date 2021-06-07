package com.oraclejava.project.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.oraclejava.project.domain.BaseTimeEntity;

@Entity
@Table(name = "product")
public class Product extends BaseTimeEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productGenerator")
	@SequenceGenerator(name = "productGenerator", sequenceName = "product_id_seq", allocationSize = 1)
	private int product_id;
	private String user_id;
	private String product_name;
	private int product_div;
	private int product_price;
	private int product_sale;
	private int product_delprice;
	private int product_count;	
	private String product_detail;
	private String product_img;
	private Date product_date;
	private int product_saleprice;
	
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

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getProduct_div() {
		return product_div;
	}

	public void setProduct_div(int product_div) {
		this.product_div = product_div;
	}

	public int getProduct_price() {
		return product_price;
	}

	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}

	public int getProduct_sale() {
		return product_sale;
	}

	public void setProduct_sale(int product_sale) {
		this.product_sale = product_sale;
	}

	public int getProduct_delprice() {
		return product_delprice;
	}

	public void setProduct_delprice(int product_delprice) {
		this.product_delprice = product_delprice;
	}

	public int getProduct_count() {
		return product_count;
	}

	public void setProduct_count(int product_count) {
		this.product_count = product_count;
	}

	public String getProduct_detail() {
		return product_detail;
	}

	public void setProduct_detail(String product_detail) {
		this.product_detail = product_detail;
	}

	public String getProduct_img() {
		return product_img;
	}

	public void setProduct_img(String product_img) {
		this.product_img = product_img;
	}

	public Date getProduct_date() {
		return product_date;
	}

	public void setProduct_date(Date product_date) {
		this.product_date = product_date;
	}

	public int getProduct_saleprice() {
		return product_saleprice;
	}

	public void setProduct_saleprice(int product_saleprice) {
		this.product_saleprice = product_saleprice;
	}
	

}
