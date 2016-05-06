package pl.edu.agh.server;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import pl.agh.edu.password.PasswordController;
import pl.edu.agh.database.User;

@RunWith(PowerMockRunner.class)
public class ChangePasswordCommandTest {
	private static final String CORRECT_PASS = "pass";
	private static final String WRONG_PASS = "wrong";
	private static final String NEW_PASS = "new_pass";
	private static final String USER_MAIL = "test@gmail.com";

	@Test
	public void testNotLoggedIn() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		Command command = ChangePasswordCommand.newInstance(status, null, null);
		assertEquals(Responses.NOT_LOGGED_IN_530, command.execute());
	}

	@Test
	public void testLoggedInWrongLine() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(true);
		String wrongLine = "CHPASS mail@domain.com";
		PasswordController controller = mock(PasswordController.class);
		when(controller.verifyData(USER_MAIL, "")).thenReturn(false);
		Command command = ChangePasswordCommand.newInstance(status, wrongLine, controller);
		assertEquals(Responses.WRONG_MAIL_OR_PASSWORD_430, command.execute());
	}

	@Test
	public void testLoggedInWrongPassword() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(true);
		String wrongLine = "CHPASS " + WRONG_PASS + " " + NEW_PASS;
		PasswordController controller = mock(PasswordController.class);
		when(controller.verifyData(USER_MAIL, WRONG_PASS)).thenReturn(false);
		Command command = ChangePasswordCommand.newInstance(status, wrongLine, controller);
		assertEquals(Responses.WRONG_MAIL_OR_PASSWORD_430, command.execute());
	}

	@PrepareForTest({ User.class })
	@Test
	public void testLoggedInRightPassword() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(true);
		String rightLine = "CHPASS " + CORRECT_PASS + " " + NEW_PASS;

		PasswordController controller = mock(PasswordController.class);
		Mockito.when(controller.verifyData(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

		mockStatic(User.class);
		User usr = mock(User.class);
		when(User.getUser(Mockito.anyString())).thenReturn(usr);

		Command command = ChangePasswordCommand.newInstance(status, rightLine, controller);
		assertEquals(Responses.COMMAND_SUCCESSFUL_200, command.execute());
	}

}
