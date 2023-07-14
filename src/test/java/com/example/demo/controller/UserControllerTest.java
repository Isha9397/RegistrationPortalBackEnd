package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

public class UserControllerTest {
	
	@InjectMocks
	private UserController userC;
	
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
		mockMvc = MockMvcBuilders.standaloneSetup(userC).build();
	}

	List<User> cList = new ArrayList<User>();
	
	@Test
	public void getAllSuccess() throws Exception
	{
		User user = new User();
		user.setAddress("LA");
		user.setContact("7774039597");
		user.setCountry("US");
		cList.add(user);
		when(userService.getAllUsers()).thenReturn(cList);
		
		List<User> uList = userService.getAllUsers();
		assertEquals(cList, uList);
		
mockMvc.perform(MockMvcRequestBuilders.get("/api/user/getAllUsers").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
