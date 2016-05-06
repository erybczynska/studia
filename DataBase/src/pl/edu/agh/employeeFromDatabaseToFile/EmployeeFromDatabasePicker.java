package pl.edu.agh.employeeFromDatabaseToFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import pl.agh.edu.h2.Employee;
import pl.agh.edu.h2.Salary;

/* Klasa do pobierania pracowników z bazy danych */
public class EmployeeFromDatabasePicker {

	/*
	 * Klasa pobierająca pracowników z bazy danych i dodającach ich do listy.
	 * Otwiera i zamyka połączenie z bazą danych.
	 *
	 * @param dbUrl adres url do bazy danych
	 * 
	 * @param userName nazwa użytkownika przy logowaniu do bazy
	 * 
	 * @param password hasło użytkownika przy logowaniu do bazy
	 * 
	 * @return lista pracowników znajdujących się w bazie danych
	 */
	public List<Employee> getEmployeesFromDateBase(String dbUrl, String userName, String password)
			throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");
		Connection conn = DriverManager.getConnection(dbUrl, userName, password);
		java.util.List<Employee> employees = getEmployeeList(conn);
		conn.close();
		return employees;
	}

	private java.util.List<Employee> getEmployeeList(Connection dbConnection) {
		java.util.List<Employee> employeeList = new LinkedList<Employee>();

		PreparedStatement stmtEmployee = null;
		PreparedStatement stmtSalary = null;

		try {
			stmtEmployee = dbConnection
					.prepareStatement("SELECT PRACOWNIK.ID,IMIE,NAZWISKO,DATA_URODZENIA FROM PRACOWNIK");
			ResultSet rs = stmtEmployee.executeQuery();
			while (rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt("ID"));
				e.setName(rs.getString("IMIE"));
				e.setSurname(rs.getString("NAZWISKO"));
				e.setDateOfBirth(rs.getDate("DATA_URODZENIA"));

				stmtSalary = dbConnection.prepareStatement("SELECT KWOTA,OD FROM PENSJA WHERE ID_PRACOWNIKA=?");
				stmtSalary.setInt(1, e.getId());
				ResultSet rsPensja = stmtSalary.executeQuery();
				while (rsPensja.next()) {
					Salary newSalary = new Salary(rsPensja.getDouble("KWOTA"), rsPensja.getDate("OD"));
					e.addSalary(newSalary);
				}
				employeeList.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmtEmployee != null) {
				try {
					stmtEmployee.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return employeeList;
	}

}
