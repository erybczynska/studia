package pl.edu.agh.employeeFromFileToDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import pl.agh.edu.h2.Employee;
import pl.agh.edu.h2.Salary;

/* Klasa odczytująca pracowników z pliku */
public class EmployeeFromFile {
	private File input;

	/*
	 * Konstruktor parametrowy, ustawiający plik wejściowy
	 * 
	 * @param input nazwa pliku wejściowego
	 */
	public EmployeeFromFile(String input) {
		this.input = new File(input);
	}

	/*
	 * Metoda pobierjąca informacje o pracownikach z pliku wejściowego, tworząca
	 * z nich obiekty Employee i zapisująca je do tablicy pracowników
	 * 
	 * @return tablica z pracownikami z pliku
	 */
	public Employee[] getInfoFromFile() throws IOException, NotEnoughInfoAboutAnEmployeeException {
		if (!input.exists())
			throw new IOException("Plik nie istnieje!");

		List<Employee> employeesList;
		try (BufferedReader br = new BufferedReader(new FileReader(input))) {
			employeesList = getInfoAboutNewEmployees(br);
		} catch (IOException e) {
			throw e;
		}
		return employeesList.toArray(new Employee[0]);
	}

	private List<Employee> getInfoAboutNewEmployees(BufferedReader br)
			throws NotEnoughInfoAboutAnEmployeeException, IOException {
		List<Employee> employeesToAdd = new ArrayList<>();
		String ln = null;
		while ((ln = br.readLine()) != null) {
			employeesToAdd.add(getInfoAboutAnEmployee(ln));
		}
		return employeesToAdd;
	}

	private Employee getInfoAboutAnEmployee(String ln) throws NotEnoughInfoAboutAnEmployeeException {

		String[] infoAboutAnEmployee = ln.split(" ");
		if (infoAboutAnEmployee.length < 3)
			throw new NotEnoughInfoAboutAnEmployeeException();

		String name = infoAboutAnEmployee[0];
		String surname = infoAboutAnEmployee[1];
		String[] dateOfBirthString = infoAboutAnEmployee[2].split("\\.");
		Date dateOfBirth = createDate(Integer.parseInt(dateOfBirthString[2]), Integer.parseInt(dateOfBirthString[1]),
				Integer.parseInt(dateOfBirthString[0]));

		Employee newEmployee = new Employee();
		if (infoAboutAnEmployee.length == 4) {
			String[] salaryString = infoAboutAnEmployee[3].split("/");
			for (String salary : salaryString) {
				String[] amoutAndDateString = salary.split(",");
				Double amount = Double.parseDouble(amoutAndDateString[0]);
				String[] dateSinceString = amoutAndDateString[1].split("\\.");
				Date dateSince = createDate(Integer.parseInt(dateSinceString[2]), Integer.parseInt(dateSinceString[1]),
						Integer.parseInt(dateSinceString[0]));
				Salary newSalaryToAdd = new Salary(amount, dateSince);
				newEmployee.addSalary(newSalaryToAdd);
			}
		}
		newEmployee.setName(name);
		newEmployee.setSurname(surname);
		newEmployee.setDateOfBirth(dateOfBirth);
		return newEmployee;
	}

	private static Date createDate(int year, int month, int day) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.clear();
		calendar.set(year, month - 1, day);
		Date date = new Date(calendar.getTime().getTime());
		return date;
	}

}
