package pl.edu.agh.employeeFromDatabaseToFile;

import java.util.List;

import pl.agh.edu.h2.Employee;

public class Main {

	public static void main(String[] args) {
		EmployeeFromDatabasePicker picker = new EmployeeFromDatabasePicker();
		try {
			List<Employee> employees = picker.getEmployeesFromDateBase("jdbc:h2:tcp://localhost/~/test", "sa", "");
			EmployeeToFile writer = new EmployeeToFile("dbOutput.txt");
			writer.writeToFile(employees);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
