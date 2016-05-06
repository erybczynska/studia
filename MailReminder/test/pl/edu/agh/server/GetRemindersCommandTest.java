package pl.edu.agh.server;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import pl.edu.agh.database.Reminder;
import pl.edu.agh.database.ReminderController;

public class GetRemindersCommandTest {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	
	private static final String timeToSend1 = "2016-01-10 19:00:00";
	private static final String timeToSend2 = "2016-01-15 11:00:00";
	private static final String timeToSend3 = "2016-01-20 13:30:00";
	
	private static Date date1;
	private static Date date2;
	private static Date date3;
	
	static {
		try {
			date1 = FORMATTER.parse(timeToSend1);
			date2 = FORMATTER.parse(timeToSend2);
			date3 = FORMATTER.parse(timeToSend3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static final String USER_MAIL = "test@gmail.com";
	private static final Reminder firstReminder = new Reminder("testowy1@gmail.com", "Wyjazd", date1.getTime()); 
	private static final Reminder secondReminder = new Reminder("testowy@gmail.com", "Urodziny", date2.getTime());
	private static final Reminder thirdReminder = new Reminder("testowy@gmail.com", "Wizyta u lekarza", date3.getTime());

	private static List<Reminder> makeRemindersTestList() {
		List<Reminder> reminders = new ArrayList<>();
		reminders.add(firstReminder);
		reminders.add(secondReminder);
		reminders.add(thirdReminder);
		return reminders;
	}

	@Test
	public void testNotLoggedIn() {
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(false);
		Command command = GetRemindersCommand.newInstance(status, null, null);
		assertEquals(Responses.NOT_LOGGED_IN_530, command.execute());
	}

	@Test
	public void testLoggedInCorrectLine() {
		List<Reminder> reminderList = makeRemindersTestList();
		Status status = mock(Status.class);
		when(status.isLoggedIn()).thenReturn(true);
		String correctLine = "GETALL";
		ReminderController controller = mock(ReminderController.class);
		when(controller.collectUserReminders(USER_MAIL)).thenReturn(reminderList);
		Command command = GetRemindersCommand.newInstance(status, correctLine, controller);
		assertEquals(Responses.COMMAND_SUCCESSFUL_200, command.execute());
	}

}
