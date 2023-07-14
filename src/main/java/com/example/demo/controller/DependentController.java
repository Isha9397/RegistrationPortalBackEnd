package com.example.demo.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.exception.TwoDependentsExistsException;
import com.example.demo.model.Dependent;
import com.example.demo.model.User;
import com.example.demo.service.DependentService;
import com.example.demo.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

//@CrossOrigin()
@RestController
@RequestMapping("api/dependent")
public class DependentController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DependentService dependentService;
	
	@Autowired
	private AuthenticationController authController;
	
	@GetMapping("/getAllDependents/{id}")
	public ResponseEntity<?> getAllDependents(@PathVariable("id") int id)
	{
		Set<Dependent> dependentList = dependentService.getAllDependents(id);
		if(dependentList!=null)
		{
			return new ResponseEntity<Set<Dependent>>(dependentList, HttpStatus.OK);
		}
		return new ResponseEntity<String>("dependentList is empty", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getDependentsForMemberId")
	public ResponseEntity<?> getDependentsForMemberId()
	{
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		System.out.println("req is --"+req);
		System.out.println("header from req is --"+req.getHeader("authorization"));
		String jwtToken=req.getHeader("authorization").substring(7);
		System.out.println("token from header is --"+jwtToken);
		Claims claims = Jwts.parser().setSigningKey("secret key").parseClaimsJws(jwtToken).getBody();
		System.out.println("claims in controller is --"+claims);
		Object getfromClaimId=claims.get("id");
		System.out.println("stringId from claims in controller is --"+getfromClaimId);
		String fetchedTokenString=getfromClaimId.toString();
		int fetchedTokenForGet=Integer.parseInt(fetchedTokenString);
		System.out.println("fetchedTokenForGet is --"+fetchedTokenForGet);
		
		
		System.out.println("first check"+fetchedTokenForGet);
		List<Dependent> dependentList = dependentService.findByMemberId(fetchedTokenForGet);
		if(dependentList!=null)
		{
			return new ResponseEntity<List<Dependent>>(dependentList, HttpStatus.OK);
		}
		return new ResponseEntity<String>("dependentList is empty", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/addDependent")
	public ResponseEntity<?> addDependent(@RequestBody Dependent dependent) throws TwoDependentsExistsException
	{
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		System.out.println("req is --"+req);
		System.out.println("header from req is --"+req.getHeader("authorization"));
		String jwtToken=req.getHeader("authorization").substring(7);
		System.out.println("token from header is --"+jwtToken);
		Claims claims = Jwts.parser().setSigningKey("secret key").parseClaimsJws(jwtToken).getBody();
		System.out.println("claims in controller is --"+claims);
		Object getfromClaimId=claims.get("id");
		System.out.println("stringId from claims in controller is --"+getfromClaimId);
		String fetchedTokenString=getfromClaimId.toString();
		int fetchedTokenForGet=Integer.parseInt(fetchedTokenString);
		System.out.println("fetchedTokenForGet is --"+fetchedTokenForGet);
		
		User expectedMember = userService.getMemberById(fetchedTokenForGet);
		System.out.println("expectedMember in dependent is --"+expectedMember);
		if(expectedMember !=null)
		{
			expectedMember.setDependentDOB(dependent.getDependentDOB());//this will go to book table.. this will be DML operation
			System.out.println("post updation user is --"+expectedMember);
			dependent.setContact(dependent.getContact());///this will go to Reader table
			dependent.setEmailId(dependent.getEmailId());//this will go to Reader table
			//dependent.setMember_id_fk(dependent.getMember_id_fk());
			dependent.setMember_id_fk(fetchedTokenForGet);
			System.out.println("dependent with fields is --"+dependent);
			
			if(dependentService.addDependent(dependent))
				System.out.println("inside if");
			return new ResponseEntity<Dependent>(dependent, HttpStatus.CREATED);
				}
//			if(userService.updateMemberDetails(expectedMember) && dependentService.addDependent(dependent))
//					System.out.println("inside if");
//				return new ResponseEntity<Dependent>(dependent, HttpStatus.CREATED);
//					}
			return new ResponseEntity<>("dependent cannot be added", HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	
	@PutMapping("/updateDependentDetails")
	public ResponseEntity<?> updateDependentDetails(@RequestBody Dependent dependent)
	{
		if(dependentService.updateDependentDetails(dependent))
		{
			return new ResponseEntity<>(dependent, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>("Dependent record cannot be updated!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/updateDependentForMemberId")
	public ResponseEntity<?> updateDependentForMemberId(@RequestBody Dependent dependent)
	{
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		System.out.println("req is --"+req);
		System.out.println("header from req is --"+req.getHeader("authorization"));
		String jwtToken=req.getHeader("authorization").substring(7);
		System.out.println("token from header is --"+jwtToken);
		Claims claims = Jwts.parser().setSigningKey("secret key").parseClaimsJws(jwtToken).getBody();
		System.out.println("claims in controller is --"+claims);
		Object getfromClaimId=claims.get("id");
		System.out.println("stringId from claims in controller is --"+getfromClaimId);
		String fetchedTokenString=getfromClaimId.toString();
		int fetchedTokenForGet=Integer.parseInt(fetchedTokenString);
		System.out.println("fetchedTokenForGet is --"+fetchedTokenForGet);
		
		if(dependentService.updateDependentForMemberId(fetchedTokenForGet,dependent))
		{
			return new ResponseEntity<>(dependent, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>("Dependent record cannot be updated!", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	

}
