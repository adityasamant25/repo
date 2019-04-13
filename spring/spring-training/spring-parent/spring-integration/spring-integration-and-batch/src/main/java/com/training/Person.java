package com.training;

public class Person {

	private String first, last, email;

	public Person() {

	}

	public Person(String first, String last, String email) {
		super();
		this.first = first;
		this.last = last;
		this.email = email;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Person [first=" + first + ", last=" + last + ", email=" + email + "]";
	}

}
