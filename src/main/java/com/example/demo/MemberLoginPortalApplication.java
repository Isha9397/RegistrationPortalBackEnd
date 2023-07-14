package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.example.demo.JwtFilter.SecurityFilter;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MemberLoginPortalApplication {

	@Bean
	public FilterRegistrationBean jwtFilter()
	{
		FilterRegistrationBean obj = new FilterRegistrationBean();
		obj.setFilter(new SecurityFilter());
		obj.addUrlPatterns("/api/user/*");
		obj.addUrlPatterns("/api/dependent/*");
		obj.addUrlPatterns("/api/member/*");
		return obj;
	}
	public static void main(String[] args) {
		SpringApplication.run(MemberLoginPortalApplication.class, args);
	}

}
