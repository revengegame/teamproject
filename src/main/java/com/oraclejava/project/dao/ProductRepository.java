package com.oraclejava.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.oraclejava.project.dto.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

	// 농산물 카테고리
	@Query("select p from Product p where p.product_div=1 or p.product_div=2")
	List<Product> findAllfruitvegetable();

	// 채소 카테고리
	@Query("select p from Product p where p.product_div=1")
	List<Product> findAllvegetable();

	// 과일 카테고리
	@Query("select p from Product p where p.product_div=2")
	List<Product> findAllfruit();

	// 수산물 카테고리
	@Query("select p from Product p where p.product_div=3")
	List<Product> findAllfish();

	// 냉동식품 카테고리
	@Query("select p from Product p where p.product_div=4")
	List<Product> findAllffrozenfood();

	// 신상품순 나열
	@Query("select p from Product p where to_char(p.createdDate, 'yyyymm') = to_char(sysdate, 'yyyymm')")
	List<Product> findNewproduct();

	// 세일상품
	@Query("select p from Product p where p.product_sale > 0 order by p.product_date")
	List<Product> findSaleproduct();

	// 낮은가격순
	@Query("select p from Product p order by p.product_price")
	List<Product> findLowpriceproduct();

	// 높은가격순
	@Query("select p from Product p order by p.product_price desc")
	List<Product> findHighpriceproduct();

	// 검색
	@Query("select p from Product p where p.product_name like %:keyword%")
	List<Product> findsearchproduct(String keyword);
	
	// 판매자 상품 검색
	@Query("select p from Product p where p.user_id = :user_id")
	List<Product> findUserProduct(String user_id);
	
	// 베스트 상품 검색
	@Query("select p from Product p where p.product_id = :product_id")
	Product findBestProduct(int product_id);
	
	
}
