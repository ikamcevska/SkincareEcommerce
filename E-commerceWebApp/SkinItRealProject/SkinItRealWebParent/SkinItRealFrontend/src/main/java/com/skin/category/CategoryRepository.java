package com.skin.category;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.skin.common.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
	@Query("SELECT c FROM Category c WHERE c.enabled=true")
	public List<Category> findAllEnabled();
	
	@Query("SELECT c FROM Category c WHERE c.enabled=true AND c.alias=?1")
	public Category findByAliasEnabled(String alias);

}
