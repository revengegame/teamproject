package com.oraclejava.project.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oraclejava.project.dao.ShoppingCartRepository;
import com.oraclejava.project.dao.ShoppingOrderRepository;
import com.oraclejava.project.dao.ShoppingUserRepository;
import com.oraclejava.project.dto.ShoppingCart;
import com.oraclejava.project.dto.ShoppingOrder;
import com.oraclejava.project.dto.ShoppingUser;

@Controller
@RequestMapping("/user")
public class CartController {
	
	@Autowired
	ShoppingCartRepository shoppingcartrepository;
	
	@Autowired
	ShoppingUserRepository userRepository;
	
	@Autowired
	ShoppingOrderRepository orderrepository;
	
	@GetMapping("/shoppingcart")
	public String index(Model model, @AuthenticationPrincipal UserDetails user) {
		
		String user_id = user.getUsername();
		List<Object> list = shoppingcartrepository.findproductcart(user_id);
//		System.out.println(list);
		model.addAttribute("cartproduct", list);

		return "/user/shoppingcart";
	}
		
	// 장바구니 추가
	@RequestMapping(value="/shoppingcart1", method = RequestMethod.POST)
	public String addshoppingcart(Model model, Integer product_id , @AuthenticationPrincipal UserDetails user, ShoppingCart shoppingcart) throws Exception{
		
		System.out.println(product_id);
		if (user != null) {
			String user_id = user.getUsername();
			
			ShoppingCart shopping = shoppingcartrepository.findcheckcart(product_id, user_id);
			
			if(shopping != null ) {
				
				shoppingcartrepository.addcount(user_id, product_id, (shopping.getWishlist_count()+1));
				
			}else {
				shoppingcart.setUser_id(user_id);
				shoppingcart.setProduct_id(product_id);
				shoppingcart.setWishlist_count(1);
				
				ShoppingCart cart = shoppingcartrepository.save(shoppingcart);
//				System.out.println(cart);
			}
		}
		model.addAttribute("wishlist", shoppingcart);
		
//		return index(model, user);
		return "redirect:/user/shoppingcart";
    }
	
	// 장바구니 삭제
	@RequestMapping(value = "/deletecart", method = RequestMethod.POST)
	public String deleteshoppingcart(HttpServletRequest request, @AuthenticationPrincipal UserDetails user) {

		String user_id = user.getUsername();
		Integer product_id = Integer.parseInt(request.getParameter("product_id"));

	    shoppingcartrepository.deletecart(user_id, product_id);
	    
	    return "/user/shoppingcart";
	}
	
	// 주문 전, 장바구니 수정
	@RequestMapping(value = "/order1",  method= RequestMethod.POST)
	public String addorder(Model model, HttpServletRequest request, @AuthenticationPrincipal UserDetails user) throws Exception {

		String user_id = user.getUsername();
		Integer size = Integer.parseInt(request.getParameter("size"));
		
		for(int i = 0; i < size; i++) {

			Integer product_id = Integer.parseInt(request.getParameter("product_id"+ i));
			Integer wishlist_count = Integer.parseInt(request.getParameter("count"+ i));
			
			shoppingcartrepository.addcount(user_id, product_id, wishlist_count);
		}
		
		return orderuserindex(model, user);
	}
	
	// 배송 정보 불러오기
	@RequestMapping(value = "/order", method = {RequestMethod.GET, RequestMethod.POST})
	public String orderuserindex(Model model, @AuthenticationPrincipal UserDetails user) {
		
		String user_id = user.getUsername();
		
		if (user != null) {
			ShoppingUser customer = userRepository.findByUserId(user.getUsername());
			model.addAttribute("userindex", customer);
		}
		
		List<Object> orderlist = shoppingcartrepository.findorder(user_id);
		model.addAttribute("orderindex", orderlist);

		return "/user/order";
	}
	
	// 주문완료
	@RequestMapping(value = "/orderend", method = {RequestMethod.GET, RequestMethod.POST})
	public String orderend(@AuthenticationPrincipal UserDetails user, @Validated ShoppingUser customer, HttpServletRequest request) throws Exception {

		SimpleDateFormat format1 = new SimpleDateFormat ("yy-MM-dd");
		Date time = new Date();

		String user_id = user.getUsername();
		
		
		String user_name = customer.getUser_name();
		String user_phone = customer.getUser_phone();
		String user_add1 = customer.getUser_add1();
		String user_add2 = customer.getUser_add2();
		String user_add3 = customer.getUser_add3();
		
		Integer size = Integer.parseInt(request.getParameter("size"));
		
		for(int i = 0; i < size; i++) {
			
			Integer product_id = Integer.parseInt(request.getParameter("product_id"+ i));
			String delprice = request.getParameter("delprice"+ i);
			Integer count = Integer.parseInt(request.getParameter("count"+ i));
			Integer product_price = Integer.parseInt(request.getParameter("product_price"+ i));
			
			ShoppingOrder shoppingOrder = new ShoppingOrder();
			
			shoppingOrder.setUser_id(user_id);
			shoppingOrder.setOrder_del(0);
			shoppingOrder.setOrder_add1(user_add1);
			shoppingOrder.setOrder_add2(user_add2);
			shoppingOrder.setOrder_add3(user_add3);
			shoppingOrder.setOrder_price(count*product_price);
			shoppingOrder.setProduct_id(product_id);
			shoppingOrder.setOrder_date(time);
			
			orderrepository.save(shoppingOrder);
			
		}
		
	    shoppingcartrepository.orderdeletecart(user_id);
				
		return "/user/orderend";
	}

	
}
