package pl.agh.edu.registration;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import pl.agh.edu.before.DatabaseControllerTests;
import pl.edu.agh.database.Registry;
import pl.edu.agh.database.Reminder;
import pl.edu.agh.database.ReminderController;

@RunWith(PowerMockRunner.class)
public class ReminderControllerTest {
	private static final String USER_NAME = "sa";
	private static final String PASSWORD = "";
	private static final String DB_URL = "jdbc:h2:file:./dbtest";

	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);

	private static final String DELETE_QUERY = "DELETE FROM reminders";
	private static final String INSERT_QUERY = "INSERT INTO reminders (mail, content, timeToSend) " + "values(?,?,?)";
	private static final String SELECT_QUERY = "SELECT * FROM reminders";

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

	private static final Reminder firstReminder = new Reminder("testowy1@gmail.com", "Wyjazd", date1.getTime());
	private static final Reminder secondReminder = new Reminder("testowy@gmail.com", "Urodziny", date2.getTime());
	private static final Reminder thirdReminder = new Reminder("testowy@gmail.com", "Wizyta u lekarza",
			date3.getTime());

	private ReminderController reminderController = ReminderController.getInstance();

	@Before
	public void clearDataBaseBeforeTesting() throws SQLException {
		DatabaseControllerTests.createTablesIfNotExists(DB_URL);
		clearDataBase();
	}

	@After
	public void clearDataBaseAfterTesting() throws SQLException {
		clearDataBase();
	}

	private static void clearDataBase() throws SQLException {
		try (Connection dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD)) {
			PreparedStatement stmtClearTableReminders = dbConnection.prepareStatement(DELETE_QUERY);
			stmtClearTableReminders.execute();
		}
	}

	private static Reminder[] makeTestReminders() {
		return new Reminder[] { firstReminder, secondReminder, thirdReminder };
	}

	private static void makeTestDataBase() throws SQLException {
		try (Connection dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD)) {
			Reminder[] testReminders = makeTestReminders();

			PreparedStatement stmtAddToRemindersTable = dbConnection.prepareStatement(INSERT_QUERY);
			executeWithNewReminderData(stmtAddToRemindersTable, testReminders[0]);
			executeWithNewReminderData(stmtAddToRemindersTable, testReminders[1]);
			executeWithNewReminderData(stmtAddToRemindersTable, testReminders[2]);
		}
	}

	private static void executeWithNewReminderData(PreparedStatement stmt, Reminder reminderData) throws SQLException {
		stmt.setString(1, reminderData.getMail());
		stmt.setString(2, reminderData.getContent());
		stmt.setTimestamp(3, new Timestamp(reminderData.getDateToSend()));
		stmt.executeUpdate();
	}

	@PrepareForTest({ Registry.class })
	@Test
	public void removeReminderTest() throws SQLException, ClassNotFoundException {		
		Class.forName("org.h2.Driver");
		PowerMockito.mockStatic(Registry.class, new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			}
		});
		
		makeTestDataBase();

		try (Connection dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD)) {
			reminderController.removeReminder(firstReminder);
			PreparedStatement stmt = dbConnection.prepareStatement(SELECT_QUERY);
			ResultSet rsReminders = stmt.executeQuery();

			List<Reminder> listOfReminders = new ArrayList<>();
			while (rsReminders.next()) {
				listOfReminders.add(new Reminder(rsReminders.getString(1), rsReminders.getString(2),
						rsReminders.getTimestamp(3).getTime()));
			}
			
			Assert.assertEquals(secondReminder, listOfReminders.get(0));
			Assert.assertEquals(thirdReminder, listOfReminders.get(1));
		}
	}

	@Test
	public void removeAllRemindersTest() throws SQLException {
		makeTestDataBase();
		try (Connection dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD)) {
			reminderController.removeAllReminders(secondReminder.getMail());
			PreparedStatement stmt = dbConnection.prepareStatement(SELECT_QUERY);
			ResultSet rsReminders = stmt.executeQuery();

			List<Reminder> listOfReminders = new ArrayList<>();
			while (rsReminders.next()) {
				listOfReminders.add(new Reminder(rsReminders.getString(1), rsReminders.getString(2),
						rsReminders.getTimestamp(3).getTime()));
			}
			Assert.assertEquals(firstReminder, listOfReminders.get(0));
		}
	}

	@PrepareForTest({ Registry.class })
	@Test
	public void generateChronologicalListRemindersTest() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		PowerMockito.mockStatic(Registry.class, new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			}
		});
		makeTestDataBase();
		List<Reminder> sortedReminders = reminderController.generateChronologicalListReminders();
		System.out.println(sortedReminders.get(0));
		System.out.println(sortedReminders.get(1));
		System.out.println(sortedReminders.get(2));

		Assert.assertEquals(firstReminder, sortedReminders.get(0));
		Assert.assertEquals(secondReminder, sortedReminders.get(1));
		Assert.assertEquals(thirdReminder, sortedReminders.get(2));
	}

	@PrepareForTest({ Registry.class })
	@Test
	public void collectUserRemindersTest() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		PowerMockito.mockStatic(Registry.class, new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			}
		});
	
		makeTestDataBase();
		List<Reminder> collectedReminders = reminderController.collectUserReminders(secondReminder.getMail());
		System.out.println(collectedReminders.size());
		
		Assert.assertEquals(secondReminder, collectedReminders.get(0));
		Assert.assertEquals(thirdReminder, collectedReminders.get(1));
	}

}
