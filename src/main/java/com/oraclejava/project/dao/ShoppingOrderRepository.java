package com.oraclejava.project.dao;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oraclejava.project.dto.Product;
import com.oraclejava.project.dto.ShoppingOrder;

public interface ShoppingOrderRepository extends PagingAndSortingRepository<ShoppingOrder, Integer>{

	@Query("select o from ShoppingOrder o order by o.order_id asc")
	List<ShoppingOrder> findAllList(Pageable pageable);
	
	default List<ShoppingOrder> findToOrders() {
		return findAllList(PageRequest.of(0, 10));
	}
	
	@Query("select count(o) from ShoppingOrder o WHERE o.product_id = :product_id") 
	int countUserProducts(int product_id);
	
	@Query("select s.product_id from ShoppingOrder s group by s.product_id order by count(s.product_id) desc")
	List<Integer> bestProducts();
	
}
