package com.skin.product;

import com.skin.common.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAllByCategoryId(Integer categoryId);

}
