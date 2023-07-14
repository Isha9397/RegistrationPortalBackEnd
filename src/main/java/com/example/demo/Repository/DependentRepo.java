package com.example.demo.Repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Dependent;
import com.example.demo.model.User;

public interface DependentRepo extends MongoRepository<Dependent,Integer>{
	
//	@Query(value="db.dependent.find({member_id_fk = member_id})")
//	Query query=new Query(Criteria.where("id").is("member_id_fk"));
	//@Query("{'member_id_fk' : ?0}")
//	@Query(value = "{member_id_fk : ?0}")
//	public Set<Dependent> findBymember_id_fk(int member_id_fk);

}
