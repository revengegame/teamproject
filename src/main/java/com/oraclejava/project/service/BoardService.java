package com.oraclejava.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oraclejava.project.dao.NoticeBoardRepository;
import com.oraclejava.project.dao.QnABoardRepository;
import com.oraclejava.project.dto.NoticeBoard;
import com.oraclejava.project.dto.QnABoard;


@Service
@Transactional
public class BoardService {

	@Autowired
	private NoticeBoardRepository noticeRepository;
	@Autowired
	private QnABoardRepository qnaRepository;
	
	public Page<NoticeBoard> getNoticeBoardList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        
        pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "createdDate"); // <- Sort 추가

        return noticeRepository.findAll(pageable);
    }
	
	public Page<QnABoard> getQnaBoardList(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        
        pageable = PageRequest.of(page, 10, Sort.Direction.DESC, "createdDate"); // <- Sort 추가

        return qnaRepository.findAll(pageable);
    }
	
}
