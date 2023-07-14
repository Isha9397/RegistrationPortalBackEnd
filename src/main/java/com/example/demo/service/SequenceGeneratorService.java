package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserSequence;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import java.util.Objects;

@Service
public class SequenceGeneratorService {
	
	@Autowired
	private MongoOperations mongoOperations;
	
	public int getSequenceNumber(String sequenceName)
	{
		Query query=new Query(Criteria.where("id").is(sequenceName));
		System.out.println("query is --"+query);
		Update update=new Update().inc("seq", 1);
		System.out.println("update is --"+update);
		UserSequence counter= mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(true),UserSequence.class);
		System.out.println("counter is ---"+counter);
		//		if(!Objects.isNull(counter))
//		{
//			return counter.getSeq();
//		}
//		else
//			return 1;
		return !Objects.isNull(counter)? counter.getSeq() : 1;
	}

}
