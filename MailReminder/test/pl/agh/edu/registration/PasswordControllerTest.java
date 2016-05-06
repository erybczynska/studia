package pl.agh.edu.registration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
import pl.agh.edu.password.PasswordController;
import pl.agh.edu.password.PasswordHasher;
import pl.edu.agh.database.Registry;
import pl.edu.agh.database.User;

@RunWith(PowerMockRunner.class)
public class PasswordControllerTest {
	private static final String USER_NAME = "sa";
	private static final String PASSWORD = "";
	private static final String DB_URL = "jdbc:h2:file:./dbtest";

	private static final String DELETE_QUERY = "DELETE FROM users";

	private static final String userPassword = "pass1";
	private static final String passwordHash = PasswordHasher.makePasswordHash(userPassword);
	private static final User testUser = new User("testowy1@gmail.com", passwordHash);
	
	private static final PasswordController controller = PasswordController.getInstance();
	
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
		Connection dbConnection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);

		PreparedStatement stmtClearTableUsers = dbConnection.prepareStatement(DELETE_QUERY);

		stmtClearTableUsers.execute();

		dbConnection.close();
	}

	private static User[] makeTestUsers() {
		User firstUser = new User("testowy1@gmail.com", passwordHash);
		User secondUser = new User("testowy2@gmail.com", "pass2");
		User thirdUser = new User("testowy3@gmail.com", "pass3");	
		return new User[] { firstUser, secondUser, thirdUser };
	}
	
	private static void makeTestDataBase() throws ClassNotFoundException { 
		User[] usersToAdd = makeTestUsers();

		for (User user : usersToAdd) {
			user.create();
		}
	}
	
	@PrepareForTest({ Registry.class })
	@Test
	public void verifyDataTest() throws ClassNotFoundException {
		Class.forName("org.h2.Driver");
		PowerMockito.mockStatic(Registry.class, new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			}
		});
		makeTestDataBase();
		Assert.assertTrue(controller.verifyData(testUser.getMail(), userPassword));
	}
	
}
