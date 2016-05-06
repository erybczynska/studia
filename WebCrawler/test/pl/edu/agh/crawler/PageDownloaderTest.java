package pl.edu.agh.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PageDownloaderTest {
	private static File testPage;
	private static String pageString;
	
	@BeforeClass
	public static void createHTMLFile() throws IOException {
		pageString = "<!DOCTYPE html>" + "<html>" + "<head>"
				+ "<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" />"
				+ "<title>Tytul</title>" + "</head>" + "<body>" + "<div><p>Text</p></div>" + "</body>"
				+ "</html>";

		testPage = new File("pageDownloaderTestPage.html");
		testPage.createNewFile();
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(testPage))) {
			writer.write(pageString);
		}
	}
	
	@AfterClass
	public static void removeFile() {
		testPage.delete();
	}
	

	@Test (expected = DownloaderException.class)
	public void testWrongURLString() throws DownloaderException {
		WWWPageDownloaderURL downloader = new WWWPageDownloaderURL();
		downloader.downloadPage("blabla");
	}
	
	@Test (expected = DownloaderException.class)
	public void testURLToWrongFile() throws DownloaderException {
		WWWPageDownloaderURL downloader = new WWWPageDownloaderURL();
		downloader.downloadPage("file:/notExistingFile.html");
	}
	
	@Test
	public void testDownloadingPage() throws MalformedURLException, DownloaderException {
		WWWPageDownloaderURL downloader = new WWWPageDownloaderURL();
		String downloadedPage = downloader.downloadPage(testPage.toURI().toURL().toString());
		assertEquals(pageString, downloadedPage);
	}

}
