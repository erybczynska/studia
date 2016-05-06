package pl.edu.agh.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa zarządzającą powiadomieniami
 * 
 * @author ewelinarybczynska
 *
 */
public class ReminderController {
	private static final String INSERT_QUERY = "INSERT INTO " + Registry.remindersTableName
			+ " (mail, content, timeToSend) VALUES(?, ?, ?)";
	private static final String GET_REMINDERS_QUERY = "SELECT * FROM " + Registry.remindersTableName + " WHERE mail=?";
	private static final String DELETE_ALL_QUERY = "DELETE FROM " + Registry.remindersTableName + " WHERE mail=?";
	private static final String DELETE_QUERY = "DELETE FROM " + Registry.remindersTableName
			+ " WHERE mail=? and content=? AND timeToSend=?";
	private static final String SORT_QUERY = "SELECT * FROM " + Registry.remindersTableName + " ORDER BY timetosend";
	private static ReminderController instance = new ReminderController();

	private ReminderController() {
	}

	/**
	 * Metoda zwracjąca jedyną instancję ReminderControllera
	 * 
	 * @return jedyna instancja ReminderControllera
	 */
	public static ReminderController getInstance() {
		return instance;
	}

	/**
	 * Metoda dodająca powiadomienie do bazy danych
	 * 
	 * @param reminder
	 *            powiadomiene do dodania
	 * @return zwraca prawdę, gdy powiadomienie zostanie prawidłowo dodane, w
	 *         przyciwnym razie zwraca fałsz
	 */
	public boolean addReminder(Reminder reminder) {
		try (Connection connection = Registry.newConnection()) {
			try (PreparedStatement stmt = connection.prepareStatement(INSERT_QUERY)) {
				stmt.setString(1, reminder.getMail());
				stmt.setString(2, reminder.getContent());
				stmt.setTimestamp(3, new Timestamp(reminder.getDateToSend()));
				stmt.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Metoda usuwająca wszystkie powiadomienia, użytkownika o podanym adresie
	 * e-mail
	 * 
	 * @param userMail
	 *            adres e-mail użytkownika, którego wszystkie powiadomienia
	 *            zostaną usunięte
	 * @return zwraca prawdę, gdy powiadomienia zostanie prawidłowo usunięte, w
	 *         przyciwnym razie zwraca fałsz
	 */
	public boolean removeAllReminders(String userMail) {
		try (Connection connection = Registry.newConnection()) {
			try (PreparedStatement stmt = connection.prepareStatement(DELETE_ALL_QUERY)) {
				stmt.setString(1, userMail);
				stmt.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Metoda usuwająca pojedyncze powiadomienie
	 * 
	 * @param reminder
	 *            powiadomienie, które zostanie usunięte
	 * @return zwraca prawdę, gdy powiadomienie zostanie prawidłowo usunięte, w
	 *         przyciwnym razie zwraca fałsz
	 */
	public boolean removeReminder(Reminder reminder) {
		try (Connection connection = Registry.newConnection()) {
			try (PreparedStatement stmt = connection.prepareStatement(DELETE_QUERY)) {
				stmt.setString(1, reminder.getMail());
				stmt.setString(2, reminder.getContent());
				stmt.setTimestamp(3, new Timestamp(reminder.getDateToSend()));
				stmt.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Metoda zwracająca listę wszystkich powiadomień użytkownika o podanym
	 * adresie e-mail
	 * 
	 * @param userMail
	 *            adres e-mail użytkownika
	 * @return listę wszystkich powiadomień użytkownika o podanym adresie e-mail
	 */
	public List<Reminder> collectUserReminders(String userMail) {
		List<Reminder> remindersList = new ArrayList<Reminder>();
		try (Connection connection = Registry.newConnection()) {
			try (PreparedStatement stmt = connection.prepareStatement(GET_REMINDERS_QUERY)) {
				stmt.setString(1, userMail);
				try (ResultSet rsReminders = stmt.executeQuery()) {
					while (rsReminders.next()) {
						remindersList.add(createReminder(rsReminders));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return remindersList;
	}

	/**
	 * Metoda zwracająca listę wszystkich powiadomień ustawionych w kolejności
	 * chronologicznej (od najwcześniejszego do najpóźniejszego)
	 * 
	 * @return lista wszystkich powiadomień ustawionych w kolejności
	 *         chronologicznej (od najwcześniejszego do najpóźniejszego)
	 */
	public List<Reminder> generateChronologicalListReminders() {
		List<Reminder> remindersList = new ArrayList<Reminder>();
		try (Connection connection = Registry.newConnection()) {
			try (PreparedStatement stmt = connection.prepareStatement(SORT_QUERY)) {
				ResultSet rsReminders = stmt.executeQuery();
				while (rsReminders.next()) {
					remindersList.add(createReminder(rsReminders));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return remindersList;
	}

	private Reminder createReminder(ResultSet rs) throws SQLException {
		return new Reminder(rs.getString("mail"), rs.getString("content"), rs.getTimestamp(3).getTime());
	}

}
