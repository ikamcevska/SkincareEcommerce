package com.skin.admin.security;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public UserDetailsService userDetailsService() {
		return new SkinItRealUserDetailsService();
	}
	@Bean
	public PasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passEncoder());
		return authProvider;
		
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/users/**","/settings/**","/states/**").hasAnyAuthority("Admin")
		.antMatchers("/categories/**").hasAnyAuthority("Admin","Editor")
		.antMatchers("/brands/**").hasAnyAuthority("Admin","Editor")
		.antMatchers("/produts/**").hasAnyAuthority("Admin")
		.antMatchers("/products/**").hasAnyAuthority("Admin","Editor","Salesperson","Shipper")
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
			.loginPage("/login")
			.usernameParameter("email")
			.permitAll()
		.and().logout().permitAll()
		.and().rememberMe().key("173197IvAnAkAmCeVsKa").tokenValiditySeconds(7*24*60*60);
		//http.authorizeRequests()
		//.anyRequest().permitAll();
		
	}
	//za statickite resursi da mozat da se prikazuvaat(slikata -logo za login str)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**","/js/**","webjars/**");
	}
	

}
