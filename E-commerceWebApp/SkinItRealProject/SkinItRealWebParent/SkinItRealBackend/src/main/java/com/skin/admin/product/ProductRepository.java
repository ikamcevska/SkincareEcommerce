package com.skin.admin.product;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.skin.common.entity.Product;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
	public Long countById(Integer id);
}
