package com.zac.batch.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

public class PersonDto {

	@NotNull
	@Range(min = 1, max = 10)
	private String lastName;
	@NotNull
	@Range(min = 1, max = 10)
	private String firstName;

	public PersonDto() {
	}

	public PersonDto(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "firstName: " + firstName + ", lastName: " + lastName;
	}

}