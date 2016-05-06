package pl.edu.agh.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Klasa reprezentująca użytkownika aplikacji Mail Reminder
 * 
 * @author ewelinarybczynska
 *
 */
public class User {
	private static final String INSERT_QUERY = "INSERT into " + Registry.userTableName + " (mail, hash) values (?, ?)";
	private static final String DELETE_QUERY = "DELETE from " + Registry.userTableName + " where mail=?";
	private static final String SEARCH_QUERY = "SELECT mail, hash from " + Registry.userTableName + " where mail=?";
	private static final String UPDATE_QUERY = "UPDATE " + Registry.userTableName + " SET hash=? where mail=?";

	private final String mail;
	private String passwordHash;

	/**
	 * Konstruktor parametrowy ustawiający maila oraz zahashowane hasło
	 * użytkownika
	 * 
	 * @param mail
	 *            adres mail użytkownika
	 * @param passwordHash
	 *            zahashowane hasło użytkownika
	 */
	public User(String mail, String passwordHash) {
		this.mail = mail;
		this.passwordHash = passwordHash;
	}

	/**
	 * Metoda pobierająca mail użytkownika
	 * 
	 * @return mail użytkownika
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Metoda ustawiająca zahashowane hasło użytkownika
	 * 
	 * @param passwordHash
	 *            zahashowane hasło użytkownika
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Metoda pobierająca zahashowane hasło użytkownika
	 * 
	 * @return zahashowane hasło użytkownika
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Metoda pobierająca użytkownika z bazy danych o podanym adresie e-mal
	 * 
	 * @param mail
	 *            adres e-mail użytkownika, którego chcemy pobrać
	 * @return
	 */
	public static User getUser(String mail) {
		try (Connection connection = Registry.newConnection()) {
			try (PreparedStatement stmt = connection.prepareStatement(SEARCH_QUERY)) {
				stmt.setString(1, mail);
				ResultSet result = stmt.executeQuery();
				if (result.next()) {
					return new User(result.getString(1), result.getString(2));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Metoda dodająca do bazy danych użytkownika
	 */
	public void create() {
		try (Connection connection = Registry.newConnection()) {
			try (PreparedStatement stmt = connection.prepareStatement(INSERT_QUERY)) {
				stmt.setString(1, mail);
				stmt.setString(2, passwordHash);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda aktualizująca dane użytkownika w bazie danych 
	 */
	public void update() {
		try (Connection connection = Registry.newConnection()) {
			try (PreparedStatement stmt = connection.prepareStatement(UPDATE_QUERY)) {
				stmt.setString(1, passwordHash);
				stmt.setString(2, mail);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/** 
	 * Metoda usuwająca użytkownika z bazy danych 
	 */
	public void delete() {
		try (Connection connection = Registry.newConnection()) {
			try (PreparedStatement stmt = connection.prepareStatement(DELETE_QUERY)) {
				stmt.setString(1, mail);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return mail + " " + passwordHash;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(mail, passwordHash);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		User secondUser = (User) obj;
		return Objects.equals(mail, secondUser.mail) && Objects.equals(passwordHash, secondUser.passwordHash);
	}

}
