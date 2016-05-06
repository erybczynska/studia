package pl.agh.edu.registration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.database.Reminder;

public class ReminderTest {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	
	private static final String timeToSend = "2016-01-10 19:00:00";
	
	private static Date date;
	
	static {
		try {
			date = FORMATTER.parse(timeToSend);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	private static final Reminder testReminder = new Reminder("testowy@gmail.com", "Wizyta",
			date.getTime());
	
	@Before 
	public void setSingleReminder() {
		testReminder.setContent("Wizyta");
		testReminder.setDateToSend(date.getTime());
	}

	@Test
	public void getMailTest() {
		Assert.assertEquals("testowy@gmail.com", testReminder.getMail());
	}

	@Test
	public void getContentTest() {
		Assert.assertEquals("Wizyta", testReminder.getContent());
	}

	@Test
	public void setContentTest() {
		testReminder.setContent("Imieniny");
		Assert.assertEquals("Imieniny", testReminder.getContent());
	}
	
	@Test
	public void getDateTest() {
		Assert.assertEquals(date.getTime(), testReminder.getDateToSend());
	}
	
	@Test
	public void setDateTest() {
		testReminder.setDateToSend(date.getTime());
		Assert.assertEquals(date.getTime(), testReminder.getDateToSend());
	}
	
	@Test 
	public void singleReminderToStringTest() {
		Assert.assertEquals("testowy@gmail.com_" + date.getTime() + "_Wizyta", testReminder.toString());
	}

}
