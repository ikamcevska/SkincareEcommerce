package com.skin.admin.brand;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.skin.common.entity.Brand;

public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer> {

	public Long countById(Integer id);
	public Brand findByName(String name);
	
	


}
