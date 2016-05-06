package pl.edu.agh.equation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import pl.edu.agh.logLibrary.DefaultLogger;
import pl.edu.agh.logLibrary.Logger;
import pl.edu.agh.logLibrary.LoggerToFile;
import pl.edu.agh.logLibrary.StandardOutputLogger;

public class EquationFromFile {
	private File input;
	private File output;
	private Logger fileLogger = null;
	private Logger stdOutLogger = new DefaultLogger(new StandardOutputLogger());

	public EquationFromFile(String input, String output) {
		this.input = new File(input);
		this.output = new File(output);
	}

	public void writeRootsToFile() throws IOException {
		if (!input.exists())
			throw new IOException("Plik nie istnieje!");

		try (BufferedReader br = new BufferedReader(new FileReader(input))) {
			writeRootsLinesToFile(br);
		} catch (IOException e) {
			throw e;
		}
	}

	private void writeRootsLinesToFile(BufferedReader br) throws IOException {
		String ln = null;
		int lineNumber = 1;
		while ((ln = br.readLine()) != null) {
			double[] values;
			try {
				values = getValues(ln);
			} catch (WrongNumberOfValuesException | WrongFormatOfValueException e) {
				stdOutLogger.error("Błąd danych wejściowych w linii: " + lineNumber);
				continue;
			} finally {
				lineNumber++;
			}
			Complex[] roots = calculateRoots(values);
			writeToFile(roots);
		} 
	}

	private Complex[] calculateRoots(double[] vals) {
		return new Equation(vals[0], vals[1], vals[2]).solveEquation();
	}


	private double[] getValues(String line) throws WrongNumberOfValuesException, WrongFormatOfValueException {
		String[] strValues = line.split(" ");
		if (strValues.length != 3)
			throw new WrongNumberOfValuesException();

		double[] values = new double[3];
		for (int i = 0; i < 3; i++) {
			try {
				values[i] = Double.parseDouble(strValues[i]);
			} catch (NumberFormatException e) {
				throw new WrongFormatOfValueException();
			}
		}
		return values;
	}

	private void writeToFile(Complex[] roots) throws IOException {
		if (fileLogger == null)
			fileLogger = new DefaultLogger(new LoggerToFile(output.getAbsolutePath()));
		StringBuilder str = new StringBuilder();
		for (Complex root : roots) {
			str.append(root + " ");
		}
		fileLogger.info(str.toString());
	}

}
