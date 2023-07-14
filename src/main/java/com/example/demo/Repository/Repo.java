package com.example.demo.Repository;

import java.util.Optional;

//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface Repo extends MongoRepository<User,Integer>{
//	@Query(value="select u from User u where u.username = :username and u.password= :password")
	@Query(value="{username: ?0, password: ?1}")
	public User validateUser(String username, String password);

}
