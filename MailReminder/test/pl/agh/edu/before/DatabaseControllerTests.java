package pl.agh.edu.before;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseControllerTests {
	private static final String REMINDERS_TABLE_NAME = "Reminders";
	private static final String USERS_TABLE_NAME = "Users";
	
	private static final String CREATE_REMINDERS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS `" + REMINDERS_TABLE_NAME
			+ "` (`mail` VARCHAR(100) NOT NULL, `content` VARCHAR(200) NOT NULL, `timetosend` timestamp NOT NULL)";
	private static final String CREATE_USERS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS `" + USERS_TABLE_NAME
			+ "` (mail VARCHAR(100) NOT NULL, `hash` VARCHAR(100) NOT NULL)";

	/**
	 * Metoda tworząca w bazie danych tabele Reminders i Users jeżeli one nie
	 * istnieją
	 * @param dbUrl 
	 * @param url 
	 * 
	 */
	public static void createTablesIfNotExists(String dbUrl) {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(dbUrl, "sa", "")) {
			try (PreparedStatement stmtReminders = connection.prepareStatement(CREATE_REMINDERS_TABLE_QUERY)) {
				stmtReminders.executeUpdate();
			}
			try (PreparedStatement stmtUsers = connection.prepareStatement(CREATE_USERS_TABLE_QUERY)) {
				stmtUsers.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}