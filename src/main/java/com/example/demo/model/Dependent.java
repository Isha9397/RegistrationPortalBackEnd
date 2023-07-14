package com.example.demo.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Document(collection="dependent")
public class Dependent {
	
	@Transient
	public static final String SEQUENCE_NAME="sequence";
	
	@Id
	private int id;
	private String contact;
	private String emailId;
	private int member_id_fk;
	private String dependentDOB;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDependentDOB() {
		return dependentDOB;
	}
	public void setDependentDOB(String dependentDOB) {
		this.dependentDOB = dependentDOB;
	}
//	public int getId() {
//		return id;
//	}
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
	public int getMember_id_fk() {
		return member_id_fk;
	}
	public void setMember_id_fk(int member_id_fk) {
		this.member_id_fk = member_id_fk;
	}

}
