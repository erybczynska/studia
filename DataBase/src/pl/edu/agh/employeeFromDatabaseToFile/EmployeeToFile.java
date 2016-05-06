package pl.edu.agh.employeeFromDatabaseToFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import pl.agh.edu.h2.Employee;
import pl.agh.edu.h2.Salary;

/* Klasa zapisująca listę pracowników do pliku */
public class EmployeeToFile {
	private File outputFile;

	/*
	 * Konstruktor paramtrowy tworzący plik wyjściowych, w którym zostanie
	 * zapisana lista pracowników
	 * 
	 * @param ścieżka do pliku do którego zostanie zapisana lista pracowników
	 */
	public EmployeeToFile(String outputPath) throws IOException {
		outputFile = new File(outputPath);
		if (!outputFile.exists())
			outputFile.createNewFile();
	}

	/*
	 * Metoda zapisująca pracowników z listy do pliku
	 * 
	 * @param employeesFromDatabase lista pracowników
	 */
	public void writeToFile(List<Employee> employeesFromDatabase) throws FileNotFoundException {
		try (PrintWriter writer = new PrintWriter(outputFile)) {
			for (Employee employeeToWrite : employeesFromDatabase) {
				Iterator<Salary> it = employeeToWrite.iterator();
				if (!it.hasNext())
					writer.print(employeeToWrite.getName() + " " + employeeToWrite.getSurname() + " "
							+ employeeToWrite.getDateOfBirth());
				else
					writer.print(employeeToWrite.getName() + " " + employeeToWrite.getSurname() + " "
							+ employeeToWrite.getDateOfBirth() + " ");
				while (it.hasNext()) {
					writer.print(it.next() + "/");
				}
				writer.println();
				writer.flush();
			}
		}
	}

}