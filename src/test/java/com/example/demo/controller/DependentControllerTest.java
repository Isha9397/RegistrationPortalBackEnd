//package com.example.demo.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.example.demo.Repository.DependentRepo;
//import com.example.demo.Repository.Repo;
//import com.example.demo.model.Dependent;
//import com.example.demo.model.User;
//import com.example.demo.service.DependentServiceImpl;
//import com.example.demo.service.UserServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class DependentControllerTest {
//	
//	@InjectMocks
//	private DependentController depC;
//	
//	@Autowired
//	private MockMvc mockMvc;
//	
//	@Mock
//	private DependentServiceImpl depService;
//	
//	
//	@Mock
//	private DependentRepo depRepo;
//	
//	
//	@BeforeEach
//	public void init()
//	{
//		MockitoAnnotations.openMocks(this);
//		mockMvc = MockMvcBuilders.standaloneSetup(depC).build();
//	}
//
//	Set<Dependent> cList = new HashSet<Dependent>();
//	
//	@Test
//	public void getAllSuccess() throws Exception
//	{
//		Set<Dependent> dep11=new HashSet<>();		
//		Dependent dependent = new Dependent();
//		dependent.setDependentDOB("1999-03-02");
//		dependent.setContact("7774039597");
//		dependent.setEmailId("lily@gmail.com");
//		dep11.add(dependent);
//		
//		cList.add(dependent);
//		when(depService.getAllDependents(1)).thenReturn(dep11);
//		
//		Set<Dependent> uList = depService.getAllDependents(1);
//		assertEquals(dep11, uList);
//		
//mockMvc.perform(MockMvcRequestBuilders.get("/api/dependent/getAllDependents").contentType(MediaType.APPLICATION_JSON))
//		.andExpect(MockMvcResultMatchers.status().isOk());	
//	}
//	
//	@Test
//	public void addDependentSuccess() throws Exception
//	{
//		Dependent dependent = new Dependent();
//		dependent.setDependentDOB("1999-03-02");
//		dependent.setContact("7774039597");
//		dependent.setEmailId("lily@gmail.com");
//		
//		when(depService.addDependent(dependent)).thenReturn(true);
//		
//mockMvc.perform(MockMvcRequestBuilders.post("/api/dependent/addDependent").contentType(MediaType.APPLICATION_JSON)
//		.content(new ObjectMapper().writeValueAsString(dependent))).andExpect(MockMvcResultMatchers.status().isCreated());
//		
//	}
//
//}
