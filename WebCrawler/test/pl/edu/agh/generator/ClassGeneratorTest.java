package pl.edu.agh.generator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClassGeneratorTest {
	private static File testFile;

	@BeforeClass
	public static void makeFile() throws IOException {
		testFile = new File("ExistingInputTestFile.java");
		testFile.createNewFile();
	}

	@AfterClass
	public static void removeFile() {
		testFile.delete();
	}

	@Test
	public void testClassGenerator() throws FileNotFoundException, IOException {
		String[] packages = { "pl", "edu", "agh" };
		ClassGenerator.generateCode(testFile, packages);
		try (Scanner reader = new Scanner(new FileInputStream(testFile))) {
			assertEquals("package pl.edu.agh;", reader.nextLine());
			assertEquals("/**", reader.nextLine());
			assertEquals("* To jest wygenerowana automatycznie klasa ExistingInputTestFile", reader.nextLine());
			assertEquals("*", reader.nextLine());
			assertEquals("*/", reader.nextLine());
			assertEquals("public class ExistingInputTestFile {", reader.nextLine());
			assertEquals("\t/**", reader.nextLine());
			assertEquals("\t* Domyslny konstruktor klasy ExistingInputTestFile", reader.nextLine());
			assertEquals("\t*/", reader.nextLine());
			assertEquals("\tpublic ExistingInputTestFile() {", reader.nextLine());
			assertEquals("\t\t// metoda wygenerowana - nalezy uzupelnic implementacje", reader.nextLine());
			assertEquals("\t}", reader.nextLine());
			assertEquals("\t/**", reader.nextLine());
			assertEquals("\t* Metoda main automatycznie wygenerowana", reader.nextLine());
			assertEquals("\t* @param args tablica argumentow przekazanych do programu", reader.nextLine());
			assertEquals("\t*/", reader.nextLine());
			assertEquals("\tpublic static void main(String[] args) {", reader.nextLine());
			assertEquals(
					"\t\tSystem.out.println(\"Metoda jeszcze nie jest zaimplementowana - tylko wygenerowany wzorzec.\");",
					reader.nextLine());
			assertEquals("\t}", reader.nextLine());
			assertEquals("}", reader.nextLine());
		}
	}

}
