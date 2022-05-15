package com.skin.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.skin.common.entity.Role;
import com.skin.common.entity.User;


@DataJpaTest(showSql=false)
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	UserRepository repo;
	
	@Autowired
    TestEntityManager enitityManager;
	
	@Test
	public void testCreateNewUserWithOneRole(){
		Role roleAdmin=enitityManager.find(Role.class,1);
		User user01=new User("ivanakamcevska@gmail.com","ivana10001","Ivana","Kamcevska");
		user01.addRole(roleAdmin);
		User savedUser=repo.save(user01);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User user03=new User("simona@gmail","ss","Simona","Andova");
		Role roleEditor=new Role(3);
		Role roleAssistant=new Role(5);
		user03.addRole(roleEditor);
		user03.addRole(roleAssistant);
		
		User savedUser=repo.save(user03);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers(){
		Iterable<User>listUsers=repo.findAll();
		listUsers.forEach(user->System.out.println(user));
		 
	}
	@Test
	public void testGetUserById(){
		User user01=repo.findById(1).get();	
		System.out.println(user01);
		assertThat(user01).isNotNull();
	}
	@Test
	public void testUpdateUserDetails(){
		User user01=repo.findById(1).get();
		user01.setEnabled(true);
		user01.setEmail("ikamcevska@gmail.com");
		
		repo.save(user01);
	}
	@Test
	public void testUpdateUserRo1les(){
		User user02=repo.findById(2).get();
		Role roleEditor=new Role(3);
		Role roleSalesperson=new Role(2);
		user02.getRoles().remove(roleEditor);
		user02.addRole(roleSalesperson);

		repo.save(user02);
	}
	@Test
	public void testDeleteUser(){
		Integer userId=2;
		repo.deleteById(userId);
	}
	@Test
	public void testGetUserByEmail() {
		String email="ikamcevska@gmail.com";
		User user=repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}


	@Test
	public void testCountById(){
		Integer id=1;
		Long countById=repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
}
	@Test
	public void testListFirtPage(){
		int pageNumber=0;
		int pageSize=4;

		Pageable pageable=PageRequest.of(pageNumber,pageSize);
		Page<User>page=repo.findAll(pageable);
		
		List<User> listUsers=page.getContent();
		listUsers.forEach(user->System.out.println(user));
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
}
