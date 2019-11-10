package com.proquest.interview.phonebook;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImplTest {
	private static PhoneBook phoneBook;
	
	@BeforeClass
	public static void createInstance() {
		DatabaseUtil.dropDB();
		DatabaseUtil.initDB();
		
		phoneBook = new PhoneBookImpl();
		
		if(phoneBook != null) {
			//Create Person and add to DataBase
			phoneBook.addPerson(new Person("Gabriela Rossi", "(895) 595-256", "656 Dane Road, Royal Oak, MI"));
		}
	}
	
	@Test
	public void addPerson() {
		if(phoneBook != null) {
			//Create Person and add to DataBase
			phoneBook.addPerson(new Person("Chris Williams", "(895) 595-652", "1236 Dane Road, Royal Drive, MI"));
			
			//Get PhoneBook entries and Print
			phoneBook.printEnteries(phoneBook.getPhoneBookList(), "PhoneBook entries");
			
			//Print people list
			phoneBook.printEnteries(null, "People list entries");
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addEmptyObject() {
		phoneBook.addPerson(null);
	}
	
	@Test
	public void findPersonInDb() {
		List<Person> result = phoneBook.findPerson("Chris", "Johnson");  
		assertNotNull(result);
		
		phoneBook.printEnteries(result, "'search findPersonInDb result'");
	}
	
	@Test
	public void findPersonInLocal() {
		List<Person> result = phoneBook.findPersonInLocalList("Gabriela", "Rossi");  
		assertNotNull(result);
		
		phoneBook.printEnteries(result, "'search findPersonInLocal result'");
	}
	
	@Test
	public void findPersonInDbByLikeName() {
		List<Person> result = phoneBook.findPersonByLike("Chr", "name");  
		assertNotNull(result);
		
		phoneBook.printEnteries(result, "'search findPersonInDbByLikeName result'");
	}
	
	@Test
	public void findPersonInDbByLikePhoneNumber() {
		List<Person> result = phoneBook.findPersonByLike("6", "phoneNumber");  
		assertNotNull(result);
		
		phoneBook.printEnteries(result, "'search findPersonInDbByLikePhoneNumber result'");
	}
	
	@Test
	public void findPersonInDbByLikeAddress() {
		List<Person> result = phoneBook.findPersonByLike("Drive", "address");  
		assertNotNull(result);
		
		phoneBook.printEnteries(result, "'search findPersonInDbByLikeAddress result'");
	}
	
	@Test
	public void findPersonInDWithFieldPhoneNumber() {
		List<Person> result = phoneBook.findPersonByLike("(321) 231-7876", "phoneNumber");  
		assertNotNull(result);
		
		phoneBook.printEnteries(result, "'search findPersonInDWithFieldPhoneNumber result'");
	}
	
	@Test
	public void findPersonInDbWithFieldAddress() {
		List<Person> result = phoneBook.findPersonByLike("285 Huron St, Port Austin, MI", "address");  
		assertNotNull(result);
		
		phoneBook.printEnteries(result, "'search findPersonInDbWithFieldAddress result'");
	}
	
	@Test
	public void notExsitingPersonInDb() {
		List<Person> result = phoneBook.findPerson("Chris", "Rossi");  
		assertNotNull(result);
		
		phoneBook.printEnteries(result, "'search notExsitingPersonInDb DB result'");
	}
	
	@Test
	public void notExsitingPersonInLocal() {
		List<Person> result = phoneBook.findPersonInLocalList("Gabriela", "Johnson");  
		assertNotNull(result);
		
		phoneBook.printEnteries(result, "'search notExsitingPersonInLocal local list result'");
	}
	
	@Test
	public void nullCriteriaPersonInDb() {
		List<Person> result = phoneBook.findPerson(null, null);  
		assertNotNull(result);
		
		phoneBook.printEnteries(result, "'search nullCriteriaPersonInDb DB result'");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullCriteriaPersonInLocal() {
		phoneBook.findPersonInLocalList(null, null);  
	}
	
	@Test
	public void nullFirsttNameCriteriaPersonInDb() {
		List<Person> result = phoneBook.findPerson(null, "Johnson");  
		assertNotNull(result);
		
		phoneBook.printEnteries(result, "'search nullFirsttNameCriteriaPersonInDb DB result'");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullFirstNameCriteriaPersonInLocal() {
		phoneBook.findPersonInLocalList(null, "Williams");  
	}
	
	@Test
	public void nullLastNameCriteriaPersonInDb() {
		List<Person> result = phoneBook.findPerson("Chris", null);  
		assertNotNull(result);
		
		phoneBook.printEnteries(result, "'search nullLastNameCriteriaPersonInDb DB result'");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void nullLastNameCriteriaPersonInLocal() {
		phoneBook.findPersonInLocalList("Gabriela", null);  
	}
}
