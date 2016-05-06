package pl.edu.agh.logLibrary;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LoggerToFileTest {
	private static File outputFile; 
	
	@BeforeClass 
	public static void makeOutputFile() throws IOException {
		outputFile = new File("testFile.txt"); 
		if(!outputFile.exists())
			outputFile.createNewFile();
	}
	
	@AfterClass
	public static void deleteTestFile() {
		if (outputFile != null)
			outputFile.delete();
	}
	

	@Test
	public void ifnoMessageToFileTest() throws FileNotFoundException {
		LoggerToFile testLogger = new LoggerToFile(outputFile.getAbsolutePath());
		testLogger.log(LogLevel.INFO, "Info");
		
		try (Scanner reader = new Scanner(new FileInputStream(outputFile))) {
			assertTrue(reader.nextLine().contains("Info"));
		}
	}
	
	@Test
	public void warningMessageToFileTest() throws FileNotFoundException {
		LoggerToFile testLogger = new LoggerToFile(outputFile.getAbsolutePath());
		testLogger.log(LogLevel.WARNING, "Warning");
		
		try (Scanner reader = new Scanner(new FileInputStream(outputFile))) {
			assertTrue(reader.nextLine().contains("Warning"));
		}
	}

	@Test
	public void errorMessageToFileTest() throws FileNotFoundException {
		LoggerToFile testLogger = new LoggerToFile(outputFile.getAbsolutePath());
		testLogger.log(LogLevel.ERROR, "Error");
		
		try (Scanner reader = new Scanner(new FileInputStream(outputFile))) {
			assertTrue(reader.nextLine().contains("Error"));
		}
	}


}
