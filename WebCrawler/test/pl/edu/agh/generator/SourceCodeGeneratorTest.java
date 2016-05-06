package pl.edu.agh.generator;

import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SourceCodeGeneratorTest {
	private static File inputFile;

	@BeforeClass
	public static void makeFile() throws IOException {
		inputFile = new File("existingInputTestFile.test");
		inputFile.createNewFile();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
			writer.write("pl.edu.agh.Testowa");
		}
	}

	@AfterClass
	public static void removeFile() {
		inputFile.delete();
		String sep = File.separator;
		inputFile = new File("pl" + sep + "edu" + sep + "agh" + sep + "Testowa.java");
		System.out.println(inputFile.getAbsolutePath());
		for (int i = 0; i < 4; ++i) {
			inputFile.delete();
			inputFile = inputFile.getParentFile();
		}
	}

	@Test(expected = IOException.class)
	public void tesNotExistingFile() throws IOException {
		new SourceCodeGenerator("notExistingFile.test", ".");
	}

	@Test
	public void testGenerateFile() throws IOException {
		new SourceCodeGenerator(inputFile.getAbsolutePath(), ".").generate();
		File file = new File("pl");
		assertTrue(file.exists() && file.isDirectory());
		file = new File(file, "edu");
		assertTrue(file.exists() && file.isDirectory());
		file = new File(file, "agh");
		assertTrue(file.exists() && file.isDirectory());
		file = new File(file, "Testowa.java");
		assertTrue(file.exists() && file.isFile());
	}
}
