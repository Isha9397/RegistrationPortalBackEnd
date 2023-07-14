package com.example.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ContactNoGreaterThanReqException;
import com.example.demo.exception.EmailIdCriteriaDoesNotMeetException;
import com.example.demo.exception.MemberAlreadyExistsException;
import com.example.demo.exception.PanNoCriterialDoesNotMeetException;
import com.example.demo.exception.PasswordCriteriaDoesNotMeetException;
import com.example.demo.model.User;
import com.example.demo.service.SequenceGeneratorService;
import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;

//@CrossOrigin()
@RestController
@RequestMapping("auth/user")
public class AuthenticationController {
	
private Map<String, String> mapObj = new HashMap<String, String>();
	
	private UserService userServ;
	
	@Autowired
	private SequenceGeneratorService seqService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	public AuthenticationController(UserService userServ)
	{
		super();
		this.userServ = userServ;
	}
	
	
	@PostMapping("/registerUser")
	public ResponseEntity<?>  addUser(@RequestBody User user) throws MemberAlreadyExistsException,PasswordCriteriaDoesNotMeetException,ContactNoGreaterThanReqException,PanNoCriterialDoesNotMeetException,EmailIdCriteriaDoesNotMeetException
	{
		if(userServ.addUser(user)!=null)
		{
			System.out.println("User is ----------"+user);
			//user.setId(seqService.getSequenceNumber(User.SEQUENCE_NAME));
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("User data not inserted", HttpStatus.CONFLICT);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user)
	{
		try
		{
			System.out.println("password is -------"+user.getPassword());
			String jwtToken = generateToken(user.getUsername(), user.getPassword());
			mapObj.put("message", "User Successfully logged in");
			mapObj.put("token", jwtToken);			
		}
		catch(Exception e)
		{
			mapObj.put("message", "User not logged in");
			mapObj.put("token", null);
			return new ResponseEntity<String>("User credentials are invalid", HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(mapObj, HttpStatus.ACCEPTED);
	}
	
	
	public String generateToken(String username, String password) throws Exception, ServletException
	{
		String jwtToken = "";
		if(username ==null || password == null)
		{
			throw  new ServletException("Please enter valid username and password");
		}
		boolean flag = userServ.validateUser(username, password);	
		System.out.println("flag is ----"+flag);	
		if(!flag)
		{
			throw  new ServletException("Invalid username and password");
		}
		else
		{
			jwtToken="token";
//			jwtToken = Jwts.builder().setSubject(username).setIssuedAt(new Date())
//			.setExpiration(new Date(System.currentTimeMillis()+ 3000000))
//			.signWith(SignatureAlgorithm.HS256, "secret key").compact();
//			
			Map<String, Object> claims = new HashMap<>();
			int fetchedId=0;
			List<User>usersForName=userService.getByMemberName(username);
			System.out.println("usersForName is --"+usersForName);
			for(User u:usersForName)
			{
				fetchedId=u.getId();
			}
			System.out.println("fetchedId is --"+fetchedId);

			jwtToken=Jwts.builder().setClaims(claims).setSubject(username).claim("id", fetchedId).setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 3000000))
					.signWith(SignatureAlgorithm.HS256, "secret key").compact();
			
		}
		return jwtToken;
	}
	
//	public int getIdFromToken(String jwt)
//	{
//		String jwtToken = "";
//		Map<String, Object> claims = new HashMap<>();
//		int fetchedId=0;
//		
//		List<User>usersForName=userService.getByMemberName(username);
//		System.out.println("usersForName is --"+usersForName);
//		for(User u:usersForName)
//		{
//			fetchedId=u.getId();
//		}
//		System.out.println("fetchedId is --"+fetchedId);
//		
//	
//		
//		System.out.println("Token is --"+jwtToken);
//		Claims getClaim=getAllClaimsFromToken(jwtToken);
//		System.out.println("getClaim is --"+getClaim);
//		Object idFromToken=getClaim.get("id");
//		System.out.println("idFromToken is --"+idFromToken);
//		int idFromTokenInt=Integer.parseInt(idFromToken.toString());
//		System.out.println("idFromTokenInt is --"+idFromTokenInt);
//		return idFromTokenInt;
//	}
	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey("secret key").parseClaimsJws(token).getBody();
	}


}
