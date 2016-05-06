package pl.edu.agh.server;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Test;

import pl.edu.agh.server.Command;
import pl.edu.agh.server.LogoutCommand;
import pl.edu.agh.server.Responses;
import pl.edu.agh.server.Status;

public class LogoutCommandTest {

	@Test
	public void notLoggedInTest() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		Command command = LogoutCommand.newInstance(status);
		assertEquals(Responses.NOT_LOGGED_IN_530, command.execute());
	}

	@Test
	public void loggedInTest() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(true);
		Command command = LogoutCommand.newInstance(status);
		assertEquals(Responses.LOGOUT_SUCCESSFUL_240, command.execute());
	}

}
