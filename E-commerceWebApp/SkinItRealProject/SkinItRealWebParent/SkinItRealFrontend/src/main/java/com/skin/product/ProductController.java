package com.skin.product;

import java.util.List;

import com.skin.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.skin.category.CategoryService;
import com.skin.common.entity.Category;

@Controller
public class ProductController {
	@Autowired private CategoryService categoryService;
	@Autowired private ProductService productService;

	
	@GetMapping("/c/{category_alias}")
	public String viewCategoryByPage(@PathVariable("category_alias") String alias,
			Model model) {
	Category category=categoryService.getCategory(alias);
	if(category==null) {
		return "error";
	}
	List<Category> listCategoryParents=categoryService.getCategoryParents(category);
	List<Product> productsByCategory = productService.findByCategory(category.getId());
	model.addAttribute("listCategoryParents", listCategoryParents);
	model.addAttribute("listProducts", productsByCategory);
   return "products_by_category";
}
}
