package pl.edu.agh.crawler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class VisitedPagesSetTest {
	private VisitedPagesSet pages;
	
	@Before
	public void init() {
		pages = new VisitedPagesSet();
	}

	@Test
	public void testIfPageNotVisited() throws MalformedURLException {
		assertFalse(pages.pageAlreadyVisited(new File("test").toURI().toURL()));
	}
	
	@Test
	public void testIfPageVisited() throws MalformedURLException {
		URL url = new File("test").toURI().toURL();
		pages.addVisitedPage(url);
		assertTrue(pages.pageAlreadyVisited(url));
	}

}
