package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.DependentRepo;
import com.example.demo.Repository.Repo;
import com.example.demo.controller.AuthenticationController;
import com.example.demo.exception.TwoDependentsExistsException;
import com.example.demo.model.Dependent;
import com.example.demo.model.User;

@Service
public class DependentServiceImpl implements DependentService{

	@Autowired
	private DependentRepo dependentRepo;
	
	@Autowired
	private Repo userRepo;
	
	@Autowired
	private DependentSequenceGeneratorService dependentSeqService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private AuthenticationController authController;
	

	@Override
	public Set<Dependent> getAllDependents (int id) {
//		for(int i=0;i<2;i++)
//		{}
		Optional<Dependent> dependentOpt = dependentRepo.findById(id);
		System.out.println("dependentOpt is --"+dependentOpt);
		Dependent dependentObj=dependentOpt.get();
		System.out.println("dependentObj is ---"+dependentObj);
		Set<Dependent> dependentList=new HashSet();
		dependentList.add(dependentObj);
		System.out.println("dependentList is --"+dependentList);
		if(dependentList !=null && dependentList.size()>0)
		{
			return dependentList;
		}	
		return null;
	}
	
	@Override
	public List<Dependent>findByMemberId(int meber_id) 
	{
		Query query=new Query();
		System.out.println("meber_id is  "+meber_id);
		query.addCriteria(Criteria.where("member_id_fk").is(meber_id));
		System.out.println("add dependent query is --"+query);
		System.out.println("query obj is --"+query.getQueryObject());
		int fetchedId=query.getQueryObject().getInteger("member_id_fk");
		System.out.println("fetchedId is --"+fetchedId);
		List<Dependent>dep= mongoTemplate.find(query, Dependent.class);
		System.out.println("dep is "+dep);	
		return dep;	 
	}
	
	@Override
	public boolean addDependent(Dependent dependent) throws TwoDependentsExistsException
	{
//		List<Integer>member_id_fk_list=new ArrayList<Integer>();
//		int id=dependent.getMember_id_fk();
//		System.out.println("id is --"+id);
//		System.out.println("member_id_fk_list before is --"+member_id_fk_list);
//		if(member_id_fk_list.contains(id))
//		{
//			System.out.println("id "+id+"already exists");
//		}
//		member_id_fk_list.add(id);
//		System.out.println("member_id_fk_list after is --"+member_id_fk_list);
		
//		Query query=new Query();
//		query.addCriteria(Criteria.where("id").is(dependent.getMember_id_fk()));
//		System.out.println("add dependent query is --"+query);
//		System.out.println("query obj is --"+query.getQueryObject());
//		int fetchedId=query.getQueryObject().getInteger("id");
//		System.out.println("fetchedId is --"+fetchedId);
//		 System.out.println("mongo template is  "+mongoTemplate.find(query, Dependent.class));
//		 System.out.println("mongo template string is  "+mongoTemplate.find(query, Dependent.class).toString());
//		System.out.println("query toString is --"+query.toString());
		
//		Set<Dependent> dependentList=dependentRepo.findBymember_id_fk(dependent.getMember_id_fk());
//		System.out.println("findByMemberId is ---"+dependentList);
		
	
		List<Dependent> dep=findByMemberId(dependent.getMember_id_fk());
		System.out.println("dep from findByMemberId is --"+dep);
		int count=dep.size();
		System.out.println("dep count is --"+count);
		if(count>=2)
		{
			throw new TwoDependentsExistsException();
		}
		
		Dependent dependentObj = new Dependent();	
		dependentObj.setId(dependentSeqService.getSequenceNumber(Dependent.SEQUENCE_NAME));
		System.out.println("dependentObj is --"+dependentObj);
		dependentObj.setContact(dependent.getContact());
		dependentObj.setEmailId(dependent.getEmailId());
		dependentObj.setMember_id_fk(dependent.getMember_id_fk());
		dependentObj.setDependentDOB(dependent.getDependentDOB());
		System.out.println("after adding dependent--"+dependentObj);
		dependentRepo.save(dependentObj);
		
		Optional<User> user=userRepo.findById(dependentObj.getMember_id_fk());
		User userObj=user.get();
		System.out.println("userObj is --"+userObj);
		userObj.setDependentDOB(dependent.getDependentDOB());
		System.out.println("user post is --"+userObj);
		userRepo.save(userObj);
		
		return true;
		}
	
	@Override
	public boolean updateDependentDetails(Dependent dependent) 
	{
		List<Dependent> dependentOpt = findByMemberId(dependent.getMember_id_fk());
		System.out.println("list is --"+dependentOpt);
		
		for(Dependent dependent1:dependentOpt)
		{
			System.out.println("dependent1 is --"+dependent1);
			dependent1.setDependentDOB(dependent.getDependentDOB());
			dependent1.setMember_id_fk(dependent.getMember_id_fk());
			dependentRepo.save(dependent1);
		}
		return true;
		
//		Optional<Dependent> dependentOpt = dependentRepo.findById(dependent.getId());
//		System.out.println("expected <optional>Dependent is ---"+dependentOpt);
//		Dependent dependent1=dependentOpt.get();
//		System.out.println("dependent obj is ---"+dependent1);
//		if(dependent1 !=null)
//		{
//			dependent1.setDependentDOB(dependent.getDependentDOB());
//			dependentRepo.save(dependent1);
//		System.out.println("dependent being updated is --"+dependent);
//		dependent.setDependentDOB(dependent.getDependentDOB());
//		dependentRepo.save(dependent);
////			
//			Optional<User> user=userRepo.findById(dependent.getId());
//			User userObj=user.get();
//			System.out.println("userObj is --"+userObj);
//			userObj.setDependentDOB(dependent.getDependentDOB());
//			System.out.println("user post is --"+userObj);
//			userRepo.save(userObj);
//			return true;
//		}
//		return false;
	}
	
	@Override
	public boolean updateDependentForMemberId(int meber_id,Dependent dependent) 
	{
		System.out.println("meber_id is --"+meber_id);
		List<Dependent> dependentOpt = findByMemberId(meber_id);
		System.out.println("list is --"+dependentOpt);
		
		for(Dependent dependent1:dependentOpt)
		{
			System.out.println("dependent1 is --"+dependent1);
			dependent1.setDependentDOB(dependent.getDependentDOB());
//			dependent1.setMember_id_fk(dependent.getMember_id_fk());
			dependent1.setMember_id_fk(meber_id);
			dependentRepo.save(dependent1);
		}
		return true;	
	}
	
}
