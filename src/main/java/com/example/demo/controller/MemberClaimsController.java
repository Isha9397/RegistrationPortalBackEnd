package com.example.demo.controller;

import java.util.List;

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

import com.example.demo.exception.ClaimNumberAlreadyExists;
import com.example.demo.exception.DateDifferenceException;
import com.example.demo.exception.EmailIdCriteriaDoesNotMeetException;
import com.example.demo.model.MemberClaims;
import com.example.demo.service.MemberClaimsServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

//@CrossOrigin("*")
@RestController
@RequestMapping("api/member")
public class MemberClaimsController {
	
	@Autowired
	private MemberClaimsServiceImpl memberClaimsService;
    
    @GetMapping("/getClaimByClaimNum/{id}")
	public ResponseEntity<?> getClaimByClaimNum(@PathVariable("id") int id)
	{
		MemberClaims reqClaim = memberClaimsService.getClaimByClaimNum(id);
		if(reqClaim !=null)
		{
			return new ResponseEntity<MemberClaims>(reqClaim, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("reqClaim is not available", HttpStatus.NO_CONTENT);
		
	}
    
    @GetMapping("/GetClaimByMemberId")
	public ResponseEntity<?> GetClaimByMemberId()
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
		
		List<MemberClaims> reqClaim = memberClaimsService.getClaimByMemberId(fetchedTokenForGet);
		if(reqClaim !=null)
		{
			return new ResponseEntity<List<MemberClaims>>(reqClaim, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("reqClaim is not available", HttpStatus.NO_CONTENT);
		
	}
    
    @PostMapping("/submitClaims")
	public ResponseEntity<?> submitClaims(@RequestBody MemberClaims memberClaims) throws ClaimNumberAlreadyExists,DateDifferenceException,EmailIdCriteriaDoesNotMeetException
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
		
		if(memberClaimsService.submitClaims(memberClaims,fetchedTokenForGet)!=null)
		{
			System.out.println("member is ---"+memberClaims);
			return new ResponseEntity<MemberClaims>(memberClaims, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<String>("Claim object is empty", HttpStatus.CONFLICT);
	}
    
//    @PutMapping("/updateClaim")
//	public ResponseEntity<?> updateClaim(@RequestBody MemberClaims memberClaims)
//	{
//		if(memberClaimsService.updateClaim(memberClaims))
//		{
//			return new ResponseEntity<>(memberClaims, HttpStatus.CREATED);
//		}
//		
//		return new ResponseEntity<String>("claim record cannot be updated!", HttpStatus.INTERNAL_SERVER_ERROR);
//	}

}
