package com.vub.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.vub.utility.Views;


/** 
 * 
 * General Person object. Provides fields for general info about people (a User is a Person)
 * 
 * @author Nicolas + Sam
 *
 */
@Entity
@Table(name="PERSON")
public class Person {
	@Id
	@Column(name="PersonID")
	@GeneratedValue
	private int id;
	
	@NotBlank(message = "Cannot be empty")
	@Column(name="FirstName")
	@JsonView(Views.EntryFilter.class)
	private String firstName;
	
	@NotBlank(message = "Cannot be empty")
	@Column(name="LastName")
	@JsonView(Views.EntryFilter.class)
	private String lastName;
	
	@NotBlank(message = "Cannot be empty")
	@Email(message = "Not a real email adress")
	// TODO - Validate this differently, because it's conflicting with database updates
	//@ValidEmail(message = "Email already exist.")
	@Column(name="Email")
	private String email;
	
	@Column(name="Birthdate")
	private Date birthdate;
	
	/**
	 * @return Returns the first name of this person
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName Sets the new first name for this person
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return Returns the new last name of this person
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName Sets the new last name for this person
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return Returns the email address for this person
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email Sets the new email address for this person
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the birthdate for this person
	 */
	public Date getBirthdate() {
		return birthdate;
	}
	/**
	 * @param birthdate Set a new birthdate for this person
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	/**
	 * @return	Returns the ID of this person
	 */
	public int getId() {
		return id;
	}
	
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", birthdate=" + birthdate
				+ "]";
	}
	
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Person))return false;
	    Person otherPerson = (Person)other;
	    return this.id == otherPerson.getId();
	}
}
