package com.oraclejava.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.oraclejava.project.dto.QnABoard;

public interface QnABoardRepository extends JpaRepository<QnABoard, Integer>{
	
	// 게시판 상세페이지
	@Query("select q from QnABoard q WHERE q.qna_id = :qna_id") 
	QnABoard findqnaDetail(int qna_id);
	
	// 조회수 업데이트
	@Transactional
	@Modifying
    @Query("update QnABoard q set q.qna_viewcut = q.qna_viewcut + 1 where q.qna_id = :id")
    int updateView(Integer id);
	
}
