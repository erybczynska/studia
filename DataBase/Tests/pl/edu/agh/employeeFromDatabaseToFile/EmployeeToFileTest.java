package pl.edu.agh.employeeFromDatabaseToFile;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import pl.agh.edu.h2.Employee;
import pl.agh.edu.h2.Salary;

public class EmployeeToFileTest {
	private static List<Employee> empoloyeesFromDatabaseTest;
	private static File outputFile;

	@BeforeClass
	public static void makeTestEmployees() {
		empoloyeesFromDatabaseTest = new ArrayList<>();
		Employee firstNewEmployee = new Employee("Jan", "Nowak", createDate(1974, 3, 22), null);
		Employee secondNewEmployee = new Employee("Marta", "Durda", createDate(1956, 8, 1), null);
		Employee thirdNewEmployee = new Employee("Kamil", "Halada", createDate(1992, 11, 16), new ArrayList<>());

		firstNewEmployee.addSalary(new Salary(5500, createDate(2015, 10, 15)));
		firstNewEmployee.addSalary(new Salary(4200, createDate(2015, 11, 15)));

		secondNewEmployee.addSalary(new Salary(1100, createDate(2015, 10, 15)));

		empoloyeesFromDatabaseTest.add(firstNewEmployee);
		empoloyeesFromDatabaseTest.add(secondNewEmployee);
		empoloyeesFromDatabaseTest.add(thirdNewEmployee);
	}

	@BeforeClass
	public static void makeTestFile() throws IOException {
		outputFile = new File("existingOutputTestFile.txt");
		outputFile.createNewFile();
	}

	@Test
	public void createFileWithEmployeesFromListTest() throws IOException {
		EmployeeToFile employeeToFile = new EmployeeToFile(outputFile.getAbsolutePath());
		employeeToFile.writeToFile(empoloyeesFromDatabaseTest);

		
		try (Scanner reader = new Scanner(new FileInputStream(outputFile))) {
				assertEquals("Jan Nowak 1974-03-22 5500.0,2015-10-15/4200.0,2015-11-15/", reader.nextLine());
				assertEquals("Marta Durda 1956-08-01 1100.0,2015-10-15/", reader.nextLine());
				assertEquals("Kamil Halada 1992-11-16", reader.nextLine());
		}
	}

	private static Date createDate(int year, int month, int day) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.clear();
		calendar.set(year, month - 1, day);
		Date date = new Date(calendar.getTime().getTime());
		return date;
	}

}
