package com.example.demo.service;

import java.util.List;

import com.example.demo.exception.PasswordCriteriaDoesNotMeetException;
import com.example.demo.exception.ContactNoGreaterThanReqException;
import com.example.demo.exception.EmailIdCriteriaDoesNotMeetException;
import com.example.demo.exception.MemberAlreadyExistsException;
import com.example.demo.exception.PanNoCriterialDoesNotMeetException;
import com.example.demo.model.User;

public interface UserService {

	public List<User> getAllUsers();
	public User getMemberById(int id);
	public List<User> getByMemberName(String username);
	public boolean updateMemberDetails(User user,int id) throws PanNoCriterialDoesNotMeetException;
	public User addUser(User user) throws MemberAlreadyExistsException,PasswordCriteriaDoesNotMeetException,ContactNoGreaterThanReqException,PanNoCriterialDoesNotMeetException,EmailIdCriteriaDoesNotMeetException;
	public boolean validateUser(String username, String password);

}

