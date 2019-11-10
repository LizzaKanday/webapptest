package com.proquest.interview.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.proquest.interview.phonebook.Person;

/**
 * This class is just a utility class, you should not have to change anything here
 * @author rconklin
 */
public class DatabaseUtil {
	public static void initDB() {
		try {
			Connection cn = getConnection();
			Statement stmt = cn.createStatement();
			stmt.execute("CREATE TABLE PHONEBOOK (NAME varchar(255), PHONENUMBER varchar(255), ADDRESS varchar(255))");
			stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES('Chris Johnson','(321) 231-7876', '452 Freeman Drive, Algonac, MI')");
			stmt.execute("INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES('Dave Williams','(231) 502-1236', '285 Huron St, Port Austin, MI')");
			cn.commit();
			cn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbcDriver");
		return DriverManager.getConnection("jdbc:hsqldb:mem", "sa", "");
	}
	
	/*new methos*/
	//DROP table
	public static void dropDB() {
		try {
			Connection cn = getConnection();
			Statement stmt = cn.createStatement();
			String truncateQuery = "DROP TABLE PHONEBOOK IF EXISTS";
			stmt.execute(truncateQuery);
			cn.commit();
			cn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	//Resultset handler
	private static ResultSetHandler<List<Person>> handler() {
		return new ResultSetHandler<List<Person>>() {
			public List<Person> handle(ResultSet rs) throws SQLException {
				List<Person> list = new ArrayList<Person>();

				while(rs.next()) {
					Person personObj = new Person(rs.getString("name"), 
													rs.getString("phoneNumber"), 
													rs.getString("address"));
					list.add(personObj);
				}
				return list;
			}
		};
	}
	
	//all query runner
	private static List<Person> allQueriesRunner(String strQuery, Object... criteria) throws ClassNotFoundException, SQLException {
		Connection cn = null;
		
		try {
			cn = getConnection();
			
			QueryRunner qRun = new QueryRunner();
			
			List<Person> results = qRun.query(cn, strQuery, handler(), criteria);
			
			return results;
		}catch (SQLException e) {
			e.printStackTrace();
			
			return new ArrayList<Person>();
		}finally {
			DbUtils.closeQuietly(cn);
		}
		
	}
	
	//getPhoneBookList method
	public static List<Person> getAllPhoneBookEntries() throws ClassNotFoundException, SQLException {
		return allQueriesRunner("SELECT * FROM PHONEBOOK ORDER BY name", new Object[]{});
	}
	
	//addPerson to Phonebook method
	public static boolean addPersonToPhoneBook(Person newPerson) throws ClassNotFoundException, SQLException {
		Connection cn = getConnection();
		try {
			String addStatement = "INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES(? , ?, ?)";
			
			PreparedStatement prepStat = cn.prepareStatement(addStatement);
			prepStat.setString(1, newPerson.name);
			prepStat.setString(2, newPerson.phoneNumber);
			prepStat.setString(3, newPerson.address);
			int result = prepStat.executeUpdate();
			
			return result == 1 ? true : false;
		}catch (SQLException e) {
			System.err.format("State: %s\n%s", e.getSQLState(), e.getMessage());
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			cn.commit();
			cn.close();
		}
		return false;
	}
	
	//findPerson in Phonebook method
	public static List<Person> findPersonInPhoneBook(String firstName, String lastName) throws ClassNotFoundException, SQLException {
		String searchStr = firstName + " " + lastName;
		return allQueriesRunner("SELECT * FROM PHONEBOOK WHERE name=? ORDER BY name", searchStr);
	}
	
	//findPerson with dynamicfield in Phonebook method
	public static List<Person> findPersonWithFieldInPhoneBook(String search, String field) throws ClassNotFoundException, SQLException {
		return allQueriesRunner("SELECT * FROM PHONEBOOK WHERE " +  field + " = ? ORDER BY name", search);
	}
	
	//findPerson by like in Phonebook method
	public static List<Person> findPersonByLikeInPhoneBook(String search, String field) throws ClassNotFoundException, SQLException {
		String searchStr = "%" + search + "%";
		return allQueriesRunner("SELECT * FROM PHONEBOOK WHERE " +  field + " LIKE ? ORDER BY name", searchStr);
	}
	
}
