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
@Document(collection="memberClaims")
public class MemberClaims {
	
	@Transient
	public static final String SEQUENCE_NAME="sequence";
	
	@Id
	private int id;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String dateOfAdmission;
	private String dateOfDischarge;
	private String providerName;
	private double billAmount;
	private int memberId;
	
	@JsonIgnore
	@OneToMany(targetEntity=Dependent.class)
	private Set<Dependent> dependentList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Set<Dependent> getDependentList() {
		return dependentList;
	}
	public void setDependentList(Set<Dependent> dependentList) {
		this.dependentList = dependentList;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDateOfAdmission() {
		return dateOfAdmission;
	}
	public void setDateOfAdmission(String dateOfAdmission) {
		this.dateOfAdmission = dateOfAdmission;
	}
	public String getDateOfDischarge() {
		return dateOfDischarge;
	}
	public void setDateOfDischarge(String dateOfDischarge) {
		this.dateOfDischarge = dateOfDischarge;
	}
	public double getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}
	
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	public MemberClaims(int id, String firstName, String lastName, String dateOfBirth, String dateOfAdmission,
			String dateOfDischarge, String providerName, double billAmount, int memberId, Set<Dependent> dependentList) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.dateOfAdmission = dateOfAdmission;
		this.dateOfDischarge = dateOfDischarge;
		this.providerName = providerName;
		this.billAmount = billAmount;
		this.memberId = memberId;
		this.dependentList = dependentList;
	}
	@Override
	public String toString() {
		return "MemberClaims [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + ", dateOfAdmission=" + dateOfAdmission + ", dateOfDischarge="
				+ dateOfDischarge + ", providerName=" + providerName + ", billAmount=" + billAmount + ", memberId="
				+ memberId + ", dependentList=" + dependentList + "]";
	}
	public MemberClaims() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
