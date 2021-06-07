package com.oraclejava.project.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="review")
public class Review{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="reviewGenerator")
	@SequenceGenerator(name="reviewGenerator",sequenceName = "review_id_seq",allocationSize = 1)
	private int review_id;
	private String user_id;
	private int product_id;
	private String review_date;
	private String review_detail;
	private String review_img;
	
	
	public int getReview_id() {
		return review_id;
	}
	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getReview_date() {
		return review_date;
	}
	public void setReview_date(String review_date) {
		this.review_date = review_date;
	}
	public String getReview_detail() {
		return review_detail;
	}
	public void setReview_detail(String review_detail) {
		this.review_detail = review_detail;
	}
	public String getReview_img() {
		return review_img;
	}
	public void setReview_img(String review_img) {
		this.review_img = review_img;
	}
	
}
