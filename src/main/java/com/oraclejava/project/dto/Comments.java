package com.oraclejava.project.dto;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="comments")
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="commentsGenerator")
	@SequenceGenerator(name="commentsGenerator",sequenceName = "comments_seq",allocationSize = 1)
	private int comments_id;
	
	private int board_id;
	private String user_id;
	private Date comments_date;
//	@NotNull
	private String comments_detail;
	
	public int getComments_id() {
		return comments_id;
	}
	public void setComments_id(int comments_id) {
		this.comments_id = comments_id;
	}
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public Date getComments_date() {
		return comments_date;
	}
	public void setComments_date(Date comments_date) {
		this.comments_date = comments_date;
	}
	public String getComments_detail() {
		return comments_detail;
	}
	public void setComments_detail(String comments_detail) {
		this.comments_detail = comments_detail;
	}
	
}
