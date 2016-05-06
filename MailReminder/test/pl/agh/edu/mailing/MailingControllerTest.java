package pl.agh.edu.mailing;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import pl.edu.agh.database.Reminder;
import pl.edu.agh.database.ReminderController;

public class MailingControllerTest {
	private static final Reminder firstReminder = new Reminder("testowy1@gmail.com", "Wyjazd",
			(new Date().getTime() + 100));
	private static final Reminder secondReminder = new Reminder("testowy2@gmail.com", "Urodziny",
			(new Date().getTime() + 200));

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws MessagingException, IOException, InterruptedException {
		List<Reminder> firstList = new ArrayList<>();
		firstList.add(firstReminder);
		firstList.add(secondReminder);
		List<Reminder> secondList = new ArrayList<>();
		secondList.add(secondReminder);

		ReminderController reminderController = mock(ReminderController.class);
		when(reminderController.generateChronologicalListReminders()).thenReturn(firstList, secondList);
		MailSender sender = mock(MailSender.class);
		
		
		final List<String> outputs = new ArrayList<>();
		outputs.add("FAILURE");
		outputs.add("FAILURE");
		final String success = "SUCCESS";

		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return outputs.set(0, success);
			}
		}).when(sender).sendReminderMail(firstReminder.getMail(),
				firstReminder.getContent());
		
		doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return outputs.set(1, success);
			}
		}).when(sender).sendReminderMail(secondReminder.getMail(),
				secondReminder.getContent());

		MailingController mailingController = MailingController.getInstance();
		mailingController.setReminderToSend();
		mailingController.setReminderToSend();
		TimeUnit.MILLISECONDS.sleep(500);

		assertEquals(success, outputs.get(0));
		assertEquals(success, outputs.get(1));
	}

}
