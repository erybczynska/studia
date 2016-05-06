package pl.edu.agh.equation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Test;

public class EquationFromFileTest {
	private File inputFile;
	private File outputFile;

	
	private void makeTestFile(String contentOfTestFile) throws IOException {
		inputFile = new File("existingInputTestFile.txt");
		inputFile.createNewFile();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
			writer.write(contentOfTestFile);
		}
	}
	
	@After 
	public void deleteTestFile() {
		if (inputFile != null)
			inputFile.delete();
		if (outputFile != null)
			outputFile.delete();
	}
	
	private void makeTestOutputFile() throws IOException {
		outputFile = new File("existingOutputTestFile.txt"); 
		outputFile.createNewFile();
	}

	@Test(expected = IOException.class)
	public void notExistingInputFileTest() throws IOException {
		
		new EquationFromFile("notExistingInputTestFile.txt", "notExistingOutputTestFile.txt").writeRootsToFile();
	}

	@Test
	public void rightDataForEquationTest() throws IOException {
		makeTestFile("1 2 1");
		makeTestOutputFile();
		EquationFromFile testEquation = new EquationFromFile(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());
		testEquation.writeRootsToFile();

		try (Scanner reader = new Scanner(new FileInputStream(outputFile))) {
			String[] result = reader.nextLine().split(" ");
			if (result.length > 6)
				assertEquals("-1.0", result[7]);
		}
	}

	@Test
	public void wrongFormatOfDataForEquationTest() throws IOException {
		makeTestFile("fg fdf as");
		makeTestOutputFile();
		EquationFromFile testEquation = new EquationFromFile(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());
	testEquation.writeRootsToFile();

		try (Scanner reader = new Scanner(new FileInputStream(outputFile))) {
			assertFalse(reader.hasNext());
		}
	}

	@Test
	public void notEnoughValuesInInputFileTest() throws IOException {
		makeTestFile("4 2");
		makeTestOutputFile();
		EquationFromFile testEquation = new EquationFromFile(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());
		testEquation.writeRootsToFile();

		try (Scanner reader = new Scanner(new FileInputStream(outputFile))) {
			assertFalse(reader.hasNext());
		}
	}

}
