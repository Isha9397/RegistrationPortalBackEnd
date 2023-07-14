package com.example.demo.service;

import java.util.List;
import java.util.Set;

import com.example.demo.exception.TwoDependentsExistsException;
import com.example.demo.model.Dependent;

public interface DependentService {
	
	public Set<Dependent> getAllDependents(int id);
	public List<Dependent>findByMemberId(int member_id);
	public boolean addDependent(Dependent dependent) throws TwoDependentsExistsException;
	public boolean updateDependentDetails(Dependent dependent);
	public boolean updateDependentForMemberId(int member_id,Dependent dependent);

}
