package pl.edu.agh.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import pl.edu.agh.database.Reminder;
import pl.edu.agh.database.ReminderController;

public class AddReminderCommandTest {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	private static String timeToSend = "2016-01-01 15:30:00";
	private static Date date;
	
	static {
		try {
			date = FORMATTER.parse(timeToSend);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	private static final String CONTENT = "Przypomnienie";
	private static final Reminder REMINDER = new Reminder("test@gmail.com", CONTENT, date.getTime());
	
	@Test
	public void testNotLoggedIn() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		ReminderController controller = mock(ReminderController.class);
		String line = "";
		Command command = AddReminderCommand.newInstance(status, line, controller);
		assertEquals(Responses.NOT_LOGGED_IN_530, command.execute());
	}

	@Test
	public void testLoggedInWrongLine() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(true);
		String wrongLine = "ADDREM mail@domain.com";
		ReminderController controller = mock(ReminderController.class);
		Command command = AddReminderCommand.newInstance(status, wrongLine, controller);
		assertEquals(Responses.WRONG_SYNTAX_540, command.execute());
	}
	
	@Test
	public void testLoggedInCorrectLine() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(true);
		String rightLine = "ADDREM " + date.getTime() + " " + CONTENT;
		ReminderController controller = mock(ReminderController.class);

		when(controller.addReminder(REMINDER)).thenReturn(true);
		Command command = AddReminderCommand.newInstance(status, rightLine, controller);
		assertEquals(Responses.COMMAND_SUCCESSFUL_200, command.execute());
	}
	
}
