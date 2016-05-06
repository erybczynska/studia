package pl.edu.agh.employeeFromFileToDatabase;

public class Main {

	public static void main(String[] args) {
		EmployeeFromFile employees = new EmployeeFromFile("testAddEmployee.txt");
		EmployeeToDatabaseAdder adder = new EmployeeToDatabaseAdder();
		try {
			adder.addEmployeeToDateBase(employees.getInfoFromFile(), "jdbc:h2:tcp://localhost/~/test", "sa", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
