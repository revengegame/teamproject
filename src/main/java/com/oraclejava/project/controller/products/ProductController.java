package com.oraclejava.project.controller.products;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oraclejava.project.dao.ProductRepository;
import com.oraclejava.project.dao.ShoppingOrderRepository;
import com.oraclejava.project.dto.Product;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductRepository productrepository;
	@Autowired
	ShoppingOrderRepository orderRepository;

	// 전체상품
	@RequestMapping(value = "/allproduct", method = RequestMethod.GET)
	public String allproduct(Model model) {

		model.addAttribute("products", productrepository.findAll());

		return "/products/allproduct";
	}

	// 농산물
	@RequestMapping(value = "/vegetable_fruit", method = RequestMethod.GET)
	public String vegefruit(Model model) {

		model.addAttribute("products", productrepository.findAllfruitvegetable());

		return "/products/vegetable_fruit";
	}

	// 채소
	@RequestMapping(value = "/vegetable", method = RequestMethod.GET)
	public String vegetablepage(Model model) {

		model.addAttribute("products", productrepository.findAllvegetable());

		return "/products/vegetable";
	}

	// 과일
	@RequestMapping(value = "/fruit", method = RequestMethod.GET)
	public String fruitpage(Model model) {

		model.addAttribute("products", productrepository.findAllfruit());

		return "/products/fruit";
	}

	// 수산물
	@RequestMapping(value = "/fish", method = RequestMethod.GET)
	public String fishpage(Model model) {

		model.addAttribute("products", productrepository.findAllfish());

		return "/products/fish";
	}

	// 냉동식품
	@RequestMapping(value = "/frozenfood", method = RequestMethod.GET)
	public String frozenfoodpage(Model model) {

		model.addAttribute("products", productrepository.findAllffrozenfood());

		return "/products/frozenfood";
	}

	// 신상품
	@RequestMapping(value = "/newproduct", method = RequestMethod.GET)
	public String newproductpage(Model model) {

		model.addAttribute("products", productrepository.findNewproduct());

		return "/products/newproduct";
	}

	// 세일상품
	@RequestMapping(value = "/saleproduct", method = RequestMethod.GET)
	public String saleproductpage(Model model) {

		model.addAttribute("products", productrepository.findSaleproduct());

		return "/products/saleproduct";
	}
	
	// 베스트셀러
	@RequestMapping(value = "/bestproduct", method = RequestMethod.GET)
	public String bestproductpage(Model model) {

		List<Integer> best = orderRepository.bestProducts();
		List<Product> products = new ArrayList<Product>();
		
		for(int i=0; i<6; i++) {
			products.add(productrepository.findBestProduct(best.get(i)));
		}
		
		model.addAttribute("products", products);

		return "/products/bestproduct";
	}
}
