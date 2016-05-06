package pl.edu.agh.vector;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Test;

public class VectorsSumFromFileTest {
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
		new VectorsSumFromFile("notExistingInputTestFile.txt", "notExistingOutputTestFile.txt").writeRootsToFile();
	}

	@Test
	public void vecotorsWithRightDataTest() throws IOException {
		makeTestFile("1 2 1,4 5 7");
		makeTestOutputFile();
		VectorsSumFromFile testVectors = new VectorsSumFromFile(inputFile.getAbsolutePath(),
				outputFile.getAbsolutePath());
		testVectors.writeRootsToFile();

		try (Scanner reader = new Scanner(new FileInputStream(outputFile))) {
			assertTrue(reader.nextLine().contains("[5.0, 7.0, 8.0]"));
		}
	}
	
	@Test
	public void vecotorsWithWrongDataTest() throws IOException {
		makeTestFile("1 2 1,5 7");
		makeTestOutputFile();
		VectorsSumFromFile testVectors = new VectorsSumFromFile(inputFile.getAbsolutePath(),
				outputFile.getAbsolutePath());
		testVectors.writeRootsToFile();
		
		try (Scanner reader = new Scanner(new FileInputStream(outputFile))) {
			assertFalse(reader.hasNext());
		}
	}

}
