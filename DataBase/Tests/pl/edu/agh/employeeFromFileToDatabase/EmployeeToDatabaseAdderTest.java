package pl.edu.agh.employeeFromFileToDatabase;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.agh.edu.h2.Employee;
import pl.agh.edu.h2.Salary;

public class EmployeeToDatabaseAdderTest {
	
	private static final String USER_NAME = "sa";
	private static final String PASSWORD = "";
	private static final String DB_URL = "jdbc:h2:tcp://localhost/~/TestingDB";

	@BeforeClass 
	public static void clearDataBaseBeforeTesting() throws Exception {
		clearDataBase(); 
	}

	@AfterClass 
	public static void clearDataBaseAfterTesting() throws Exception {
		clearDataBase(); 
	}
	
	private static void clearDataBase() throws Exception {
		Connection dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
		
		PreparedStatement stmtClearTableEmployee = dbConnection.prepareStatement("DELETE FROM PRACOWNIK");
		PreparedStatement stmtClearTableSalary = dbConnection.prepareStatement("DELETE FROM PENSJA");
		
		stmtClearTableEmployee.execute();
		stmtClearTableSalary.execute();
		
		dbConnection.close();
	}
	
	private Employee[] makeTestEmployees() {		

        Employee firstNewEmployee = new Employee("Jan", "Kowalski", createDate(1988, 4, 12), null);
        Employee secondNewEmployee = new Employee("Alicja", "Nowak", createDate(1964, 8, 29), null);
        Employee thirdNewEmployee= new Employee("Karol", "Polak", createDate(1990, 1, 5), new ArrayList<>());
        
        firstNewEmployee.addSalary(new Salary(4000, createDate(2015, 6, 10)));
        firstNewEmployee.addSalary(new Salary(5000, createDate(2015, 8, 10)));
        
        secondNewEmployee.addSalary(new Salary(1000, createDate(2015, 4, 13)));
        
		return new Employee[]{firstNewEmployee, secondNewEmployee, thirdNewEmployee}; 
	}

	@Test
	public void test() throws Exception {
		Employee[] newEmployees = makeTestEmployees();

		EmployeeToDatabaseAdder adder = new EmployeeToDatabaseAdder();
		adder.addEmployeeToDateBase(newEmployees, DB_URL, USER_NAME, PASSWORD);

		Connection dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);

		PreparedStatement stmtEmployee = dbConnection.prepareStatement("SELECT * FROM PRACOWNIK");
		ResultSet rsEmployee = stmtEmployee.executeQuery();
		
		int[] idOfEmployees = new int[3];
		String[] namesOfEmployees = new String[3]; 
		String[] surnamesOfEmployees = new String[3]; 
		Date[] datesOfBirthOfEmployees = new Date[3];
		
		int i = 0; 
		
		while (rsEmployee.next()) {
			idOfEmployees[i] = rsEmployee.getInt(1);
			namesOfEmployees[i] = rsEmployee.getString(2);
			surnamesOfEmployees[i] = rsEmployee.getString(3);
			datesOfBirthOfEmployees[i] = rsEmployee.getDate(4);
			i++;
		}
		
		PreparedStatement stmtSalary = 	dbConnection.prepareStatement("SELECT ID_PRACOWNIKA, KWOTA, OD FROM PENSJA");
		ResultSet rsSalary = stmtSalary.executeQuery();
		
		List<Salary> listOfSalary = new ArrayList<>();
		while (rsSalary.next()) {
			Salary newSalary = new Salary(rsSalary.getInt(1), rsSalary.getDouble(2), rsSalary.getDate(3));
			listOfSalary.add(newSalary);
		}
		
		Assert.assertEquals("Jan", namesOfEmployees[0]);
		Assert.assertEquals("Alicja", namesOfEmployees[1]);
		Assert.assertEquals("Karol", namesOfEmployees[2]);
		
		Assert.assertEquals("Kowalski", surnamesOfEmployees[0]);
		Assert.assertEquals("Nowak", surnamesOfEmployees[1]);
		Assert.assertEquals("Polak", surnamesOfEmployees[2]);	

		Assert.assertEquals(createDate(1988, 4, 12), datesOfBirthOfEmployees[0]);
		Assert.assertEquals(createDate(1964, 8, 29), datesOfBirthOfEmployees[1]);
		Assert.assertEquals(createDate(1990, 1, 5), datesOfBirthOfEmployees[2]);	
		
		Assert.assertEquals(3,listOfSalary.size());
		
		Assert.assertEquals(listOfSalary.get(0), new Salary(1, 4000, createDate(2015, 6, 10)));
		Assert.assertEquals(listOfSalary.get(1), new Salary(1, 5000, createDate(2015, 8, 10)));
		Assert.assertEquals(listOfSalary.get(2), new Salary(2, 1000, createDate(2015, 4, 13)));
	}
	
	
    private static Date createDate(int year, int month, int day) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.clear();
        calendar.set(year, month - 1, day);
        Date date = new Date(calendar.getTime().getTime());
        return date;
    } 

}
