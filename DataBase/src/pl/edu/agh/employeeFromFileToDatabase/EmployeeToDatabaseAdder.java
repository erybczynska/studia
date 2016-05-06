package pl.edu.agh.employeeFromFileToDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import pl.agh.edu.h2.Employee;
import pl.agh.edu.h2.Salary;

/* Klasa dodająca pracowników do bazy danych */
public class EmployeeToDatabaseAdder {

	/*
	 * Metoda dodająca pracowników do bazy danych
	 * 
	 * @param employees tablica pracowników do dodania do bazy danych
	 * 
	 * @param dbUrl adres url do bazy danych
	 * 
	 * @param userName nazwa użytkownika przy logowaniu do bazy
	 * 
	 * @param password hasło użytkownika przy logowaniu do bazy
	 */
	public void addEmployeeToDateBase(Employee[] employees, String dbUrl, String userName, String password)
			throws Exception {
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection(dbUrl, userName, password);

		for (Employee newEmployee : employees) {
			insertEmployeeWithManySalary(conn, newEmployee);
		}
		conn.close();
	}

	private static void insertEmployeeWithManySalary(Connection dbConnection, Employee newEmployee) {

		PreparedStatement stmtEmployee = null;
		PreparedStatement stmtSalary = null;
		PreparedStatement selectLastId = null;

		try {
			stmtEmployee = dbConnection
					.prepareStatement("insert into pracownik (imie,nazwisko,data_urodzenia) " + "values(?,?,?)");
			stmtEmployee.setString(1, newEmployee.getName());
			stmtEmployee.setString(2, newEmployee.getSurname());
			stmtEmployee.setDate(3, newEmployee.getDateOfBirth());
			stmtEmployee.executeUpdate();

			selectLastId = dbConnection
					.prepareStatement("select id from pracownik where imie=? and nazwisko=? and data_urodzenia=?");
			selectLastId.setString(1, newEmployee.getName());
			selectLastId.setString(2, newEmployee.getSurname());
			selectLastId.setDate(3, newEmployee.getDateOfBirth());
			ResultSet result = selectLastId.executeQuery();
			result.last();
			int id_pracownika = result.getInt(1);

			stmtSalary = dbConnection
					.prepareStatement("insert into pensja (id_pracownika,kwota,od) " + "values(?,?,?)");
			stmtSalary.setInt(1, id_pracownika);
			Iterator<Salary> it = newEmployee.iterator();

			while (it.hasNext()) {
				Salary salary = it.next();
				stmtSalary.setDouble(2, salary.getAmount());
				stmtSalary.setDate(3, salary.getSince());
				stmtSalary.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmtEmployee != null && stmtSalary != null) {
				try {
					stmtEmployee.close();
					stmtSalary.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
