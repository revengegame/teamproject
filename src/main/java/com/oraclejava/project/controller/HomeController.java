package com.oraclejava.project.controller;

import java.security.Principal;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oraclejava.project.MailSend;
import com.oraclejava.project.dao.BoardRepository;
import com.oraclejava.project.dao.NoticeBoardRepository;
import com.oraclejava.project.dao.ProductRepository;
import com.oraclejava.project.dao.QnABoardRepository;
import com.oraclejava.project.dao.ReviewRepository;
import com.oraclejava.project.dao.ShoppingUserRepository;
import com.oraclejava.project.dto.NoticeBoard;
import com.oraclejava.project.dto.Product;
import com.oraclejava.project.dto.QnABoard;
import com.oraclejava.project.dto.ShoppingUser;
import com.oraclejava.project.dto.ShoppingUserRole;
import com.oraclejava.project.service.BoardService;
import com.oraclejava.project.service.UserService;

@Controller
public class HomeController {

	// 로그를 남겨줌
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	public JavaMailSender javaMailSender;

	@Autowired
	private ShoppingUserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private MailSend mailSender;
	@Autowired
	private ProductRepository productrepository;
	@Autowired
	private NoticeBoardRepository noticeRepository;
	@Autowired
	private QnABoardRepository qnaRepository;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	
	// main 이동
	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("products", productrepository.findAll());
		model.addAttribute("newproducts", productrepository.findNewproduct());
		model.addAttribute("lowproducts", productrepository.findLowpriceproduct());
		model.addAttribute("highproducts", productrepository.findHighpriceproduct());
		
		return "/main";
	}
	
	@GetMapping("/select")
	public String user_select(Model model) {
		
		return "/home/select";
	}

	// 약관동의
	@GetMapping("/agree")
	public String agree() {

		return "/home/agree"; // 뷰로 보냄
	}

	// 아이디 중복 검사
	@PostMapping("/userIdChk")
	@ResponseBody
	public String userIdChk(String userId) throws Exception {
		/* logger.info("userIdChk() 진입"); */

		int result = userService.idCheck(userId);

		/* logger.info("결과값 = " + result); */

		if (result == 1) {
			return "fail"; // 중복 아이디가 존재
		} else {
			return "success"; // 중복 아이디 x
		}
	}

	/* 이메일 인증번호 전송 */
	@GetMapping("/mailCheck")
	@ResponseBody
	public String mailCheckGET(String email) throws Exception{
        
        /* 뷰(View)로부터 넘어온 데이터 확인 */
        /* logger.info("이메일 데이터 전송 확인");
        logger.info("인증번호 : " + email); */ 
        
        String checkNum = mailSender.sendMail(email);
        
        return checkNum;
    }
	
	/* 이메일 인증 */
	@PostMapping("/emailChk")
	@ResponseBody
	public String emailChk(String email) throws Exception{
        
		String mail = userRepository.findUserMail(email);
        
		/* logger.info(mail); */
		
		if(mail == null) { //중복없다
			return "success";
		} 
		return "fail";
		
    }
	
	/* 사업자 인증 */
	@PostMapping("/regChk")
	@ResponseBody
	public String regChk(String reg) throws Exception{
        
		String regNum = userRepository.findUserReg(reg.replace("-", ""));
        		
		if(regNum == null) { //중복없다
			return "success";
		} 
		return "fail";
		
    }
	
	// 회원가입_GET
	@GetMapping("/register")
	public String register(Integer div, Model model) {
		
		model.addAttribute("div", div);
		
		return "/home/register"; // 뷰로 보냄
	}

	// 회원가입_POST
	@PostMapping("/register")
	public String register_success(Integer div, ShoppingUser user) {
		
		//userService.join(div,user);
		ShoppingUserRole userRole = new ShoppingUserRole();
		
		if(div == 1) {
			userRole.setRoleName("ROLE_USER");
			user.setUser_div(1);
		} else if(div == 2) {
			userRole.setRoleName("ROLE_SELLER");
			user.setUser_div(2);
		} else {
			userRole.setRoleName("ROLE_ADMIN");
			user.setUser_div(0);
		}
		
		if(user.getUser_regnum() != null) {
			// 사업자 번호 하이픈제거
			String reg = user.getUser_regnum();
			user.setUser_regnum(reg.replace("-", ""));
		}
		
		user.setUserRole(Arrays.asList(userRole));
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getUser_pass());
		user.setUser_pass(encodedPassword);
				
		userRepository.save(user);

		return "redirect:/login";
	}

	// 로그인
	@GetMapping("/login")
	public String login() {
		
		return "/home/login"; // 뷰로 보냄
	}
		
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
	    new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
	    return "/main";
	 }

	// 판매자 약관동의
	@GetMapping("/sellerAgree")
	public String sellerAgree() {
		return "/home/sellerAgree";
	}
	
	// 아이디 찾기
	@GetMapping("/searchId")
	public String searchId() {
		return "/home/searchId";
	}
	
	// 아이디 찾기
	@PostMapping("/searchIdChk")
	public String postSearchId(String user_name, String userEmail, Model model) {
		
		String userId = userRepository.findUserMailCheck(userEmail, user_name);
		
		if(userId != null) {
			model.addAttribute("user_name", user_name);
			model.addAttribute("userSearchId", userId);
		}
		return "/home/searchIdChk";
	}

	// 비밀번호 찾기
	@GetMapping("/searchPw")
	public String searchPw() {
		return "/home/searchPw";
	}
	
	// 비밀번호 찾기
	@PostMapping("/searchPwChk")
	public String postsearchPw(String user_id, String user_email, Model model) throws Exception{
				
		ShoppingUser user = userRepository.findUserPwCheck(user_email, user_id);
		
		// 가입된 아이디가 있으면
		if(user != null) {
			mailSender.sendPwMail(user);
			model.addAttribute("result", "success");
		} else {
		
			model.addAttribute("result", "fail");
		}
		
		return "/home/searchPwChk";
		
	}
	
	// 공지사항 리스트
	@GetMapping("/noticeList")
	public String noticeList(@PageableDefault Pageable pageable ,Model model) {
				
		Page<NoticeBoard> boardList = boardService.getNoticeBoardList(pageable);
        model.addAttribute("boardList", boardList);
		
		
		return "/home/noticeList";
	}
	
	// 공지사항 상세보기
	@GetMapping("/noticeDetail/{notice_id}")
	public String noticeWrite(Model model, @PathVariable Integer notice_id) {
		
		// 조회수 증가
		noticeRepository.updateView(notice_id);
		
		model.addAttribute("notice", noticeRepository.findNoticeDetail(notice_id));
		
		return "/home/noticeDetail";
	}
	
	// 자주묻는질문 리스트
	@GetMapping("/qnaList")
	public String qnaList(@PageableDefault Pageable pageable, Model model) {
		
		Page<QnABoard> boardList = boardService.getQnaBoardList(pageable);
		model.addAttribute("boardList",boardList);
		
		return "/home/qnaList";
	}
	
	// 공지사항 상세보기
	@GetMapping("/qnaDetail/{qna_id}")
	public String qnaWrite(Model model, @PathVariable Integer qna_id) {
		
		// 조회수 증가
		qnaRepository.updateView(qna_id);
		
		model.addAttribute("qna", qnaRepository.findqnaDetail(qna_id));
		
		return "/home/qnaDetail";
	}
	
	// 상품 상세페이지 이동
	@RequestMapping(value = "/product_info/{product_id}", method = {RequestMethod.GET,RequestMethod.POST})
	public String getUser1(@PathVariable Integer product_id, Model model) {
		
		Product product = productRepository.findById(product_id).get();
		ShoppingUser user = userRepository.findByUserId(product.getUser_id());
		
		model.addAttribute("board",boardRepository.findAll());
//		model.addAttribute("comments",commentsRepository.findAll());
		model.addAttribute("review",reviewRepository.findAll());
		model.addAttribute("product", product);
		model.addAttribute("user", user);
		
		
		return "/home/product_info";
	}
	
	// 검색어 받기
	@PostMapping("/searchproduct")
	public String search(String keyword, Model model) throws Exception{
		
		return Getsearch(keyword, model);
		
    }
	
	// 검색 뿌리기
	@GetMapping("/searchproducts")
	public String Getsearch(String keyword, Model model) throws Exception{
		
		model.addAttribute("searchproducts", productrepository.findsearchproduct(keyword));
        
		return "/searchproduct";
		
    }
	
	@GetMapping("/403")
	public String accessDenied(Principal user, Model model) {
		if(user != null) {
			//로그인 완료시 
			model.addAttribute("msg", user.getName()+"님, 해당 페이지는 접근제한 페이지입니다.");
		} else {
			model.addAttribute("msg", "접근제한 페이지입니다.");
		}		
		return "/erorr/403";
	}
	
	
}
