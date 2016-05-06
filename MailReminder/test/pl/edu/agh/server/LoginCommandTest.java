package pl.edu.agh.server;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import pl.agh.edu.password.PasswordController;
import pl.edu.agh.server.Command;
import pl.edu.agh.server.LoginCommand;
import pl.edu.agh.server.Responses;
import pl.edu.agh.server.Status;

public class LoginCommandTest {

	private static final String USER_MAIL = "test@gmail.com";
	private static final String USER_HASH = "hash";

	@Test
	public void testLoggedIn() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(true);
		Command command = LoginCommand.newInstance(status, null, null);
		assertEquals(Responses.ALREADY_LOGGED_IN_503, command.execute());
	}
	
	@Test
	public void testLoggedInWithWrongLine() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		String wrongLine = "LOGIN";
		Command command = LoginCommand.newInstance(status, wrongLine, null);
		assertEquals(Responses.WRONG_SYNTAX_540, command.execute());
	}
	
	@Test
	public void testLoggedInWithWrongLineOnlyMail() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		String wrongLine = "LOGIN " + USER_MAIL;
		Command command = LoginCommand.newInstance(status, wrongLine, null);
		assertEquals(Responses.WRONG_SYNTAX_540, command.execute());
	}
	
	@Test
	public void testLoggedInWithWrongData() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		String line = "LOGIN " + USER_MAIL + " " + USER_HASH;
		
		PasswordController controller = mock(PasswordController.class);
		when(controller.verifyData(USER_MAIL, USER_HASH)).thenReturn(false);
		
		Command command = LoginCommand.newInstance(status, line, controller);
		assertEquals(Responses.WRONG_MAIL_OR_PASSWORD_430, command.execute());
	}
	
	@Test
	public void testLoggedInWithCorrectData() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		String line = "LOGIN " + USER_MAIL + " " + USER_HASH;
		
		PasswordController controller = mock(PasswordController.class);
		when(controller.verifyData(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		
		Command command = LoginCommand.newInstance(status, line, controller);
		assertEquals(Responses.USER_LOGGED_IN_230, command.execute());
	}
	

}
