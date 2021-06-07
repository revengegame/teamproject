package com.oraclejava.project.controller.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oraclejava.project.dao.BoardRepository;
import com.oraclejava.project.dao.CommentsRepository;
import com.oraclejava.project.dao.ReviewRepository;
import com.oraclejava.project.dao.ShoppingCartRepository;
import com.oraclejava.project.dao.ShoppingUserRepository;
import com.oraclejava.project.dto.Board;
import com.oraclejava.project.dto.Comments;
import com.oraclejava.project.dto.Review;
import com.oraclejava.project.dto.ShoppingUser;


@Controller
@RequestMapping("/user")
public class UserContorller {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private CommentsRepository commentsRepository;
	
	@Autowired
	private ShoppingUserRepository shoppingUserRepository;
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	// 로그를 남겨줌
	private static final Logger logger = LoggerFactory.getLogger(UserContorller.class);

	// 사용자 수정(GET)
	@GetMapping("/customer")
	public String getUser(@AuthenticationPrincipal UserDetails user, Model model) {
			
		if(user != null) {
			ShoppingUser customer= shoppingUserRepository.findByUserId(user.getUsername());
			//customer.setUser_pass(customer.getUser_pass().substring(6)); 비밀번호 바뀜
			model.addAttribute("shoppingUser", customer);
		}
		
		return "/user/customer";
	}
	
	// 사용자 수정(POST)
	@PostMapping(params = "update", value = "/customer")
	public String postUser(@Validated ShoppingUser customer, BindingResult bindingResult, Model model) {
	
		if (bindingResult.hasErrors()) {
			model.addAttribute("contents", "/user/customer :: userUpdate_contents");
			return "/user/customer";
		}

		customer.setUser_pass(customer.getUser_pass());
		
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode(customer.getUser_pass());
		String add1 = customer.getUser_add1();
		String add2 = customer.getUser_add2();
		String add3 = customer.getUser_add3();
		String user_name = customer.getUser_name();
		String user_phone = customer.getUser_phone();
		String user_id = customer.getUser_id();
		shoppingUserRepository.findUpdate(add1, add2, add3, password, user_name, user_phone, user_id);
		
		return "redirect:/user/customer";
	}
	
	// 주문완료
	@RequestMapping(value = "/delivery", method = RequestMethod.GET)
	public String orderend(Model model, @AuthenticationPrincipal UserDetails user) throws Exception {
		String user_id = user.getUsername();
		
		List<Object> orderlist = shoppingCartRepository.findorderlist(user_id);
		model.addAttribute("orderlist", orderlist);
		
		return "/user/delivery";
	}
	
	@RequestMapping(value="/inquire" , method = RequestMethod.GET)
	public String question(@AuthenticationPrincipal UserDetails user, Model model) {
		
		if(user != null) {
			ShoppingUser customer= shoppingUserRepository.findByUserId(user.getUsername());
			model.addAttribute("shoppingUser", customer);
			Board board=new Board();
			model.addAttribute("board", board);
			return "/user/inquire";
		}
		
		return "/login";
		
	}

	@RequestMapping(value="/inquire", method = RequestMethod.POST)
	public String question2 (@Validated Board board,Model model){
		
		boardRepository.save(board);
		return "seller/complete";
	
	}
	
	@RequestMapping(value="/after" , method = RequestMethod.GET)
	public String after(@AuthenticationPrincipal UserDetails user, Model model) {
		
		if(user != null) {
			ShoppingUser customer= shoppingUserRepository.findByUserId(user.getUsername());
			logger.info(customer.getUser_id());
			model.addAttribute("shoppingUser", customer);
			Review review=new Review();
			model.addAttribute("review", review);
			SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			model.addAttribute("date", sDate.format(new Date()));
			return "/user/after";
		}
		 return "/login";
	}

	
	@RequestMapping(value="/after", method = RequestMethod.POST)
	public String createNewUser2 (@Validated Review review, Model model, String user_id, String date){
		review.setUser_id(user_id);
		review.setReview_date(date);
		reviewRepository.save(review);
		return "/user/complete";
	
	}
	
	@RequestMapping(value="/complete")
	public String complete(Model model) {
		
		return "/user/complete";
	}
	
	@GetMapping(value="/comment/{board_id}")
	public String getUser2(@PathVariable Integer board_id, @Validated Comments comments,Model model) {
		Board board = boardRepository.findById(board_id).get();
		
		model.addAttribute("comments", comments);
	
		model.addAttribute("board", board);

		commentsRepository.save(comments);
		
		return "/user/comment";
	}

}
