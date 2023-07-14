package com.example.demo.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.example.demo.model.Dependent;
import com.example.demo.model.User;
import com.example.demo.JwtFilter.SecurityFilter;
import com.example.demo.Repository.DependentRepo;
import com.example.demo.Repository.Repo;
import com.example.demo.config.BCryptPasswordEncoder;
import com.example.demo.config.PasswordEncoder;
import com.example.demo.controller.AuthenticationController;
import com.example.demo.exception.PasswordCriteriaDoesNotMeetException;
import com.example.demo.exception.ContactNoGreaterThanReqException;
import com.example.demo.exception.EmailIdCriteriaDoesNotMeetException;
import com.example.demo.exception.MemberAlreadyExistsException;
import com.example.demo.exception.PanNoCriterialDoesNotMeetException;
import com.example.demo.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;


@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private Repo userRepo;
	
	@Autowired
    BCryptPasswordEncoder passwordEncoder;
	
	@Autowired DependentRepo dependentRepo;
	
	@Autowired
	private SequenceGeneratorService seqService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
//	@Autowired
//	private AuthenticationController authController;
	
//	@Autowired
//    private AuthenticationManager authenticationManager;
	
//	@Autowired
//	private SecurityFilter securityFilter;
	
//	@Autowired
//	private Authentication authentication;

	@Override
	public List<User> getAllUsers() {
		
		List<User> userList = userRepo.findAll();
		System.out.println("userList is --"+userList);
		if(userList !=null && userList.size()>0)
		{
//			for(User user:userList)
//			{
//				System.out.println("single user is---"+user);
//				LocalDate dob=LocalDate.parse(user.getDateOfBirth());
//				user.setAge(calculateAge(dob));
//				System.out.println("users'dob is --"+dob+"and age is --"+calculateAge(dob));
//				userList.add(user);
//				System.out.println("final list is ---"+userList);
//			}
			return userList;
		}
		
		return null;
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	     return new BCryptPasswordEncoder();
	 }
	
	@Override
	public User getMemberById(int id) {
		
//		HttpServletRequest httpReq = (HttpServletRequest) request;
//		String authHeader = httpReq.getHeader("authorization");
//		String jwtToken = authHeader.substring(7);
//		Claims claims = Jwts.parser().setSigningKey("secret key").parseClaimsJws(jwtToken).getBody();
//		System.out.println("claims is --"+claims);
//		String sub=claims.getSubject();
//		Object o=SecurityContextHolder.getContext().getAuthentication()
//        .getPrincipal();
//		System.out.println("o is --"+o);
//		String name=authentication.getName();
//		System.out.println("name from auth is --"+name);

//		String idFromToken=authController.getToken(id, null);
//		System.out.println("idFromToken is --"+idFromToken);
		
		System.out.println("requesting for id---"+id);
		Optional <User> reqUser = userRepo.findById(id);
		User userObj=reqUser.get();
		System.out.println("userObj in getMemberById--"+userObj);
		User finalUser=new User();
		System.out.println("expected member is "+reqUser);
		LocalDate dob = LocalDate.parse(userObj.getDateOfBirth());
		System.out.println("local dob is ---"+dob);
		System.out.println("age is --"+calculateAge(dob));
		if(reqUser.isPresent())
		{
			if(!userObj.getPassword().isEmpty())
			{
				finalUser.setId(userObj.getId());
				finalUser.setUsername(userObj.getUsername());
				finalUser.setContact(userObj.getContact());
				finalUser.setEmailId(userObj.getEmailId());
				finalUser.setPanNumber(userObj.getPanNumber());
				finalUser.setDateOfBirth(userObj.getDateOfBirth());
				finalUser.setAddress(userObj.getAddress());
				finalUser.setState(userObj.getState());
				finalUser.setCountry(userObj.getCountry());
				finalUser.setDependentName(userObj.getDependentName());
				finalUser.setPassword(passwordEncoder.encode(userObj.getPassword()));
				finalUser.setAge(calculateAge(dob));
			}
//			return reqUser.get();
			System.out.println("finalUser is ---"+finalUser);
			return finalUser;
		}
		return null;		
	}
	
	@Override
	public boolean updateMemberDetails(User user,int tokenId) throws PanNoCriterialDoesNotMeetException
	{
		System.out.println("user that we are updating is --"+user);
		Optional<User> userOpt = userRepo.findById(tokenId);
//		Optional<User> userOpt = userRepo.findById(user.getId());
		System.out.println("expected <optional>user is ---"+userOpt);
		User user1=userOpt.get();
		System.out.println("user obj is ---"+user1);
		if(user1 !=null)
		{
			if(!isValidPanNumber(user.getPanNumber()))
			{
				System.out.println("inside 5th if");
				throw new PanNoCriterialDoesNotMeetException();
			}
			else
			{
			user1.setEmailId(user.getEmailId());
			user1.setPanNumber(user.getPanNumber());
			user1.setAddress(user.getAddress());
			user1.setContact(user.getContact());
			user1.setUsername(user.getUsername());
			//user1.setId(tokenId);
			userRepo.save(user1);
			return true;
			}
		}
		return false;
		
	}
    
	public static boolean isValidEmailId(String emailId)
	{
		String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
		 Pattern p = Pattern.compile(regex);
		 System.out.println("p is ---"+p);
		 if (emailId == null) {
           return false;
		 }
		 Matcher m = p.matcher(emailId);
		 System.out.println("m is ---"+m);
		 return m.matches();
	}
	
    public static boolean
    isValidPassword(String password)
    {
        String regex = "^(?=.*[0-9])"
                       + "(?=.*[a-z])(?=.*[A-Z])"
                       + "(?=.*[@#$%^&+=])"
                       + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        System.out.println("p is ---"+p);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        System.out.println("m is ---"+m);
        return m.matches();
    }
    
    public static boolean
    isValidPanNumber(String panNumber)
    {
        String regex = "^(?=.*[0-9])"
                       + "(?=.*[a-z])(?=.*[A-Z])"
                       + "(?=\\S+$).{12}$";
        Pattern p = Pattern.compile(regex);
        System.out.println("p is ---"+p);
        if (panNumber == null) {
            return false;
        }
        Matcher m = p.matcher(panNumber);
        System.out.println("m is ---"+m);
        return m.matches();
    }
    
    public static int calculateAge(LocalDate dob)   
    {  
    LocalDate curDate = LocalDate.now();  
    System.out.println("curDate is --"+curDate);
    //calculates the amount of time between two dates and returns the years  
    if ((dob != null) && (curDate != null))   
    {  
    return Period.between(dob, curDate).getYears();  
    }  
    else  
    {  
    return 0;  
    }  
    }  
    
    public static boolean isValidContactNumber(String contactNo)
    {
    	 String regex = "^(?=.*[0-9])"
                 + "(?=\\S+$).{11}$";
    	 Pattern p = Pattern.compile(regex);
         System.out.println("p is ---"+p);
         if (contactNo == null) {
             return false;
         }
         Matcher m = p.matcher(contactNo);
         System.out.println("m is ---"+m);
         return m.matches();
    }
    
	@Override
	public User addUser(User user) throws MemberAlreadyExistsException,PasswordCriteriaDoesNotMeetException,ContactNoGreaterThanReqException,PanNoCriterialDoesNotMeetException,EmailIdCriteriaDoesNotMeetException
	{
//		Optional<User> userObj=userRepo.findById(user.getId());
//		System.out.println("userObj for id is ---"+userObj);
		user.setId(seqService.getSequenceNumber(User.SEQUENCE_NAME));
		System.out.println("user in addUser method is --"+user);
		System.out.println("user DOB is ---"+user.getDateOfBirth());
		LocalDate dob = LocalDate.parse(user.getDateOfBirth());
		System.out.println("local dob is ---"+dob);
		System.out.println("age is --"+calculateAge(dob));
		
//		if(userObj.isPresent())
//		{
//			System.out.println("inside 2nd if");
//			throw new MemberAlreadyExistsException();
//		}
		
		if(!isValidPassword(user.getPassword()))
		{
			System.out.println("inside 3rd if");
			throw new PasswordCriteriaDoesNotMeetException();
		}
		if(!isValidContactNumber(user.getContact()))
		{
			System.out.println("inside 4th if");
			throw new ContactNoGreaterThanReqException();
		}
		if(!isValidPanNumber(user.getPanNumber()))
		{
			System.out.println("inside 5th if");
			throw new PanNoCriterialDoesNotMeetException();
		}
		if(!isValidEmailId(user.getEmailId()))
		{
			throw new EmailIdCriteriaDoesNotMeetException();
		}
		user.setAge(calculateAge(dob));
		System.out.println("final user with age is --"+user);
		//user.setDependentDOB(dependentRepo.findById(user.getId()));
		return userRepo.save(user);	
		//return null;
	}

	@Override
	public boolean validateUser(String username, String password)
	{
		System.out.println("repo--"+userRepo);
		List<User>fetchedUsers=userRepo.findAll();
		System.out.println("fetched users are --"+fetchedUsers);
		User user = userRepo.validateUser(username, password);
		System.out.println("validated user is ---------"+user);
		if(user!=null)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public List<User> getByMemberName(String username) 
	{
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Access-Control-Allow-Origin", "");
		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		Query query=new Query();
		System.out.println("username is  "+username);
		query.addCriteria(Criteria.where("username").is(username));
		System.out.println("add dependent query is --"+query);
		System.out.println("query obj is --"+query.getQueryObject());
		List<User> fetcheddep= mongoTemplate.find(query, User.class);
		System.out.println("fetcheddep is "+fetcheddep);	
		
		return fetcheddep;	 
	}
	
//	@Value("${jwt.secret}")
//	private String secret;
	
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
