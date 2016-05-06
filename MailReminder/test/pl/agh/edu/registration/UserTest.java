package pl.agh.edu.registration;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
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
import pl.edu.agh.database.User;
@RunWith(PowerMockRunner.class)
public class UserTest {
	private static final String USER_NAME = "sa";
	private static final String PASSWORD = "";
	private static final String DB_URL = "jdbc:h2:file:~/dbtest";

	private static final String DELETE_QUERY = "DELETE FROM users";
	private static final String SELECT_QUERY = "SELECT * FROM users";
	private static final String SELECT_USER_QUERY = "SELECT * FROM users where mail=?";

	private static final User firstUser = new User("testowy1@gmail.com", "pass1");
	private static final User secondUser = new User("testowy2@gmail.com", "pass2");
	private static final User thirdUser = new User("testowy3@gmail.com", "pass3");

	@Before
	public void clearDataBaseBeforeTesting() throws Exception {
		DatabaseControllerTests.createTablesIfNotExists(DB_URL);
		clearDataBase();
	}

	@After
	public void clearDataBaseAfterTesting() throws Exception {
		clearDataBase();
	}
	
	private static void clearDataBase() throws Exception {
		try (Connection dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD)) {
			PreparedStatement stmtClearTableUsers = dbConnection.prepareStatement(DELETE_QUERY);

			stmtClearTableUsers.execute();
		}
	}

	private static User[] makeTestUsers() {
		return new User[] { firstUser, secondUser, thirdUser };
	}

	private static void makeTestDataBase() {
		User[] usersToAdd = makeTestUsers();

		for (User user : usersToAdd) {
			user.create();
		}
	}

	@PrepareForTest({ Registry.class })
	@Test
	public void getMailTest() throws ClassNotFoundException {
		Class.forName("org.h2.Driver");
		PowerMockito.mockStatic(Registry.class, new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			}
		});
		User testUser = new User("testowy@gmail.com", "pass");
		Assert.assertEquals("testowy@gmail.com", testUser.getMail());
	}

	@PrepareForTest({ Registry.class })
	@Test
	public void setPasswordHashTest() throws ClassNotFoundException {
		Class.forName("org.h2.Driver");
		PowerMockito.mockStatic(Registry.class, new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			}
		});
		User testUser = new User("testowy@gmail.com", "pass");
		testUser.setPasswordHash("newPassword");
		Assert.assertEquals("newPassword", testUser.getPasswordHash());
	}

	@PrepareForTest({ Registry.class })
	@Test
	public void getPasswordHashTest() throws ClassNotFoundException {
		Class.forName("org.h2.Driver");
		PowerMockito.mockStatic(Registry.class, new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			}
		});
		User testUser = new User("testowy@gmail.com", "pass");
		Assert.assertEquals("pass", testUser.getPasswordHash());
	}

	@PrepareForTest({ Registry.class })
	@Test
	public void getUserByMailTest() throws ClassNotFoundException {
		Class.forName("org.h2.Driver");
		PowerMockito.mockStatic(Registry.class, new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			}
		});
		makeTestDataBase();

		User userByMail = User.getUser("testowy1@gmail.com");
		Assert.assertEquals(firstUser.getMail(), userByMail.getMail());
	}

	
	@PrepareForTest({ Registry.class })
	@Test
	public void updateUserPasswordTest() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		PowerMockito.mockStatic(Registry.class, new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			}
		});
		makeTestDataBase();
		try(Connection dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD)) {
			firstUser.setPasswordHash("newPassword");
			firstUser.update();

			PreparedStatement stmtUser = dbConnection.prepareStatement(SELECT_USER_QUERY);
			stmtUser.setString(1, firstUser.getMail());
			ResultSet rsUser = stmtUser.executeQuery();
			rsUser.next();
			Assert.assertEquals("newPassword", rsUser.getString(2));
		}
		
	}

	@PrepareForTest({ Registry.class })
	@Test
	public void addNewUsersTest() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		PowerMockito.mockStatic(Registry.class, new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			}
		});
		makeTestDataBase();
		List<User> listOfUsers = new ArrayList<>();

		try (Connection dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD)) {
			PreparedStatement stmtUsers = dbConnection.prepareStatement(SELECT_QUERY);
			ResultSet rsUsers = stmtUsers.executeQuery();
			while (rsUsers.next()) {
				listOfUsers.add(new User(rsUsers.getString(1), rsUsers.getString(2)));
			}
		}

		Assert.assertEquals(firstUser, listOfUsers.get(0));
		Assert.assertEquals(secondUser, listOfUsers.get(1));
		Assert.assertEquals(thirdUser, listOfUsers.get(2));
	}

	@PrepareForTest({ Registry.class })
	@Test
	public void deleteUserTest() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		PowerMockito.mockStatic(Registry.class, new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			}
		});
		makeTestDataBase();
		secondUser.delete();
		List<User> listOfUsers = new ArrayList<>();

		try (Connection dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD)) {

			PreparedStatement stmtUsers = dbConnection.prepareStatement(SELECT_QUERY);
			ResultSet rsUsers = stmtUsers.executeQuery();

			while (rsUsers.next()) {
				listOfUsers.add(new User(rsUsers.getString(1), rsUsers.getString(2)));
			}
		}
		Assert.assertEquals(firstUser, listOfUsers.get(0));
		Assert.assertEquals(thirdUser, listOfUsers.get(1));
	}

}
