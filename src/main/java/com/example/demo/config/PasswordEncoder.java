package com.example.demo.config;

import org.springframework.context.annotation.Bean;



public class PasswordEncoder {
	
	@Bean
//	public PasswordEncoder encoder() {
//	    return new BCryptPasswordEncoder();
//	}
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	     return new BCryptPasswordEncoder();
	 }
	
	public String encode(String passWord) {
        // TODO Auto-generated method stub
        return null;
    }

}
