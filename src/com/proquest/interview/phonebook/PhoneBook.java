package com.proquest.interview.phonebook;

import java.util.List;

public interface PhoneBook {
	public List<Person> getPhoneBookList();
	public List<Person> findPerson(String firstName, String lastName);
	public List<Person> findPersonInLocalList(String firstName, String lastName);
	public List<Person> findPersonByLike(String searchByName, String fieldName);
	public List<Person> findPersonWithFieldInPhoneBook(String searchByName, String fieldName);
	public void addPerson(Person newPerson);
	public void printEnteries(List<Person> list, String message);
}
