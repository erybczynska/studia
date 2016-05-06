package pl.agh.edu.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import pl.edu.agh.database.Reminder;

/**
 * Klasa kontrolująca panel powiadomień
 * 
 * @author ewelinarybczynska
 *
 */
public class RemindersController {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat FORMATTER = new SimpleDateFormat(DATE_FORMAT);
	private Controller controller;

	/**
	 * Metoda ustawiająca controller
	 * 
	 * @param controller
	 */
	public RemindersController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Metoda służąca do dodania nowego powiadomienia
	 * 
	 * @param timeToSend
	 *            data, kiedy wysłane ma zostać powiadomienie
	 * @param content
	 *            treść powiadomienia
	 * @return zwraca prawdę gdy dodanie nowego powiadomienia się powiedzie, w
	 *         przyciwnym razie zwraca fałsz
	 */
	public Result addNewReminder(String timeToSend, String content) {
		long dateToSendInMillis;
		try {
			Date date = FORMATTER.parse(timeToSend);
			dateToSendInMillis = date.getTime();
		} catch (ParseException e) {
			return Result.WRONG_SYNTAX;
		}
		if (dateToSendInMillis < System.currentTimeMillis()) {
			return Result.WRONG_DATE; 
		}
		controller.checkConnection();
		controller.getSocketOut()
				.println(String.format(ClientCommands.ADD_REMINDER, dateToSendInMillis, content));
		String respone = controller.getSocketIn().nextLine();
		if (respone.startsWith("2")) {
			return Result.OK;
		}
		return Result.WRONG_SYNTAX;
	}

	/**
	 * Metoda zwracająca listę powiadomień użytkownika
	 * 
	 * @return lista powiadomień użytkownika
	 */
	public List<Reminder> makeRemindersList() {
		controller.checkConnection();
		controller.getSocketOut().println(ClientCommands.GET_ALL_REMINDERS);
		String respone = controller.getSocketIn().nextLine();
		String[] remindersTable = respone.split(Pattern.quote("&$#"));

		List<Reminder> remindersList = new ArrayList<>();
		for (int i = 0; i < (remindersTable.length - 1); i++) {
			String[] remTable = remindersTable[i].split("_", 3);
			Reminder rem = new Reminder(remTable[0], remTable[2], Long.parseLong(remTable[1]));
			remindersList.add(rem);
		}
		return remindersList;
	}

	/**
	 * Metoda usuwająca wszystkie powiadomienia użytkowinika
	 * 
	 * @return zwraca prawdę jeżeli powiadomienia zostaną prawidłowo usunięte, w
	 *         przeciwnym razie zwraca fałsz
	 */
	public boolean deleteAllReminders() {
		controller.checkConnection();
		controller.getSocketOut().println(ClientCommands.DELETE_ALL_REMINDERS);
		String respone = controller.getSocketIn().nextLine();
		if (respone.startsWith("2")) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda usuwająca pojedyczne powiadomienie użytkownika
	 * 
	 * @param timeToSend
	 *            data, kiedy powiadomienie ma zostać wysłane
	 * @param content
	 *            zawartość powiadomienia
	 * @return zwraca prawdę jeżeli powiadomienie zostanie prawidłowo usunięte,
	 *         w przeciwnym razie zwraca fałsz
	 * 
	 */
	public boolean deleteOneReminder(String timeToSend, String content) {
		long dateToSendInMillis;
		try {
			Date date = FORMATTER.parse(timeToSend);
			dateToSendInMillis = date.getTime();
		} catch (ParseException e) {
			return false;
		}
		controller.checkConnection();
		controller.getSocketOut()
				.println(String.format(ClientCommands.DELETE_ONE_REMINDER, dateToSendInMillis, content));
		String respone = controller.getSocketIn().nextLine();
		if (respone.startsWith("2")) {
			return true;
		}
		return false;
	}
}
