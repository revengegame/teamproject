package com.oraclejava.project.controller.seller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oraclejava.project.dao.ProductRepository;
import com.oraclejava.project.dao.ShoppingOrderRepository;
import com.oraclejava.project.dao.ShoppingUserRepository;
import com.oraclejava.project.dto.Product;
import com.oraclejava.project.dto.ShoppingUser;




@Controller
@RequestMapping("/seller")
public class SellerContoller {

	@Autowired
	private ShoppingUserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ShoppingUserRepository shoppingUserRepository;
	
	@Autowired
	private ShoppingOrderRepository shoppingOrderRepository;

	private Map<String, String> radioEnable1;
	
	private Map<String, String> initRadioEnable1() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("채소", "1");
		map.put("과일", "2");
		map.put("수산물", "3");
		map.put("냉동식품", "4");
		return map;
	}
	
		
	// 상품등록
	@RequestMapping(value="/upload", method = RequestMethod.GET)
	public String registration1 (Model model, @AuthenticationPrincipal UserDetails user){
		
		if(user != null) {
			ShoppingUser userId= userRepository.findByUserId(user.getUsername());
			model.addAttribute("userId", userId.getUser_id());
		}
		
		radioEnable1 = initRadioEnable1();
		
		model.addAttribute("radioEnable1", radioEnable1);
		return "/seller/upload";
	}
	
	@RequestMapping(value="/upload", method = RequestMethod.POST)
	public String createNewUser1 (Product product){
		
		if(product.getProduct_sale() > 0) {
			int sale = product.getProduct_price()*product.getProduct_sale()/100;
			product.setProduct_saleprice(product.getProduct_price()-sale);
		}
		
		productRepository.save(product);
		return "redirect:/seller/sellerPage";
	
		}
	
	// Seller page
	@RequestMapping(value="/sellerPage", method = {RequestMethod.GET,RequestMethod.POST})
	public String seller(Model model, @AuthenticationPrincipal UserDetails user) {
		
		if(user != null) {
			
			int count = 0;
			
			List<Product> product = productRepository.findUserProduct(user.getUsername());
			model.addAttribute("product", product);
			
			for(int i=0; i < product.size(); i++) {
				count += shoppingOrderRepository.countUserProducts(product.get(i).getProduct_id());				
			}
			
			model.addAttribute("orderCount", count);
		}
		
		return "/seller/sellerPage";
	}
	
	@RequestMapping(value="/upload1", method = RequestMethod.GET)
	public String registration (Model model, @AuthenticationPrincipal UserDetails user){
		
		if(user != null) {
			ShoppingUser shoppingUser= userRepository.findByUserId(user.getUsername());
			model.addAttribute("shoppingUser", shoppingUser);
		}
		
		return "seller/upload1";
	}
	
	@RequestMapping(value="/upload1", method = RequestMethod.POST)
	public String createNewUser (ShoppingUser shoppingUser,Model model){
		
		ShoppingUser user = shoppingUserRepository.findByUserId(shoppingUser.getUser_id());

		if(user != null) {
			user.setUser_add1(shoppingUser.getUser_add1());
			user.setUser_add2(shoppingUser.getUser_add2());
			user.setUser_add3(shoppingUser.getUser_add3());
			user.setUser_email(shoppingUser.getUser_email());
			user.setUser_name(shoppingUser.getUser_name());
			user.setUser_regnum(shoppingUser.getUser_regnum());
			user.setUser_phone(shoppingUser.getUser_phone());
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedPassword = passwordEncoder.encode(shoppingUser.getUser_pass());
			user.setUser_pass(encodedPassword);
			
			shoppingUserRepository.save(user);
		}
		
		
		return "redirect:/seller/sellerPage";
	
		}

	
	
	@RequestMapping(value="/shoppingorder", method = RequestMethod.GET)
	public String home(Model model) {

		model.addAttribute("shoppingOrder",shoppingOrderRepository.findToOrders());
		

		return "seller/shoppingorder";
	}
	
	@PostMapping("/productDelete")
	@ResponseBody
	public String productDelete(Integer product_id) {
		if(product_id != null) {
			productRepository.deleteById(product_id);
			return "success";
		}
		return "fail";
	}
	
}




