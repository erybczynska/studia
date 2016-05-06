package pl.edu.agh.server;

import java.util.regex.Matcher;

import pl.edu.agh.database.ReminderController;
import pl.edu.agh.database.User;

public class DeleteUserCommand implements Command{
	private DeleteUserCommand() {
	}

	public static DeleteUserCommand newInstance(Status status, String inputLine, ReminderController reminderController) {
		if (!status.isLoggedIn()) {
			return new DeleteUserCommand();
		}
		return new DeleteUserCommandLoggedIn(status, inputLine, reminderController);
	}

	@Override
	public String execute() {
		return Responses.NOT_LOGGED_IN_530;
	}

	private static class DeleteUserCommandLoggedIn extends DeleteUserCommand {
		private String mail;
		private ReminderController reminderController;

		private DeleteUserCommandLoggedIn(Status status, String inputLine, ReminderController reminderController) {
			Matcher matcher = CommandPatterns.DELUSR.matcher(inputLine);
			if (matcher.matches()) {
				mail = status.getMail();
			}
			this.reminderController = reminderController;
		}

		@Override
		public String execute() {
			if (mail != null) {
				User userToDelete = User.getUser(mail);
				userToDelete.delete();
				reminderController.removeAllReminders(mail);
				return Responses.COMMAND_SUCCESSFUL_200;
				
			}
			return Responses.WRONG_SYNTAX_540;
		}
	}

}
