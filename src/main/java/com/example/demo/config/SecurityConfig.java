//package com.example.demo.config;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//@Configuration
//public class SecurityConfig {
//	
//	
//	@Bean
//	 public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        final List<GlobalAuthenticationConfigurerAdapter> configurers = new ArrayList<>();
//        configurers.add(new GlobalAuthenticationConfigurerAdapter() {
//                    @Override
//                    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//                        // auth.doSomething()
//                    }
//                }
//        );
//        return authConfig.getAuthenticationManager();
//    }
//
//}
