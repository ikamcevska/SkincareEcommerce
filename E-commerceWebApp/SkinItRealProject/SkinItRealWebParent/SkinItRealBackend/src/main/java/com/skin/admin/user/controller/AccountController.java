package com.skin.admin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.skin.admin.security.SkinItRealUserDetails;
import com.skin.admin.user.UserService;
import com.skin.common.entity.User;

@Controller
public class AccountController {
	@Autowired
	private UserService userservice;
	
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal SkinItRealUserDetails loggedUser,Model model) {
		String email=loggedUser.getUsername();
		User user=userservice.getByEmail(email);
		model.addAttribute("user", user);
		return "user/account_form";
	}
	//@PostMapping("/account/update")
	//public String saveDetails(User user,RedirectAttributes redirectAttributes){
	//	System.out.println(user);
	//	userservice.save(user); 
	//	redirectAttributes.addFlashAttribute("message","The user has been saved succesfully");
	//	return "redirect:/users"; 
	//}

}
