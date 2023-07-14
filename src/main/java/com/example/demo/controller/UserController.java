package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.example.demo.exception.PanNoCriterialDoesNotMeetException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers()
	{
		List<User> userList = userService.getAllUsers();
		if(userList!=null)
		{
			return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
		}
		return new ResponseEntity<String>("userList is empty", HttpStatus.OK);
	}
	
//	@GetMapping("/getMemberById/{id}")
//	public ResponseEntity<?> getMemberById(@PathVariable("id") int id)
//	{
//		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		System.out.println("req is --"+req);
//		System.out.println("header from req is --"+req.getHeader("authorization"));
//		String jwtToken=req.getHeader("authorization").substring(7);
//		System.out.println("token from header is --"+jwtToken);
//		Claims claims = Jwts.parser().setSigningKey("secret key").parseClaimsJws(jwtToken).getBody();
//		System.out.println("claims in controller is --"+claims);
//		Object getfromClaimId=claims.get("id");
//		System.out.println("stringId from claims in controller is --"+getfromClaimId);
//		String fetchedTokenString=getfromClaimId.toString();
//		int fetchedTokenForGet=Integer.parseInt(fetchedTokenString);
//		System.out.println("fetchedTokenForGet is --"+fetchedTokenForGet);
//		
//		User reqMember = userService.getMemberById(id);
//		System.out.println("reqMember is --"+reqMember);
//		if(reqMember !=null)
//		{
//			return new ResponseEntity<User>(reqMember, HttpStatus.OK);
//		}
//		
//		return new ResponseEntity<String>("reqMember is not available", HttpStatus.NO_CONTENT);	
//	}
	
	@GetMapping("/getMemberById")
	public ResponseEntity<?> getMemberById()
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
		
		User reqMember = userService.getMemberById(fetchedTokenForGet);
		System.out.println("reqMember is --"+reqMember);
		if(reqMember !=null)
		{
			return new ResponseEntity<User>(reqMember, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("reqMember is not available", HttpStatus.NO_CONTENT);	
	}
	
	@GetMapping("/getByMemberName/{username}")
	public ResponseEntity<?> getByMemberName(@PathVariable("username") String username)
	{
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
//		headers.set("Access-Control-Allow-Origin", "");
//		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		List<User> reqMember = userService.getByMemberName(username);
		System.out.println("reqMember is --"+reqMember);
		if(reqMember !=null)
		{
			return new ResponseEntity<List<User>>(reqMember, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("reqMember is not available", HttpStatus.NO_CONTENT);	
	}

	@PutMapping("/updateMemberDetails")
	public ResponseEntity<?> updateMemberDetails(@RequestBody User user) throws PanNoCriterialDoesNotMeetException
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
		
		System.out.println("user's id is --"+user.getId());
		if(userService.updateMemberDetails(user,fetchedTokenForGet))
		{
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>("Member record cannot be updated!", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
