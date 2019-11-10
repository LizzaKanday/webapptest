package com.proquest.interview.phonebook;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	public List<Person> people = new ArrayList<Person>();
	
	@Override
	public void addPerson(Person newPerson) {
		try {
			if(newPerson == null) {
				throw new IllegalArgumentException("Person passed to save is not correct.");
			}
			boolean isInsertOk = DatabaseUtil.addPersonToPhoneBook(newPerson);
			if(isInsertOk) {
				System.out.println("Person with name " + newPerson.name + " added in DB.");
				
				people.add(newPerson);
				System.out.println("Person with name " + newPerson.name + " added in Local list.");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Person> findPerson(String firstName, String lastName) {
		try {
			return DatabaseUtil.findPersonInPhoneBook(firstName, lastName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Person> findPersonInLocalList(String firstName, String lastName) {
		if(firstName == null || "".equals(firstName) || lastName == null || "".equals(lastName)) {
			throw new IllegalArgumentException("Person passed to save is not correct.");
		}
		
		List<Person> list = new ArrayList<Person>();
		String nameToSearch = firstName + " " + lastName;
		if(people != null && !people.isEmpty()) {
			for(Person entry : people) {
				if(nameToSearch.equals(entry.name)) {
					list.add(entry);
				}
			}
		}
		
		return list;
	}
	
	@Override
	public List<Person> findPersonByLike(String searchStr, String fieldName) {
		try {
			return DatabaseUtil.findPersonByLikeInPhoneBook(searchStr, fieldName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Person> findPersonWithFieldInPhoneBook(String searchStr, String fieldName) {
		try {
			return DatabaseUtil.findPersonWithFieldInPhoneBook(searchStr, fieldName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Person> getPhoneBookList() {
		try {
			return DatabaseUtil.getAllPhoneBookEntries();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void printEnteries(List<Person> list, String message) {
		List<Person> objToPrint = list != null ? list : people;
		
		if(objToPrint != null && !objToPrint.isEmpty()) {
			System.out.println("\nPrinting " + message + "... Start");
			for(Person entry : objToPrint) {
				System.out.println(entry);
			}
			System.out.println("Printing " + message + "... End");
		}else {
			System.out.println("No persons found for " + message);
		}
	}
	
	public static void main(String[] args) {
		try {
			DatabaseUtil.dropDB();
			DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database
			
			//phonebook instance
			List<Person> personFoundByCriteria = new ArrayList<Person>();
			PhoneBook phoneBook = new PhoneBookImpl();
			
			//create first person
			//add first person to DB
			phoneBook.addPerson(new Person("John Smith", "(248) 123-4567", "1234 Sand Hill Dr, Royal Oak, MI"));

			//create second person
			//add second person to DB
			phoneBook.addPerson(new Person("Cynthia Smith", "(824) 128-8758", "875 Main St, Ann Arbor, MI"));
			
			//Get PhoneBook entries and Print
			phoneBook.printEnteries(phoneBook.getPhoneBookList(), "PhoneBook entries");
			
			//Print people list
			phoneBook.printEnteries(null, "People list entries");
			
			//Find Cynthia Smith in DB and Print
			String firstName = "Cynthia";
			String lastName = "Smith";
			personFoundByCriteria = phoneBook.findPerson(firstName, lastName);
			phoneBook.printEnteries(personFoundByCriteria, "'search name-surname result'");
			
			//Find Cynthia Smith in loacl list and Print
			firstName = "Dave";
			lastName = "Williams";
			personFoundByCriteria = phoneBook.findPerson(firstName, lastName);
			phoneBook.printEnteries(personFoundByCriteria, "'search name-surname in local list result'");
			
			//Find Cynthia Smith by like and Print
			String fieldToSearch = "name";
			personFoundByCriteria = phoneBook.findPersonByLike("Sm", fieldToSearch);
			phoneBook.printEnteries(personFoundByCriteria, "search like " + fieldToSearch);
			
			//Find by address and Print
			fieldToSearch = "phoneNumber";
			personFoundByCriteria = phoneBook.findPersonWithFieldInPhoneBook("(248) 123-4567", fieldToSearch);
			phoneBook.printEnteries(personFoundByCriteria, "'search with field' " + fieldToSearch);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/*** REQUIREMENTS ***/
		/* TODO: create person objects and put them in the PhoneBook and database
		 * John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 * Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		*/ 
		// TODO: print the phone book out to System.out
		// TODO: find Cynthia Smith and print out just her entry
		// TODO: insert the new person objects into the database
	}
	
}
