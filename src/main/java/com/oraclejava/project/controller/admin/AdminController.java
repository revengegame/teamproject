package com.oraclejava.project.controller.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oraclejava.project.dao.KakaoUserRepository;
import com.oraclejava.project.dao.NoticeBoardRepository;
import com.oraclejava.project.dao.QnABoardRepository;
import com.oraclejava.project.dao.ShoppingUserRepository;
import com.oraclejava.project.dto.KakaoUser;
import com.oraclejava.project.dto.NoticeBoard;
import com.oraclejava.project.dto.QnABoard;
import com.oraclejava.project.dto.ShoppingUser;
import com.oraclejava.project.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ShoppingUserRepository userRepository;
	@Autowired
	private NoticeBoardRepository noticeRepository;
	@Autowired
	private QnABoardRepository qnaRepository;
	@Autowired
	private KakaoUserRepository kakaoUserRepository;
	@Autowired
	private UserService userService;
	
	@GetMapping("/adminPage")
	public String adminPage(Model model) {
		
		Long allUserCount = userRepository.count() + kakaoUserRepository.count();
			
		model.addAttribute("allUserCount", allUserCount);
		model.addAttribute("userCount", userRepository.userCount());
		model.addAttribute("sellerCount", userRepository.sellerCount());
		model.addAttribute("kakaoUser", kakaoUserRepository.count());
		
		
		return "/admin/adminPage";
	}
	
	// 공지사항 등록
	@GetMapping("/noticeWrite")
	public String noticeWrite(Model model) {
		NoticeBoard noticeBoard = new NoticeBoard();
		model.addAttribute("noticeBoard", noticeBoard);
		return "/admin/noticeForm";
	}
	
	// 공지사항 등록 DB저장
	@PostMapping("/noticeWrite")
	public String postNoticeWrite(NoticeBoard noticeBoard) {
		
		noticeRepository.save(noticeBoard);
		
		return "redirect:/noticeList";
	}
	
	// 자주묻는질문 등록
	@GetMapping("/qnaWrite")
	public String qnaWrite(Model model) {
		QnABoard qnaBoard = new QnABoard();
		model.addAttribute("qnaBoard", qnaBoard);
		return "/admin/qnaForm";
	}
	
	// 자주묻는질문 DB 저장
	@PostMapping("/qnaWrite")
	public String postQnaWrite(QnABoard qnaBoard) {
		
		qnaRepository.save(qnaBoard);
		
		return "redirect:/qnaList";
	}
	
	// 공지사항 수정
	@GetMapping("/noticeUpdate/{notice_id}")
	public String noticeUpdate(@PathVariable int notice_id, Model model) {
		
		Optional<NoticeBoard> noticeUpdate = noticeRepository.findById(notice_id);
		
		model.addAttribute("noticeUpdate", noticeUpdate);
		
		return "/admin/noticeUpdateForm";
	}
	
	// 공지사항 수정요청
	@PostMapping("/noticeUpdate")
	public String postnoticeUpdate(NoticeBoard noticeBoard) {
		
		int notice_id = noticeBoard.getNotice_id();
		
		NoticeBoard notice = noticeRepository.findById(notice_id).get();
		
		notice.setNotice_title(noticeBoard.getNotice_title());
		notice.setNotice_content(noticeBoard.getNotice_content());
		
		noticeRepository.save(notice);
		
		return "redirect:/noticeList";
	}
	
	// 공지사항 삭제
	@PostMapping("/noticeDelete")
	@ResponseBody
	public String noticeDelete(Integer notice_id) {
		if(notice_id != null) {
			noticeRepository.deleteById(notice_id);
			return "success";
		}
		return "fail";
	}
	
	// 자주묻는질문 수정
	@GetMapping("/qnaUpdate/{qna_id}")
	public String qnaUpdate(@PathVariable int qna_id, Model model) {
		
		Optional<QnABoard> qnaUpdate = qnaRepository.findById(qna_id);
		
		model.addAttribute("qnaUpdate", qnaUpdate);
		
		return "/admin/qnaUpdateForm";
	}
		
	// 자주묻는질문 수정요청
	@PostMapping("/qnaUpdate")
	public String postQnaUpdate(QnABoard qnaBoard) {
		
		int qna_id = qnaBoard.getQna_id();
		
		QnABoard qna = qnaRepository.findById(qna_id).get();
		
		qna.setQna_title(qnaBoard.getQna_title());
		qna.setQna_content(qnaBoard.getQna_content());
		
		qnaRepository.save(qna);
		
		return "redirect:/qnaList";
	}
		
	// 자주묻는질문 삭제
	@PostMapping("/qnaDelete")
	@ResponseBody
	public String qnaDelete(Integer qna_id) {
		if(qna_id != null) {
			qnaRepository.deleteById(qna_id);
			return "success";
		}
		return "fail";		
	}
	
	// 모든 회원리스트
	@GetMapping("/allUserList")
	public String allUserList(@PageableDefault Pageable pageable ,Model model) {
		
		Page<ShoppingUser> userList = userService.getAllUserList(pageable);
		
		model.addAttribute("userList", userList);
		
		return "/admin/allUserList";
	}
	
	// 판매 회원리스트
	@GetMapping("/sellerUserList")
	public String sellerUserList(@PageableDefault Pageable pageable ,Model model) {
		
		Page<ShoppingUser> userList = userService.getSellerUserList(pageable);
		
		model.addAttribute("userList", userList);
		
		return "/admin/sellerUserList";
	}
	
	// 일반 회원리스트
	@GetMapping("/userList")
	public String UserList(@PageableDefault Pageable pageable ,Model model) {
		
		Page<ShoppingUser> userList = userService.getUserList(pageable);
		
		model.addAttribute("userList", userList);
		
		return "/admin/userList";
	}
	
	// 카카오 회원리스트
	@GetMapping("/kakaoUserList")
	public String kakaoUserList(@PageableDefault Pageable pageable ,Model model) {
		
		Page<KakaoUser> userList = userService.getKakaoUserList(pageable);
					
		model.addAttribute("userList", userList);
		
		return "/admin/kakaoUserList";
	}
	
	// 회원정보 열람
	@GetMapping("/userDetail/{user_id}")
	public String userDetail(@PathVariable String user_id, Model model) {
		
		ShoppingUser shoppingUser = userRepository.findByUserId(user_id);
		model.addAttribute("shoppingUser", shoppingUser);
		System.out.println(shoppingUser);
		
		return "/admin/userDetail";
	}
	
	// 회원정보 수정
	@PostMapping("/userUpdate")
	public String userUpdate(ShoppingUser user, Model model) {
		
		ShoppingUser shoppingUser = userRepository.findByUserId(user.getUser_id());
		
		if(shoppingUser != null) {
			shoppingUser.setUser_name(user.getUser_name());
			shoppingUser.setUser_phone(user.getUser_phone());
			shoppingUser.setUser_add1(user.getUser_add1());
			shoppingUser.setUser_add2(user.getUser_add2());
			shoppingUser.setUser_add3(user.getUser_add3());
			
			userRepository.save(shoppingUser);
		}
		
		return "/admin/userDetail";
	}
	
	//회원정보 삭제
	@PostMapping("/userDelete")
	@ResponseBody
	public String userDelete(String user_id) {
		ShoppingUser shoppingUser = userRepository.findByUserId(user_id);
		if(shoppingUser != null) {
			userRepository.delete(shoppingUser);
			return "success";
		}
		return "fail";
	}
}
