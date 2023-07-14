package com.example.demo.Repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.MemberClaims;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface MemberClaimsRepository extends MongoRepository<MemberClaims,Integer> {

	//Optional<MemberClaims> findByName(String username);

}
