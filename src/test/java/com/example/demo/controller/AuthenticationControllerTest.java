package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.Repository.Repo;
import com.example.demo.model.User;
import com.example.demo.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationControllerTest {
	
	@InjectMocks
	private AuthenticationController authC;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private UserServiceImpl userService;
	
	
	@Mock
	private Repo userRepo;
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(authC).build();
	}
	
	List<User> cList = new ArrayList<User>();
	
	@Test
	public void addUserSuccess() throws Exception
	{
		User user = new User();
		user.setAddress("LA");
		user.setContact("7774039597");
		user.setCountry("US");
		
		when(userService.addUser(any())).thenReturn(user);
		cList.add(user);
		
		
		assertEquals(1,cList.size());
mockMvc.perform(MockMvcRequestBuilders.post("/auth/user/registerUser").contentType(MediaType.APPLICATION_JSON)
		.content(new ObjectMapper().writeValueAsString(user))).andExpect(MockMvcResultMatchers.status().isCreated());
		
	}
	
	@Test
	public void addUserFailure() throws Exception
	{
		
		when(userService.addUser(any())).thenReturn(null);
		
		User u1 = userService.addUser(null);
		assertNull(u1);
		
mockMvc.perform(MockMvcRequestBuilders.post("/auth/user/registerUser").contentType(MediaType.APPLICATION_JSON)
.content(new ObjectMapper().writeValueAsString(u1))).andExpect(MockMvcResultMatchers.status().is4xxClientError());

		
	}

}
