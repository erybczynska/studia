package pl.edu.agh.crawler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class DefaultDownloadQueueTest {
	private DownloadQueueURL queue;
	
	@Before
	public void init() {
		queue = new DownloadQueueURL();
	}
	
	@Test
	public void testIsEmptyWhileEmpty() {
		assertTrue(queue.isEmpty());
	}
	
	@Test
	public void testIsEmptyWhileNotEmpty() throws MalformedURLException {
		queue.addPage(new File("test").toURI().toURL());
		assertFalse(queue.isEmpty());
	}
	
	@Test
	public void addPageTest() throws MalformedURLException  {
		queue.addPage(new File("test1").toURI().toURL());
		assertEquals(1, queue.size());
		queue.addPage(new File("test2").toURI().toURL());
		assertEquals(2, queue.size());
		queue.addPage(new File("test3").toURI().toURL());
		assertEquals(3, queue.size());
	}
	
	@Test
	public void testGetNextPageWhileEmpty() {
		assertNull(queue.getNextPage());
	}
	
	@Test
	public void testGetNextPageWhile1InQueue() throws MalformedURLException {
		URL url = new File("test").toURI().toURL();
		queue.addPage(url);
		assertEquals(url, queue.getNextPage());
	}
	
	@Test
	public void testGetNextPageWhile3InQueue() throws MalformedURLException {
		URL url1 = new File("test1").toURI().toURL();
		URL url2 = new File("test2").toURI().toURL();
		URL url3 = new File("test1").toURI().toURL();
		queue.addPage(url1);
		queue.addPage(url2);
		queue.addPage(url3);
		assertEquals(url1, queue.getNextPage());
		assertEquals(url2, queue.getNextPage());
		assertEquals(url3, queue.getNextPage());
	}
	

}
