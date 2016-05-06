package pl.edu.agh.server;

import pl.agh.edu.mailing.MailSender;
import pl.agh.edu.mailing.MailingController;
import pl.agh.edu.password.PasswordController;
import pl.edu.agh.database.ReminderController;

/**
 * Klasa wykonująca polecenia
 * 
 * @author ewelinarybczynska
 *
 */
public class LineExecutor {
	private Status status;

	/**
	 * Konstruktor parametrowy, ustawiający status zalogowania
	 * 
	 * @param status
	 *            status zalogowania
	 */
	public LineExecutor(Status status) {
		this.status = status;
	}

	/**
	 * Metoda wykonująca działania odpowiednio wybranej komendy
	 * 
	 * @param inputLine
	 *            dane wejściowe do komendy
	 * @return odpowiedź serwera na komendę 
	 */
	public String execute(String inputLine) {
		if (CommandPatterns.LOGIN.matcher(inputLine).matches()) {
			return LoginCommand.newInstance(status, inputLine, PasswordController.getInstance()).execute();
		} else if (CommandPatterns.QUIT.matcher(inputLine).matches()) {
			return QuitCommand.newInstance().execute();
		} else if (CommandPatterns.ADD_USER.matcher(inputLine).matches()) {
			return AddUserCommand.newInstance(status, inputLine, MailSender.getInstance()).execute();
		} else if (CommandPatterns.ADD_REMINDER.matcher(inputLine).matches()) {
			return AddReminderCommand.newInstance(status, inputLine, ReminderController.getInstance()).execute();
		} else if (CommandPatterns.CHANGE_PASSWORD.matcher(inputLine).matches()) {
			return ChangePasswordCommand.newInstance(status, inputLine, PasswordController.getInstance()).execute();
		} else if (CommandPatterns.DELETE_ONE_REMINDER.matcher(inputLine).matches()) {
			return DeleteOneReminderCommand
					.newInstance(status, inputLine, ReminderController.getInstance(), MailingController.getInstance())
					.execute();
		} else if (CommandPatterns.DELETE_ALL_REMINDERS.matcher(inputLine).matches()) {
			return DeleteAllRemindersCommand
					.newInstance(status, inputLine, ReminderController.getInstance(), MailingController.getInstance())
					.execute();
		} else if (CommandPatterns.GET_REMINDERS.matcher(inputLine).matches()) {
			return GetRemindersCommand.newInstance(status, inputLine, ReminderController.getInstance()).execute();
		} else if (CommandPatterns.FORGET_PASSWORD.matcher(inputLine).matches()) {
			return ForgetPasswordCommand.newInstance(status, inputLine, MailSender.getInstance()).execute();
		} else if (CommandPatterns.LOGOUT.matcher(inputLine).matches()) {
			return LogoutCommand.newInstance(status).execute();
		} else if (CommandPatterns.DELUSR.matcher(inputLine).matches()) {
			return DeleteUserCommand.newInstance(status, inputLine, ReminderController.getInstance()).execute();
		}
		return Responses.WRONG_SYNTAX_540;
	}

}
