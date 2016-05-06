package pl.edu.agh.server;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pl.edu.agh.server.Status;

public class StatusTest {

	private static final Status STATUS = new Status();
	private static final String MAIL = "test@gmail.com";

	@Test
	public void isLoggedInTest() {
		assertEquals(false, STATUS.isLoggedIn());
	}

	@Test
	public void setLoggedInTest() {
		STATUS.setLoggedIn();
		assertEquals(true, STATUS.isLoggedIn());
	}

	@Test
	public void setMailTest() {
		STATUS.setMail(MAIL);
		assertEquals(MAIL, STATUS.getMail());
	}

	@Test
	public void getMailTest() {
		assertEquals(MAIL, STATUS.getMail());
	}

	@Test
	public void setLogoutTest() {
		STATUS.setLoggedOut();
		assertEquals(null, STATUS.getMail());
		assertEquals(false, STATUS.isLoggedIn());
	}
}
