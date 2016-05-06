package pl.edu.agh.employeeFromDatabaseToFile;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class EmployeeFromDatabasePickerTest {
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

	private static void makeTestDataBase() throws Exception {
		Connection dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);

		PreparedStatement selectLastId = dbConnection
				.prepareStatement("select id from pracownik where imie=? and nazwisko=? and data_urodzenia=?");

		PreparedStatement stmtAddToEmployeeTable = dbConnection
				.prepareStatement("insert into pracownik (imie,nazwisko,data_urodzenia) " + "values(?,?,?)");
		executeWithNewEmployeeData(stmtAddToEmployeeTable, "Jan", "Nowak", createDate(1974, 3, 22));
		executeWithNewEmployeeData(stmtAddToEmployeeTable, "Marta", "Durda", createDate(1956, 8, 1));
		executeWithNewEmployeeData(stmtAddToEmployeeTable, "Kamil", "Halada", createDate(1992, 11, 16));

		PreparedStatement stmtAddToSalaryTable = dbConnection
				.prepareStatement("insert into pensja (id_pracownika, kwota, od) " + "values(?,?,?)");
		executeWithNewSalaryData(stmtAddToSalaryTable,
				getIdOfLastEmployee(selectLastId, "Jan", "Nowak", createDate(1974, 3, 22)), 5500,
				createDate(2015, 10, 15));
		executeWithNewSalaryData(stmtAddToSalaryTable,
				getIdOfLastEmployee(selectLastId, "Jan", "Nowak", createDate(1974, 3, 22)), 4200,
				createDate(2015, 10, 15));
		executeWithNewSalaryData(stmtAddToSalaryTable,
				getIdOfLastEmployee(selectLastId, "Marta", "Durda", createDate(1956, 8, 1)), 1100,
				createDate(2015, 10, 15));
	}

	private static void executeWithNewEmployeeData(PreparedStatement stmt, String name, String surname,
			Date dateOfBirth) throws SQLException {
		stmt.setString(1, name);
		stmt.setString(2, surname);
		stmt.setDate(3, dateOfBirth);
		stmt.executeUpdate();
	}

	private static void executeWithNewSalaryData(PreparedStatement stmt, int id, double salary, Date since)
			throws SQLException {
		stmt.setInt(1, id);
		stmt.setDouble(2, salary);
		stmt.setDate(3, since);
		stmt.executeUpdate();
	}

	private static int getIdOfLastEmployee(PreparedStatement stmt, String name, String surname, Date dateOfBirth)
			throws Exception {
		stmt.setString(1, name);
		stmt.setString(2, surname);
		stmt.setDate(3, dateOfBirth);
		ResultSet result = stmt.executeQuery();
		result.last();
		return result.getInt(1);
	}

	private List<Employee> makeTestEmployees() {
		List<Employee> listOfEmployeesFromDataBase = new ArrayList<>();
		Employee firstNewEmployee = new Employee("Jan", "Nowak", createDate(1974, 3, 22), null);
		Employee secondNewEmployee = new Employee("Marta", "Durda", createDate(1956, 8, 1), null);
		Employee thirdNewEmployee = new Employee("Kamil", "Halada", createDate(1992, 11, 16), new ArrayList<>());

		firstNewEmployee.addSalary(new Salary(5500, createDate(2015, 10, 15)));
		firstNewEmployee.addSalary(new Salary(4200, createDate(2015, 10, 15)));

		secondNewEmployee.addSalary(new Salary(1100, createDate(2015, 10, 15)));

		listOfEmployeesFromDataBase.add(firstNewEmployee);
		listOfEmployeesFromDataBase.add(secondNewEmployee);
		listOfEmployeesFromDataBase.add(thirdNewEmployee);

		return listOfEmployeesFromDataBase;
	}

	@Test
	public void test() throws Exception {
		List<Employee> listOfEmployeesTest = makeTestEmployees();
		makeTestDataBase();

		EmployeeFromDatabasePicker picker = new EmployeeFromDatabasePicker();
		List<Employee> listOfEmployeesToCheck = picker.getEmployeesFromDateBase(DB_URL, USER_NAME, PASSWORD);

		Assert.assertEquals(listOfEmployeesTest, listOfEmployeesToCheck);
	}

	private static Date createDate(int year, int month, int day) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.clear();
		calendar.set(year, month - 1, day);
		Date date = new Date(calendar.getTime().getTime());
		return date;
	}
}
