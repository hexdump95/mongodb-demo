package com.mycompany.demo.dtos;

import java.io.Serializable;

public class AuthorDto implements Serializable {

	private String firstName;
	private String lastName;
	
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

	public AuthorDto() {
	}

	public AuthorDto(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "AuthorDto [firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
