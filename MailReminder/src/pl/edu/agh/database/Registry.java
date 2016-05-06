package pl.edu.agh.database;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import pl.agh.edu.password.PasswordHasher;

/**
 * Klasa nawiązująca połoączenie z bazą danych 
 * @author ewelinarybczynska
 *
 */
public class Registry {
	public static final String userTableName = "users";
	public static final String remindersTableName = "reminders";

	static {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda tworząca połączenia 
	 * @return zwraca połączenie z bazą danych 
	 */
	public static Connection newConnection() {
		try {
			String path = Paths.get("db/TestMailReminder").normalize().toAbsolutePath().toString();
			String host = "jdbc:h2:file:" + path + ";MV_STORE=FALSE;MVCC=FALSE";
			return DriverManager.getConnection(host, "sa", "");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/** 
	 * Metoda rejestrująca nowego użytkownika 
	 * @param mail adres e-mail nowego użytkownika 
	 * @param password adres zahashowane hasło użytkownika
	 */
	public static void registryNewUser(String mail, String password) {
		new User(mail, PasswordHasher.makePasswordHash(password)).create();
	}

}
