package com.skin.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import com.skin.common.entity.Brand;
import com.skin.common.entity.Category;



@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {
	
	@Autowired
	private BrandRepository repo;
	
	@Test
	public void testCreateBrand1() {
		Category creams = new Category(4);
		Brand axi = new Brand("AXIS-Y");
		axi.getCategories().add(creams);
		
		Brand savedBrand = repo.save(axi);
		
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateBrand2() {
		Category  hairShampoo= new Category(17);
		Category hairMask = new Category(13);
		
		Brand christopheRobin = new Brand("Christophe Robin");
		christopheRobin.getCategories().add(hairShampoo);
		christopheRobin.getCategories().add(hairMask);
		
		Brand savedBrand = repo.save(christopheRobin);
		
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateBrand3() {
		Brand imFrom = new Brand("I'm from");
		
		imFrom.getCategories().add(new Category(5));	// category memory
		imFrom.getCategories().add(new Category(7));	// category internal hard drive
		
		Brand savedBrand = repo.save(imFrom);
		
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testFindAll() {
		Iterable<Brand> brands = repo.findAll();
		brands.forEach(System.out::println);
		
		assertThat(brands).isNotEmpty();
	}
	
	@Test
	public void testGetById() {
		Brand brand = repo.findById(1).get();
		
		assertThat(brand.getName()).isEqualTo("AXIS-Y");
	}
	
	@Test
	public void testUpdateName() {
		String newName = "Samsung Electronics";
		Brand samsung = repo.findById(3).get();
		samsung.setName(newName);
		
		Brand savedBrand = repo.save(samsung);
		assertThat(savedBrand.getName()).isEqualTo(newName);
	}
	
	@Test
	public void testDelete() {
		Integer id = 3;
		repo.deleteById(id);
		
		Optional<Brand> result = repo.findById(id);
		
		assertThat(result.isEmpty());
	}
}