package pl.edu.agh.server;

import pl.agh.edu.mailing.MailingController;
import pl.edu.agh.database.ReminderController;

/**
 * Klasa reprezentująca komendę usunięcia wszystkich przypomnień danego
 * użytkownika
 * 
 * @author ewelinarybczynska
 *
 */
public class DeleteAllRemindersCommand implements Command {
	private DeleteAllRemindersCommand() {
	}

	/**
	 * Metoda zwracająca jedyną instancję DeleteAllRemindersCommand
	 * 
	 * @param status
	 *            status zalogowania
	 * @param inputLine
	 *            dane wejściowe komendy
	 * @param reminderController
	 *            jedyna instancja reminderControllera, służącego do weryfikacji
	 *            danych
	 * @param mailingController
	 *            jedyna instancja mailingControllera
	 * @return jedyna instancję DeleteAllRemindersCommand
	 */
	public static DeleteAllRemindersCommand newInstance(Status status, String inputLine,
			ReminderController reminderController, MailingController mailingController) {
		if (!status.isLoggedIn()) {
			return new DeleteAllRemindersCommand();
		}
		return new DeleteAllRemindersCommandLoggedIn(status, inputLine, reminderController, mailingController);
	}

	/**
	 * Metoda wykonująca działanie komendy
	 * 
	 */
	@Override
	public String execute() {
		return Responses.NOT_LOGGED_IN_530;
	}

	private static class DeleteAllRemindersCommandLoggedIn extends DeleteAllRemindersCommand {
		private String mail;
		private ReminderController controller;
		private MailingController mailingController;

		private DeleteAllRemindersCommandLoggedIn(Status status, String inputLine,
				ReminderController reminderController, MailingController mailingController) {
			mail = status.getMail();
			this.controller = reminderController;
			this.mailingController = mailingController;
		}

		@Override
		public String execute() {
			mailingController.stopLastTask();
			controller.removeAllReminders(mail);
			mailingController.setReminderToSend();
			return Responses.COMMAND_SUCCESSFUL_200;
		}
	}
}
