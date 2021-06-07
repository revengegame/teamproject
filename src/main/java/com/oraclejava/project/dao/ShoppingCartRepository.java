package com.oraclejava.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.oraclejava.project.dto.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, String> {

	// 장바구니 삭제
	@Transactional
	@Modifying 
	@Query("delete from ShoppingCart w where w.user_id = :user_id and w.product_id = :product_id")
	void deletecart(String user_id, Integer product_id);
	
//	@Transactional
	@Query("select a.user_id, a.product_id, a.wishlist_count, b.product_name, b.product_price, b.product_sale, b.product_delprice, b.product_img from ShoppingCart a , Product b where b.product_id = a.product_id and a.user_id = :user_id")
	List<Object> findproductcart(String user_id);
		
	// 장바구니 확인
	@Query("select w from ShoppingCart w where w.product_id = :product_id and w.user_id = :user_id")
	ShoppingCart findcheckcart(Integer product_id, String user_id);
	
	// 장바구니 추가
	@Transactional
	@Modifying 
	@Query("update ShoppingCart w set w.wishlist_count = :wishlist_count where w.product_id = :product_id and w.user_id = :user_id")
	void addcount(String user_id, Integer product_id, Integer wishlist_count);
	
	// 주문 내역
	@Query("select p.product_name, p.product_price, p.product_delprice, w.wishlist_count, p.product_id from Product p join ShoppingCart w on p.product_id = w.product_id and w.user_id = :user_id")
	List<Object> findorder(String user_id);
	
	// 주문 완료 후 장바구니 삭제
	@Transactional
	@Modifying
	@Query("delete from ShoppingCart w where w.user_id=:user_id")
	void orderdeletecart(String user_id);
	
	// 주문 내역
	@Query("select o.order_id, p.product_name, o.order_price, to_char(o.order_date, 'YY-MM-DD') as createdDate, o.order_del from ShoppingOrder o, Product p where p.product_id=o.product_id and o.user_id=:user_id")
	List<Object> findorderlist(String user_id);	
}


