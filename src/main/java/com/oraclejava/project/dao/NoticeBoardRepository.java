package com.oraclejava.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.oraclejava.project.dto.NoticeBoard;

public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, Integer>{
	
	// 게시판 상세페이지
	@Query("select n from NoticeBoard n WHERE n.notice_id = :notice_id") 
	NoticeBoard findNoticeDetail(int notice_id);
	
	// 조회수 업데이트
	@Transactional
	@Modifying
    @Query("update NoticeBoard n set n.notice_viewcut = n.notice_viewcut + 1 where n.notice_id = :id")
    int updateView(Integer id);
		
}
