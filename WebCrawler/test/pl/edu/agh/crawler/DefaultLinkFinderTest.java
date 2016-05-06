package pl.edu.agh.crawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.Test;

public class DefaultLinkFinderTest {
	private static String stringURL1 = "http://www.student.agh.edu.pl/";
	private static String stringURL2 = "http://www.agh.edu.pl/";
	private static URL url1;
	private static URL url2;

	static {
		try {
			url2 = new URL(stringURL2);
			url1 = new URL(stringURL1);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String page = "<!DOCTYPE html>" + "<a href=\"" + stringURL1 + "\"></a>"
			+ ".pageHeaderWrapper.toogle.menuButton{margin:3px 28px 19px 16px}" + "<a href=\"" + stringURL2 + "\"></a>"
			+ ".plransition:background none;-moz-transition:background no"
			+ "ne;-o-transition:background none;transition:background none}#pag"
			+ "eHeader #searchForm #searchQuery::-webkit-input-placeholder{color"
			+ ":#7a7c80}#pageHeader #searchForm #sear";

	@Test
	public void testGetUrlFromString() throws MalformedURLException {
		DefaultLinkFinder finder = new DefaultLinkFinder();
		List<URL> newURLs = finder.getUrlsFromString(page);

		assertEquals(2, newURLs.size());
		assertTrue(newURLs.contains(url1));
		assertTrue(newURLs.contains(url2));
	}

}
