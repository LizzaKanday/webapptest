package com.proquest.interview.phonebook;

public class Person {
	public String name;
	public String phoneNumber;
	public String address;
	
	//constructor
	public Person(String name, String phoneNumber, String address) {
		if(name == null || "".equals(name) || phoneNumber == null || "".equals(phoneNumber) || address == null || "".equals(address)) {
			throw new IllegalArgumentException("Person details entered are not correct.");
		}
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
	
	//getter and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	//toString method
	public String toString() {
		return "Name: " + this.name + ", PhoneNumber: " + this.phoneNumber + ", Address: " + this.address;
	}
}
