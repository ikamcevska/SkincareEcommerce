package com.skin.admin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan({"com.skin.common.entity","com.skin.admin.user","com.skin.admin.product"})
@ComponentScan
public class SkinItRealBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkinItRealBackendApplication.class, args);
	}

}
