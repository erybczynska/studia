package pl.edu.agh.server;

import java.util.regex.Matcher;

import pl.agh.edu.mailing.MailingController;
import pl.edu.agh.database.Reminder;
import pl.edu.agh.database.ReminderController;

/**
 * Klasa reprezentująca komendę usuwającą pojedyncze przypomnienie
 * 
 * @author ewelinarybczynska
 *
 */
public class DeleteOneReminderCommand implements Command {
	private DeleteOneReminderCommand() {
	}

	/**
	 * Metoda zwracająca jedyną instancję DeleteOneReminderCommand
	 * 
	 * @param status
	 *            status zalogowania
	 * @param inputLine
	 *            dane wejściowe komendy
	 * @param reminderController
	 *            jedyna instancja reminderControllera, służącego do weryfikacji
	 *            danych
	 * @param mailingController
	 *            jedyna instacja mailingControllera
	 * @return jedyna instancję DeleteOneReminderCommand
	 */
	public static DeleteOneReminderCommand newInstance(Status status, String inputLine,
			ReminderController reminderController, MailingController mailingController) {
		if (!status.isLoggedIn()) {
			return new DeleteOneReminderCommand();
		}
		return new DeleteOneReminderCommandLoggedIn(status, inputLine, reminderController, mailingController);
	}

	/**
	 * Metoda wykonująca działanie komendy
	 * 
	 */
	@Override
	public String execute() {
		return Responses.NOT_LOGGED_IN_530;
	}

	private static class DeleteOneReminderCommandLoggedIn extends DeleteOneReminderCommand {
		private String mail;
		private long timeToSend = -1;
		private String content;
		private ReminderController reminderController;
		private MailingController mailingController;

		private DeleteOneReminderCommandLoggedIn(Status status, String inputLine, ReminderController reminderController,
				MailingController mailingController) {
			Matcher matcher = CommandPatterns.DELETE_ONE_REMINDER.matcher(inputLine);
			if (matcher.matches()) {
				mail = status.getMail();
				timeToSend = Long.parseLong(matcher.group(1));
				content = matcher.group(2);
				this.reminderController = reminderController;
				this.mailingController = mailingController;
			}
		}
		
		@Override
		public String execute() {
			if (timeToSend != -1) {
				Reminder reminder = new Reminder(mail, content, timeToSend);
				mailingController.stopLastTask();
				reminderController.removeReminder(reminder);
				mailingController.setReminderToSend();
				return Responses.COMMAND_SUCCESSFUL_200;
			}
			return Responses.WRONG_SYNTAX_540;
		}
	}

}
