package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.Repository.Repo;
import com.example.demo.model.User;

public class UserServiceImplTest {
	
	@InjectMocks
	private UserServiceImpl userService;
	@Autowired
	private MockMvc mockMvc;
	@Mock
	private Repo userRepo;
	
	@BeforeEach
	public void init()
	{
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
	}

	List<User> userList = new ArrayList<User>();
	
	@Test
	public void getAllSuccess() throws Exception
	{
		User user = new User();
		user.setAddress("LA");
		user.setDependentName("Keith");
		
		userList.add(user);
		when(userRepo.findAll()).thenReturn(userList);
		
		List<User> uList = userService.getAllUsers();
		assertEquals(userList, uList);
		
	}
	
	@Test
	public void getAllUsersFailure() throws Exception
	{
		
		when(userRepo.findAll()).thenReturn(null);
		
		List<User> uList = userService.getAllUsers();
		assertNull(uList);
		
	}

}
