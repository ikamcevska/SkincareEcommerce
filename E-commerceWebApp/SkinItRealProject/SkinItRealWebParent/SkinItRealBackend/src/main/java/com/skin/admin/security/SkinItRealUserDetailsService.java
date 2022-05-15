package com.skin.admin.security;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.skin.admin.user.UserRepository;
import com.skin.common.entity.User;

public class SkinItRealUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=userRepo.getUserByEmail(email);
		if(user!=null) {
			return new SkinItRealUserDetails(user);
		}
		throw new UsernameNotFoundException("Could not find user with that email");
		
	}

}
