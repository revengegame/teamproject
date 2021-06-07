package com.oraclejava.project.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.oraclejava.project.domain.BaseTimeEntity;

@Entity
@Table(name = "qna_board")
public class QnABoard extends BaseTimeEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qnaBoardGenerator")
	@SequenceGenerator(name = "qnaBoardGenerator", sequenceName = "qna_board_id_seq", allocationSize = 1)
	private int qna_id;
	private String qna_title;
	private String qna_content;
	private int qna_viewcut;
	@CreatedDate
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@LastModifiedDate
	@Column(name = "modified_date")
	private LocalDateTime modifiedDate;
	
	public int getQna_id() {
		return qna_id;
	}
	public void setQna_id(int qna_id) {
		this.qna_id = qna_id;
	}
	public String getQna_title() {
		return qna_title;
	}
	public void setQna_title(String qna_title) {
		this.qna_title = qna_title;
	}
	public String getQna_content() {
		return qna_content;
	}
	public void setQna_content(String qna_content) {
		this.qna_content = qna_content;
	}
	public int getQna_viewcut() {
		return qna_viewcut;
	}
	public void setQna_viewcut(int qna_viewcut) {
		this.qna_viewcut = qna_viewcut;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Override
	public String toString() {
		return "QnABoard [qna_id=" + qna_id + ", qna_title=" + qna_title + ", qna_content=" + qna_content
				+ ", qna_viewcut=" + qna_viewcut + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate
				+ "]";
	}
}
