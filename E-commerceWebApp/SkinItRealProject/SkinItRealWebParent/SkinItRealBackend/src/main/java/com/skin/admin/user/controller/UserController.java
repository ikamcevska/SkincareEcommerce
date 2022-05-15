package com.skin.admin.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.skin.admin.user.UserNotFoundException;
import com.skin.admin.user.UserService;
import com.skin.common.entity.Role;
import com.skin.common.entity.User;

@Controller
public class UserController {
	@Autowired
	private UserService userservice;
	
	@GetMapping("/users")
	public String listFirstPage(Model model){
		return listByPage(1,model,"firstName","asc");
	}
	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name="pageNum")int pageNum,Model model,
			@Param("sortField") String sortField,@Param("sortDir") String sortDir) {
		System.out.println("Sort field:" + sortField);
		System.out.println("Sort order:" + sortDir);
		Page<User> page=userservice.listByPage(pageNum,sortField,sortDir);
		List<User> listUsers=page.getContent();
	
		long startCount=(pageNum-1)*UserService.USERS_PER_PAGE+1;
		long endCount=startCount+UserService.USERS_PER_PAGE-1;
		if(endCount >page.getTotalElements()) {
			endCount=page.getTotalElements();
		}
		String reverseSortDir=sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("currentPage",pageNum);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("startCount",startCount);
		model.addAttribute("endCount",endCount);
		model.addAttribute("totalElements",page.getTotalElements());
		model.addAttribute("listUsers",listUsers);
		model.addAttribute("sortField",sortField);
		model.addAttribute("sortDir",sortDir);
		model.addAttribute("reverseSortDir",reverseSortDir);
		
		return "user/users";
		
	}

	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role>listRoles=userservice.listRoles();
		User user=new User();
		user.setEnabled(true);
		model.addAttribute("user",user);
		model.addAttribute("listRoles",listRoles);
		model.addAttribute("pageTitle","Create new user");
		return "user/user_form";
	}
	@PostMapping("/users/save")
	public String saveUser(User user,RedirectAttributes redirectAttributes){
		System.out.println(user);
		userservice.save(user); 
		redirectAttributes.addFlashAttribute("message","The user has been saved succesfully");
		return "redirect:/users"; 
	}
   @GetMapping("/users/edit/{id}")
   public String editUser(@PathVariable(value="id")Integer id,Model model) {
	   try {
	   User user=userservice.get(id);
	   List<Role>listRoles=userservice.listRoles();
	   model.addAttribute("listRoles",listRoles);
	   model.addAttribute("user",user);
	   model.addAttribute("pageTitle","Edit user");
	   return "user/user_form";
	   }catch(UserNotFoundException ex) {
		   return "redirect:/users";
	   }
	   
   }
   @GetMapping("/users/delete/{id}")
   public String deleteUser(@PathVariable(value="id")Integer id,Model model,RedirectAttributes redirectAttributes) throws UserNotFoundException{
   userservice.delete(id);
   return "redirect:/users";
   }
}
