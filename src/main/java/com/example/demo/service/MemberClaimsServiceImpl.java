package com.example.demo.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.MemberClaimsRepository;
import com.example.demo.exception.ClaimNumberAlreadyExists;
import com.example.demo.exception.DateDifferenceException;
import com.example.demo.exception.EmailIdCriteriaDoesNotMeetException;
import com.example.demo.model.Dependent;
import com.example.demo.model.MemberClaims;

@Service
public class MemberClaimsServiceImpl implements MemberClaimsService{
	
	@Autowired
	private MemberClaimsRepository memberClaimsRepo;
	
	@Autowired
	private ClaimSequenceGeneratorService ClaimSequenceService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public MemberClaims getClaimByClaimNum(int id) {

		System.out.println("repo is --"+memberClaimsRepo);
		System.out.println("claimNumber is --"+id);
		Optional <MemberClaims> reqClaim = memberClaimsRepo.findById(id);
		System.out.println("expected claim is --"+reqClaim);
		if(reqClaim.isPresent() )
		{
			return reqClaim.get();
		}
			return null;	
	}
	
//	@Override
//	public MemberClaims getClaimByName(String username) {
//
//		System.out.println("repo is --"+memberClaimsRepo);
//		System.out.println("name is --"+username);
//		Optional <MemberClaims> reqClaim = memberClaimsRepo.findByName(username);
//		if(reqClaim.isPresent() )
//		{
//			return reqClaim.get();
//		}
//		else
//		{
//			System.out.println("Claim does not exists for name "+username);
//			return null;	
//		}
//	}
	
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
	
	@Override
	public MemberClaims submitClaims(MemberClaims memberClaims,int id)throws ClaimNumberAlreadyExists,DateDifferenceException,EmailIdCriteriaDoesNotMeetException{

		//Optional<MemberClaims> claimObj=memberClaimsRepo.findById(memberClaims.getId());
		Optional<MemberClaims> claimObj=memberClaimsRepo.findById(id);
		System.out.println("memberClaims is --"+memberClaims);
		LocalDate admission= LocalDate.parse(memberClaims.getDateOfAdmission());
		LocalDate discharge= LocalDate.parse(memberClaims.getDateOfDischarge());
		boolean isNegative=Period.between(admission, discharge).isNegative();
		System.out.println("obj is --"+claimObj);
		if(claimObj.isPresent())
		{
			throw new ClaimNumberAlreadyExists();
		}
		if(isNegative)
		{
			throw new DateDifferenceException();
		}
		memberClaims.setId(ClaimSequenceService.getSequenceNumber(MemberClaims.SEQUENCE_NAME));
		memberClaims.setMemberId(id);
		memberClaimsRepo.save(memberClaims);
		System.out.println("updated repo---"+memberClaimsRepo);
		return memberClaims;
	}
	
	@Override
	public List<MemberClaims>getClaimByMemberId(int memberId) 
	{
		Query query=new Query();
		System.out.println("meber_id is  "+memberId);
		query.addCriteria(Criteria.where("memberId").is(memberId));
		System.out.println("add dependent query is --"+query);
		System.out.println("query obj is --"+query.getQueryObject());
		//int fetchedId=query.getQueryObject().getInteger("member_id_fk");
		//System.out.println("fetchedId is --"+fetchedId);
		List<MemberClaims>claimList= mongoTemplate.find(query, MemberClaims.class);
		System.out.println("dep is "+claimList);	
		return claimList;	 
	}
	
//	@Override
//	public boolean updateClaim(MemberClaims memberClaims) 
//	{
//		MemberClaims claimObj = memberClaimsRepo.findAll(memberClaims.getClaimNumber());
//		
//		if(claimObj !=null)
//		{
//			claimObj.setAddress(memberClaims.getAddress());;
//			claimObj.setCountry(memberClaims.getCountry());
//			claimObj.setEmailId(memberClaims.getEmailId());
//			memberClaimsRepo.save(claimObj);
//			return true;
//		}
//		return false;
//		
//	}

}
