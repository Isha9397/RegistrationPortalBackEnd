package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.ClaimNumberAlreadyExists;
import com.example.demo.exception.DateDifferenceException;
import com.example.demo.exception.EmailIdCriteriaDoesNotMeetException;
import com.example.demo.model.MemberClaims;

public interface MemberClaimsService {
	
	public MemberClaims getClaimByClaimNum(int claimNum);
	
	public List<MemberClaims> getClaimByMemberId(int memberId);
	
	//public MemberClaims getClaimByName(String name);
	
	public MemberClaims submitClaims(MemberClaims memberClaims,int id) throws ClaimNumberAlreadyExists,DateDifferenceException,EmailIdCriteriaDoesNotMeetException;
	
	//public boolean updateClaim(MemberClaims memberClaims);

}
