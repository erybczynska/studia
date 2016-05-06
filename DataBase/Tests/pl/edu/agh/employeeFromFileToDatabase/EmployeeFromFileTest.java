package pl.edu.agh.employeeFromFileToDatabase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.agh.edu.h2.Employee;
import pl.agh.edu.h2.Salary;

public class EmployeeFromFileTest {
	private static Employee[] newEmpoloyeesTest;
	private static File inputFile; 

	@BeforeClass
	public static void makeTestEmployees() {

		Employee firstNewEmployee = new Employee("Jan", "Kowalski", createDate(1988, 4, 12), null);
		Employee secondNewEmployee = new Employee("Alicja", "Nowak", createDate(1964, 8, 29), null);
		Employee thirdNewEmployee = new Employee("Karol", "Polak", createDate(1990, 1, 5), new ArrayList<>());

		firstNewEmployee.addSalary(new Salary(4000, createDate(2015, 6, 10)));
		firstNewEmployee.addSalary(new Salary(5000, createDate(2015, 8, 10)));

		secondNewEmployee.addSalary(new Salary(1000, createDate(2015, 4, 13)));

		newEmpoloyeesTest = new Employee[]{firstNewEmployee,secondNewEmployee,thirdNewEmployee};
	}
	
	@BeforeClass
	public static void makeTestFile() throws IOException { 
		inputFile = new File("existingInputTestFile.txt");
		inputFile.createNewFile();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
			writer.write("Jan Kowalski 12.4.1988 4000,10.6.2015/5000,10.8.2015" + System.lineSeparator());
			writer.write("Alicja Nowak 29.8.1964 1000,13.4.2015" + System.lineSeparator());
			writer.write("Karol Polak 5.1.1990" + System.lineSeparator());
		}
	}

	@Test
	public void createNewEmployeesArrayWithExistingFile() throws IOException, NotEnoughInfoAboutAnEmployeeException {
		EmployeeFromFile employeeFromFile = new EmployeeFromFile(inputFile.getAbsolutePath());
		Employee[] newEmployeesToCheck = employeeFromFile.getInfoFromFile();
		
		Assert.assertEquals(3, newEmployeesToCheck.length);
		Assert.assertArrayEquals(newEmpoloyeesTest, newEmployeesToCheck);	
	}
	
	@Test (expected = IOException.class)
	public void createNewEmployeesArrayWithNotExistingFile() throws IOException, NotEnoughInfoAboutAnEmployeeException {
		EmployeeFromFile employeeFromFile = new EmployeeFromFile("notExistingFile.txt");
		employeeFromFile.getInfoFromFile();
	}

	@Test(expected = NotEnoughInfoAboutAnEmployeeException.class)
	public void notEnoughInformationAboutNewEmployee() throws IOException, NotEnoughInfoAboutAnEmployeeException {
		inputFile = new File("existingInputTestFile.txt");
		inputFile.createNewFile();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
			writer.write("Jan" + System.lineSeparator());
		}
		
		EmployeeFromFile employeeFromFile = new EmployeeFromFile(inputFile.getAbsolutePath());
		employeeFromFile.getInfoFromFile();
	}
	
    private static Date createDate(int year, int month, int day) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.clear();
        calendar.set(year, month -1, day);
        Date date = new Date(calendar.getTime().getTime());
        return date;
    } 


}
