package com.skin.product;

import com.skin.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public List<Product> findByCategory(Integer categoryId){
        return repo.findAllByCategoryId(categoryId);
    }

}
