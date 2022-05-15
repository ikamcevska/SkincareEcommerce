package com.skin.admin.product;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skin.common.entity.Product;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;
	 
	public List<Product> listAll(){
		return (List<Product>) repo.findAll();
	}
	public Product save(Product product) {
		if(product.getId()==null) {
			product.setCreatedTime(new Date());
		}
		if(product.getAlias()==null || product.getAlias().isEmpty()) {
			String defaultAlias=product.getName().replaceAll(" ","-");
			product.setAlias(defaultAlias);
		}else {
			product.setAlias(product.getAlias().replaceAll(" ","-"));
		}
		product.setUpdatedTime(new Date());
		return repo.save(product);
	}
	public void delete(Integer id)throws ProductNotFoundException{
		Long countById=repo.countById(id);
		if(countById==null || countById==0){
			throw new ProductNotFoundException("Could not find any product with ID " +id);
		}
		repo.deleteById(id);
	}
}
