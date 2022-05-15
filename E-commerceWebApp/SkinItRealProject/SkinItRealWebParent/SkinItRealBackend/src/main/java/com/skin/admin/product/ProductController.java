package com.skin.admin.product;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.skin.admin.FileUploadUtil;
import com.skin.admin.brand.BrandService;
import com.skin.common.entity.Brand;
import com.skin.common.entity.Product;

@Controller
public class ProductController {
	@Autowired private ProductService service;
	@Autowired private BrandService brandService;
	
	
	@GetMapping("/products")
	public String listAll(Model model) {
		List<Product> listProducts=service.listAll();
		model.addAttribute("listProducts", listProducts);
		return "products/products";
	}
	@GetMapping("/products/new")
	public String newProduct(Model model) {
		List<Brand>listBrands=brandService.listAll();
		Product product=new Product();
		product.setEnabled(true);
		product.setInStock(true);
		model.addAttribute("product", product);
		model.addAttribute("listBrands", listBrands);
		return "products/product_form";
	}
	@PostMapping("/products/save")
	public String saveProduct(Product product,RedirectAttributes ra,
			@RequestParam("fileImage")MultipartFile multipartFile) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			product.setMainImage(fileName);
			
			Product savedProduct = service.save(product);
			String uploadDir = "SkinItRealWebParent/SkinItRealBackend/src/main/resources/static/images/product-images/" + savedProduct.getId();
			String uploadDir2 = "SkinItRealWebParent/SkinItRealFrontend/src/main/resources/static/images/product-images/" + savedProduct.getId();

			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			FileUploadUtil.cleanDir(uploadDir2);
			FileUploadUtil.saveFile(uploadDir2, fileName, multipartFile);
		}else {
				service.save(product);
			}
		ra.addFlashAttribute("message","The product has been saved successfully");
		return "redirect:/products";
	}
	
	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable(name="id")Integer id,Model model,RedirectAttributes ra){
	try{
		service.delete(id);
		ra.addFlashAttribute("message",
			"The product ID" +id+ " has been deleted successfully");
		}catch(ProductNotFoundException ex){
		ra.addFlashAttribute("message",ex.getMessage());
		}
	return "redirect:/products";
		}


}
