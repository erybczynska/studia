package pl.edu.agh.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import pl.agh.edu.mailing.MailingController;
import pl.edu.agh.database.ReminderController;

public class DeleteAllRemindersCommandTest {


	private static final String USER_MAIL = "test@gmail.com";

	@Test
	public void testNotLoggedIn() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		Command command = DeleteAllRemindersCommand.newInstance(status, null, null, null);
		assertEquals(Responses.NOT_LOGGED_IN_530, command.execute());
	}
	
	@Test
	public void testLoggedInCorrectLine() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(true);
		String correctLine = "DELREM";
		ReminderController controller = mock(ReminderController.class);
		when(controller.removeAllReminders(USER_MAIL)).thenReturn(true);
		MailingController mailingController = mock(MailingController.class);
		Command command = DeleteAllRemindersCommand.newInstance(status, correctLine, controller, mailingController);
		assertEquals(Responses.COMMAND_SUCCESSFUL_200, command.execute());
	}
	
}
