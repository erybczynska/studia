package pl.edu.agh.server;

import java.util.List;

import pl.edu.agh.database.Reminder;
import pl.edu.agh.database.ReminderController;

/**
 * Klasa reprezentująca komendę pobierającą wszystkie przypomnienia danego
 * użytkownika
 * 
 * @author ewelinarybczynska
 *
 */
public class GetRemindersCommand implements Command {
	private GetRemindersCommand() {
	}

	/**
	 * Metoda zwracającą jedyną instancję GetRemindersCommand
	 * 
	 * @param status
	 * @param inputLine
	 * @param reminderController
	 * @return
	 */
	public static GetRemindersCommand newInstance(Status status, String inputLine,
			ReminderController reminderController) {
		if (!status.isLoggedIn()) {
			return new GetRemindersCommand();
		}
		return new GetRemindersCommandLoggedIn(status, reminderController);
	}

	/**
	 * Metoda wykonująca działanie komendy
	 * 
	 */
	@Override
	public String execute() {
		return Responses.NOT_LOGGED_IN_530;
	}

	private static class GetRemindersCommandLoggedIn extends GetRemindersCommand {
		private String mail;
		private ReminderController reminderController;

		private GetRemindersCommandLoggedIn(Status status, ReminderController reminderController) {
			mail = status.getMail();
			this.reminderController = reminderController;
		}

		@Override
		public String execute() {
			StringBuilder builder = new StringBuilder();
			//reminderController.collectUserReminders(mail).forEach(rem -> builder.append(rem.toString() + "&$#"));
			List<Reminder> reminderList = reminderController.collectUserReminders(mail);
			for (Reminder rem : reminderList) { 
				builder.append(rem.toString() + "&$#");
			}
			
			builder.append(Responses.COMMAND_SUCCESSFUL_200);
			return builder.toString();
		}
	}
}
