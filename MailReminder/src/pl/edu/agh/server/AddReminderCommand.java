package pl.edu.agh.server;

import java.util.regex.Matcher;

import pl.agh.edu.mailing.MailingController;
import pl.edu.agh.database.Reminder;
import pl.edu.agh.database.ReminderController;

/**
 * Klasa reprezentująca komendę dodawania nowego przypomnienia
 * 
 * @author ewelinarybczynska
 *
 */
public class AddReminderCommand implements Command {
	private AddReminderCommand() {
	}

	/**
	 * Metoda zwracającą jedyną instację AddReminderCommand
	 * 
	 * @param status
	 *            status zalogowania
	 * @param inputLine
	 *            dane wejściowe komendy
	 * @param controller
	 *            jedyna instancja ReminderControllera, służącego do zarządzania
	 *            przypomnieniami
	 * @return
	 */
	public static AddReminderCommand newInstance(Status status, String inputLine, ReminderController controller) {
		if (!status.isLoggedIn()) {
			return new AddReminderCommand();
		}
		return new AddReminderCommandLoggedIn(status, inputLine, controller);
	}

	/**
	 * Metoda wykonująca działanie komendy
	 * 
	 */
	@Override
	public String execute() {
		return Responses.NOT_LOGGED_IN_530;
	}

	private static class AddReminderCommandLoggedIn extends AddReminderCommand {
		private String mail;
		private long timeToSend = -1;
		private String content;
		private ReminderController controller;

		private AddReminderCommandLoggedIn(Status status, String inputLine, ReminderController controller) {
			Matcher matcher = CommandPatterns.ADD_REMINDER.matcher(inputLine);
			if (matcher.matches()) {
				mail = status.getMail();
				timeToSend = Long.parseLong(matcher.group(1));
				content = matcher.group(2);
			}
			this.controller = controller;
		}

	
		@Override
		public String execute() {
			if (timeToSend != -1) {
				Reminder reminder = new Reminder(mail, content, timeToSend);
				controller.addReminder(reminder);
				MailingController mailingController = MailingController.getInstance();
				mailingController.setReminderToSend();
				return Responses.COMMAND_SUCCESSFUL_200;
			}
			return Responses.WRONG_SYNTAX_540;

		}
	}

}
