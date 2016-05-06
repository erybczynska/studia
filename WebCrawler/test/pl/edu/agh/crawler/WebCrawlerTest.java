package pl.edu.agh.crawler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.edu.agh.logLibrary.LoggerToFile;

public class WebCrawlerTest {
	private static File rootFile;
	private static File secondFile;
	private static File thirdFile;
	
	private static String loggerFilePath = "WebCrawlerTestOutput.test";
	
	
	@BeforeClass
	public static void createFiles() throws IOException {
		rootFile = new File("WebCrawlerTestRootFile.test");
		secondFile = new File("WebCrawlerTestSecondFile.test");
		thirdFile = new File("WebCrawlerTestThirdFile.test");
		rootFile.createNewFile();
		secondFile.createNewFile();
		thirdFile.createNewFile();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(rootFile))) {
			writer.write("<div class=\"container\"><div class=\"header\">"
						+ "<a href=\"" + secondFile.toURI().toURL() + "\"></a>"
						+ "</p></div>" + " <!-- .validator --></div> <!-- .content -->");
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(secondFile))) {
			writer.write("<div style=\"clear: both;\"></div> "
						+ "<a href=\"" + thirdFile.toURI().toURL() + "\"></a>"
						+ "<p>Fusce lobortis sed erat a fringilla. ");
		}
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(thirdFile))) {
			writer.write("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" />"
						+ "<a href=\"" + rootFile.toURI().toURL() + "\"></a>"
						+ "<a href=\"" + secondFile.toURI().toURL() + "\"></a>"
						+ "<p>Fusce lobortis sed erat a fringilla. ");
		}
	}
	
	
	
	@AfterClass
	public static void removeFiles() {
		rootFile.delete();
		secondFile.delete();
		thirdFile.delete();
		new File(loggerFilePath).delete();
	}

	@Test
	public void testWebCrawler() throws IOException {
		URL rootUrl = rootFile.toURI().toURL();
		WebCrawlerUrl crawler = new WebCrawlerUrl(rootUrl, new LoggerToFile(loggerFilePath));
		crawler.run();
		
		try (Scanner reader = new Scanner(new FileInputStream(new File(loggerFilePath)))) {
			assertTrue(Pattern.matches(".*"+rootFile.toURI().toURL().toString(), reader.nextLine()));
			reader.nextLine();
			assertTrue(Pattern.matches(".*"+secondFile.toURI().toURL().toString(), reader.nextLine()));
			reader.nextLine();
			assertTrue(Pattern.matches(".*"+thirdFile.toURI().toURL().toString(), reader.nextLine()));
			reader.nextLine();
			assertFalse(reader.hasNextLine());
		}
	}
	
	@Test
	public void testSetLoggerType() throws IOException {
		URL rootUrl = rootFile.toURI().toURL();
		WebCrawlerUrl crawler = new WebCrawlerUrl(rootUrl);
		crawler.setLoggerType(new LoggerToFile(loggerFilePath));
		crawler.run();
		
		try (Scanner reader = new Scanner(new FileInputStream(new File(loggerFilePath)))) {
			assertTrue(Pattern.matches(".*"+rootFile.toURI().toURL().toString(), reader.nextLine()));
			reader.nextLine();
			assertTrue(Pattern.matches(".*"+secondFile.toURI().toURL().toString(), reader.nextLine()));
			reader.nextLine();
			assertTrue(Pattern.matches(".*"+thirdFile.toURI().toURL().toString(), reader.nextLine()));
			reader.nextLine();
			assertFalse(reader.hasNextLine());
		}
	}

}
