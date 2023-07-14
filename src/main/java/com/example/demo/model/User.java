package com.example.demo.model;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@Document(collection="member")
public class User {
	
	@Transient
	public static final String SEQUENCE_NAME="sequence";
	
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int id;
	private String username;
	private String password;
	private String contact;
	private String emailId;
	private String panNumber;
	private String dateOfBirth;
	private String address;
	private String state;
	private String country;
	private String dependentName;
	private String dependentDOB;
	private int age;
	
	@JsonIgnore
	@OneToMany(targetEntity=Dependent.class)
	private Set<Dependent> dependentList;
	
	public int getAge()
	{
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDependentName() {
		return dependentName;
	}
	public void setDependentName(String dependentName) {
		this.dependentName = dependentName;
	}
	public String getDependentDOB() {
		return dependentDOB;
	}
	public void setDependentDOB(String dependentDOB) {
		this.dependentDOB = dependentDOB;
	}
	public Set<Dependent> getDependentList() {
		return dependentList;
	}
	public void setDependentList(Set<Dependent> dependentList) {
		this.dependentList = dependentList;
	}
	
	public User(int id, String username, String password, String contact, String emailId, String panNumber,
			String dateOfBirth, String address, String state, String country, String dependentName, String dependentDOB,
			int age, Set<Dependent> dependentList) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.contact = contact;
		this.emailId = emailId;
		this.panNumber = panNumber;
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.state = state;
		this.country = country;
		this.dependentName = dependentName;
		this.dependentDOB = dependentDOB;
		this.age = age;
		this.dependentList = dependentList;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", contact=" + contact
				+ ", emailId=" + emailId + ", panNumber=" + panNumber + ", dateOfBirth=" + dateOfBirth + ", address="
				+ address + ", state=" + state + ", country=" + country + ", dependentName=" + dependentName
				+ ", dependentDOB=" + dependentDOB + ", age=" + age + ", dependentList=" + dependentList + "]";
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

}
