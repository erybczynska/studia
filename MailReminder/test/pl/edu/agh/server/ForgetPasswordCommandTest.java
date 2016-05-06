package pl.edu.agh.server;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import pl.agh.edu.mailing.MailSender;
import pl.edu.agh.database.User;

@RunWith(PowerMockRunner.class)
public class ForgetPasswordCommandTest {

	private static final String USER_MAIL = "test@gmail.com";

	@Test
	public void testLoggedIn() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(true);
		Command command = ForgetPasswordCommand.newInstance(status, null, null);
		assertEquals(Responses.ALREADY_LOGGED_IN_503, command.execute());
	}

	@PrepareForTest({ User.class })
	@Test
	public void testNotLoggedInWithWrongLine() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		String wrongLine = "FORGET";

		mockStatic(User.class);

		Command command = ForgetPasswordCommand.newInstance(status, wrongLine, null);
		assertEquals(Responses.WRONG_MAIL_OR_PASSWORD_430, command.execute());
	}

	@PrepareForTest({ User.class })
	@Test
	public void testNotLoggedInWithCorrectLine() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		String correctLine = "FORGET " + USER_MAIL;

		mockStatic(User.class);
		User usr = mock(User.class);
		when(User.getUser(USER_MAIL)).thenReturn(usr);

		MailSender sender = mock(MailSender.class);

		Command command = ForgetPasswordCommand.newInstance(status, correctLine, sender);
		assertEquals(Responses.COMMAND_SUCCESSFUL_200, command.execute());
	}
	

}
