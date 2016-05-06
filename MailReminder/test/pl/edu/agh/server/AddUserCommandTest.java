package pl.edu.agh.server;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import pl.agh.edu.mailing.MailSender;
import pl.edu.agh.database.User;

@RunWith(PowerMockRunner.class)
public class AddUserCommandTest {

	private static final String USER_MAIL = "test@gmail.com";

	@Test
	public void testLoggedIn() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(true);
		Command command = AddUserCommand.newInstance(status, null, null);
		assertEquals(Responses.ALREADY_LOGGED_IN_503, command.execute());
	}

	@Test
	public void testNotLoggedInWithNoDataLine() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		String wrongLine = "ADDUSR";
		Command command = AddUserCommand.newInstance(status, wrongLine, null);
		assertEquals(Responses.WRONG_SYNTAX_540, command.execute());
	}


	@PrepareForTest({ User.class })
	@Test
	public void testLoggedInWithTakenEmail() throws Exception {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		String correctLine = "ADDUSR " + USER_MAIL;

		mockStatic(User.class);
		User usr = mock(User.class);
		when(User.getUser(USER_MAIL)).thenReturn(usr);
		
//		MailSender sender = mock(MailSender.class);
//

		Command command = AddUserCommand.newInstance(status, correctLine, null);
		assertEquals(Responses.USER_EXIST_505, command.execute());
	}
	
	@PrepareForTest({ User.class })
	@Test
	public void testLoggedInWithoutTakenEmail() throws Exception {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		String correctLine = "ADDUSR " + USER_MAIL;
		mockStatic(User.class);
		suppress(method(User.class, "create"));
		
		MailSender sender = mock(MailSender.class);
		
		Command command = AddUserCommand.newInstance(status, correctLine, sender);
		assertEquals(Responses.COMMAND_SUCCESSFUL_200, command.execute());
	}
	
}
